(() => {
'use strict';
const data=window.TRACE;
if (!data?.steps?.length) {document.getElementById('app').textContent='轨迹数据未加载。';return;}
const $=id=>document.getElementById(id);
const esc=s=>String(s).replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
const pretty=x=>JSON.stringify(x,null,2);
const varsAt=ref=>Object.fromEntries(data.pool[ref].map(([k,v])=>[data.pool[k],data.pool[v]]));
const playback=window.ExecutionSteps.create(data),stops=playback.stops;
const labels={enter:'进入调用',return:'返回',prune:'剪枝 / 跳过',save:'保存答案',undo:'撤销选择',choose:'做选择',call:'递归调用',condition:'条件判断',loop:'推进循环',statement:'执行语句'};
$('app').innerHTML='<header class="topbar"><a class="brand" href="../../index.html"><span class="brand-symbol">⌘</span>逐行<span class="brand-en">LEETCODE LAB</span></a><nav><a href="../../index.html">题目目录</a><a id="note-link" target="_blank" rel="noreferrer">飞书笔记 ↗</a></nav></header><main><section class="lesson"><div><div class="eyebrow">BACKTRACKING / '+String(data.id).padStart(4,'0')+'</div><h1>'+esc(data.title)+'<small>'+esc(data.english)+'</small></h1><p class="lesson-input">'+esc(data.input)+'</p></div><div class="trace-label"><span class="status-dot"></span>Java 17 · PASS<small>'+data.steps.length.toLocaleString()+' 步</small></div></section><div class="workspace"><section class="panel code-panel"><div class="panel-title"><span><i class="java-icon">J</i> '+esc(data.javaFile.split('/').at(-1))+'</span><span id="line-number">L'+data.steps[0].line+'</span></div><div id="code" class="code"></div></section><section class="panel tree-panel"><div class="panel-title"><span>'+ (data.id===332?'Hierholzer 调用树':'递归搜索树')+'</span><span id="node-count"></span></div><div class="tree-legend"><span class="legend-active">当前调用</span><span class="legend-answer">已收集答案</span><span class="legend-prune">跳过候选</span><label>缩放 <select id="zoom" aria-label="递归树缩放"><option value="0.65">65%</option><option value="0.85">85%</option><option value="1" selected>100%</option><option value="1.3">130%</option></select></label></div><div id="tree-scroll" class="tree-scroll"><div id="tree-space"></div><canvas id="tree"></canvas></div><div class="tree-note" id="tree-note">滚动查看历史节点 · 点击节点查看进入时的变量</div></section></div><section class="step-info"><span id="operation" class="operation"></span><strong id="step-title"></strong><p id="explanation"></p><span id="depth" class="depth"></span></section><section class="inspect"><section class="panel"><div class="panel-title">当前变量 <span id="frame-title"></span></div><div id="variables" class="variables"></div></section><section class="panel stack-panel"><div class="panel-title">调用栈 <span></span></div><div id="stack" class="stack"></div></section><section class="panel"><div class="panel-title">结果 <span id="result-count"></span></div><div id="result" class="result"></div><details class="verification"><summary>校验</summary><dl><dt>预期</dt><dd id="expected"></dd><dt>实际</dt><dd id="actual"></dd><dt>测试来源</dt><dd>'+esc(data.testSource)+'</dd><dt>源码核对</dt><dd>'+esc(data.sourceStatus)+'</dd></dl></details></section></section><footer class="controls"><div class="progress-row"><span id="progress-label"></span><input id="progress" aria-label="执行进度" type="range" min="0" max="'+(data.steps.length-1)+'" value="0"><span id="percent">0%</span></div><div class="button-row"><div class="control-group"><button id="reset" title="重置 (Home)">↺ 重置</button><button id="prev" title="上一步 (←)">← 上一步</button><button id="next" class="primary" title="下一步 (→)">下一步 →</button><button id="key" title="下一个关键操作 (K)">关键操作 ⇥</button><button id="play" title="播放 / 暂停 (Space)">▶ 播放</button></div><label class="speed">速度 <select id="speed" aria-label="播放速度"><option value="1400">0.5×</option><option value="700" selected>1×</option><option value="350">2×</option><option value="140">5×</option></select></label><span class="shortcut">← → 单步 · 空格播放</span></div></footer><p class="provenance">真实 JDI 行号 / 参数 / 共享对象 / 返回事件 · 无框架，无外部资源 · <a href="../../index.html">返回目录</a></p></main>';
$('note-link').href=data.source;
$('expected').textContent=data.expected;
$('actual').textContent=data.actual+' · PASS';
function highlight(s) {
 const tokens=/(\/\/.*$|"(?:\\.|[^"\\])*"|'(?:\\.|[^'\\])*'|\b(?:public|private|static|class|void|int|char|boolean|return|if|else|for|while|continue|break|new|true|false|null|final)\b|\b\d+\b)/g;
 let html='',end=0;for(const m of s.matchAll(tokens)){html+=esc(s.slice(end,m.index));const t=m[0];html+='<span class="'+(t.startsWith('//')?'syntax-comment':t.startsWith('"')||t.startsWith("'")?'syntax-string':/^\d/.test(t)?'syntax-number':'syntax-keyword')+'">'+esc(t)+'</span>';end=m.index+t.length;}return html+esc(s.slice(end));
}
$('code').innerHTML=data.code.map((line,i)=>'<div class="code-line" data-line="'+(data.displayStart+i)+'"><span class="gutter">'+(data.displayStart+i)+'</span><code>'+highlight(line.replace(/^ {4}/,''))+'</code></div>').join('');
let position=0,index=stops[0], timer=null, selectedFrame=null, positions=[], visibleNodes=[], selectedNode=null;
const expandedVariables=new Set();
document.body.classList.add('backtracking-player');
$('progress').max=stops.length-1;
document.querySelector('.trace-label small').textContent=stops.length.toLocaleString()+' 步';
let zoom=1,zoomMode='fit';
$('zoom').insertAdjacentHTML('afterbegin','<option value="fit">自适应</option>');$('zoom').value='fit';
document.querySelector('.workspace').append($('stack').closest('.panel'));
document.querySelector('.workspace').before(document.querySelector('.controls'));
const renderStack=window.StackView.create($('stack'),data.code);
const compact=value=>{const text=JSON.stringify(value);return text===undefined?'—':text.length>60?text.slice(0,57)+'…':text;};
const canvas=$('tree'),ctx=canvas.getContext('2d');
function layoutTree() {
 visibleNodes=data.nodes.filter(n=>n.born<=index);
 const children=new Map();for(const n of visibleNodes){if(n.parent!==null){if(!children.has(n.parent))children.set(n.parent,[]);children.get(n.parent).push(n);}}
 positions=[];let leaf=0;
 function place(n,depth){const kids=children.get(n.id)||[];let x;if(!kids.length)x=100+leaf++*184;else {const xx=kids.map(c=>place(c,depth+1));x=(xx[0]+xx.at(-1))/2;}positions[n.id]={x,y:48+depth*92,node:n};return x;}
 place(visibleNodes[0],0);
 const sc=$('tree-scroll');
 const contentWidth=Math.max(200,...positions.filter(Boolean).map(p=>p.x+100)),contentHeight=Math.max(200,...positions.filter(Boolean).map(p=>p.y+48));
 if(zoomMode==='fit')zoom=Math.max(.65,Math.min(1,sc.clientWidth/contentWidth));
 const width=Math.max(sc.clientWidth/zoom,contentWidth),height=Math.max(sc.clientHeight/zoom,contentHeight);
 $('tree-space').style.width=width*zoom+'px';$('tree-space').style.height=height*zoom+'px';
 const active=positions[data.steps[index].active];
 if(active){if((active.x-80)*zoom<sc.scrollLeft||(active.x+80)*zoom>sc.scrollLeft+sc.clientWidth)sc.scrollLeft=Math.max(0,active.x*zoom-sc.clientWidth/2);if((active.y-24)*zoom<sc.scrollTop||(active.y+24)*zoom>sc.scrollTop+sc.clientHeight)sc.scrollTop=Math.max(0,active.y*zoom-sc.clientHeight/2);}
 drawTree();
 $('node-count').textContent=visibleNodes.filter(n=>n.kind!=='prune').length+' 调用 · '+visibleNodes.filter(n=>n.kind==='prune').length+' 剪枝';
}
function drawTree() {
 const sc=$('tree-scroll'),dpr=window.devicePixelRatio||1,w=sc.clientWidth,h=sc.clientHeight;
 canvas.width=w*dpr;canvas.height=h*dpr;canvas.style.width=w+'px';canvas.style.height=h+'px';
 canvas.style.left=sc.scrollLeft+'px';canvas.style.top=sc.scrollTop+'px';
 ctx.setTransform(dpr,0,0,dpr,0,0);ctx.clearRect(0,0,w,h);ctx.scale(zoom,zoom);ctx.translate(-sc.scrollLeft/zoom,-sc.scrollTop/zoom);
 const top=sc.scrollTop/zoom,left=sc.scrollLeft/zoom,right=left+w/zoom,bottom=top+h/zoom;
 const inView=p=>p.x+80>=left&&p.x-80<=right&&p.y+24>=top&&p.y-24<=bottom;
 for(const p of positions){if(!p||p.node.parent===null)continue;const parent=positions[p.node.parent];if(!parent)continue;if(p.y<top||parent.y>bottom||Math.max(p.x,parent.x)+80<left||Math.min(p.x,parent.x)-80>right)continue;ctx.beginPath();ctx.strokeStyle=p.node.kind==='prune'?'#aa5360':'#455168';ctx.lineWidth=1.2;ctx.setLineDash(p.node.kind==='prune'?[4,5]:[]);ctx.moveTo(parent.x,parent.y+24);ctx.bezierCurveTo(parent.x,parent.y+46,p.x,p.y-46,p.x,p.y-24);ctx.stroke();}
 ctx.setLineDash([]);
 for(const p of positions){if(!p||!inView(p))continue;const n=p.node,active=n.id===data.steps[index].active,answer=n.answerAt!==undefined&&n.answerAt<=index,prune=n.kind==='prune';ctx.fillStyle=active?'#17483e':answer?'#163b2a':prune?'#34222d':'#1b2433';ctx.strokeStyle=active?'#6be5bd':answer?'#5abe88':prune?'#e78891':'#44536b';ctx.lineWidth=active?2:1;ctx.beginPath();ctx.roundRect(p.x-80,p.y-24,160,48,7);ctx.fill();ctx.stroke();ctx.fillStyle=prune?'#efa8af':active?'#c1ffe7':'#d6dfec';ctx.font='12px Consolas, monospace';let label=n.label;while(ctx.measureText(label).width>143)label=label.slice(0,-2)+'…';ctx.fillText(label,p.x-71,p.y-3);ctx.fillStyle='#8495ae';ctx.font='10px sans-serif';ctx.fillText(prune?'跳过':n.returned!==undefined&&n.returned<=index?'已返回':n.detail||n.method||'入口',p.x-71,p.y+14);}
}
function renderVariables(vars) {
 const priority=['path','board','remaining','startIndex','startNum','index','row','col','end','i','airport'];
 $('variables').innerHTML=Object.entries(vars).sort(([a],[b])=>(priority.includes(a)?priority.indexOf(a):99)-(priority.includes(b)?priority.indexOf(b):99)).map(([k,v])=>v===null||typeof v!=='object'?'<div class="variable scalar"><span>'+esc(k)+'</span><b>'+esc(compact(v))+'</b></div>':'<details class="variable" data-variable="'+esc(k)+'" '+(expandedVariables.has(k)?'open':'')+'><summary><span>'+esc(k)+'</span><b>'+esc(k==='board'?'棋盘 '+v.length+' × '+(v[0]?.length||0):compact(v))+'</b></summary>'+(k==='board'&&Array.isArray(v)?'<table class="board-grid">'+v.map((row,r)=>'<tr>'+row.map((cell,c)=>'<td class="'+(r===vars.row&&c===vars.col?'cell-active':'')+'">'+esc(cell)+'</td>').join('')+'</tr>').join('')+'</table>':'<pre>'+esc(pretty(v))+'</pre>')+'</details>').join('')||'<p class="empty">—</p>';
 $('variables').querySelectorAll('details').forEach(detail=>detail.ontoggle=()=>{if(!detail.isConnected)return;if(detail.open)expandedVariables.add(detail.dataset.variable);else expandedVariables.delete(detail.dataset.variable);});
}
function render() {
 const step=data.steps[index],vars=varsAt(step.vars),frames=data.pool[step.stack].map(ref=>({...data.pool[ref],method:data.pool[data.pool[ref].method]}));
 if(!frames.some(f=>f.id===selectedFrame))selectedFrame=frames.at(-1).id;
 const frame=frames.find(f=>f.id===selectedFrame),frameVars=varsAt(frame.vars);
 for(const key of data.pool[frame.sharedKeys]) if(key in vars)frameVars[key]=vars[key];
 document.querySelector('.code-line.current')?.classList.remove('current');
 const row=document.querySelector('[data-line="'+frame.line+'"]');
 if(row){row.classList.add('current');const panel=$('code');if(row.offsetTop<panel.scrollTop||row.offsetTop+row.offsetHeight>panel.scrollTop+panel.clientHeight)panel.scrollTop=row.offsetTop-panel.clientHeight/2;}
 const event=playback.events[position];
 $('line-number').textContent='L'+frame.line+(event.complete?' · 完成':' · 待执行');
 $('operation').className='operation '+step.kind;$('operation').textContent=event.complete?'执行完成':labels[step.kind];
 $('step-title').textContent=step.method+'() · 第 '+step.line+' 行'+(step.phase==='exit'?' · 已退出方法':'');
 $('explanation').textContent=step.phase==='exit'&&step.returnValue!==undefined&&step.returnValue!==null?'↩ '+compact(step.returnValue):'';
 $('depth').textContent='';
 $('frame-title').textContent=frame.method+'()';
 renderVariables(frameVars);
 const stackFrames=frames.map(f=>{const values=varsAt(f.vars);for(const key of data.pool[f.sharedKeys])if(key in vars)values[key]=vars[key];return {...f,vars:values};});
 renderStack({frames:event.complete?[]:stackFrames,selectedId:selectedFrame,phase:event.entered?'enter':'line',returns:event.returns,format:compact,onSelect:id=>{selectedFrame=id;render();}});
 let result=vars.result;
 if(data.id===37)result=vars.board;
 $('result').innerHTML=result===undefined?'<p class="empty">—</p>':'<pre>'+esc(pretty(result))+'</pre>';
 $('result-count').textContent=data.id===37?'棋盘快照':Array.isArray(result)?result.length+' 项':'';
 if(index===data.steps.length-1)$('result').innerHTML+='<div class="pass">✓ PASS</div>';
 $('progress').value=position;$('progress-label').textContent=(position+1)+' / '+stops.length;$('percent').textContent=Math.round(position/Math.max(1,stops.length-1)*100)+'%';
 $('prev').disabled=position===0;$('next').disabled=position===stops.length-1;$('key').disabled=position===stops.length-1;
 selectedNode=null;$('tree-note').textContent='滚动查看历史节点 · 点击节点查看进入时的变量';layoutTree();
}
function pause(){if(timer)clearInterval(timer);timer=null;$('play').textContent='▶ 播放';}
function seek(value){position=Math.max(0,Math.min(stops.length-1,value));index=stops[position];selectedFrame=null;render();}
function play(){if(timer){pause();return;}if(position===stops.length-1)seek(0);$('play').textContent='Ⅱ 暂停';timer=setInterval(()=>{if(position>=stops.length-1){pause();return;}seek(position+1);},Number($('speed').value));}
$('prev').addEventListener('click',()=>{pause();seek(position-1);});
$('next').addEventListener('click',()=>{pause();seek(position+1);});
$('reset').addEventListener('click',()=>{pause();seek(0);});
$('key').addEventListener('click',()=>{pause();let target=position+1;while(target<stops.length-1&&!playback.events[target].entered&&!playback.events[target].returns.length&&!['return','choose','undo','save','prune'].includes(data.steps[stops[target]].kind))target++;seek(target);});
$('play').addEventListener('click',play);
$('speed').addEventListener('change',()=>{if(timer){pause();play();}});
$('zoom').addEventListener('change',()=>{zoomMode=$('zoom').value;if(zoomMode!=='fit')zoom=Number(zoomMode);layoutTree();});
$('progress').addEventListener('input',e=>{pause();seek(Number(e.target.value));});
$('tree-scroll').addEventListener('scroll',drawTree);
window.addEventListener('resize',render);
canvas.addEventListener('click',e=>{const bounds=canvas.getBoundingClientRect(),sc=$('tree-scroll');const x=(e.clientX-bounds.left+sc.scrollLeft)/zoom,y=(e.clientY-bounds.top+sc.scrollTop)/zoom;const p=positions.find(p=>p&&Math.abs(x-p.x)<=80&&Math.abs(y-p.y)<24);if(p&&p.node.state!==undefined){selectedNode=p.node.id;renderVariables(varsAt(p.node.state));$('frame-title').textContent='节点 '+p.node.label+' · 历史';$('tree-note').textContent='节点 '+p.node.label+' 的历史变量；单步后恢复当前变量。';}});
window.addEventListener('keydown',e=>{if(['INPUT','SELECT','TEXTAREA'].includes(e.target.tagName))return;if(e.key==='ArrowRight'){e.preventDefault();$('next').click();}else if(e.key==='ArrowLeft'){e.preventDefault();$('prev').click();}else if(e.code==='Space'){e.preventDefault();play();}else if(e.key.toLowerCase()==='k')$('key').click();else if(e.key==='Home')$('reset').click();});
render();
})();
