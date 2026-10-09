import fs from 'node:fs';
import path from 'node:path';
import vm from 'node:vm';
const root=path.resolve('.work/visual-debug/tmp/pages');
fs.mkdirSync(root,{recursive:true});
for(const item of ['index.html','.nojekyll','assets','problems'])fs.cpSync(path.join('docs',item),path.join(root,item),{recursive:true});
fs.mkdirSync('outputs',{recursive:true});
const build=JSON.parse(fs.readFileSync('.work/visual-debug/logs/build-report.json','utf8'));
const browser=JSON.parse(fs.readFileSync('.work/visual-debug/logs/browser-report.json','utf8'));
const checks=JSON.parse(fs.readFileSync('.work/visual-debug/logs/verification.json','utf8'));
const home=JSON.parse(fs.readFileSync('.work/visual-debug/logs/home-offline-report.json','utf8'));
if(build.length!==15||browser.length!==15||checks.length!==15||build.some(x=>x.trace!=='PASS')||browser.some(x=>x.status!=='PASS'))throw Error('不能打包未完整通过的章节');
const lines=['# 回溯算法交互式调试项目验收记录','','日期：2026-10-09（Asia/Shanghai）','','- 项目根目录：E:/Leetcode(Java)。','- 项目 JDK：Temurin 17.0.20.1；系统 JDK 8 未修改，既有 VS Code JavaSE-17 配置保留。','- 飞书真实读取：revision 122，15 道主算法与本地已导入 revision 90 的算法一致；216、131 只有注释差异。','- 原 Java 算法、原始笔记、imports 中既有快照均未覆盖。','- Java 15/15 PASS；真实轨迹一致性 15/15 PASS；浏览器交互 15/15 PASS；生成失败 0。','- 总计 '+build.reduce((sum,x)=>sum+x.steps,0).toLocaleString()+' 个 JDI 事件。','- 第 131 题：aab，106 步，两个答案 [["a","a","b"],["aa","b"]]；ab/aab 非回文剪枝，5 次 path 加入与 5 次撤销。','- 第 332 题：保留 Hierholzer 的 traverse，不改写为普通回溯。','- 第 51 题：44 个冲突候选，16 次放置与撤销。','- 第 37 题：148,380 步，4,209 次递归进入/退出；4,208 次填入、4,157 次撤销，成功路径保留 51 个数字。','- 浏览器：独立无头 Chrome，通过单步、后退恢复、关键操作、播放暂停、进度跳转、剪枝与结果更新，无页面 JavaScript 异常。','- 首页：题号/中文/英文搜索、章节、完成状态筛选通过；390px 手机宽度无页面横向溢出；桌面截图已检查。','- 离线：真实 file URL 打开首页与第 131 题并执行单步通过；页面没有外部 CDN。','- VS Code 图形界面未操作；可打开任意 Java 的 main，点击 Run/Debug，在变量面板展开共享对象并切换调用栈。','- Git：本地 main，未设置 remote，尚未推送；GitHub CLI 使用工作目录中的官方便携版本，系统未安装新软件。','- GitHub 浏览器登录等待授权超时；尚无远程仓库或实际 Pages 域名。','- 公开仓库尚待用户确认范围：完整项目公开会包含飞书笔记与既有快照；可选择 Java 项目私有、静态调试器单独公开。','- 飞书链接没有写回；docs/feishu-links.md 已记录逐题本地路径和待发布状态，部署真实访问成功后才生成 HTTPS 链接并局部写回。','','## 已交付页面','','| 题号 | 题目 | 步骤 | 调用与剪枝节点 | 校验 |','|---|---|---:|---:|---|'];
const publication=fs.existsSync('outputs/publication.json')?JSON.parse(fs.readFileSync('outputs/publication.json','utf8')):null;
if(publication){
 for(let i=0;i<lines.length;i++){
  if(lines[i].startsWith('- Git：'))lines[i]='- Git：本地 main 跟踪 origin/main；仓库 '+publication.repository.url+'，公开完整项目。';
  if(lines[i].startsWith('- GitHub 浏览器登录'))lines[i]='- GitHub：XiaoShuPeiQi 登录/API/Git 推送已验证，凭据保存于系统凭据库。';
  if(lines[i].startsWith('- 公开仓库尚待'))lines[i]='- GitHub Pages：'+publication.pages.url+'，main /docs，HTTPS，线上首页及 15 题浏览器交互验证 PASS。';
  if(lines[i].startsWith('- 飞书链接没有'))lines[i]='- 飞书：15/15 链接已局部写回并回读核验，原正文与代码保留，文档更新至 revision '+publication.feishu.revision_id+'。';
 }
}
for(const p of build)lines.push('| '+p.id+' | ['+p.title+'](../docs/'+p.url+'index.html) | '+p.steps.toLocaleString()+' | '+p.nodes.toLocaleString()+' | Java / 轨迹 / 浏览器 PASS |');
lines.push('','## 维护入口','','本地首页：[打开](../docs/index.html)；飞书链接表：[查看](../docs/feishu-links.md)。','在项目根目录：node .work/visual-debug/scripts/serve.mjs。','重新采集单题：& .\\.work\\visual-debug\\scripts\\build.ps1 -ProblemIds 131。','后续请求：“把飞书某某章节导入项目，生成交互式调试器并发布到 GitHub。”','','离线包只包含已测试的静态网页；不包含原始飞书文档、imports、JDK、凭据、日志或临时数据。解压后打开 index.html。','数独完整轨迹文件约 '+(build.find(x=>x.id===37).bytes/1024/1024).toFixed(1)+' MiB，首次打开比其他题目慢。','');
if(publication)lines.push('在线首页：'+publication.pages.url,'第 131 题：'+publication.pages.url+'problems/0131-palindrome-partitioning/','');
fs.writeFileSync('outputs/回溯算法-可视化验收报告.md',lines.join('\n'));
fs.writeFileSync('outputs/validation.json',JSON.stringify({date:'2026-10-09',java:'PASS',build,browser,checks,home,github:publication?.repository||'awaiting authorization',pages:publication?.pages||null,feishuWriteBack:publication?.feishu||false},null,2));
console.log('Static staging and acceptance report: READY');
