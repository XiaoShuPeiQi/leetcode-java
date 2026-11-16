(() => {
    const article = document.getElementById('problem-content');
    const source = document.getElementById('java-source');
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
})();
