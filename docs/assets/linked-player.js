(() => {
'use strict';
const data=window.LINKED_TRACE,$=id=>document.getElementById(id);
if(!data?.steps?.length){$('app').textContent='轨迹数据未加载。';return;}
document.body.classList.add('greedy-player');
const playback=window.ExecutionSteps.create(data),stops=playback.stops;
const esc=s=>String(s).replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
const kinds={enter:'进入调用',return:'返回',condition:'条件判断',loop:'推进循环',modify:'更新状态',statement:'执行语句'};
$('app').innerHTML=`<header class="topbar"><a class="brand" href="../../index.html"><span class="brand-symbol">⌘</span>展老师小课堂<span class="brand-en">LEETCODE LAB</span></a><nav><a href="../../index.html?chapter=${encodeURIComponent('链表')}">题目目录</a><a href="${esc(data.source)}" target="_blank" rel="noreferrer">飞书笔记 ↗</a></nav></header><main><section class="lesson"><div><div class="eyebrow">LINKED LIST / ${String(data.id).padStart(4,'0')} · 链表</div><h1>${esc(data.title)}<small>${esc(data.english)}</small></h1><p class="lesson-input">${esc(data.input)}</p></div><div class="trace-label"><span class="status-dot"></span>Java 17 · PASS</div></section><footer class="controls"><div class="progress-row"><span id="progress-label"></span><input id="progress" aria-label="执行进度" type="range" min="0" max="${stops.length-1}" value="0"><span id="percent"></span></div><div class="button-row"><div class="control-group"><button id="reset">↺ 重置</button><button id="prev">← 上一步</button><button id="next" class="primary">下一步 →</button><button id="key">关键操作 ⇥</button><button id="play">▶ 播放</button></div><label class="speed">速度 <select id="speed" aria-label="播放速度"><option value="1400">0.5×</option><option value="700" selected>1×</option><option value="350">2×</option><option value="140">5×</option></select></label><span class="shortcut">← → 单步 · 空格播放</span></div></footer><div class="workspace"><section class="panel code-panel"><div class="panel-title"><span><i class="java-icon">J</i> ${esc(data.javaFile.split('/').at(-1))}</span><span id="line-number"></span></div><div id="code" class="code"></div></section><section class="panel state-panel"><div class="panel-title"><span>可视化状态</span><span id="state-title">JDI · 当前快照</span></div><div id="state" class="state-body"></div></section><section class="panel stack-panel"><div class="panel-title">调用栈 <span></span></div><div id="stack" class="stack"></div></section></div><section class="step-info"><span id="operation" class="operation"></span><strong id="step-title"></strong><p id="explanation"></p></section><section class="inspect"><section class="panel"><div class="panel-title">当前变量 <span id="frame-title"></span></div><div id="variables" class="variables"></div></section><section class="panel"><div class="panel-title">结果 <span id="result-count"></span></div><div id="result" class="result"></div><details class="verification"><summary>校验</summary><dl><dt>预期</dt><dd>${esc(data.expected)}</dd><dt>实际</dt><dd>${esc(data.actual)} · PASS</dd><dt>测试来源</dt><dd>${esc(data.testSource)}</dd></dl></details></section></section><details class="verification"><summary>轨迹与源码</summary><p>本页逐行回放代表性输入的真实 Java 17 / JDI 暂停快照，高亮语句尚未执行；执行后的变化出现在下一暂停位置。点击执行过的代码行设置播放断点。网页不运行任意Java输入。</p><p>${esc(data.sourceStatus)}</p></details></main>`;
function highlight(s){const tokens=/(\/\/.*$|"(?:\\.|[^"\\])*"|\b(?:public|private|static|class|void|int|boolean|long|double|char|return|if|else|for|while|continue|break|new|true|false|null|final)\b|\b\d+\b)/g;let html='',end=0;for(const m of s.matchAll(tokens)){html+=esc(s.slice(end,m.index));html+='<span class="'+(m[0].startsWith('//')?'syntax-comment':m[0].startsWith('"')?'syntax-string':/^\d/.test(m[0])?'syntax-number':'syntax-keyword')+'">'+esc(m[0])+'</span>';end=m.index+m[0].length;}return html+esc(s.slice(end));}
$('code').innerHTML=data.code.map((line,i)=>'<div class="code-line" data-line="'+(data.displayStart+i)+'" tabindex="0" role="button" aria-label="第'+(data.displayStart+i)+'行，切换播放断点"><span class="gutter">'+(data.displayStart+i)+'</span><code>'+highlight(line.replace(/^ {4}/,''))+'</code></div>').join('');
let position=0,index=stops[0],timer=null,selectedFrame=null,heap={},frames=[];
const breakpoints=new Set(),renderStack=window.StackView.create($('stack'),data.code);
const ref=x=>x?.$ref===undefined?null:String(x.$ref);
function readable(x,seen=new Set()){const id=ref(x);if(id!==null){const node=heap[id];if(!node||seen.has(id))return '#'+id;const next=new Set(seen);next.add(id);return Object.fromEntries([['节点','#'+id],...Object.entries(node).filter(([k])=>k!=='$type').map(([k,v])=>[k,readable(v,next)])]);}if(Array.isArray(x))return x.map(v=>readable(v,seen));return x;}
function brief(x){if(ref(x)!==null)return '#'+ref(x);const text=JSON.stringify(x);return text?.length>55?text.slice(0,52)+'…':text;}
function variables(vars){$('variables').innerHTML=Object.entries(vars).map(([k,v])=>v===null||typeof v!=='object'?'<div class="variable scalar"><span>'+esc(k)+'</span><b>'+esc(brief(v))+'</b></div>':'<details class="variable"><summary><span>'+esc(k)+'</span><b>'+esc(brief(v))+'</b></summary><pre>'+esc(JSON.stringify(readable(v),null,2))+'</pre></details>').join('')||'<p class="empty">—</p>';}

function drawState(step,vars){
 const ids=data.nodeIds.filter(id=>heap[id]&&Object.hasOwn(heap[id],'val'));
 const labels={},nulls=[];
 for(const [name,v] of Object.entries(vars)){
  const id=ref(v);
  if(id!==null&&heap[id]&&Object.hasOwn(heap[id],'val'))(labels[id]??=[]).push(name);
  else if(v===null)nulls.push(name);
 }
 const positions=Object.fromEntries(data.nodeIds.map((id,i)=>[id,{x:86+(i%4)*142,y:90+Math.floor(i/4)*150}]));
 const height=Math.max(220,...ids.map(id=>positions[id].y+105));
 let svg='<svg viewBox="0 0 605 '+height+'" role="img" aria-label="链表节点身份、next连线与当前指针"><defs><marker id="arrow-next" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="6" markerHeight="6" orient="auto-start-reverse"><path d="M0 0L10 5L0 10Z" fill="var(--accent)"/></marker></defs>';
 for(const id of ids){const p=positions[id],next=ref(heap[id].next),q=positions[next];
  if(next!==null&&heap[next]&&q){
   let path;
   if(next===id)path='M'+(p.x+22)+' '+p.y+' C'+(p.x+70)+' '+(p.y-75)+' '+(p.x-70)+' '+(p.y-75)+' '+(p.x-22)+' '+p.y;
   else if(q.y===p.y&&q.x>p.x&&q.x-p.x<150)path='M'+(p.x+26)+' '+p.y+' L'+(q.x-27)+' '+q.y;
   else if(q.y===p.y&&q.x>p.x)path='M'+(p.x+26)+' '+p.y+' C'+(p.x+60)+' '+(p.y-78)+' '+(q.x-60)+' '+(q.y-78)+' '+(q.x-27)+' '+q.y;
   else if(q.y===p.y)path='M'+p.x+' '+(p.y+27)+' Q'+((p.x+q.x)/2)+' '+(p.y+90)+' '+q.x+' '+(q.y+27);
   else path='M'+p.x+' '+(p.y+27)+' C'+p.x+' '+(p.y+80)+' '+q.x+' '+(q.y-75)+' '+q.x+' '+(q.y-27);
   svg+='<path data-next-from="'+id+'" data-next-to="'+next+'" d="'+path+'" fill="none" stroke="var(--accent)" stroke-width="2" marker-end="url(#arrow-next)"/>';
  }else svg+='<text x="'+(p.x+32)+'" y="'+(p.y+5)+'" fill="var(--muted)">→ null</text>';
 }
 for(const id of ids){const p=positions[id],names=labels[id]||[],node=heap[id];
  svg+='<g data-node="'+id+'"><title>'+esc(names.join(', '))+' · 节点#'+id+'</title><circle cx="'+p.x+'" cy="'+p.y+'" r="26" fill="'+(names.length?'var(--canvas-active)':'var(--canvas-fill)')+'" stroke="'+(names.length?'var(--accent)':'var(--canvas-border)')+'" stroke-width="2"/><text x="'+p.x+'" y="'+(p.y+5)+'" text-anchor="middle">'+esc(node.val)+'</text><text x="'+p.x+'" y="'+(p.y+48)+'" text-anchor="middle" fill="var(--muted)">#'+id+'</text>';
  names.slice(0,3).forEach((name,i)=>svg+='<text x="'+p.x+'" y="'+(p.y-38-(Math.min(3,names.length)-1-i)*14)+'" text-anchor="middle" fill="var(--accent)">'+esc(name)+'</text>');
  svg+='</g>';
 }
 svg+='</svg>';
 const scalar=Object.entries(vars).filter(([,v])=>typeof v==='number'||typeof v==='boolean');
 $('state').innerHTML=svg+'<p class="state-caption">圆内是val，#编号标识同一个Java对象；箭头表示当前next。局部引用移动不会自动改线。'+(nulls.length?' 当前为null：'+esc(nulls.join(', '))+'。':'')+'</p>'+(scalar.length?'<p class="state-caption">'+scalar.map(([k,v])=>esc(k)+' = '+esc(v)).join(' · ')+'</p>':'');
}
function render(){const step=data.steps[index],event=playback.events[position];heap=data.pool[step.heap];frames=data.pool[step.frames];if(!frames.some(f=>f.id===selectedFrame))selectedFrame=frames.at(-1).id;const frame=frames.find(f=>f.id===selectedFrame);
 document.querySelector('.code-line.current')?.classList.remove('current');const row=document.querySelector('[data-line="'+frame.line+'"]');if(row){row.classList.add('current');const panel=$('code');if(row.offsetTop<panel.scrollTop||row.offsetTop+row.offsetHeight>panel.scrollTop+panel.clientHeight)panel.scrollTop=row.offsetTop-panel.clientHeight/2;}
 $('line-number').textContent='L'+frame.line+(event.complete?' · 完成':' · 待执行');$('operation').className='operation '+step.kind;$('operation').textContent=event.complete?'执行完成':kinds[step.kind];$('step-title').textContent=step.method+'() · 第 '+step.line+' 行';const ret=step.returnValue<0?undefined:data.pool[step.returnValue];$('explanation').textContent=event.returns.length?event.returns.map(r=>'↩ '+r.method+'() = '+brief(r.value)).join(' · '):'';
 $('frame-title').textContent=frame.method+'()';variables(frame.vars);drawState(step,frame.vars);
 renderStack({frames:event.complete?[]:frames,selectedId:selectedFrame,phase:event.entered?'enter':'line',returns:event.returns,format:brief,onSelect:id=>{selectedFrame=id;render();}});
 const vars=frames.at(-1).vars,result=event.complete?ret:vars.result??vars.sum??vars.steps??vars.cameras;
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
