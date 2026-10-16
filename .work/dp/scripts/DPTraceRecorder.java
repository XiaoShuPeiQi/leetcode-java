import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.io.*;

// 仅读取 JDI 对象字段，不改写算法，不调用被观察对象的方法。
public class DPTraceRecorder {
    static Map<Long, Object> cache;
    static Map<String, Object> heap;

    public static void main(String[] args) throws Exception {
        String className = args[0];
        Path output = Path.of(args[1]);
        LaunchingConnector launcher = Bootstrap.virtualMachineManager().defaultConnector();
        Map<String, Connector.Argument> options = launcher.defaultArguments();
        options.get("main").setValue(className + (args.length > 3 ? " dp" : ""));
        options.get("home").setValue(Path.of(args[2]).toAbsolutePath().toString());
        options.get("options").setValue("-Dfile.encoding=UTF-8 -cp bin");
        VirtualMachine vm = launcher.launch(options);
        Process process = vm.process();
        ByteArrayOutputStream stdout = new ByteArrayOutputStream(), stderr = new ByteArrayOutputStream();
        Thread out = new Thread(() -> copy(process.getInputStream(), stdout));
        Thread err = new Thread(() -> copy(process.getErrorStream(), stderr));
        out.start(); err.start();
        String filter = className + "$" + (args.length > 3 ? args[3] : "Solution");
        EventRequestManager requests = vm.eventRequestManager();
        ClassPrepareRequest prepare = requests.createClassPrepareRequest();
        prepare.addClassFilter(filter); prepare.setSuspendPolicy(EventRequest.SUSPEND_ALL); prepare.enable();
        MethodEntryRequest enter = requests.createMethodEntryRequest();
        enter.addClassFilter(filter); enter.setSuspendPolicy(EventRequest.SUSPEND_ALL); enter.enable();
        MethodExitRequest exit = requests.createMethodExitRequest();
        exit.addClassFilter(filter); exit.setSuspendPolicy(EventRequest.SUSPEND_ALL); exit.enable();
        List<Integer> frameIds = new ArrayList<>();
        int nextId = 0, count = 0;
        boolean alive = true;
        try (BufferedWriter writer = Files.newBufferedWriter(output, StandardCharsets.UTF_8)) {
            while (alive) {
                EventSet events;
                try { events = vm.eventQueue().remove(30000); }
                catch (VMDisconnectedException done) { break; }
                if (events == null) throw new IOException("30 秒没有 JDI 事件");
                for (Event event : events) {
                    if (event instanceof ClassPrepareEvent e) {
                        Set<String> locations = new HashSet<>();
                        for (Location loc : e.referenceType().allLineLocations()) {
                            if (loc.method().name().equals("<init>")) continue;
                            if (!locations.add(loc.method().name() + ":" + loc.codeIndex())) continue;
                            BreakpointRequest bp = requests.createBreakpointRequest(loc);
                            bp.setSuspendPolicy(EventRequest.SUSPEND_ALL); bp.enable();
                        }
                    } else if (event instanceof LocatableEvent e) {
                        if (e.location().method().name().equals("<init>")) continue;
                        cache = new HashMap<>(); heap = new LinkedHashMap<>();
                        List<StackFrame> all = e.thread().frames();
                        List<StackFrame> stack = all.stream()
                                .filter(f -> f.location().declaringType().name().equals(filter)).toList();
                        if (stack.isEmpty()) continue;
                        while (frameIds.size() > stack.size()) frameIds.remove(frameIds.size() - 1);
                        while (frameIds.size() < stack.size()) frameIds.add(++nextId);
                        List<Object> frames = new ArrayList<>();
                        for (int i = 0; i < stack.size(); i++) {
                            StackFrame frame = stack.get(stack.size() - i - 1);
                            Map<String, Object> state = new LinkedHashMap<>();
                            state.put("id", frameIds.get(i));
                            state.put("method", frame.location().method().name());
                            state.put("line", frame.location().lineNumber());
                            state.put("vars", variables(frame));
                            frames.add(state);
                        }
                        Map<String, Object> roots = new LinkedHashMap<>();
                        for (StackFrame frame : all) {
                            if (!frame.location().declaringType().name().equals(className)
                                    || !frame.location().method().name().equals("runSolution")) continue;
                            for (LocalVariable local : frame.visibleVariables()) {
                                if (Set.of("root", "root1", "root2").contains(local.name()))
                                    roots.put(local.name(), value(frame.getValue(local), 0));
                            }
                        }
                        String kind = event instanceof MethodEntryEvent ? "enter"
                                : event instanceof MethodExitEvent ? "exit" : "line";
                        Map<String, Object> record = new LinkedHashMap<>();
                        record.put("kind", kind); record.put("method", e.location().method().name());
                        record.put("line", e.location().lineNumber()); record.put("frames", frames);
                        record.put("roots", roots);
                        if (event instanceof MethodExitEvent x)
                            record.put("returnValue", value(x.returnValue(), 0));
                        record.put("heap", heap);
                        writer.write(json(record)); writer.newLine(); count++;
                        if (kind.equals("exit")) frameIds.remove(frameIds.size() - 1);
                    } else if (event instanceof VMDeathEvent || event instanceof VMDisconnectEvent) alive = false;
                }
                if (alive) events.resume();
            }
        } finally { try { vm.dispose(); } catch (VMDisconnectedException ignored) {} }
        process.waitFor(); out.join(); err.join();
        String log = stdout.toString(StandardCharsets.UTF_8);
        Files.writeString(Path.of(output + ".stdout"), log, StandardCharsets.UTF_8);
        if (process.exitValue() != 0 || !log.contains("Result: PASS"))
            throw new IOException("原程序失败：" + stderr.toString(StandardCharsets.UTF_8) + log);
        System.out.println(className + " " + count + " steps / PASS");
    }

    static Map<String, Object> variables(StackFrame frame) throws AbsentInformationException {
        Map<String, Object> vars = new LinkedHashMap<>();
        for (LocalVariable local : frame.visibleVariables()) vars.put(local.name(), value(frame.getValue(local), 0));
        ObjectReference self = frame.thisObject();
        if (self != null) for (Field f : self.referenceType().allFields())
            if (!f.isStatic() && !f.name().startsWith("this$")) vars.putIfAbsent(f.name(), value(self.getValue(f), 0));
        return vars;
    }

    static Value field(ObjectReference object, String name) {
        Field f = object.referenceType().fieldByName(name);
        return f == null ? null : object.getValue(f);
    }
    static int integer(Value value) { return value instanceof IntegerValue n ? n.value() : 0; }
    static void copy(InputStream in, OutputStream out) {
        try { in.transferTo(out); } catch (IOException e) { throw new UncheckedIOException(e); }
    }

    static Object value(Value v, int depth) {
        if (v == null || v instanceof VoidValue) return null;
        if (v instanceof StringReference s) return s.value();
        if (v instanceof CharValue c) return String.valueOf(c.value());
        if (v instanceof BooleanValue b) return b.value();
        if (v instanceof PrimitiveValue p) return p instanceof FloatValue || p instanceof DoubleValue ? p.doubleValue() : p.longValue();
        ObjectReference object = (ObjectReference) v;
        long id = object.uniqueID();
        if (cache.containsKey(id)) return cache.get(id);
        if (depth > 60) throw new IllegalStateException("对象展开过深");
        String type = object.referenceType().name();
        Object result;
        if (object instanceof ArrayReference array) {
            List<Object> values = new ArrayList<>(); cache.put(id, values);
            for (Value item : array.getValues()) values.add(value(item, depth + 1));
            result = values;
        } else if (type.equals("java.util.Arrays$ArrayList")) {
            result = value(field(object, "a"), depth + 1);
        } else if (type.equals("java.util.ArrayList")) {
            List<Object> values = new ArrayList<>(); cache.put(id, values);
            ArrayReference array = (ArrayReference) field(object, "elementData");
            for (Value item : array.getValues(0, integer(field(object, "size")))) values.add(value(item, depth + 1));
            result = values;
        } else if (type.equals("java.lang.StringBuilder")) {
            int count = integer(field(object, "count")), coder = integer(field(object, "coder"));
            ArrayReference array = (ArrayReference) field(object, "value");
            byte[] bytes = new byte[count << coder];
            for (int i = 0; i < bytes.length; i++) bytes[i] = ((ByteValue) array.getValue(i)).value();
            result = new String(bytes, coder == 0 ? StandardCharsets.ISO_8859_1 : StandardCharsets.UTF_16LE);
        } else if (type.equals("java.util.ArrayDeque")) {
            List<Object> values = new ArrayList<>(); cache.put(id, values);
            ArrayReference array = (ArrayReference) field(object, "elements");
            int head = integer(field(object, "head")), tail = integer(field(object, "tail"));
            while (head != tail) { values.add(value(array.getValue(head), depth + 1)); head = (head + 1) % array.length(); }
            result = values;
        } else if (type.startsWith("java.util.HashMap")) {
            List<Object> entries = new ArrayList<>();
            Map<String, Object> map = new LinkedHashMap<>(); map.put("$map", entries); cache.put(id, map);
            ArrayReference table = (ArrayReference) field(object, "table");
            if (table != null) for (Value bucket : table.getValues()) {
                ObjectReference node = (ObjectReference) bucket;
                while (node != null) {
                    entries.add(Arrays.asList(value(field(node, "key"), depth + 1), value(field(node, "value"), depth + 1)));
                    node = (ObjectReference) field(node, "next");
                }
            }
            result = map;
        } else if (type.equals("java.util.HashSet")) {
            result = value(field(object, "map"), depth + 1);
        } else if (Set.of("java.lang.Integer", "java.lang.Long", "java.lang.Boolean", "java.lang.Character", "java.lang.Double").contains(type)) {
            result = value(field(object, "value"), depth + 1);
        } else if (type.equals("common.TreeNode") || type.startsWith("leetcode.binarytree.")) {
            result = Map.of("$ref", id); cache.put(id, result);
            Map<String, Object> fields = new LinkedHashMap<>();
            fields.put("$type", type); heap.put(Long.toString(id), fields);
            for (Field f : object.referenceType().allFields()) if (!f.isStatic() && !f.name().startsWith("this$"))
                fields.put(f.name(), value(object.getValue(f), depth + 1));
        } else throw new IllegalStateException("尚未支持的对象类型：" + type);
        cache.put(id, result);
        return result;
    }

    static String json(Object value) {
        if (value == null) return "null";
        if (value instanceof Number || value instanceof Boolean) return value.toString();
        if (value instanceof Map<?, ?> map) {
            StringJoiner out = new StringJoiner(",", "{", "}");
            for (Map.Entry<?, ?> e : map.entrySet()) out.add(json(e.getKey().toString()) + ":" + json(e.getValue()));
            return out.toString();
        }
        if (value instanceof Iterable<?> items) {
            StringJoiner out = new StringJoiner(",", "[", "]");
            for (Object item : items) out.add(json(item));
            return out.toString();
        }
        StringBuilder out = new StringBuilder("\"");
        for (char c : value.toString().toCharArray()) {
            switch (c) {
                case '"' -> out.append("\\\""); case '\\' -> out.append("\\\\");
                case '\n' -> out.append("\\n"); case '\r' -> out.append("\\r"); case '\t' -> out.append("\\t");
                default -> { if (c < 32) out.append(String.format("\\u%04x", (int) c)); else out.append(c); }
            }
        }
        return out.append('"').toString();
    }
}
