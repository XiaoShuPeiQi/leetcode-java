from pathlib import Path
import subprocess,json,random,itertools,collections,re,sys
import argparse
sys.stdout.reconfigure(encoding='utf-8')
parser=argparse.ArgumentParser();parser.add_argument('--jdk',required=True);args=parser.parse_args()
R=Path(__file__).resolve().parents[3];B=R/'.work/hashtable/tmp';B.mkdir(parents=True,exist_ok=True);T=B/'tests';T.mkdir(exist_ok=True)
P=json.loads((R/'imports/哈希表.import.json').read_text(encoding='utf-8'))['problems'];V={int(k):v for k,v in json.loads((R/'imports/哈希表-扩展解法.json').read_text(encoding='utf-8')).items()}
J8=Path(args.jdk)
rng=random.Random(20261102)
def java(x):
    if isinstance(x,bool):return str(x).lower()
    if isinstance(x,int):return str(x)
    if isinstance(x,str):return json.dumps(x,ensure_ascii=False)
    if isinstance(x,list):return 'new int[]{'+','.join(map(str,x))+'}'
def combos(a,k,target):return sorted(set(tuple(sorted(a[i] for i in ix)) for ix in itertools.combinations(range(len(a)),k) if sum(a[i] for i in ix)==target))
def happy(n):
    # Independent stopping condition: all non-happy base-10 trajectories reach 4.
    while n not in (1,4):n=sum(int(x)**2 for x in str(n))
    return n==1
classes=[];checks=[];reports=[]
for p in P:
    cases=[];id=p['id']
    if id==242:
        words=[''.join(t) for n in range(4) for t in itertools.product('ab',repeat=n)]
        for a in words:
            for b in words:cases.append(([a,b],sorted(a)==sorted(b)))
    elif id==383:
        words=[''.join(t) for n in range(4) for t in itertools.product('ab',repeat=n)]
        for a in words:
            for b in words:cases.append(([a,b],not(collections.Counter(a)-collections.Counter(b))))
    elif id==349:
        for _ in range(180):
            a=[rng.randrange(0,12) for _ in range(rng.randrange(9))];b=[rng.randrange(0,12) for _ in range(rng.randrange(9))]
            cases.append(([a,b],sorted(set(a)&set(b))))
        cases.append(([[0,1000,1000],[1000,0]],[0,1000]))
    elif id==202:
        for n in list(range(1,301))+[2147483647,1000000000]:cases.append(([n],happy(n)))
    elif id==1:
        while len(cases)<150:
            a=[rng.randrange(-9,10) for _ in range(rng.randrange(2,10))];target=rng.randrange(-12,13)
            pairs=[ix for ix in itertools.combinations(range(len(a)),2) if sum(a[i] for i in ix)==target]
            if len(pairs)==1:cases.append(([a,target],list(pairs[0])))
        cases.extend([([[3,3],6],[0,1]),([[-1000000000,1000000000],0],[0,1])])
    elif id==454:
        for _ in range(160):
            n=rng.randrange(1,5);a=[[rng.randrange(-3,4) for i in range(n)] for _ in range(4)]
            expected=sum(sum(v)==0 for v in itertools.product(*a));cases.append((a,expected))
        cases.extend([([[[0,0][i] for i in range(2)] for _ in range(4)],16),([[[268435456],[-268435456],[268435456],[-268435456]][i] for i in range(4)],1)])
    else:
        k=3 if id==15 else 4
        for _ in range(220):
            a=[rng.randrange(-5,6) for i in range(rng.randrange(k,10))];target=0 if k==3 else rng.randrange(-12,13)
            cases.append(([a] if k==3 else [a,target],combos(a,k,target)))
        if id==18:
            for a,t in [([-5,-4,-3,-2],-14),([1000000000]*4,-294967296),([0]*5,0),([-1000000000]*2+[1000000000]*2,0),([2]*5,8)]:cases.append(([a,t],combos(a,4,t)))
        else:
            for a in [[-1,-1,2],[0,0,0,0],[-100000,0,100000]]:cases.append(([a],combos(a,3,0)))
    for vi,v in enumerate([{'code':p['code'],'name':'主解'}]+V.get(id,[])):
        name=f'S{id}V{vi}';code=v['code']
        d=T/name;d.mkdir(exist_ok=True);(d/'Solution.java').write_text(code,encoding='utf-8')
        run=subprocess.run([str(J8/'bin/javac.exe'),'-encoding','UTF-8',str(d/'Solution.java')],capture_output=True)
        assert run.returncode==0,run.stderr.decode('utf-8',errors='replace')
        classes.append(re.sub(r'^import[^\n]*\n','',code,flags=re.M).replace('class Solution','static class '+name,1))
        method=p['signature'].split('(')[0].split()[-1]
        for args,expected in cases:
            call='new '+name+'().'+method+'('+','.join(java(x) for x in args)+')'
            if id in (15,18):
                exp='Arrays.asList('+','.join('Arrays.asList('+','.join(map(str,row))+')' for row in expected)+')'
                expr=f'canonical({call}).equals(canonical({exp}))'
            elif id in (1,349):expr='Arrays.equals(sorted('+call+'),'+java(sorted(expected))+')'
            else:expr=call+' == '+java(expected)
            checks.append(f'check({expr},"{name} case {len(checks)}");')
        reports.append({'id':id,'variant':v['name'],'cases':len(cases),'standaloneJava8':'PASS'})
chunks=['static void batch'+str(i)+'(){\n'+'\n'.join(checks[i*150:(i+1)*150])+'\n}' for i in range((len(checks)+149)//150)]
harness='import java.util.*;\npublic class AllTests {\n'+ '\n'.join(classes)+'''
static int checks=0;
static void check(boolean ok,String name){if(!ok)throw new AssertionError(name);checks++;}
static int[] sorted(int[] a){int[] b=a.clone();Arrays.sort(b);return b;}
static String canonical(List<? extends List<Integer>> a){List<String> b=new ArrayList<>();for(List<Integer> row:a){List<Integer> r=new ArrayList<>(row);Collections.sort(r);b.add(r.toString());}Collections.sort(b);return b.toString();}
'''+ '\n'.join(chunks)+'\npublic static void main(String[] args){'+''.join('batch'+str(i)+'();' for i in range(len(chunks)))+'System.out.println("PASS "+checks);}\n}'
(T/'AllTests.java').write_text(harness,encoding='utf-8')
r=subprocess.run([str(J8/'bin/javac.exe'),'-encoding','UTF-8',str(T/'AllTests.java')],capture_output=True);assert r.returncode==0,r.stderr.decode('utf-8',errors='replace')
r=subprocess.run([str(J8/'bin/java.exe'),'-cp',str(T),'AllTests'],capture_output=True);assert r.returncode==0,r.stderr.decode('utf-8',errors='replace')
print(r.stdout.decode().strip())
(B/'verification.json').write_text(json.dumps({'status':'PASS','checks':len(checks),'java8':str(J8),'variants':reports,'seed':20261102,'oracle':'frequency/sorted strings, set intersection, brute force index combinations; combination multiplicity is retained during result comparison'},ensure_ascii=False,indent=2),encoding='utf-8')
