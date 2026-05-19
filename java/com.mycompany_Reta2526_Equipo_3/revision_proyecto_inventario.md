# Revisión del Proyecto — Inventario Taller Informática
**Equipo 3 · Reta 25/26 · Fecha: 18/05/2026**

---

## ÍNDICE
1. [Bugs reales — rompen la aplicación](#1-bugs-reales--rompen-la-aplicación)
2. [Errores de lógica — se ejecuta pero hace algo mal](#2-errores-de-lógica--se-ejecuta-pero-hace-algo-mal)
3. [Problemas en el Validador](#3-problemas-en-el-validador)
4. [Problemas en los DAOs](#4-problemas-en-los-daos)
5. [Problemas de diseño y arquitectura](#5-problemas-de-diseño-y-arquitectura)
6. [Problemas en comentarios y Javadoc](#6-problemas-en-comentarios-y-javadoc)
7. [Inconsistencias menores y code smell](#7-inconsistencias-menores-y-code-smell)
8. [Base de datos SQL](#8-base-de-datos-sql)
9. [Lo que está bien hecho ✅](#9-lo-que-está-bien-hecho-)
10. [Tabla resumen de correcciones prioritarias](#10-tabla-resumen-de-correcciones-prioritarias)

---

## 1. Bugs reales — rompen la aplicación

### 🔴 BUG 1 — `Pc.setFecha_alta` parsea `nombre` en lugar de `fecha_alta`
**Archivo:** `Objetos/Pc.java`

```java
// ❌ CÓDIGO ACTUAL — parsea el campo NOMBRE, no la fecha
public void setFecha_alta(String fecha_alta) throws FechaInvalidaException {
    Validador.validaFecha(fecha_alta);
    this.fecha_alta = LocalDate.parse(nombre, formato);  // ← BUG: "nombre" en vez de "fecha_alta"
}

// ✅ CORRECCIÓN
public void setFecha_alta(String fecha_alta) throws FechaInvalidaException {
    Validador.validaFecha(fecha_alta);
    this.fecha_alta = LocalDate.parse(fecha_alta, formato);  // ← fecha_alta
}
```
**Impacto:** Cualquier intento de crear o leer un `Pc` lanza `DateTimeParseException`. Ningún PC funciona.

---

### 🔴 BUG 2 — `Armario` no llama a `setMovilidad` en su constructor
**Archivo:** `Objetos/Armario.java`

```java
// ❌ CÓDIGO ACTUAL — el parámetro "movilidad" se recibe pero nunca se asigna
public Armario(String id_ubi, String nombre, String descripcion, String movilidad) throws IdInvalidoException {
    super(id_ubi, nombre, descripcion);
    // ← falta: setMovilidad(movilidad);
}

// ✅ CORRECCIÓN
public Armario(String id_ubi, String nombre, String descripcion, String movilidad)
        throws IdInvalidoException, MovilidadInvalidaException, DescripcionInvalidaException {
    super(id_ubi, nombre, descripcion);
    setMovilidad(movilidad);
}
```
**Impacto:** Todo armario creado tiene `movilidad = null`. El campo está en la BD pero nunca se carga en el objeto.

---

### 🔴 BUG 3 — `guardarPeriferico` verifica `filas` en lugar de `filas2`
**Archivo:** `DAO/AdministradorDAO.java`

```java
// ❌ CÓDIGO ACTUAL — comprueba la variable equivocada en la segunda inserción
int filas2 = ps2.executeUpdate();
if (filas != 1) {  // ← debería ser filas2, no filas
    LoggerApp.log("No se ha insertado correctamente en perifericos_pcs");
}

// ✅ CORRECCIÓN
if (filas2 != 1) {
    LoggerApp.log("No se ha insertado correctamente en perifericos_pcs");
}
```
**Impacto:** Nunca se detecta un fallo al insertar en `perifericos_pcs`.

---

### 🔴 BUG 4 — `validaMovilidad` llama a `.toUpperCase()` antes de comprobar `null`
**Archivo:** `Validador/Validador.java`

```java
// ❌ CÓDIGO ACTUAL — NullPointerException si movilidad es null
public static void validaMovilidad(String movilidad) throws ... {
    movilidad = movilidad.toUpperCase().trim();  // ← NPE si movilidad == null
    if (movilidad == null || movilidad.isBlank()) { ... }

// ✅ CORRECCIÓN — comprobar null PRIMERO
public static void validaMovilidad(String movilidad) throws ... {
    if (movilidad == null || movilidad.isBlank()) {
        throw new MovilidadInvalidaException("La movilidad no puede estar vacía.");
    }
    movilidad = movilidad.toUpperCase().trim();
    ...
}
```
**Mismo problema en:** `validaEstadoFungible`, `validaTipoHerramienta`, `validaTipoConexion`.
Los cuatro métodos hacen `.toUpperCase()` antes de la comprobación de `null`.

---

### 🔴 BUG 5 — `Perifericos` hereda de `Componentes` en vez de `MaterialInventario`
**Archivo:** `Objetos/Perifericos.java`

```java
// ❌ CÓDIGO ACTUAL
public class Perifericos extends Componentes { ... }

// ✅ DEBERÍA SER
public class Perifericos extends MaterialInventario { ... }
```
**Impacto:** Un `Periférico` hereda un `id_pc` de `Componentes` pero además tiene su propia
relación N:M con PCs en la tabla `perifericos_pcs`. La herencia fuerza una relación 1:1
cuando debería ser 1:N. El propio código tiene un comentario que lo reconoce:
`// Tal vez se deberia quitar la herencia para crear un array de id_pc`.

---

## 2. Errores de lógica — se ejecuta pero hace algo mal

### 🟠 ERROR 6 — `validaInventario` limita IDs a 99, pero la BD no tiene ese límite
**Archivo:** `Validador/Validador.java`

```java
// ❌ PROBLEMA
if (valor < 0 || valor > 99) {
    throw new IdInvalidoException("El ID de inventario debe ser entre 0 y 99.");
}
```
La BD usa `AUTO_INCREMENT` sin tope. Con más de 99 registros, `listarMaterial()` construirá
objetos con `setId_matTa()` que lanzarán excepción para cada fila. El mismo problema
existe en `validaPc`.

**Corrección:** Cambiar el límite a `Integer.MAX_VALUE` o simplemente eliminar el tope
superior, dejando solo la comprobación de que sea positivo.

---

### 🟠 ERROR 7 — `validaEstado` no incluye `EN_REPARACION`
**Archivo:** `Validador/Validador.java`

```java
// ❌ El switch no contempla "EN_REPARACION"
switch (estado) {
    case "OBSOLETO" -> { }
    case "OPERATIVO" -> { }
    case "REPARACION" -> { }   // ← la BD guarda "en reparacion", que se convierte a "EN_REPARACION"
    default -> throw new EstadoInvalidoException(...);
}
```
La BD tiene el valor `'en reparacion'`. En `crearMaterialBD` se convierte a `EN_REPARACION`
con `.replace(" ", "_")`, pero el validador no lo acepta. Al leer cualquier registro
con ese estado se lanza excepción.

```java
// ✅ CORRECCIÓN
case "OBSOLETO" -> { }
case "OPERATIVO" -> { }
case "REPARACION" -> { }
case "EN_REPARACION" -> { }   // ← añadir este caso
```
También hay que añadir `EN_REPARACION` al enum `Estados.java`.

---

### 🟠 ERROR 8 — `setObservaciones` no acepta valores nulos ni vacíos
**Archivo:** `Objetos/MaterialInventario.java`, `Objetos/Pc.java`

El campo `observaciones` es `NULL` en la BD, pero `setObservaciones` llama a
`validaDescripcion`, que lanza excepción si está vacío. Si el usuario no escribe
observaciones, la inserción falla.

```java
// ✅ CORRECCIÓN en setObservaciones
public void setObservaciones(String observaciones) throws DescripcionInvalidaException {
    if (observaciones != null && !observaciones.isBlank()) {
        Validador.validaDescripcion(observaciones);
    }
    this.observaciones = observaciones;
}
```

---

### 🟠 ERROR 9 — `validaDescripcion` limita a 50 caracteres pero la BD permite 150
**Archivo:** `Validador/Validador.java`

```java
// ❌ El validador rechaza descripciones de más de 50 caracteres
if (descripcion.length() < 2 || descripcion.length() > 50) { ... }
```
La columna `descripcion` en `materialesTaller` es `VARCHAR(150)`. Con el validador
actual se pierde capacidad de la BD sin motivo.

---

### 🟠 ERROR 10 — `crearMaterialBD` siempre devuelve `MaterialInventario` base
**Archivo:** `DAO/AdministradorDAO.java`

```java
// ❌ Siempre crea el tipo padre, ignorando los subtipos
private MaterialInventario crearMaterialBD(ResultSet rs) throws ... {
    return new MaterialInventario(
        rs.getString("id_matTa"), ...
    );
}
```
`listarMaterial()` no hace JOIN con las tablas hijas (`cableado`, `componentes`, etc.),
así que toda la información específica de cada subtipo se pierde al leer de la BD.

**Corrección:** Hacer un JOIN o consulta secundaria y construir el subtipo correcto
según la tabla hija a la que pertenezca el registro.

---

### 🟠 ERROR 11 — `setFecha_alta` en `MaterialInventario` tiene lógica de parseo ambigua
**Archivo:** `Objetos/MaterialInventario.java`

```java
// ❌ PROBLEMA — "dd-MM-yyyy" también tiene guiones y longitud 10
if (fecha_alta.contains("-") && fecha_alta.length() == 10) {
    this.fecha_alta = LocalDate.parse(fecha_alta); // asume yyyy-MM-dd
}
```
Tanto `"31-12-2025"` (formato UI) como `"2025-12-31"` (formato BD) cumplen la condición.
Si alguien introduce `"31-12-2025"` desde la interfaz, `LocalDate.parse()` sin formato
lanzará excepción porque espera ISO (`yyyy-MM-dd`).

**Corrección:** Usar los dos `DateTimeFormatter` del `Validador` explícitamente,
o reutilizar la lógica de `validaFecha` para saber qué formato aplicar.

---

## 3. Problemas en el Validador

### 🟡 ERROR 12 — `validaTipo` tiene comentario de error que dice "estado" en vez de "tipo"
**Archivo:** `Validador/Validador.java`

```java
// ❌ Mensaje de excepción incorrecto — dice "estado" cuando valida "tipo"
if (tipo == null || tipo.isBlank()) {
    throw new DescripcionInvalidaException("El estado no puede estar vacio.");
                                         // ↑ debería decir "El tipo"
}
```

---

### 🟡 ERROR 13 — `validaUbi` tiene mensaje de error que habla de "balda"
**Archivo:** `Validador/Validador.java`

```java
// ❌ Mensaje confuso — dice "balda" cuando se está validando una "ubicación"
if (ubi == null || ubi.isBlank()) {
    throw new IdInvalidoException("El ID de la balda no puede estar vacío.");
                                 // ↑ debería decir "El ID de la ubicación"
}
```

---

### 🟡 ERROR 14 — `validaCantidad` dice "entre 0 y 99" pero el límite real es 120
**Archivo:** `Validador/Validador.java`

```java
// ❌ El mensaje no coincide con el código
if (valor < 0 || valor > 120) {
    throw new CantidadInvalidaException("La cantidad debe ser entre 0 y 99.");
                                      // ↑ el límite real es 120, no 99
}
```

---

### 🟡 ERROR 15 — `validaTipoHerramienta` lanza `CategoriaInvalidaException` en vez de `TipoInvalidoException`
**Archivo:** `Validador/Validador.java`

```java
// ❌ Tipo de excepción incorrecto para una herramienta
public static void validaTipoHerramienta(String tipoHerramienta)
        throws CategoriaInvalidaException, DescripcionInvalidaException { ... }

// ✅ Debería lanzar TipoInvalidoException (que existe en el proyecto)
public static void validaTipoHerramienta(String tipoHerramienta)
        throws TipoInvalidoException, DescripcionInvalidaException { ... }
```
Lo mismo ocurre en `validaTipoConexion`: lanza `CategoriaInvalidaException` cuando
debería usar `ConectorInvalidoException`, que ya existe.

---

### 🟡 ERROR 16 — `validaBalda` usa regex incompleta para el número de balda
**Archivo:** `Validador/Validador.java`

```java
// ❌ La regex no tiene $ al final — acepta "1abc", "6xyz", etc.
if (!balda.matches("^[1-6]")) {
    throw ...
}

// ✅ CORRECCIÓN
if (!balda.matches("^[1-6]$")) {
    throw ...
}
```

---

## 4. Problemas en los DAOs

### 🟠 ERROR 17 — Métodos de interfaz sin implementar lanzan excepción en runtime
**Archivo:** `DAO/AdministradorDAO.java`

Los métodos `porIdMaterial`, `listarPc`, `porIdPc`, `guardarPc` y `eliminarPc`
están generados por NetBeans y lanzan `UnsupportedOperationException`.
Si se llaman desde la interfaz, la app explota sin mensaje claro para el usuario.

**Corrección a corto plazo:** Sustituir el `throw` por un log descriptivo y
devolver `null` o lista vacía. **Corrección real:** Implementarlos.

---

### 🟡 ERROR 18 — `guardarComponente` usa `System.out.println` en lugar de `LoggerApp`
**Archivo:** `DAO/AdministradorDAO.java`

```java
// ❌ Inconsistente con el resto del proyecto
System.out.println("No se ha insertado correctamente en componentes");

// ✅
LoggerApp.log("No se ha insertado correctamente en componentes");
```

---

### 🟡 ERROR 19 — `eliminarMaterial` usa el Logger estándar de Java en lugar de `LoggerApp`
**Archivo:** `DAO/AdministradorDAO.java`

```java
// ❌ Mezcla dos sistemas de logging
} catch (SQLException ex) {
    Logger.getLogger(AdministradorDAO.class.getName()).log(Level.SEVERE, null, ex);
}

// ✅
} catch (SQLException ex) {
    LoggerApp.log("Error al eliminar material: " + ex.getMessage());
}
```

---

### 🟡 ERROR 20 — `PcDAO` existe pero no implementa ninguna interfaz
**Archivo:** `DAO/PcDAO.java`

`PcDAO` tiene `listarPCs()` y `guardarPC()` funcionando, pero no implementa
`RepositorioPc`. Mientras tanto, `AdministradorDAO` implementa `RepositorioPc`
pero sus métodos están vacíos. Los dos DAOs duplican responsabilidades sin coordinarse.

---

## 5. Problemas de diseño y arquitectura

### 🟡 DISEÑO 21 — `Ubicacion.setNombre` y `setDescripcion` no validan
**Archivo:** `Objetos/Ubicacion.java`

```java
// ❌ Los setters no llaman al Validador — cualquier valor entra sin control
public void setNombre(String nombre) {
    this.nombre = nombre;   // sin validación
}
public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;   // sin validación
}
```
Es el único objeto del proyecto que no valida. Debería llamar a
`Validador.validaNombre()` y `Validador.validaDescripcion()`.

---

### 🟡 DISEÑO 22 — Credenciales de BD hardcodeadas en el código fuente
**Archivo:** `AccesoBD/AccesoBaseDatos.java`

```java
// ❌ Las credenciales están en el código — problema de seguridad y de trabajo en equipo
private static final String USUARIO = "root";
private static final String CLAVE = "mysql";
```
**Corrección:** Leerlas de un fichero `config.properties` externo al `.jar`
(y excluirlo del repositorio con `.gitignore`).

---

### 🟡 DISEÑO 23 — `DateTimeFormatter` se instancia por objeto en vez de ser constante
**Archivo:** `Objetos/MaterialInventario.java`, `Objetos/Pc.java`

```java
// ❌ Se crea una nueva instancia cada vez que se instancia el objeto
DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");

// ✅ Debe ser estático y final
private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd-MM-yyyy");
```
Además, `MaterialInventario` usa `"dd-MM-yyyy"` y `Pc` usa `"dd/MM/yyyy"` (guion vs barra).
Deberían usar el mismo formato, preferiblemente definido una sola vez en `Validador`.

---

### 🟡 DISEÑO 24 — `InicializadorUsuarios` tiene credenciales por defecto visibles en el código
**Archivo:** `Usuarios/InicializadorUsuarios.java`

```java
// Las contraseñas iniciales están en texto plano en el fuente
String hashAdmin = BCrypt.hashpw("admin123", BCrypt.gensalt());
String hashProfesor = BCrypt.hashpw("profesor123", BCrypt.gensalt());
```
Para un entorno educativo es aceptable, pero hay que documentarlo claramente
como "contraseñas de desarrollo" y cambiarlas en producción.

---

### 🟡 DISEÑO 25 — `Balda` hereda de `Armario` siendo una parte de él
**Archivo:** `Objetos/Balda.java`

```java
public class Balda extends Armario { ... }
```
Una `Balda` **no es un tipo de Armario** (no cumple el principio de Liskov).
Debería ser una clase independiente con una referencia al armario al que pertenece,
o simplemente no existir como objeto y gestionarse desde `Armario`.

---

## 6. Problemas en comentarios y Javadoc

### 📝 JAVADOC 26 — `RepositorioPc.guardarPc` tiene el Javadoc roto
**Archivo:** `Repositorio/RepositorioPc.java`

```java
// ❌ Falta el asterisco — el texto cae fuera del bloque Javadoc
/**
 * ...
 * aunque también se pueden crear un método para añadir un objeto y otro para modificar
 inserta un registro en la tabla o bien lo modifica   ← esta línea está fuera del /** */
 * ...
 */
```
La línea `inserta un registro...` no tiene `*` delante, lo que rompe el formato
del bloque Javadoc y la convierte en código comentado incorrecto.

---

### 📝 JAVADOC 27 — `validaEstado` documenta que lanza `DescripcionInvalidaException` con mensaje incorrecto
**Archivo:** `Validador/Validador.java`

```java
// El Javadoc dice "@throws DescripcionInvalidaException" y el mensaje interno dice:
throw new DescripcionInvalidaException("El estado no puede estar vacía.");
// ← "vacía" con tilde femenina para "estado" (neutro/masculino)
// Debería decir "vacío"
```

---

### 📝 JAVADOC 28 — Getters sin descripción en casi todos los archivos
En todas las clases los getters tienen solo `@return` sin texto:
```java
/**
 *
 * @return       ← no dice qué devuelve
 */
public int getId_matTa() { ... }
```
Mínimo debería decir `@return el identificador del material en el inventario`.
Afecta a: `MaterialInventario`, `Pc`, `Cableado`, `Componentes`, `Herramientas`,
`Perifericos`, `Material_Fungible`, `Equipos_en_red`, `Armario`, `Balda`, `Estacion`.

---

### 📝 JAVADOC 29 — `login` en `GestionUsuarios` tiene el Javadoc del parámetro `contrasenaPlana` mal puesto
**Archivo:** `Usuarios/GestionUsuarios.java`

```java
/**
 * ... (bloque largo de instrucciones para el equipo) ...
 * @param nombre    ← documenta "nombre" pero no documenta "contrasenaPlana"
 */
public Usuario login(String nombre, String contrasenaPlana) { ... }
```
Falta `@param contrasenaPlana` y `@return` (que indica el usuario autenticado o null).

---

### 📝 JAVADOC 30 — Comentario `// !` sin explicar en la BD SQL
**Archivo:** `inventario_taller.sql`

Hay varios comentarios `-- !` junto a columnas sin ninguna explicación:
```sql
movilidad enum('movil','fija') not null DEFAULT 'fija', -- !
conexion enum('inalambrica','cable') not null,-- !
tipo enum('soldadura','generales') not null, -- !
```
No está claro qué significa `-- !`. Probablemente son recordatorios pendientes
de revisión, pero deberían sustituirse por un comentario legible o eliminarse.

---

### 📝 JAVADOC 31 — Código comentado sin explicación en `MaterialInventario`
**Archivo:** `Objetos/MaterialInventario.java`

```java
// Hay una implementación antigua comentada sin explicar por qué se descartó:
//    public void setFecha_alta(String fecha_alta) throws FechaInvalidaException {
//        Validador.validaFecha(fecha_alta);
//        this.fecha_alta = LocalDate.parse(fecha_alta, formato);
//    }
```
El código muerto debería eliminarse o llevar un comentario que explique por qué
se sustituyó (`// Reemplazado para soportar también el formato yyyy-MM-dd de la BD`).

---

### 📝 JAVADOC 32 — `Cableado.setConector1` y `setConector2` no validan y no tienen Javadoc real
**Archivo:** `Objetos/Cableado.java`

```java
// ❌ Los setters de conector no llaman a Validador.validaConector() — que sí existe
public void setConector1(String conector1) {
    this.conector1 = conector1;   // sin validación
}
```
Existe `Validador.validaConector()` y `ConectorInvalidoException` pero no se usan aquí.

---

## 7. Inconsistencias menores y code smell

### 🔵 MENOR 33 — Nombre de clase `Equipos_en_red` no sigue convención Java
El estándar Java usa `PascalCase` para clases. `Equipos_en_red` mezcla guión bajo.
Debería llamarse `EquiposEnRed`.
Lo mismo aplica a `Material_Fungible` → `MaterialFungible`.

---

### 🔵 MENOR 34 — `setId_balda` en `MaterialInventario` tiene una rama `else if` inalcanzable
**Archivo:** `Objetos/MaterialInventario.java`

```java
if (id_balda == null) {
    this.id_balda = null;
}
else if (id_balda.isBlank()) {   // ← NUNCA se ejecuta: si fuera blank, el bloque
    this.id_balda = null;         //   anterior ya lo convirtió a null
}
else {
    this.id_balda = Integer.valueOf(id_balda);
}
```
El `else if` es código muerto. Se puede simplificar a:
```java
this.id_balda = (id_balda == null) ? null : Integer.valueOf(id_balda);
```

---

### 🔵 MENOR 35 — `LoggerApp` solo tiene `log(String)` — no diferencia niveles
**Archivo:** `Utilidades/LoggerApp.java`

Todos los mensajes tienen el mismo nivel, mezclando errores críticos con información
rutinaria. Sería útil añadir `logError(String)` y `logInfo(String)` para poder
filtrarlos, o usar los niveles del `Logger` de Java.

---

### 🔵 MENOR 36 — `Pc` tiene `CantidadInvalidaException` en los imports sin usarla
**Archivo:** `Objetos/Pc.java`

```java
import Excepciones.CantidadInvalidaException;  // ← importada pero nunca usada en Pc
```

---

### 🔵 MENOR 37 — `LocalDateTime` importado en `Validador` sin usarse
**Archivo:** `Validador/Validador.java`

```java
import java.time.LocalDateTime;  // ← importado pero nunca usado
```

---

## 8. Base de datos SQL

### 🟠 SQL 38 — El FK doble impide asignar material directamente a una estación
**Archivo:** `inventario_taller.sql`

```sql
-- ❌ Este FK obliga a que id_ubi sea siempre un armario con balda
FOREIGN KEY (id_ubi, id_balda) REFERENCES balda(id_armario, id_balda)
```
Si se intenta insertar un material con `id_ubi = 'EST01'` y `id_balda = NULL`,
MySQL rechaza la inserción porque `(EST01, NULL)` no existe en `balda`.
El trigger `est_nbalda` que solucionaría esto está comentado.

**Corrección:** Implementar el trigger, o separar la FK en dos opcionales
con una restricción de CHECK.

---

### 🟡 SQL 39 — `CHECK(id_ubi LIKE('ARM%'))` en minúsculas pero los datos se insertan en mayúsculas
**Archivo:** `inventario_taller.sql`

```sql
CHECK(id_ubi LIKE('ARM%'))  -- el validador Java genera 'ARM01', no 'arm01'
```
El CHECK comprueba minúsculas pero los IDs que genera el validador Java son
en mayúsculas. En MySQL el LIKE no distingue mayúsculas por defecto en `utf8mb4_general_ci`,
pero es una ambigüedad que debería aclararse. El comentario `// ! CAMBIAR CHECK A MAYUSCULAS EN BASE DE DATOS MYSQL`
en `Validador.java` confirma que el equipo ya lo detectó pero no lo corrigió.

---

### 🟡 SQL 40 — `Estados` en Java no tiene `EN_REPARACION` pero la BD sí tiene `'en reparacion'`
**Archivo:** `Enum/Estados.java` + `inventario_taller.sql`

```java
// ❌ El enum solo tiene tres valores
public enum Estados {
    OBSOLETO, OPERATIVO, REPARACION;
}
```
La BD guarda `'en reparacion'` (con espacio). El mapper lo convierte a `EN_REPARACION`
pero el enum no lo define. Hay que añadirlo.

```java
// ✅
public enum Estados {
    OBSOLETO, OPERATIVO, REPARACION, EN_REPARACION;
}
```

---

## 9. Lo que está bien hecho ✅

- **Singleton con Initialization-on-demand Holder** en `AccesoBaseDatos` — correcto y thread-safe.
- **BCrypt para contraseñas** en `GestionUsuarios` — buena práctica de seguridad real.
- **Pattern matching en switch** en `guardarMaterial` (Java 21+) — moderno y limpio.
- **Excepciones propias** para cada tipo de error — muy profesional para el nivel del proyecto.
- **Separación en capas** clara: Objetos / DAO / Repositorio / Validador / Interfaz / Usuarios.
- **Dos constructores** (con y sin ID) en cada objeto para separar inserción de lectura.
- **`Integer` (en vez de `int`) para `id_balda`** para permitir valores nulos — correcto.
- **`try-with-resources`** en todos los DAOs para cerrar conexiones automáticamente.
- **`PreparedStatement`** en todas las consultas — previene inyección SQL.
- **`LoggerApp` centralizado** como punto único de logging (aunque faltan niveles).
- **`validaFecha` con doble formato** para aceptar tanto el formato BD como el de usuario.

---

## 10. Tabla resumen de correcciones prioritarias

| # | Archivo | Problema | Prioridad |
|---|---------|----------|-----------|
| 1 | `Objetos/Pc.java` | `setFecha_alta` parsea `nombre` en lugar de `fecha_alta` | 🔴 CRÍTICO |
| 2 | `Objetos/Armario.java` | Constructor no llama a `setMovilidad` | 🔴 CRÍTICO |
| 3 | `DAO/AdministradorDAO.java` | `filas` en lugar de `filas2` en `guardarPeriferico` | 🔴 CRÍTICO |
| 4 | `Validador/Validador.java` | NPE en 4 métodos por `.toUpperCase()` antes de null-check | 🔴 CRÍTICO |
| 5 | `Objetos/Perifericos.java` | Hereda de `Componentes` en vez de `MaterialInventario` | 🔴 CRÍTICO |
| 6 | `Validador/Validador.java` | IDs limitados a 99 — incompatible con AUTO_INCREMENT | 🟠 ALTO |
| 7 | `Validador/Validador.java` | `EN_REPARACION` no está en el switch de `validaEstado` | 🟠 ALTO |
| 8 | `Objetos/MaterialInventario.java` | `setObservaciones` rechaza valores nulos/vacíos | 🟠 ALTO |
| 9 | `Validador/Validador.java` | Límite de descripción 50 vs BD 150 | 🟠 ALTO |
| 10 | `DAO/AdministradorDAO.java` | `crearMaterialBD` siempre devuelve tipo base | 🟠 ALTO |
| 11 | `Validador/Validador.java` | Varios mensajes de excepción con texto incorrecto | 🟡 MEDIO |
| 12 | `Validador/Validador.java` | Regex de balda sin `$` al final | 🟡 MEDIO |
| 13 | `Validador/Validador.java` | 4 métodos lanzan excepción incorrecta | 🟡 MEDIO |
| 14 | `DAO/AdministradorDAO.java` | Métodos sin implementar lanzan excepción en runtime | 🟡 MEDIO |
| 15 | `Objetos/Ubicacion.java` | `setNombre` y `setDescripcion` sin validación | 🟡 MEDIO |
| 16 | `Objetos/Cableado.java` | `setConector1/2` no llaman a `validaConector` | 🟡 MEDIO |
| 17 | `Repositorio/RepositorioPc.java` | Javadoc roto — línea sin asterisco | 📝 DOC |
| 18 | `Objetos/MaterialInventario.java` | Código comentado antiguo sin explicación | 📝 DOC |
| 19 | Todos los objetos | Getters con `@return` vacío | 📝 DOC |
| 20 | `inventario_taller.sql` | Comentarios `-- !` sin explicar | 📝 DOC |

---

*Generado automáticamente tras análisis completo del backup del 17/05/2026*
