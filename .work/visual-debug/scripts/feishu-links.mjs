import fs from 'node:fs';
import path from 'node:path';
import vm from 'node:vm';
import {spawnSync} from 'node:child_process';
const root=process.cwd();
const run='C:/Users/Mr.zhan/AppData/Roaming/npm/node_modules/@larksuite/cli/scripts/run.js';
const doc='https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g';
const site='https://xiaoshupeiqi.github.io/leetcode-java/';
const c={window:{}};vm.runInNewContext(fs.readFileSync('docs/assets/catalog.js','utf8'),c);
const selected=process.argv.slice(2).map(Number);
const problems=c.window.CATALOG.filter(p=>!selected.length||selected.includes(p.id)).sort((a,b)=>(a.id===131?0:1)-(b.id===131?0:1));
let sequence=0;
function cli(args,label){
 const result=spawnSync(process.execPath,[run,...args,'--as','user'],{cwd:root,encoding:'utf8',windowsHide:true,maxBuffer:64*1024*1024});
 fs.writeFileSync('.work/visual-debug/tmp/feishu-'+String(++sequence).padStart(3,'0')+'-'+label+'.json',result.stdout||'');
 let response;try{response=JSON.parse((result.stdout||'').replace(/^\uFEFF/,''));}catch{throw Error(label+': CLI 未返回 JSON，'+(result.stderr||'').slice(0,1000));}
 if(result.status!==0||!response.ok)throw Error(label+': '+JSON.stringify(response.error||response._notice||{status:result.status,stderr:result.stderr}));
 return response;
}
function text(xml){return xml.replace(/<[^>]*>/g,'').replace(/\s/g,'');}
function removeNewParagraph(xml,title){return xml.replace(/<p\b[^>]*>[\s\S]*?<\/p>/g,block=>text(block).includes(title.replace(/\s/g,''))?'':block);}
const report=[];
for(const p of problems){
 const url=new URL(p.url,site).href;
 const online=await fetch(url);
 if(!online.ok||!(await online.text()).includes('trace.js'))throw Error(p.id+' 页面无法验证，停止写入');
 const outline=cli(['docs','+fetch','--doc',doc,'--scope','outline','--max-depth','3','--detail','full'],'outline-'+p.id);
 const titles=[...outline.data.document.content.matchAll(/<h3\b[^>]*id="([^"]+)"[^>]*>([\s\S]*?)<\/h3>/g)];
 const heading=titles.find(m=>new RegExp('^3\\.\\d+\\s+'+p.id+'\\.').test(text(m[2]).replace(/^3\.(\d+)/,'3.$1 ')));
 // XML 标题保留空格，直接移除内联标签后匹配题号。
 const target=titles.find(m=>new RegExp('^3\\.\\d+\\s+'+p.id+'\\.').test(m[2].replace(/<[^>]+>/g,'')))||heading;
 if(!target)throw Error('无法定位第 '+p.id+' 题标题');
 const before=cli(['docs','+fetch','--doc',doc,'--scope','section','--start-block-id',target[1],'--detail','full'],'before-'+p.id);
 let revision=before.data.document.revision_id;
 let action='already-present',newBlocks=[];
 const linkTitle='交互式调试器：'+p.id+' '+p.title;
 if(!before.data.document.content.includes(url)){
  const xml='<p><a type="url-preview" href="'+url+'">'+linkTitle+'</a></p>';
  const update=cli(['docs','+update','--doc',doc,'--command','block_insert_after','--block-id',target[1],'--revision-id',String(revision),'--content',xml],'insert-'+p.id);
  if(update.data.result!=='success'||(update.data.warnings?.length||0)>0)throw Error(p.id+' 写入未完整成功：'+JSON.stringify(update.data));
  action='inserted';newBlocks=update.data.document.new_blocks||[];
 }
 const freshOutline=cli(['docs','+fetch','--doc',doc,'--scope','outline','--max-depth','3','--detail','full'],'fresh-outline-'+p.id);
 const freshTitle=[...freshOutline.data.document.content.matchAll(/<h3\b[^>]*id="([^"]+)"[^>]*>([\s\S]*?)<\/h3>/g)].find(m=>new RegExp('^3\\.\\d+\\s+'+p.id+'\\.').test(m[2].replace(/<[^>]+>/g,'')));
 if(!freshTitle)throw Error(p.id+' 写入后标题丢失');
 const after=cli(['docs','+fetch','--doc',doc,'--scope','section','--start-block-id',freshTitle[1],'--detail','full'],'after-'+p.id);
 if(!after.data.document.content.includes(url))throw Error(p.id+' 回读未找到真实链接');
 if(action==='inserted'&&text(removeNewParagraph(after.data.document.content,linkTitle))!==text(before.data.document.content))throw Error(p.id+' 回读原正文文本不一致，请检查保存的 before / after');
 const code=xml=>[...xml.matchAll(/<pre\b[\s\S]*?<\/pre>/g)].map(m=>text(m[0]));
 if(JSON.stringify(code(before.data.document.content))!==JSON.stringify(code(after.data.document.content)))throw Error(p.id+' 原代码块发生变化');
 const row={id:p.id,title:p.title,url,headingBlock:freshTitle[1],newBlocks,action,revision:after.data.document.revision_id,status:'verified'};
 report.push(row);
 fs.writeFileSync('.work/visual-debug/logs/feishu-writeback.json',JSON.stringify(report,null,2));
 console.log(p.id+' '+action+' / read-back verified / revision '+row.revision);
}
console.log('Feishu '+report.length+'/'+problems.length+' verified');
