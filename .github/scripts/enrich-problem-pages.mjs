import {readFileSync, readdirSync, writeFileSync} from 'node:fs';
import {join} from 'node:path';
import {runInNewContext} from 'node:vm';

const pages = process.argv[2];
if (!pages) throw new Error('Pass the directory containing the staged GitHub Pages files.');

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

function renderProblem(trace, statement, source, java) {
    const title = escapeHtml(trace.title);
    const gitHubSource = 'https://github.com/XiaoShuPeiQi/leetcode-java/blob/main/' + trace.javaFile;
    return `
<article class="problem-content" id="problem-content">
    <section class="problem-statement" aria-labelledby="problem-statement-title">
        <h1 class="problem-fallback-title">${title}</h1>
        <div class="problem-section-title">
            <h2 id="problem-statement-title">题意</h2>
            <div class="problem-actions">
                <a href="${escapeHtml(source)}">官方原题 ↗</a>
                <a href="problem.txt">题意与 Java 纯文本</a>
            </div>
        </div>
        <p>${escapeHtml(statement)}</p>
        <dl class="problem-example">
            <dt>课堂示例输入</dt><dd><code>${escapeHtml(trace.input)}</code></dd>
            <dt>预期结果</dt><dd><code>${escapeHtml(trace.expected)}</code></dd>
        </dl>
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
        .replace('</head>', () => `<meta name="description" content="${escapeHtml(statement)}"><link rel="stylesheet" href="../../assets/problem-content.css"></head>`)
        .replace('<div id="app"></div>', () => '<div id="app"></div>' + renderProblem(trace, statement, source, java))
        .replace('</body>', '<script src="../../assets/problem-content.js"></script></body>'));
    writeFileSync(join(directory, 'problem.txt'),
        [`${id} · ${trace.title}`, '题意', statement, `官方原题 ${source}`,
            '课堂示例输入', trace.input, '预期结果', trace.expected, 'Java 解法', java].join('\n\n'));
    renderedIds.add(id);
    renderedPages++;
}
console.log(`Published static statements and Java for ${renderedPages} pages (${renderedIds.size} problems).`);
