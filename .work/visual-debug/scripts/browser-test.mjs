import fs from 'node:fs';
import path from 'node:path';
import assert from 'node:assert/strict';
import {createRequire} from 'node:module';
const require=createRequire(import.meta.url);
const moduleRoot=process.env.CODEX_TEST_MODULES;
const {chromium}=moduleRoot?require(path.join(moduleRoot,'playwright')):require('playwright');
const browser=await chromium.launch({executablePath:process.env.CODEX_TEST_CHROME||'C:/Program Files/Google/Chrome/Application/chrome.exe',headless:true});
const page=await browser.newPage({viewport:{width:1440,height:1100}});
const errors=[];
page.on('pageerror',e=>errors.push(e.message));
const base=process.env.PREVIEW_URL||'http://127.0.0.1:8787';
const ids=process.argv.slice(2).map(Number);
const ctx={window:{}};await import('node:vm').then(vm=>vm.runInNewContext(fs.readFileSync('docs/assets/catalog.js','utf8'),ctx));
const catalog=ctx.window.CATALOG.filter(p=>p.complete&&(!ids.length||ids.includes(p.id)));
const results=[];
for(const p of catalog){
 await page.goto(base+'/'+p.url);
 await page.locator('#progress').waitFor();
 const trace=await page.evaluate(()=>window.TRACE);
 const vals=ref=>Object.fromEntries(trace.pool[ref].map(([k,v])=>[trace.pool[k],trace.pool[v]]));
 async function seek(index){await page.locator('#progress').evaluate((el,i)=>{el.value=i;el.dispatchEvent(new Event('input',{bubbles:true}));},index);}
 async function snapshot(){return page.evaluate(()=>({line:document.querySelector('.code-line.current')?.dataset.line,vars:document.getElementById('variables').textContent,stack:document.getElementById('stack').textContent,result:document.getElementById('result').textContent,nodes:document.getElementById('node-count').textContent}));}
 assert.match(await page.locator('#progress-label').textContent(),/^1 \/ /);
 await page.getByRole('button',{name:'下一步 →',exact:true}).click();assert.equal((await snapshot()).line,String(trace.steps[1].line));
 await page.getByRole('button',{name:'← 上一步',exact:true}).click();assert.equal((await snapshot()).line,String(trace.steps[0].line));
 const samples=[0,Math.floor(trace.steps.length/2),trace.steps.length-1,...trace.steps.map((s,i)=>['prune','save','undo'].includes(s.kind)?i:-1).filter(x=>x>=0).slice(0,8)];
 for(const i of samples){
   await seek(i);const s1=await snapshot();assert.equal(s1.line,String(trace.steps[i].line));
   if(i>0){await seek(i-1);await seek(i);assert.deepEqual(await snapshot(),s1);}
   const result=vals(trace.steps[i].vars)[p.id===37?'board':'result'];
   if(result!==undefined)assert.ok(s1.result.replace(/\s/g,'').includes(JSON.stringify(result).replace(/\s/g,'')));
 }
 await seek(0);await page.getByRole('button',{name:'关键操作 ⇥',exact:true}).click();assert.ok(Number((await page.locator('#progress').inputValue()))>0);
 await page.locator('#speed').selectOption('140');await page.getByRole('button',{name:'▶ 播放',exact:true}).click();
 await page.waitForFunction(()=>Number(document.getElementById('progress').value)>4);
 await page.getByRole('button',{name:'Ⅱ 暂停',exact:true}).click();const stopped=await page.locator('#progress').inputValue();
 await page.waitForTimeout(220);assert.equal(await page.locator('#progress').inputValue(),stopped);
 await page.getByRole('button',{name:'↺ 重置',exact:true}).click();assert.equal(await page.locator('#progress').inputValue(),'0');
 if(p.id===131){
   const paths=trace.steps.map(s=>vals(s.vars).path).filter(x=>x!==undefined);
   assert.ok(paths.some(x=>JSON.stringify(x)==='["a","a","b"]'));assert.ok(paths.some(x=>JSON.stringify(x)==='["aa","b"]'));
   assert.deepEqual(trace.finalReturn,[['a','a','b'],['aa','b']]);
   assert.deepEqual(trace.nodes.filter(n=>n.kind==='prune').map(n=>n.label),['ab · 非回文','aab · 非回文']);
   await seek(trace.steps.findIndex(s=>s.kind==='prune'));
   await page.screenshot({path:'.work/visual-debug/previews/131-desktop.png',fullPage:true});
   await page.setViewportSize({width:390,height:844});
   await page.screenshot({path:'.work/visual-debug/previews/131-mobile.png',fullPage:true});
   assert.equal(await page.evaluate(()=>document.documentElement.scrollWidth>innerWidth),false);
   await page.setViewportSize({width:1440,height:1100});
 }
 results.push({id:p.id,steps:trace.steps.length,status:'PASS'});
}
assert.deepEqual(errors,[]);
await browser.close();
fs.writeFileSync('.work/visual-debug/logs/browser-report.json',JSON.stringify(results,null,2));
console.log(JSON.stringify({pages:results,errors},null,2));
