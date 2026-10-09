# LeetCode-Java

使用原生 Java 17 在 VS Code 中学习、运行和单步调试 LeetCode。飞书笔记用于理解算法，本地题目用于验证结果，Debug 用于观察执行过程。三者优先使用同一组输入。

当前项目根目录是 `E:\Leetcode(Java)`，直接打开此目录，无需额外嵌套一层项目。不使用 Maven、Gradle、Spring Boot 或第三方 Java 依赖。

## 项目结构

```text
LeetCode-Java/（当前工作区）
├── src/main/java/
│   ├── common/                 TreeNode.java、ListNode.java、TreeSupport.java
│   └── leetcode/
│       ├── backtracking/       飞书原文的 15 道题，每题独立入口
│       └── binarytree/         飞书原文的 38 道题，78 个解法
├── docs/                       回溯/二叉树原文、逐题导入与 Debug 记录
├── imports/                    原始响应、Markdown 快照、来源和运行记录
├── .vscode/                    Java 工程配置、扩展推荐
├── .tools/jdk-17/              本机 Java 17，忽略提交
├── bin/                        编译输出，忽略提交
├── AGENTS.md                   长期维护规则
├── README.md
└── .gitignore
```

当前有回溯与二叉树两个真实来源章节。其他章节在提供实际题目或文档后再创建；没有实际来源时，不批量生成题解。

## 环境与 Java 17 配置

- VS Code 与 Extension Pack for Java（扩展 ID：`vscjava.vscode-java-pack`）。
- JDK 17，需要同时具备 `java` 和 `javac`。
- UTF-8，Java 使用四个空格缩进。

本机原有默认 JDK 是 `E:\myApps\java\jdk1.8.0_201`。本项目使用独立的 `.tools/jdk-17`，通过 [settings.json](.vscode/settings.json) 的 `java.configuration.runtimes` 指定 `JavaSE-17` 为默认运行时。此设置适用于没有构建工具的 Java 工程，见 [VS Code 官方工程配置说明](https://code.visualstudio.com/docs/java/java-project#_configure-runtime-for-projects)。

VS Code 新建终端也会优先使用该 JDK；已经打开的终端需要关闭后重新创建。项目外的系统 `JAVA_HOME` 和 PATH 不受影响。

在项目根目录的 PowerShell 终端检查：

```powershell
& '.\.tools\jdk-17\bin\java.exe' -version
& '.\.tools\jdk-17\bin\javac.exe' -version
```

如果迁移或复制项目到其他目录，请准备 JDK 17，并修改 `.vscode/settings.json` 中运行时 `path` 和终端 `JAVA_HOME`、`PATH` 的绝对路径。`.tools/` 不会被 Git 保存。

现代 Red Hat Java 扩展的平台版本自带语言服务器所需运行时；项目编译和运行仍使用这里配置的 Java 17。不要将 `java.jdt.ls.java.home` 指向旧 JDK 8 或强行指向 17，参见 [扩展官方运行时说明](https://github.com/redhat-developer/vscode-java#requirements)。

## 在 VS Code 运行

1. 用“文件 → 打开文件夹”打开 `E:\Leetcode(Java)`。
2. 如果当前窗口早于扩展安装，执行 `Developer: Reload Window`，等待 Java 工程加载完成。
3. 打开 [LC0040CombinationSumII.java](src/main/java/leetcode/backtracking/LC0040CombinationSumII.java)。
4. 点击 `main()` 上方的 **Run**。输出应包含输入、Expected、Actual 和 `Result: PASS`。

所有题目都有独立 `main()`，可以打开对应文件后直接运行。公共 `TreeNode` 和 `ListNode` 是数据结构，不是题目入口。

## 单步 Debug

1. 在 `backtracking()` 第一条可执行语句处打断点；也可在 `path.add()`、递归调用、`path.remove()`、同层去重的 `continue` 处打断点。
2. 点击该题 `main()` 上方的 **Debug**，使用 Java 扩展自动识别当前入口。无需为每道题建立 `launch.json`。
3. 展开左侧“变量”面板，自动显示当前作用域内的全部参数、局部变量；不需要逐个添加“监视”。这套方式适用于全部 15 题及后续新增题目。对象字段可通过展开 `this` 或相应对象查看。
4. 在“调用堆栈”切换递归层，区分各层的局部变量；`path` 是多层共享的当前路径。

项目已统一开启行内变量显示、Java 集合的逻辑结构显示以及对象的 toString 值显示：暂停时可在代码旁查看变量值，在“变量”面板展开 path、result、used、board 等数组或集合查看内容。每题可见的变量取决于当前方法和作用域；例如第 40 题的 path / result 是参数，i 进入循环后才可见。切换到 main 的调用栈帧可查看已初始化的 expected、actual 等本地测试变量。首次启用后重新启动一次调试即可。

“监视”列表用于额外固定少量表达式，VS Code 没有通过 settings.json 或 Java launch 配置批量预填该列表的选项。全量观察使用自动的“变量”面板；本项目没有为此创建逐题启动配置。[变量面板说明](https://code.visualstudio.com/docs/debugtest/debugging#_data-inspection)

| 操作 | Windows 快捷键 | 观察目的 |
|---|---|---|
| Step Into | F11 | 在递归调用处进入下一层 |
| Step Over | F10 | 执行当前语句，观察添加、去重、撤销 |
| Step Out | Shift+F11 | 执行完当前层并返回调用者 |
| Continue | F5 | 运行到下一个断点 |

对照 [第 40 题导入与 Debug 记录](docs/回溯算法-导入记录.md#lc0040) 及 [飞书原文](docs/回溯算法.md)，观察 `[] → [1] → [1, 1] → [1] → [] → [2] → []`。在递归调用处按 F11，在集合操作处按 F10，能更清晰地关注自己的算法。第 332 题调试的是原文 Hierholzer 的 `traverse()`，各题变量见导入记录。功能依据 [VS Code 官方 Java 调试说明](https://code.visualstudio.com/docs/java/java-debugging)。

如果没有 Run / Debug：检查扩展是否启用、文件夹是否被识别为 Java 工程，执行 `Java: Configure Java Runtime` 确认 JavaSE-17。配置已选择 Standard 模式。仍有识别问题时可执行 `Java: Clean Java Language Server Workspace` 后重新加载。

## 命令行编译与运行

在项目根目录使用 PowerShell：

```powershell
$javac = '.\.tools\jdk-17\bin\javac.exe'
$java = '.\.tools\jdk-17\bin\java.exe'
$javaSources = @(Get-ChildItem -LiteralPath 'src/main/java' -Recurse -Filter '*.java' | Select-Object -ExpandProperty FullName)
New-Item -ItemType Directory -Path 'bin' -Force | Out-Null
& $javac --release 17 -encoding UTF-8 -g -d bin $javaSources
if ($LASTEXITCODE -ne 0) { throw '编译失败' }
& $java '-Dfile.encoding=UTF-8' -cp bin leetcode.backtracking.LC0040CombinationSumII
if ($LASTEXITCODE -ne 0) { throw '运行或校验失败' }
```

`-g` 保留行号、局部变量等调试信息。以后运行其他题目，只需替换末行中的完整类名。

## 新增题目

1. 先阅读 [AGENTS.md](AGENTS.md)，按题号搜索已有文件，避免重复。
2. 选择一个主要章节，有真实内容时再创建章节目录；文件名使用 `LC` + 至少四位题号 + 英文题名，public 类与文件同名，package 与源码路径一致。
3. 将用户原始算法放入外层类中的 `static class Solution`。多解法可使用 `SolutionRecursive`、`SolutionIterative` 等内部类；保留原始方法签名、变量和递归结构。
4. 在 `main()` 构造一组主要测试数据，优先使用笔记推演的输入，并注明来源。打印 expected、actual、PASS / FAIL，失败时抛出 `AssertionError`。
5. 数组按内容比较，树和链表按结构与值比较；无序组合使用保留重复项数量的比较方式，不使用会丢失重复项的 Set。
6. 实际编译运行后，更新下方索引与对应章节笔记。源代码有错时先报告，由用户决定修改；不能改 expected 来通过测试。

复制回 LeetCode 时，仅提取对应 Solution 实现，去掉本地 package、main、外层包装与 `static`。LeetCode 提供节点定义的题目去掉本地 `common` 导入。

## 导入飞书笔记

已真实读取 [飞书回溯算法笔记](https://acn581cm6pdd.feishu.cn/docx/VSYBdzUkuoIxf3xuSfVcsgKkn3g)，保存 revision 90 的完整响应与 Markdown，并导入下表 15 道题。原始思路、递归三部曲、推演、复杂度和代码保存在 [回溯算法笔记](docs/回溯算法.md)，输入、校验与变量见 [逐题导入记录](docs/回溯算法-导入记录.md)。

第 40 题已按最新要求移除演示版，只保留原文代码。第 77 题原文代码使用 startNum；第 332 题原文实现为 Hierholzer；第 37 题笔记没有具体数独棋盘，使用已核验的 [LeetCode 官方示例 1](https://leetcode.com/problems/sudoku-solver/)。所有适配差异均已记录，不生成原文未提供的迭代实现。

后续任选一种方式提供章节：

1. 发来可访问的飞书 `docx` / `wiki` 链接，优先真实读取；权限不足时明确报告。
2. 将飞书导出的 Markdown 或 Word 文件放入 `imports/`，图片附件一并保留。
3. 直接提供 Java 源码或 Markdown 内容，并注明章节、题号和来源。

收到内容后先提取题号、题名、原始 Java 代码、推演输入与预期结果，给出导入范围；发现重复题号或冲突版本先报告。按章节逐步导入，最小化适配代码，保留原笔记并逐题编译运行、更新索引。原文与代码不一致的地方标注“待核对”，不编造缺失内容。

再次导入已有题号时，先对照原文快照与当前实现，报告版本差异，再按用户要求处理；不能创建第二个同题号主文件，不添加无必要的演示版本。

## 交互式算法调试器（2026-10-09）

打开 [学习首页](docs/index.html)，或直接打开 [第 131 题：分割回文串](docs/problems/0131-palindrome-partitioning/index.html)。所有页面使用相对路径和本地脚本，不依赖 CDN，双击 HTML 也可离线运行。首页支持题号、中文/英文题名搜索、章节和完成状态筛选。

桌面代码与搜索树各占一半，手机按上下排列。支持上一步、下一步、关键操作、播放/暂停、速度、重置和进度拖动。点击调用栈可以切换栈帧；点击树节点可以查看该节点进入或跳过时的历史变量。树保留已访问节点，绿色表示收集过答案，红色虚线表示没有实际递归进入的剪枝候选。棋盘变量以网格显示。

页面回放的是代表性输入的真实 Java 17 执行快照，浏览器不执行 Java，也不支持任意输入。高亮行显示执行前的参数、局部变量与共享对象；集合变更显示在下一暂停位置。方法进入/退出事件单独记录。Java 短路条件可能直接跳到循环行而不经过 continue 的行号，此时根据实际控制流记录跳过候选，并在步骤说明中注明条件所在行。

本次读取飞书 revision 122，15 道主算法与已导入 revision 90 的实现一致，第 216、131 题有注释差异。原 Java 文件、原始笔记和导入快照均保留。第 332 题回放真实 Hierholzer 的 traverse；第 37 题使用已有官方示例棋盘，成功返回时保留解，不要求撤销成功路径。

### 页面结构与轨迹来源

- docs/index.html：可搜索的学习首页。
- docs/assets/：最小共享播放器、样式、题目目录。
- docs/problems/题号-slug/index.html：独立题目页面，路径永久稳定。
- 每题 trace.js：真实行号、变量、调用栈、返回值、树节点和 Java 校验结果；重复快照采用引用池降低体积。
- .work/visual-debug/scripts/TraceRecorder.java：通过 JDK 自带 JDI 调试接口读取原程序，不改写算法、不调用被观察对象的方法。
- .work/visual-debug/tmp、logs、previews：临时轨迹、真实日志和截图，忽略提交。

### 本地预览、重新生成与验证

在项目根目录运行：

~~~powershell
node .work/visual-debug/scripts/serve.mjs
~~~

访问 http://127.0.0.1:8787。也可离线直接打开 docs/index.html。

上述前台命令需要保持终端运行；服务停止后，localhost 会显示“拒绝连接”。也可运行以下命令启动独立的隐藏后台预览：

~~~powershell
& .\.work\visual-debug\scripts\start-preview.ps1
~~~

跨设备优先使用已经发布的 [在线首页](https://xiaoshupeiqi.github.io/leetcode-java/) 和 [第 131 题](https://xiaoshupeiqi.github.io/leetcode-java/problems/0131-palindrome-partitioning/)。它们不依赖本机预览服务。

只重新采集第 131 题：

~~~powershell
& .\.work\visual-debug\scripts\build.ps1 -ProblemIds 131
~~~

重新采集全部题目（数独完整逐行追踪较慢，临时数据较大）：

~~~powershell
& .\.work\visual-debug\scripts\build.ps1
~~~

工具需要 JDK 17 和 Node.js；JDK 可通过 -JdkHome 指定。前端不需要 Node.js。重新生成会保留 Java 原实现并再次执行 main 的 PASS / FAIL 校验。源码哈希改变后必须重新采集，不手改网页轨迹来匹配错误结果。

已有轨迹可以独立核验：

~~~powershell
node .work/visual-debug/scripts/verify.mjs
~~~

浏览器验证工具位于 .work/visual-debug/scripts/browser-test.mjs，需要 Playwright（可通过 CODEX_TEST_MODULES 指定已有公共 node_modules 目录）和 Chrome（可通过 CODEX_TEST_CHROME 指定）。PREVIEW_URL 可指向本地 HTTP、离线 file URL 或已发布的网址。测试包含单步、后退恢复、关键操作、播放暂停、进度跳转、剪枝与结果更新。

新增一题时，先按现有规则导入原 Java 和代表性输入、编译运行，再补充 imports/回溯算法.import.json 的对应关系及 build.mjs 中的稳定 slug。使用 build.ps1 -ProblemIds 题号采集真实执行，验证后更新首页目录和飞书链接表。新增章节时依据实际来源扩展元数据，不创建空章节。

### GitHub 与飞书链接维护

当前本地 Git 分支为 main，跟踪 origin/main；已发布到用户确认的公开仓库 [XiaoShuPeiQi/leetcode-java](https://github.com/XiaoShuPeiQi/leetcode-java)。完整项目包含原飞书笔记及既有快照。GitHub 登录、API 访问和正常 Git 推送已验证，凭据保存在本机系统凭据库，不写入项目。仓库使用 HTTPS，Git 凭据助手仅配置于本项目。

Pages 已采用 Settings → Pages → Deploy from a branch → main /docs，HTTPS 已启用，发布地址由 GitHub API 实际返回。线上首页的搜索与手机布局，以及 15 个题目的单步、后退、播放、关键操作、进度和结果更新都通过浏览器验证。此发布源也托管 docs 下的原始 Markdown 笔记。

若未来只发布调试器，可切换到 GitHub Actions 并手动运行 .github/workflows/deploy-pages.yml；备用工作流只上传 index.html、.nojekyll、assets、problems，当前不自动触发，避免与分支发布冲突。

工作流依据 GitHub 官方 Pages 文档配置：
https://docs.github.com/en/pages/getting-started-with-github-pages/using-custom-workflows-with-github-pages

已实际访问首页和所有题目页，并用真实 HTTPS 域名更新 [飞书链接表](docs/feishu-links.md)。15 个“交互式调试器”链接已局部插入飞书对应题目标题后，并逐题回读确认；原正文和代码保持不变，写回后文档为 revision 137。原始 revision 90 导入快照及原始本地笔记继续保留，不覆盖历史来源。

后续可直接提出：“把飞书某某章节导入项目，生成交互式调试器并发布到 GitHub。”已确认的仓库、可见性和账号授权应继续复用。


## 按章节分类的题目索引

| 章节 | 题号 | 题目 | Java 文件 | 笔记 | 来源 / 状态 |
|---|---|---|---|---|---|
| 回溯 | 77 | 组合 | [LC0077Combinations.java](src/main/java/leetcode/backtracking/LC0077Combinations.java) | [3.1 / Debug](docs/回溯算法-导入记录.md#lc0077) | 飞书原文；PASS |
| 回溯 | 216 | 组合总和 III | [LC0216CombinationSumIII.java](src/main/java/leetcode/backtracking/LC0216CombinationSumIII.java) | [3.2 / Debug](docs/回溯算法-导入记录.md#lc0216) | 飞书原文；PASS |
| 回溯 | 17 | 电话号码的字母组合 | [LC0017LetterCombinationsOfAPhoneNumber.java](src/main/java/leetcode/backtracking/LC0017LetterCombinationsOfAPhoneNumber.java) | [3.3 / Debug](docs/回溯算法-导入记录.md#lc0017) | 飞书原文；PASS |
| 回溯 | 39 | 组合总和 | [LC0039CombinationSum.java](src/main/java/leetcode/backtracking/LC0039CombinationSum.java) | [3.4 / Debug](docs/回溯算法-导入记录.md#lc0039) | 飞书原文；PASS |
| 回溯 | 40 | 组合总和 II | [LC0040CombinationSumII.java](src/main/java/leetcode/backtracking/LC0040CombinationSumII.java) | [3.5 / Debug](docs/回溯算法-导入记录.md#lc0040) | 飞书原文；PASS |
| 回溯 | 131 | 分割回文串 | [LC0131PalindromePartitioning.java](src/main/java/leetcode/backtracking/LC0131PalindromePartitioning.java) | [3.6 / Debug](docs/回溯算法-导入记录.md#lc0131) | 飞书原文；PASS |
| 回溯 | 93 | 复原 IP 地址 | [LC0093RestoreIPAddresses.java](src/main/java/leetcode/backtracking/LC0093RestoreIPAddresses.java) | [3.7 / Debug](docs/回溯算法-导入记录.md#lc0093) | 飞书原文；PASS |
| 回溯 | 78 | 子集 | [LC0078Subsets.java](src/main/java/leetcode/backtracking/LC0078Subsets.java) | [3.8 / Debug](docs/回溯算法-导入记录.md#lc0078) | 飞书原文；PASS |
| 回溯 | 90 | 子集 II | [LC0090SubsetsII.java](src/main/java/leetcode/backtracking/LC0090SubsetsII.java) | [3.9 / Debug](docs/回溯算法-导入记录.md#lc0090) | 飞书原文；PASS |
| 回溯 | 491 | 非递减子序列 | [LC0491NonDecreasingSubsequences.java](src/main/java/leetcode/backtracking/LC0491NonDecreasingSubsequences.java) | [3.10 / Debug](docs/回溯算法-导入记录.md#lc0491) | 飞书原文；PASS |
| 回溯 | 46 | 全排列 | [LC0046Permutations.java](src/main/java/leetcode/backtracking/LC0046Permutations.java) | [3.11 / Debug](docs/回溯算法-导入记录.md#lc0046) | 飞书原文；PASS |
| 回溯 | 47 | 全排列 II | [LC0047PermutationsII.java](src/main/java/leetcode/backtracking/LC0047PermutationsII.java) | [3.12 / Debug](docs/回溯算法-导入记录.md#lc0047) | 飞书原文；PASS |
| 回溯 | 332 | 重新安排行程 | [LC0332ReconstructItinerary.java](src/main/java/leetcode/backtracking/LC0332ReconstructItinerary.java) | [3.13 / Debug](docs/回溯算法-导入记录.md#lc0332) | 飞书原文 Hierholzer；PASS |
| 回溯 | 51 | N 皇后 | [LC0051NQueens.java](src/main/java/leetcode/backtracking/LC0051NQueens.java) | [3.14 / Debug](docs/回溯算法-导入记录.md#lc0051) | 飞书原文；PASS |
| 回溯 | 37 | 解数独 | [LC0037SudokuSolver.java](src/main/java/leetcode/backtracking/LC0037SudokuSolver.java) | [3.15 / Debug](docs/回溯算法-导入记录.md#lc0037) | 飞书原文；官方棋盘；PASS |

## 环境与本次导入验证（2026-10-08）

- 实际使用 Eclipse Temurin JDK `17.0.20.1+1`，`javac 17.0.20.1`。官方 [下载包](https://github.com/adoptium/temurin17-binaries/releases/download/jdk-17.0.20.1%2B1/OpenJDK17U-jdk_x64_windows_hotspot_17.0.20.1_1.zip) 已核对 SHA-256：`e53a79c3c3d86865bd7e787903884331068e71321714ffd44f145785affc7cb0`。
- 已安装 Extension Pack for Java `0.31.1`、Red Hat Java `1.56.0`、Debugger for Java `0.59.0`、Project Manager for Java `0.27.6`；本机 Red Hat Java 平台扩展包含 `jre/`。
- 17 份 Java 源码（15 道题与两个公共节点）通过 `javac --release 17 -encoding UTF-8 -g -Xlint:all`，无编译错误或警告。
- 15 题逐一运行，退出码均为 0，全部输出 `Result: PASS`。完整 expected / actual 见 [真实运行记录](imports/回溯算法.validation.txt)。第 40 题仍使用 `[1, 1, 2]`、目标 `2`，结果 `[[1, 1], [2]]`。
- 逐题比对源码：去掉本地 static 修饰与一层缩进后，15 份 Solution 类体与飞书 Java 代码完全一致。package、文件名、public 类名和链接均已核验，编译包含调试信息。
- VS Code 图形界面的断点、递归单步和变量面板尚未实际操作验证；请按上方“单步 Debug”步骤执行一次。
- 已保存 [完整响应](imports/回溯算法.feishu.json)、[原始 Markdown](imports/回溯算法.source.md)、[来源及输入记录](imports/回溯算法.import.json)；回溯章节以外暂不创建空目录或占位笔记。

## 二叉树章节（2026-10-09）

已读取[飞书二叉树原文](https://acn581cm6pdd.feishu.cn/docx/Xo3odqeiboNchExUhAFcvpEGnng)，导入 38 题，保留 78 个递归/迭代原文解法，全部编译运行 PASS。网页每题只制作第一种递归解法的调试器，树结构与调用树按从上到下布局。

[二叉树在线目录](https://xiaoshupeiqi.github.io/leetcode-java/?chapter=%E4%BA%8C%E5%8F%89%E6%A0%91) · [原文笔记](docs/二叉树算法.md) · [逐题输入、运行输出与 Debug 记录](docs/二叉树算法-导入记录.md)

38 个线上调试器均核对通过，38 个链接已写回飞书对应题目标题下方并回读确认，原文正文和代码块保持一致。详见[飞书写回与在线链接记录](docs/二叉树算法-调试器链接.md)。

| 题号 | 题目 | Java | 递归调试器 | 来源与校验 |
|---|---|---|---|---|
| 144 | 二叉树的前序遍历 | [LC0144BinaryTreePreorderTraversal.java](src/main/java/leetcode/binarytree/LC0144BinaryTreePreorderTraversal.java) | [打开](docs/problems/0144-binary-tree-preorder-traversal/index.html) | [原文 3.1 / PASS](docs/二叉树算法-导入记录.md#lc0144) |
| 94 | 二叉树的中序遍历 | [LC0094BinaryTreeInorderTraversal.java](src/main/java/leetcode/binarytree/LC0094BinaryTreeInorderTraversal.java) | [打开](docs/problems/0094-binary-tree-inorder-traversal/index.html) | [原文 3.2 / PASS](docs/二叉树算法-导入记录.md#lc0094) |
| 145 | 二叉树的后序遍历 | [LC0145BinaryTreePostorderTraversal.java](src/main/java/leetcode/binarytree/LC0145BinaryTreePostorderTraversal.java) | [打开](docs/problems/0145-binary-tree-postorder-traversal/index.html) | [原文 3.3 / PASS](docs/二叉树算法-导入记录.md#lc0145) |
| 102 | 二叉树的层序遍历 | [LC0102BinaryTreeLevelOrderTraversal.java](src/main/java/leetcode/binarytree/LC0102BinaryTreeLevelOrderTraversal.java) | [打开](docs/problems/0102-binary-tree-level-order-traversal/index.html) | [原文 3.4 / PASS](docs/二叉树算法-导入记录.md#lc0102) |
| 107 | 二叉树的层序遍历 II | [LC0107BinaryTreeLevelOrderTraversalII.java](src/main/java/leetcode/binarytree/LC0107BinaryTreeLevelOrderTraversalII.java) | [打开](docs/problems/0107-binary-tree-level-order-traversal-ii/index.html) | [原文 3.5 / PASS](docs/二叉树算法-导入记录.md#lc0107) |
| 199 | 二叉树的右视图 | [LC0199BinaryTreeRightSideView.java](src/main/java/leetcode/binarytree/LC0199BinaryTreeRightSideView.java) | [打开](docs/problems/0199-binary-tree-right-side-view/index.html) | [原文 3.6 / PASS](docs/二叉树算法-导入记录.md#lc0199) |
| 637 | 二叉树的层平均值 | [LC0637AverageOfLevelsInBinaryTree.java](src/main/java/leetcode/binarytree/LC0637AverageOfLevelsInBinaryTree.java) | [打开](docs/problems/0637-average-of-levels-in-binary-tree/index.html) | [原文 3.7 / PASS](docs/二叉树算法-导入记录.md#lc0637) |
| 429 | N 叉树的层序遍历 | [LC0429NAryTreeLevelOrderTraversal.java](src/main/java/leetcode/binarytree/LC0429NAryTreeLevelOrderTraversal.java) | [打开](docs/problems/0429-n-ary-tree-level-order-traversal/index.html) | [原文 3.8 / PASS](docs/二叉树算法-导入记录.md#lc0429) |
| 515 | 在每个树行中找最大值 | [LC0515FindLargestValueInEachTreeRow.java](src/main/java/leetcode/binarytree/LC0515FindLargestValueInEachTreeRow.java) | [打开](docs/problems/0515-find-largest-value-in-each-tree-row/index.html) | [原文 3.9 / PASS](docs/二叉树算法-导入记录.md#lc0515) |
| 116 | 填充每个节点的下一个右侧节点指针 | [LC0116PopulatingNextRightPointersInEachNode.java](src/main/java/leetcode/binarytree/LC0116PopulatingNextRightPointersInEachNode.java) | [打开](docs/problems/0116-populating-next-right-pointers-in-each-node/index.html) | [原文 3.10 / PASS](docs/二叉树算法-导入记录.md#lc0116) |
| 117 | 填充每个节点的下一个右侧节点指针 II | [LC0117PopulatingNextRightPointersInEachNodeII.java](src/main/java/leetcode/binarytree/LC0117PopulatingNextRightPointersInEachNodeII.java) | [打开](docs/problems/0117-populating-next-right-pointers-in-each-node-ii/index.html) | [原文 3.11 / PASS](docs/二叉树算法-导入记录.md#lc0117) |
| 226 | 翻转二叉树 | [LC0226InvertBinaryTree.java](src/main/java/leetcode/binarytree/LC0226InvertBinaryTree.java) | [打开](docs/problems/0226-invert-binary-tree/index.html) | [原文 3.12 / PASS](docs/二叉树算法-导入记录.md#lc0226) |
| 101 | 对称二叉树 | [LC0101SymmetricTree.java](src/main/java/leetcode/binarytree/LC0101SymmetricTree.java) | [打开](docs/problems/0101-symmetric-tree/index.html) | [原文 3.13 / PASS](docs/二叉树算法-导入记录.md#lc0101) |
| 104 | 二叉树的最大深度 | [LC0104MaximumDepthOfBinaryTree.java](src/main/java/leetcode/binarytree/LC0104MaximumDepthOfBinaryTree.java) | [打开](docs/problems/0104-maximum-depth-of-binary-tree/index.html) | [原文 3.14 / PASS](docs/二叉树算法-导入记录.md#lc0104) |
| 559 | N 叉树的最大深度（关联补充） | [LC0559MaximumDepthOfNAryTree.java](src/main/java/leetcode/binarytree/LC0559MaximumDepthOfNAryTree.java) | [打开](docs/problems/0559-maximum-depth-of-n-ary-tree/index.html) | [原文 3.15 / PASS](docs/二叉树算法-导入记录.md#lc0559) |
| 111 | 二叉树的最小深度 | [LC0111MinimumDepthOfBinaryTree.java](src/main/java/leetcode/binarytree/LC0111MinimumDepthOfBinaryTree.java) | [打开](docs/problems/0111-minimum-depth-of-binary-tree/index.html) | [原文 3.16 / PASS](docs/二叉树算法-导入记录.md#lc0111) |
| 222 | 完全二叉树的节点个数 | [LC0222CountCompleteTreeNodes.java](src/main/java/leetcode/binarytree/LC0222CountCompleteTreeNodes.java) | [打开](docs/problems/0222-count-complete-tree-nodes/index.html) | [原文 3.17 / PASS](docs/二叉树算法-导入记录.md#lc0222) |
| 110 | 平衡二叉树 | [LC0110BalancedBinaryTree.java](src/main/java/leetcode/binarytree/LC0110BalancedBinaryTree.java) | [打开](docs/problems/0110-balanced-binary-tree/index.html) | [原文 3.18 / PASS](docs/二叉树算法-导入记录.md#lc0110) |
| 257 | 二叉树的所有路径 | [LC0257BinaryTreePaths.java](src/main/java/leetcode/binarytree/LC0257BinaryTreePaths.java) | [打开](docs/problems/0257-binary-tree-paths/index.html) | [原文 3.19 / PASS](docs/二叉树算法-导入记录.md#lc0257) |
| 404 | 左叶子之和 | [LC0404SumOfLeftLeaves.java](src/main/java/leetcode/binarytree/LC0404SumOfLeftLeaves.java) | [打开](docs/problems/0404-sum-of-left-leaves/index.html) | [原文 3.20 / PASS](docs/二叉树算法-导入记录.md#lc0404) |
| 513 | 找树左下角的值 | [LC0513FindBottomLeftTreeValue.java](src/main/java/leetcode/binarytree/LC0513FindBottomLeftTreeValue.java) | [打开](docs/problems/0513-find-bottom-left-tree-value/index.html) | [原文 3.21 / PASS](docs/二叉树算法-导入记录.md#lc0513) |
| 112 | 路径总和 | [LC0112PathSum.java](src/main/java/leetcode/binarytree/LC0112PathSum.java) | [打开](docs/problems/0112-path-sum/index.html) | [原文 3.22 / PASS](docs/二叉树算法-导入记录.md#lc0112) |
| 113 | 路径总和 II（关联补充） | [LC0113PathSumII.java](src/main/java/leetcode/binarytree/LC0113PathSumII.java) | [打开](docs/problems/0113-path-sum-ii/index.html) | [原文 3.23 / PASS](docs/二叉树算法-导入记录.md#lc0113) |
| 106 | 从中序与后序遍历序列构造二叉树 | [LC0106ConstructBinaryTreeFromInorderAndPostorderTraversal.java](src/main/java/leetcode/binarytree/LC0106ConstructBinaryTreeFromInorderAndPostorderTraversal.java) | [打开](docs/problems/0106-construct-binary-tree-from-inorder-and-postorder-traversal/index.html) | [原文 3.24 / PASS](docs/二叉树算法-导入记录.md#lc0106) |
| 105 | 从前序与中序遍历序列构造二叉树（关联补充） | [LC0105ConstructBinaryTreeFromPreorderAndInorderTraversal.java](src/main/java/leetcode/binarytree/LC0105ConstructBinaryTreeFromPreorderAndInorderTraversal.java) | [打开](docs/problems/0105-construct-binary-tree-from-preorder-and-inorder-traversal/index.html) | [原文 3.25 / PASS](docs/二叉树算法-导入记录.md#lc0105) |
| 654 | 最大二叉树 | [LC0654MaximumBinaryTree.java](src/main/java/leetcode/binarytree/LC0654MaximumBinaryTree.java) | [打开](docs/problems/0654-maximum-binary-tree/index.html) | [原文 3.26 / PASS](docs/二叉树算法-导入记录.md#lc0654) |
| 617 | 合并二叉树 | [LC0617MergeTwoBinaryTrees.java](src/main/java/leetcode/binarytree/LC0617MergeTwoBinaryTrees.java) | [打开](docs/problems/0617-merge-two-binary-trees/index.html) | [原文 3.27 / PASS](docs/二叉树算法-导入记录.md#lc0617) |
| 700 | 二叉搜索树中的搜索 | [LC0700SearchInABinarySearchTree.java](src/main/java/leetcode/binarytree/LC0700SearchInABinarySearchTree.java) | [打开](docs/problems/0700-search-in-a-binary-search-tree/index.html) | [原文 3.28 / PASS](docs/二叉树算法-导入记录.md#lc0700) |
| 98 | 验证二叉搜索树 | [LC0098ValidateBinarySearchTree.java](src/main/java/leetcode/binarytree/LC0098ValidateBinarySearchTree.java) | [打开](docs/problems/0098-validate-binary-search-tree/index.html) | [原文 3.29 / PASS](docs/二叉树算法-导入记录.md#lc0098) |
| 530 | 二叉搜索树的最小绝对差 | [LC0530MinimumAbsoluteDifferenceInBST.java](src/main/java/leetcode/binarytree/LC0530MinimumAbsoluteDifferenceInBST.java) | [打开](docs/problems/0530-minimum-absolute-difference-in-bst/index.html) | [原文 3.30 / PASS](docs/二叉树算法-导入记录.md#lc0530) |
| 501 | 二叉搜索树中的众数 | [LC0501FindModeInBinarySearchTree.java](src/main/java/leetcode/binarytree/LC0501FindModeInBinarySearchTree.java) | [打开](docs/problems/0501-find-mode-in-binary-search-tree/index.html) | [原文 3.31 / PASS](docs/二叉树算法-导入记录.md#lc0501) |
| 236 | 二叉树的最近公共祖先 | [LC0236LowestCommonAncestorOfABinaryTree.java](src/main/java/leetcode/binarytree/LC0236LowestCommonAncestorOfABinaryTree.java) | [打开](docs/problems/0236-lowest-common-ancestor-of-a-binary-tree/index.html) | [原文 3.32 / PASS](docs/二叉树算法-导入记录.md#lc0236) |
| 235 | 二叉搜索树的最近公共祖先 | [LC0235LowestCommonAncestorOfABinarySearchTree.java](src/main/java/leetcode/binarytree/LC0235LowestCommonAncestorOfABinarySearchTree.java) | [打开](docs/problems/0235-lowest-common-ancestor-of-a-binary-search-tree/index.html) | [原文 3.33 / PASS](docs/二叉树算法-导入记录.md#lc0235) |
| 701 | 二叉搜索树中的插入操作 | [LC0701InsertIntoABinarySearchTree.java](src/main/java/leetcode/binarytree/LC0701InsertIntoABinarySearchTree.java) | [打开](docs/problems/0701-insert-into-a-binary-search-tree/index.html) | [原文 3.34 / PASS](docs/二叉树算法-导入记录.md#lc0701) |
| 450 | 删除二叉搜索树中的节点 | [LC0450DeleteNodeInABST.java](src/main/java/leetcode/binarytree/LC0450DeleteNodeInABST.java) | [打开](docs/problems/0450-delete-node-in-a-bst/index.html) | [原文 3.35 / PASS](docs/二叉树算法-导入记录.md#lc0450) |
| 669 | 修剪二叉搜索树 | [LC0669TrimABinarySearchTree.java](src/main/java/leetcode/binarytree/LC0669TrimABinarySearchTree.java) | [打开](docs/problems/0669-trim-a-binary-search-tree/index.html) | [原文 3.36 / PASS](docs/二叉树算法-导入记录.md#lc0669) |
| 108 | 将有序数组转换为二叉搜索树 | [LC0108ConvertSortedArrayToBinarySearchTree.java](src/main/java/leetcode/binarytree/LC0108ConvertSortedArrayToBinarySearchTree.java) | [打开](docs/problems/0108-convert-sorted-array-to-binary-search-tree/index.html) | [原文 3.37 / PASS](docs/二叉树算法-导入记录.md#lc0108) |
| 538 | 把二叉搜索树转换为累加树 | [LC0538ConvertBSTToGreaterTree.java](src/main/java/leetcode/binarytree/LC0538ConvertBSTToGreaterTree.java) | [打开](docs/problems/0538-convert-bst-to-greater-tree/index.html) | [原文 3.38 / PASS](docs/二叉树算法-导入记录.md#lc0538) |

二叉树输入通过 `TreeSupport.fromLevelOrder` 按层构建，null 保留左右位置。HTML 高亮行、变量、节点身份和返回值来自真实 JDI。38 页均检查单步、回退、进度、栈帧切换、最终结果和纵向树连线；代表性页面另检验移动端布局。VS Code 图形界面需按导入记录手动验证。
