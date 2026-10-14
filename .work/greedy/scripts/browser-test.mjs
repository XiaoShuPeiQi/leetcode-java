import fs from 'node:fs';
import vm from 'node:vm';
import assert from 'node:assert/strict';
import {createRequire} from 'node:module';
import path from 'node:path';
const require=createRequire(import.meta.url),{chromium}=require(path.join(process.env.CODEX_TEST_MODULES,'playwright'));
const base=process.env.PREVIEW_URL||'http://127.0.0.1:8787';
const browser=await chromium.launch({channel:'msedge',headless:true});
const page=await browser.newPage({viewport:{width:1440,height:1100}}),errors=[],report=[];
page.on('pageerror',e=>errors.push(e.message));
await page.goto(base+'/?chapter='+encodeURIComponent('贪心'));
assert.equal(await page.locator('.card').count(),17);
assert.equal(await page.locator('#problem-count').textContent(),'70');
for(const [chapter,n] of [['回溯算法',15],['二叉树',38],['贪心算法',17]]){await page.getByRole('button',{name:chapter,exact:true}).click();assert.equal(await page.locator('.card').count(),n);}
const catalog={window:{}};vm.runInNewContext(fs.readFileSync('docs/assets/catalog.js','utf8'),catalog);
for(const p of catalog.window.CATALOG.filter(p=>p.chapter==='贪心')){
 await page.goto(base+'/'+p.url);await page.locator('.code-line.current').waitFor();
 const state=await page.evaluate(()=>({vars:document.getElementById('variables').innerHTML,stack:document.getElementById('stack').innerHTML,state:document.getElementById('state').innerHTML}));
 await page.locator('#next').click();await page.locator('#prev').click();
 assert.deepEqual(await page.evaluate(()=>({vars:document.getElementById('variables').innerHTML,stack:document.getElementById('stack').innerHTML,state:document.getElementById('state').innerHTML})),state,'回退必须恢复真实变量和栈');
 const stops=await page.evaluate(()=>window.ExecutionSteps.create(window.GREEDY_TRACE).stops);
 // 每一个可执行暂停位置都必须有高亮、变量与匹配的真实帧行号。
 for(let i=0;i<stops.length;i++){
  await page.locator('#progress').evaluate((e,i)=>{e.value=i;e.dispatchEvent(new Event('input',{bubbles:true}));},i);
  assert.equal(await page.locator('.code-line.current').count(),1,p.id+' missing code highlight');
  const got=await page.evaluate(()=>({line:Number(document.querySelector('.code-line.current').dataset.line),label:document.getElementById('frame-title').textContent}));
  const expected=await page.evaluate(raw=>{const d=window.GREEDY_TRACE,s=d.steps[raw],f=d.pool[s.frames].at(-1);return {line:f.line,label:f.method+'()'};},stops[i]);
  assert.deepEqual(got,expected,'高亮与JDI栈顶一致');
 }
 assert((await page.locator('#result').textContent()).includes('PASS'));
 assert.equal(await page.locator('#result pre').textContent(),await page.evaluate(()=>window.GREEDY_TRACE.actual));
 await page.locator('#reset').click();await page.keyboard.press('ArrowRight');assert.equal(await page.locator('#progress').inputValue(),'1');
 report.push({id:p.id,stops:stops.length,status:'PASS'});
}
await page.goto(base+'/problems/0045-jump-game-ii/');
const breakpoint=await page.evaluate(()=>{const d=window.GREEDY_TRACE,s=window.ExecutionSteps.create(d).stops;return d.steps[s[1]].line;});
await page.locator(`[data-line="${breakpoint}"]`).click();await page.locator('#speed').selectOption('140');await page.locator('#play').click();await page.waitForFunction(()=>document.getElementById('explanation').textContent==='命中播放断点');
assert.equal(await page.locator('#play').textContent(),'▶ 播放');
await page.locator('.theme-toggle').click();assert.equal(await page.locator('html').getAttribute('data-theme'),'dark');
await page.screenshot({path:'.work/greedy/tmp/jump-desktop-dark.png',fullPage:true});
await page.locator('.theme-toggle').click();await page.screenshot({path:'.work/greedy/tmp/jump-desktop-light.png',fullPage:true});
for(const p of catalog.window.CATALOG.filter(p=>p.chapter==='贪心')){
 await page.setViewportSize({width:390,height:900});await page.goto(base+'/'+p.url);
 const widths=await page.evaluate(()=>({view:innerWidth,scroll:document.documentElement.scrollWidth}));assert(widths.scroll<=widths.view+1,p.id+' 移动端横向溢出');
}
await page.screenshot({path:'.work/greedy/tmp/camera-mobile.png',fullPage:true});
// 未修改的两章仍能加载、前进、抵达最终PASS。
await page.setViewportSize({width:1440,height:1100});
for(const url of ['problems/0077-combinations/','problems/0104-maximum-depth-of-binary-tree/']){
 await page.goto(base+'/'+url);await page.locator('#next').click();
 await page.locator('#progress').evaluate(e=>{e.value=e.max;e.dispatchEvent(new Event('input',{bubbles:true}));});
 assert((await page.locator('#result').textContent()).includes('PASS'));
}
assert.deepEqual(errors,[]);await browser.close();
fs.writeFileSync('outputs/贪心算法-浏览器验收.json',JSON.stringify({pages:report,mobilePages:17,breakpoint:'PASS',themes:'PASS',existingChapters:'PASS',errors},null,2)+'\n');
console.log('17页所有暂停位置、回退、进度、真实栈帧、最终输出、移动端、断点、主题全部PASS；原回溯/二叉树仍PASS。');
