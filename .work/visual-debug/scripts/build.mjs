import fs from 'node:fs';
import path from 'node:path';
import crypto from 'node:crypto';
import readline from 'node:readline';
import vm from 'node:vm';
const stateContext={window:{}};
vm.runInNewContext(fs.readFileSync('docs/assets/search-state.js','utf8'),stateContext);

const meta = JSON.parse(fs.readFileSync('imports/回溯算法.import.json', 'utf8'));
const latestFile = '.work/visual-debug/tmp/feishu-current.json';
const latest = fs.existsSync(latestFile) ? JSON.parse(fs.readFileSync(latestFile, 'utf8').replace(/^\uFEFF/, '')).data.document : null;
const latestSections = latest ? [...latest.content.matchAll(/^### 3\.\d+ \[(\d+)\. ([^\]]+)\][\s\S]*?(?=^### 3\.|^## 4\.|$(?![\s\S]))/gm)] : [];
const clean = s => s.replace(/\/\*[\s\S]*?\*\//g, '').replace(/\/\/[^\n]*/g, '').replace(/\s/g, '');
const fence = String.fromCharCode(96).repeat(3);
const codePattern = () => new RegExp(fence+'java\\s*\\n([\\s\\S]*?)'+fence,'gi');
const slugs = {17:'letter-combinations-of-a-phone-number',37:'sudoku-solver',39:'combination-sum',40:'combination-sum-ii',46:'permutations',47:'permutations-ii',51:'n-queens',77:'combinations',78:'subsets',90:'subsets-ii',93:'restore-ip-addresses',131:'palindrome-partitioning',216:'combination-sum-iii',332:'reconstruct-itinerary',491:'non-decreasing-subsequences'};
const english = {17:'Letter Combinations of a Phone Number',37:'Sudoku Solver',39:'Combination Sum',40:'Combination Sum II',46:'Permutations',47:'Permutations II',51:'N-Queens',77:'Combinations',78:'Subsets',90:'Subsets II',93:'Restore IP Addresses',131:'Palindrome Partitioning',216:'Combination Sum III',332:'Reconstruct Itinerary',491:'Non-decreasing Subsequences'};
const include = process.argv.slice(2).map(Number);
const reports = [];
const SHARED = ['path','result','board','used','rows','columns','boxes','diagonals','antiDiagonals','graph'];
const escapeHTML = s => s.replace(/[&<>"']/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
const stringify = x => JSON.stringify(x).replace(/</g,'\\u003c');

function typeOf(line) {
  if (/\bcontinue\b|\bbreak\b/.test(line)) return 'prune';
  if (/result\.add/.test(line)) return 'save';
  if (/path\.(remove|deleteCharAt)|^board\[.*= '\.'/.test(line)) return 'undo';
  if (/path\.(add|append)|^board\[.*= (?:'Q'|\(char\))/.test(line)) return 'choose';
  if (/\bbacktracking\(|\btraverse\(/.test(line)) return 'call';
  if (/\breturn\b/.test(line)) return 'return';
  if (/\bif\b|\bwhile\b/.test(line)) return 'condition';
  if (/\bfor\b/.test(line)) return 'loop';
  return 'statement';
}

for (const p of meta.problems) {
  const directory = String(p.id).padStart(4,'0')+'-'+slugs[p.id];
  const url = 'problems/'+directory+'/';
  if (include.length && !include.includes(p.id)) continue;
  const file = '.work/visual-debug/tmp/trace-'+p.id+'.ndjson';
  if (!fs.existsSync(file)) continue;
  const source = fs.readFileSync(p.java_file,'utf8');
  const lines = source.split('\n');
  const raw = readline.createInterface({input:fs.createReadStream(file),crlfDelay:Infinity});
  const log = fs.readFileSync(file+'.stdout','utf8');
  const liveSection = latestSections.find(x=>Number(x[1])===p.id);
  const liveCode = liveSection && [...liveSection[0].matchAll(codePattern())][0]?.[1];
  const sourceStatus = liveCode ? (clean(liveCode) === clean(p.source_java) ? '最新飞书算法与本地导入版本一致；注释可能不同。' : '待核对：最新飞书算法与本地导入版本不同，页面采用已验证本地实现。') : '采用已读取的 revision 90 本地快照。';
  const pool = [], poolMap = new Map();
  function intern(value) { const key = JSON.stringify(value); if (!poolMap.has(key)) {poolMap.set(key,pool.length); pool.push(value);} return poolMap.get(key); }
  function internVars(value) { return intern(Object.entries(value).map(([key,value])=>[intern(key),intern(value)])); }
  const frameVars = new Map();
  const nodes = [{id:0,parent:null,born:0,label:'入口',method:'Solution',kind:'root'}];
  const nodeForFrame = new Map();
  const steps = [];
  let previousResult = null;
  const recursiveName = p.id===332 ? 'traverse' : 'backtracking';
  const added = [], removed = [], entered = [], exited = [];
  let lastEvent=null,lastVars=null,boardAdd=0,boardRemove=0;
  let finalReturn = null;
  let i=-1;
  for await (const eventLine of raw) {
    const event=JSON.parse(eventLine); i++;
    if(i===0)nodes[0].method=event.method;
    const activeFrame = event.frames.at(-1);
    frameVars.set(activeFrame.id, event.vars);
    const shared = {...event.shared};
    for (const key of SHARED) if (key in event.vars) shared[key] = event.vars[key];
    const stack = event.frames.map(f=>intern({id:f.id,method:intern(f.method),line:f.line,vars:internVars(Object.fromEntries(Object.entries(frameVars.get(f.id)||{}).filter(([key])=>!SHARED.includes(key)))),sharedKeys:intern(Object.keys(frameVars.get(f.id)||{}).filter(key=>SHARED.includes(key)))}));
    const recursiveFrames = event.frames.filter(f=>f.method===recursiveName);
    let active = recursiveFrames.length ? nodeForFrame.get(recursiveFrames.at(-1).id) : 0;
    const vars = {...event.shared,...event.vars};
    let skippedVars=null,conditionLine=null;
    if(lastEvent?.kind==='line') {
      const before=lines[lastEvent.line-1].trim();
      const following=lines.slice(lastEvent.line).find(line=>line.trim()&&!line.trim().startsWith('//'))?.trim();
      if(before.startsWith('if (')&&following==='continue;'&&event.kind==='line'&&event.line<lastEvent.line&&event.frames.at(-1).id===lastEvent.frames.at(-1).id){skippedVars=lastVars;conditionLine=lastEvent.line;}
      if(p.id!==51 && /path\.(add|append)/.test(before) && lastVars.path!==undefined && vars.path!==undefined && vars.path.length!==lastVars.path.length+1) throw Error(p.id+' path 加入后的状态不符，行 '+lastEvent.line);
      if(/path\.(remove|deleteCharAt)/.test(before) && lastVars.path!==undefined && vars.path!==undefined && vars.path.length!==lastVars.path.length-1) throw Error(p.id+' path 撤销后的状态不符，行 '+lastEvent.line);
      if(/result\.add/.test(before) && lastVars.result!==undefined && vars.result!==undefined && vars.result.length!==lastVars.result.length+1) throw Error(p.id+' 保存答案后的 result 不符');
    }
    lastEvent=event; lastVars=vars;
    const code = lines[event.line-1]?.trim()||'';
    let kind = event.kind === 'enter' ? 'enter' : event.kind === 'exit' ? 'return' : typeOf(code);
    if(skippedVars)kind='prune';
    if(p.id===51 && /path\.add/.test(code) && event.kind==='line')kind='statement';
    if (event.kind==='enter' && event.method===recursiveName) {
      const parentFrame = recursiveFrames.at(-2);
      const parent = parentFrame ? nodeForFrame.get(parentFrame.id) : 0;
      const parentVars=parentFrame?frameVars.get(parentFrame.id):null;
      const label = p.id===332 ? vars.airport : vars.path!==undefined ? JSON.stringify(vars.path) : p.id===37 ? parentVars?'填 '+parentVars.digit+' @ ('+parentVars.row+','+parentVars.col+')':'空格 '+vars.index : parentVars?'Q @ ('+parentVars.row+','+parentVars.col+')':'第 '+vars.row+' 行';
      active = nodes.length;
      const detail=['startIndex','startNum','remaining','index','row'].filter(key=>key in vars).map(key=>key+'='+vars[key]).join(' · ');
      nodes.push({id:active,parent,born:i,label,detail,method:event.method,kind:'call',state:internVars(vars)});
      nodeForFrame.set(activeFrame.id,active); entered.push(activeFrame.id);
    }
    if (event.kind==='exit' && event.method===recursiveName) {exited.push(activeFrame.id); if(active!==undefined) nodes[active].returned=i;}
    if (kind==='prune' && event.kind==='line') {
      const candidateVars=skippedVars||vars;
      const rejected=stateContext.window.SearchState.discarded(p.id,candidateVars);
      nodes.push({id:nodes.length,parent:active??0,born:i,label:rejected.label,detail:rejected.reason,kind:'prune',state:internVars(candidateVars)});
    }
    if (event.kind==='line' && kind==='choose' && /path\.(add|append)/.test(code)) added.push(i);
    if (event.kind==='line' && kind==='undo' && /path\.(remove|deleteCharAt)/.test(code)) removed.push(i);
    if(event.kind==='line' && /^board\[.*= (?:'Q'|\(char\))/.test(code))boardAdd++;
    if(event.kind==='line' && /^board\[.*= '\.'/.test(code))boardRemove++;
    const result = vars.result;
    if (result !== undefined && previousResult !== null && result.length>previousResult.length && active!==undefined) nodes[active].answerAt ??= i;
    if (result!==undefined) previousResult=result;
    if (event.kind==='exit' && event.frames.length===1) finalReturn=event.returnValue;
    if (p.id===37 && event.kind==='exit' && event.method===recursiveName && event.returnValue===true && vars.index===vars.spaces.length) nodes[active].answerAt=i;
    const phrases={enter:'进入 '+event.method+'，调用栈增加一层。',return:event.method+' 返回'+(event.returnValue===undefined||event.returnValue===null?'':' '+JSON.stringify(event.returnValue))+'，随后回到调用者。',prune:'此处真实执行 continue / break；红色虚线表示跳过候选，没有递归进入。',save:'即将保存答案；执行后的结果在下一暂停位置显示。',undo:'即将撤销本层选择；撤销后的变量在下一暂停位置显示。',choose:'即将做选择；选择后的变量在下一暂停位置显示。',call:'即将调用递归；进入事件会创建实际调用节点。',condition:'即将检查条件；后续实际行号显示程序选择的分支。',loop:'循环暂停位置：检查边界或推进候选，变量显示当前值。',statement:'即将执行高亮语句，变量为执行前的真实快照。'};
    steps.push({line:event.line,kind,phase:event.kind,method:event.method,stack:intern(stack),vars:internVars(vars),active:active??0,returnValue:event.returnValue,conditionLine});
  }
  const expected=log.match(/Expected:\r?\n([\s\S]*?)\r?\nActual:/)?.[1]?.trim();
  const actual=log.match(/Actual:\r?\n([\s\S]*?)\r?\nResult:/)?.[1]?.trim();
  if (!log.includes('Result: PASS')) throw Error(p.id+' 原程序未通过');
  if (entered.length!==exited.length || entered.some(x=>!exited.includes(x))) throw Error(p.id+' 调用进入/返回不平衡');
  if (p.id!==37 && added.length!==removed.length) throw Error(p.id+' path 加入/撤销不平衡');
  if(p.id===51 && boardAdd!==boardRemove)throw Error('N 皇后棋盘选择/撤销不平衡');
  if(p.id===37 && boardAdd-boardRemove!==51)throw Error('数独未保留恰好 51 个空格的填入');
  const javaPrint=x=>Array.isArray(x)?'['+x.map(javaPrint).join(', ')+']':String(x);
  const finalState=Object.fromEntries(pool[steps.at(-1).vars].map(([k,v])=>[pool[k],pool[v]]));
  const traceActual=p.id===37?finalState.board:finalReturn;
  if(javaPrint(traceActual)!==actual)throw Error(p.id+' 轨迹最终状态与 Java 输出不同');
  const sourceStart = source.indexOf('    static class Solution');
  const displayStart=source.slice(0,sourceStart).split('\n').length;
  const displayLines=lines.slice(displayStart-1,lines.length-1);
  const data={id:p.id,title:p.title,english:english[p.id],chapter:'回溯',slug:directory,source:meta.source_url,sourceStatus,revision:latest?.revision_id??meta.revision_id,javaFile:p.java_file,input:p.input,testSource:p.test_source,expected,actual,log,sourceHash:crypto.createHash('sha256').update(source).digest('hex'),displayStart,code:displayLines,pool,steps,nodes,finalReturn,validation:{java:'PASS',enter:entered.length,exit:exited.length,pathAdd:added.length,pathRemove:removed.length,boardAdd,boardRemove,trace:'PASS'}};
  const dir=path.join('docs','problems',directory); fs.mkdirSync(dir,{recursive:true});
  const stepKinds=['enter','return','prune','save','undo','choose','call','condition','loop','statement'];
  const stepPhases=['enter','exit','line'];
  const rows=steps.map(s=>[s.line,stepKinds.indexOf(s.kind),stepPhases.indexOf(s.phase),intern(s.method),s.stack,s.vars,s.active,s.returnValue===undefined?-1:intern(s.returnValue),s.conditionLine||0]);
  const header={...data,steps:undefined,stepKinds,stepPhases};
  fs.writeFileSync(path.join(dir,'trace.js'),'window.TRACE = '+stringify(header)+';\nwindow.TRACE.steps = '+stringify(rows)+'.map(s=>({line:s[0],kind:window.TRACE.stepKinds[s[1]],phase:window.TRACE.stepPhases[s[2]],method:window.TRACE.pool[s[3]],stack:s[4],vars:s[5],active:s[6],returnValue:s[7]<0?undefined:window.TRACE.pool[s[7]],conditionLine:s[8]||null}));\n');
  fs.writeFileSync(path.join(dir,'index.html'),'<!doctype html>\n<html lang="zh-CN"><head><script src="../../assets/theme.js"></script><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>'+p.id+' · '+escapeHTML(p.title)+' — 展老师小课堂</title><link rel="stylesheet" href="../../assets/player.css"><link rel="stylesheet" href="../../assets/stack-view.css"><link rel="stylesheet" href="../../assets/theme.css"><link rel="stylesheet" href="../../assets/code-style.css"><link rel="stylesheet" href="../../assets/panel-resize.css"></head><body><div id="app"></div><script src="trace.js"></script><script src="../../assets/execution-steps.js"></script><script src="../../assets/stack-view.js"></script><script src="../../assets/search-state.js"></script><script src="../../assets/player.js?v=classroom-style-1"></script><script src="../../assets/code-style.js"></script><script src="../../assets/panel-resize.js"></script></body></html>\n');
  reports.push({id:p.id,title:p.title,url,steps:steps.length,nodes:nodes.length,bytes:fs.statSync(path.join(dir,'trace.js')).size,sourceStatus,...data.validation});
}
fs.mkdirSync('docs/assets',{recursive:true});
const previousCatalog={window:{}};
if(fs.existsSync('docs/assets/catalog.js'))vm.runInNewContext(fs.readFileSync('docs/assets/catalog.js','utf8'),previousCatalog);
const catalog=[...(previousCatalog.window.CATALOG||[]).filter(p=>p.chapter!=='回溯'),...meta.problems.map(p=>{const slug=String(p.id).padStart(4,'0')+'-'+slugs[p.id];return {id:p.id,title:p.title,english:english[p.id],chapter:'回溯',input:p.input,source:meta.source_url,url:'problems/'+slug+'/',complete:fs.existsSync('docs/problems/'+slug+'/trace.js')};})];
fs.writeFileSync('docs/assets/catalog.js','window.CATALOG = '+stringify(catalog)+';\n');
fs.writeFileSync('.work/visual-debug/logs/build-report.json',JSON.stringify(reports,null,2));
console.log(JSON.stringify(reports,null,2));
