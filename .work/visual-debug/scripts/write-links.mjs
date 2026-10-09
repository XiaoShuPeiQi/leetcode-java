import fs from 'node:fs';
import vm from 'node:vm';
const c={window:{}};vm.runInNewContext(fs.readFileSync('docs/assets/catalog.js','utf8'),c);
const meta=JSON.parse(fs.readFileSync('imports/回溯算法.import.json','utf8'));
const input=process.argv[2];
let base;
if(input){
 base=new URL(input);
 if(base.protocol!=='https:'||base.username||base.password)throw Error('发布地址必须是无凭据的 HTTPS URL');
 if(!base.pathname.endsWith('/'))base.pathname+='/';
 const home=await fetch(base);
 if(!home.ok||!(await home.text()).includes('LEETCODE LAB'))throw Error('实际首页验证失败');
 for(const p of c.window.CATALOG){const response=await fetch(new URL(p.url,base));if(!response.ok||!(await response.text()).includes('trace.js'))throw Error('实际题目网页验证失败：'+p.id);}
}
const lines=['# 飞书交互式调试器链接','','本地页面已生成并验证。'+(base?'以下 HTTPS 地址已实际访问确认。':'尚未创建远程仓库和 GitHub Pages，在线地址留空；不将本机地址或猜测的域名写入飞书。'),'','| 题号 | 题目 | Java 文件 | 本地调试器 | GitHub Pages 调试器链接 | 飞书原笔记链接 | 发布状态 |','|---|---|---|---|---|---|---|'];
for(const p of c.window.CATALOG){const original=meta.problems.find(x=>x.id===p.id);lines.push('| '+p.id+' | '+p.title+' | ['+original.java_file.split('/').at(-1)+'](../'+original.java_file+') | [打开]('+p.url+'index.html) | '+(base?'[在线调试器]('+new URL(p.url,base).href+')':'待发布')+' | [原笔记]('+p.source+') | '+(base?'网页已验证；飞书回写待核验':'本地 PASS；待 GitHub 授权与公开范围确认')+' |');}
lines.push('','飞书写回方式：发布成功并验证真实地址后，重新读取对应题目标题的 block ID，在标题后局部插入链接，每次写入后回读核验，保留原有正文、代码、图片。当前没有写回飞书。','');
fs.writeFileSync('docs/feishu-links.md',lines.join('\n'));
console.log(base?'已维护真实在线地址；飞书尚需写入并回读。':'已生成 15 题本地映射；在线地址待发布。');
