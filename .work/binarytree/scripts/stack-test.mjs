import fs from 'node:fs';
import vm from 'node:vm';
import path from 'node:path';
import assert from 'node:assert/strict';
import {createRequire} from 'node:module';
const require=createRequire(import.meta.url),{chromium}=require(path.join(process.env.CODEX_TEST_MODULES,'playwright'));
const browser=await chromium.launch({executablePath:'C:/Program Files/Google/Chrome/Application/chrome.exe',headless:true});
const page=await browser.newPage({viewport:{width:1440,height:1000}}),errors=[],report=[];
page.on('pageerror',e=>errors.push(e.message));
const context={window:{}};vm.runInNewContext(fs.readFileSync('docs/assets/catalog.js','utf8'),context);
const base=process.env.PREVIEW_URL||'http://127.0.0.1:8787';
for(const p of context.window.CATALOG){
 await page.goto(base+'/'+p.url);await page.locator('#progress').waitFor();
 const trace=await page.evaluate(()=>window.TREE_TRACE||window.TRACE),tree=p.chapter==='二叉树';
 const framesAt=s=>tree?trace.pool[s.frames]:trace.pool[s.stack].map(ref=>({...trace.pool[ref],method:trace.pool[trace.pool[ref].method]}));
 const voidMethods=new Set([...trace.code.join('\n').matchAll(/\bvoid\s+(\w+)\s*\(/g)].map(m=>m[1]));
 const events=trace.steps.map((s,i)=>s.phase==='enter'||s.phase==='exit'?i:-1).filter(i=>i>=0);
 const samples=[...new Set([0,...events.slice(0,10),...events.slice(-8),trace.steps.length-1])];
 async function seek(i){await page.locator('#progress').evaluate((el,i)=>{el.value=i;el.dispatchEvent(new Event('input',{bubbles:true}));},i);}
 for(const i of samples){
  await seek(i);const step=trace.steps[i],frames=framesAt(step),actual=await page.locator('#stack [data-frame]').evaluateAll(nodes=>nodes.map(n=>Number(n.dataset.frame)));
  assert.deepEqual(actual,frames.slice().reverse().map(f=>f.id),p.id+' 栈帧顺序');
  assert.equal(await page.locator('#stack [data-top="true"]').count(),1);
  assert.equal(await page.locator('#stack .stack-base').textContent(),'栈底 '+frames.length+' 层');
  assert.equal(await page.locator('#stack .stack-cap').textContent(),'栈顶'+(step.phase==='exit'?'↑ 出栈':step.phase==='enter'?'↓ 入栈':''));
  const returnValue=tree?(step.returnValue<0?undefined:trace.pool[step.returnValue]):step.returnValue;
  assert.equal(await page.locator('#stack .stack-return').count(),step.phase==='exit'&&returnValue!==undefined&&!voidMethods.has(step.method)?1:0,p.id+' 返回值 / void');
  if(frames.length>1){await page.locator('#stack .stack-frame').last().click();assert.equal(await page.locator('#stack .stack-frame[aria-pressed="true"]').getAttribute('data-frame'),String(frames[0].id));assert.equal(await page.locator('.code-line.current').getAttribute('data-line'),String(frames[0].line));}
 }
 const pop=trace.steps.findIndex((s,i)=>s.phase==='exit'&&trace.steps[i+1]&&framesAt(trace.steps[i+1]).length<framesAt(s).length);
 if(pop>=0){await seek(pop);const count=await page.locator('#stack .stack-frame').count();await page.locator('#next').click();assert.equal(await page.locator('#stack .stack-frame').count(),count-1);await page.locator('#prev').click();assert.equal(await page.locator('#stack .stack-frame').count(),count);}
 assert.equal(await page.locator('#explanation').textContent().then(s=>s.length<80),true);
 if([104,77].includes(p.id)){
  const deepest=trace.steps.reduce((best,s,i)=>framesAt(s).length>framesAt(trace.steps[best]).length?i:best,0);await seek(deepest);
  await page.screenshot({path:'.work/binarytree/previews/stack-'+p.id+'-desktop.png',fullPage:true});
  await page.setViewportSize({width:390,height:844});assert.equal(await page.evaluate(()=>document.documentElement.scrollWidth>innerWidth),false);await page.screenshot({path:'.work/binarytree/previews/stack-'+p.id+'-mobile.png',fullPage:true});await page.setViewportSize({width:1440,height:1000});
 }
 report.push({id:p.id,chapter:p.chapter,stackSnapshots:samples.length,status:'PASS'});
}
assert.deepEqual(errors,[]);await browser.close();fs.writeFileSync('.work/binarytree/logs/stack-report.json',JSON.stringify({pages:report,errors},null,2)+'\n');console.log('53 页栈视图：帧身份与顺序、入栈/出栈、返回值、栈帧切换及回退全部 PASS。');
