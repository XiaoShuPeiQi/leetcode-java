import assert from 'node:assert/strict';
import {readFileSync, readdirSync} from 'node:fs';
import {join} from 'node:path';
import {runInNewContext} from 'node:vm';

const pages = process.argv[2];
assert(pages, 'Pass the staged Pages directory.');
const home = readFileSync(join(pages, 'index.html'), 'utf8');
const sitemap = readFileSync(join(pages, 'sitemap.xml'), 'utf8');
const statements = JSON.parse(readFileSync(new URL('problem-statements.json', import.meta.url), 'utf8'));
let count = 0;
for (const slug of readdirSync(join(pages, 'problems'))) {
    const directory = join(pages, 'problems', slug);
    const context = {window: {}};
    runInNewContext(readFileSync(join(directory, 'trace.js'), 'utf8'), context, {timeout: 5000});
    const trace = Object.values(context.window)[0];
    const exported = JSON.parse(readFileSync(join(directory, 'trace.json'), 'utf8'));
    assert.equal(JSON.stringify(exported), JSON.stringify(trace), `${slug}: incomplete trace export`);
    const text = readFileSync(join(directory, 'problem.txt'), 'utf8');
    assert.equal(text, readFileSync(join('docs/readable', slug + '.md'), 'utf8'));
    for (const field of ['title', 'input', 'actual', 'expected', 'testSource', 'sourceStatus', 'stateMeaning', 'transition', 'sourceHash']) {
        if (trace[field]) assert(text.includes(String(trace[field])), `${slug}: missing ${field}`);
    }
    // Every original algorithm body line must survive export (only the outer class declaration is adapted).
    for (const line of trace.code.slice(1)) {
        if (line.trim()) assert(text.includes(line.replace(/^ {4}/, '')), `${slug}: missing code line`);
    }
    const html = readFileSync(join(directory, 'index.html'), 'utf8');
    const statement = statements[String(trace.id)];
    const statementSection = html.match(/<section class="problem-statement"[\s\S]*?<\/section>/)?.[0];
    assert(statementSection?.includes(statement.html), `${slug}: missing full GitHub statement in HTML`);
    assert(statementSection.includes(statement.github), `${slug}: missing GitHub statement source link`);
    assert(!statementSection.includes('回放输入'), `${slug}: playback input belongs in classroom notes`);
    assert(text.includes(`## 题意\n\n${statement.text}\n\n官方原题：${statement.official}\n\nGitHub 题面来源：${statement.github}\n\n## 回放用例`),
        `${slug}: missing full GitHub statement in text export`);
    assert.equal((html.match(/id="problem-content"/g) || []).length, 1);
    for (const required of ['课堂说明', 'Java 解法', 'trace.json', 'problem.txt', 'copy-classroom', 'rel="canonical"']) {
        assert(html.includes(required), `${slug}: missing static ${required}`);
    }
    assert(home.includes(`href="problems/${slug}/"`), `${slug}: missing home link`);
    assert(sitemap.includes(`/problems/${slug}/`), `${slug}: missing sitemap entry`);
    count++;
}
console.log(`Verified ${count} classrooms with full GitHub statements, text mirrors and lossless trace exports.`);
