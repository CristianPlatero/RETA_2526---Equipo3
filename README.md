# 🚀 HISTORIAS DEL HARDWARE - RETA CANTABRIA 2526

> Gestión y Localización del Material del Taller de Informática

[![Build Status](https://img.shields.io/github/actions/workflow/status/CristianPlatero/RETA_2526---Equipo3/ci.yml?branch=main&style=flat-square)](https://github.com/CristianPlatero/RETA_2526---Equipo3)
[![Coverage](https://img.shields.io/codecov/c/github/CristianPlatero/RETA_2526---Equipo3?style=flat-square)](https://codecov.io/gh/CristianPlatero/RETA_2526---Equipo3)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)](https://opensource.org/licenses/MIT)
[![Version](https://img.shields.io/github/v/release/CristianPlatero/RETA_2526---Equipo3?style=flat-square)](https://github.com/CristianPlatero/RETA_2526---Equipo3/releases)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](CONTRIBUTING.md)

---

## 📖 Descripción

Este proyecto es la culminación del primer curso del C.F.G.S de Desarrollo de Aplicaciones Web, en el que el Equipo3 ha diseñado y construido una Base de Datos y su correspondiente Aplicación en Java para la gestión y locallización del material electrónico del taller de Informática. 

Se trata de una aplicación dirigida a los usuarios (Profesorado) del Taller de Informática, con una interfaz sencilla y funcional que muestre la información que necesiten en cada momento: Ubicación de los componentes, Stock restante, y mucho más.

Deseamos que nuestro producto acabe siendo una aplicación en java, con la cual podamos inventariar, clasificar y localizar cada elemento del taller. 
 
Para ello utilizaremos una **aplicación de escritorio en java**. Para la interfaz utilizaremos java swing y dentro del programa lo enlazaremos con la base de datos. 
 
Para ello el MySQL se alojará en la primera máquina virtual. 
 
El programa se conectará a la base de datos mediante JDBC y se espera que esté tenga varios módulos. Módulo de gestión del inventario (Administrador), módulo de consulta y localización (Profesor) y módulo de informes (Exportación a PDF O Excel).  
 
También haremos una página web en la cual se muestre de forma gráfica la distribución física del taller. Para ello se utilizará HTML, css y JavaScript. 
 
En cuanto a las máquinas virtuales, se utilizarán dos. En una se alojará la base de datos y solo se podrá acceder a ella a través de la otra máquina virtual.  Y en la otra se alojará la página web y se podrá acceder a ella a través de los ordenadores del laboratorio.

Nuestra aplicación cuenta con una página web dinámica que muestra toda la información de forma moderna y visual.



**¿Por qué este proyecto y no otro?** 

Una parte clave de este proyecto es fomentar el trabajo en equipo y la colaboración entre los estudiantes. Por eso creemos que junto con el programa como producto, somos nosotros mismos como equipo de desarrolladores los que debemos mostrarnos como el verdadero producto final, aunando resultados con las metodologías de trabajo que hemos aplicado.

Como elemento diferenciador, y dentro del requisito de trabajar según el **método SCRUM**, hemos decidido aplicar el sistema de **Pair Programming**. Se trata de una técnica de trabajo donde dos programadores trabajan en un mismo ordenador. 

Si existen alternativas, menciona brevemente qué diferencia a este proyecto (velocidad, simplicidad, integración específica, licencia, etc.).

### ✨ Características principales

- **Interfaz dinámica** — descripción concisa de lo que aporta.
- **Base de datos completa** — descripción concisa de lo que aporta.
- **Aplicación de escritorio ligera y robusta** — descripción concisa de lo que aporta.
- **Página web interactiva** — descripción concisa de lo que aporta.
- **Diseño elegante y 'user friendly'** — descripción concisa de lo que aporta.

### 🖼️ Capturas de pantalla / Demo

> Incluye aquí screenshots, GIFs animados o un enlace a una demo en vivo. Una imagen vale más que mil palabras.

```
<!-- Ejemplo -->
![Demo](docs/assets/demo.gif)
```

[![Demo en vivo](https://img.shields.io/badge/Demo-Ver%20en%20vivo-blue?style=flat-square)](https://RETA_2526---Equipo3/releases.example.com)

---

## ⚙️ Instalación

### Requisitos previos

Antes de instalar, asegúrate de tener lo siguiente:

- [Node.js](https://nodejs.org/) >= 18.0 (o el runtime que aplique)
- [Git](https://git-scm.com/)
- Cualquier otra dependencia del sistema

### Instalación paso a paso

**1. Clona el repositorio**

```bash
git clone https://github.com/CristianPlatero/RETA_2526---Equipo3/releases.git
cd tuproyecto
```

**2. Instala las dependencias**

```bash
npm install
# o con yarn
yarn install
```

**3. Configura las variables de entorno**

```bash
cp .env.example .env
# Edita .env con tus valores
```

**4. Inicia el proyecto**

```bash
npm run dev
```

La aplicación estará disponible en `http://localhost:3000`.

---

## 🛠️ Uso

El ejemplo más pequeño posible para demostrar el valor del proyecto:

```bash
# Ejemplo básico
RETA_2526---Equipo3 --input archivo.txt --output resultado.json
```

**Salida esperada:**

```
✔ Procesado: archivo.txt
✔ Resultado guardado en: resultado.json
```

### Ejemplos adicionales

```bash
# Modo verbose
RETA_2526---Equipo3 --input archivo.txt --verbose

# Procesamiento por lotes
RETA_2526---Equipo3 --batch ./carpeta/ --format json
```

> 📚 Para ejemplos más avanzados y casos de uso completos, consulta la [documentación oficial](https://github.com/CristianPlatero/RETA_2526---Equipo3/docs).

---


### Configuración del entorno de desarrollo

```bash
# Instalar dependencias de desarrollo
npm install

# Ejecutar tests
npm test

# Lint
npm run lint

# Build de producción
npm run build
```



---

## 🗺️ Roadmap

- [x] Funcionalidad base
- [x] Creación e instalación de BBDD relacional
- [ ] Compilación de programa Java
- [ ] Diseño de GUI
- [ ] Funcionalidades extra
- 

¿Tienes ideas? Abre un [issue](https://github.com/CristianPlatero/RETA_2526---Equipo3/issues) con la etiqueta `mejoras`.

---

## 🆘 Soporte

Si tienes problemas o preguntas:

- 🐛 **Bugs y problemas**: abre un [issue](https://github.com/CristianPlatero/RETA_2526---Equipo3/issues)
- 💬 **Preguntas y discusión**: únete a nuestro [GitHub Discussions](https://github.com/CristianPlatero/RETA_2526---Equipo3/discussions)
- 📧 **Contacto directo**: [CristianPlatero](mailto:cplateroz2501@educantabria.es)

---

## 👥 Autores y agradecimientos

**Autores**

- [@lina-educantabria](https://github.com/lina-educantabria) — Diseño y desarrollo
- [@Ossel18](https://github.com/Ossel18) — Diseño y desarrollo
- [@RFCDAW1](https://github.com/RFCDAW1) — Diseño y desarrollo
- [@tiimurr](https://github.com/tiimurr) — Diseño y desarrollo
- [@CristianPlatero](https://github.com/CristianPlatero) — Diseño y desarrollo

Un agradecimiento especial a los profesores de DAW1 y los compañeros de los demás equipos por su ayuda, servir de inspiración, y a todos los que han abierto issues y PRs.

---

## 📄 Licencia

Este proyecto está licenciado bajo la **ELEGIR LICENCIA**. Consulta el archivo [LICENSE](LICENSE) para más detalles.

---

## 📊 Estado del proyecto

> ✅ **En desarrollo activo** — Se aceptan contribuciones e issues.

<!-- Si el proyecto está pausado, usa algo como: -->
<!-- ⚠️ **Mantenimiento mínimo** — Este proyecto recibe únicamente correcciones críticas. Si quieres tomar el relevo como mantenedor, abre un issue. -->
<!-- ❌ **Archivado** — Este proyecto ya no recibe mantenimiento activo. -->