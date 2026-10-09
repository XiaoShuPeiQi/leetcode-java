import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.io.*;

/** 使用 JDK 17 JDI 读取原程序；不改写、不调用被观察对象的方法。 */
public class TraceRecorder {
    static Map<Long, Object> cache = new HashMap<>();
    static final Set<String> SHARED = Set.of("path", "result", "board", "used", "rows", "columns", "boxes", "diagonals", "antiDiagonals", "graph");

    public static void main(String[] args) throws Exception {
        String className = args[0];
        Path output = Path.of(args[1]);
        Path javaHome = Path.of(args[2]).toAbsolutePath();
        LaunchingConnector launcher = Bootstrap.virtualMachineManager().defaultConnector();
        Map<String, Connector.Argument> options = launcher.defaultArguments();
        options.get("main").setValue(className);
        options.get("home").setValue(javaHome.toString());
        options.get("options").setValue("-Dfile.encoding=UTF-8 -cp bin");
        VirtualMachine vm = launcher.launch(options);
        Process process = vm.process();
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();
        Thread out = new Thread(() -> copy(process.getInputStream(), stdout));
        Thread err = new Thread(() -> copy(process.getErrorStream(), stderr));
        out.start(); err.start();
        EventRequestManager requests = vm.eventRequestManager();
        String filter = className + "$Solution*";
        ClassPrepareRequest prepare = requests.createClassPrepareRequest();
        prepare.addClassFilter(filter); prepare.setSuspendPolicy(EventRequest.SUSPEND_ALL); prepare.enable();
        MethodEntryRequest entry = requests.createMethodEntryRequest();
        entry.addClassFilter(filter); entry.setSuspendPolicy(EventRequest.SUSPEND_ALL); entry.enable();
        MethodExitRequest leave = requests.createMethodExitRequest();
        leave.addClassFilter(filter); leave.setSuspendPolicy(EventRequest.SUSPEND_ALL); leave.enable();
        int count = 0;
        long started = System.currentTimeMillis();
        boolean alive = true;
        List<Map<String, Object>> frameStates = new ArrayList<>();
        int nextId = 0;
        try (BufferedWriter writer = Files.newBufferedWriter(output, StandardCharsets.UTF_8)) {
            while (alive) {
                EventSet events;
                try { events = vm.eventQueue().remove(30000); }
                catch (VMDisconnectedException done) { break; }
                if (events == null) throw new IOException("30 秒没有调试事件");
                for (Event event : events) {
                    if (event instanceof ClassPrepareEvent e) {
                        Set<Long> installed = new HashSet<>();
                        for (Location loc : e.referenceType().allLineLocations()) {
                            if (loc.method().name().equals("<init>")) continue;
                            String unique = loc.method().name() + ":" + loc.codeIndex();
                            long key = unique.hashCode();
                            if (!installed.add(key)) continue;
                            BreakpointRequest bp = requests.createBreakpointRequest(loc);
                            bp.setSuspendPolicy(EventRequest.SUSPEND_ALL); bp.enable();
                        }
                    } else if (event instanceof LocatableEvent e) {
                        String method = e.location().method().name();
                        if (method.equals("<init>")) continue;
                        cache = new HashMap<>();
                        List<StackFrame> frames = e.thread().frames().stream()
                                .filter(f -> f.location().declaringType().name().startsWith(className + "$Solution"))
                                .toList();
                        if (frames.isEmpty()) continue;
                        int depth = frames.size();
                        while (frameStates.size() > depth) frameStates.remove(frameStates.size() - 1);
                        while (frameStates.size() < depth) {
                            Map<String, Object> frame = new LinkedHashMap<>();
                            frame.put("id", ++nextId);
                            frameStates.add(frame);
                        }
                        StackFrame top = frames.get(0);
                        Map<String, Object> vars = new LinkedHashMap<>();
                        for (LocalVariable local : top.visibleVariables()) vars.put(local.name(), value(top.getValue(local), 0));
                        ObjectReference self = top.thisObject();
                        if (self != null) {
                            for (Field field : self.referenceType().allFields()) {
                                if (!field.isStatic() && !field.name().startsWith("this$")) vars.putIfAbsent(field.name(), value(self.getValue(field), 0));
                            }
                        }
                        for (int i = 0; i < depth; i++) {
                            StackFrame f = frames.get(depth - i - 1);
                            Map<String, Object> state = frameStates.get(i);
                            state.put("method", f.location().method().name());
                            state.put("line", f.location().lineNumber());
                        }
                        String kind = event instanceof MethodEntryEvent ? "enter" : event instanceof MethodExitEvent ? "exit" : "line";
                        Map<String, Object> record = new LinkedHashMap<>();
                        record.put("kind", kind); record.put("line", e.location().lineNumber());
                        record.put("method", method); record.put("frames", frameStates);
                        record.put("vars", vars);
                        if (event instanceof MethodExitEvent x && vm.canGetMethodReturnValues()) record.put("returnValue", value(x.returnValue(), 0));
                        // 所有帧的共享对象应是当前堆快照；原始局部值通过上一暂停位置保存。
                        Map<String, Object> shared = new LinkedHashMap<>();
                        for (StackFrame f : frames.subList(0, Math.min(2, frames.size()))) {
                            for (LocalVariable local : f.visibleVariables()) {
                                if (SHARED.contains(local.name()) && !vars.containsKey(local.name()) && !shared.containsKey(local.name())) shared.put(local.name(), value(f.getValue(local), 0));
                            }
                        }
                        record.put("shared", shared);
                        writer.write(json(record)); writer.newLine();
                        if (++count % 10000 == 0) { writer.flush(); System.out.println(className + " events=" + count + " seconds=" + (System.currentTimeMillis() - started) / 1000); }
                        if (kind.equals("exit")) frameStates.remove(frameStates.size() - 1);
                    } else if (event instanceof VMDeathEvent || event instanceof VMDisconnectEvent) alive = false;
                }
                if (alive) events.resume();
            }
        } finally { try { vm.dispose(); } catch (VMDisconnectedException ignored) {} }
        process.waitFor(); out.join(); err.join();
        String text = stdout.toString(StandardCharsets.UTF_8);
        Files.writeString(Path.of(output + ".stdout"), text, StandardCharsets.UTF_8);
        if (process.exitValue() != 0 || !text.contains("Result: PASS")) throw new IOException("原程序失败: " + stderr.toString(StandardCharsets.UTF_8) + text);
        System.out.println(className + " events=" + count + " PASS seconds=" + (System.currentTimeMillis() - started) / 1000);
    }

    static void copy(InputStream stream, OutputStream output) { try { stream.transferTo(output); } catch (IOException e) { throw new UncheckedIOException(e); } }
    static Value field(ObjectReference object, String name) { Field f = object.referenceType().fieldByName(name); return f == null ? null : object.getValue(f); }
    static int integer(Value v) { return v instanceof IntegerValue n ? n.value() : 0; }

    static Object value(Value v, int depth) {
        if (v == null || v instanceof VoidValue) return null;
        if (v instanceof StringReference s) return s.value();
        if (v instanceof CharValue c) return String.valueOf(c.value());
        if (v instanceof BooleanValue b) return b.value();
        if (v instanceof PrimitiveValue p) return p instanceof FloatValue || p instanceof DoubleValue ? p.doubleValue() : p.longValue();
        ObjectReference object = (ObjectReference) v;
        long id = object.uniqueID();
        if (cache.containsKey(id)) return cache.get(id);
        if (depth > 12) throw new IllegalStateException("无法完整展开对象: " + object.referenceType().name());
        String type = object.referenceType().name();
        Object result;
        if (object instanceof ArrayReference array) {
            List<Object> values = new ArrayList<>(); cache.put(id, values);
            for (Value item : array.getValues()) values.add(value(item, depth + 1));
            result = values;
        } else if (type.equals("java.util.ArrayList")) {
            int size = integer(field(object, "size"));
            List<Object> values = new ArrayList<>(); cache.put(id, values);
            ArrayReference data = (ArrayReference) field(object, "elementData");
            for (Value item : data.getValues(0, size)) values.add(value(item, depth + 1));
            result = values;
        } else if (type.equals("java.lang.StringBuilder")) {
            int count = integer(field(object, "count"));
            int coder = integer(field(object, "coder"));
            ArrayReference bytes = (ArrayReference) field(object, "value");
            byte[] raw = new byte[count << coder];
            List<Value> items = bytes.getValues(0, raw.length);
            for (int i = 0; i < raw.length; i++) raw[i] = ((ByteValue) items.get(i)).value();
            result = new String(raw, coder == 0 ? StandardCharsets.ISO_8859_1 : StandardCharsets.UTF_16LE);
        } else if (type.equals("java.util.PriorityQueue")) {
            int size = integer(field(object, "size"));
            List<Object> values = new ArrayList<>(); cache.put(id, values);
            ArrayReference data = (ArrayReference) field(object, "queue");
            for (Value item : data.getValues(0, size)) values.add(value(item, depth + 1));
            result = values;
        } else if (type.equals("java.util.HashMap")) {
            Map<String, Object> values = new TreeMap<>(); cache.put(id, values);
            ArrayReference table = (ArrayReference) field(object, "table");
            if (table != null) for (Value bucket : table.getValues()) {
                ObjectReference node = (ObjectReference) bucket;
                while (node != null) {
                    values.put(String.valueOf(value(field(node, "key"), depth + 1)), value(field(node, "value"), depth + 1));
                    node = (ObjectReference) field(node, "next");
                }
            }
            result = values;
        } else if (type.equals("java.util.HashSet")) {
            @SuppressWarnings("unchecked") Map<String, Object> map = (Map<String, Object>) value(field(object, "map"), depth + 1);
            result = new ArrayList<>(map.keySet());
        } else if (type.startsWith("java.util.ImmutableCollections$List")) {
            List<Object> values = new ArrayList<>(); cache.put(id, values);
            if (type.endsWith("ListN")) {
                ArrayReference elements = (ArrayReference) field(object, "elements");
                for (Value item : elements.getValues()) values.add(value(item, depth + 1));
            } else {
                values.add(value(field(object, "e0"), depth + 1));
                Value e1 = field(object, "e1");
                if (e1 != null && !((ObjectReference)e1).referenceType().name().equals("java.lang.Object")) values.add(value(e1, depth + 1));
            }
            result = values;
        } else if (Set.of("java.lang.Integer", "java.lang.Long", "java.lang.Boolean", "java.lang.Character").contains(type)) {
            result = value(field(object, "value"), depth + 1);
        } else if (type.equals("java.lang.Object")) result = "present";
        else throw new IllegalStateException("未支持的真实对象类型: " + type);
        cache.put(id, result); return result;
    }

    static String json(Object value) {
        if (value == null) return "null";
        if (value instanceof Number || value instanceof Boolean) return value.toString();
        if (value instanceof Map<?, ?> map) {
            StringJoiner join = new StringJoiner(",", "{", "}");
            for (Map.Entry<?, ?> e : map.entrySet()) join.add(json(e.getKey().toString()) + ":" + json(e.getValue()));
            return join.toString();
        }
        if (value instanceof Iterable<?> values) {
            StringJoiner join = new StringJoiner(",", "[", "]");
            for (Object item : values) join.add(json(item));
            return join.toString();
        }
        String text = value.toString(); StringBuilder out = new StringBuilder("\"");
        for (char c : text.toCharArray()) {
            switch (c) { case '"' -> out.append("\\\""); case '\\' -> out.append("\\\\"); case '\n' -> out.append("\\n"); case '\r' -> out.append("\\r"); case '\t' -> out.append("\\t"); default -> { if (c < 32) out.append(String.format("\\u%04x", (int)c)); else out.append(c); } }
        }
        return out.append('"').toString();
    }
}
