from pathlib import Path
import json,math,random,re,subprocess,sys
import argparse,os
R=Path(__file__).resolve().parents[3]
os.chdir(R)
P=json.loads((R/"imports/数组.import.json").read_text(encoding="utf-8"))["problems"]
sys.stdout.reconfigure(encoding='utf-8')
B=R/'.work/array/tmp';B.mkdir(parents=True,exist_ok=True);T=B/'tests';T.mkdir(exist_ok=True)
parser=argparse.ArgumentParser();parser.add_argument('--jdk17',required=True);parser.add_argument('--jdk8',required=True);args=parser.parse_args()
JDK8=Path(args.jdk8).resolve();JDK17=Path(args.jdk17).resolve()
templates=json.loads((R/'imports/数组-力扣模板.json').read_text(encoding='utf-8'))
def run(argv,**kwargs):
    r=subprocess.run([str(x) for x in argv],capture_output=True,**kwargs)
    if r.returncode:raise RuntimeError(r.stderr.decode('utf-8',errors='replace')+r.stdout.decode('utf-8',errors='replace'))
    return r.stdout.decode('utf-8',errors='replace')
def j(x,typ=None):
    if isinstance(x,bool):return str(x).lower()
    if isinstance(x,str):return json.dumps(x,ensure_ascii=True)
    if isinstance(x,int):return str(x)
    if isinstance(x,list):
        if typ=='int[][]' or (x and isinstance(x[0],list)):
            return 'new int[][]{'+','.join('{'+','.join(str(a) for a in row)+'}' for row in x)+'}'
        return 'new int[]{'+','.join(str(a) for a in x)+'}'
    raise ValueError(x)
def spiral(a):
    if not a or not a[0]:return []
    m,n=len(a),len(a[0]);seen=set();row=col=d=0;ans=[];dirs=[(0,1),(1,0),(0,-1),(-1,0)]
    for _ in range(m*n):
        ans.append(a[row][col]);seen.add((row,col));dr,dc=dirs[d];rr,cc=row+dr,col+dc
        if not(0<=rr<m and 0<=cc<n) or (rr,cc) in seen:d=(d+1)%4;dr,dc=dirs[d]
        row+=dr;col+=dc
    return ans
def unback(s):
    a=[]
    for c in s:
        if c=='#':
            if a:a.pop()
        else:a.append(c)
    return ''.join(a)
def window(s,t):
    if not t:return ''
    from collections import Counter
    need=Counter(t);best=None
    for l in range(len(s)):
        for r in range(l+1,len(s)+1):
            c=Counter(s[l:r])
            if all(c[k]>=v for k,v in need.items()):
                if best is None or r-l<len(best):best=s[l:r]
                break
    return best or ''
rng=random.Random(20261102)
def cases(p):
    id=p['id'];out=[p['inputs']]
    for _ in range(110):
        a=[rng.randrange(-5,6) for _ in range(rng.randrange(0,13))]
        if id in (704,35):x={'nums':sorted(set(a)),'target':rng.randrange(-7,8)}
        elif id==34:x={'nums':sorted(a),'target':rng.randrange(-7,8)}
        elif id==69:x={'x':rng.randrange(0,2147483648)}
        elif id==367:x={'num':rng.randrange(1,2147483648)}
        elif id==27:x={'nums':a,'val':rng.randrange(-5,6)}
        elif id in (26,977):x={'nums':sorted(a)}
        elif id==283:x={'nums':a}
        elif id==844:x={k:''.join(rng.choice('ab#') for _ in range(rng.randrange(0,15))) for k in ('s','t')}
        elif id==209:x={'target':rng.randrange(1,35),'nums':[rng.randrange(1,9) for _ in a]}
        elif id==904:x={'fruits':[rng.randrange(4) for _ in a]}
        elif id==76:x={'s':''.join(rng.choice('ABab') for _ in range(rng.randrange(0,14))),'t':''.join(rng.choice('ABab') for _ in range(rng.randrange(0,5)))}
        elif id==59:x={'n':rng.randrange(1,14)}
        elif id in (54,'LCR146'):
            m,n=rng.randrange(0,7),rng.randrange(0,7);mat=[[i*n+c for c in range(n)] for i in range(m)];x={('matrix' if id==54 else 'array'):mat}
        else:continue
        out.append(x)
    if id in (69,367):
        key='x' if id==69 else 'num';out += [{key:x} for x in ([0] if id==69 else [])+[1,2,3,4,2147395600,2147395601,2147483647]]
    if id==76:out += [{'s':'AAABBC','t':'AABC'},{'s':'a','t':'aa'},{'s':'AA','t':'AA'}]
    if id==844:out += [{'s':'####','t':'#'},{'s':'bxj##tw','t':'bxo#j##tw'}]
    return out
def answer(id,x):
    a=x.get('nums',[])
    if id==704:return a.index(x['target']) if x['target'] in a else -1
    if id==35:return next((i for i,v in enumerate(a) if v>=x['target']),len(a))
    if id==34:
        inds=[i for i,v in enumerate(a) if v==x['target']];return [inds[0],inds[-1]] if inds else [-1,-1]
    if id==69:return math.isqrt(x['x'])
    if id==367:return math.isqrt(x['num'])**2==x['num']
    if id==27:return [v for v in a if v!=x['val']]
    if id==26:return sorted(set(a))
    if id==283:return [v for v in a if v!=0]+[0]*a.count(0)
    if id==844:return unback(x['s'])==unback(x['t'])
    if id==977:return sorted(v*v for v in a)
    if id==209:return min([r-l for l in range(len(a)) for r in range(l+1,len(a)+1) if sum(a[l:r])>=x['target']] or [0])
    if id==904:
        a=x['fruits'];return max([r-l for l in range(len(a)) for r in range(l+1,len(a)+1) if len(set(a[l:r]))<=2] or [0])
    if id==76:return window(x['s'],x['t'])
    if id==59:
        n=x['n'];a=[[0]*n for _ in range(n)];row=col=d=0;dirs=[(0,1),(1,0),(0,-1),(-1,0)]
        for v in range(1,n*n+1):
            a[row][col]=v;dr,dc=dirs[d];rr,cc=row+dr,col+dc
            if not(0<=rr<n and 0<=cc<n) or a[rr][cc]:d=(d+1)%4;dr,dc=dirs[d]
            row+=dr;col+=dc
        return a
    if id in (54,'LCR146'):return spiral(x.get('matrix',x.get('array')))
all_sources=[];methods=[];reports=[];total=0
for p in P:
    if not p['slug']:continue
    official=next(s['code'] for s in templates[p['slug']]['codeSnippets'] if s['langSlug']=='java')
    sig=re.search(r'public\s+[^\n{]+\([^)]*\)',official).group()
    for vnum,v in enumerate([{'code':p['code'],'name':'主解'}]+p['variants']):
        code=v['code'];actual=re.search(r'public\s+[^\n{]+\([^)]*\)',code).group();assert sig==actual,(p['id'],sig,actual)
        assert '\t' not in code and all((len(l)-len(l.lstrip()))%4==0 for l in code.splitlines())
        key='S'+str(len(reports));folder=T/key;folder.mkdir(exist_ok=True)
        (folder/'Solution.java').write_text(code,encoding='utf-8')
        run([JDK8/'bin/javac.exe','-encoding','UTF-8','-g',folder/'Solution.java'])
        all_sources.append(code[code.index('class Solution'):].replace('class Solution','class '+key,1))
        stmts=[];caseset=cases(p)
        for x in caseset:
            ex=answer(p['id'],x);decl=[]
            for k,val in x.items():
                typ='String' if isinstance(val,str) else 'int' if isinstance(val,int) else 'int[][]' if k in ('matrix','array') else 'int[]'
                decl.append(typ+' '+k+' = '+j(val,typ)+';')
            call='new '+key+'().'+p['call'];typ=p['ret'];id=p['id']
            if id in (27,26):
                check='int size = '+call+'; int[] actual = Arrays.copyOf(nums, size);'
                if id==27 and vnum==1:check+='Arrays.sort(actual);';ex=sorted(ex)
                check+=' check(size == '+str(len(ex))+' && Arrays.equals(actual, '+j(ex)+'));'
            elif id==283:check=call+'; check(Arrays.equals(nums, '+j(ex)+'));'
            elif typ=='int[]':check='check(Arrays.equals('+call+', '+j(ex)+'));'
            elif typ=='int[][]':check='check(Arrays.deepEquals('+call+', '+j(ex,'int[][]')+'));'
            elif typ=='List<Integer>':check='check('+call+'.equals(Arrays.asList('+','.join(map(str,ex))+')));'
            elif typ=='String':check='check('+call+'.equals('+j(ex)+'));'
            else:check='check('+call+' == '+j(ex)+');'
            stmts.append('{ '+''.join(decl)+check+' }')
        methods.append('static void test'+key+'() {\n'+'\n'.join(stmts)+'\n}')
        reports.append({'id':p['id'],'method':v.get('name'),'cases':len(caseset),'standaloneJava8':'PASS'});total+=len(caseset)
calls=''.join('testS'+str(i)+'();' for i in range(len(reports)))
harness='import java.util.*;\n'+'\n'.join(all_sources)+'\npublic class ArrayChecks { static int n; static void check(boolean x) { n++; if(!x) throw new AssertionError("Case "+n); } public static void main(String[] args) {'+calls+'System.out.println("PASS "+n);}\n'+'\n'.join(methods)+'\n}'
(T/'ArrayChecks.java').write_text(harness,encoding='utf-8')
run([JDK17/'bin/javac.exe','--release','17','-encoding','UTF-8','-g','-d',T,T/'ArrayChecks.java'])
print(run([JDK17/'bin/java.exe','-cp',T,'ArrayChecks']).strip())
# 两份卡码完整Main独立编译，并验证真实标准输入输出。
for p in P:
    if p['slug']:continue
    folder=T/p['id'];folder.mkdir(exist_ok=True);(folder/'Main.java').write_text(p['code'],encoding='utf-8')
    run([JDK8/'bin/javac.exe','-encoding','UTF-8',folder/'Main.java'])
    ncases=0
    for z in range(16):
        if p['id']=='K58':
            a=[2147483647,2147483647] if z==0 else [rng.randrange(-100,101) for _ in range(20)]
            q=[(0,len(a)-1),(0,0),(len(a)-1,len(a)-1)]+[tuple(sorted(rng.sample(range(len(a)),2))) for _ in range(5)]
            stdin=str(len(a))+'\n'+'\n'.join(map(str,a))+'\n'+'\n'.join(f'{l} {r}' for l,r in q)+'\n'
            expected='\n'.join(str(sum(a[l:r+1])) for l,r in q)
        else:
            m,n=((1,5) if z==0 else (5,1) if z==1 else (rng.randrange(2,6),rng.randrange(2,6)))
            a=[[rng.randrange(1,30) for _ in range(n)] for _ in range(m)];total_sum=sum(map(sum,a))
            costs=[abs(2*sum(map(sum,a[:r]))-total_sum) for r in range(1,m)]+[abs(2*sum(sum(row[:c]) for row in a)-total_sum) for c in range(1,n)]
            stdin=f'{m} {n}\n'+'\n'.join(' '.join(map(str,row)) for row in a)+'\n';expected=str(min(costs))
        output=run([JDK8/'bin/java.exe','-cp',folder,'Main'],input=stdin.encode()).strip();assert output==expected,(p['id'],expected,output);ncases+=1
    reports.append({'id':p['id'],'method':'完整Main','cases':ncases,'standaloneJava8':'PASS'});total+=ncases
(R/'outputs/数组-代码验收.json').write_text(json.dumps({'jdk8':str(JDK8),'jdk17':str(JDK17),'javaBlocks':len(reports),'checks':total,'results':reports},ensure_ascii=False,indent=2),encoding='utf-8')
print('24 standalone Java blocks / official signatures PASS;',total,'semantic cases PASS')
