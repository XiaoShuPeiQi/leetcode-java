const fs=require('fs'),vm=require('vm'),path=require('path'),assert=require('assert/strict');
const {chromium}=require(path.join(process.env.CODEX_TEST_MODULES,'playwright'));
const base=process.env.PREVIEW_URL||'http://127.0.0.1:8892';
const c={window:{}};vm.runInNewContext(fs.readFileSync('docs/assets/catalog.js','utf8'),c);
const catalog=c.window.CATALOG.filter(p=>p.chapter==='数组');
(async()=>{
 const browser=await chromium.launch({channel:'msedge',headless:true});
 const page=await browser.newPage({viewport:{width:1440,height:1050}}),errors=[],failed=[];let checks=0;
 page.on('pageerror',e=>errors.push(e.message));page.on('response',r=>{if(r.status()>=400)failed.push(r.url());});
 await page.goto(base+'/index.html?chapter='+encodeURIComponent('数组'));
 assert.equal(await page.locator('.card').count(),18);assert.equal(await page.locator('#problem-count').innerText(),String(c.window.CATALOG.length));checks+=2;
 await page.locator('[data-chapter="哈希表"]').click();assert.equal(await page.locator('.card').count(),8);await page.locator('[data-chapter="数组"]').click();checks++;
 await page.setViewportSize({width:390,height:844});assert(await page.evaluate(()=>document.documentElement.scrollWidth<=innerWidth+1),'home mobile overflow');await page.setViewportSize({width:1440,height:1050});checks++;
 await page.locator('#search').fill('K58');assert.equal(await page.locator('.card').count(),1);checks++;
 const seek=async i=>page.locator('#progress').evaluate((el,i)=>{el.value=i;el.dispatchEvent(new Event('input',{bubbles:true}));},i);
 const snap=async()=>page.evaluate(()=>({line:document.querySelector('.code-line.current')?.dataset.line,vars:document.getElementById('variables').innerHTML,state:document.getElementById('state').innerHTML,stack:document.getElementById('stack').innerHTML}));
 for(const p of catalog){
  await page.goto(base+'/'+p.url);await page.locator('.code-line.current').waitFor();
  assert.equal(await page.locator('.classroom-splitter').count(),2);assert((await page.locator('.eyebrow').innerText()).includes('数组'));checks+=2;
  const data=await page.evaluate(()=>({max:Number(document.getElementById('progress').max),raw:ARRAY_TRACE.steps.length,expected:ARRAY_TRACE.expected}));
  let changed=0,pointers=0,matrix=0;
  for(let i=0;i<=data.max;i++){
   await seek(i);assert(await page.locator('.code-line.current').count());changed+=await page.locator('.changed').count();pointers+=await page.locator('.pointer').count();matrix+=await page.locator('.matrix').count();checks++;
  }
  assert((await page.locator('#result').innerText()).includes('PASS'));assert((await page.locator('#result').innerText()).includes(data.expected));checks+=2;
  if([27,26,283,977,59,'LCR146','K58'].includes(p.id))assert(changed>0,p.id+' missing visible mutation');
  if([704,35,34,27,26,283,977,209,904,76,844].includes(p.id))assert(pointers>0,p.id+' missing pointer labels');
  if([54,59,'LCR146','K44'].includes(p.id))assert(matrix>0,p.id+' missing matrix');
  await seek(Math.min(4,data.max-1));const a=await snap();await page.locator('#next').click();await page.locator('#prev').click();assert.deepEqual(await snap(),a);checks++;
  for(const theme of ['light','dark']){
   await page.evaluate(t=>{localStorage.setItem('zhan-classroom-theme',t);location.reload();},theme);await page.locator('.code-line.current').waitFor();
   const font=await page.locator('.code-line code').first().evaluate(el=>({family:getComputedStyle(el).fontFamily,weight:getComputedStyle(el).fontWeight}));assert(Number(font.weight)>=600);assert(font.family.includes('Microsoft YaHei'));checks+=2;
   const before=await page.locator('.workspace > .panel').first().boundingBox(),handle=await page.locator('.classroom-splitter').first().boundingBox();
   await page.mouse.move(handle.x+handle.width/2,handle.y+80);await page.mouse.down();await page.mouse.move(handle.x+handle.width/2+60,handle.y+80,{steps:5});await page.mouse.up();
   const after=await page.locator('.workspace > .panel').first().boundingBox();assert(after.width>before.width+30);await page.locator('.classroom-splitter').first().dblclick();checks++;
  }
  const target=await page.evaluate(()=>{const p=ExecutionSteps.create(ARRAY_TRACE);return ARRAY_TRACE.steps[p.stops[Math.min(5,p.stops.length-2)]].line;});
  await page.locator('[data-line="'+target+'"]').click();assert.equal(await page.locator('.code-line.breakpoint').count(),1);checks++;
  await page.locator('#speed').selectOption('140');await page.locator('#play').click();
  await page.waitForFunction(()=>document.getElementById('explanation').textContent==='命中播放断点',null,{timeout:8000});assert.equal(await page.locator('#play').innerText(),'▶ 播放');checks++;
  if(p.id===34){
   const deep=await page.evaluate(()=>{const p=ExecutionSteps.create(ARRAY_TRACE);return p.stops.findIndex(i=>ARRAY_TRACE.pool[ARRAY_TRACE.steps[i].frames].length>1);});await seek(deep);
   await page.locator('.stack-frame').nth(1).click();assert.equal(await page.locator('.stack-frame.selected').getAttribute('data-top'),'false');checks++;
  }
  await page.setViewportSize({width:390,height:844});assert(await page.evaluate(()=>document.documentElement.scrollWidth<=innerWidth+1),p.id+' mobile overflow');await page.locator('#next').click();checks++;
  if([76,59,209].includes(p.id)){
   fs.mkdirSync('.work/array/previews',{recursive:true});await page.screenshot({path:'.work/array/previews/'+p.id+'-mobile.png',fullPage:true});
   await page.setViewportSize({width:1440,height:1050});await seek(Math.floor(data.max*.7));await page.screenshot({path:'.work/array/previews/'+p.id+'.png',fullPage:true});
  }
  await page.setViewportSize({width:1440,height:1050});console.log(p.id+' PASS '+data.raw+' JDI events');
 }
 for(const [id,chapter] of [[77,'回溯'],[104,'二叉树'],[455,'贪心'],[70,'动态规划'],[242,'哈希表']]){
  const p=c.window.CATALOG.find(p=>p.id===id&&p.chapter===chapter);await page.goto(base+'/'+p.url);await page.locator('#next').waitFor();await page.locator('#next').click();await page.locator('#progress').evaluate(el=>{el.value=el.max;el.dispatchEvent(new Event('input',{bubbles:true}));});assert((await page.locator('#result').innerText()).includes('PASS'));checks++;
 }
 assert.deepEqual(errors,[]);assert.deepEqual(failed,[]);
 const report={status:'PASS',pages:18,regressionPages:5,catalogEntries:c.window.CATALOG.length,checks,base,errors,failed};fs.writeFileSync(process.env.REPORT_PATH||'outputs/数组-浏览器验收.json',JSON.stringify(report,null,2)+'\n');console.log(JSON.stringify(report));await browser.close();
})().catch(e=>{console.error(e);process.exit(1);});
