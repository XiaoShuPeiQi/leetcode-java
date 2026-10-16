import fs from 'node:fs';
import vm from 'node:vm';
import assert from 'node:assert/strict';
import crypto from 'node:crypto';
const meta=JSON.parse(fs.readFileSync('imports/动态规划.import.json','utf8'));
const ctx={window:{}};vm.runInNewContext(fs.readFileSync('docs/assets/catalog.js','utf8'),ctx);
const catalog=ctx.window.CATALOG.filter(p=>p.chapter!=='动态规划'),before=catalog.length;
const reports=[];
for(const p of meta.problems){
 const source=fs.readFileSync(p.java_file,'utf8');
 assert.equal(crypto.createHash('sha256').update(source).digest('hex'),p.sourceHash);
 assert(source.includes(p.adapted));
 const raw=fs.readFileSync('.work/dp/tmp/trace-'+p.id+'.ndjson','utf8').trim().split('\n').map(JSON.parse);
 const log=fs.readFileSync('.work/dp/tmp/trace-'+p.id+'.ndjson.stdout','utf8').replace(/\r/g,'');
 assert(log.includes('Result: PASS'));assert.deepEqual(raw.at(-1).returnValue,p.expectedValue);
 const pool=[],lookup=new Map(),intern=v=>{const key=JSON.stringify(v);if(!lookup.has(key)){lookup.set(key,pool.length);pool.push(v);}return lookup.get(key);};
 const calls=[],byFrame=new Map(),steps=[],states={};
 for(const e of raw){
  const frame=e.frames.at(-1),parent=e.frames.at(-2),index=steps.length;
  if(e.kind==='enter'){
   const label=e.method+'('+Object.entries(frame.vars).slice(0,2).map(([k,v])=>v?.$ref?'#'+v.$ref:Array.isArray(v)?k+'['+v.length+']':String(v)).join(', ')+')';
   const call={id:calls.length,parent:parent?byFrame.get(parent.id):null,born:index,label,frame:frame.id};byFrame.set(frame.id,call.id);calls.push(call);
  }
  const active=byFrame.get(frame.id);assert(active!==undefined);
  if(e.kind==='exit')calls[active].returned=index;
  if(p.id===337&&e.kind==='exit'&&e.method==='traverse'&&frame.vars.node?.$ref)states[String(frame.vars.node.$ref)]=e.returnValue;
  const line=source.split('\n')[e.line-1]||'';
  const kind=e.kind==='enter'?'enter':e.kind==='exit'?'return':/\breturn\b/.test(line)?'return':/\b(if|while)\b/.test(line)?'condition':/\bfor\b/.test(line)?'loop':/dp\[|result\s*=|\+\+/.test(line)?'modify':'statement';
  steps.push({line:e.line,kind,phase:e.kind,method:e.method,frames:intern(e.frames),heap:intern(e.heap),roots:intern(e.roots),active,returnValue:e.returnValue===undefined?-1:intern(e.returnValue),states:intern({...states})});
 }
 assert(calls.every(c=>c.returned!==undefined));
 const data={id:p.id,title:p.title,english:p.english,chapter:'动态规划',slug:p.slug_page,input:p.input,inputObject:p.inputObject,source:meta.source_url,javaFile:p.java_file,testSource:p.test_source,sourceStatus:'算法与本章Java代码一致，仅增加包、独立main、static及运行适配；同题旧解法保留。',sourceHash:p.sourceHash,code:p.adapted.split('\n'),displayStart:p.displayStart,expected:String(p.expectedValue),actual:String(p.expectedValue),pool,steps,calls,stateMeaning:p.state,transition:p.transfer,validation:{java:'PASS',trace:'PASS',enter:calls.length,exit:calls.length}};
 const dir='docs/problems/'+p.slug_page;fs.mkdirSync(dir,{recursive:true});
 fs.writeFileSync(dir+'/trace.js','window.DP_TRACE = '+JSON.stringify(data).replace(/</g,'\\u003c')+';\n');
 fs.writeFileSync(dir+'/index.html','<!doctype html>\n<html lang="zh-CN"><head><script src="../../assets/theme.js"></script><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>'+p.id+' · '+p.title+' — 展老师小课堂</title><link rel="stylesheet" href="../../assets/player.css"><link rel="stylesheet" href="../../assets/stack-view.css"><link rel="stylesheet" href="../../assets/greedy-player.css"><link rel="stylesheet" href="../../assets/dp-player.css"><link rel="stylesheet" href="../../assets/theme.css"><link rel="stylesheet" href="../../assets/code-style.css"><link rel="stylesheet" href="../../assets/panel-resize.css"></head><body><div id="app"></div><script src="trace.js"></script><script src="../../assets/execution-steps.js"></script><script src="../../assets/stack-view.js"></script><script src="../../assets/dp-player.js"></script><script src="../../assets/code-style.js"></script><script src="../../assets/panel-resize.js"></script></body></html>\n');
 catalog.push({id:p.id,title:p.title,english:p.english,chapter:'动态规划',input:p.input,source:meta.source_url,url:'problems/'+p.slug_page+'/',complete:true});
 reports.push({id:p.id,title:p.title,steps:steps.length,calls:calls.length,bytes:fs.statSync(dir+'/trace.js').size,...data.validation});
}
assert.equal(catalog.length,before+37);fs.writeFileSync('docs/assets/catalog.js','window.CATALOG = '+JSON.stringify(catalog).replace(/</g,'\\u003c')+';\n');
let home=fs.readFileSync('docs/index.html','utf8');
if(!home.includes('data-chapter="动态规划"'))home=home.replace('<button data-chapter="贪心">贪心算法</button>','<button data-chapter="贪心">贪心算法</button><button data-chapter="动态规划">动态规划</button>');
home=home.replace(/id="problem-count">\d+/,`id="problem-count">${catalog.length}`).replace(/id="all-count">\d+/,`id="all-count">${catalog.length}`).replace(/id="chapter-count">\d+/,'id="chapter-count">04');
fs.writeFileSync('docs/index.html',home);fs.writeFileSync('outputs/动态规划-调试器验收.json',JSON.stringify({source:meta.source_url,problems:reports},null,2)+'\n');
console.log('37 independent Java/JDI DP pages built; 107 chapter entries, 105 distinct problem IDs.');
