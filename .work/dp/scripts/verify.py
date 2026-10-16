from pathlib import Path
from itertools import product,combinations
from collections import deque
import concurrent.futures,json,math,random,re,subprocess,sys,time
import argparse
sys.stdout.reconfigure(encoding='utf-8')
args=argparse.ArgumentParser();args.add_argument('--jdk',required=True);options=args.parse_args()
R=Path(__file__).resolve().parents[3];B=R/'.work/dp/tmp';B.mkdir(parents=True,exist_ok=True);JDK=options.jdk;rng=random.Random(20261016)
P=json.loads((R/'imports/动态规划.import.json').read_text(encoding='utf-8'))['problems']
def java(x):
    if x is None:return 'null'
    if isinstance(x,bool):return str(x).lower()
    if isinstance(x,int):return str(x)
    if isinstance(x,str):return json.dumps(x)
    if isinstance(x,list):
        if x and isinstance(x[0],list):return 'new int[][]{'+','.join('{'+','.join(map(str,r))+'}' for r in x)+'}'
        if x and isinstance(x[0],str):return 'new String[]{'+','.join(java(y) for y in x)+'}'
        return 'new int[]{'+','.join(map(str,x))+'}'
def subsets(a):
    for mask in range(1<<len(a)):yield [a[i] for i in range(len(a)) if mask>>i&1]
def lcs(a,b):return max((len(x) for x in subsets(a) if subseq(x,b)),default=0)
def subseq(s,t):
    it=iter(t);return all(any(x==y for y in it) for x in s)
def rob(a,circle=False):return max((sum(v for i,v in enumerate(a) if mask>>i&1) for mask in range(1<<len(a)) if not mask&(mask<<1) and not(circle and len(a)>1 and mask&1 and mask>>(len(a)-1)&1)),default=0)
def stock(a,k,fee=0,cool=False):
    def search(i,hold,sold,locked,cash):
        if i==len(a):return cash if not hold else -10**9
        best=search(i+1,hold,sold,False,cash)
        if hold:best=max(best,search(i+1,False,sold+1,cool,cash+a[i]-fee))
        elif not locked and sold<k:best=max(best,search(i+1,True,sold,False,cash-a[i]))
        return best
    return search(0,False,0,False,0)
def shortest(coins,target):
    q=deque([(0,0)]);seen={0}
    while q:
        n,d=q.popleft()
        if n==target:return d
        for c in coins:
            if n+c<=target and n+c not in seen:seen.add(n+c);q.append((n+c,d+1))
    return -1
def paths(grid):
    def walk(i,j):
        if i>=len(grid) or j>=len(grid[0]) or grid[i][j]:return 0
        if (i,j)==(len(grid)-1,len(grid[0])-1):return 1
        return walk(i+1,j)+walk(i,j+1)
    return walk(0,0)
def cost(a):
    def walk(i):return 0 if i>=len(a) else a[i]+min(walk(i+1),walk(i+2))
    return min(walk(0),walk(1))
def compositions(n):
    if n==0:yield []
    for k in range(1,n+1):
        for rest in compositions(n-k):yield [k]+rest
def bst(n):
    if n==0:return {None}
    return {(l,r) for k in range(n) for l in bst(k) for r in bst(n-1-k)}
def change(coins,n):
    def search(i,left):
        if i==len(coins):return int(left==0)
        return sum(search(i+1,left-c*coins[i]) for c in range(left//coins[i]+1))
    return search(0,n)
def ordered(nums,n):return int(n==0) if n<=0 else sum(ordered(nums,n-v) for v in nums if v<=n)
def wordbreak(s,words):return not s or any(s.startswith(w) and wordbreak(s[len(w):],words) for w in words)
def edit(a,b):
    q=deque([(a,0)]);seen={a};limit=max(len(a),len(b))
    while q:
        s,d=q.popleft()
        if s==b:return d
        nxt=[s[:i]+s[i+1:] for i in range(len(s))]
        nxt += [s[:i]+c+s[i+1:] for i in range(len(s)) for c in 'ab']
        if len(s)<limit:nxt += [s[:i]+c+s[i:] for i in range(len(s)+1) for c in 'ab']
        for v in nxt:
            if v not in seen:seen.add(v);q.append((v,d+1))
def treebest(a):
    nodes=[];parents=[];q=deque([0]);idx=1
    # same level-order representation as TreeSupport, with explicit parent edges
    nodes=[a[0]];parents=[-1]
    while q:
        parent=q.popleft()
        for _ in range(2):
            if idx>=len(a):break
            v=a[idx];idx+=1
            if v is not None:nodes.append(v);parents.append(parent);q.append(len(nodes)-1)
    return max(sum(nodes[i] for i in range(len(nodes)) if mask>>i&1) for mask in range(1<<len(nodes)) if all(not(mask>>i&1 and mask>>pa&1) for i,pa in enumerate(parents) if pa>=0))
cases={p['id']:[] for p in P}
def add(id,x,y):cases[id].append((x,y))
for p in P:add(p['id'],p['inputObject'],p['expectedValue'])
for _ in range(180):
    a=[rng.randrange(0,8) for _ in range(rng.randint(1,8))];positive=[x+1 for x in a];small=rng.randint(0,10)
    add(509,{'n':small},round((((1+math.sqrt(5))/2)**small-((1-math.sqrt(5))/2)**small)/math.sqrt(5)))
    n=rng.randint(1,12);add(70,{'n':n},sum(1 for x in compositions(n) if all(v<=2 for v in x)))
    add(746,{'cost':a if len(a)>=2 else a+[1]},cost(a if len(a)>=2 else a+[1]))
    m,n=rng.randint(1,5),rng.randint(1,5);add(62,{'m':m,'n':n},paths([[0]*n for _ in range(m)]))
    grid=[[int(rng.random()<.25) for _ in range(n)] for _ in range(m)];add(63,{'obstacleGrid':grid},paths(grid))
    n=rng.randint(2,10);add(343,{'n':n},max(math.prod(x) for x in compositions(n) if len(x)>1))
    n=rng.randint(1,6);add(96,{'n':n},len(bst(n)))
    add(416,{'nums':positive},any(sum(x)*2==sum(positive) for x in subsets(positive)))
    add(1049,{'stones':positive},min(abs(sum(positive)-2*sum(x)) for x in subsets(positive)))
    t=rng.randint(-sum(a)-2,sum(a)+2);add(494,{'nums':a,'target':t},sum(sum(v*s for v,s in zip(a,sgn))==t for sgn in product([-1,1],repeat=len(a))))
    strs=[''.join(rng.choice('01') for _ in range(rng.randint(1,4))) for _ in range(rng.randint(1,7))];m,n=rng.randint(0,6),rng.randint(0,6)
    add(474,{'strs':strs,'m':m,'n':n},max((len(x) for x in subsets(strs) if sum(s.count('0') for s in x)<=m and sum(s.count('1') for s in x)<=n),default=0))
    coins=rng.sample(range(1,8),rng.randint(1,5));amt=rng.randint(0,15)
    add(518,{'amount':amt,'coins':coins},change(coins,amt));add(377,{'nums':coins,'target':small},ordered(coins,small))
    add(322,{'coins':coins,'amount':amt},shortest(coins,amt));n=rng.randint(1,60);add(279,{'n':n},shortest([i*i for i in range(1,math.isqrt(n)+1)],n))
    s=''.join(rng.choice('ab') for _ in range(rng.randint(1,8)));words=rng.sample(['a','b','ab','ba','aa','bb','aba'],rng.randint(1,6))
    add(139,{'s':s,'wordDict':words},wordbreak(s,words));add(198,{'nums':a},rob(a));add(213,{'nums':a},rob(a,True))
    tree=[rng.randint(0,9) for _ in range(rng.randint(1,8))];add(337,{'root':tree},treebest(tree))
    prices=a;k=rng.randint(0,4);fee=rng.randint(0,4)
    for id,transactions,f,c in [(121,1,0,False),(122,len(a),0,False),(123,2,0,False),(188,k,0,False),(309,len(a),0,True),(714,len(a),fee,False)]:
        x={'prices':prices}
        if id==188:x={'k':k,'prices':prices}
        if id==714:x['fee']=fee
        add(id,x,stock(prices,transactions,f,c))
    arr=[v-4 for v in a];b=[rng.randint(-3,5) for _ in range(rng.randint(1,8))]
    add(300,{'nums':arr},max(len(x) for x in subsets(arr) if all(x[i]<x[i+1] for i in range(len(x)-1))))
    segments=[arr[i:j] for i in range(len(arr)) for j in range(i+1,len(arr)+1)]
    add(674,{'nums':arr},max(len(x) for x in segments if all(x[i]<x[i+1] for i in range(len(x)-1))))
    add(718,{'nums1':arr,'nums2':b},max([0]+[len(x) for x in segments if any(b[i:i+len(x)]==x for i in range(len(b)))]))
    add(1035,{'nums1':arr,'nums2':b},lcs(arr,b));add(53,{'nums':arr},max(sum(x) for x in segments))
    t=''.join(rng.choice('ab') for _ in range(rng.randint(0,7)))
    add(1143,{'text1':s,'text2':t or 'a'},lcs(s,t or 'a'));add(392,{'s':t,'t':s},subseq(t,s))
    add(115,{'s':s,'t':t or 'b'},sum(''.join(s[i] for i in c)==(t or 'b') for c in combinations(range(len(s)),len(t or 'b'))))
    add(583,{'word1':s,'word2':t},len(s)+len(t)-2*lcs(s,t));a1=s[:3];b1=t[:3];add(72,{'word1':a1,'word2':b1},edit(a1,b1))
    add(647,{'s':s},sum(s[i:j]==s[i:j][::-1] for i in range(len(s)) for j in range(i+1,len(s)+1)))
    add(516,{'s':s},max(len(x) for x in subsets(s) if x==x[::-1]))
# Targeted boundary/overflow checks not covered by the small random oracles.
add(494,{'nums':[0,0,0,0,0,0,0,0,1],'target':1},256)
add(377,{'nums':list(range(2,21,2))+[999],'target':999},1)
add(115,{'s':'a'*100+'b','t':'b'},1)
add(115,{'s':'a'*100,'t':'a'*100},1)
add(63,{'obstacleGrid':[[0,1,0,0]]},0)
add(337,{'root':[3,2,3,None,3,None,1]},7)
classes=[];testmethods=[]
for p in P:
    code=p['code'][p['code'].index('class Solution'):].replace('class Solution','static class C'+str(p['id']),1)
    classes.append(code)
    calls=[];method=p['signature'].split('(')[0].split()[-1]
    for idx,(x,y) in enumerate(cases[p['id']]):
        vals=[]
        for k,v in x.items():
            vals.append('TreeSupport.fromLevelOrder('+','.join(java(z) for z in v)+')' if k=='root' else 'Arrays.asList('+','.join(java(z) for z in v)+')' if k=='wordDict' else java(v))
        calls.append('check(new C'+str(p['id'])+'().'+method+'('+','.join(vals)+') == '+java(y)+', "'+str(p['id'])+'/'+str(idx)+'");')
    testmethods.append('static void test'+str(p['id'])+'(){\n'+'\n'.join(calls)+'\n}')
source='import java.util.*;\nimport common.*;\npublic class DPChecks {\nstatic int checks=0;\nstatic void check(boolean ok,String name){if(!ok)throw new AssertionError(name);checks++;}\n'+'\n'.join(classes+testmethods)+'\npublic static void main(String[] args){'+''.join('test'+str(p['id'])+'();' for p in P)+'System.out.println("PASS "+checks);}\n}'
tmp=R/'.work/dp/tmp';tmp.mkdir(parents=True,exist_ok=True);(tmp/'DPChecks.java').write_text(source,encoding='utf-8')
src=[str(p) for p in (R/'src/main/java').rglob('*.java')]+[str(R/'.work/dp/scripts/DPTraceRecorder.java'),str(tmp/'DPChecks.java')]
cp=subprocess.run([str(Path(JDK)/'bin/javac.exe'),'--release','17','-encoding','UTF-8','-g','-d',str(R/'bin'),*src],capture_output=True)
if cp.returncode:raise RuntimeError(cp.stderr.decode('utf-8',errors='replace'))
r=subprocess.run([str(Path(JDK)/'bin/java.exe'),'-Dfile.encoding=UTF-8','-cp',str(R/'bin'),'DPChecks'],capture_output=True)
assert r.returncode==0,r.stderr.decode('utf-8',errors='replace');print(r.stdout.decode('utf-8').strip(),flush=True)
# Standalone Java-8-compatible LeetCode snippets, no dependency on repository wrappers.
for p in P:
    d=B/'standalone'/str(p['id']);d.mkdir(parents=True,exist_ok=True)
    (d/'Solution.java').write_text(('import common.TreeNode;\n' if p['id']==337 else '')+p['code'],encoding='utf-8')
    r=subprocess.run([str(Path(JDK)/'bin/javac.exe'),'--release','8','-encoding','UTF-8','-cp',str(R/'bin'),'-d',str(d),str(d/'Solution.java')],capture_output=True)
    assert r.returncode==0,(p['id'],r.stderr.decode('utf-8',errors='replace'))
report={'status':'PASS','jdk':JDK,'checks':sum(map(len,cases.values())),'problems':{str(k):len(v) for k,v in cases.items()},'standaloneJava8':37,'reference':'exhaustive subsets/signs/paths/sequences, string-edit BFS, transaction enumeration; fixed seed 20261016'}
(B/'verification.json').write_text(json.dumps(report,ensure_ascii=False,indent=2),encoding='utf-8');print('37 standalone Java 8 snippets compiled',flush=True)
meta=P
def record(p):
    dest='.work/dp/tmp/trace-'+str(p['id'])+'.ndjson'
    cmd=[str(Path(JDK)/'bin/java.exe'),'--add-modules','jdk.jdi','-Dfile.encoding=UTF-8','-cp','bin','DPTraceRecorder',p['mainClass'],dest,JDK]
    if p['solutionClass']=='DPSolution':cmd.append('DPSolution')
    r=subprocess.run(cmd,cwd=R,capture_output=True,timeout=150)
    assert r.returncode==0,(p['id'],r.stderr.decode('utf-8',errors='replace'))
    print(r.stdout.decode('utf-8').strip(),flush=True)
    return {'id':p['id'],'status':'PASS'}
with concurrent.futures.ThreadPoolExecutor(max_workers=3) as ex:traces=list(ex.map(record,meta))
(B/'traces-verification.json').write_text(json.dumps(traces,indent=2),encoding='utf-8')
