// =========================================
// CREAR FILAS EN LAS TABLAS DEL INVENTARIO
// =========================================
// Para crear todas las tablas sin duplicar código, hacemos un for para que recorra un array de id's de tablas y otro de rutas de '.csv'
const tablaNombre = ["tablaBodyPC", "tablaBodyArm1","tablaBodyArm2","tablaBodyArm3","tablaBodyArm4","tablaBodyArm5","tablaBodyArm6","tablaBodyEst1","tablaBodyEst2","tablaBodyEst3","tablaBodyEst4","tablaBodyEst5","tablaBodyEst6","tablaBodyEst7","tablaBodyEst8"];
const ruta = ["../assets/pc.csv", "/exports/ARM01.csv","/exports/ARM02.csv","/exports/ARM03.csv","/exports/ARM04.csv","/exports/ARM05.csv","/exports/ARM06.csv","/exports/EST01.csv","/exports/EST02.csv","/exports/EST03.csv","/exports/EST04.csv","/exports/EST05.csv","/exports/EST06.csv","/exports/EST07.csv","/exports/EST08.csv"];
/* guardamos en variable la tabla que vamos a trabajar*/
for (i = 0; i < tablaNombre.length; i++) {
  const tablaPC = document.getElementById(tablaNombre[i]);

  /* Recogemos el archivo csv */
  fetch(ruta[i])
    .then((response) => response.text()) // convertimos el csv a texto
    .then((data) => {
      
      // y creamos la variable data
      const lineas = data.split("\n").slice(1); // ahora lineas es un array con una linea por cada fila
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
