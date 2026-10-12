import fs from 'node:fs';
import path from 'node:path';
import assert from 'node:assert/strict';
import {createRequire} from 'node:module';
const require=createRequire(import.meta.url);
const moduleRoot=process.env.CODEX_TEST_MODULES;
const {chromium}=moduleRoot?require(path.join(moduleRoot,'playwright')):require('playwright');
const browser=await chromium.launch({executablePath:process.env.CODEX_TEST_CHROME||'C:/Program Files/Google/Chrome/Application/chrome.exe',headless:true});
const page=await browser.newPage({viewport:{width:1440,height:1100}});
await page.addInitScript(()=>{
 window.__treeEdges=[];window.__treeNodes=[];
 const prototype=CanvasRenderingContext2D.prototype;
 for(const name of ['clearRect','moveTo','bezierCurveTo','roundRect','fillText']){
  const original=prototype[name];
  prototype[name]=function(...args){
   if(this.canvas.id==='tree'){
    if(name==='clearRect'){window.__treeEdges=[];window.__treeNodes=[];}
    if(name==='moveTo')this.lastTreeStart=args;
    if(name==='bezierCurveTo')window.__treeEdges.push({from:this.lastTreeStart,to:args.slice(-2)});
    if(name==='roundRect')window.__treeNodes.push({x:args[0],y:args[1],width:args[2],height:args[3]});
    if(name==='fillText'&&window.__treeNodes.length&&!window.__treeNodes.at(-1).label)window.__treeNodes.at(-1).label=args[0];
   }
   return original.apply(this,args);
  };
 }
});
const errors=[];
page.on('pageerror',e=>errors.push(e.message));
const base=process.env.PREVIEW_URL||'http://127.0.0.1:8787';
const ids=process.argv.slice(2).map(Number);
const ctx={window:{}};await import('node:vm').then(vm=>vm.runInNewContext(fs.readFileSync('docs/assets/catalog.js','utf8'),ctx));
const catalog=ctx.window.CATALOG.filter(p=>p.chapter==='回溯'&&p.complete&&(!ids.length||ids.includes(p.id)));
const results=[];
for(const p of catalog){
 await page.goto(base+'/'+p.url);
 await page.locator('#progress').waitFor();
 assert.match(await page.title(),/展老师小课堂/);
 const initialTheme=await page.locator('html').getAttribute('data-theme'),initialColor=await page.locator('body').evaluate(el=>getComputedStyle(el).backgroundColor);
 await page.locator('.theme-toggle').click();assert.notEqual(await page.locator('html').getAttribute('data-theme'),initialTheme);
 assert.notEqual(await page.locator('body').evaluate(el=>getComputedStyle(el).backgroundColor),initialColor);
 assert.equal(await page.locator('#progress').inputValue(),'0');await page.locator('.theme-toggle').click();
 const trace=await page.evaluate(()=>window.TRACE);
 const playback=await page.evaluate(()=>window.ExecutionSteps.create(window.TREE_TRACE||window.TRACE)),steps=playback.stops.map(i=>trace.steps[i]);
 const vals=ref=>Object.fromEntries(trace.pool[ref].map(([k,v])=>[trace.pool[k],trace.pool[v]]));
 async function seek(index){await page.locator('#progress').evaluate((el,i)=>{el.value=i;el.dispatchEvent(new Event('input',{bubbles:true}));},index);}
 async function snapshot(){return page.evaluate(()=>({line:document.querySelector('.code-line.current')?.dataset.line,vars:document.getElementById('variables').textContent,stack:document.getElementById('stack').textContent,result:document.getElementById('result').textContent,nodes:document.getElementById('node-count').textContent}));}
 assert.match(await page.locator('#progress-label').textContent(),/^1 \/ /);
 assert.equal(await page.locator('#variables details[open]').count(),0,p.id+' 变量详情默认收起');
 assert.equal(await page.locator('.verification[open]').count(),0,p.id+' 校验默认收起');
 assert.equal(await page.locator('.workspace .stack-panel').count(),1,p.id+' 栈位于主工作区');
 assert.equal(await page.locator('#zoom').inputValue(),'fit',p.id+' 默认自适应');
 await page.getByRole('button',{name:'下一步 →',exact:true}).click();assert.equal((await snapshot()).line,String(steps[1].line));
 await page.getByRole('button',{name:'← 上一步',exact:true}).click();assert.equal((await snapshot()).line,String(steps[0].line));
 const samples=[0,Math.floor(steps.length/2),steps.length-1,...steps.map((s,i)=>['prune','save','undo'].includes(s.kind)?i:-1).filter(x=>x>=0).slice(0,8)];
 for(const i of samples){
   await seek(i);const s1=await snapshot();assert.equal(s1.line,String(steps[i].line));
   if(i>0){await seek(i-1);await seek(i);assert.deepEqual(await snapshot(),s1);}
   const result=vals(steps[i].vars)[p.id===37?'board':'result'];
   if(result!==undefined)assert.ok(s1.result.replace(/\s/g,'').includes(JSON.stringify(result).replace(/\s/g,'')));
 }
 await seek(0);await page.getByRole('button',{name:'关键操作 ⇥',exact:true}).click();assert.ok(Number((await page.locator('#progress').inputValue()))>0);
 await page.locator('#speed').selectOption('140');await page.getByRole('button',{name:'▶ 播放',exact:true}).click();
 await page.waitForFunction(()=>Number(document.getElementById('progress').value)>4);
 await page.getByRole('button',{name:'Ⅱ 暂停',exact:true}).click();const stopped=await page.locator('#progress').inputValue();
 await page.waitForTimeout(220);assert.equal(await page.locator('#progress').inputValue(),stopped);
 await page.getByRole('button',{name:'↺ 重置',exact:true}).click();assert.equal(await page.locator('#progress').inputValue(),'0');
 if(p.id===131){
   const paths=steps.map(s=>vals(s.vars).path).filter(x=>x!==undefined);
   assert.ok(paths.some(x=>JSON.stringify(x)==='["a","a","b"]'));assert.ok(paths.some(x=>JSON.stringify(x)==='["aa","b"]'));
   assert.deepEqual(trace.finalReturn,[['a','a','b'],['aa','b']]);
   assert.equal(trace.nodes.filter(n=>n.kind==='prune').length,2);
   const pathDetail=page.locator('#variables details[data-variable="path"]');
   await seek(steps.findIndex(s=>Array.isArray(vals(s.vars).path)));await pathDetail.locator('summary').click();
   await page.locator('#next').click();assert.equal(await pathDetail.getAttribute('open'),'','展开后单步保持展开');
   await pathDetail.locator('summary').click();await page.locator('#next').click();assert.equal(await pathDetail.getAttribute('open'),null);
   await seek(steps.length-1);
   assert.ok(await page.evaluate(()=>window.__treeEdges.length>0&&window.__treeEdges.every(e=>e.to[1]>e.from[1])),'每条搜索树边从上到下');
   const node=await page.evaluate(()=>window.__treeNodes.find(n=>n.label==='["a","ab"]'));
   assert.ok(node,'剪枝节点可见');
   const bounds=await page.locator('#tree').boundingBox();
   // 固定缩放后用画布记录的实际节点位置测试历史快照点击。
   await page.locator('#zoom').selectOption('0.65');
   const prune=await page.evaluate(()=>window.__treeNodes.find(n=>n.label==='["a","ab"]'));
   const scroll=await page.locator('#tree-scroll').evaluate(el=>({left:el.scrollLeft,top:el.scrollTop}));
   await page.mouse.click(bounds.x+(prune.x+prune.width/2)*.65-scroll.left,bounds.y+(prune.y+prune.height/2)*.65-scroll.top);
   assert.equal(await page.locator('#frame-title').textContent(),'节点 ["a","ab"] · 历史');
   await page.locator('#prev').click();assert.doesNotMatch(await page.locator('#frame-title').textContent(),/历史/);
   await page.locator('#zoom').selectOption('fit');
   await seek(steps.findIndex(s=>s.kind==='prune'));
   await page.screenshot({path:'.work/visual-debug/previews/131-desktop.png',fullPage:true});
   await page.setViewportSize({width:390,height:844});
   await page.screenshot({path:'.work/visual-debug/previews/131-mobile.png',fullPage:true});
   assert.equal(await page.evaluate(()=>document.documentElement.scrollWidth>innerWidth),false);
   await page.setViewportSize({width:1440,height:1100});
 }
 results.push({id:p.id,steps:steps.length,compactLayout:'PASS',status:'PASS'});
}
assert.deepEqual(errors,[]);
await browser.close();
fs.writeFileSync('.work/visual-debug/logs/browser-report.json',JSON.stringify(results,null,2));
console.log(JSON.stringify({pages:results,errors},null,2));
