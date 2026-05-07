# Justificación y Elección de Licencia de Uso

## Tipos de licencias de software: software libre, propietario y demás

Existen diferentes tipos de licencias de software: propietarias, libres, de código abierto, de dominio público y de software como servicio (SaaS). Cada licencia tiene sus ventajas y desventajas, y la elección dependerá de las necesidades del proyecto de software. Es importante revisar cuidadosamente los términos y condiciones de cada licencia antes de decidir cuál utilizar.

---

## Tipos de licencias de software en la actualidad

Las licencias de software son un tema importante en la industria del software y hay varias opciones disponibles en la actualidad.

### 1. Software Propietario

Distribuido normalmente en formato binario, sin acceso al código fuente, en el cual el autor no transmite ninguno de los derechos, sino que establece las condiciones en que el software puede ser utilizado, limitando normalmente los derechos de ejecución, copia, modificación, cesión o redistribución. El propietario —sea quien lo ha desarrollado o quien lo distribuye— sólo vende derechos restringidos de uso, con lo que el usuario no *adquiere* sino que más bien *alquila*; es decir, el producto pertenece al propietario, desarrollador o proveedor, que concede al usuario el "privilegio" de utilizarlo.

### 2. Software de Dominio Público

No hay licencia porque no hay forma de conocer al autor, y por tanto puede ser utilizado tanto para desarrollar software propietario como software libre o de fuentes abiertas.

### 3. Software Libre

Según la filosofía de la **Free Software Foundation**, se distribuye junto con el código fuente, reconociéndose al usuario cuatro libertades:

1. **Libertad de utilizar** el programa con cualquier fin.
2. **Libertad de estudiar** cómo funciona el programa y de adaptar su código a necesidades específicas *(requiere acceso al código fuente)*.
3. **Libertad de distribuir** copias a otros usuarios (con o sin modificaciones).
4. **Libertad de mejorar** el programa y de hacer públicas y distribuir las modificaciones *(requiere acceso al código fuente)*.

> **Nota:** El software libre no implica que sea gratuito; recibe su denominación por las *libertades* que se conceden al usuario.

### 4. Software de Fuentes Abiertas / Código Abierto

Según la filosofía de la **Open Source Initiative**, la licencia debe cumplir los siguientes criterios:

1. Libre distribución.
2. Distribución del código fuente.
3. La licencia debe permitir la modificación del código fuente, los desarrollos derivados y su redistribución en las mismas condiciones que el software original.
4. Integridad del código fuente del autor. La licencia puede imponer que los desarrollos derivados se redistribuyan con un nombre o número de versión diferente al del software original.
5. La licencia no debe ser discriminatoria de persona alguna o grupos de personas.
6. La licencia no debe restringir la utilización del software a campos de dominio o actividad.
7. Los derechos otorgados al programa deben ser aplicables a todos aquellos a quienes el software es redistribuido, sin imponer condiciones complementarias.
8. Los derechos otorgados a un programa no deben depender de que forme parte de una distribución de software específica.
9. La licencia no debe imponer restricciones en otro software que se distribuya junto con la distribución licenciada.
10. La licencia debe ser neutral en relación con la tecnología.

---

## Licencias MIT, GPLv3 y Apache 2.0

La elección entre estas tres licencias depende principalmente de si se prefiere la máxima permisividad o asegurar que las modificaciones del código sigan siendo abiertas. MIT y Apache son licencias **permisivas**, mientras que GPLv3 es una licencia **copyleft estricta**.

### 1. Licencia MIT

Es la más permisiva, simple y breve de las tres. Su filosofía es *"haz lo que quieras con el código"*.

- **Ventajas:** Máxima libertad, ideal para proyectos pequeños, bibliotecas y fomentar la adopción masiva.
- **Desventajas:** No aborda explícitamente la concesión de patentes (aunque se asume).
- **Uso típico:** Bibliotecas de programación (ej. jQuery, Node.js).

### 2. Licencia Apache 2.0

Es una licencia permisiva moderna y robusta, ideal para entornos empresariales.

- **Ventajas:** Incluye una cláusula explícita de patentes y protección contra *patent trolls*.
- **Diferencia con MIT:** Es más larga y legalmente detallada, ideal si preocupa la propiedad intelectual.
- **Uso típico:** Proyectos grandes de código abierto (ej. Android, Kubernetes).

### 3. Licencia GPLv3 (GNU General Public License)

Es una licencia copyleft estricta que garantiza que el software siga siendo libre.

- **Ventajas:** Protege la libertad del software; exige que si modificas y distribuyes el código, debes publicar tus cambios bajo la misma licencia.
- **Desventajas:** No apta para software comercial propietario.
- **Diferencia clave:** Si tomas código GPLv3, tu producto final debe ser GPLv3.

---

## Resumen Comparativo

| Característica     | MIT          | Apache 2.0     | GPLv3                        |
|--------------------|--------------|----------------|------------------------------|
| Tipo               | Permisiva    | Permisiva      | Copyleft (Estricta)          |
| Uso Comercial      | Sí           | Sí             | Sí                           |
| Modificación       | Sí           | Sí             | Sí                           |
| Distribución       | Sí           | Sí             | Sí                           |
| Privatización      | Permitida    | Permitida      | No (debe ser código abierto) |
| Cláusula Patentes  | No explícita | Sí (Explícita) | Sí                           |
| Complejidad        | Muy simple   | Detallada      | Muy detallada                |

---

## Nuestra Elección: GPLv3

Nuestro proyecto forma parte de un Reto de formación de estudiantes de Desarrollo de Aplicaciones Web. Creemos que el código debe ser libre y accesible, por lo que consideramos que **GPL** es la licencia que más se ajusta al *scope* de nuestro proyecto y nuestra sensibilidad como desarrolladores.

La adopción de la licencia GPLv3 responde a nuestro compromiso estratégico con el modelo copyleft, garantizando que el software y cualquier mejora futura permanezcan siempre accesibles para la comunidad. Al elegir este marco legal, aseguramos que el conocimiento compartido no pueda ser privatizado en versiones cerradas, fomentando un ciclo de innovación colaborativa donde cada contribución revierte directamente en beneficio del interés común y de la transparencia tecnológica.

Desde una perspectiva de seguridad y gobernanza, la GPLv3 nos proporciona una protección robusta frente a litigios de patentes y prácticas comerciales, blindando la soberanía del código fuente. Esta elección no solo protege los derechos fundamentales de los usuarios finales para ejecutar, estudiar y modificar la herramienta, sino que también posiciona nuestro proyecto como un estándar de confianza y ética profesional dentro del sector del software libre.
