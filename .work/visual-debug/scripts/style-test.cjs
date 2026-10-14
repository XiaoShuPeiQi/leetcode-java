const fs = require('fs'), path = require('path'), vm = require('vm'), assert = require('assert/strict');
const {chromium} = require(path.join(process.env.CODEX_TEST_MODULES, 'playwright'));
const base = process.env.PREVIEW_URL || 'http://127.0.0.1:8787';
const context = {window: {}};
vm.runInNewContext(fs.readFileSync('docs/assets/catalog.js', 'utf8'), context);
const ids = process.argv.slice(2).map(Number);
const catalog = context.window.CATALOG.filter(problem => problem.complete && (!ids.length || ids.includes(problem.id)));
const out = '.work/visual-debug/previews/styles';
fs.mkdirSync(out, {recursive: true});
const palette = {keyword: 'rgb(0, 0, 255)', control: 'rgb(176, 0, 204)', type: 'rgb(0, 111, 137)',
    identifier: 'rgb(0, 16, 128)', method: 'rgb(121, 94, 38)', number: 'rgb(0, 118, 94)', comment: 'rgb(0, 120, 0)', string: 'rgb(121, 94, 38)'};

(async () => {
    const browser = await chromium.launch({channel: 'msedge', headless: true});
    const page = await browser.newPage({viewport: {width: 1440, height: 1080}});
    const errors = [], failedRequests = [], report = [];
    page.on('pageerror', error => errors.push(error.message));
    page.on('response', response => {if (response.status() >= 400) failedRequests.push(response.url());});
    await page.addInitScript(() => {
        localStorage.setItem('zhan-classroom-theme', 'light');
        window.__classroomFonts = new Set();
        window.__classroomLabels = [];
        const clear = CanvasRenderingContext2D.prototype.clearRect;
        CanvasRenderingContext2D.prototype.clearRect = function (...args) {
            if (this.canvas.id === 'tree') window.__classroomLabels = [];
            return clear.apply(this, args);
        };
        const original = CanvasRenderingContext2D.prototype.fillText;
        CanvasRenderingContext2D.prototype.fillText = function (...args) {
            window.__classroomFonts.add(this.font);
            if (this.canvas.id === 'tree') {
                const point = this.getTransform().transformPoint(new DOMPoint(args[1], args[2]));
                window.__classroomLabels.push({label: args[0], x: point.x / devicePixelRatio, y: point.y / devicePixelRatio});
            }
            return original.apply(this, args);
        };
    });
    const close = (actual, expected, label) => assert(Math.abs(actual - expected) < 1.5, `${label}: ${actual} != ${expected}`);
    const widths = () => page.locator('.workspace > .panel').evaluateAll(panels => panels.map(panel => panel.getBoundingClientRect().width));
    const state = () => page.evaluate(() => ({step: document.querySelector('#progress').value,
        variables: document.querySelector('#variables').textContent, stack: document.querySelector('#stack').textContent,
        result: document.querySelector('#result').textContent, current: document.querySelector('.code-line.current')?.dataset.line}));
    async function drag(index, distance) {
        const handle = page.locator('.classroom-splitter').nth(index), box = await handle.boundingBox();
        await page.mouse.move(box.x + box.width / 2, box.y + box.height / 2);
        await page.mouse.down();
        await page.mouse.move(box.x + box.width / 2 + distance, box.y + box.height / 2, {steps: 5});
        await page.mouse.up();
        assert.equal(await page.locator('.classroom-panel-dragging').count(), 0);
    }
    async function canvasFits() {
        if (!await page.locator('#tree').count()) return;
        await page.waitForFunction(() => {
            const canvas = document.querySelector('#tree'), panel = document.querySelector('#tree-scroll');
            return Math.abs(canvas.width / devicePixelRatio - panel.clientWidth) < 1.5;
        });
        const fonts = await page.evaluate(() => [...window.__classroomFonts]);
        assert(fonts.length && fonts.every(font => font.includes('Microsoft YaHei')), '画布中文使用微软雅黑');
    }
    await page.goto(base + '/?preview=classroom-styles');
    assert.equal(await page.locator('.card').count(), 70);
    assert((await page.locator('body').evaluate(element => getComputedStyle(element).fontFamily)).startsWith('"Microsoft YaHei"'));
    await page.locator('#search').fill('分发饼干');
    assert.equal(await page.locator('.card').count(), 1);
    await page.locator('#search').fill('');
    await page.locator('.theme-toggle').click();
    await page.screenshot({path: out + '/home-dark.png', fullPage: true});
    await page.locator('.theme-toggle').click();
    await page.screenshot({path: out + '/home-light.png', fullPage: true});
    for (const problem of catalog) {
        await page.setViewportSize({width: 1440, height: 1080});
        await page.goto(base + '/' + problem.url + '?preview=classroom-styles');
        await page.waitForSelector('.java-token-keyword');
        const source = await page.evaluate(() => (window.TRACE || window.TREE_TRACE || window.GREEDY_TRACE).code.map(line => line.replace(/^ {4}/, '')));
        assert.deepEqual(await page.locator('.code-line > code').allTextContents(), source, problem.id + ' 代码原文');
        assert.equal(await page.locator('.classroom-splitter').count(), 2, problem.id + ' 两条分界');
        const appearance = await page.evaluate(() => {
            const code = getComputedStyle(document.querySelector('.code-line > code'));
            return {size: code.fontSize, weight: code.fontWeight, plain: code.color,
                ui: getComputedStyle(document.querySelector('h1')).fontFamily,
                tokens: Object.fromEntries([...document.querySelectorAll('[class^="java-token-"]')]
                    .map(element => [element.className.replace('java-token-', ''), getComputedStyle(element).color]))};
        });
        assert.equal(appearance.size, '13px'); assert.equal(appearance.weight, '600');
        assert.equal(appearance.plain, 'rgb(31, 35, 40)');
        assert(appearance.ui.startsWith('"Microsoft YaHei"'));
        for (const [kind, color] of Object.entries(appearance.tokens)) assert.equal(color, palette[kind]);
        const original = await widths(), before = await state();
        await drag(0, 60);
        const first = await widths();
        close(first[0], original[0] + 60, '代码变宽'); close(first[1], original[1] - 60, '中间变窄'); close(first[2], original[2], '调用栈不变');
        await drag(1, -30);
        const second = await widths();
        close(second[0], first[0], '代码不变'); close(second[1], first[1] - 30, '中间变窄'); close(second[2], first[2] + 30, '调用栈变宽');
        await canvasFits();
        assert.deepEqual(await state(), before, '拖动不改变调试状态');
        await page.keyboard.press('ArrowRight');
        close((await widths())[1], second[1] + 10, '键盘微调');
        assert.deepEqual(await state(), before, '分界方向键不触发下一步');
        await page.locator('.classroom-splitter').nth(0).dblclick();
        (await widths()).forEach((value, index) => close(value, original[index], '恢复默认'));
        await page.locator('#next').click(); assert(Number(await page.locator('#progress').inputValue()) > 0);
        await page.locator('#prev').click(); assert.deepEqual(await state(), before);
        await page.locator('.theme-toggle').click();
        assert.equal(await page.locator('.java-token-keyword').count(), 0);
        assert.equal(await page.locator('.code-line > code').first().evaluate(element => getComputedStyle(element).fontWeight), '600');
        assert.deepEqual(await page.locator('.code-line > code').allTextContents(), source);
        await drag(0, 40); await canvasFits();
        if ([131, 104, 455].includes(problem.id)) await page.screenshot({path: `${out}/${problem.id}-dark.png`, fullPage: true});
        await page.locator('#progress').evaluate(element => {element.value = element.max; element.dispatchEvent(new Event('input', {bubbles: true}));});
        assert((await page.locator('#result').textContent()).includes('PASS'), problem.id + ' 回放至终点');
        await page.locator('#reset').click();
        await page.locator('.theme-toggle').click();
        assert.deepEqual(await page.locator('.code-line > code').allTextContents(), source);
        if ([131, 104, 455].includes(problem.id)) await page.screenshot({path: `${out}/${problem.id}-light.png`, fullPage: true});
        for (const width of [1100, 390]) {
            await page.setViewportSize({width, height: 900});
            await page.waitForFunction(() => !document.querySelector('.workspace').style.gridTemplateColumns);
            assert.equal(await page.locator('.classroom-splitter').first().isVisible(), false);
            assert(await page.evaluate(() => document.documentElement.scrollWidth <= innerWidth + 1), problem.id + ' 窄屏无横向溢出');
            await canvasFits();
        }
        report.push({id: problem.id, chapter: problem.chapter, colors: 'PASS', fonts: 'PASS', source: 'UNCHANGED', drag: 'PASS', canvas: 'PASS', playback: 'PASS', responsive: 'PASS'});
        if (report.length % 10 === 0) console.log(`Verified ${report.length}/${catalog.length} debugger pages`);
    }
    // 重绘后仍可点击树节点查看变量，继续拖动保留节点详情。
    for (const [url, label, title] of [['0131-palindrome-partitioning', '[]', '历史'], ['0104-maximum-depth-of-binary-tree', '4', '当前字段']]) {
        await page.setViewportSize({width: 1440, height: 1080});
        await page.goto(base + '/problems/' + url + '/?preview=classroom-styles');
        if (title === '历史') await page.locator('#progress').evaluate(element => {
            element.value = 10; element.dispatchEvent(new Event('input', {bubbles: true}));
        });
        await drag(0, 70); await canvasFits();
        const point = await page.evaluate(label => window.__classroomLabels.find(item => item.label === label), label);
        assert(point, '节点已重绘');
        const bounds = await page.locator('#tree').boundingBox();
        await page.mouse.click(bounds.x + point.x, bounds.y + point.y);
        assert((await page.locator('#frame-title').textContent()).includes(title), '重绘后节点点击位置正确');
        const selected = await page.locator('#frame-title').textContent();
        await drag(1, -20); await canvasFits();
        assert.equal(await page.locator('#frame-title').textContent(), selected, '拖动保留节点详情');
    }
    // 边界拖动、取消与桌面布局恢复在代表页面检查。
    await page.setViewportSize({width: 1440, height: 1080});
    await page.goto(base + '/problems/0455-assign-cookies/?preview=classroom-styles');
    await drag(0, -3000); close((await widths())[0], 260, '代码最小宽度');
    await drag(0, 3000); close((await widths())[1], 240, '中间最小宽度');
    await drag(1, 3000); close((await widths())[2], 200, '调用栈最小宽度');
    const handle = page.locator('.classroom-splitter').first(), box = await handle.boundingBox();
    await page.mouse.move(box.x + 6, box.y + 50); await page.mouse.down();
    await handle.dispatchEvent('pointercancel');
    assert.equal(await page.locator('.classroom-panel-dragging').count(), 0); await page.mouse.up();
    for (const width of [1280, 1100, 390, 1440]) {
        await page.setViewportSize({width, height: 900});
        await page.waitForTimeout(50);
        assert(await page.evaluate(() => document.documentElement.scrollWidth <= innerWidth + 1));
    }
    await drag(0, 20);
    assert.deepEqual(errors, []); assert.deepEqual(failedRequests, []);
    fs.mkdirSync('.work/visual-debug/logs', {recursive: true});
    fs.writeFileSync('.work/visual-debug/logs/style-report.json', JSON.stringify({base, home: 'PASS', report, nodeClicks: 'PASS', minimums: 'PASS', cancellation: 'PASS', errors, failedRequests}, null, 2));
    console.log(`PASS: homepage and ${report.length} debuggers; both themes, draggable dividers, canvas resize, playback, and responsive layout.`);
    await browser.close();
})().catch(error => {console.error(error); process.exit(1);});
