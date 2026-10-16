(() => {
'use strict';
const data=window.DP_TRACE,$=id=>document.getElementById(id);
if(!data?.steps?.length){$('app').textContent='轨迹数据未加载。';return;}
document.body.classList.add('greedy-player');
const playback=window.ExecutionSteps.create(data),stops=playback.stops;
const esc=s=>String(s).replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
const kinds={enter:'进入调用',return:'返回',condition:'条件判断',loop:'推进循环',modify:'更新状态',statement:'执行语句'};
$('app').innerHTML=`<header class="topbar"><a class="brand" href="../../index.html"><span class="brand-symbol">⌘</span>展老师小课堂<span class="brand-en">LEETCODE LAB</span></a><nav><a href="../../index.html?chapter=${encodeURIComponent('动态规划')}">题目目录</a><a href="${esc(data.source)}" target="_blank" rel="noreferrer">飞书笔记 ↗</a></nav></header><main><section class="lesson"><div><div class="eyebrow">DYNAMIC PROGRAMMING / ${String(data.id).padStart(4,'0')} · 动态规划</div><h1>${esc(data.title)}<small>${esc(data.english)}</small></h1><p class="lesson-input">${esc(data.input)}</p></div><div class="trace-label"><span class="status-dot"></span>Java 17 · PASS</div></section><footer class="controls"><div class="progress-row"><span id="progress-label"></span><input id="progress" aria-label="执行进度" type="range" min="0" max="${stops.length-1}" value="0"><span id="percent"></span></div><div class="button-row"><div class="control-group"><button id="reset">↺ 重置</button><button id="prev">← 上一步</button><button id="next" class="primary">下一步 →</button><button id="key">关键操作 ⇥</button><button id="play">▶ 播放</button></div><label class="speed">速度 <select id="speed" aria-label="播放速度"><option value="1400">0.5×</option><option value="700" selected>1×</option><option value="350">2×</option><option value="140">5×</option></select></label><span class="shortcut">← → 单步 · 空格播放</span></div></footer><div class="workspace"><section class="panel code-panel"><div class="panel-title"><span><i class="java-icon">J</i> ${esc(data.javaFile.split('/').at(-1))}</span><span id="line-number"></span></div><div id="code" class="code"></div></section><section class="panel state-panel"><div class="panel-title"><span>可视化状态</span><span id="state-title">JDI · 当前快照</span></div><div id="state" class="state-body"></div></section><section class="panel stack-panel"><div class="panel-title">调用栈 <span></span></div><div id="stack" class="stack"></div></section></div><section class="step-info"><span id="operation" class="operation"></span><strong id="step-title"></strong><p id="explanation"></p></section><section class="inspect"><section class="panel"><div class="panel-title">当前变量 <span id="frame-title"></span></div><div id="variables" class="variables"></div></section><section class="panel"><div class="panel-title">结果 <span id="result-count"></span></div><div id="result" class="result"></div><details class="verification"><summary>校验</summary><dl><dt>预期</dt><dd>${esc(data.expected)}</dd><dt>实际</dt><dd>${esc(data.actual)} · PASS</dd><dt>测试来源</dt><dd>${esc(data.testSource)}</dd></dl></details></section></section><details class="verification"><summary>轨迹与源码</summary><p>本页逐行回放代表性输入的真实 Java 17 / JDI 暂停快照，高亮语句尚未执行；执行后的变化出现在下一暂停位置。点击执行过的代码行设置播放断点。网页不运行任意Java输入。</p><p>${esc(data.sourceStatus)}</p></details></main>`;
function highlight(s){const tokens=/(\/\/.*$|"(?:\\.|[^"\\])*"|\b(?:public|private|static|class|void|int|boolean|long|double|char|return|if|else|for|while|continue|break|new|true|false|null|final)\b|\b\d+\b)/g;let html='',end=0;for(const m of s.matchAll(tokens)){html+=esc(s.slice(end,m.index));html+='<span class="'+(m[0].startsWith('//')?'syntax-comment':m[0].startsWith('"')?'syntax-string':/^\d/.test(m[0])?'syntax-number':'syntax-keyword')+'">'+esc(m[0])+'</span>';end=m.index+m[0].length;}return html+esc(s.slice(end));}
$('code').innerHTML=data.code.map((line,i)=>'<div class="code-line" data-line="'+(data.displayStart+i)+'" tabindex="0" role="button" aria-label="第'+(data.displayStart+i)+'行，切换播放断点"><span class="gutter">'+(data.displayStart+i)+'</span><code>'+highlight(line.replace(/^ {4}/,''))+'</code></div>').join('');
let position=0,index=stops[0],timer=null,selectedFrame=null,heap={},frames=[];
const breakpoints=new Set(),renderStack=window.StackView.create($('stack'),data.code);
const ref=x=>x?.$ref===undefined?null:String(x.$ref);
function readable(x,seen=new Set()){const id=ref(x);if(id!==null){const node=heap[id];if(!node||seen.has(id))return '#'+id;const next=new Set(seen);next.add(id);return Object.fromEntries([['节点','#'+id],...Object.entries(node).filter(([k])=>k!=='$type').map(([k,v])=>[k,readable(v,next)])]);}if(Array.isArray(x))return x.map(v=>readable(v,seen));return x;}
function brief(x){if(ref(x)!==null)return '#'+ref(x);const text=JSON.stringify(x);return text?.length>55?text.slice(0,52)+'…':text;}
function variables(vars){$('variables').innerHTML=Object.entries(vars).map(([k,v])=>v===null||typeof v!=='object'?'<div class="variable scalar"><span>'+esc(k)+'</span><b>'+esc(brief(v))+'</b></div>':'<details class="variable"><summary><span>'+esc(k)+'</span><b>'+esc(brief(v))+'</b></summary><pre>'+esc(JSON.stringify(readable(v),null,2))+'</pre></details>').join('')||'<p class="empty">—</p>';}
function array(name,values,active=-1,cover=-1){if(!Array.isArray(values))return '';return '<div class="state-label">'+esc(name)+'</div><div class="state-array">'+values.map((v,i)=>'<div class="state-cell '+(i===active?'active ':'')+(i<=cover?'covered':'')+'">'+esc(Array.isArray(v)?JSON.stringify(v):v)+'<small>'+i+'</small></div>').join('')+'</div>';}
// The highlighted Java line is pending execution; snapshots are never recomputed in JS.
function expression(source,vars){
 const s=source.replace(/\s/g,'');
 if(!/^[a-zA-Z0-9_+*\-]+$/.test(s))return null;
 const parts=s.match(/[+-]?[^+-]+/g)||[];let result=0;
 for(const part of parts){let sign=1,t=part;if(t[0]==='-'){sign=-1;t=t.slice(1);}else if(t[0]==='+')t=t.slice(1);let value=1;
  for(const factor of t.split('*')){const n=/^\d+$/.test(factor)?Number(factor):vars[factor];if(typeof n!=='number')return null;value*=n;}
  result+=sign*value;
 }return Number.isInteger(result)?result:null;
}
function dpReferences(vars){
 const line=data.code[frames.find(f=>f.id===selectedFrame).line-data.displayStart]||'',refs=[];
 for(const m of line.matchAll(/dp\[([^\]]+)\](?:\[([^\]]+)\])?/g)){
  const i=expression(m[1],vars),j=m[2]===undefined?null:expression(m[2],vars);
  if(i!==null&&(m[2]===undefined||j!==null))refs.push({i,j,write:/^\s*(?:\[[^\]]+\])*\s*(?:[+|]?=)(?!=)/.test(line.slice(m.index+m[0].length))});
 }return refs;
}
function table(name,values,vars,refs){
 if(!Array.isArray(values)||!values.length)return '';
 const matrix=Array.isArray(values[0]),rows=matrix?values:[values],columns=rows[0].length;
 const stock={121:['持股','空仓'],122:['持股','空仓'],123:['不操作','买1','卖1','买2','卖2'],309:['持股','休息','刚卖'],714:['持股','空仓']};
 const columnLabels=data.id===188?Array.from({length:columns},(_,j)=>j===0?'不操作':(j%2?'买':'卖')+Math.ceil(j/2)):stock[data.id];
 let previous;
 if(position>0){const prev=data.steps[stops[position-1]],ff=data.pool[prev.frames].find(f=>f.id===selectedFrame);previous=ff?.vars?.[name];}
 const label=(v)=>v===-1073741824?'−∞':v===2147483647?'≥2³¹−1':typeof v==='boolean'?(v?'T':'F'):String(v);
 let out='<div class="state-label">'+esc(name)+' · '+(matrix?rows.length+' × '+columns:columns+' 个状态')+'</div><div class="dp-scroll"><table class="dp-table"><thead><tr><th>'+(matrix?'i / j':'j')+'</th>';
 out+=Array.from({length:columns},(_,j)=>'<th>'+esc(columnLabels?.[j]??j)+'</th>').join('')+'</tr></thead><tbody>';
 rows.forEach((row,i)=>{out+='<tr><th>'+(matrix?i:'值')+'</th>';row.forEach((value,j)=>{
  const hit=refs.filter(r=>matrix?r.i===i&&r.j===j:r.i===j),write=hit.some(r=>r.write),read=hit.length&&!write;
  const old=matrix?previous?.[i]?.[j]:previous?.[j],changed=old!==undefined&&old!==value;
  out+='<td data-row="'+i+'" data-col="'+j+'" class="'+(write?'dp-target ':read?'dp-read ':'')+(changed?'dp-changed':'')+'" title="'+esc(name+(matrix?'['+i+']['+j+']':'['+j+']'))+'">'+esc(label(value))+'</td>';
 });out+='</tr>';});return out+'</tbody></table></div>';
}
function dpTree(step,vars){
 const root=ref(data.pool[step.roots].root);if(root===null)return '<p class="empty">当前为空节点，返回[0,0]。</p>';
 const states=data.pool[step.states],positions=[],edges=[],seen=new Set();let leaf=0;
 function visit(id,depth){if(seen.has(id)||!heap[id])return null;seen.add(id);const n=heap[id],children=[ref(n.left),ref(n.right)].filter(x=>x!==null),xx=children.map(c=>visit(c,depth+1)).filter(x=>x!==null);const x=xx.length?(xx[0]+xx.at(-1))/2:55+leaf++*115;positions.push({id,x,y:32+depth*85});children.forEach(c=>edges.push([id,c]));return x;}visit(root,0);
 const w=Math.max(250,...positions.map(p=>p.x+55)),h=Math.max(...positions.map(p=>p.y+68)),by=new Map(positions.map(p=>[p.id,p]));let out='<svg class="dp-tree" viewBox="0 0 '+w+' '+h+'" role="img" aria-label="树形DP：各子树不偷与偷当前节点的收益">';
 for(const [a,b] of edges){const p=by.get(a),q=by.get(b);if(p&&q)out+='<line x1="'+p.x+'" y1="'+p.y+'" x2="'+q.x+'" y2="'+q.y+'" stroke="var(--canvas-edge)"/>';}
 for(const p of positions){const active=ref(vars.node??vars.root)===p.id;out+='<circle cx="'+p.x+'" cy="'+p.y+'" r="22" fill="'+(active?'var(--canvas-active)':'var(--canvas-fill)')+'" stroke="'+(active?'var(--accent)':'var(--canvas-border)')+'"/><text x="'+p.x+'" y="'+(p.y+4)+'" text-anchor="middle">'+heap[p.id].val+'</text><text x="'+p.x+'" y="'+(p.y+40)+'" text-anchor="middle">'+(states[p.id]===undefined?'待返回':'['+states[p.id].join(', ')+']')+'</text>';}
 return out+'</svg><p class="state-caption">每个返回值为[不偷本节点, 偷本节点]。节点显示真实值，相同值仍是不同节点；标签来自实际递归返回。</p>';
}
function drawState(step,vars){
 let html='<p class="state-caption dp-meaning">'+esc(data.stateMeaning)+'</p>';
 html+='<div class="dp-legend"><span class="target">待写入</span><span class="read">读取来源</span><span class="changed">上一步改变</span></div>';
 if(data.id===337){html+=dpTree(step,vars);for(const key of ['left','right','result'])if(Array.isArray(vars[key]))html+=array(key+' · [不偷,偷]',vars[key]);}
 else{
  for(const [key,value] of Object.entries(data.inputObject)){
   if(Array.isArray(value)&&!Array.isArray(value[0]))html+=array(key,value,vars.i??-1);
   else if(typeof value==='string')html+=array(key+' · 字符下标',Array.from(value));
  }
  if(Array.isArray(data.inputObject.obstacleGrid))html+=table('obstacleGrid',data.inputObject.obstacleGrid,vars,[]);
  if(Array.isArray(vars.dp))html+=table('dp',vars.dp,vars,dpReferences(vars));
  else html+='<p class="empty">此暂停位置尚未初始化dp。</p>';
 }
 const scalars=Object.entries(vars).filter(([,v])=>typeof v==='number'||typeof v==='boolean');
 if(scalars.length)html+='<p class="state-caption">'+scalars.map(([k,v])=>esc(k)+' = '+v).join(' · ')+'</p>';
 html+='<p class="state-caption">'+esc(data.transition)+'</p>';
 $('state').innerHTML=html;
}

function render(){const step=data.steps[index],event=playback.events[position];heap=data.pool[step.heap];frames=data.pool[step.frames];if(!frames.some(f=>f.id===selectedFrame))selectedFrame=frames.at(-1).id;const frame=frames.find(f=>f.id===selectedFrame);
 document.querySelector('.code-line.current')?.classList.remove('current');const row=document.querySelector('[data-line="'+frame.line+'"]');if(row){row.classList.add('current');const panel=$('code');if(row.offsetTop<panel.scrollTop||row.offsetTop+row.offsetHeight>panel.scrollTop+panel.clientHeight)panel.scrollTop=row.offsetTop-panel.clientHeight/2;}
 $('line-number').textContent='L'+frame.line+(event.complete?' · 完成':' · 待执行');$('operation').className='operation '+step.kind;$('operation').textContent=event.complete?'执行完成':kinds[step.kind];$('step-title').textContent=step.method+'() · 第 '+step.line+' 行';const ret=step.returnValue<0?undefined:data.pool[step.returnValue];$('explanation').textContent=event.returns.length?event.returns.map(r=>'↩ '+r.method+'() = '+brief(r.value)).join(' · '):'';
 $('frame-title').textContent=frame.method+'()';variables(frame.vars);drawState(step,frame.vars);
 renderStack({frames:event.complete?[]:frames,selectedId:selectedFrame,phase:event.entered?'enter':'line',returns:event.returns,format:brief,onSelect:id=>{selectedFrame=id;render();}});
 const vars=frames.at(-1).vars,result=event.complete?ret:vars.result;
 $('result').innerHTML=event.complete?'<pre>'+esc(data.actual)+'</pre><div class="pass">✓ PASS</div>':result===undefined?'<p class="empty">—</p>':'<pre>'+esc(JSON.stringify(readable(result),null,2))+'</pre>';$('result-count').textContent=event.complete?'最终返回':'当前状态';
 $('progress').value=position;$('progress-label').textContent=(position+1)+' / '+stops.length;$('percent').textContent=Math.round(position/Math.max(1,stops.length-1)*100)+'%';$('prev').disabled=position===0;$('next').disabled=$('key').disabled=position===stops.length-1;
}
function pause(){clearInterval(timer);timer=null;$('play').textContent='▶ 播放';}
function seek(value){position=Math.max(0,Math.min(stops.length-1,value));index=stops[position];selectedFrame=null;render();}
function play(){if(timer){pause();return;}if(position===stops.length-1)seek(0);$('play').textContent='Ⅱ 暂停';timer=setInterval(()=>{if(position>=stops.length-1){pause();return;}seek(position+1);if(breakpoints.has(data.steps[index].line)){pause();$('explanation').textContent='命中播放断点';}},Number($('speed').value));}
$('prev').onclick=()=>{pause();seek(position-1);};$('next').onclick=()=>{pause();seek(position+1);};$('reset').onclick=()=>{pause();seek(0);};$('play').onclick=play;
$('key').onclick=()=>{pause();let target=position+1;while(target<stops.length-1&&!playback.events[target].entered&&!playback.events[target].returns.length&&!['modify','return'].includes(data.steps[stops[target]].kind))target++;seek(target);};
$('speed').onchange=()=>{if(timer){pause();play();}};$('progress').oninput=e=>{pause();seek(Number(e.target.value));};
function breakpoint(line){if(!stops.some(i=>data.steps[i].line===line))return;breakpoints.has(line)?breakpoints.delete(line):breakpoints.add(line);const row=document.querySelector('[data-line="'+line+'"]');row.classList.toggle('breakpoint',breakpoints.has(line));row.setAttribute('aria-pressed',String(breakpoints.has(line)));}
$('code').onclick=e=>{const row=e.target.closest('[data-line]');if(row)breakpoint(Number(row.dataset.line));};$('code').onkeydown=e=>{if(e.key==='Enter'||e.key===' '){const row=e.target.closest('[data-line]');if(row){e.preventDefault();e.stopPropagation();breakpoint(Number(row.dataset.line));}}};
window.addEventListener('keydown',e=>{if(['INPUT','SELECT','TEXTAREA'].includes(e.target.tagName))return;if(e.key==='ArrowRight'){e.preventDefault();$('next').click();}else if(e.key==='ArrowLeft'){e.preventDefault();$('prev').click();}else if(e.code==='Space'){e.preventDefault();play();}else if(e.key.toLowerCase()==='k')$('key').click();else if(e.key==='Home')$('reset').click();});
window.addEventListener('resize',render);document.addEventListener('visibilitychange',()=>{if(document.hidden)pause();});window.ClassroomTheme.mount();render();
})();
