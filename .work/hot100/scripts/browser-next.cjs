const assert = require('node:assert/strict');
const fs = require('node:fs');
const path = require('node:path');
const {pathToFileURL} = require('node:url');
const {chromium} = require(path.join(process.env.CODEX_TEST_MODULES, 'playwright'));

const root = path.resolve(__dirname, '../../..');
const section = process.argv[2] || '21-40';
const scope = process.argv[3] || section;
const [first,last] = scope.split('-').map(Number);
const problems = JSON.parse(fs.readFileSync(path.join(root, 'imports/Hot100-'+section+'.import.json'), 'utf8')).problems
 .filter(problem => problem.order >= first && problem.order <= last);
assert.equal(problems.length,last-first+1);
const catalog = {window:{}};
require('node:vm').runInNewContext(fs.readFileSync(path.join(root,'docs/assets/catalog.js'),'utf8'),catalog);
const hot100 = catalog.window.CATALOG.filter(p=>p.chapter==='Hot100');
const base = process.env.PREVIEW_URL;
const url = rel => base ? base + '/' + rel : pathToFileURL(path.join(root, 'docs', rel)).href;

(async () => {
 const browser = await chromium.launch({channel:'msedge', headless:true});
 const page = await browser.newPage({viewport:{width:1440,height:1000}});
 const errors = [];
 const report = [];
 page.on('pageerror', error => errors.push(error.message));
 page.on('response', response => {if(response.status() >= 400) errors.push(response.status() + ' ' + response.url());});
 try {
  await page.goto(url('index.html') + '?chapter=Hot100');
  assert.equal(await page.locator('.card').count(), hot100.length);
  assert.deepEqual((await page.locator('.card h3').allTextContents()).slice(problems[0].order-1,problems.at(-1).order),problems.map(p=>p.title));
  for (const p of problems) {
   await page.goto(url('problems/' + p.page_slug + '/index.html'));
   await page.locator('.code-line.current').waitFor();
   const traceFile=fs.readFileSync(path.join(root,'docs/problems',p.page_slug,'trace.js'),'utf8');
   const trace=JSON.parse(traceFile.slice(traceFile.indexOf('=')+1).trim().replace(/;$/,''));
   assert.deepEqual(await page.evaluate(()=>window.HOT100_TRACE),trace);
   assert.match(await page.locator('.eyebrow').textContent(), new RegExp(String(p.order)));
   const progress = page.locator('#progress');
   const max = Number(await progress.getAttribute('max'));
   const seek = i => progress.evaluate((e, value) => {
    e.value = String(value);
    e.dispatchEvent(new Event('input', {bubbles:true}));
   }, i);
   const initial = await page.locator('#variables').innerHTML();
   await page.locator('#next').click();
   await page.locator('#prev').click();
   assert.equal(await page.locator('#variables').innerHTML(), initial);
   const stops = await page.evaluate(() => {
    const data = window.HOT100_TRACE;
    const playback = window.ExecutionSteps.create(data);
    return playback.stops.map(index => data.steps[index].line);
   });
   assert.equal(stops.length, max+1);
   // 在后续语句上设断点，以五倍速播放到该语句。
   const stop = stops.findIndex((line, i) => i > 0 && i < Math.min(max, 10) && line !== stops[0]);
   assert.ok(stop > 0);
   await page.locator('.code-line[data-line="' + stops[stop] + '"]').click();
   await page.locator('#speed').selectOption('140');
   await page.locator('#play').click();
   await page.waitForFunction(value => Number(document.querySelector('#progress').value) === value, stop);
   await page.waitForFunction(() => document.querySelector('#play').textContent.includes('播放'));
   await seek(max);
   assert.match(await page.locator('#result').textContent(), /PASS/);
   assert.ok((await page.locator('#result').textContent()).includes(p.expected), p.id);
   if ([240,200,994,79,74].includes(p.id)) assert.ok(await page.locator('#state .matrix-cell').count() > 0);
   if (trace.nodeIds.length) assert.ok(await page.locator('#state [data-node]').count() > 0, p.id);
   if (p.id === 138) assert.ok(await page.locator('[data-edge=random]').count() > 0);
   if (p.id === 146) assert.ok(await page.locator('[data-edge=prev]').count() > 0);
   if (p.id === 208) assert.ok(await page.locator('[data-word-end=true]').count() >= 2);
   if ([240,146,543,105,208,79].includes(p.id) || p.order >= 61) {
    await page.setViewportSize({width:390,height:844});
    assert.ok(await page.evaluate(() => document.documentElement.scrollWidth <= innerWidth+1), p.id);
    await page.setViewportSize({width:1440,height:1000});
   }
   if (base && p.preserveSubmission) {
    assert.equal((await page.locator('#java-source').textContent()).trimEnd(), p.code.trimEnd());
    const exported = await page.request.get(new URL('trace.json', page.url()).href);
    assert.ok(exported.ok());
    assert.deepEqual(await exported.json(), trace);
   }
   assert.deepEqual(errors, []);
   report.push({id:p.id, visibleStops:max+1, stepBack:'PASS', breakpoint:'PASS', state:'PASS', result:'PASS'});
   console.log(p.id, 'PASS', max+1, 'visible stops');
  }
 } finally {
  await browser.close();
 }
 const file = path.join(root, 'outputs', 'Hot100-'+scope+(base ? '-online.json' : '-browser.json'));
 fs.writeFileSync(file, JSON.stringify({base:base||'local', problems:report},null,2)+'\n');
})().catch(error => {console.error(error); process.exitCode=1;});
