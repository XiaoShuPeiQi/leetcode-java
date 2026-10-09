(() => {
'use strict';
// 原始 JDI 事件保留用于树和历史快照，导航只停在待执行语句上。
window.ExecutionSteps = {
 create(data) {
  const tree=!!data.calls;
  const sameState=(a,b)=>tree?a.frames===b.frames&&a.heap===b.heap&&a.roots===b.roots:a.stack===b.stack&&a.vars===b.vars;
  const executable=line=>{
   const source=(data.code[line-data.displayStart]||'').replace(/\/\/.*$/,'').trim();
   return source&&!/^[{};\s]+$/.test(source)&&!/^(?:}\s*)?else\s*\{?$/.test(source);
  };
  const stops=[],events=[];
  let returns=[],entered=false;
  for(let index=0;index<data.steps.length;index++){
   const step=data.steps[index],final=index===data.steps.length-1;
   if(step.phase==='enter'){entered=true;continue;}
   if(step.phase==='exit'){
    returns.push({method:step.method,value:tree?(step.returnValue<0?undefined:data.pool[step.returnValue]):step.returnValue});
    if(!final)continue;
   }else{
    if(!executable(step.line))continue;
    const previous=data.steps[index-1];
    if(previous?.phase==='line'&&previous.line===step.line&&sameState(previous,step))continue;
   }
   stops.push(index);
   events.push({entered,returns,complete:final});
   returns=[];entered=false;
  }
  if(!stops.length){stops.push(data.steps.length-1);events.push({entered:false,returns:[],complete:true});}
  return {stops,events};
 }
};
})();
