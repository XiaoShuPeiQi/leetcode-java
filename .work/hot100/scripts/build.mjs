import fs from 'node:fs';
import vm from 'node:vm';
import path from 'node:path';
import crypto from 'node:crypto';
import assert from 'node:assert/strict';
import {fileURLToPath} from 'node:url';
const B=path.dirname(fileURLToPath(import.meta.url));
const R=path.resolve(B,'../../..');
process.chdir(R);
const meta=JSON.parse(fs.readFileSync('imports/Hot100.import.json','utf8'));
const context={window:{}};vm.runInNewContext(fs.readFileSync('docs/assets/catalog.js','utf8'),context);
const original=context.window.CATALOG.filter(p=>p.chapter!=='Hot100'),catalog=[...original],reports=[];
function canonical(value,unordered){
 if(!Array.isArray(value))return JSON.stringify(value);
 const parts=value.map(x=>canonical(x,unordered));if(unordered)parts.sort();return '['+parts.join(',')+']';
}
for(const p of meta.problems){
 const source=fs.readFileSync(p.javaFile,'utf8');assert.equal(crypto.createHash('sha256').update(source).digest('hex'),p.sourceHash);assert(source.includes(p.adapted));
 const raw=fs.readFileSync('.work/hot100/tmp/trace-'+p.id+'.ndjson','utf8').trim().split('\n').map(JSON.parse);
 const log=fs.readFileSync('.work/hot100/tmp/trace-'+p.id+'.ndjson.stdout','utf8').replace(/\r/g,'');assert(log.includes('Result: PASS'));
 const final=raw.at(-1);let actual=final.returnValue;
 if([283,189].includes(p.id))actual=final.frames.at(-1).vars.nums;
 if([73,48].includes(p.id))actual=final.frames.at(-1).vars.matrix;
 assert.equal(canonical(actual,[15,49].includes(p.id)),canonical(p.expectedValue,[15,49].includes(p.id)),p.id+' final JDI state');
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
  assert(e.line>=p.displayStart&&e.line<p.displayStart+p.adapted.split('\n').length,p.id+' source mapping');
  const kind=e.kind==='enter'?'enter':e.kind==='exit'?'return':/\breturn\b/.test(line)?'return':/\b(if|while)\b/.test(line)?'condition':/\bfor\b/.test(line)?'loop':/\+\+|--|\+=|-=|\]\s*=|\.put\(|\.remove\(|\.add\(/.test(line)?'modify':'statement';
  steps.push({line:e.line,kind,phase:e.kind,method:e.method,frames:intern(e.frames),heap:intern(e.heap),roots:intern(e.roots),active,returnValue:e.returnValue===undefined?-1:intern(e.returnValue)});
 }
 assert(calls.every(c=>c.returned!==undefined));
 const expected=log.match(/Expected:\s*([\s\S]*?)\nActual:/)[1].trim(),printed=log.match(/Actual:\s*([\s\S]*?)\nResult:/)[1].trim();assert.equal(printed,expected);
 const data={id:p.id,title:p.title,english:p.english,chapter:'Hot100',order:p.order,group:p.group,slug:p.page_slug,input:p.input,inputObject:p.example,source:meta.target_url,javaFile:p.javaFile,testSource:p.test_source,sourceStatus:p.provenance+' 真实JDI回放对应的Solution类体，算法中未加入调试输出。',sourceHash:p.sourceHash,code:p.adapted.split('\n'),displayStart:p.displayStart,expected,actual:printed,pool,steps,calls,stateMeaning:p.state,transition:p.summary,validation:{java:'PASS',trace:'PASS',enter:calls.length,exit:calls.length}};
 const dir='docs/problems/'+p.page_slug;fs.mkdirSync(dir,{recursive:true});
 fs.writeFileSync(dir+'/trace.js','window.HOT100_TRACE = '+JSON.stringify(data).replace(/</g,'\\u003c')+';\n');
 fs.writeFileSync(dir+'/index.html','<!doctype html>\n<html lang="zh-CN"><head><script src="../../assets/theme.js"></script><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>'+p.id+' · '+p.title+' — 展老师小课堂</title><link rel="stylesheet" href="../../assets/player.css"><link rel="stylesheet" href="../../assets/stack-view.css"><link rel="stylesheet" href="../../assets/greedy-player.css"><link rel="stylesheet" href="../../assets/array-player.css"><link rel="stylesheet" href="../../assets/hot100-player.css"><link rel="stylesheet" href="../../assets/theme.css"><link rel="stylesheet" href="../../assets/code-style.css"><link rel="stylesheet" href="../../assets/panel-resize.css"></head><body><div id="app"></div><script src="trace.js"></script><script src="../../assets/execution-steps.js"></script><script src="../../assets/stack-view.js"></script><script src="../../assets/hot100-player.js"></script><script src="../../assets/code-style.js"></script><script src="../../assets/panel-resize.js"></script></body></html>\n');
 catalog.push({id:p.id,title:p.title,english:p.english,chapter:'Hot100',order:p.order,input:p.input,source:meta.target_url,url:'problems/'+p.page_slug+'/',complete:true});
 reports.push({id:p.id,title:p.title,steps:steps.length,calls:calls.length,bytes:fs.statSync(dir+'/trace.js').size,...data.validation});
}
assert.equal(catalog.length,original.length+20);assert.equal(new Set(catalog.map(p=>p.url)).size,catalog.length);
fs.writeFileSync('docs/assets/catalog.js','window.CATALOG = '+JSON.stringify(catalog).replace(/</g,'\\u003c')+';\n');
let home=fs.readFileSync('docs/index.html','utf8');
if(!home.includes('data-chapter="Hot100"'))home=home.replace('<button data-chapter="数组">','<button data-chapter="Hot100">Hot100 · 前20题</button><button data-chapter="数组">');
home=home.replace(/id="problem-count">\d+/,`id="problem-count">${catalog.length}`).replace(/id="all-count">\d+/,`id="all-count">${catalog.length}`).replace(/id="chapter-count">\d+/,`id="chapter-count">${String(new Set(catalog.map(p=>p.chapter)).size).padStart(2,'0')}`);
fs.writeFileSync('docs/index.html',home);
let player=fs.readFileSync('docs/assets/array-player.js','utf8').replaceAll('window.ARRAY_TRACE','window.HOT100_TRACE').replaceAll("encodeURIComponent('数组')","encodeURIComponent('Hot100')").replace('ARRAY / ${String(data.id).padStart(4,\'0\')} · 数组','HOT100 / ${String(data.order).padStart(2,\'0\')} / 20 · ${esc(data.group)} · ${String(data.id).padStart(4,\'0\')}');
player=player.replace("let keys=['left','right','mid','slow','fast','write','i','j','start','end'];","let keys=['left','right','mid','slow','fast','write','i','j','start','end','target'];\n if(data.id===238)keys=['i'];");
player=player.replace("const chars=[...new Set(Array.from(data.inputObject.t||''))];","const chars=[...new Set(Array.from(data.inputObject.t||data.inputObject.p||''))];");
player=player.replace("value[c.charCodeAt(0)]","value[c.charCodeAt(0)-(data.id===438?97:0)]");
player=player.replace("name==='need'&&Array.isArray(value)","(name==='need'||(data.id===438&&name==='window'))&&Array.isArray(value)");
player=player.replace('need · 目标字符欠账（负数表示多余）',"'+esc(data.id===438?name+' · 字母频次':'need · 目标字符欠账（负数表示多余）')+'");
player=player.replace(' · 窗口内频次</div>',' · 键 → 值</div>');
player=player.replace("esc(k)+'<small>次数 '+esc(v)","esc(k)+'<small>→ '+esc(JSON.stringify(v))");
player=player.replace("function drawState(step,vars){",`function heightChart(values,vars){
 const max=Math.max(1,...values),width=Math.max(350,values.length*38+20);
 return '<svg class="height-chart" viewBox="0 0 '+width+' 185" role="img" aria-label="柱子高度与左右指针">'+values.map((h,i)=>{
  const x=15+i*38,y=140-h/max*105,active=i===vars.left||i===vars.right;
  return '<rect x="'+x+'" y="'+y+'" width="25" height="'+(140-y)+'" fill="'+(active?'var(--accent)':'var(--muted)')+'" opacity=".75"/><text x="'+(x+12)+'" y="'+(y-6)+'" text-anchor="middle">'+h+'</text><text x="'+(x+12)+'" y="158" text-anchor="middle">'+i+'</text>'+(active?'<text x="'+(x+12)+'" y="178" text-anchor="middle">'+(i===vars.left?'left':'right')+'</text>':'');
 }).join('')+'</svg>';
}
function drawState(step,vars){`);
player=player.replace("function view(name,value,original=false){","if([11,42].includes(data.id))html+=heightChart(data.inputObject.height,vars);\n function view(name,value,original=false){");
player=player.replace("let html='<p class=\"state-caption array-meaning\">'", "let html='<p class=\"state-caption array-meaning\">'");
fs.writeFileSync('docs/assets/hot100-player.js',player);
fs.writeFileSync('docs/assets/hot100-player.css','.height-chart{display:block;width:100%;max-height:250px;margin:12px 0 20px;font:11px "Microsoft YaHei",sans-serif;fill:var(--ink)}.state-cell small{overflow-wrap:anywhere;max-width:140px}.state-array{row-gap:30px}\n');
let homeCss=fs.readFileSync('docs/assets/home.css','utf8');
if(!homeCss.includes('.filter-tabs{flex-wrap:wrap}'))fs.appendFileSync('docs/assets/home.css','\n/* More chapter tabs wrap within the available width. */\n.filter-tabs{flex-wrap:wrap}\n');
fs.writeFileSync('outputs/Hot100-调试器验收.json',JSON.stringify({source:meta.target_url,problems:reports,totalEvents:reports.reduce((n,p)=>n+p.steps,0)},null,2)+'\n');
console.log('20 Java/JDI pages built; '+catalog.length+' catalog entries.');
