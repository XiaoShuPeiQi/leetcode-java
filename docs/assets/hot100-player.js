(() => {
'use strict';
const data=window.HOT100_TRACE,$=id=>document.getElementById(id);
if(!data?.steps?.length){$('app').textContent='轨迹数据未加载。';return;}
document.body.classList.add('greedy-player', 'array-player');
const playback=window.ExecutionSteps.create(data),stops=playback.stops;
const esc=s=>String(s).replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
const kinds={enter:'进入调用',return:'返回',condition:'条件判断',loop:'推进循环',modify:'更新状态',statement:'执行语句'};
$('app').innerHTML=`<header class="topbar"><a class="brand" href="../../index.html"><span class="brand-symbol">⌘</span>展老师小课堂<span class="brand-en">LEETCODE LAB</span></a><nav><a href="../../index.html?chapter=${encodeURIComponent('Hot100')}">题目目录</a><a href="${esc(data.source)}" target="_blank" rel="noreferrer">飞书笔记 ↗</a></nav></header><main><section class="lesson"><div><div class="eyebrow">HOT100 / ${String(data.order).padStart(2,'0')} / ${data.order>20?40:20} · ${esc(data.group)} · ${String(data.id).padStart(4,'0')}</div><h1>${esc(data.title)}<small>${esc(data.english)}</small></h1><p class="lesson-input">${esc(data.input)}</p></div><div class="trace-label"><span class="status-dot"></span>Java 17 · PASS</div></section><footer class="controls"><div class="progress-row"><span id="progress-label"></span><input id="progress" aria-label="执行进度" type="range" min="0" max="${stops.length-1}" value="0"><span id="percent"></span></div><div class="button-row"><div class="control-group"><button id="reset">↺ 重置</button><button id="prev">← 上一步</button><button id="next" class="primary">下一步 →</button><button id="key">关键操作 ⇥</button><button id="play">▶ 播放</button></div><label class="speed">速度 <select id="speed" aria-label="播放速度"><option value="1400">0.5×</option><option value="700" selected>1×</option><option value="350">2×</option><option value="140">5×</option></select></label><span class="shortcut">← → 单步 · 空格播放</span></div></footer><div class="workspace"><section class="panel code-panel"><div class="panel-title"><span><i class="java-icon">J</i> ${esc(data.javaFile.split('/').at(-1))}</span><span id="line-number"></span></div><div id="code" class="code"></div></section><section class="panel state-panel"><div class="panel-title"><span>可视化状态</span><span id="state-title">JDI · 当前快照</span></div><div id="state" class="state-body"></div></section><section class="panel stack-panel"><div class="panel-title">调用栈 <span></span></div><div id="stack" class="stack"></div></section></div><section class="step-info"><span id="operation" class="operation"></span><strong id="step-title"></strong><p id="explanation"></p></section><section class="inspect"><section class="panel"><div class="panel-title">当前变量 <span id="frame-title"></span></div><div id="variables" class="variables"></div></section><section class="panel"><div class="panel-title">结果 <span id="result-count"></span></div><div id="result" class="result"></div><details class="verification"><summary>校验</summary><dl><dt>预期</dt><dd>${esc(data.expected)}</dd><dt>实际</dt><dd>${esc(data.actual)} · PASS</dd><dt>测试来源</dt><dd>${esc(data.testSource)}</dd></dl></details></section></section><details class="verification"><summary>轨迹与源码</summary><p>本页逐行回放代表性输入的真实 Java 17 / JDI 暂停快照，高亮语句尚未执行；执行后的变化出现在下一暂停位置。点击执行过的代码行设置播放断点。网页不运行任意Java输入。</p><p>${esc(data.sourceStatus)}</p></details></main>`;
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

function previousVars(){
 if(position===0)return {};
 const prev=data.steps[stops[position-1]],old=data.pool[prev.frames];
 return old.find(f=>f.id===selectedFrame)?.vars||{};
}
function pointers(name,vars){
 let keys=['left','right','mid','slow','fast','write','i','j','start','end','target'];
 if(data.id===238)keys=['i'];
 if(data.id===844)keys=name==='s'?['i']:name==='t'?['j']:[];
 if(data.id===76)keys=['left','right'];
 return keys.filter(k=>Number.isInteger(vars[k])).map(k=>[k,vars[k]]);
}
function vector(name,values,vars,old,label=name){
 const pts=pointers(name,vars);
 return '<div class="state-label">'+esc(label)+'</div><div class="state-array">'+values.map((v,i)=>{
  const names=pts.filter(([,value])=>value===i).map(([key])=>key),changed=Array.isArray(old)&&old[i]!==undefined&&old[i]!==v;
  return '<div class="state-cell '+(names.length?'pointer ':'')+(changed?'changed':'')+'">'+esc(v)+'<small>'+i+'</small>'+(names.length?'<span class="pointer-name">'+esc(names.join(' / '))+'</span>':'')+'</div>';
 }).join('')+'</div>'+(values.length?'':'<p class="empty">空数组</p>');
}
function matrix(name,values,vars,old,label=name){
 if(!values.length)return '<p class="state-caption">'+esc(label)+' = []</p>';
 const source=data.code[frames.find(f=>f.id===selectedFrame).line-data.displayStart]||'';
 const refs=[...source.matchAll(/(\w+)\[(\w+)\]\[(\w+)\]/g)].filter(m=>m[1]===name).map(m=>[vars[m[2]],vars[m[3]]]);
 return '<div class="state-label">'+esc(label)+'</div><div class="matrix-scroll"><table class="matrix"><thead><tr><th>行 / 列</th>'+values[0].map((_,j)=>'<th>'+j+'</th>').join('')+'</tr></thead><tbody>'+values.map((row,i)=>'<tr><th>'+i+'</th>'+row.map((v,j)=>{
  const changed=Array.isArray(old?.[i])&&old[i][j]!==v;
  const active=refs.some(([row,col])=>row===i&&col===j)||(data.id===240&&vars.row===i&&vars.col===j);
  return '<td data-row="'+i+'" data-col="'+j+'" class="matrix-cell '+(active?'active ':'')+(changed?'changed':'')+'">'+esc(v)+'</td>';
 }).join('')+'</tr>').join('')+'</tbody></table></div>';
}
function heightChart(values,vars){
 const max=Math.max(1,...values),width=Math.max(350,values.length*38+20);
 return '<svg class="height-chart" viewBox="0 0 '+width+' 185" role="img" aria-label="柱子高度与左右指针">'+values.map((h,i)=>{
  const x=15+i*38,y=140-h/max*105,active=i===vars.left||i===vars.right;
  return '<rect x="'+x+'" y="'+y+'" width="25" height="'+(140-y)+'" fill="'+(active?'var(--accent)':'var(--muted)')+'" opacity=".75"/><text x="'+(x+12)+'" y="'+(y-6)+'" text-anchor="middle">'+h+'</text><text x="'+(x+12)+'" y="158" text-anchor="middle">'+i+'</text>'+(active?'<text x="'+(x+12)+'" y="178" text-anchor="middle">'+(i===vars.left?'left':'right')+'</text>':'');
 }).join('')+'</svg>';
}
function drawState(step,vars){
 if(window.Hot100Structures?.draw($('state'), data, heap, vars, data.pool[step.roots]))return;
 const old=previousVars(),shown=new Set();
 let html='<p class="state-caption array-meaning">'+esc(data.stateMeaning)+'</p><div class="array-legend"><span>下方标签：当前指针</span><span>着色：较上一暂停位置改变</span></div>';
 if([11,42].includes(data.id))html+=heightChart(data.inputObject.height,vars);
 function view(name,value,original=false){
  const label=name+(original?' · 原始输入':' · 当前值');
  if(Array.isArray(value))html+=Array.isArray(value[0])?matrix(name,value,vars,old[name],label):vector(name,value,vars,old[name],label);
  else if(typeof value==='string')html+=vector(name,Array.from(value),vars,undefined,label);
 }
 for(const [name,input] of Object.entries(data.inputObject)){
  if(Array.isArray(input)||typeof input==='string'){
   const has=Object.hasOwn(vars,name);view(name,has?vars[name]:input,!has);shown.add(name);
  }
 }
 for(const [name,value] of Object.entries(vars)){
  if(shown.has(name))continue;
  if((name==='need'||(data.id===438&&name==='window'))&&Array.isArray(value)){
   const chars=[...new Set(Array.from(data.inputObject.t||data.inputObject.p||''))];
   html+='<div class="state-label">'+esc(data.id===438?name+' · 字母频次':'need · 目标字符欠账（负数表示多余）')+'</div><div class="state-array">'+chars.map(c=>'<div class="state-cell">'+esc(c)+'<small>'+value[c.charCodeAt(0)-(data.id===438?97:0)]+'</small></div>').join('')+'</div>';
  }else if(value?.$map){
   html+='<div class="state-label">'+esc(name)+' · 键 → 值</div><div class="state-array">'+value.$map.map(([k,v])=>'<div class="state-cell">'+esc(k)+'<small>→ '+esc(JSON.stringify(v))+'</small></div>').join('')+'</div>';
  }else if(Array.isArray(value)||typeof value==='string')view(name,value);
 }
 const scalars=Object.entries(vars).filter(([,v])=>typeof v==='number'||typeof v==='boolean');
 html+='<div class="scalar-strip">'+scalars.map(([k,v])=>'<div class="scalar-chip">'+esc(k)+' = <strong>'+esc(v)+'</strong></div>').join('')+'</div>';
 html+='<p class="state-caption">'+esc(data.transition)+'</p>';
 $('state').innerHTML=html;
}

function render(){const step=data.steps[index],event=playback.events[position];heap=data.pool[step.heap];frames=data.pool[step.frames];if(!frames.some(f=>f.id===selectedFrame))selectedFrame=frames.at(-1).id;const frame=frames.find(f=>f.id===selectedFrame);
 document.querySelector('.code-line.current')?.classList.remove('current');const row=document.querySelector('[data-line="'+frame.line+'"]');if(row){row.classList.add('current');const panel=$('code');if(row.offsetTop<panel.scrollTop||row.offsetTop+row.offsetHeight>panel.scrollTop+panel.clientHeight)panel.scrollTop=row.offsetTop-panel.clientHeight/2;}
 $('line-number').textContent='L'+frame.line+(event.complete?' · 完成':' · 待执行');$('operation').className='operation '+step.kind;$('operation').textContent=event.complete?'执行完成':kinds[step.kind];$('step-title').textContent=step.method+'() · 第 '+step.line+' 行';const ret=step.returnValue<0?undefined:data.pool[step.returnValue];$('explanation').textContent=event.returns.length?event.returns.map(r=>'↩ '+r.method+'() = '+brief(r.value)).join(' · '):'';
 $('frame-title').textContent=frame.method+'()';variables(frame.vars);drawState(step,frame.vars);
 renderStack({frames:event.complete?[]:frames,selectedId:selectedFrame,phase:event.entered?'enter':'line',returns:event.returns,format:brief,onSelect:id=>{selectedFrame=id;render();}});
 const vars=frames.at(-1).vars,result=event.complete?ret:vars.res??vars.result;
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
