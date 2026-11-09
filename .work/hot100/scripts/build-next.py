from collections import Counter
from pathlib import Path
import hashlib
import html
import json
import re
import sys


sys.stdout.reconfigure(encoding="utf-8")
R = Path(__file__).resolve().parents[3]
D = R / "docs"
T = R / ".work/hot100/next-tmp"
meta = json.loads((R / "imports/Hot100-21-40.import.json").read_text(encoding="utf8"))
checks = {p["id"]: p for p in json.loads((T / "capture.json").read_text(encoding="utf8"))}
catalog_path = D / "assets/catalog.js"


def read_js(path):
    return json.loads(path.read_text(encoding="utf8").split("=", 1)[1].strip().removesuffix(";"))


def write(path, content):
    path.write_text(content, encoding="utf8", newline="\n")


def write_js(path, name, data):
    write(path, f"window.{name} = " + json.dumps(data, ensure_ascii=False, separators=(",", ":")).replace("<", "\\u003c") + ";\n")


catalog = [p for p in read_js(catalog_path) if not (p["chapter"] == "Hot100" and p.get("order", 0) > 20)]
template = (D / "problems/hot100-0001-two-sum/index.html").read_text(encoding="utf8")
for p in meta["problems"]:
    id = p["id"]
    source = (R / p["javaFile"]).read_text(encoding="utf8")
    assert hashlib.sha256(source.encode()).hexdigest() == p["sourceHash"]
    source_lines = source.splitlines()
    start = p["displayStart"]
    code = p["adapted"].splitlines()
    assert [x.rstrip() for x in source_lines[start-1:start-1+len(code)]] == code
    file = T / f"trace-{id}.ndjson"
    raw = [json.loads(l) for l in file.read_text(encoding="utf8").splitlines()]
    log = Path(str(file) + ".stdout").read_text(encoding="utf8")
    expected, actual, pass_flag = re.search(r"Expected:\s*(.*?)\nActual:\s*(.*?)\nResult: (PASS|FAIL)", log, re.S).groups()
    assert pass_flag == "PASS" and expected == actual == p["expected"], (id, expected, actual, p["expected"])
    pool, keys, steps, calls, frames, node_ids = [], {}, [], [], {}, set()

    def intern(value):
        key = json.dumps(value, ensure_ascii=False, sort_keys=True)
        if key not in keys:
            keys[key] = len(pool)
            pool.append(value)
        return keys[key]

    for event in raw:
        phase = event["kind"]
        stack = event["frames"]
        frame = stack[-1]
        index = len(steps)
        if phase == "enter":
            parent = frames[stack[-2]["id"]] if len(stack) > 1 else None
            frames[frame["id"]] = len(calls)
            calls.append(dict(id=len(calls), parent=parent, born=index, label=frame["method"]+"()", frame=frame["id"]))
        active = frames[frame["id"]]
        if phase == "exit":
            calls[active]["returned"] = index
        line = event["line"]
        assert start <= line < start + len(code), (id, line)
        statement = source_lines[line-1]
        kind = ("enter" if phase == "enter" else "return" if phase == "exit" or "return " in statement
                else "condition" if re.search(r"\b(if|while)\b", statement) else "loop" if "for (" in statement
                else "modify" if re.search(r"\+\+|--|\s=\s|\.add\(|\.put\(", statement) else "statement")
        node_ids.update(k for k, v in event["heap"].items() if "val" in v)
        steps.append(dict(line=line, kind=kind, phase=phase, method=event["method"],
                          frames=intern(stack), heap=intern(event["heap"]), roots=intern(event["roots"]),
                          active=active, returnValue=intern(event["returnValue"]) if "returnValue" in event else -1))
    assert raw[-1]["kind"] == "exit" and all("returned" in c for c in calls)
    assert bool(node_ids) == (id != 240)
    assert len(steps) == checks[id]["events"]
    data = dict(id=id, title=p["title"], english=p["english"], chapter="Hot100", order=p["order"],
                group=p["group"], slug=p["page_slug"], input=p["input"], inputObject={}, source=meta["target_url"],
                javaFile=p["javaFile"], testSource=p["test_source"], sourceHash=p["sourceHash"],
                sourceStatus=p["provenance"]+" 由 Java 17 / JDI 录制，调用栈、节点身份及字段均来自实际暂停快照。",
                code=code, displayStart=start, expected=expected, actual=actual, pool=pool,
                nodeIds=sorted(node_ids, key=int), steps=steps, calls=calls, stateMeaning=p["state"],
                transition=p["summary"], validation=checks[id])
    dir = D / "problems" / p["page_slug"]
    dir.mkdir(parents=True, exist_ok=True)
    page = re.sub(r"<title>.*?</title>", lambda _: f'<title>{id} · {html.escape(p["title"])} — 展老师小课堂</title>', template)
    page = page.replace('<script src="../../assets/hot100-player.js">',
                        '<script src="../../assets/hot100-structures.js"></script><script src="../../assets/hot100-player.js">')
    write(dir / "index.html", page)
    write_js(dir / "trace.js", "HOT100_TRACE", data)
    catalog.append(dict(id=id, title=p["title"], english=p["english"], chapter="Hot100", order=p["order"],
                        input=p["input"], source=meta["target_url"], url="problems/"+p["page_slug"]+"/", complete=True))

hot100 = [p for p in catalog if p["chapter"] == "Hot100"]
assert [p["order"] for p in hot100] == list(range(1,41))
assert len({p["url"] for p in catalog}) == len(catalog)
write_js(catalog_path, "CATALOG", catalog)
home = (D / "index.html").read_text(encoding="utf8").replace("Hot100 · 前20题", "Hot100 · 前40题")
for id in ("problem-count", "all-count"):
    home = re.sub(f'(id="{id}">)\\d+', lambda m: m[1]+str(len(catalog)), home)
write(D / "index.html", home)
write(R / "outputs/Hot100-21-40-capture.json", json.dumps(list(checks.values()), ensure_ascii=False, indent=2)+"\n")
print("Built 20 debugger pages.", sum(p["events"] for p in checks.values()), "JDI events.")
