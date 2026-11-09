from concurrent.futures import ThreadPoolExecutor
from pathlib import Path
import json
import subprocess
import sys


sys.stdout.reconfigure(encoding="utf-8")
ROOT = Path(__file__).resolve().parents[3]
JDK = Path(sys.argv[1])
TMP = ROOT / ".work/hot100/next-tmp"
TMP.mkdir(parents=True, exist_ok=True)
BIN = ROOT / "bin"
BIN.mkdir(exist_ok=True)
problems = json.loads((ROOT / "imports/Hot100-21-40.import.json").read_text(encoding="utf8"))["problems"]


def run(args, cwd=ROOT):
    p = subprocess.run(list(map(str, args)), cwd=cwd, capture_output=True, timeout=100)
    if p.returncode:
        raise RuntimeError((p.stdout + p.stderr).decode("utf8", errors="replace"))
    return p.stdout.decode("utf8", errors="replace").strip()


src = ROOT / "src/main/java"
recorder = ROOT / ".work/linkedlist/scripts/LinkedTraceRecorder.java"
run([JDK / "bin/javac.exe", "--release", "17", "-encoding", "UTF-8", "-g", "-d", BIN,
     *sorted((src / "common").glob("*.java")), recorder, *[ROOT / p["javaFile"] for p in problems]])


def capture(p):
    id = p["id"]
    isolated = TMP / str(id)
    isolated.mkdir(exist_ok=True)
    solution = isolated / "Submission.java"
    # 力扣环境自带节点类型。只给独立编译补上其最小定义。
    stub = ""
    if "ListNode" in p["code"]:
        stub = "class ListNode { int val; ListNode next; ListNode(int v){val=v;} ListNode(int v,ListNode n){val=v;next=n;} }"
    elif "TreeNode" in p["code"]:
        stub = "class TreeNode { int val; TreeNode left,right; }"
    elif id == 138:
        stub = "class Node { int val; Node next,random; Node(int v){val=v;} }"
    solution.write_text(p["code"]+"\n"+stub+"\n", encoding="utf8")
    run([JDK / "bin/javac.exe", "--release", "8", "-encoding", "UTF-8", "-d", isolated, solution])
    trace = TMP / f"trace-{id}.ndjson"
    summary = run([JDK / "bin/java.exe", "--add-modules", "jdk.jdi", "-Dfile.encoding=UTF-8",
                   "-cp", BIN, "LinkedTraceRecorder", p["mainClass"], trace, JDK, p["solutionClass"]])
    print(summary, flush=True)
    return {"id": id, "standalone_release8": "PASS", "java17_jdi": "PASS",
            "events": len(trace.read_text(encoding="utf8").splitlines())}


with ThreadPoolExecutor(max_workers=4) as pool:
    checks = list(pool.map(capture, problems))
(TMP / "capture.json").write_text(json.dumps(checks, indent=2)+"\n", encoding="utf8")
