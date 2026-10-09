import fs from 'node:fs';
import vm from 'node:vm';
import crypto from 'node:crypto';
const meta=JSON.parse(fs.readFileSync('imports/二叉树算法.import.json','utf8'));
const stringify=x=>JSON.stringify(x).replace(/</g,'\\u003c');
const context={window:{}};vm.runInNewContext(fs.readFileSync('docs/assets/catalog.js','utf8'),context);
const catalog=context.window.CATALOG.filter(p=>p.chapter!=='二叉树');
const reports=[];
for(const p of meta.problems){
 const source=fs.readFileSync(p.java_file,'utf8'),lines=source.split('\n');
 for(const v of p.variants){
  const local=v.source_java.slice(v.source_java.indexOf('class Solution')).replace(/^class Solution/,'static class '+v.className).split('\n').map(l=>'    '+l).join('\n');
  if(!source.includes(local))throw Error(p.id+' 原文源码不一致：'+v.className);
 }
 const raw=fs.readFileSync('.work/binarytree/tmp/trace-'+p.id+'.ndjson','utf8').trim().split('\n').map(JSON.parse);
 const log=fs.readFileSync('.work/binarytree/tmp/trace-'+p.id+'.ndjson.stdout','utf8').replace(/\r/g,'');
 const primary=log.split('Method: Solution\n')[1]?.split('Method:')[0];
 if(!primary?.includes('Result: PASS'))throw Error('原程序未通过：'+p.id);
 const expected=primary.match(/Expected:\n([\s\S]*?)\nActual:/)[1].trim(),actual=primary.match(/Actual:\n([\s\S]*?)\nResult:/)[1].trim();
 const pool=[],lookup=new Map();
 const intern=value=>{const key=JSON.stringify(value);if(!lookup.has(key)){lookup.set(key,pool.length);pool.push(value);}return lookup.get(key);};
 const calls=[],callByFrame=new Map(),steps=[];
 const refLabel=(x,heap)=>x?.$ref?String(heap[x.$ref]?.val??'#'+x.$ref):x===null?'∅':JSON.stringify(x);
 function label(e){const vars=e.frames.at(-1).vars;const keys=['node','root','a','b','left','right'].filter(k=>k in vars);return e.method+'('+keys.map(k=>refLabel(vars[k],e.heap)).join(', ')+')';}
 for(const e of raw){
  const index=steps.length,frame=e.frames.at(-1);
  if(e.kind==='enter'){
   const parent=e.frames.at(-2);const call={id:calls.length,parent:parent?callByFrame.get(parent.id):null,born:index,label:label(e),frame:frame.id};
   callByFrame.set(frame.id,call.id);calls.push(call);
  }
  const active=callByFrame.get(frame.id);if(active===undefined)throw Error('未找到调用：'+p.id);
  if(e.kind==='exit')calls[active].returned=index;
  const code=lines[e.line-1]||'';
  const kind=e.kind==='enter'?'enter':e.kind==='exit'?'return':/\breturn\b/.test(code)?'return':/path\.(remove|delete)/.test(code)?'undo':/result\.add/.test(code)?'save':/path\.(add|append)/.test(code)?'choose':/\b(if|while)\b/.test(code)?'condition':/\bfor\b/.test(code)?'loop':/\w+\.(left|right|next|val)\s*=/.test(code)?'modify':'statement';
  steps.push({line:e.line,kind,phase:e.kind,method:e.method,frames:intern(e.frames),heap:intern(e.heap),roots:intern(e.roots),active,returnValue:e.returnValue===undefined?-1:intern(e.returnValue)});
 }
 if(calls.some(c=>c.returned===undefined))throw Error('调用进入返回不平衡：'+p.id);
 const last=raw.at(-1),ret=last.returnValue;
 const expand=(x,heap)=>x?.$ref?heap[x.$ref]:x;
 const levelOrder=(root,heap)=>{if(!root)return [];const q=[root],result=[];for(let i=0;i<q.length;i++){const n=expand(q[i],heap);result.push(n?.val??null);if(n){q.push(n.left??null,n.right??null);}}while(result.at(-1)===null)result.pop();return result;};
 const print=x=>Array.isArray(x)?'['+x.map(print).join(', ')+']':String(x);
 let final=ret;
 if(ret?.$ref){
  if([116,117].includes(p.id)){
   final=[];let level=[ret];while(level.length){final.push(level.map(x=>expand(x,last.heap).val));for(let i=0;i<level.length;i++){if(JSON.stringify(expand(level[i],last.heap).next)!==JSON.stringify(level[i+1]??null))throw Error('next 快照错误：'+p.id);}level=level.flatMap(x=>{const n=expand(x,last.heap);return [n.left,n.right].filter(Boolean);});}
  } else final=levelOrder(ret,last.heap);
 }
 const matches=p.id===637?JSON.stringify(final)===JSON.stringify(JSON.parse(actual)):print(final)===actual;
 if(!matches)throw Error('最终 JDI 快照与 Java 输出不一致：'+p.id+' '+print(final)+' / '+actual);
 const v=p.variants[0],local=v.source_java.slice(v.source_java.indexOf('class Solution')).replace(/^class Solution/,'static class Solution').split('\n').map(l=>'    '+l).join('\n');
 const displayStart=source.slice(0,source.indexOf(local)).split('\n').length;
 const data={...p,variants:undefined,chapter:'二叉树',source:meta.source_url,revision:meta.revision_id,sourceStatus:'递归与其他解法均逐字核对原文类体；只作内部类命名、static 与外层适配。',javaFile:p.java_file,testSource:p.test_source,code:local.split('\n'),displayStart,expected,actual,sourceHash:crypto.createHash('sha256').update(source).digest('hex'),pool,steps,calls,validation:{java:'PASS',trace:'PASS',enter:calls.length,exit:calls.length,variants:p.variants.length}};
 const dir='docs/problems/'+p.slug;fs.mkdirSync(dir,{recursive:true});
 fs.writeFileSync(dir+'/trace.js','window.TREE_TRACE = '+stringify(data)+';\n');
 fs.writeFileSync(dir+'/index.html','<!doctype html>\n<html lang="zh-CN"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>'+p.id+' · '+p.title+' — 逐行</title><link rel="stylesheet" href="../../assets/player.css"><link rel="stylesheet" href="../../assets/tree-player.css"></head><body><div id="app"></div><script src="trace.js"></script><script src="../../assets/tree-player.js"></script></body></html>\n');
 catalog.push({id:p.id,title:p.title,english:p.english,chapter:'二叉树',input:p.input,source:meta.source_url,url:'problems/'+p.slug+'/',complete:true});
 reports.push({id:p.id,title:p.title,steps:steps.length,calls:calls.length,bytes:fs.statSync(dir+'/trace.js').size,...data.validation});
}
fs.writeFileSync('docs/assets/catalog.js','window.CATALOG = '+stringify(catalog)+';\n');
fs.writeFileSync('.work/binarytree/logs/build-report.json',JSON.stringify(reports,null,2)+'\n');
console.log('38 个递归调试器：源码、进入/返回、最终快照全部 PASS。');
