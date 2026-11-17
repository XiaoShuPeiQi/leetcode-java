(() => {
    const article = document.getElementById('problem-content');
    if (!article) return;
    const source = document.getElementById('java-source');
    const classroomFallback = article.innerText + '\n\n' + [...article.querySelectorAll('a')].map(a => a.textContent + ': ' + a.href).join('\n');
    const classroomButton = article.querySelector('.copy-classroom');
    const main = document.querySelector('#app main');
    const lesson = main?.querySelector('.lesson');
    const buttons = [...article.querySelectorAll('.copy-java')];
    if (lesson) {
        article.querySelector('.problem-fallback-title').hidden = true;
        lesson.after(article.querySelector('.problem-statement'));
        main.append(article);
        const codeTitle = document.getElementById('code')?.previousElementSibling;
        if (codeTitle?.classList.contains('panel-title')) {
            const button = buttons[0].cloneNode(true);
            button.classList.add('copy-java-inline');
            codeTitle.append(button);
            buttons.push(button);
        }
    }

    async function copyJava() {
        if (navigator.clipboard?.writeText) {
            try {
                await navigator.clipboard.writeText(source.textContent);
                return true;
            } catch {
                // Local pages and some browsers require the selection fallback.
            }
        }
        const selection = window.getSelection();
        const range = document.createRange();
        range.selectNodeContents(source);
        selection.removeAllRanges();
        selection.addRange(range);
        if (document.execCommand('copy')) {
            selection.removeAllRanges();
            return true;
        }
        source.scrollIntoView({block: 'nearest'});
        return false;
    }

    for (const button of buttons) {
        button.hidden = false;
        let reset;
        button.addEventListener('click', async () => {
            clearTimeout(reset);
            button.textContent = await copyJava() ? '已复制' : '已选中，请手动复制';
            reset = setTimeout(() => { button.textContent = '复制 Java'; }, 2200);
        });
    }
    if (classroomButton) {
        classroomButton.hidden = false;
        classroomButton.addEventListener('click', async () => {
            let text = classroomFallback;
            try {
                const response = await fetch('problem.txt');
                if (response.ok) text = await response.text();
            } catch { /* File URLs can still copy the rendered classroom. */ }
            let copied = false;
            try { await navigator.clipboard.writeText(text); copied = true; } catch {
                const textarea = document.createElement('textarea');
                textarea.value = text;
                textarea.style.cssText = 'position:fixed;left:0;top:0;width:1px;height:1px;opacity:0';
                document.body.append(textarea);
                textarea.select();
                try { copied = document.execCommand('copy'); } catch { /* Offer the text link below. */ }
                textarea.remove();
            }
            classroomButton.textContent = copied ? '已复制课堂内容' : '请打开完整课堂文本复制';
            setTimeout(() => { classroomButton.textContent = '复制课堂内容'; }, 2400);
        });
    }
})();
