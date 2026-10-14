(() => {
    'use strict';

    const root = document.documentElement;
    const escapeHTML = text => text.replace(/[&<>"']/g, character => ({
        '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;'
    }[character]));
    const declarations = new Set(['class', 'public', 'private', 'protected', 'static', 'final', 'new', 'true', 'false', 'null']);
    const controls = new Set(['for', 'if', 'else', 'return', 'while', 'do', 'break', 'continue', 'switch', 'case', 'try', 'catch', 'throw']);
    const types = new Set(['int', 'boolean', 'char', 'long', 'double', 'float', 'byte', 'short', 'void', 'String', 'Integer', 'Solution', 'TreeNode', 'ListNode', 'Node']);
    const rows = [...document.querySelectorAll('#code .code-line > code')].map(element => ({
        element, original: element.innerHTML, text: element.textContent
    }));

    function highlight(text) {
        const tokens = /\/\/.*$|"(?:\\.|[^"\\])*"|'(?:\\.|[^'\\])*'|\b[A-Za-z_$][\w$]*\b|\b\d+(?:\.\d+)?\b/g;
        let html = '', end = 0;
        for (const match of text.matchAll(tokens)) {
            html += escapeHTML(text.slice(end, match.index));
            const token = match[0];
            const kind = token.startsWith('//') ? 'comment'
                : token.startsWith('"') || token.startsWith("'") ? 'string'
                : /^\d/.test(token) ? 'number'
                : declarations.has(token) ? 'keyword'
                : controls.has(token) ? 'control'
                : types.has(token) ? 'type'
                : /^\s*\(/.test(text.slice(match.index + token.length)) ? 'method'
                : 'identifier';
            html += '<span class="java-token-' + kind + '">' + escapeHTML(token) + '</span>';
            end = match.index + token.length;
        }
        return html + escapeHTML(text.slice(end));
    }

    function apply() {
        const light = root.dataset.theme === 'light';
        for (const row of rows) row.element.innerHTML = light ? highlight(row.text) : row.original;
    }
    document.addEventListener('classroomthemechange', apply);
    apply();
})();
