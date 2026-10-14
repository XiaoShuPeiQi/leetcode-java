(() => {
    const workspace = document.querySelector('.workspace');
    if (!workspace) return;
    const panels = [...workspace.querySelectorAll(':scope > .panel')];
    if (panels.length !== 3) return;
    const minimums = [260, 240, 200];
    const desktop = matchMedia('(min-width: 1201px)');
    let sizes = null;
    let drag = null;
    workspace.classList.add('classroom-resizable');

    const handles = panels.slice(0, 2).map((panel, index) => {
        const handle = document.createElement('div');
        handle.className = 'classroom-splitter';
        handle.tabIndex = 0;
        handle.setAttribute('role', 'separator');
        handle.setAttribute('aria-orientation', 'vertical');
        const visualName = panels[1].querySelector('.panel-title > span').textContent;
        handle.setAttribute('aria-label', index === 0 ? `调整代码与${visualName}宽度` : `调整${visualName}与调用栈宽度`);
        handle.title = '左右拖动调整宽度 · 双击恢复默认 · 方向键微调';
        panel.after(handle);
        return handle;
    });

    const widths = () => panels.map(panel => panel.getBoundingClientRect().width);
    const available = () => workspace.clientWidth - 24;

    function updateAria() {
        const current = widths();
        handles.forEach((handle, index) => {
            handle.setAttribute('aria-valuemin', minimums[index]);
            handle.setAttribute('aria-valuemax', Math.round(current[index] + current[index + 1] - minimums[index + 1]));
            handle.setAttribute('aria-valuenow', Math.round(current[index]));
            handle.setAttribute('aria-valuetext', `左侧面板宽度 ${Math.round(current[index])} 像素`);
        });
    }

    function apply() {
        workspace.style.gridTemplateColumns = `${sizes[0]}px 12px ${sizes[1]}px 12px ${sizes[2]}px`;
        updateAria();
    }

    function move(index, delta, initial) {
        const change = Math.max(minimums[index] - initial[index],
            Math.min(delta, initial[index + 1] - minimums[index + 1]));
        sizes = [...initial];
        sizes[index] += change;
        sizes[index + 1] -= change;
        apply();
    }

    function finish() {
        if (!drag) return;
        const {handle, pointerId} = drag;
        drag = null;
        handle.classList.remove('is-dragging');
        document.body.classList.remove('classroom-panel-dragging');
        if (handle.hasPointerCapture(pointerId)) handle.releasePointerCapture(pointerId);
    }

    handles.forEach((handle, index) => {
        handle.addEventListener('pointerdown', event => {
            if (!desktop.matches || event.button !== 0) return;
            event.preventDefault();
            handle.focus({preventScroll: true});
            drag = {handle, pointerId: event.pointerId, index, x: event.clientX, initial: widths()};
            handle.setPointerCapture(event.pointerId);
            handle.classList.add('is-dragging');
            document.body.classList.add('classroom-panel-dragging');
        });
        handle.addEventListener('pointermove', event => {
            if (drag?.handle === handle && drag.pointerId === event.pointerId) {
                move(index, event.clientX - drag.x, drag.initial);
            }
        });
        ['pointerup', 'pointercancel', 'lostpointercapture'].forEach(type => handle.addEventListener(type, finish));
        handle.addEventListener('keydown', event => {
            if (!desktop.matches || !['ArrowLeft', 'ArrowRight'].includes(event.key)) return;
            event.preventDefault();
            event.stopPropagation();
            move(index, (event.key === 'ArrowLeft' ? -1 : 1) * (event.shiftKey ? 50 : 10), widths());
        });
        handle.addEventListener('dblclick', () => {
            finish();
            sizes = null;
            workspace.style.removeProperty('grid-template-columns');
            updateAria();
        });
    });

    // 窗口变窄时保留原有折行布局，桌面尺寸变化时按比例分配剩余宽度。
    function fit() {
        finish();
        if (!desktop.matches) {
            workspace.style.removeProperty('grid-template-columns');
            return;
        }
        if (sizes) {
            const extra = available() - minimums.reduce((sum, value) => sum + value, 0);
            const weights = sizes.map((size, index) => Math.max(0, size - minimums[index]));
            const total = weights.reduce((sum, value) => sum + value, 0);
            sizes = minimums.map((min, index) => min + extra * (total ? weights[index] / total : 1 / 3));
            apply();
        } else updateAria();
    }
    let lastWidth = workspace.clientWidth;
    new ResizeObserver(() => {
        if (workspace.clientWidth === lastWidth) return;
        lastWidth = workspace.clientWidth;
        fit();
    }).observe(workspace);
    desktop.addEventListener('change', fit);
    // 画布尺寸由面板决定；拖动后通知树图重新布局。
    new ResizeObserver(() => document.dispatchEvent(new Event('classroompanelresize'))).observe(panels[1]);
    updateAria();
})();
