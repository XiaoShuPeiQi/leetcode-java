(() => {
'use strict';
const root=document.documentElement,key='zhan-classroom-theme';
let current;
try{current=localStorage.getItem(key);}catch{}
if(!['light','dark'].includes(current))current=matchMedia('(prefers-color-scheme: dark)').matches?'dark':'light';
root.dataset.theme=current;
function apply(theme){
 current=theme;root.dataset.theme=theme;
 try{localStorage.setItem(key,theme);}catch{}
 document.querySelectorAll('.theme-toggle').forEach(button=>{
  button.textContent=theme==='dark'?'☀ 浅色':'☾ 深色';
  button.setAttribute('aria-label',theme==='dark'?'切换到浅色模式':'切换到深色模式');
  button.setAttribute('aria-pressed',String(theme==='dark'));
 });
 document.dispatchEvent(new Event('classroomthemechange'));
}
window.ClassroomTheme={
 mount(){
  const nav=document.querySelector('header nav');
  if(nav&&!nav.querySelector('.theme-toggle')){
   const button=document.createElement('button');button.className='theme-toggle';button.type='button';
   button.onclick=()=>apply(current==='dark'?'light':'dark');nav.append(button);
  }
  apply(current);
 },
 palette(){
  const style=getComputedStyle(root),names=['edge','next','fill','active','answer','prune','border','active-border','answer-border','prune-border','text','active-text','prune-text','muted','empty'];
  return Object.fromEntries(names.map(name=>[name,style.getPropertyValue('--canvas-'+name).trim()]));
 }
};
window.addEventListener('storage',event=>{if(event.key===key&&['light','dark'].includes(event.newValue))apply(event.newValue);});
})();
