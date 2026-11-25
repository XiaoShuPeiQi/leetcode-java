# 课堂正文发布

发布时把完整题意、原题示例、实际解法、状态说明、来源与回放说明预先写入 HTML。交互播放器使用的输入列在「执行回放」。

`problem-statements.json` 按题号保存题面。同题的章节页与 Hot100 页读取同一份原题。力扣题面取自 [Doocs/leetcode](https://github.com/doocs/leetcode)，卡码网题面取自 [代码随想录](https://github.com/youngyangyang04/leetcode-master) 各题的题目部分。每条记录的 `github` 指向具体提交的原文，`official` 指向原题，`html` 和 `text` 分别用于页面和可复制文本。导入时保留题面、示例、解释、限制和进阶要求，调整网页标签和配图链接以适应课堂页面。Doocs 来源内容沿用上游的 [CC BY-SA 4.0 许可](https://github.com/doocs/leetcode/blob/main/LICENSE)。

- 每页 `problem.txt`：课堂文字与 Java 代码。
- 每页 `trace.json`：与播放器完全一致的所有原始轨迹数据，包含全部暂停事件和共享状态池。大轨迹不塞入正文，避免正文被截断。
- `docs/readable/<页面目录名>.md`：同一正文的 GitHub 镜像，在 Pages 无法访问时可直接分享。
- 首页静态目录、`sitemap.xml`、`llms.txt`：辅助发现内容；不是 ChatGPT 必定访问或完整读取的保证。
- “复制课堂内容”：直接把课堂文字交给聊天工具，文本明确链接单独的完整轨迹。

修改题目或轨迹后，从仓库根目录执行（以下为 PowerShell）：

```powershell
New-Item -ItemType Directory -Force .work/pages | Out-Null
Copy-Item docs/index.html,docs/.nojekyll .work/pages/
Copy-Item docs/assets,docs/problems .work/pages/ -Recurse -Force
node .github/scripts/enrich-problem-pages.mjs .work/pages
node .github/scripts/verify-readable-pages.mjs .work/pages
```

每次都从原始模板重新复制后再生成，不对已经增强的页面重复运行。将更新后的 `docs/readable` 与源文件一并提交。主分支相关改动会自动构建、核验和部署，镜像未同步时部署失败。

外部飞书文档仍可能需要登录；本流程只导出网页已公开的内容，不读取或公开额外的飞书内容。网站无法强制聊天产品使用联网工具、遵循链接或超出其上下文容量读取文件。
