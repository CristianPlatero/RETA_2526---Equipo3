// ===============================
// TARJETAS INTERACTIVAS DEL MAPA
// ===============================

// Creamos un objeto llamado "data" donde guardamos
// toda la información de cada punto del mapa.
//
// Cada clave (a1, a2, e1...) representa un hotspot.
// Dentro guardamos:
// - tipo de elemento
// - título
// - descripción
// - imagen
// - enlace
// - texto del enlace
const data = {
  a1: {
    type: "armario",
    title: "Armario 1",
    desc: "Herramientas de diagnóstico, destornilladores, llaves.",
    img: "../assets/taller_fotos/arm1.jpg",
    link: "../pages/inventario.html#linkarm1",
    linkText: "Ver contenido del Armario",
  },
  a2: {
    type: "armario",
    title: "Armario 2",
    desc: "Cables y conectores: HDMI, USB, VGA, adaptadores.",
    img: "../assets/taller_fotos/arm2.jpg",
    link: "../pages/inventario.html#linkarm2",
    linkText: "Ver contenido del Armario",
  },
  a3: {
    type: "armario",
    title: "Armario 3",
    desc: "Repuestos: RAM, discos SSD, tarjetas de red.",
    img: "../assets/taller_fotos/arm3.jpg",
    link: "../pages/inventario.html#linkarm3",
    linkText: "Ver contenido del Armario",
  },
  a4: {
    type: "armario",
    title: "Armario 4",
    desc: "Periféricos: teclados, ratones, auriculares.",
    img: "../assets/taller_fotos/arm4.jpg",
    link: "../pages/inventario.html#linkarm4",
    linkText: "Ver contenido del Armario",
  },
  a5: {
    type: "armario",
    title: "Armario 5",
    desc: "Material de limpieza y consumibles: pasta térmica, sprays.",
    img: "../assets/taller_fotos/arm5.jpg",
    link: "../pages/inventario.html#linkarm5",
    linkText: "Ver contenido del Armario",
  },
  a6: {
    type: "armario",
    title: "Armario 6",
    desc: "Equipos en cuarentena pendientes de diagnóstico.",
    img: "../assets/taller_fotos/armario limpieza abierto.jpg",
    link: "../pages/inventario.html#linkarm6",
    linkText: "Ver contenido del Armario",
  },
  e1: {
    type: "estacion",
    title: "Estación 1",
    desc: "PC de diagnóstico general. Windows 11 + herramientas de benchmark.",
    img: "../assets/taller_fotos/estacion_1.jpg",
    link: "../pages/inventario.html#linkest1",
    linkText: "Ver Estación",
  },
  e2: {
    type: "estacion",
    title: "Estación 2",
    desc: "Soldadura y reparación de placa. Cautín, flux, osciloscopio.",
    img: "../assets/taller_fotos/estacion_6.jpg",
    link: "../pages/inventario.html#linkest2",
    linkText: "Ver Estación",
  },
  e3: {
    type: "estacion",
    title: "Estación 3",
    desc: "Clonado de discos duros y recuperación de datos.",
    img: "../assets/taller_fotos/estacion_5.jpg",
    link: "../pages/inventario.html#linkest3",
    linkText: "Ver Estación",
  },
  e4: {
    type: "estacion",
    title: "Estación 4",
    desc: "Test de monitores y tarjetas gráficas.",
    img: "../assets/taller_fotos/estacion_reparacion_1.jpg",
    link: "../pages/inventario.html#linkest4",
    linkText: "Ver Estación",
  },
  e5: {
    type: "estacion",
    title: "Estación 5",
    desc: "Instalación de SO y actualizaciones en red.",
    img: "../assets/taller_fotos/estacion_reparacion_2.jpg",
    link: "../pages/inventario.html#linkest5",
    linkText: "Ver Estación",
  },
  e6: {
    type: "estacion",
    title: "Estación 6",
    desc: "Reparación de portátiles. Pasta térmica, pantallas.",
    img: "../assets/taller_fotos/estacion_4.jpg",
    link: "../pages/inventario.html#linkest6",
    linkText: "Ver Estación",
  },
  e7: {
    type: "estacion",
    title: "Estación 7",
    desc: "Impresoras y periféricos: mantenimiento y calibración.",
    img: "../assets/taller_fotos/estacion_3.jpg",
    link: "../pages/inventario.html#linkest7",
    linkText: "Ver Estación",
  },
  e8: {
    type: "estacion",
    title: "Estación 8",
    desc: "Control de calidad: prueba final antes de entrega.",
    img: "../assets/taller_fotos/estacion_2.jpg",
    link: "../pages/inventario.html#linkest8",
    linkText: "Ver Estación",
  },
  t1: {
    type: "taller",
    title: "Taller de Informática",
    desc: "Este es el taller de Informática del IES Miguel Herrero",
    img: "../assets/taller_fotos/taller_general.jpg",
    link: "../pages/inventario.html",
    linkText: "Ver Inventario",
  },
};

// ===============================
// CAPTURAMOS ELEMENTOS DEL HTML
// ===============================

// Guardamos la tarjeta emergente completa
const card = document.getElementById("infoCard");
//Guardamos el contenedor del mapa
const wrap = document.getElementById("mapWrap");
// Variable para recordar qué hotspot está activo
let activeHs = null;

// ===============================
// RECORREMOS TODOS LOS HOTSPOTS
// ===============================

// Seleccionamos todos los elementos con clase ".hotspot"
// y recorremos cada uno con forEach()
document.querySelectorAll(".hotspot").forEach((hs) => {
  // Añadimos un evento click a cada hotspot
  hs.addEventListener("click", function (e) {
    // Evita que el click se propague al document
    // y cierre la tarjeta inmediatamente
    e.stopPropagation();
    // Obtenemos el ID guardado en data-id
    const id = this.dataset.id;
    // Buscamos los datos correspondientes en el objeto "data"
    const d = data[id];
    // Si no existen datos, terminamos la función
    if (!d) return;

    // ===============================
    // CONTROL DE HOTSPOT ACTIVO
    // ===============================

    // Si ya había un hotspot activo,
    // le quitamos la clase "active"
    if (activeHs) activeHs.classList.remove("active");

    // Si hacemos click otra vez sobre el mismo hotspot
    // y la tarjeta ya está visible,
    // ocultamos la tarjeta
    if (activeHs === this && card.classList.contains("visible")) {
      // Ocultamos tarjeta
      card.classList.remove("visible");
      // Reiniciamos hotspot activo
      activeHs = null;
      return;
    }
    // Guardamos el hotspot actual como activo
    activeHs = this;
    // Añadimos clase active para resaltarlo visualmente
    this.classList.add("active");

    // ===============================
    // RELLENAMOS LA TARJETA
    // ===============================

    // Cambiamos Todos los elementos de la tarjeta con los datos de la posición correcta del array de datos
    document.getElementById("cardTitle").textContent = d.title;
    document.getElementById("cardDesc").textContent = d.desc;
    const lnk = document.getElementById("cardLink");
    lnk.href = d.link;
    lnk.textContent = d.linkText;
    const badge = document.getElementById("cardBadge");
    const img = document.getElementById("cardImage");
    img.src = d.img;
    img.alt = d.title;
    if (d.type === "armario") {
      badge.textContent = "Armario";
      badge.className = "badge badge-armario";
    } else if (d.type === "estacion") {
      badge.textContent = "Estación";
      badge.className = "badge badge-estacion";
    } else if (d.type === "taller") {
      badge.textContent = "Taller";
      badge.className = "badge badge-taller";
    }
    /* badge.textContent = d.type === 'armario' ? 'Armario' : 'Estación';
    badge.className = 'badge ' + (d.type === 'armario' ? 'badge-armario' : 'badge-estacion');
*/
    // ===============================
    // CALCULAMOS POSICIÓN DE TARJETA
    // ===============================

    // Coordenadas del contenedor principal
    const wRect = wrap.getBoundingClientRect();
    // Coordenadas del hotspot pulsado
    const hRect = this.getBoundingClientRect();
    // Posición vertical:
    // debajo del hotspot + 6px
    let top = hRect.bottom - wRect.top + 6;
    // Posición horizontal
    let left = hRect.left - wRect.left;
    // Anchura fija de la tarjeta
    const cardW = 210;
    // Evitamos que la tarjeta se salga por la derecha
    if (left + cardW > wrap.offsetWidth - 10)
      left = wrap.offsetWidth - cardW - 10;
    if (left < 4) left = 4;
    card.style.top = top + "px";
    card.style.left = left + "px";
    // Mostramos la tarjeta
    card.classList.add("visible");
  });
});

// ===============================
// BOTÓN DE CERRAR TARJETA
// ===============================

// Evento del botón cerrar
document.getElementById("cardClose").addEventListener("click", function (e) {
  // Evita propagación
  e.stopPropagation();
  // Oculta tarjeta
  card.classList.remove("visible");
  // Si había hotspot activo:
  if (activeHs) {
    activeHs.classList.remove("active");
    activeHs = null;
  } // Quitamos clase visual y Reiniciamos variable
});
// ===============================
// CERRAR AL HACER CLICK FUERA
// ===============================
document.addEventListener("click", function () {
  // Ocultamos tarjeta
  card.classList.remove("visible");
  // Quitamos hotspot activo
  if (activeHs) {
    activeHs.classList.remove("active");
    activeHs = null;
  }
});
