import fs from 'node:fs';
import vm from 'node:vm';
import crypto from 'node:crypto';
import assert from 'node:assert/strict';
const read=file=>{const c={window:{}};vm.runInNewContext(fs.readFileSync(file,'utf8'),c);return c.window;};
const catalog=read('docs/assets/catalog.js').CATALOG;
const report=[];
for(const p of catalog){
 if(!p.complete||p.chapter!=='回溯')continue;
 const data=read('docs/'+p.url+'trace.js').TRACE;
 const source=fs.readFileSync(data.javaFile,'utf8');
 assert.equal(data.sourceHash,crypto.createHash('sha256').update(source).digest('hex'),'Java 修改后必须重新采集');
 const vals=ref=>Object.fromEntries(data.pool[ref].map(([k,v])=>[data.pool[k],data.pool[v]]));
 const active=[];
 let saves=0;
 for(const [i,s] of data.steps.entries()){
  assert.ok(s.line>=data.displayStart&&s.line<data.displayStart+data.code.length);
  assert.ok(data.nodes[s.active].born<=i);
  const stack=data.pool[s.stack].map(ref=>data.pool[ref]);
  assert.ok(stack.length>0);
  if(s.phase==='enter'){if(active.length)assert.equal(stack.length,active.length+1);active.push(stack.at(-1).id);}
  else if(s.phase==='exit'){assert.equal(active.pop(),stack.at(-1).id);if(stack.length>1){const next=data.steps[i+1];assert.ok(next);}}
  else assert.equal(stack.map(f=>f.id).join(','),active.join(','));
  if(s.kind==='save'&&s.phase==='line')saves++;
 }
 assert.equal(active.length,0);
 assert.equal(data.validation.enter,data.validation.exit);
 for(const n of data.nodes){if(n.parent!==null){assert.ok(n.parent<n.id);assert.ok(data.nodes[n.parent].born<=n.born);}if(n.kind==='prune')assert.equal(n.method,undefined);}
 const javaPrint=x=>Array.isArray(x)?'['+x.map(javaPrint).join(', ')+']':String(x);
 const final=vals(data.steps.at(-1).vars);
 assert.equal(javaPrint(p.id===37?final.board:data.finalReturn),data.actual);
 if(p.id!==332&&p.id!==37)assert.equal(saves,final.result.length);
 if(p.id===51)assert.equal(data.nodes.filter(n=>n.kind==='prune').length,44);
 report.push({id:p.id,title:p.title,steps:data.steps.length,nodes:data.nodes.length,source:'一致',trace:'PASS'});
}
fs.writeFileSync('.work/visual-debug/logs/verification.json',JSON.stringify(report,null,2));
console.log(JSON.stringify(report,null,2));
