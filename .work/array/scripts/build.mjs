import fs from 'node:fs';
import vm from 'node:vm';
import assert from 'node:assert/strict';
import crypto from 'node:crypto';
const meta=JSON.parse(fs.readFileSync('imports/数组.import.json','utf8'));
const context={window:{}};vm.runInNewContext(fs.readFileSync('docs/assets/catalog.js','utf8'),context);
const original=context.window.CATALOG.filter(p=>p.chapter!=='数组'),catalog=[...original],reports=[];
for(const p of meta.problems){
 const source=fs.readFileSync(p.java_file,'utf8');
 assert.equal(crypto.createHash('sha256').update(source).digest('hex'),p.sourceHash);assert(source.includes(p.adapted));
 const raw=fs.readFileSync('.work/array/tmp/trace-'+p.id+'.ndjson','utf8').trim().split('\n').map(JSON.parse);
 const log=fs.readFileSync('.work/array/tmp/trace-'+p.id+'.ndjson.stdout','utf8').replace(/\r/g,'');assert(log.includes('Result: PASS'));
 const final=raw.at(-1);let actual=final.returnValue;
 if([27,26].includes(p.id))actual=final.frames.at(-1).vars.nums.slice(0,final.returnValue);
 if(p.id===283)actual=final.frames.at(-1).vars.nums;
 if(p.id==='K58'){
   const values=raw.filter(e=>e.kind==='exit'&&e.method==='rangeSum').map(e=>e.returnValue);assert.deepEqual(values,p.expectedValue);actual=values;
 }
 assert.deepEqual(actual,p.expectedValue,p.id+' final Java state');
 const pool=[],lookup=new Map(),intern=v=>{const key=JSON.stringify(v);if(!lookup.has(key)){lookup.set(key,pool.length);pool.push(v);}return lookup.get(key);};
 const calls=[],byFrame=new Map(),steps=[];
 for(const e of raw){
  const frame=e.frames.at(-1),parent=e.frames.at(-2),index=steps.length;
  if(e.kind==='enter'){
   const call={id:calls.length,parent:parent?byFrame.get(parent.id):null,born:index,label:e.method+'()',frame:frame.id};byFrame.set(frame.id,call.id);calls.push(call);
  }
  const active=byFrame.get(frame.id);assert(active!==undefined);
  if(e.kind==='exit')calls[active].returned=index;
  const line=source.split('\n')[e.line-1]||'';
  const kind=e.kind==='enter'?'enter':e.kind==='exit'?'return':/\breturn\b/.test(line)?'return':/\b(if|while)\b/.test(line)?'condition':/\bfor\b/.test(line)?'loop':/\+\+|--|\+=|-=|\]\s*=|\.put\(|\.remove\(|\.add\(/.test(line)?'modify':'statement';
  steps.push({line:e.line,kind,phase:e.kind,method:e.method,frames:intern(e.frames),heap:intern(e.heap),roots:intern(e.roots),active,returnValue:e.returnValue===undefined?-1:intern(e.returnValue)});
 }
 assert(calls.every(c=>c.returned!==undefined));
 const expected=log.match(/Expected:\n([\s\S]*?)\nActual:/)[1].trim(),printed=log.match(/Actual:\n([\s\S]*?)\nResult:/)[1].trim();assert.equal(printed,expected);
 const data={id:p.id,title:p.title,english:p.english,chapter:'数组',slug:p.page_slug,input:p.input,inputObject:p.inputObject,source:meta.source_url,javaFile:p.java_file,testSource:p.test_source,sourceStatus:p.slug?'Solution类体与数组笔记主解一致，只增加static、外层类和独立main。':'回放笔记Main中的同名计算函数，函数体一致；Scanner输入输出与示例数据适配在录制范围之外。',sourceHash:p.sourceHash,code:p.adapted.split('\n'),displayStart:p.displayStart,expected,actual:printed,pool,steps,calls,stateMeaning:p.state,transition:p.summary,validation:{java:'PASS',trace:'PASS',enter:calls.length,exit:calls.length}};
 const dir='docs/problems/'+p.page_slug;fs.mkdirSync(dir,{recursive:true});
 fs.writeFileSync(dir+'/trace.js','window.ARRAY_TRACE = '+JSON.stringify(data).replace(/</g,'\\u003c')+';\n');
 fs.writeFileSync(dir+'/index.html','<!doctype html>\n<html lang="zh-CN"><head><script src="../../assets/theme.js"></script><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>'+p.id+' · '+p.title+' — 展老师小课堂</title><link rel="stylesheet" href="../../assets/player.css"><link rel="stylesheet" href="../../assets/stack-view.css"><link rel="stylesheet" href="../../assets/greedy-player.css"><link rel="stylesheet" href="../../assets/array-player.css"><link rel="stylesheet" href="../../assets/theme.css"><link rel="stylesheet" href="../../assets/code-style.css"><link rel="stylesheet" href="../../assets/panel-resize.css"></head><body><div id="app"></div><script src="trace.js"></script><script src="../../assets/execution-steps.js"></script><script src="../../assets/stack-view.js"></script><script src="../../assets/array-player.js"></script><script src="../../assets/code-style.js"></script><script src="../../assets/panel-resize.js"></script></body></html>\n');
 catalog.push({id:p.id,title:p.title,english:p.english,chapter:'数组',input:p.input,source:meta.source_url,url:'problems/'+p.page_slug+'/',complete:true});
 reports.push({id:p.id,title:p.title,steps:steps.length,calls:calls.length,bytes:fs.statSync(dir+'/trace.js').size,...data.validation});
}
assert.equal(catalog.length,original.length+18);assert.equal(new Set(catalog.map(p=>p.url)).size,catalog.length);
fs.writeFileSync('docs/assets/catalog.js','window.CATALOG = '+JSON.stringify(catalog).replace(/</g,'\\u003c')+';\n');
let home=fs.readFileSync('docs/index.html','utf8');
if(!home.includes('data-chapter="数组"'))home=home.replace('<button data-chapter="回溯">','<button data-chapter="数组">数组</button><button data-chapter="回溯">');
home=home.replace(/id="problem-count">\d+/,`id="problem-count">${catalog.length}`).replace(/id="all-count">\d+/,`id="all-count">${catalog.length}`).replace(/id="chapter-count">\d+/,`id="chapter-count">${String(new Set(catalog.map(p=>p.chapter)).size).padStart(2,'0')}`);
fs.writeFileSync('docs/index.html',home);
fs.writeFileSync('outputs/数组-调试器验收.json',JSON.stringify({source:meta.source_url,problems:reports},null,2)+'\n');
console.log('18 Java/JDI pages built; '+catalog.length+' catalog entries.');
