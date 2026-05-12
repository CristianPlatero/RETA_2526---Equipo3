const data = {
  a1: { type:'armario', title:'Armario 1', desc:'Herramientas de diagnóstico, destornilladores, llaves.', img:'./assets/Equipo3logo.png', link:'#inventario-armario-1', linkText:'Ver inventario' },
  a2: { type:'armario', title:'Armario 2', desc:'Cables y conectores: HDMI, USB, VGA, adaptadores.',img:'./assets/Equipo3logo.png', link:'#inventario-armario-2', linkText:'Ver inventario' },
  a3: { type:'armario', title:'Armario 3', desc:'Repuestos: RAM, discos SSD, tarjetas de red.',img:'./assets/Equipo3logo.png', link:'#inventario-armario-3', linkText:'Ver inventario' },
  a4: { type:'armario', title:'Armario 4', desc:'Periféricos: teclados, ratones, auriculares.',img:'./assets/Equipo3logo.png', link:'#inventario-armario-4', linkText:'Ver inventario' },
  a5: { type:'armario', title:'Armario 5', desc:'Material de limpieza y consumibles: pasta térmica, sprays.',img:'./assets/Equipo3logo.png', link:'#inventario-armario-5', linkText:'Ver inventario' },
  a6: { type:'armario', title:'Armario 6', desc:'Equipos en cuarentena pendientes de diagnóstico.',img:'./assets/Equipo3logo.png', link:'#inventario-armario-6', linkText:'Ver inventario' },
  e1: { type:'estacion', title:'Estación 1', desc:'PC de diagnóstico general. Windows 11 + herramientas de benchmark.',img:'./assets/Equipo3logo.png', link:'#estacion-a', linkText:'Ver ficha técnica' },
  e2: { type:'estacion', title:'Estación 2', desc:'Soldadura y reparación de placa. Cautín, flux, osciloscopio.',img:'./assets/Equipo3logo.png', link:'#estacion-b', linkText:'Ver ficha técnica' },
  e3: { type:'estacion', title:'Estación 3', desc:'Clonado de discos duros y recuperación de datos.',img:'./assets/Equipo3logo.png', link:'#estacion-c', linkText:'Ver ficha técnica' },
  e4: { type:'estacion', title:'Estación 4', desc:'Test de monitores y tarjetas gráficas.',img:'./assets/Equipo3logo.png', link:'#estacion-d', linkText:'Ver ficha técnica' },
  e5: { type:'estacion', title:'Estación 5', desc:'Instalación de SO y actualizaciones en red.',img:'./assets/Equipo3logo.png', link:'#estacion-e', linkText:'Ver ficha técnica' },
  e6: { type:'estacion', title:'Estación 6', desc:'Reparación de portátiles. Pasta térmica, pantallas.',img:'./assets/Equipo3logo.png', link:'#estacion-f', linkText:'Ver ficha técnica' },
  e7: { type:'estacion', title:'Estación 7', desc:'Impresoras y periféricos: mantenimiento y calibración.',img:'./assets/Equipo3logo.png', link:'#estacion-g', linkText:'Ver ficha técnica' },
  e8: { type:'estacion', title:'Estación 8', desc:'Control de calidad: prueba final antes de entrega.',img:'./assets/Equipo3logo.png', link:'#estacion-h', linkText:'Ver ficha técnica' },
};

const card = document.getElementById('infoCard');
const wrap = document.getElementById('mapWrap');
let activeHs = null;

document.querySelectorAll('.hotspot').forEach(hs => {
  hs.addEventListener('click', function(e) {
    e.stopPropagation();
    const id = this.dataset.id;
    const d = data[id];
    if (!d) return;

    if (activeHs) activeHs.classList.remove('active');
    if (activeHs === this && card.classList.contains('visible')) {
      card.classList.remove('visible');
      activeHs = null;
      return;
    }
    activeHs = this;
    this.classList.add('active');

    document.getElementById('cardTitle').textContent = d.title;
    document.getElementById('cardDesc').textContent = d.desc;
    const lnk = document.getElementById('cardLink');
    lnk.href = d.link; lnk.textContent = d.linkText;
    const badge = document.getElementById('cardBadge');
    const img = document.getElementById('cardImage');
    img.src = d.img;
    img.alt = d.title;
    badge.textContent = d.type === 'armario' ? 'Armario' : 'Estación';
    badge.className = 'badge ' + (d.type === 'armario' ? 'badge-armario' : 'badge-estacion');

    const wRect = wrap.getBoundingClientRect();
    const hRect = this.getBoundingClientRect();
    let top = (hRect.bottom - wRect.top) + 6;
    let left = (hRect.left - wRect.left);
    const cardW = 210;
    if (left + cardW > wrap.offsetWidth - 10) left = wrap.offsetWidth - cardW - 10;
    if (left < 4) left = 4;
    card.style.top = top + 'px';
    card.style.left = left + 'px';
    card.classList.add('visible');
  });
});

document.getElementById('cardClose').addEventListener('click', function(e) {
  e.stopPropagation();
  card.classList.remove('visible');
  if (activeHs) { activeHs.classList.remove('active'); activeHs = null; }
});

document.addEventListener('click', function() {
  card.classList.remove('visible');
  if (activeHs) { activeHs.classList.remove('active'); activeHs = null; }
});
