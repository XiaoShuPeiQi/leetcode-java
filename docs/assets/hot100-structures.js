(() => {
 'use strict';
 const ref = value => value?.$ref === undefined ? null : String(value.$ref);
 const esc = value => String(value).replace(/[&<>"']/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
 const marker = (id, color) => '<marker id="' + id + '" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="6" markerHeight="6" orient="auto"><path d="M0 0L10 5L0 10Z" fill="' + color + '"/></marker>';

 function treePositions(ids, heap, roots) {
  const places = new Map();
  let column = 0;
  function visit(id, depth) {
   if (!id || !heap[id] || places.has(id)) return;
   places.set(id, null);
   visit(ref(heap[id].left), depth + 1);
   places.set(id, [48 + column++ * 73, 59 + depth * 78]);
   visit(ref(heap[id].right), depth + 1);
  }
  const children = new Set(ids.flatMap(id => [ref(heap[id].left), ref(heap[id].right)]));
  visit(ref(roots.root), 0);
  ids.filter(id => !children.has(id)).forEach(id => visit(id, 0));
  ids.forEach(id => visit(id, 0));
  return places;
 }

 function chainPositions(ids, heap, data, vars) {
  let ordered = data.nodeIds;
  // 缓存头到尾按当前 next 排列。改线中途脱离主链的节点保留在后面。
  if (data.id === 146) {
   const seen = new Set();
   let id = ref(vars.head);
   while (id && heap[id] && !seen.has(id)) {
    seen.add(id);
    id = ref(heap[id].next);
   }
   ordered = [...seen, ...ids.filter(id => !seen.has(id))];
  }
  return new Map(ordered.map((id, i) => [id, [75 + (i % 4) * 142, 76 + Math.floor(i / 4) * 126]]));
 }

 function edge(x, y, tx, ty, field) {
  if (x === tx && y === ty) return 'M' + (x + 15) + ' ' + (y - 17) + ' c34 -48 -62 -48 -31 0';
  const dx = tx - x, dy = ty - y, len = Math.hypot(dx, dy);
  const a = x + dx * 23 / len, b = y + dy * 23 / len;
  const c = tx - dx * 24 / len, d = ty - dy * 24 / len;
  if (field === 'next' && (dy || (dx > 0 && dx <= 143)) || field === 'left' || field === 'right') {
   return `M${a} ${b} L${c} ${d}`;
  }
  const lift = field === 'random' ? -38 : 37;
  return `M${a} ${b} Q${(x + tx)/2} ${(y + ty)/2 + lift} ${c} ${d}`;
 }

 function draw(container, data, heap, vars, roots = {}) {
  if (!data.nodeIds?.length) return false;
  const ids = data.nodeIds.filter(id => heap[id] && Object.hasOwn(heap[id], 'val'));
  if (!ids.length) return false;
  const tree = data.group === '二叉树';
  const fields = tree ? ['left', 'right'] : ['next', 'random', 'prev'];
  const places = tree ? treePositions(ids, heap, roots) : chainPositions(ids, heap, data, vars);
  const width = Math.max(300, ...ids.map(id => places.get(id)[0] + 65));
  const height = Math.max(140, ...ids.map(id => places.get(id)[1] + 65));
  const labels = new Map(ids.map(id => [id, []]));
  for (const [name, value] of Object.entries(vars)) labels.get(ref(value))?.push(name);
  let svg = `<svg viewBox="0 0 ${width} ${height}" role="img" aria-label="${tree?'二叉树':'链表'}当前引用关系"><defs>`;
  svg += marker('edge-main','var(--accent)') + marker('edge-alt','var(--muted)') + '</defs>';
  for (const id of ids) {
   const [x,y] = places.get(id);
   for (const field of fields) {
    const target = ref(heap[id][field]);
    if (!target || !heap[target] || !places.has(target)) continue;
    const [tx,ty] = places.get(target), alt = field === 'random' || field === 'prev';
    svg += `<path data-edge="${field}" d="${edge(x,y,tx,ty,field)}" fill="none" stroke="${alt?'var(--muted)':'var(--accent)'}" stroke-width="2" ${alt?'stroke-dasharray="5 4"':''} marker-end="url(#edge-${alt?'alt':'main'})"/>`;
    if (tree) svg += `<text x="${(x+tx)/2}" y="${(y+ty)/2-4}" text-anchor="middle">${field==='left'?'L':'R'}</text>`;
   }
  }
  for (const id of ids) {
   const [x,y] = places.get(id), node = heap[id], names = labels.get(id);
   const value = data.id === 146 ? (names.includes('head')?'头':names.includes('tail')?'尾':node.key+':'+node.val) : node.val;
   svg += `<g data-node="${id}"><title>${esc(names.join(', '))}</title><circle cx="${x}" cy="${y}" r="22" fill="${names.length?'var(--canvas-active)':'var(--canvas-fill)'}" stroke="var(--accent)"/><text x="${x}" y="${y+5}" text-anchor="middle">${esc(value)}</text><text x="${x}" y="${y+39}" text-anchor="middle">#${id}</text>`;
   if (names.length) svg += `<text x="${x}" y="${y-31}" text-anchor="middle">${esc(names.slice(0,3).join(' / '))}</text>`;
   svg += '</g>';
  }
  svg += '</svg>';
  const values = id => '#' + id + ' (' + heap[id]?.val + ')';
  const tables = Object.entries(vars).flatMap(([name, value]) => {
   if (value?.$map) return [name + ' · ' + value.$map.map(([k,v]) => (ref(k) ? values(ref(k)) : k) + ' → ' + (ref(v) ? values(ref(v)) : v)).join('，')];
   if (Array.isArray(value)) return [name + ' · [' + value.map(v => ref(v) ? values(ref(v)) : v).join(', ') + ']'];
   if (typeof value === 'number' || typeof value === 'boolean') return [name + ' = ' + value];
   return [];
  });
  const legend = tree ? 'L / R 表示左右引用。高亮节点由当前栈帧引用。' :
   data.id === 138 ? '实线为 next，虚线为 random。#编号区分原节点和副本。' :
   data.id === 146 ? '实线为 next，虚线为 prev。沿头到尾是最近使用到最久未用。' :
   '箭头表示当前 next。#编号标识 Java 节点对象。';
  container.innerHTML = '<p class="state-caption">'+esc(data.stateMeaning)+'</p>'+svg+
   '<p class="state-caption">'+legend+'</p>'+tables.map(line=>'<p class="state-caption">'+esc(line)+'</p>').join('');
  return true;
 }
 window.Hot100Structures = {draw};
})();
