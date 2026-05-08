# Informe de Justificación de Diseño
## Base de Datos: Inventario del Taller de Informática — IES Miguel Herrero

---

## 1. Introducción

El presente informe recoge las decisiones de diseño tomadas durante el desarrollo del esquema relacional de la base de datos `inventario_taller`, destinada a gestionar el inventario de materiales, equipos y ubicaciones del taller de informática del IES Miguel Herrero. Se documenta la estructura final adoptada, justificando cada decisión en base a los requisitos del sistema y a los principios de integridad, consistencia y normalización.

---

## 2. Estructura de Ubicaciones

### 2.1 Supertipo `ubicacion`

Se ha definido una tabla genérica `ubicacion` como supertipo del que heredan `armario` y `estacion`. Esta decisión permite que cualquier elemento del inventario pueda referenciarse a una ubicación de forma unificada a través de `id_ubi`, independientemente de si se trata de un armario o una estación de trabajo.

### 2.2 Tabla `armario`

La tabla `armario` hereda de `ubicacion` mediante una relación de identificación: su clave primaria `id_ubi` es a su vez clave foránea de `ubicacion`. Se ha prescindido del campo `num_baldas` como dato estático, dado que su valor sería redundante respecto a las filas reales existentes en la tabla `balda`. El número de baldas de un armario se obtiene en todo momento mediante una consulta agregada sobre dicha tabla, garantizando así la consistencia del dato:

```sql
SELECT id_armario, COUNT(*) AS num_baldas
FROM balda
GROUP BY id_armario;
```

Esta decisión elimina el riesgo de inconsistencia que habría supuesto mantener un contador manual susceptible de desincronizarse con el estado real de la base de datos.

### 2.3 Tabla `balda`

Todas las baldas pertenecen obligatoriamente a un armario, por lo que `id_armario` es `NOT NULL`. La clave primaria compuesta `(id_balda, id_armario)` permite que el número de balda se repita entre distintos armarios, lo cual refleja fielmente la realidad: el armario A puede tener una balda número 1 y el armario B también.

### 2.4 Tabla `estacion`

Hereda igualmente de `ubicacion`. Incluye el campo `tipo` para distinguir entre estaciones operativas y estaciones destinadas a reparación, lo que permite filtrar fácilmente los equipos según su contexto de uso.

---

## 3. Estructura de Materiales

### 3.1 Tabla base `materialesTaller`

Se ha definido una tabla central `materialesTaller` que actúa como supertipo para todos los elementos del inventario a excepción de los PCs. Contiene los atributos comunes a cualquier material: `nombre`, `descripcion`, `estado`, `cantidad`, `id_ubi`, `fecha_alta` y `observaciones`. De esta forma se evita la duplicación de columnas en cada subtipo y se centraliza la gestión del inventario en una única tabla consultable.

La referencia a `ubicacion` mediante `id_ubi` permite que cualquier material pueda estar ubicado en un armario, una estación o cualquier otra ubicación registrada en el sistema.

### 3.2 Subtipos de material

Las tablas `componentes`, `perifericos`, `equipos_red`, `cableado`, `herramientas` y `material_fungible` especializan a `materialesTaller` mediante una relación de identificación: su clave primaria `id_matTa` es también clave foránea con `ON DELETE CASCADE`, de modo que al eliminar un registro de la tabla base se eliminan automáticamente sus datos específicos asociados.

Las tablas que no requieren atributos adicionales (`equipos_red`, `material_fungible`) mantienen únicamente la clave primaria/foránea, lo que es suficiente para identificar a qué subtipo pertenece cada material.

`cableado` incorpora los campos `longitud`, `conector1` y `conector2`, propios de este tipo de material. `herramientas` incorpora el campo `tipo` para distinguir entre herramientas de soldadura y herramientas generales.

### 3.3 Tabla `pcs`

Los PCs se han diseñado como una entidad independiente, no como subtipo de `materialesTaller`. Esta decisión responde a que los equipos informáticos tienen una naturaleza y ciclo de vida diferente al resto de materiales del taller: se ubican siempre en una estación de trabajo concreta, se categorizan entre portátiles y sobremesas, y son el elemento central al que se asocian componentes y periféricos. Tratarlos como una entidad propia simplifica las consultas más frecuentes sobre equipos y su estado.

---

## 4. Relación Periféricos ↔ PCs

La relación entre periféricos y PCs responde a una cardinalidad **N:M**: un periférico (como una impresora de red o un monitor) puede ser utilizado por varios PCs, y un PC puede tener asociados varios periféricos. Para representar correctamente esta relación se ha creado la tabla intermedia `perifericos_pcs`:

```sql
CREATE TABLE perifericos_pcs (
  id_matTa INT,
  id_pc    INT,
  PRIMARY KEY (id_matTa, id_pc),
  FOREIGN KEY (id_matTa) REFERENCES perifericos(id_matTa) ON DELETE CASCADE,
  FOREIGN KEY (id_pc)    REFERENCES pcs(id_pc) ON DELETE CASCADE
);
```

Esta solución evita la redundancia y los valores nulos que habría generado almacenar `id_pc` directamente en la tabla `perifericos`, donde solo habría podido registrarse una asociación por periférico.

---

## 5. Integridad Referencial y Restricciones

- Se aplica `ON DELETE CASCADE` en todas las tablas hijas respecto a su supertipo, garantizando que no queden registros huérfanos.
- Se aplica una restricción `CHECK (cantidad >= 0)` en `materialesTaller` para impedir valores negativos en el stock.
- Los campos `estado` utilizan tipos `ENUM` para limitar los valores posibles y evitar datos inconsistentes.
- La fecha de alta se establece automáticamente con `DEFAULT (current_date)`, eliminando la necesidad de introducirla manualmente en cada inserción.

---

## 6. Conclusión

El esquema resultante refleja fielmente el modelo E/R planteado, resolviendo adecuadamente las relaciones de herencia mediante el patrón de tabla por subtipo, la relación N:M entre periféricos y PCs mediante una tabla intermedia, y la redundancia de datos eliminando campos calculables. El diseño prioriza la integridad y consistencia de los datos sobre la simplicidad de inserción, lo cual es adecuado para un sistema de inventario donde la fiabilidad de la información es crítica.
