const fs=require('fs'),vm=require('vm'),path=require('path'),assert=require('assert/strict');
const {chromium}=require(path.join(process.env.CODEX_TEST_MODULES,'playwright'));
const base=process.env.PREVIEW_URL||'http://127.0.0.1:8891';
const c={window:{}};vm.runInNewContext(fs.readFileSync('docs/assets/catalog.js','utf8'),c);
const catalog=c.window.CATALOG.filter(p=>p.chapter==='动态规划');
(async()=>{
 const browser=await chromium.launch({channel:'msedge',headless:true}),page=await browser.newPage({viewport:{width:1440,height:1050}}),errors=[],failed=[];let checks=0;
 page.on('pageerror',e=>errors.push(e.message));page.on('response',r=>{if(r.status()>=400)failed.push(r.url());});
 await page.goto(base+'/index.html?chapter='+encodeURIComponent('动态规划'));
 assert.equal(await page.locator('.card').count(),37);assert.equal(await page.locator('#problem-count').innerText(),'107');checks+=2;
 await page.locator('#search').fill('53');assert.equal(await page.locator('.card').count(),1);await page.locator('[data-chapter="all"]').click();for(const p of c.window.CATALOG.filter(p=>p.id===53))assert.equal(await page.locator('a.card-main[href="'+p.url+'"]').count(),1);checks+=3;
 const snapshot=()=>page.evaluate(()=>({progress:document.getElementById('progress').value,vars:document.getElementById('variables').innerHTML,state:document.getElementById('state').innerHTML,line:document.querySelector('.code-line.current')?.dataset.line,stack:document.getElementById('stack').innerHTML}));
 for(const p of catalog){
  await page.goto(base+'/'+p.url);await page.locator('.code-line.current').waitFor();
  assert.equal(await page.locator('.classroom-splitter').count(),2);assert((await page.locator('.eyebrow').innerText()).includes('动态规划'));checks+=2;
  const data=await page.evaluate(()=>({id:DP_TRACE.id,n:document.getElementById('progress').max,raw:DP_TRACE.steps.length,code:DP_TRACE.code,expected:DP_TRACE.expected}));
  let sawTable=p.id===337,read=0,write=0,changed=0;
  for(let i=0;i<=Number(data.n);i++){
   await page.locator('#progress').evaluate((el,i)=>{el.value=i;el.dispatchEvent(new Event('input',{bubbles:true}));},i);
   if(await page.locator('.dp-table').count())sawTable=true;
   read+=await page.locator('.dp-read').count();write+=await page.locator('.dp-target').count();changed+=await page.locator('.dp-changed').count();
   assert(await page.locator('.code-line.current').count());checks++;
  }
  assert(sawTable,p.id+' missing DP state table');assert((await page.locator('#result').innerText()).includes('PASS'));assert((await page.locator('#result').innerText()).includes(data.expected));checks+=3;
  if(p.id!==337)assert(changed>0,p.id+' missing state mutation');
  const target=Math.min(6,Number(data.n)-1);await page.locator('#progress').evaluate((el,i)=>{el.value=i;el.dispatchEvent(new Event('input',{bubbles:true}));},target);
  const a=await snapshot();await page.locator('#next').click();await page.locator('#prev').click();assert.deepEqual(await snapshot(),a);checks++;
  for(const theme of ['light','dark']){
   await page.evaluate(t=>{localStorage.setItem('zhan-classroom-theme',t);location.reload();},theme);await page.locator('.code-line.current').waitFor();
   const font=await page.locator('.code-line code').first().evaluate(el=>({font:getComputedStyle(el).fontFamily,weight:getComputedStyle(el).fontWeight}));assert(Number(font.weight)>=600);checks++;
   const before=await page.locator('.workspace > .panel').first().boundingBox();const handle=await page.locator('.classroom-splitter').first().boundingBox();
   await page.mouse.move(handle.x+handle.width/2,handle.y+100);await page.mouse.down();await page.mouse.move(handle.x+handle.width/2+60,handle.y+100,{steps:5});await page.mouse.up();
   const after=await page.locator('.workspace > .panel').first().boundingBox();assert(after.width>before.width+30,p.id+' resize failed '+theme);await page.locator('.classroom-splitter').first().dblclick();checks++;
  }
  // A playback breakpoint is attached to an executed source line, and pauses automatic playback.
  const firstLine=await page.locator('.code-line.current').getAttribute('data-line');await page.locator('[data-line="'+firstLine+'"]').click();assert.equal(await page.locator('.code-line.breakpoint').count(),1);checks++;
  if(p.id===70){
   await page.locator('[data-line="'+firstLine+'"]').click();
   const stopLine=await page.evaluate(()=>{const p=ExecutionSteps.create(DP_TRACE);return DP_TRACE.steps[p.stops[5]].line;});
   await page.locator('[data-line="'+stopLine+'"]').click();await page.locator('#speed').selectOption('140');await page.locator('#play').click();
   await page.waitForFunction(()=>document.getElementById('explanation').textContent==='命中播放断点',{timeout:10000});assert.equal(await page.locator('#play').innerText(),'▶ 播放');checks++;
  }
  if(p.id===337){
   const deep=await page.evaluate(()=>{const p=ExecutionSteps.create(DP_TRACE);return p.stops.findIndex(i=>DP_TRACE.pool[DP_TRACE.steps[i].frames].length>=3);});
   await page.locator('#progress').evaluate((el,i)=>{el.value=i;el.dispatchEvent(new Event('input',{bubbles:true}));},deep);
   await page.locator('.stack-frame').nth(1).click();assert.equal(await page.locator('.stack-frame.selected').getAttribute('data-top'),'false');checks++;
  }
  await page.setViewportSize({width:390,height:844});assert(await page.evaluate(()=>document.documentElement.scrollWidth<=innerWidth+1),p.id+' mobile overflow');await page.locator('#next').click();checks++;
  if([416,1143,337].includes(p.id)){await page.setViewportSize({width:1440,height:1050});await page.locator('#progress').evaluate(el=>{el.value=Math.floor(Number(el.max)*.8);el.dispatchEvent(new Event('input',{bubbles:true}));});fs.mkdirSync('.work/dp/previews',{recursive:true});await page.screenshot({path:'.work/dp/previews/'+p.id+'.png',fullPage:true});}
  await page.setViewportSize({width:1440,height:1050});console.log(p.id+' PASS '+data.raw+' JDI events');
 }
 // Existing chapters still load and reach a verified result.
 for(const id of [77,104,455,53,122]){const p=c.window.CATALOG.find(p=>p.id===id&&p.chapter!=='动态规划');await page.goto(base+'/'+p.url);await page.locator('#next').waitFor();await page.locator('#next').click();await page.locator('#progress').evaluate(el=>{el.value=el.max;el.dispatchEvent(new Event('input',{bubbles:true}));});assert((await page.locator('#result').innerText()).includes('PASS'));checks++;}
 assert.deepEqual(errors,[]);assert.deepEqual(failed,[]);
 const report={status:'PASS',pages:37,regressionPages:5,checks,base,errors,failed};fs.writeFileSync('outputs/动态规划-浏览器验收.json',JSON.stringify(report,null,2));console.log(JSON.stringify(report));await browser.close();
})().catch(e=>{console.error(e);process.exit(1);});
