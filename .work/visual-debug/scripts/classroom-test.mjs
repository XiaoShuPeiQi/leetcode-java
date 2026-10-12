import fs from 'node:fs';
import vm from 'node:vm';
import path from 'node:path';
import assert from 'node:assert/strict';
import {createRequire} from 'node:module';
import {pathToFileURL} from 'node:url';
const context={window:{}};
for(const file of ['catalog.js','search-state.js'])vm.runInNewContext(fs.readFileSync('docs/assets/'+file,'utf8'),context);
const catalog=context.window.CATALOG,report=[];
for(const p of catalog){
 const html=fs.readFileSync('docs/'+p.url+'index.html','utf8');
 assert.ok(html.includes('展老师小课堂')&&html.includes('theme.css')&&html.includes('theme.js'),p.id+' 名称和主题完整覆盖');
 if(p.chapter!=='回溯')continue;
 const ctx={window:{}};vm.runInNewContext(fs.readFileSync('docs/'+p.url+'trace.js','utf8'),ctx);
 const data=ctx.window.TRACE;let count=0;
 for(const node of data.nodes.filter(n=>n.kind==='prune')){
  const vars=Object.fromEntries(data.pool[node.state].map(([k,v])=>[data.pool[k],data.pool[v]]));
  const before=JSON.stringify(vars),view=context.window.SearchState.discarded(p.id,vars);
  assert.equal(JSON.stringify(vars),before,p.id+' 舍弃分支不改动真实快照');
  assert.ok(!view.label.includes('候选'),p.id+' 显示舍弃结果');
  if(Array.isArray(vars.path)){
   assert.ok(view.path.length===vars.path.length+1,p.id+' 完整路径');
   assert.equal(JSON.stringify(view.path.slice(0,-1)),JSON.stringify(vars.path));
  }else assert.ok(view.placement&&Number.isInteger(view.placement.row)&&Number.isInteger(view.placement.col),p.id+' 舍弃棋盘定位');
  count++;
 }
 report.push({id:p.id,discardedBranches:count,status:'PASS'});
}
const require=createRequire(import.meta.url),{chromium}=require(path.join(process.env.CODEX_TEST_MODULES,'playwright'));
const browser=await chromium.launch({executablePath:'C:/Program Files/Google/Chrome/Application/chrome.exe',headless:true});
const page=await browser.newPage({viewport:{width:1440,height:1100}}),errors=[];
page.on('pageerror',e=>errors.push(e.message));
await page.addInitScript(()=>{
 window.drawn=[];const proto=CanvasRenderingContext2D.prototype;
 for(const name of ['clearRect','roundRect','arc','fillText']){
  const original=proto[name];proto[name]=function(...args){
   if(this.canvas.id==='tree'){
    if(name==='clearRect')window.drawn=[];
    if(name==='roundRect')window.drawn.push({x:args[0]+args[2]/2,y:args[1]+args[3]/2,w:args[2],h:args[3]});
    if(name==='arc')window.drawn.push({x:args[0],y:args[1],w:args[2]*2,h:args[2]*2});
    if(name==='fillText'&&window.drawn.length){const item=window.drawn.at(-1);if(!item.label)item.label=args[0];else if(/^#\d+/.test(args[0]))item.id=Number(args[0].match(/^#(\d+)/)[1]);}
   }
   return original.apply(this,args);
  };
 }
});
const base=process.env.PREVIEW_URL||'http://127.0.0.1:8787';
async function seekEnd(){await page.locator('#progress').evaluate(el=>{el.value=el.max;el.dispatchEvent(new Event('input',{bubbles:true}));});}
async function collect(){
 const found=new Map(),size=await page.locator('#tree-scroll').evaluate(el=>({width:el.scrollWidth,height:el.scrollHeight,w:el.clientWidth,h:el.clientHeight}));
 for(let y=0;y<size.height;y+=Math.max(100,size.h-60))for(let x=0;x<size.width;x+=Math.max(100,size.w-60)){
  await page.locator('#tree-scroll').evaluate((el,p)=>{el.scrollLeft=p.x;el.scrollTop=p.y;el.dispatchEvent(new Event('scroll'));},{x,y});
  const nodes=await page.evaluate(()=>window.drawn);
  for(let i=1;i<nodes.length;i++)assert.ok(nodes[i].y>nodes[i-1].y||(nodes[i].y===nodes[i-1].y&&nodes[i].x>=nodes[i-1].x),'绘制从上到下、从左到右');
  for(const n of nodes)if(n.id!==undefined)found.set(n.id,n);
 }
 return found;
}
const problem=catalog.find(p=>p.id===491);
await page.goto(base+'/'+problem.url);await seekEnd();await page.locator('#zoom').selectOption('0.65');
const trace=await page.evaluate(()=>window.TRACE),positions=await collect();
assert.equal(positions.size,trace.nodes.length,'整棵搜索树全部可滚动查看');
const children=new Map();
for(const node of trace.nodes){
 const at=positions.get(node.id);
 if(node.parent!==null){assert.ok(at.y>positions.get(node.parent).y,'父节点在上');if(!children.has(node.parent))children.set(node.parent,[]);children.get(node.parent).push(node);}
}
for(const siblings of children.values()){
 siblings.sort((a,b)=>a.born-b.born||a.id-b.id);
 for(let i=1;i<siblings.length;i++)assert.ok(positions.get(siblings[i].id).x>positions.get(siblings[i-1].id).x,'兄弟分支按实际产生顺序从左到右');
}
const rejected=[...positions.values()].find(n=>n.label==='[4,7,6]');assert.ok(rejected,'完整舍弃路径 [4,7,6]');
await page.locator('#tree-scroll').evaluate((el,p)=>{el.scrollLeft=Math.max(0,p.x*.65-el.clientWidth/2);el.scrollTop=Math.max(0,p.y*.65-el.clientHeight/2);el.dispatchEvent(new Event('scroll'));},rejected);
const area=await page.locator('#tree-scroll').evaluate(el=>({x:el.scrollLeft,y:el.scrollTop})),bounds=await page.locator('#tree').boundingBox();
await page.mouse.click(bounds.x+rejected.x*.65-area.x,bounds.y+rejected.y*.65-area.y);
assert.match(await page.locator('.discarded-branch').textContent(),/舍弃 \[4,7,6\].*破坏非递减/);
assert.equal(await page.locator('#variables [data-variable="path"] summary b').textContent(),'[4,7]','原始 path 仍为 [4,7]');
const before=await page.locator('#progress').inputValue();
for(const theme of ['dark','light']){
 if(await page.locator('html').getAttribute('data-theme')!==theme)await page.locator('.theme-toggle').click();
 assert.equal(await page.locator('#progress').inputValue(),before,'换主题保留当前执行位置');
 await page.screenshot({path:'.work/visual-debug/previews/491-'+theme+'.png',fullPage:true});
 await page.setViewportSize({width:390,height:844});assert.equal(await page.evaluate(()=>document.documentElement.scrollWidth>innerWidth),false);
 await page.screenshot({path:'.work/visual-debug/previews/491-'+theme+'-mobile.png',fullPage:true});await page.setViewportSize({width:1440,height:1100});
}
await page.reload();assert.equal(await page.locator('html').getAttribute('data-theme'),'light','刷新后记住主题');
await page.goto(base+'/');assert.equal(await page.locator('html').getAttribute('data-theme'),'light','首页与调试器共用主题');
await page.locator('.theme-toggle').click();assert.equal(await page.locator('html').getAttribute('data-theme'),'dark');
await page.screenshot({path:'.work/visual-debug/previews/home-classroom-dark.png',fullPage:true});
await page.goto(base+'/'+catalog.find(p=>p.id===104).url);assert.equal(await page.locator('html').getAttribute('data-theme'),'dark');
for(const tab of ['structure','calls']){
 await page.locator('#'+tab+'-tab').click();await seekEnd();await collect();
 if(tab==='calls'){
  const data=await page.evaluate(()=>window.TREE_TRACE),nodes=await collect();
  for(const call of data.calls){if(call.parent!==null)assert.ok(nodes.get(call.id).y>nodes.get(call.parent).y);}
 }
 await page.locator('.theme-toggle').click();await page.screenshot({path:'.work/visual-debug/previews/104-'+tab+'-theme.png',fullPage:true});
}
await page.goto(pathToFileURL(path.resolve('docs/'+problem.url+'index.html')).href);await page.locator('.theme-toggle').click();await page.locator('#next').click();assert.equal(await page.locator('#progress').inputValue(),'1','离线页面主题与单步');
assert.deepEqual(errors,[]);await browser.close();
fs.writeFileSync('.work/visual-debug/logs/classroom-report.json',JSON.stringify({pages:report,brandPages:catalog.length,treeOrder:'PASS',themes:'PASS',offline:'PASS',errors},null,2));
console.log('15 题完整舍弃分支、53 页品牌/主题入口、树顺序、主题保持、手机与离线验证 PASS。');
