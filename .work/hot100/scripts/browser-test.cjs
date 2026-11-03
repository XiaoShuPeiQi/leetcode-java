const fs=require('fs'),path=require('path'),assert=require('assert/strict'),{pathToFileURL}=require('url');
const {chromium}=require(path.join(process.env.CODEX_TEST_MODULES,'playwright'));
const R=path.resolve(__dirname,'../../..');
const previews=path.join(R,'.work/hot100/previews');fs.mkdirSync(previews,{recursive:true});
const meta=JSON.parse(fs.readFileSync(path.join(R,'imports/Hot100.import.json'),'utf8'));
const base=process.env.PREVIEW_URL;
const url=p=>base?base+'/'+p:pathToFileURL(path.join(R,'docs',p)).href;
(async()=>{
 const browser=await chromium.launch({channel:'msedge',headless:true});
 const page=await browser.newPage({viewport:{width:1440,height:1050}}),errors=[],failed=[],reports=[];
 page.on('pageerror',e=>errors.push(e.message));page.on('response',r=>{if(r.status()>=400)failed.push(r.url());});
 try{
  await page.goto(url('index.html')+'?chapter=Hot100');assert.equal(await page.locator('.card').count(),20);
  const titles=await page.locator('.card h3').allTextContents();assert.deepEqual(titles,meta.problems.map(p=>p.title));
  await page.locator('#search').fill('438');assert.equal(await page.locator('.card').count(),1);await page.locator('#search').fill('');
  await page.setViewportSize({width:390,height:844});assert(await page.evaluate(()=>document.documentElement.scrollWidth<=innerWidth+1));await page.setViewportSize({width:1440,height:1050});
  for(const p of meta.problems){
   await page.goto(url('problems/'+p.page_slug+'/index.html'));await page.locator('.code-line.current').waitFor();
   assert((await page.locator('.eyebrow').innerText()).includes('HOT100'));
   assert.equal(await page.locator('.classroom-splitter').count(),2);
   const seek=async i=>page.locator('#progress').evaluate((el,i)=>{el.value=i;el.dispatchEvent(new Event('input',{bubbles:true}));},i);
   const snapshot=()=>page.evaluate(()=>['state','variables','stack'].map(id=>document.getElementById(id).innerHTML));
   const initial=await snapshot();await page.locator('#next').click();await page.locator('#prev').click();assert.deepEqual(await snapshot(),initial);
   const max=Number(await page.locator('#progress').getAttribute('max'));let matrix=0,pointers=0,changed=0,hash=0;
   for(let i=0;i<=max;i++){
    await seek(i);assert.equal(await page.locator('.code-line.current').count(),1);
    const n=await page.evaluate(()=>({matrix:document.querySelectorAll('.matrix').length,pointers:document.querySelectorAll('.pointer').length,changed:document.querySelectorAll('.changed').length,hash:document.getElementById('state').innerText.includes('键 → 值')}));
    matrix+=n.matrix;pointers+=n.pointers;changed+=n.changed;hash+=n.hash;
   }
   assert((await page.locator('#result').innerText()).includes('PASS'));
   if([48,54,73].includes(p.id))assert(matrix>0,p.id+' missing matrix');
   if([283,189,41,48,73].includes(p.id))assert(changed>0,p.id+' missing mutation');
   if([1,49,560].includes(p.id))assert(hash>0,p.id+' missing map');
   if([11,42].includes(p.id))assert.equal(await page.locator('.height-chart rect').count(),p.example.height.length);
   for(const theme of ['light','dark']){
    await page.evaluate(t=>{localStorage.setItem('zhan-classroom-theme',t);location.reload();},theme);await page.locator('.code-line.current').waitFor();
    const font=await page.locator('.code-line code').first().evaluate(el=>({family:getComputedStyle(el).fontFamily,weight:getComputedStyle(el).fontWeight}));assert(Number(font.weight)>=600);assert(font.family.includes('Microsoft YaHei'));
   }
   const before=await page.locator('.workspace > .panel').first().boundingBox(),handle=await page.locator('.classroom-splitter').first().boundingBox();
   await page.mouse.move(handle.x+handle.width/2,handle.y+80);await page.mouse.down();await page.mouse.move(handle.x+60,handle.y+80,{steps:4});await page.mouse.up();
   assert((await page.locator('.workspace > .panel').first().boundingBox()).width>before.width+25);await page.locator('.classroom-splitter').first().dblclick();
   await seek(4);const target=await page.evaluate(()=>{const p=ExecutionSteps.create(HOT100_TRACE);return HOT100_TRACE.steps[p.stops[5]].line;});
   await page.locator('[data-line="'+target+'"]').click();assert.equal(await page.locator('.code-line.breakpoint').count(),1);
   await page.locator('#speed').selectOption('140');await page.locator('#play').click();
   await page.waitForFunction(()=>document.getElementById('explanation').textContent==='命中播放断点',null,{timeout:8000});
   if(p.id===189){
    const deep=await page.evaluate(()=>{const p=ExecutionSteps.create(HOT100_TRACE);return p.stops.findIndex(i=>HOT100_TRACE.pool[HOT100_TRACE.steps[i].frames].length>1);});assert(deep>=0);await seek(deep);
    await page.locator('.stack-frame').nth(1).click();assert.equal(await page.locator('.stack-frame.selected').getAttribute('data-top'),'false');
   }
   await seek(Math.floor(max*.65));
   if([49,42,438,41,73,48].includes(p.id))await page.screenshot({path:path.join(previews,'preview-'+p.id+'.png'),fullPage:true});
   await page.setViewportSize({width:390,height:844});assert(await page.evaluate(()=>document.documentElement.scrollWidth<=innerWidth+1),p.id+' mobile overflow');
   if(p.id===73)await page.screenshot({path:path.join(previews,'preview-mobile.png'),fullPage:true});
   await page.setViewportSize({width:1440,height:1050});
   reports.push({id:p.id,stops:max+1,matrix,pointers,changed,hash,playback:'PASS',breakpoint:'PASS',themes:'PASS',resize:'PASS',mobile:'PASS'});console.log(p.id+' PASS '+(max+1)+' visible stops');
  }
  assert.deepEqual(errors,[]);assert.deepEqual(failed,[]);
  const result={status:'PASS',base:base||'local files',problems:reports,pageErrors:errors,httpFailures:failed};
  fs.writeFileSync(path.join(R,'outputs',base?'Hot100-线上浏览器验收.json':'Hot100-浏览器验收.json'),JSON.stringify(result,null,2));
 }finally{await browser.close();}
})().catch(e=>{console.error(e);process.exit(1);});
