import fs from 'node:fs';
import path from 'node:path';
import vm from 'node:vm';
import assert from 'node:assert/strict';
import {createRequire} from 'node:module';
const require=createRequire(import.meta.url);
const {chromium}=require(path.join(process.env.CODEX_TEST_MODULES,'playwright'));
const browser=await chromium.launch({executablePath:'C:/Program Files/Google/Chrome/Application/chrome.exe',headless:true});
const page=await browser.newPage({viewport:{width:1440,height:1100}}),errors=[],report=[];
page.on('pageerror',e=>errors.push(e.message));
await page.addInitScript(()=>{
 window.__segments=[];let start=null;const proto=CanvasRenderingContext2D.prototype;
 const move=proto.moveTo,line=proto.lineTo,clear=proto.clearRect;
 proto.clearRect=function(...a){window.__segments=[];return clear.apply(this,a);};
 proto.moveTo=function(x,y){start={x,y};return move.call(this,x,y);};
 proto.lineTo=function(x,y){if(start&&this.getLineDash()[0]!==5)window.__segments.push([start.y,y]);start={x,y};return line.call(this,x,y);};
});
const base=process.env.PREVIEW_URL||'http://127.0.0.1:8787';
const context={window:{}};vm.runInNewContext(fs.readFileSync('docs/assets/catalog.js','utf8'),context);
await page.goto(base+'/?chapter='+encodeURIComponent('二叉树'));
assert.equal(await page.locator('.card').count(),38);
assert.equal(await page.locator('#problem-count').textContent(),'53');
await page.getByRole('button',{name:'回溯算法',exact:true}).click();assert.equal(await page.locator('.card').count(),15);
for(const p of context.window.CATALOG.filter(p=>p.chapter==='二叉树')){
 await page.goto(base+'/'+p.url);await page.locator('#progress').waitFor();
 const trace=await page.evaluate(()=>window.TREE_TRACE);
 assert.ok(trace.calls.every(c=>c.returned!==undefined));
 async function seek(i){await page.locator('#progress').evaluate((el,i)=>{el.value=i;el.dispatchEvent(new Event('input',{bubbles:true}));},i);}
 async function snapshot(){return page.evaluate(()=>({line:document.querySelector('.code-line.current')?.dataset.line,vars:document.getElementById('variables').textContent,stack:document.getElementById('stack').textContent,result:document.getElementById('result').textContent,nodes:document.getElementById('node-count').textContent}));}
 const first=await snapshot();await page.locator('#next').click();assert.equal((await snapshot()).line,String(trace.steps[1].line));await page.locator('#prev').click();assert.deepEqual(await snapshot(),first);
 for(const i of [0,Math.floor(trace.steps.length/2),trace.steps.length-1]){
  await seek(i);const current=await snapshot();assert.equal(current.line,String(trace.steps[i].line));
  if(i>0){await seek(i-1);await seek(i);assert.deepEqual(await snapshot(),current);}
  assert.ok(await page.evaluate(()=>window.__segments.every(([parent,child])=>child>=parent)),'实际树连线必须从上到下');
  await page.locator('#calls-tab').click();assert.ok(await page.evaluate(()=>window.__segments.every(([parent,child])=>child>parent)),'调用树连线必须从上到下');
  await page.locator('#structure-tab').click();
 }
 assert.match(await page.locator('#result').textContent(),/PASS/);
 const stacked=trace.steps.findIndex(s=>trace.pool[s.frames].length>1);if(stacked>=0){await seek(stacked);await page.locator('.stack-frame').last().click();const root=trace.pool[trace.steps[stacked].frames][0];assert.equal((await snapshot()).line,String(root.line));}
 await page.locator('#reset').click();await page.locator('#key').click();assert.ok(Number(await page.locator('#progress').inputValue())>0);
 if([104,106,116,669].includes(p.id)){
  await seek(Math.floor(trace.steps.length/2));await page.screenshot({path:'.work/binarytree/previews/'+p.id+'-desktop.png',fullPage:true});
  await page.setViewportSize({width:390,height:844});assert.equal(await page.evaluate(()=>document.documentElement.scrollWidth>innerWidth),false);
  await page.screenshot({path:'.work/binarytree/previews/'+p.id+'-mobile.png',fullPage:true});await page.setViewportSize({width:1440,height:1100});
 }
 report.push({id:p.id,status:'PASS',steps:trace.steps.length});
}
await page.locator('#reset').click();await page.locator('#speed').selectOption('140');await page.locator('#play').click();await page.waitForFunction(()=>Number(document.getElementById('progress').value)>=3);await page.locator('#play').click();
const stopped=await page.locator('#progress').inputValue();await page.waitForTimeout(220);assert.equal(await page.locator('#progress').inputValue(),stopped);
assert.deepEqual(errors,[]);await browser.close();
fs.writeFileSync('.work/binarytree/logs/browser-report.json',JSON.stringify({pages:report,errors},null,2)+'\n');console.log('38 页：单步、回退、进度、栈帧、两种纵向树视图、结果 PASS；移动端无横向溢出。');
