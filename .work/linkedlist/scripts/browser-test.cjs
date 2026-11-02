const fs=require('fs'),path=require('path'),assert=require('assert/strict'),{pathToFileURL}=require('url');
const {chromium}=require(path.join(process.env.CODEX_TEST_MODULES,'playwright'));
(async()=>{
 const root=process.cwd(),base=process.env.PREVIEW_URL||pathToFileURL(path.join(root,'docs/') ).href,browser=await chromium.launch({channel:'msedge',headless:true});
 const screenshots=path.join(root,'.work/linkedlist/tmp/screenshots');fs.mkdirSync(screenshots,{recursive:true});
 const page=await browser.newPage({viewport:{width:1500,height:1050}}),errors=[],reports=[];
 page.on('pageerror',e=>errors.push(e.message));
 try{
  await page.goto(new URL('index.html',base).href);
  const expected=await page.evaluate(()=>CATALOG);assert.equal(await page.locator('.card').count(),expected.length);for(const chapter of [...new Set(expected.map(p=>p.chapter))]){await page.locator('[data-chapter="'+chapter+'"]').click();assert.equal(await page.locator('.card').count(),expected.filter(p=>p.chapter===chapter).length);}await page.locator('[data-chapter="链表"]').click();assert.equal(await page.locator('.card').count(),8);
  const meta=JSON.parse(fs.readFileSync(path.join(root,'imports/链表.import.json'),'utf8')).problems;
  for(const p of meta){
   await page.goto(new URL('problems/'+p.slug+'/index.html',base).href);
   await page.locator('.code-line.current').waitFor();
   const state=()=>page.evaluate(()=>['variables','stack','state'].map(id=>document.getElementById(id).innerHTML));
   const initial=await state();await page.locator('#next').click();await page.locator('#prev').click();assert.deepEqual(await state(),initial);
   const data=await page.evaluate(()=>({steps:LINKED_TRACE.steps.length,code:LINKED_TRACE.code,nodeIds:LINKED_TRACE.nodeIds,stops:ExecutionSteps.create(LINKED_TRACE).stops}));
   assert(data.nodeIds.length>0);
   for(let i=0;i<data.stops.length;i++){
    await page.locator('#progress').evaluate((e,v)=>{e.value=v;e.dispatchEvent(new Event('input',{bubbles:true}));},i);
    const inspected=await page.evaluate(()=>({current:document.querySelector('.code-line.current')?.dataset.line,svg:document.querySelector('#state svg')?.getAttribute('aria-label'),nodes:document.querySelectorAll('#state [data-node]').length}));
    assert(inspected.current);assert(inspected.svg);assert(inspected.nodes>0,p.id+' missing node graph');
   }
   assert((await page.locator('#result').innerText()).includes('PASS'));
   assert((await page.locator('#result').innerText()).includes(p.expected));
   await page.locator('#reset').click();
   const active=page.locator('.code-line.current');await active.click();assert((await active.getAttribute('class')).includes('breakpoint'));
   await active.click();
   await page.locator('#reset').click();
   const nextLine=await page.evaluate(()=>{const p=ExecutionSteps.create(LINKED_TRACE);return LINKED_TRACE.steps[p.stops[1]].line;});
   const row=page.locator('[data-line="'+nextLine+'"]');await row.click();
   await page.locator('#speed').selectOption('140');await page.locator('#play').click();
   await page.waitForFunction(line=>document.getElementById('progress').value!=='0'&&Number(document.querySelector('.code-line.current').dataset.line)===line&&document.getElementById('play').textContent.includes('播放'),nextLine,{timeout:10000});
   await row.click();await page.locator('#reset').click();
   const theme=await page.locator('html').getAttribute('data-theme');
   await page.locator('.theme-toggle').click();assert.notEqual(await page.locator('html').getAttribute('data-theme'),theme);
   if(p.id===206)await page.screenshot({path:path.join(screenshots,'preview-dark.png'),fullPage:true});
   await page.locator('.theme-toggle').click();
   const handle=page.locator('.classroom-splitter').first();
   const widths=()=>page.locator('.code-panel').evaluate(e=>e.getBoundingClientRect().width);
   const beforeWidth=await widths();const box=await handle.boundingBox();
   await page.mouse.move(box.x+box.width/2,box.y+box.height/2);await page.mouse.down();await page.mouse.move(box.x+box.width/2+55,box.y+box.height/2);await page.mouse.up();
   assert(await widths()>beforeWidth+30);await handle.dblclick();
   if(p.id===206||p.id===142||p.id===707){
    await page.locator('#progress').evaluate(e=>{e.value=Math.floor(Number(e.max)*.65);e.dispatchEvent(new Event('input',{bubbles:true}));});
    await page.screenshot({path:path.join(screenshots,'preview-'+p.id+'.png'),fullPage:true});
   }
   await page.setViewportSize({width:390,height:844});
   assert(await page.locator('#next').isVisible());
   const dims=await page.evaluate(()=>({w:innerWidth,scroll:document.documentElement.scrollWidth}));assert(dims.scroll<=dims.w+2,p.id+' page overflow');
   await page.setViewportSize({width:1500,height:1050});
   reports.push({id:p.id,navigation:'PASS',fixedSnapshots:data.stops.length,graph:'PASS',final:'PASS',breakpoints:'PASS',playbackStopsAtBreakpoint:'PASS',mobile:'PASS',themes:'PASS',resize:'PASS'});
  }
  assert.deepEqual(errors,[]);
  fs.writeFileSync(path.join(root,'outputs/链表-浏览器验收.json'),JSON.stringify({status:'PASS',browser:'Microsoft Edge headless / Playwright',errors,reports},null,2));
  console.log('PASS: 8 pages, all snapshot positions, reversible steps, breakpoints, final results and mobile layouts.');
 }finally{await browser.close();}
})().catch(e=>{console.error(e);process.exit(1);});
