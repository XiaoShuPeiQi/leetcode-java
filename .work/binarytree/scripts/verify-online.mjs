import fs from 'node:fs';
import crypto from 'node:crypto';
const meta=JSON.parse(fs.readFileSync('imports/二叉树算法.import.json','utf8'));
const base='https://xiaoshupeiqi.github.io/leetcode-java/';
const stamp=Date.now(),report=[];
async function read(url){const response=await fetch(url+'?v='+stamp,{headers:{'Cache-Control':'no-cache'}});if(!response.ok)throw Error(response.status+' '+url);return response.text();}
const home=await read(base),catalog=await read(base+'assets/catalog.js');
if(!home.includes('data-chapter="二叉树"')||!catalog.includes('二叉树'))throw Error('线上章节尚未更新');
for(const p of meta.problems){
 const url=base+'problems/'+p.slug+'/';
 if(!(await read(url)).includes('tree-player.js'))throw Error('HTML 尚未更新：'+p.id);
 const trace=JSON.parse((await read(url+'trace.js')).replace(/^window.TREE_TRACE = /,'').replace(/;\s*$/,''));
 const hash=crypto.createHash('sha256').update(fs.readFileSync(p.java_file)).digest('hex');
 if(trace.sourceHash!==hash||trace.validation.trace!=='PASS'||trace.validation.java!=='PASS')throw Error('线上轨迹与本地源码不一致：'+p.id);
 report.push({id:p.id,url,status:'verified',sourceHash:hash});
}
for(const asset of ['tree-player.js','tree-player.css'])if(await read(base+'assets/'+asset)!==fs.readFileSync('docs/assets/'+asset,'utf8'))throw Error('线上公共资源尚未更新：'+asset);
fs.writeFileSync('.work/binarytree/logs/online-report.json',JSON.stringify(report,null,2)+'\n');
console.log('38 个线上 HTML、轨迹源文件哈希、公共资源全部核验通过。');
