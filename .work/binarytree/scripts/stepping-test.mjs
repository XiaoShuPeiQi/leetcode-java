import fs from 'node:fs';
import vm from 'node:vm';
import path from 'node:path';
import assert from 'node:assert/strict';
import {createRequire} from 'node:module';
const ctx={window:{}};
vm.runInNewContext(fs.readFileSync('docs/assets/catalog.js','utf8'),ctx);
vm.runInNewContext(fs.readFileSync('docs/assets/execution-steps.js','utf8'),ctx);
const report=[],traces=new Map();
for(const problem of ctx.window.CATALOG){
 const c={window:{}};
 vm.runInNewContext(fs.readFileSync('docs/'+problem.url+'trace.js','utf8'),c);
 const data=c.window.TREE_TRACE||c.window.TRACE,playback=ctx.window.ExecutionSteps.create(data);
 traces.set(problem.id,{problem,data,playback});
 assert.equal(playback.stops.at(-1),data.steps.length-1,problem.id+' 最终结果保留');
 assert.ok(playback.stops.length<data.steps.length,problem.id+' 合并重复停顿');
 for(let i=0;i<playback.stops.length-1;i++){
  const raw=playback.stops[i],step=data.steps[raw],source=data.code[step.line-data.displayStart]?.trim();
  assert.equal(step.phase,'line',problem.id+' 只停在执行语句前');
  assert.ok(source&&!/^[{};\s]+$/.test(source),problem.id+' 不停在括号');
  if(i)assert.ok(raw>playback.stops[i-1],problem.id+' 保持原始执行顺序');
 }
 // 调用和返回事件必须随相邻执行步骤保留，不能因合并而丢失。
 assert.equal(playback.events.reduce((n,e)=>n+e.returns.length,0),data.steps.filter(s=>s.phase==='exit').length,problem.id+' 所有返回仍可观察');
 report.push({id:problem.id,rawEvents:data.steps.length,executionStops:playback.stops.length,status:'PASS'});
}
const maxDepth=traces.get(104);
assert.equal(JSON.stringify(maxDepth.playback.stops.slice(0,13)),JSON.stringify([1,2,4,5,7,8,10,11,13,15,16,18,20]));
const require=createRequire(import.meta.url),{chromium}=require(path.join(process.env.CODEX_TEST_MODULES,'playwright'));
const browser=await chromium.launch({executablePath:'C:/Program Files/Google/Chrome/Application/chrome.exe',headless:true});
const page=await browser.newPage({viewport:{width:1440,height:1000}}),errors=[];
page.on('pageerror',e=>errors.push(e.message));
const base=process.env.PREVIEW_URL||'http://127.0.0.1:8787';
await page.goto(base+'/'+maxDepth.problem.url);
// 手工对照原代码：根 -> 左子树 -> 空节点 -> return -> 调用者右子树。
const expected=[{line:50,ids:[1]},{line:54,ids:[1]},{line:50,ids:[2,1]},{line:54,ids:[2,1]},{line:50,ids:[3,2,1]},{line:54,ids:[3,2,1]},{line:50,ids:[4,3,2,1]},{line:51,ids:[4,3,2,1]},{line:55,ids:[3,2,1]},{line:50,ids:[5,3,2,1]},{line:51,ids:[5,3,2,1]},{line:56,ids:[3,2,1]},{line:55,ids:[2,1]}];
for(let i=0;i<expected.length;i++){
 if(i)await page.locator('#next').click();
 assert.equal(await page.locator('.code-line.current').getAttribute('data-line'),String(expected[i].line),'LC104 下一步 '+i);
 assert.deepEqual(await page.locator('#stack [data-frame]').evaluateAll(nodes=>nodes.map(n=>Number(n.dataset.frame))),expected[i].ids);
 if(i===8){assert.match(await page.locator('#variables').textContent(),/leftHeight0/);assert.match(await page.locator('.stack-return').textContent(),/maxDepth\(\) = 0/);}
 if(i===12)assert.match(await page.locator('#variables').textContent(),/leftHeight1/);
}
const combination=traces.get(40);
await page.goto(base+'/'+combination.problem.url);
const vars=step=>Object.fromEntries(combination.data.pool[step.vars].map(([k,v])=>[combination.data.pool[k],combination.data.pool[v]]));
async function seek(position){await page.locator('#progress').evaluate((el,i)=>{el.value=i;el.dispatchEvent(new Event('input',{bubbles:true}));},position);}
// 做选择和撤销各执行一次，下一步立即显示真实的路径变化。
for(const [kind,before,after] of [['choose',[],[1]],['undo',[1,1],[1]]]){
 const position=combination.playback.stops.findIndex(raw=>{
  const step=combination.data.steps[raw];return step.kind===kind&&JSON.stringify(vars(step).path)===JSON.stringify(before);
 });
 assert.ok(position>=0,kind+' snapshot exists');await seek(position);await page.locator('#next').click();
 const pathText=await page.locator('#variables .variable').filter({has:page.locator('summary span', {hasText:/^path$/})}).locator('pre').textContent();
 assert.equal(pathText.replace(/\s/g,''),JSON.stringify(after));
}
await seek(combination.playback.stops.length-1);
assert.equal(await page.locator('#stack [data-frame]').count(),0,'完成后栈为空');
assert.match(await page.locator('#result').textContent(),/PASS/);
await page.screenshot({path:'.work/binarytree/previews/stepping-40-complete.png',fullPage:true});
assert.deepEqual(errors,[]);await browser.close();
fs.writeFileSync('.work/binarytree/logs/stepping-report.json',JSON.stringify({pages:report,errors},null,2)+'\n');
console.log(JSON.stringify(report.filter(r=>[104,106,40,77,332,37].includes(r.id)),null,2));
console.log('53 条轨迹顺序与返回覆盖校验、LC104 逐次递归/返回、LC40 加入/撤销同步 PASS。');
