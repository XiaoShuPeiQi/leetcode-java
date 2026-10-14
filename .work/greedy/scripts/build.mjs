import fs from 'node:fs';
import vm from 'node:vm';
import crypto from 'node:crypto';
import assert from 'node:assert/strict';
const meta=JSON.parse(fs.readFileSync('imports/贪心算法.import.json','utf8'));
const context={window:{}};vm.runInNewContext(fs.readFileSync('docs/assets/catalog.js','utf8'),context);
const catalog=context.window.CATALOG.filter(p=>p.chapter!=='贪心');
const stringify=x=>JSON.stringify(x).replace(/</g,'\\u003c');
const reports=[];
for(const p of meta.problems){
 const source=fs.readFileSync(p.java_file,'utf8'),lines=source.split('\n');
 const original=p.source_java.slice(p.source_java.indexOf('class Solution')).replace('class Solution','static class Solution').split('\n').map(l=>l.trim()?'    '+l:'').join('\n');
 assert(source.includes(original),p.id+' 必须保持飞书类体');
 const raw=fs.readFileSync('.work/greedy/tmp/trace-'+p.id+'.ndjson','utf8').trim().split('\n').map(JSON.parse);
 const log=fs.readFileSync('.work/greedy/tmp/trace-'+p.id+'.ndjson.stdout','utf8').replace(/\r/g,'');
 assert(log.includes('Result: PASS'));
 assert.deepEqual(raw.at(-1).returnValue,p.expectedValue,p.id+' 真实最终返回与预期不符');
 const pool=[],lookup=new Map(),intern=value=>{const key=JSON.stringify(value);if(!lookup.has(key)){lookup.set(key,pool.length);pool.push(value);}return lookup.get(key);};
 const calls=[],byFrame=new Map(),steps=[],states={},cameraRefs=new Set();
 for(const e of raw){
  const frame=e.frames.at(-1),parent=e.frames.at(-2),index=steps.length;
  if(e.kind==='enter'){
   const label=e.method+'('+Object.entries(frame.vars).filter(([k])=>!['cameras'].includes(k)).slice(0,2).map(([k,v])=>v?.$ref?'#'+v.$ref:Array.isArray(v)?k+'['+v.length+']':String(v)).join(', ')+')';
   const call={id:calls.length,parent:parent?byFrame.get(parent.id):null,born:index,label,frame:frame.id};
   byFrame.set(frame.id,call.id);calls.push(call);
  }
  const active=byFrame.get(frame.id);assert(active!==undefined,'调用缺少进入事件');
  if(e.kind==='exit')calls[active].returned=index;
  if(p.id===968&&e.kind==='exit'&&e.method==='traverse'&&frame.vars.node?.$ref){
   states[String(frame.vars.node.$ref)]=e.returnValue;
   if(e.returnValue===1)cameraRefs.add(String(frame.vars.node.$ref));
  }
  if(p.id===968&&e.kind==='exit'&&e.method==='minCameraCover'&&e.returnValue>cameraRefs.size&&e.roots.root?.$ref)cameraRefs.add(String(e.roots.root.$ref));
  const code=lines[e.line-1]||'';
  const kind=e.kind==='enter'?'enter':e.kind==='exit'?'return':/\breturn\b/.test(code)?'return':/\b(if|while)\b/.test(code)?'condition':/\bfor\b/.test(code)?'loop':/(?:\+\+|--|\+=|-=|Math\.(max|min)|result\.add)/.test(code)?'modify':'statement';
  steps.push({line:e.line,kind,phase:e.kind,method:e.method,frames:intern(e.frames),heap:intern(e.heap),roots:intern(e.roots),active,returnValue:e.returnValue===undefined?-1:intern(e.returnValue),states:intern({...states}),cameraRefs:intern([...cameraRefs])});
 }
 assert(calls.every(c=>c.returned!==undefined),p.id+' 调用进入返回必须平衡');
 const displayStart=source.slice(0,source.indexOf(original)).split('\n').length;
 const expected=log.match(/Expected:\n([\s\S]*?)\nActual:/)[1].trim(),actual=log.match(/Actual:\n([\s\S]*?)\nResult:/)[1].trim();
 const data={id:p.id,title:p.title,english:p.english,chapter:'贪心',slug:p.slug,input:p.input,inputObject:p.inputObject,source:meta.source_url,revision:meta.revision_id,javaFile:p.java_file,testSource:p.test_source,sourceStatus:'录制代码与2026-10-14读取的飞书Solution类体一致，只增加static与外层运行适配。',sourceHash:crypto.createHash('sha256').update(source).digest('hex'),code:original.split('\n'),displayStart,expected,actual,pool,steps,calls,validation:{java:'PASS',trace:'PASS',enter:calls.length,exit:calls.length}};
 const dir='docs/problems/'+p.slug;fs.mkdirSync(dir,{recursive:true});
 fs.writeFileSync(dir+'/trace.js','window.GREEDY_TRACE = '+stringify(data)+';\n');
 fs.writeFileSync(dir+'/index.html','<!doctype html>\n<html lang="zh-CN"><head><script src="../../assets/theme.js"></script><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>'+p.id+' · '+p.title+' — 展老师小课堂</title><link rel="stylesheet" href="../../assets/player.css"><link rel="stylesheet" href="../../assets/stack-view.css"><link rel="stylesheet" href="../../assets/greedy-player.css"><link rel="stylesheet" href="../../assets/theme.css"></head><body><div id="app"></div><script src="trace.js"></script><script src="../../assets/execution-steps.js"></script><script src="../../assets/stack-view.js"></script><script src="../../assets/greedy-player.js"></script></body></html>\n');
 catalog.push({id:p.id,title:p.title,english:p.english,chapter:'贪心',input:p.input,source:meta.source_url,url:'problems/'+p.slug+'/',complete:true});
 reports.push({id:p.id,title:p.title,steps:steps.length,calls:calls.length,bytes:fs.statSync(dir+'/trace.js').size,...data.validation});
}
assert.equal(catalog.length,70);
fs.writeFileSync('docs/assets/catalog.js','window.CATALOG = '+stringify(catalog)+';\n');
let home=fs.readFileSync('docs/index.html','utf8');
if(!home.includes('data-chapter="贪心"'))home=home.replace('<button data-chapter="二叉树">二叉树</button>','<button data-chapter="二叉树">二叉树</button><button data-chapter="贪心">贪心算法</button>');
home=home.replace('id="problem-count">53','id="problem-count">70').replace('id="all-count">53','id="all-count">70').replace('id="chapter-count">02','id="chapter-count">03');
fs.writeFileSync('docs/index.html',home);
fs.writeFileSync('outputs/贪心算法-调试器验收.json',JSON.stringify({source:meta.source_url,revision:meta.revision_id,problems:reports},null,2)+'\n');
console.log('17个独立页面：飞书源码、Java输出、真实JDI最终状态、调用进入返回全部PASS。');
