import fs from 'node:fs';
const meta=JSON.parse(fs.readFileSync('imports/二叉树算法.import.json','utf8'));
const report=JSON.parse(fs.readFileSync('.work/binarytree/logs/build-report.json','utf8'));
const fence='```';
const lines=['# 二叉树算法导入与 Debug 记录','','来源：['+meta.source_url+']('+meta.source_url+')。已真实读取 revision '+meta.revision_id+'，原文快照保存在 imports 与 [原文笔记](二叉树算法.md)。','','本次导入 38 题、78 个原文解法。每题一个主文件，第一种递归写法为 Solution，其他写法只更名内部类；保留方法签名、变量、逻辑和注释。逐字核对全部 78 个类体，只适配 package/import、外层 public 类、内部 static、main 与必要节点。429/559 的 Node 和 116/117 的 Node 在各题内部定义，避免同名类型混用。','','38 个 HTML 页面只回放原文第一种递归写法。所有状态来自 Java 17 JDI；每个暂停位置读取全部算法栈帧与堆字段，引用用节点身份区分。树结构和递归调用树均为根在上、孩子在下；同层左右位置保持不变，next 以横向虚线表示。树的运行适配也按层序从上到下构建。构造题仍执行原文的递归顺序（如先左子树再右子树），布局方向不改变算法执行顺序。','','使用 .tools/jdk-17，javac --release 17 -encoding UTF-8 -g -Xlint:all 编译全部源码，38 题的 78 个解法全部运行 PASS。递归进入/返回平衡，最终 JDI 返回值与实际 Java 输出逐题一致。next 按节点身份验证每层链和末尾 null；LCA 必须返回原树中的祖先节点；树按结构和值校验。无序路径排序副本比较并保留重复数量。','','VS Code：在 Solution 的递归方法第一条可执行语句打断点，点击 main 上方 Debug；展开变量面板的 root/node，再展开 left/right。切换调用栈帧观察各层参数、子树返回值和共享字段，无需逐项添加监视。VS Code 图形界面未实际操作验证。','','| 题号 | 题目 | 原文节 | Java | 递归 HTML | 原文解法数 | 递归步数 |','|---|---|---|---|---|---|---|'];
for(const p of meta.problems){const r=report.find(x=>x.id===p.id);lines.push('| '+p.id+' | '+p.title+' | '+p.section+' | [源码](../'+p.java_file+') | [调试器](problems/'+p.slug+'/) | '+p.variants.length+' | '+r.steps+' |');}
for(const p of meta.problems){
 const log=fs.readFileSync('.work/binarytree/tmp/trace-'+p.id+'.ndjson.stdout','utf8').replace(/\r/g,'');
 lines.push('','<a id="lc'+String(p.id).padStart(4,'0')+'"></a>','## '+p.id+' '+p.title,'','- 原文：'+p.section+'；[飞书笔记]('+meta.source_url+')。','- 输入：`'+p.input+'`。','- 测试来源：'+p.test_source+'。','- 本地解法：'+p.variants.map(v=>v.className+'（'+v.label+'）').join('；')+'。','- HTML：只展示 `Solution.'+p.variants[0].method+'` 及其实际调用的递归方法。','','以下为本次真实运行输出：','',fence+'text',log.trim(),fence);
}
fs.writeFileSync('docs/二叉树算法-导入记录.md',lines.join('\n')+'\n');
let readme=fs.readFileSync('README.md','utf8');
readme=readme.replace('│       └── backtracking/       飞书原文的 15 道题，每题独立入口','│       ├── backtracking/       飞书原文的 15 道题，每题独立入口\n│       └── binarytree/         飞书原文的 38 道题，78 个解法');
readme=readme.replace('按用户要求仅保留回溯章节，已删除其他空章节目录与占位笔记。未来提供实际题目或章节来源时再创建对应目录。没有实际来源时，不批量生成题解。','当前有回溯与二叉树两个真实来源章节。其他章节在提供实际题目或文档后再创建；没有实际来源时，不批量生成题解。');
readme=readme.replace('TreeNode.java、ListNode.java','TreeNode.java、ListNode.java、TreeSupport.java');
const section=['','## 二叉树章节（2026-10-09）','','已读取[飞书二叉树原文]('+meta.source_url+')，导入 38 题，保留 78 个递归/迭代原文解法，全部编译运行 PASS。网页每题只制作第一种递归解法的调试器，树结构与调用树按从上到下布局。','','[二叉树在线目录](https://xiaoshupeiqi.github.io/leetcode-java/?chapter=%E4%BA%8C%E5%8F%89%E6%A0%91) · [原文笔记](docs/二叉树算法.md) · [逐题输入、运行输出与 Debug 记录](docs/二叉树算法-导入记录.md)','','| 题号 | 题目 | Java | 递归调试器 | 来源与校验 |','|---|---|---|---|---|'];
for(const p of meta.problems)section.push('| '+p.id+' | '+p.title+' | ['+p.className+'.java]('+p.java_file+') | [打开](docs/problems/'+p.slug+'/index.html) | [原文 '+p.section+' / PASS](docs/二叉树算法-导入记录.md#lc'+String(p.id).padStart(4,'0')+') |');
section.push('','二叉树输入通过 `TreeSupport.fromLevelOrder` 按层构建，null 保留左右位置。HTML 高亮行、变量、节点身份和返回值来自真实 JDI。38 页均检查单步、回退、进度、栈帧切换、最终结果和纵向树连线；代表性页面另检验移动端布局。VS Code 图形界面需按导入记录手动验证。','');
if(!readme.includes('## 二叉树章节'))readme+=section.join('\n');fs.writeFileSync('README.md',readme);
console.log('README 与逐题真实运行记录已更新。');
