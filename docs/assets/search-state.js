(() => {
'use strict';
// 舍弃分支是 path 加上被拒绝的选择；原始快照与结果不变。
window.SearchState = {
 discarded(id,vars) {
  const value=vars.nums?.[vars.i]??vars.candidates?.[vars.i]??vars.i;
  let path,reason='条件不满足',placement;
  if(Array.isArray(vars.path)){
   const choice=vars.end!==undefined&&typeof vars.s==='string'?vars.s.slice(vars.startIndex,vars.end+1):value;
   path=[...vars.path,choice];
   if(id===491)reason=vars.path.length&&choice<vars.path.at(-1)?'破坏非递减':'同层重复';
   else if(id===131)reason='非回文';
   else if(id===93)reason=choice.length>1&&choice[0]==='0'?'前导零':'IP 段超出范围';
   else if([39,40,216].includes(id))reason=choice>vars.remaining?'超过剩余和':'同层重复';
   else if([46,47].includes(id))reason=vars.used?.[vars.i]?'已使用此位置':'同层重复';
   else if(id===90)reason='同层重复';
  }else if(Array.isArray(vars.board)){
   placement={row:vars.row,col:vars.col,value:id===51?'Q':String(vars.digit)};
   if(id===51){
    path=vars.board.slice(0,vars.row).map(row=>row.indexOf('Q')).concat(vars.col);
    reason=vars.columns?.[vars.col]?'同列冲突':vars.diagonals?.[vars.diagonal]?'主对角线冲突':'副对角线冲突';
   }else reason=vars.rows?.[vars.row]?.[vars.digit]?'同行冲突':vars.columns?.[vars.col]?.[vars.digit]?'同列冲突':'宫格冲突';
  }
  return {label:path?JSON.stringify(path):placement?'填 '+placement.value+' @ ('+placement.row+','+placement.col+')':'舍弃分支',path,reason,placement};
 }
};
})();
