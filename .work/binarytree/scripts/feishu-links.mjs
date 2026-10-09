import fs from 'node:fs';
import {spawnSync} from 'node:child_process';
const meta=JSON.parse(fs.readFileSync('imports/二叉树算法.import.json','utf8'));
const verified=JSON.parse(fs.readFileSync('.work/binarytree/logs/online-report.json','utf8'));
if(verified.length!==38||verified.some(p=>p.status!=='verified'))throw Error('线上验证不完整，停止写回。');
const run='C:/Users/Mr.zhan/AppData/Roaming/npm/node_modules/@larksuite/cli/scripts/run.js';
let seq=0;const report=[];
function cli(args,label){const result=spawnSync(process.execPath,[run,...args,'--as','user'],{encoding:'utf8',windowsHide:true,maxBuffer:64*1024*1024});
 fs.writeFileSync('.work/binarytree/tmp/feishu-'+String(++seq).padStart(3,'0')+'-'+label+'.json',result.stdout||'');
 let response;try{response=JSON.parse(result.stdout.replace(/^\uFEFF/,''));}catch{throw Error('CLI 非 JSON：'+label);}
 if(result.status!==0||!response.ok)throw Error(label+': '+JSON.stringify(response.error||response._notice||{status:result.status}));return response;}
const text=xml=>xml.replace(/<[^>]*>/g,'').replace(/\s/g,'');
function heading(xml,p){return [...xml.matchAll(/<h3\b[^>]*id="([^"]+)"[^>]*>([\s\S]*?)<\/h3>/g)].find(m=>new RegExp('3\\.\\d+.*?'+p.id+'\\.').test(text(m[2])));}
for(const p of meta.problems){
 const online=verified.find(v=>v.id===p.id),url=online.url,title='递归交互式调试器：'+p.id+' '+p.title;
 const outline=cli(['docs','+fetch','--doc',meta.source_url,'--scope','outline','--max-depth','3','--detail','full'],'outline-'+p.id);
 const target=heading(outline.data.document.content,p);if(!target)throw Error('找不到原文标题：'+p.id);
 const before=cli(['docs','+fetch','--doc',meta.source_url,'--scope','section','--start-block-id',target[1],'--detail','full'],'before-'+p.id);
 let action='already-present';
 if(!before.data.document.content.includes(url)){
  const content='<p><a type="url-preview" href="'+url+'">'+title+'</a></p>';
  const result=cli(['docs','+update','--doc',meta.source_url,'--command','block_insert_after','--block-id',target[1],'--revision-id',String(before.data.document.revision_id),'--content',content],'insert-'+p.id);
  if(result.data.result!=='success'||result.data.warnings?.length)throw Error('插入未完整成功：'+p.id);
  action='inserted';
 }
 const fresh=cli(['docs','+fetch','--doc',meta.source_url,'--scope','outline','--max-depth','3','--detail','full'],'fresh-'+p.id),freshHeading=heading(fresh.data.document.content,p);
 if(!freshHeading)throw Error('回读标题缺失：'+p.id);
 const after=cli(['docs','+fetch','--doc',meta.source_url,'--scope','section','--start-block-id',freshHeading[1],'--detail','full'],'after-'+p.id);
 if(!after.data.document.content.includes(url))throw Error('回读未找到页面链接：'+p.id);
 if(action==='inserted'){
  const removed=after.data.document.content.replace(/<p\b[^>]*>[\s\S]*?<\/p>/g,block=>text(block).includes(title.replace(/\s/g,''))?'':block);
  if(text(removed)!==text(before.data.document.content))throw Error('原文文本核对不一致：'+p.id);
 }
 const codes=x=>[...x.matchAll(/<pre\b[\s\S]*?<\/pre>/g)].map(m=>text(m[0]));
 if(JSON.stringify(codes(before.data.document.content))!==JSON.stringify(codes(after.data.document.content)))throw Error('原代码发生变化：'+p.id);
 report.push({id:p.id,title:p.title,url,action,status:'verified',revision:after.data.document.revision_id});
 fs.writeFileSync('.work/binarytree/logs/feishu-writeback.json',JSON.stringify(report,null,2)+'\n');
 console.log(p.id+' '+action+' / 原文与代码保留 / 链接回读确认 / '+report.length+'/38');
}
const lines=['# 二叉树递归调试器链接','','38 个 GitHub Pages 页面已验证，38 个链接已逐题写入飞书标题下方并回读确认。原文和代码块核对保持一致。','','[二叉树在线目录](https://xiaoshupeiqi.github.io/leetcode-java/?chapter=%E4%BA%8C%E5%8F%89%E6%A0%91) · [飞书原文]('+meta.source_url+')','','| 题号 | 题目 | Java | 在线递归调试器 | 飞书写回 |','|---|---|---|---|---|'];
for(const p of meta.problems)lines.push('| '+p.id+' | '+p.title+' | [源码](../'+p.java_file+') | [打开]('+verified.find(v=>v.id===p.id).url+') | 已回读确认 |');
fs.writeFileSync('docs/二叉树算法-调试器链接.md',lines.join('\n')+'\n');
console.log('飞书 38/38 链接写回并回读确认完成。');
