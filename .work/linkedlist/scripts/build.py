from pathlib import Path
import json,re,hashlib,sys
sys.stdout.reconfigure(encoding='utf-8')
R=Path.cwd();SITE=R/'docs';A=SITE/'assets'
metadata=json.loads((R/'imports/链表.import.json').read_text(encoding='utf-8'));meta=metadata['problems'];reports=[];catalog=[]
source='https://acn581cm6pdd.feishu.cn/wiki/HPizwBkbkiEf0zks7jUcvs8En2b'
for p in meta:
    raw=[json.loads(s) for s in (R/'.work/linkedlist/tmp'/f'trace-{p["id"]}.ndjson').read_text(encoding='utf-8').splitlines()]
    log=(R/'.work/linkedlist/tmp'/f'trace-{p["id"]}.ndjson.stdout').read_text(encoding='utf-8')
    assert 'Result: PASS' in log
    pool=[];lookup={}
    def intern(v):
        key=json.dumps(v,ensure_ascii=False,sort_keys=True)
        if key not in lookup:lookup[key]=len(pool);pool.append(v)
        return lookup[key]
    steps=[];calls=[];byframe={};ids=set()
    source_text=(R/p['javaFile']).read_text(encoding='utf-8');lines=source_text.splitlines();assert hashlib.sha256(source_text.encode()).hexdigest()==p['sourceHash'];assert p['algorithm'] in source_text
    for e in raw:
        frame=e['frames'][-1];parent=e['frames'][-2] if len(e['frames'])>1 else None;index=len(steps)
        if e['kind']=='enter':
            call=dict(id=len(calls),parent=byframe[parent['id']] if parent else None,born=index,label=e['method']+'()',frame=frame['id']);byframe[frame['id']]=call['id'];calls.append(call)
        active=byframe[frame['id']]
        if e['kind']=='exit':calls[active]['returned']=index
        for id,node in e['heap'].items():
            if 'val' in node:ids.add(id)
        code=lines[e['line']-1]
        kind='enter' if e['kind']=='enter' else 'return' if e['kind']=='exit' or re.search(r'\breturn\b',code) else 'condition' if re.search(r'\b(if|while)\b',code) else 'loop' if 'for (' in code else 'modify' if re.search(r'\.next\s*=|\+\+|--|\.next;',code) else 'statement'
        steps.append(dict(line=e['line'],kind=kind,phase=e['kind'],method=e['method'],frames=intern(e['frames']),heap=intern(e['heap']),roots=intern(e['roots']),active=active,returnValue=intern(e['returnValue']) if 'returnValue' in e else -1))
    assert all('returned' in c for c in calls)
    assert source_text.splitlines()[p['displayStart']-1:p['displayStart']-1+len(p['algorithm'].splitlines())]==p['algorithm'].splitlines()
    actual=re.search(r'Actual:\n(.*?)\nResult:',log,re.S)[1].strip();assert actual==p['expected']
    data={**p,'source':source,'revision':metadata['revision_id'],'javaFile':p['javaFile'],'chapter':'链表','inputObject':p['example'],'testSource':'飞书链表revision 115的实例走过程；707为力扣官方示例，其余为讲解用例。','sourceStatus':'回放已核对的飞书链表revision 115主解；仅增加package、import、static与外层运行适配，算法逻辑不变。','sourceHash':hashlib.sha256(source_text.encode()).hexdigest(),'code':p['algorithm'].splitlines(),'actual':actual,'nodeIds':sorted(ids,key=int),'pool':pool,'steps':steps,'calls':calls}
    path=SITE/'problems'/p['slug'];path.mkdir(parents=True,exist_ok=True)
    assert not (path/'index.html').exists() or (path/'trace.js').exists(), 'Unexpected existing page'
    (path/'trace.js').write_text('window.LINKED_TRACE = '+json.dumps(data,ensure_ascii=False,separators=(',',':')).replace('<','\\u003c')+';\n',encoding='utf-8')
    page=(R/'docs/problems/0455-assign-cookies/index.html').read_text(encoding='utf-8').replace('455 · 分发饼干',str(p['id'])+' · '+p['title']).replace('greedy-player.js','linked-player.js')
    (path/'index.html').write_text(page,encoding='utf-8')
    catalog.append(dict(id=p['id'],title=p['title'],english=p['english'],chapter='链表',input=p['input'],source=source,url='problems/'+p['slug']+'/',complete=True))
    reports.append(dict(id=p['id'],steps=len(steps),calls=len(calls),nodeIds=len(ids),java='PASS',jdi='PASS',sourceHash=data['sourceHash']))

catalogFile=A/'catalog.js'
raw=catalogFile.read_text(encoding='utf-8')
original=json.loads(raw[raw.index('['):raw.rindex(']')+1])
previous=[p for p in original if p['chapter']!='链表']
assert not {str(p['id']) for p in previous}.intersection(str(p['id']) for p in catalog), 'Duplicate problem IDs'
combined=previous+catalog
catalogFile.write_text('window.CATALOG = '+json.dumps(combined,ensure_ascii=False,separators=(',',':'))+';\n',encoding='utf-8')
home=SITE/'index.html';text=home.read_text(encoding='utf-8')
if 'data-chapter="链表"' not in text:text=text.replace('<button data-chapter="数组">数组</button>','<button data-chapter="数组">数组</button><button data-chapter="链表">链表</button>')
for id in ('problem-count','all-count'):text=re.sub(r'(id="'+id+r'">)\d+',lambda m:m[1]+str(len(combined)),text)
text=re.sub(r'(id="chapter-count">)\d+',lambda m:m[1]+str(len(set(p['chapter'] for p in combined))).zfill(2),text)
home.write_text(text,encoding='utf-8')
report={'status':'PASS','source':metadata['source_url'],'revision':metadata['revision_id'],'existingCatalogEntriesPreserved':len(previous),'catalogTotal':len(combined),'totalSteps':sum(p['steps'] for p in reports),'problems':reports}
(R/'outputs/链表-调试器验收.json').write_text(json.dumps(report,ensure_ascii=False,indent=2),encoding='utf-8')
print('PASS: 8 linked-list pages,',report['totalSteps'],'JDI snapshots; catalog total',len(combined))
