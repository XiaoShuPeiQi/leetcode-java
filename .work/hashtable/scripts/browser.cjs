const fs=require('fs'),path=require('path'),vm=require('vm'),assert=require('assert/strict');
const {chromium}=require(path.join(process.env.CODEX_TEST_MODULES,'playwright'));
const base=process.env.PREVIEW_URL||'http://127.0.0.1:8893';
const R=path.resolve(__dirname,'../../..'),ctx={window:{}};
vm.runInNewContext(fs.readFileSync(path.join(R,'docs/assets/catalog.js'),'utf8'),ctx);
const P=ctx.window.CATALOG.filter(p=>p.chapter==='哈希表');
(async()=>{
 const browser=await chromium.launch({channel:'msedge',headless:true});
 const page=await browser.newPage({viewport:{width:1440,height:1050}}),errors=[],bad=[],reports=[];
 page.on('pageerror',e=>errors.push(e.message));page.on('response',r=>{if(r.status()>=400)bad.push(r.url());});
 await page.goto(base+'/index.html?chapter='+encodeURIComponent('哈希表'));
 assert.equal(await page.locator('.card').count(),8);
 for(const p of P){
  await page.goto(base+'/'+p.url);await page.locator('.code-line.current').waitFor();
  assert((await page.locator('.eyebrow').innerText()).includes('哈希表'));
  assert.equal(await page.locator('.classroom-splitter').count(),2);
  const last=Number(await page.locator('#progress').getAttribute('max'));
  let cells=0,states=new Set();
  for(let i=0;i<=last;i++){
   await page.locator('#progress').evaluate((el,i)=>{el.value=i;el.dispatchEvent(new Event('input',{bubbles:true}));},i);
   assert.equal(await page.locator('.code-line.current').count(),1);
   cells+=await page.locator('.hash-cell').count();
   states.add(await page.locator('#state').innerText());
  }
  assert(states.size>1,p.id+' missing changing state');
  if([242,383,349,202,1,454].includes(p.id))assert(cells>0,p.id+' missing hash cells');
  assert((await page.locator('#result').innerText()).includes('PASS'));
  const snap=()=>page.evaluate(()=>['progress','variables','state','stack'].map(k=>k==='progress'?document.getElementById(k).value:document.getElementById(k).innerHTML));
  await page.locator('#reset').click();await page.locator('#next').click();const a=await snap();await page.locator('#next').click();await page.locator('#prev').click();assert.deepEqual(await snap(),a);
  for(const theme of ['light','dark']){
   await page.evaluate(t=>{localStorage.setItem('zhan-classroom-theme',t);location.reload();},theme);await page.locator('.code-line.current').waitFor();
   const font=await page.locator('.code-line code').first().evaluate(e=>({weight:getComputedStyle(e).fontWeight,family:getComputedStyle(e).fontFamily}));assert(Number(font.weight)>=600);
   const panel=page.locator('.workspace > .panel').first(),before=await panel.boundingBox(),handle=await page.locator('.classroom-splitter').first().boundingBox();
   await page.mouse.move(handle.x+handle.width/2,handle.y+80);await page.mouse.down();await page.mouse.move(handle.x+handle.width/2+70,handle.y+80,{steps:4});await page.mouse.up();
   assert((await panel.boundingBox()).width>before.width+30);await page.locator('.classroom-splitter').first().dblclick();
  }
  const stop=await page.evaluate(()=>{const p=ExecutionSteps.create(HASH_TRACE);return HASH_TRACE.steps[p.stops[Math.min(5,p.stops.length-2)]].line;});
  await page.locator('[data-line="'+stop+'"]').click();assert.equal(await page.locator('.code-line.breakpoint').count(),1);
  await page.locator('#speed').selectOption('140');await page.locator('#play').click();
  await page.waitForFunction(()=>document.getElementById('explanation').textContent==='命中播放断点',{timeout:10000});
  assert.equal(await page.locator('#play').innerText(),'▶ 播放');
  if(p.id===242){await page.screenshot({path:path.join(R,'.work/hashtable/tmp/hash-preview.png'),fullPage:true});}
  await page.setViewportSize({width:560,height:950});await page.locator('#next').click();assert(await page.locator('.code-line.current').count());await page.setViewportSize({width:1440,height:1050});
  reports.push({id:p.id,steps:last+1,changingStates:states.size,hashCellsChecked:cells,rewind:'PASS',themes:'PASS',resize:'PASS',playbackBreakpoint:'PASS',narrowViewport:'PASS'});
  console.log(p.id,'browser PASS');
 }
 assert.deepEqual(errors,[]);assert.deepEqual(bad,[]);
 fs.writeFileSync(path.join(R,'outputs/哈希表-浏览器复验.json'),JSON.stringify({status:'PASS',base,problems:reports,errors,bad},null,2));
 await browser.close();
})().catch(e=>{console.error(e);process.exit(1);});
