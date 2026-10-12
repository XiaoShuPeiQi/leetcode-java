import fs from 'node:fs';
import vm from 'node:vm';
const context={window:{}};
vm.runInNewContext(fs.readFileSync('docs/assets/catalog.js','utf8'),context);
function entry(html,prefix,backtracking){
 html=html.replaceAll('— 逐行','— 展老师小课堂');
 if(!html.includes(prefix+'theme.js'))html=html.replace('<head>','<head><script src="'+prefix+'theme.js"></script>');
 if(!html.includes(prefix+'theme.css'))html=html.replace('</head>','<link rel="stylesheet" href="'+prefix+'theme.css"></head>');
 if(backtracking&&!html.includes(prefix+'search-state.js'))html=html.replace('<script src="'+prefix+'player.js">','<script src="'+prefix+'search-state.js"></script><script src="'+prefix+'player.js">');
 return html;
}
for(const p of context.window.CATALOG){
 const file='docs/'+p.url+'index.html';
 fs.writeFileSync(file,entry(fs.readFileSync(file,'utf8'),'../../assets/',p.chapter==='回溯'));
}
for(const file of ['.work/visual-debug/scripts/build.mjs','.work/binarytree/scripts/build.mjs']){
 fs.writeFileSync(file,entry(fs.readFileSync(file,'utf8'),'../../assets/',file.includes('visual-debug')));
}
for(const file of ['docs/index.html','docs/assets/player.js','docs/assets/tree-player.js']){
 let text=fs.readFileSync(file,'utf8').replaceAll('>逐行<','>展老师小课堂<');
 if(file==='docs/index.html')text=entry(text,'assets/',false).replace('逐行 · LeetCode 算法学习实验室','展老师小课堂 · LeetCode').replace('选择一道题，开始逐行。','选择一道题，开始调试。');
 if(file.endsWith('player.js')&&!file.endsWith('tree-player.js'))text=text.replace('跳过候选','舍弃分支');
 fs.writeFileSync(file,text);
}
const file='docs/assets/tree-player.js';
let tree=fs.readFileSync(file,'utf8');
const colors={'#8dabf7':'next','#44536b':'edge','#17483e':'active','#182c28':'answer','#141c28':'empty','#1b2433':'fill','#6be5bd':'active-border','#354155':'border','#53637b':'border','#8191a9':'muted','#c1ffe7':'active-text','#d6dfec':'text','#8495ae':'muted','#9dacbf':'muted'};
for(const [color,key] of Object.entries(colors))tree=tree.replaceAll("'"+color+"'",'colors['+JSON.stringify(key)+']');
tree=tree.replace("returned?'已返回':'调用 #'+p.call.frame","'#'+p.call.id+' · '+(returned?'已返回':'调用')");
fs.writeFileSync(file,tree);
console.log('53 页与两个生成模板：名称、主题入口已同步。');
