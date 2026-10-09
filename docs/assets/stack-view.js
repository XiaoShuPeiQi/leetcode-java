(() => {
'use strict';
const esc=value=>String(value).replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
function parameters(code) {
 const methods=new Map();
 for(const match of code.join('\n').matchAll(/(?:public|private|protected)\s+(?:static\s+)?[\w<>\[\], ?]+\s+(\w+)\s*\(([^)]*)\)\s*\{/g)) {
  let depth=0,part='',parts=[];
  for(const c of match[2]){if(c==='<')depth++;if(c==='>')depth--;if(c===','&&depth===0){parts.push(part);part='';}else part+=c;}
  parts.push(part);methods.set(match[1],parts.map(p=>p.match(/(\w+)\s*$/)?.[1]).filter(Boolean));
 }
 return methods;
}
window.StackView = {
 create(container,code) {
  const args=parameters(code),voidMethods=new Set([...code.join('\n').matchAll(/\bvoid\s+(\w+)\s*\(/g)].map(m=>m[1]));
  return ({frames,selectedId,phase,returnValue,format,onSelect})=>{
   const exiting=phase==='exit',entering=phase==='enter';
   container.innerHTML='<div class="stack-cap"><span>栈顶</span><span class="stack-motion '+(exiting?'pop':entering?'push':'')+'">'+(exiting?'↑ 出栈':entering?'↓ 入栈':'')+'</span></div><div class="stack-well">'+frames.slice().reverse().map((frame,index)=>{
    const params=(args.get(frame.method)||Object.keys(frame.vars)).filter(k=>k in frame.vars).slice(0,4);
    const locals=Object.entries(frame.vars).filter(([k,v])=>!params.includes(k)&&(typeof v==='number'||typeof v==='boolean')).slice(0,3);
    const chips=params.map(k=>'<span><i>'+esc(k)+'</i> = '+esc(format(frame.vars[k]))+'</span>').join('');
    return '<button class="stack-frame '+(frame.id===selectedId?'selected ':'')+(index===0?'stack-top ':'')+(index===0&&exiting?'stack-leaving':'')+'" data-frame="'+frame.id+'" data-top="'+(index===0)+'" aria-pressed="'+(frame.id===selectedId)+'"><div class="stack-heading"><b>'+esc(frame.method)+'()</b><span>'+(index===0?exiting?'返回':'当前':'等待')+'</span><small>L'+frame.line+'</small></div><div class="stack-args">'+(chips||'<span>—</span>')+'</div>'+(locals.length?'<div class="stack-locals">'+locals.map(([k,v])=>esc(k)+' = '+esc(format(v))).join(' · ')+'</div>':'')+(index===0&&exiting&&returnValue!==undefined&&!voidMethods.has(frame.method)?'<div class="stack-return">↩ '+esc(returnValue===null?'null':format(returnValue))+'</div>':'')+'</button>';
   }).join('')+'</div><div class="stack-base">栈底 <span>'+frames.length+' 层</span></div>';
   container.querySelectorAll('[data-frame]').forEach(button=>button.onclick=()=>onSelect(Number(button.dataset.frame)));
  };
 }
};
})();
