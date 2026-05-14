// =========================================
// CREAR FILAS EN LAS TABLAS DEL INVENTARIO
// =========================================
// Para crear todas las tablas sin duplicar código, hacemos un for para que recorra un array de id's de tablas y otro de rutas de '.csv'
const tablaNombre = ["tablaBodyPC", "tablaBodyArm1"];
const ruta = ["../assets/pc.csv", "../assets/arm1.csv"];
/* guardamos en variable la tabla que vamos a trabajar*/
for (i = 0; i < tablaNombre.length; i++) {
  const tablaPC = document.getElementById(tablaNombre[i]);

  /* Recogemos el archivo csv */
  fetch(ruta[i])
    .then((response) => response.text()) // convertimos el csv a texto
    .then((data) => {
      // y creamos la variable data
      const lineas = data.split("\n"); // ahora lineas es un array con una linea por cada fila
      lineas.forEach((linea) => {
        // por cada linea
        const columnas = linea.split(","); // se separan los campos
        const fila = document.createElement("tr"); // se crea el elemento fila de la tabla
        columnas.forEach((columna) => {
          // y por cada columna
          const celda = document.createElement("td"); // crea las celdas
          celda.textContent = columna.trim();
          fila.appendChild(celda); // añade los datos a cada celda
        });

        tablaPC.appendChild(fila); // añade las celdas a cada fila
      });
    });
}
