(() => {
 const catalog=window.CATALOG||[],cards=document.getElementById('cards'),search=document.getElementById('search'),status=document.getElementById('status');
 let chapter='all';
 const esc=s=>String(s).replace(/[&<>"']/g,c=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
 document.getElementById('problem-count').textContent=catalog.length;
 document.getElementById('all-count').textContent=catalog.length;
 document.getElementById('complete-count').textContent=catalog.filter(p=>p.complete).length;
 function render(){const query=search.value.trim().toLocaleLowerCase();const rows=catalog.filter(p=>(chapter==='all'||chapter===p.chapter)&&(!query||(p.id+' '+p.title+' '+p.english).toLocaleLowerCase().includes(query))&&(status.value==='all'||(status.value==='complete')===p.complete));cards.innerHTML=rows.map(p=>'<article class="card"><a class="card-main '+(p.complete?'':'disabled')+'" '+(p.complete?'href="'+p.url+'"':'')+'><div class="card-top"><span class="number">'+String(p.id).padStart(4,'0')+'</span><span class="tag '+(p.complete?'':'pending')+'">'+(p.complete?'可视化已完成':'待生成')+'</span></div><h3>'+esc(p.title)+'</h3><h4>'+esc(p.english)+'</h4><p class="card-input">'+esc(p.input)+'</p></a><div class="card-bottom">'+(p.complete?'<a href="'+p.url+'">打开调试器 →</a>':'<span>尚未生成</span>')+'<a href="'+p.source+'" target="_blank" rel="noreferrer">飞书笔记 ↗</a></div></article>').join('');document.getElementById('no-results').hidden=rows.length>0;}
 search.addEventListener('input',render);status.addEventListener('change',render);
 document.querySelectorAll('[data-chapter]').forEach(button=>button.addEventListener('click',()=>{chapter=button.dataset.chapter;document.querySelectorAll('[data-chapter]').forEach(b=>b.classList.toggle('selected',b===button));render();}));
 render();
})();
