(() => {
'use strict';
const data=window.TREE_TRACE,$=id=>document.getElementById(id);
if(!data?.steps?.length){$('app').textContent='轨迹数据未加载。';return;}
const playback=window.ExecutionSteps.create(data),stops=playback.stops;
const esc=s=>String(s).replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
const kinds={enter:'进入调用',return:'返回',condition:'条件判断',loop:'推进循环',choose:'加入路径',undo:'撤销路径',save:'收集结果',modify:'修改树',statement:'执行语句'};
$('app').innerHTML=`<header class="topbar"><a class="brand" href="../../index.html"><span class="brand-symbol">⌘</span>展老师小课堂<span class="brand-en">LEETCODE LAB</span></a><nav><a href="../../index.html">题目目录</a><a href="${esc(data.source)}" target="_blank" rel="noreferrer">飞书笔记 ↗</a></nav></header><main><section class="lesson"><div><div class="eyebrow">BINARY TREE / ${String(data.id).padStart(4,'0')} · 递归</div><h1>${esc(data.title)}<small>${esc(data.english)}</small></h1><p class="lesson-input">${esc(data.input)}</p></div><div class="trace-label"><span class="status-dot"></span>Java 17 · PASS</div></section><div class="workspace"><section class="panel code-panel"><div class="panel-title"><span><i class="java-icon">J</i> ${esc(data.javaFile.split('/').at(-1))}</span><span id="line-number"></span></div><div id="code" class="code"></div></section><section class="panel tree-panel"><div class="panel-title"><span>树</span><span id="node-count"></span></div><div class="tree-tabs"><button id="structure-tab" class="selected">树结构</button><button id="calls-tab">调用树</button><label>缩放 <select id="zoom" aria-label="树缩放"><option value="0.65">65%</option><option value="0.85">85%</option><option value="1" selected>100%</option><option value="1.3">130%</option></select></label></div><div class="tree-legend"><span class="legend-active">当前</span><span>∅ 空节点</span>${[116,117].includes(data.id)?'<span class="legend-next">next</span>':''}</div><div id="tree-scroll" class="tree-scroll"><div id="tree-space"></div><canvas id="tree"></canvas></div><div id="tree-note" class="tree-note"></div></section></div><section class="step-info"><span id="operation" class="operation"></span><strong id="step-title"></strong><p id="explanation"></p><span id="depth" class="depth"></span></section><section class="inspect"><section class="panel"><div class="panel-title">当前变量 <span id="frame-title"></span></div><div id="variables" class="variables"></div></section><section class="panel stack-panel"><div class="panel-title">调用栈 <span></span></div><div id="stack" class="stack"></div></section><section class="panel"><div class="panel-title">结果 <span id="result-count"></span></div><div id="result" class="result"></div><details class="verification"><summary>校验</summary><dl><dt>预期</dt><dd>${esc(data.expected)}</dd><dt>实际</dt><dd>${esc(data.actual)} · PASS</dd><dt>测试来源</dt><dd>${esc(data.testSource)}</dd></dl></details></section></section><footer class="controls"><div class="progress-row"><span id="progress-label"></span><input id="progress" aria-label="执行进度" type="range" min="0" max="${data.steps.length-1}" value="0"><span id="percent"></span></div><div class="button-row"><div class="control-group"><button id="reset">↺ 重置</button><button id="prev">← 上一步</button><button id="next" class="primary">下一步 →</button><button id="key">关键操作 ⇥</button><button id="play">▶ 播放</button></div><label class="speed">速度 <select id="speed" aria-label="播放速度"><option value="1400">0.5×</option><option value="700" selected>1×</option><option value="350">2×</option><option value="140">5×</option></select></label><span class="shortcut">← → 单步 · 空格播放</span></div></footer></main>`;
function highlight(s){const tokens=/(\/\/.*$|"(?:\\.|[^"\\])*"|\b(?:public|private|static|class|void|int|boolean|long|double|return|if|else|for|while|continue|break|new|true|false|null|final)\b|\b\d+\b)/g;let html='',end=0;for(const m of s.matchAll(tokens)){html+=esc(s.slice(end,m.index));html+='<span class="'+(m[0].startsWith('//')?'syntax-comment':m[0].startsWith('"')?'syntax-string':/^\d/.test(m[0])?'syntax-number':'syntax-keyword')+'">'+esc(m[0])+'</span>';end=m.index+m[0].length;}return html+esc(s.slice(end));}
$('code').innerHTML=data.code.map((line,i)=>'<div class="code-line" data-line="'+(data.displayStart+i)+'"><span class="gutter">'+(data.displayStart+i)+'</span><code>'+highlight(line.replace(/^ {4}/,''))+'</code></div>').join('');
let position=0,index=stops[0],timer=null,selectedFrame=null,mode='structure',zoom=1,zoomMode='fit',positions=[],edges=[],heap={},frames=[],picked=null;
$('progress').max=stops.length-1;
document.querySelector('.workspace').append($('stack').closest('.panel'));
document.querySelector('.workspace').before(document.querySelector('.controls'));
const renderStack=window.StackView.create($('stack'),data.code);
const voidMethods=new Set([...data.code.join('\n').matchAll(/\bvoid\s+(\w+)\s*\(/g)].map(m=>m[1]));
const canvas=$('tree'),ctx=canvas.getContext('2d');
$('zoom').insertAdjacentHTML('afterbegin','<option value="fit">自适应</option>');$('zoom').value='fit';
const refId=x=>x?.$ref===undefined?null:String(x.$ref);
const isTree=x=>x&&'val'in x&&('left'in x||'children'in x);
function readable(x,seen=new Set(),depth=0){const id=refId(x);if(id!==null){const object=heap[id];if(!object)return '#'+id;if(seen.has(id)||depth>3)return '#'+id+(object.val===undefined?'':' (值 '+object.val+')');const next=new Set(seen);next.add(id);return Object.fromEntries([['节点','#'+id],...Object.entries(object).filter(([k])=>k!=='$type').map(([k,v])=>[k,readable(v,next,depth+1)])]);}if(Array.isArray(x))return x.map(v=>readable(v,seen,depth+1));if(x&&typeof x==='object')return Object.fromEntries(Object.entries(x).map(([k,v])=>[k,readable(v,seen,depth+1)]));return x;}
function brief(x){const id=refId(x);return id!==null?(heap[id]?.val===undefined?'#'+id:heap[id].val+' (#'+id+')'):Array.isArray(x)?'Array('+x.length+')':x&&typeof x==='object'?'Object':JSON.stringify(x);}
function compact(value){if(Array.isArray(value)){const text=value.every(x=>x===null||typeof x!=='object')?JSON.stringify(value):'Array('+value.length+')';return text.length>60?text.slice(0,57)+'…':text;}return brief(value);}
function renderVariables(vars){$('variables').innerHTML=Object.entries(vars).filter(([k])=>k!=='$type').map(([k,v])=>v===null||typeof v!=='object'?'<div class="variable scalar"><span>'+esc(k)+'</span><b>'+esc(compact(v))+'</b></div>':'<details class="variable"><summary><span>'+esc(k)+'</span><b>'+esc(compact(v))+'</b></summary><pre>'+esc(JSON.stringify(readable(v),null,2))+'</pre></details>').join('')||'<p class="empty">—</p>';}
function makeLayout(){positions=[];edges=[];let leaf=0;const step=data.steps[index];
 if(mode==='calls'){
  const visible=data.calls.filter(c=>c.born<=index),kids=new Map();for(const n of visible){if(!kids.has(n.parent))kids.set(n.parent,[]);kids.get(n.parent).push(n);}
  for(const siblings of kids.values())siblings.sort((a,b)=>a.born-b.born||a.id-b.id);
  function place(n,depth){const children=kids.get(n.id)||[],xx=children.map(c=>place(c,depth+1));const x=xx.length?(xx[0]+xx.at(-1))/2:90+leaf++*190;positions.push({id:String(n.id),x,y:55+depth*95,label:n.label,call:n,w:170,h:48});for(const child of children)edges.push({from:String(n.id),to:String(child.id)});return x;}
  for(const root of kids.get(null)||[])place(root,0);
 }else{
  const roots=Object.entries(data.pool[step.roots]);
  let candidates=roots.map(([name,value])=>({name,id:refId(value)})).filter(x=>x.id!==null);
  if(index===data.steps.length-1&&refId(data.pool[step.returnValue])!==null&&isTree(heap[refId(data.pool[step.returnValue])]))candidates=[{name:'返回的树',id:refId(data.pool[step.returnValue])}];
  if(!candidates.length){for(const f of frames){for(const k of ['root','node']){const id=refId(f.vars[k]);if(id!==null&&isTree(heap[id])){candidates=[{name:'正在构建',id}];break;}}if(candidates.length)break;}}
  if(!candidates.length){const item=Object.entries(heap).find(([,n])=>isTree(n));if(item)candidates=[{name:'正在构建',id:item[0]}];}
  function place(id,depth,prefix,seen=new Set(),rootLabel=''){
   const n=heap[id],key=prefix+id;if(seen.has(id))return null;const next=new Set(seen);next.add(id);
   const children=n.children? n.children.map((v,i)=>({ref:v,label:String(i+1)})):!n.left&&!n.right?[]:[{ref:n.left,label:'左'},{ref:n.right,label:'右'}];
   const childKeys=[],xx=[];
   for(const child of children){const cid=refId(child.ref);if(cid===null){if(n.children)continue;const emptyId=key+'-'+child.label;const x=55+leaf++*94;positions.push({id:emptyId,x,y:55+(depth+1)*95,label:'∅',empty:true,w:30,h:30});xx.push(x);childKeys.push({id:emptyId,label:child.label});}else{const x=place(cid,depth+1,prefix,next);if(x!==null){xx.push(x);childKeys.push({id:prefix+cid,label:child.label});}}}
   const x=xx.length?(xx[0]+xx.at(-1))/2:55+leaf++*94;
   positions.push({id:key,ref:id,x,y:55+depth*95,label:String(n.val),rootLabel,w:46,h:46});
   for(const c of childKeys)edges.push({from:key,to:c.id,label:c.label});return x;
  }
  for(const [i,r] of candidates.entries()){place(r.id,0,i+'-',new Set(),r.name);leaf++;}
  for(const p of positions){if(!p.ref||!heap[p.ref].next)continue;const to=positions.find(x=>x.ref===refId(heap[p.ref].next)&&x.id.split('-')[0]===p.id.split('-')[0]);if(to)edges.push({from:p.id,to:to.id,next:true});}
 }
 // 绘制根在上、子节点在下，同层从左到右；结构仍保留 left/right 与 children 顺序。
 positions.sort((a,b)=>a.y-b.y||a.x-b.x);
 const contentWidth=Math.max(200,...positions.map(p=>p.x+p.w/2+25));
 const contentHeight=Math.max(200,...positions.map(p=>p.y+p.h/2+55));
 if(zoomMode==='fit')zoom=mode==='structure'?Math.min(1,$('tree-scroll').clientWidth/contentWidth,$('tree-scroll').clientHeight/contentHeight):Math.max(0.65,Math.min(1,$('tree-scroll').clientWidth/contentWidth));
 const width=Math.max($('tree-scroll').clientWidth/zoom,contentWidth),height=Math.max(350,...positions.map(p=>p.y+p.h/2+55));
 $('tree-space').style.width=width*zoom+'px';$('tree-space').style.height=height*zoom+'px';
 const active=positions.find(p=>mode==='calls'?Number(p.id)===step.active:Object.values(frames.find(f=>f.id===selectedFrame)?.vars||{}).map(refId).includes(p.ref));
 const sc=$('tree-scroll');if(active){if(active.x*zoom<sc.scrollLeft||active.x*zoom>sc.scrollLeft+sc.clientWidth)sc.scrollLeft=Math.max(0,active.x*zoom-sc.clientWidth/2);if(active.y*zoom<sc.scrollTop||active.y*zoom>sc.scrollTop+sc.clientHeight)sc.scrollTop=Math.max(0,active.y*zoom-sc.clientHeight/2);}
 $('node-count').textContent=mode==='calls'?positions.length+' 次调用':positions.filter(p=>!p.empty).length+' 个节点';draw();
}
function draw(){const colors=window.ClassroomTheme.palette(),sc=$('tree-scroll'),dpr=devicePixelRatio||1,w=sc.clientWidth,h=sc.clientHeight;canvas.width=w*dpr;canvas.height=h*dpr;canvas.style.width=w+'px';canvas.style.height=h+'px';canvas.style.left=sc.scrollLeft+'px';canvas.style.top=sc.scrollTop+'px';ctx.setTransform(dpr,0,0,dpr,0,0);ctx.clearRect(0,0,w,h);ctx.scale(zoom,zoom);ctx.translate(-sc.scrollLeft/zoom,-sc.scrollTop/zoom);
 const byId=new Map(positions.map(p=>[p.id,p]));
 for(const e of edges){const a=byId.get(e.from),b=byId.get(e.to);if(!a||!b)continue;ctx.beginPath();ctx.strokeStyle=e.next?colors["next"]:colors["edge"];ctx.lineWidth=1.3;ctx.setLineDash(e.next?[5,4]:b.empty?[3,4]:[]);if(e.next){ctx.moveTo(a.x+a.w/2,a.y);ctx.lineTo(b.x-b.w/2,b.y);ctx.lineTo(b.x-b.w/2-7,b.y-4);ctx.moveTo(b.x-b.w/2,b.y);ctx.lineTo(b.x-b.w/2-7,b.y+4);}else{ctx.moveTo(a.x,a.y+a.h/2);ctx.lineTo(b.x,b.y-b.h/2);}ctx.stroke();if(e.label){ctx.fillStyle=colors["muted"];ctx.font='500 10px "Microsoft YaHei", sans-serif';ctx.fillText(e.label,(a.x+b.x)/2+4,(a.y+b.y)/2);}}
 ctx.setLineDash([]);const vars=frames.find(f=>f.id===selectedFrame)?.vars||{},activeRefs=new Set(Object.values(vars).map(refId).filter(Boolean));
 for(const p of positions){const active=mode==='calls'?Number(p.id)===data.steps[index].active:activeRefs.has(p.ref),returned=p.call?.returned<=index;ctx.fillStyle=active?colors["active"]:returned?colors["answer"]:p.empty?colors["empty"]:colors["fill"];ctx.strokeStyle=active?colors["active-border"]:p.empty?colors["border"]:colors["border"];ctx.lineWidth=active?2:1;ctx.beginPath();if(mode==='calls')ctx.roundRect(p.x-p.w/2,p.y-p.h/2,p.w,p.h,7);else ctx.arc(p.x,p.y,p.w/2,0,Math.PI*2);ctx.fill();ctx.stroke();ctx.textAlign='center';ctx.fillStyle=p.empty?colors["muted"]:active?colors["active-text"]:colors["text"];ctx.font=mode==='calls'?'600 11px Consolas, "Microsoft YaHei", monospace':'600 15px Consolas, "Microsoft YaHei", monospace';let label=p.label;while(ctx.measureText(label).width>p.w-14)label=label.slice(0,-2)+'…';ctx.fillText(label,p.x,p.y+(mode==='calls'?-2:5));ctx.font='500 10px "Microsoft YaHei", sans-serif';ctx.fillStyle=colors["muted"];if(mode==='calls')ctx.fillText('#'+p.call.id+' · '+(returned?'已返回':'调用'),p.x,p.y+15);else if(p.ref)ctx.fillText('#'+p.ref,p.x,p.y+37);if(p.rootLabel)ctx.fillText(p.rootLabel,p.x,p.y-33);}
 ctx.textAlign='left';
 if(!positions.length){ctx.fillStyle=colors["muted"];ctx.font='500 14px "Microsoft YaHei", sans-serif';ctx.fillText('尚未创建节点',25,65);}
}
function render(){const step=data.steps[index];heap=data.pool[step.heap];frames=data.pool[step.frames];if(!frames.some(f=>f.id===selectedFrame))selectedFrame=frames.at(-1).id;const frame=frames.find(f=>f.id===selectedFrame);
 document.querySelector('.code-line.current')?.classList.remove('current');const row=document.querySelector('[data-line="'+frame.line+'"]');if(row){row.classList.add('current');const panel=$('code');if(row.offsetTop<panel.scrollTop||row.offsetTop+row.offsetHeight>panel.scrollTop+panel.clientHeight)panel.scrollTop=row.offsetTop-panel.clientHeight/2;}
 const event=playback.events[position];
 $('line-number').textContent='L'+frame.line+(event.complete?' · 完成':' · 待执行');$('operation').className='operation '+step.kind;$('operation').textContent=event.complete?'执行完成':kinds[step.kind];$('step-title').textContent=step.method+'() · 第 '+step.line+' 行';
 const returned=step.returnValue<0?undefined:data.pool[step.returnValue];$('explanation').textContent=step.phase==='exit'&&returned!==null&&returned!==undefined?'↩ '+compact(returned):'';
 $('depth').textContent='';$('frame-title').textContent=frame.method+'()';renderVariables(frame.vars);
 renderStack({frames:event.complete?[]:frames,selectedId:selectedFrame,phase:event.entered?'enter':'line',returns:event.returns,format:compact,onSelect:id=>{selectedFrame=id;render();}});
 const vars=frames.at(-1).vars,result=index===data.steps.length-1?returned:vars.result??vars.path??(step.phase==='exit'&&!voidMethods.has(step.method)?returned:undefined);$('result').innerHTML=result===undefined?'<p class="empty">—</p>':'<pre>'+esc(index===data.steps.length-1?data.actual:JSON.stringify(readable(result),null,2))+'</pre>';
 $('result-count').textContent=index===data.steps.length-1?'最终返回':Array.isArray(result)?result.length+' 项':'';if(index===data.steps.length-1)$('result').innerHTML+='<div class="pass">✓ PASS</div>';
 $('progress').value=position;$('progress-label').textContent=(position+1)+' / '+stops.length;$('percent').textContent=Math.round(position/Math.max(1,stops.length-1)*100)+'%';$('prev').disabled=position===0;$('next').disabled=position===stops.length-1;$('key').disabled=position===stops.length-1;
 picked=null;makeLayout();
}
function pause(){clearInterval(timer);timer=null;$('play').textContent='▶ 播放';}
function seek(value){position=Math.max(0,Math.min(stops.length-1,value));index=stops[position];selectedFrame=null;render();}
function play(){if(timer){pause();return;}if(position===stops.length-1)seek(0);$('play').textContent='Ⅱ 暂停';timer=setInterval(()=>{if(position===stops.length-1){pause();return;}seek(position+1);},Number($('speed').value));}
$('prev').onclick=()=>{pause();seek(position-1);};$('next').onclick=()=>{pause();seek(position+1);};$('reset').onclick=()=>{pause();seek(0);};$('key').onclick=()=>{pause();let n=position+1;while(n<stops.length-1&&!playback.events[n].entered&&!playback.events[n].returns.length&&!['return','choose','undo','save','modify'].includes(data.steps[stops[n]].kind))n++;seek(n);};$('play').onclick=play;$('speed').onchange=()=>{if(timer){pause();play();}};$('progress').oninput=e=>{pause();seek(Number(e.target.value));};$('zoom').onchange=()=>{zoomMode=$('zoom').value;if(zoomMode!=='fit')zoom=Number(zoomMode);makeLayout();};
for(const tab of ['structure','calls'])$(tab+'-tab').onclick=()=>{mode=tab;for(const t of ['structure','calls'])$(t+'-tab').classList.toggle('selected',t===mode);$('tree-scroll').scrollLeft=0;$('tree-scroll').scrollTop=0;render();};
$('tree-scroll').onscroll=draw;document.addEventListener('classroompanelresize',makeLayout);window.addEventListener('resize',makeLayout);
canvas.onclick=e=>{const b=canvas.getBoundingClientRect(),sc=$('tree-scroll'),x=(e.clientX-b.left+sc.scrollLeft)/zoom,y=(e.clientY-b.top+sc.scrollTop)/zoom,p=positions.find(p=>Math.abs(x-p.x)<p.w/2&&Math.abs(y-p.y)<p.h/2);if(!p||p.empty)return;if(mode==='calls'){const step=data.steps[p.call.born],historic=data.pool[step.frames].at(-1);heap=data.pool[step.heap];renderVariables(historic.vars);$('frame-title').textContent=p.call.label+' · 进入时';heap=data.pool[data.steps[index].heap];}else{renderVariables(heap[p.ref]);$('frame-title').textContent='节点 #'+p.ref+' · 当前字段';}};
window.addEventListener('keydown',e=>{if(['INPUT','SELECT','TEXTAREA'].includes(e.target.tagName))return;if(e.key==='ArrowRight'){e.preventDefault();$('next').click();}else if(e.key==='ArrowLeft'){e.preventDefault();$('prev').click();}else if(e.code==='Space'){e.preventDefault();play();}else if(e.key.toLowerCase()==='k')$('key').click();else if(e.key==='Home')$('reset').click();});
window.ClassroomTheme.mount();
document.addEventListener('classroomthemechange',draw);
render();
})();
