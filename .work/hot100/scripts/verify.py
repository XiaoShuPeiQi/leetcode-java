from pathlib import Path
import json,random,itertools,math,subprocess,concurrent.futures,re,sys,hashlib
sys.stdout.reconfigure(encoding='utf-8')
R=Path(__file__).resolve().parents[3];B=R/'.work/hot100/tmp/verification';B.mkdir(parents=True,exist_ok=True);P=json.loads((R/'imports/Hot100.import.json').read_text(encoding='utf-8'))['problems'];rng=random.Random(1103)
JDK8=Path(sys.argv[1]);assert (JDK8/'bin/javac.exe').is_file(), 'Usage: py -3.11 verify.py <JDK8 root>'
def canonical(v,unordered=False):
    if isinstance(v,list):
        a=[canonical(x,unordered) for x in v]
        if unordered:a.sort()
        return '['+','.join(a)+']'
    return json.dumps(v,ensure_ascii=False,separators=(',',':'))
def java(v,kind=None):
    if isinstance(v,str):return json.dumps(v,ensure_ascii=False)
    if isinstance(v,int):return str(v)
    if kind=='strs':return 'new String[]{'+','.join(java(x) for x in v)+'}'
    if kind in ('matrix','intervals'):return 'new int[][]{'+','.join('{'+','.join(map(str,row))+'}' for row in v)+'}'
    return 'new int[]{'+','.join(map(str,v))+'}'
def oracle(id,x):
    a=x.get('nums',[])
    if id==1:
        pairs=[list(z) for z in itertools.combinations(range(len(a)),2) if a[z[0]]+a[z[1]]==x['target']]
        return pairs[0] if len(pairs)==1 else None
    if id==49:
        groups={}
        for s in x['strs']:groups.setdefault(''.join(sorted(s)),[]).append(s)
        return list(groups.values())
    if id==128:
        s=sorted(set(a));best=run=0
        for i,v in enumerate(s):
            run=run+1 if i and v==s[i-1]+1 else 1;best=max(best,run)
        return best
    if id==283:return [v for v in a if v]+[0]*a.count(0)
    if id==11:return max((min(x['height'][i],x['height'][j])*(j-i) for i in range(len(x['height'])) for j in range(i+1,len(x['height']))),default=0)
    if id==15:return [list(t) for t in sorted(set(tuple(sorted(t)) for t in itertools.combinations(a,3) if sum(t)==0))]
    if id==42:
        h=x['height'];return sum(max(0,min(max(h[:i+1]),max(h[i:]))-h[i]) for i in range(len(h)))
    if id==3:
        s=x['s'];return max((j-i for i in range(len(s)+1) for j in range(i,len(s)+1) if len(set(s[i:j]))==j-i),default=0)
    if id==438:
        s=x['s'];p=x['p'];return [i for i in range(len(s)-len(p)+1) if sorted(s[i:i+len(p)])==sorted(p)]
    if id==560:return sum(sum(a[i:j])==x['k'] for i in range(len(a)) for j in range(i+1,len(a)+1))
    if id==239:return [max(a[i:i+x['k']]) for i in range(len(a)-x['k']+1)]
    if id==76:
        from collections import Counter
        s=x['s'];need=Counter(x['t']);result=''
        for i in range(len(s)):
            for j in range(i+1,len(s)+1):
                c=Counter(s[i:j])
                if all(c[k]>=v for k,v in need.items()):
                    if not result or j-i<len(result):result=s[i:j]
                    break
        return result
    if id==53:return max(sum(a[i:j]) for i in range(len(a)) for j in range(i+1,len(a)+1))
    if id==56:
        seg=[v[:] for v in x['intervals']];changed=True
        while changed:
            changed=False
            for i in range(len(seg)):
                for j in range(i+1,len(seg)):
                    if max(seg[i][0],seg[j][0])<=min(seg[i][1],seg[j][1]):
                        seg[i]=[min(seg[i][0],seg[j][0]),max(seg[i][1],seg[j][1])];seg.pop(j);changed=True;break
                if changed:break
        return sorted(seg)
    if id==189:
        k=x['k']%len(a);return a[-k:]+a[:-k] if k else a[:]
    if id==238:return [math.prod(a[:i]+a[i+1:]) for i in range(len(a))]
    if id==41:return next(i for i in range(1,len(a)+2) if i not in set(a))
    if id==73:
        m=x['matrix'];return [[0 if any(z==0 for z in m[i]) or any(row[j]==0 for row in m) else m[i][j] for j in range(len(m[0]))] for i in range(len(m))]
    if id==54:
        m=x['matrix'];seen=set();r=c=d=0;ans=[];dirs=[(0,1),(1,0),(0,-1),(-1,0)]
        for _ in range(len(m)*len(m[0])):
            ans.append(m[r][c]);seen.add((r,c));nr=r+dirs[d][0];nc=c+dirs[d][1]
            if not(0<=nr<len(m) and 0<=nc<len(m[0])) or (nr,nc) in seen:d=(d+1)%4
            r+=dirs[d][0];c+=dirs[d][1]
        return ans
    if id==48:
        m=x['matrix'];n=len(m);return [[m[n-1-j][i] for j in range(n)] for i in range(n)]
def randomcase(id):
    a=[rng.randint(-4,4) for _ in range(rng.randint(1,9))]
    if id==1:
        while True:
            a=[rng.randint(-20,20) for _ in range(rng.randint(2,9))];i,j=rng.sample(range(len(a)),2);x={'nums':a,'target':a[i]+a[j]}
            if oracle(id,x) is not None:return x
    if id==49:return {'strs':[''.join(rng.choices('abc',k=rng.randint(0,6))) for _ in range(rng.randint(1,10))]}
    if id in (11,42):return {'height':[rng.randint(0,8) for _ in range(rng.randint(2,10))]}
    if id==3:return {'s':''.join(rng.choices('abCD 12',k=rng.randint(0,10)))}
    if id in (438,76):return {'s':''.join(rng.choices('abc',k=rng.randint(0 if id==3 else 1,10))),('p' if id==438 else 't'):''.join(rng.choices('abc',k=rng.randint(1,5)))}
    if id==560:return {'nums':a,'k':rng.randint(-5,5)}
    if id==239:return {'nums':a,'k':rng.randint(1,len(a))}
    if id==189:return {'nums':a,'k':rng.randint(0,25)}
    if id==56:return {'intervals':[sorted(rng.sample(range(11),2)) for _ in range(rng.randint(1,8))]}
    if id in (73,54,48):
        m=rng.randint(1,5);n=m if id==48 else rng.randint(1,5)
        return {'matrix':[[rng.randint(0,6) for _ in range(n)] for _ in range(m)]}
    if id==238 and len(a)<2:a+=[0]
    return {'nums':a}
special={128:[{'nums':[]},{'nums':[1,1,1,2,3]},{'nums':[1000000000,-1000000000]}],41:[{'nums':[1,1]},{'nums':[2,2]},{'nums':[1,2,3]},{'nums':[-1,-2]}],560:[{'nums':[0,0,0],'k':0},{'nums':[1,-1,0],'k':0}],53:[{'nums':[-3,-2,-5]}],238:[{'nums':[0,0,2]},{'nums':[0,2,3]}],76:[{'s':'AAABBC','t':'AABC'},{'s':'a','t':'aa'}],3:[{'s':'abba'},{'s':''}],73:[{'matrix':[[0]]},{'matrix':[[1,0,3]]},{'matrix':[[1],[0],[3]]}],54:[{'matrix':[[1]]},{'matrix':[[1,2,3]]},{'matrix':[[1],[2],[3]]}],48:[{'matrix':[[1]]}],15:[{'nums':[0,0,0,0]}],239:[{'nums':[2,2,1],'k':2}]}
jobs=[]
for p in P:
    id=p['id'];cases=[p['example']]+special.get(id,[])+[randomcase(id) for _ in range(140)]
    tests=[]
    for c in cases:
        expected=oracle(id,c);assert expected is not None
        setup=' '.join(('String[]' if k=='strs' else 'String' if isinstance(v,str) else 'int[][]' if k in ('matrix','intervals') else 'int[]' if isinstance(v,list) else 'int')+' '+k+' = '+java(v,k)+';' for k,v in c.items())
        void=id in (283,189,73,48)
        execute=('new Solution().'+p['call']+'; Object actual = '+('matrix' if id in (73,48) else 'nums')+';') if void else 'Object actual = new Solution().'+p['call']+';'
        exp=json.dumps(canonical(expected,id in (15,49)),ensure_ascii=False)
        tests.append('{ '+setup+' '+execute+' check(actual, '+exp+'); }')
    helper=r'''
    static String canonical(Object v) {
        if (v instanceof int[]) return Arrays.toString((int[])v).replace(" ", "");
        if (v instanceof int[][]) return Arrays.deepToString((int[][])v).replace(" ", "");
        if (v instanceof List<?>) {
            List<String> a = new ArrayList<>();
            for (Object x : (List<?>)v) a.add(canonical(x));
SORT
            return "[" + String.join(",", a) + "]";
        }
        if (v instanceof String) return "\"" + v + "\"";
        return String.valueOf(v);
    }
    static void check(Object actual, String expected) {
        String a=canonical(actual);
        if (!a.equals(expected)) throw new AssertionError(a+" != "+expected);
    }
'''.replace('SORT','            Collections.sort(a);' if id in (15,49) else '')
    s='import java.util.*;\n'+p['code'][p['code'].index('class Solution {'):]+'\npublic class Test {\n'+helper+'\n public static void main(String[] args) {\n'+'\n'.join(tests)+'\nSystem.out.println("PASS '+str(len(cases))+'");\n}\n}\n'
    out=B/'tests'/str(id);out.mkdir(parents=True,exist_ok=True);(out/'Test.java').write_text(s,encoding='utf-8')
    official=next(q for q in json.loads((R/'imports/Hot100-力扣模板.json').read_text(encoding='utf-8')) if int(q['questionFrontendId'])==id)
    template=official['javaTemplate']
    signature=re.search(r'public\s+[\w<>\[\], ]+\s+\w+\([^)]*\)',template)[0]
    assert re.sub(r'\s','',signature) in re.sub(r'\s','',p['code']),f'signature mismatch {id}'
    jobs.append((p,out,len(cases)))
def run(job):
    p,out,n=job
    for cmd in ([str(JDK8/'bin/javac.exe'),'-encoding','UTF-8','-g','-d',str(out),str(out/'Test.java')],[str(JDK8/'bin/java.exe'),'-cp',str(out),'Test']):
        r=subprocess.run(cmd,capture_output=True,timeout=45)
        if r.returncode:raise RuntimeError(str(p['id'])+' '+r.stdout.decode('utf-8','replace')+r.stderr.decode('utf-8','replace'))
    return {'id':p['id'],'cases':n,'compile':'PASS','independentOracle':'PASS','officialSignature':'PASS','code_sha256':hashlib.sha256(p['code'].encode()).hexdigest()}
with concurrent.futures.ThreadPoolExecutor(max_workers=4) as pool:reports=list(pool.map(run,jobs))
report={'status':'PASS','java8':str(JDK8),'problems':reports,'totalCases':sum(p['cases'] for p in reports),'seed':1103,'oracle':'Python brute-force enumeration, direct coordinate formulas, or independent baseline; compared against actual Java execution.'}
(R/'outputs/Hot100-代码验收.json').write_text(json.dumps(report,ensure_ascii=False,indent=2),encoding='utf-8');print(json.dumps(report,ensure_ascii=False))
