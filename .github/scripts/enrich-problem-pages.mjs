import {readFileSync, readdirSync, writeFileSync, mkdirSync} from 'node:fs';
import {join} from 'node:path';
import {runInNewContext} from 'node:vm';

const pages = process.argv[2];
if (!pages) throw new Error('Pass the directory containing the staged GitHub Pages files.');
const base = 'https://xiaoshupeiqi.github.io/leetcode-java/';
const mirrorDirectory = 'docs/readable';
mkdirSync(mirrorDirectory, {recursive: true});
const replayHelp = '本页回放代表性输入的真实 Java 17 / JDI 暂停快照。高亮行尚未执行，执行后的变化出现在下一暂停位置。可单步、后退、重置、播放、拖动进度和选择调用栈帧；网页不运行任意 Java 输入。';
const traceHelp = '完整轨迹 JSON 与交互页面使用同一份数据：code 为代码，displayStart 为起始行号，steps 为按时间排列的全部暂停事件，pool 为共享状态池。事件中的 frames、heap、roots、stack、vars 等整数索引指向 pool；旧回溯格式的 vars 是键索引/值索引对。calls 或 nodes 保存调用树。';

const statements = new Map(
    readFileSync(new URL('problem-statements.tsv', import.meta.url), 'utf8')
        .trim().split(/\r?\n/).map(line => line.split('\t'))
);
const sourceOverrides = {
    K44: 'https://kamacoder.com/problempage.php?pid=1044',
    K58: 'https://kamacoder.com/problempage.php?pid=1070',
    LCR146: 'https://leetcode.cn/problems/shun-shi-zhen-da-yin-ju-zhen-lcof/'
};
const escapeHtml = value => String(value).replace(/[&<>"']/g, char => ({
    '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;'
})[char]);

function readTrace(file) {
    const context = {window: {}};
    runInNewContext(readFileSync(file, 'utf8'), context, {filename: file, timeout: 5000});
    return Object.values(context.window)[0];
}

function javaSnippet(trace) {
    const javaFile = readFileSync(trace.javaFile, 'utf8');
    const imports = javaFile.match(/^import (?:static )?java\.[^\r\n]+;/gm) ?? [];
    // Older traces include the enclosing project class's unindented closing brace.
    const lines = trace.code.at(-1) === '}' ? trace.code.slice(0, -1) : trace.code;
    const code = lines.map(line => line.replace(/^ {4}/, ''));
    if (!/^static class \w+\s*\{$/.test(code[0])) {
        throw new Error(`Unrecognized solution class in ${trace.javaFile}`);
    }
    // Remove the project wrapper's static modifier for a top-level Java class.
    code[0] = code[0].replace(/^static /, '').replace(/^class DPSolution\b/, 'class Solution');
    return [...imports, ...(imports.length ? [''] : []), ...code].join('\n') + '\n';
}

function renderProblem(trace, statement, source, java, slug) {
    const title = escapeHtml(trace.title);
    const gitHubSource = 'https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/' + trace.javaFile;
    return `
<article class="problem-content" id="problem-content">
    <section class="problem-statement" aria-labelledby="problem-statement-title">
        <h1 class="problem-fallback-title">${escapeHtml(trace.id)} · ${title} — 展老师小课堂</h1>
        <div class="problem-section-title">
            <h2 id="problem-statement-title">题意</h2>
            <div class="problem-actions">
                <a href="${escapeHtml(source)}">官方原题 ↗</a>
                <a href="problem.txt">完整课堂文本</a>
                <a href="https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/docs/readable/${slug}.md">GitHub 文字版</a>
                <button type="button" class="copy-classroom" aria-live="polite" hidden>复制课堂内容</button>
            </div>
        </div>
        <p>${escapeHtml(statement)}</p>
        <dl class="problem-example">
            <dt>课堂示例输入</dt><dd><code>${escapeHtml(trace.input)}</code></dd>
            <dt>预期结果</dt><dd><code>${escapeHtml(trace.expected)}</code></dd>
        </dl>
    </section>
    <section class="problem-notes" aria-labelledby="classroom-notes-title">
        <h2 id="classroom-notes-title">课堂说明</h2>
        <p>${escapeHtml([trace.chapter, trace.group, trace.english].filter(Boolean).join(' · '))}</p>
        ${trace.algorithm ? `<p>解法：${escapeHtml(trace.algorithm)}</p>` : ''}
        ${trace.stateMeaning ? `<h3>状态含义</h3><p>${escapeHtml(trace.stateMeaning)}</p>` : ''}
        ${trace.transition ? `<h3>状态变化</h3><p>${escapeHtml(trace.transition)}</p>` : ''}
        <p>实际结果：<code>${escapeHtml(trace.actual)}</code>；预期结果：<code>${escapeHtml(trace.expected)}</code>。</p>
        <p>测试来源：${escapeHtml(trace.testSource)}</p>
        <p>源码说明：${escapeHtml(trace.sourceStatus)}</p>
        <p><a href="${escapeHtml(trace.source)}">飞书来源笔记</a>（外部文档可能需要登录，本文仅包含本页已发布的内容。）</p>
        <h3>执行回放</h3><p>${replayHelp}</p>
        <p>本例包含 ${trace.steps.length} 个原始暂停事件。<a href="trace.json">完整执行轨迹 JSON（含全部变量、调用栈及对象状态）</a></p>
        <p>${traceHelp}</p>
    </section>
    <section class="problem-java" id="java-solution" aria-labelledby="java-solution-title">
        <div class="problem-section-title">
            <h2 id="java-solution-title">Java 解法</h2>
            <div class="problem-actions">
                <a href="${escapeHtml(gitHubSource)}">GitHub 源码 ↗</a>
                <button type="button" class="copy-java" aria-live="polite" hidden>复制 Java</button>
            </div>
        </div>
        <pre tabindex="0"><code id="java-source" class="language-java">${escapeHtml(java)}</code></pre>
    </section>
</article>
`;
}

const problems = join(pages, 'problems');
const renderedIds = new Set();
const entries = [];
let renderedPages = 0;
for (const entry of readdirSync(problems, {withFileTypes: true})) {
    if (!entry.isDirectory()) continue;
    const directory = join(problems, entry.name);
    const page = join(directory, 'index.html');
    const trace = readTrace(join(directory, 'trace.js'));
    const id = String(trace.id);
    const statement = statements.get(id);
    if (!statement) throw new Error(`Missing statement for ${entry.name} (problem ${id})`);
    const slug = entry.name.match(/^(?:hot100-|dp-)?\d+-(.+)$/)?.[1];
    const source = sourceOverrides[id] ?? `https://leetcode.cn/problems/${slug}/`;
    if (!sourceOverrides[id] && !slug) throw new Error(`Missing source URL for ${entry.name}`);
    const java = javaSnippet(trace);
    const html = readFileSync(page, 'utf8');
    if (!html.includes('<div id="app"></div>')) {
        throw new Error(`Expected app mount in ${page}. Enrich a fresh copy of the source pages.`);
    }
    writeFileSync(page, html
        .replace('</head>', () => `<meta name="description" content="${escapeHtml(statement)}"><meta name="robots" content="index,follow,max-snippet:-1"><link rel="canonical" href="${base}problems/${entry.name}/"><link rel="alternate" type="text/plain" href="problem.txt" title="完整课堂文本"><link rel="stylesheet" href="../../assets/problem-content.css"></head>`)
        .replace('<div id="app"></div>', () => '<div id="app"></div>' + renderProblem(trace, statement, source, java, entry.name))
        .replace('</body>', '<script src="../../assets/problem-content.js"></script></body>'));
    const text = [
        `# ${id} · ${trace.title} — 展老师小课堂`,
        `网页：${base}problems/${entry.name}/`,
        [trace.chapter, trace.group, trace.english].filter(Boolean).join(' · '),
        '## 题意', statement, `官方原题：${source}`,
        '## 课堂示例', `输入：${trace.input}`, `预期结果：${trace.expected}`, `实际结果：${trace.actual}`,
        `测试来源：${trace.testSource}`,
        ...(trace.algorithm ? ['## 解法', trace.algorithm] : []),
        ...(trace.stateMeaning ? ['## 状态含义', trace.stateMeaning] : []),
        ...(trace.transition ? ['## 状态变化', trace.transition] : []),
        '## Java 解法', '```java\n' + java + '```',
        '## 来源与版本', trace.sourceStatus,
        `Java 源码：https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/${trace.javaFile}`,
        `源码指纹：${trace.sourceHash}`,
        `飞书来源笔记：${trace.source}（外部文档可能需要登录，本文仅包含本页已发布的内容。）`,
        '## 执行回放', replayHelp, `原始暂停事件：${trace.steps.length} 个。`, traceHelp,
        `完整轨迹：${base}problems/${entry.name}/trace.json`,
        '本文包含题意、示例、网页已提供的状态说明、Java 解法和来源；逐步变量及调用栈保存在上面的完整轨迹文件中。'
    ].join('\n\n') + '\n';
    writeFileSync(join(directory, 'problem.txt'), text);
    writeFileSync(join(directory, 'trace.json'), JSON.stringify(trace));
    writeFileSync(join(mirrorDirectory, entry.name + '.md'), text);
    entries.push({slug: entry.name, id, title: trace.title});
    renderedIds.add(id);
    renderedPages++;
}
// Ordinary HTML links make every classroom discoverable without running the catalog JS.
const home = join(pages, 'index.html');
const catalog = '<details class="static-catalog"><summary>完整课堂文字目录</summary><ul>' + entries.map(p =>
    `<li><a href="problems/${p.slug}/">${escapeHtml(p.id)} · ${escapeHtml(p.title)} (${escapeHtml(p.slug.startsWith('hot100-') ? 'Hot100' : '章节课堂')})</a></li>`).join('\n') + '</ul></details>';
writeFileSync(home, readFileSync(home, 'utf8').replace('</main>', catalog + '</main>'));
writeFileSync(join(pages, 'sitemap.xml'), '<?xml version="1.0" encoding="UTF-8"?>\n<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">' +
    [base, ...entries.map(p => `${base}problems/${p.slug}/`)].map(url => `<url><loc>${escapeHtml(url)}</loc></url>`).join('\n') + '</urlset>\n');
writeFileSync(join(pages, 'llms.txt'), '# 展老师小课堂\n\nJava 算法课堂。每个题目 URL 的 HTML 已包含正文，无需执行 JavaScript。problem.txt 为课堂文本，trace.json 为完整执行轨迹。\n\n' +
    entries.map(p => `- [${p.id} · ${p.title}](${base}problems/${p.slug}/problem.txt)`).join('\n') + '\n');
console.log(`Published complete classroom text and traces for ${renderedPages} pages (${renderedIds.size} problems).`);
