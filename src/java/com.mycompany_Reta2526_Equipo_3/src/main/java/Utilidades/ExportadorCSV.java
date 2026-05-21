/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import AccesoBD.AccesoBaseDatos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Exportador del inventario a ficheros CSV.
 *@see 
 * Genera un fichero .csv por cada ubicación (armario y estación)
 * dentro de la carpeta {@code exports/}, que puede ser leída directamente
 * desde una web con JavaScript.
 *
 * Uso típico: llamar a {@link #exportarTodo()} justo después
 * del login, en {@code App.java}. La exportación se ejecuta antes de abrir
 * la ventana principal.
 *
 * Formato de los ficheros generados:
 * 
 *   exports/
 *     ARM01.csv
 *     ARM02.csv
 *     ...
 *     EST01.csv
 *     EST02.csv
 *     ...
 * 
 *
 * Cabecera de cada CSV:
 * 
 *   id,nombre,descripcion,estado,cantidad,ubicacion,balda,fecha_alta,observaciones,tipo
 * 
 *
 * @author Equipo 3
 */
public class ExportadorCSV {

    // -------------------------------------------------------------------------
    // CONSTANTES
    // -------------------------------------------------------------------------

    /**
     * Carpeta donde se guardan los ficheros CSV generados.
     * @see 
     * La ruta es relativa al directorio de ejecución del programa.
     * Si quieres que la web la lea directamente, cámbiala por la ruta
     * absoluta de tu servidor: p.ej. "C:/xampp/htdocs/inventario/exports"
     */
    private static final String DIRECTORIO_SALIDA = "./exports";

    /**
     * Codificación usada para escribir los CSV.
     * @see 
     * UTF-8 asegura que los caracteres especiales (tildes, ñ) se escriban bien.
     */
    private static final String CODIFICACION = "UTF-8";

    /**
     * Cabecera que aparece en la primera línea de todos los CSV.
     */
    private static final String CABECERA_CSV =
            "id,nombre,descripcion,estado,cantidad,ubicacion,balda,fecha_alta,observaciones,tipo";

    // -------------------------------------------------------------------------
    // PUNTO DE ENTRADA PÚBLICO
    // -------------------------------------------------------------------------

    /**
     * Ejecuta la exportación completa del inventario a CSV.
     *@see 
     * El programa espera a que termine antes de continuar, por lo que
     * la ventana principal se abrirá justo después de que todos los ficheros
     * estén generados. Con un inventario normal (decenas o pocos cientos de
     * registros) esto es prácticamente instantáneo.
     *
     * 
     * 
     */
    public static void exportarTodo() {

    System.out.println(">>> EXPORTADOR INICIADO");

    crearDirectorioSalida();

    System.out.println(">>> CARPETA OK");

    exportarPorTipoUbicacion("armario");

    exportarPorTipoUbicacion("estacion");

    System.out.println(">>> EXPORTADOR FINALIZADO");
}
//    public static void exportarTodo() {
//        LoggerApp.log("Iniciando exportacion CSV...");
//
//        // 1. Crear la carpeta de salida si no existe todavía
//        crearDirectorioSalida();
//
//        // 2. Exportar un CSV por cada armario registrado en la BD
//        exportarPorTipoUbicacion("armario");
//
//        // 3. Exportar un CSV por cada estación registrada en la BD
//        exportarPorTipoUbicacion("estacion");
//
//        LoggerApp.log("Exportacion CSV finalizada. Carpeta: " + DIRECTORIO_SALIDA + "/");
//    }

    // -------------------------------------------------------------------------
    // MÉTODOS PRIVADOS — ESTRUCTURA GENERAL
    // -------------------------------------------------------------------------

    /**
     * Crea la carpeta {@code exports/} si todavía no existe.
     * @see 
     * Si no se puede crear, registra el error en el log y continúa
     * (los intentos de escritura posteriores fallarán con su propio mensaje).
     */
    private static void crearDirectorioSalida() {
        
       
        
        
        
        try {
            Files.createDirectories(Paths.get(DIRECTORIO_SALIDA));
        } catch (IOException e) {
            LoggerApp.log("No se pudo crear el directorio '" + DIRECTORIO_SALIDA + "': " + e.getMessage());
        }
    }

    /**
     * Obtiene todos los IDs de una tabla de ubicación (armario o estacion)
     * y genera un CSV por cada uno.
     *@see 
     * a consulta es genérica y funciona igual para las dos tablas porque
     * ambas tienen la columna {@code id_ubi}.
     *
     * @param tablaUbicacion nombre de la tabla SQL: {@code "armario"} o {@code "estacion"}
     */
    private static void exportarPorTipoUbicacion(String tablaUbicacion) {

        String sql = "SELECT id_ubi FROM " + tablaUbicacion + " ORDER BY id_ubi";

        // Obtenemos la conexión una sola vez y la reutilizamos para todos los CSV
        Connection conn = AccesoBaseDatos.getInstance().getConn();

        if (conn == null) {
            LoggerApp.log("No hay conexion con la BD. No se puede exportar " + tablaUbicacion + ".");
            return;
        }

        try (PreparedStatement ps = conn.prepareStatement(sql);
     ResultSet rs = ps.executeQuery()) {

    boolean hayDatos = false;

    while (rs.next()) {
        hayDatos = true;

        String idUbicacion = rs.getString("id_ubi");
        System.out.println("UBICACION: " + idUbicacion);

        exportarUbicacion(idUbicacion, conn);
    }

    if (!hayDatos) {
        System.out.println("NO HAY UBICACIONES EN: " + tablaUbicacion);
    }

} catch (SQLException e) {
    e.printStackTrace();
}
    }

    // -------------------------------------------------------------------------
    // MÉTODOS PRIVADOS — GENERACIÓN DEL CSV POR UBICACIÓN
    // -------------------------------------------------------------------------

    /**
     * Genera el fichero CSV para una ubicación concreta.
     *@see 
     * Realiza un SELECT sobre {@code materialesTaller} con LEFT JOINs a todas
     * las tablas hijas para determinar el tipo de cada material (cableado,
     * componente, equipo de red, herramienta, fungible o periférico).
     *
     * El fichero se guarda en {@code exports/<idUbicacion>.csv}.
     *
     * @param idUbicacion identificador de la ubicación, p.ej. {@code "ARM01"} o {@code "EST03"}
     * @param conn        conexión activa a la base de datos (se reutiliza para evitar
     *                    abrir y cerrar conexiones en cada iteración)
     */
    private static void exportarUbicacion(String idUbicacion, Connection conn) {

        // ------------------------------------------------------------------
        // CONSULTA SQL
        // Obtiene todos los materiales de la ubicación indicada.
        //
        // Los LEFT JOIN comprueban en qué tabla hija aparece cada material:
        //   - Si tiene fila en "cableado"          → tipo = 'cableado'
        //   - Si tiene fila en "componentes"       → tipo = 'componente'
        //   - Si tiene fila en "equipos_red"       → tipo = 'equipo_red'
        //   - Si tiene fila en "herramientas"      → tipo = 'herramienta'
        //   - Si tiene fila en "material_fungible" → tipo = 'fungible'
        //   - Si tiene fila en "perifericos"       → tipo = 'periferico'
        //   - Si no aparece en ninguna             → tipo = 'material' (caso raro)
        //
        // El CASE evalúa los JOIN en orden y devuelve el primer tipo que coincida.
        // ------------------------------------------------------------------
        String sql = """
                SELECT
                    m.id_matTa,
                    m.nombre,
                    m.descripcion,
                    m.estado,
                    m.cantidad,
                    m.id_ubi,
                    m.id_balda,
                    m.fecha_alta,
                    m.observaciones,
                    CASE
                        WHEN c.id_matTa  IS NOT NULL THEN 'cableado'
                        WHEN co.id_matTa IS NOT NULL THEN 'componente'
                        WHEN er.id_matTa IS NOT NULL THEN 'equipo_red'
                        WHEN h.id_matTa  IS NOT NULL THEN 'herramienta'
                        WHEN mf.id_matTa IS NOT NULL THEN 'fungible'
                        WHEN p.id_matTa  IS NOT NULL THEN 'periferico'
                        ELSE 'material'
                    END AS tipo
                FROM materialesTaller m
                LEFT JOIN cableado          c  ON m.id_matTa = c.id_matTa
                LEFT JOIN componentes       co ON m.id_matTa = co.id_matTa
                LEFT JOIN equipos_red       er ON m.id_matTa = er.id_matTa
                LEFT JOIN herramientas      h  ON m.id_matTa = h.id_matTa
                LEFT JOIN material_fungible mf ON m.id_matTa = mf.id_matTa
                LEFT JOIN perifericos       p  ON m.id_matTa = p.id_matTa
                WHERE m.id_ubi = ?
                ORDER BY m.id_matTa
                """;

        // Ruta completa del fichero de salida
        // File.separator pone '/' en Linux/Mac y '\' en Windows automáticamente
        String rutaFichero = DIRECTORIO_SALIDA + File.separator + idUbicacion + ".csv";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            // Sustituimos el "?" por el ID de la ubicación que queremos exportar
            ps.setString(1, idUbicacion);

            try (ResultSet rs = ps.executeQuery();

                 // PrintWriter con UTF-8 para que las tildes y la ñ se guarden bien
                 PrintWriter writer = new PrintWriter(
                         new OutputStreamWriter(
                                 new FileOutputStream(rutaFichero), CODIFICACION))) {

                // Primera línea del fichero: nombres de las columnas
                writer.println(CABECERA_CSV);

                // Contador para el mensaje de log al final
                int registros = 0;

                // Cada fila del ResultSet es un material → una línea del CSV
                while (rs.next()) {
                    writer.println(construirLineaCSV(rs));
                    registros++;
                }

                LoggerApp.log(idUbicacion + ".csv generado con " + registros + " registros.");

            } // PrintWriter se cierra aquí solo (try-with-resources)

        } catch (Exception e) {
            LoggerApp.log("Error exportando " + idUbicacion + ": " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    // MÉTODOS PRIVADOS — UTILIDADES CSV
    // -------------------------------------------------------------------------

    /**
     * Construye la línea CSV correspondiente a la fila actual del ResultSet.
     *@see 
     * Cada campo pasa por {@link #escaparCSV(String)} antes de unirse
     * con comas, para garantizar que los valores que contengan comas,
     * comillas o saltos de línea no rompan el formato del fichero.
     *
     * @param rs ResultSet posicionado en la fila a convertir
     * @return   cadena CSV con los campos separados por comas
     * @throws SQLException si hay un error leyendo alguna columna del ResultSet
     */
    private static String construirLineaCSV(ResultSet rs) throws SQLException {
        return escaparCSV(rs.getString("id_matTa"))     + "," +
               escaparCSV(rs.getString("nombre"))        + "," +
               escaparCSV(rs.getString("descripcion"))   + "," +
               escaparCSV(rs.getString("estado"))        + "," +
               escaparCSV(rs.getString("cantidad"))      + "," +
               escaparCSV(rs.getString("id_ubi"))        + "," +
               escaparCSV(rs.getString("id_balda"))      + "," +
               escaparCSV(rs.getString("fecha_alta"))    + "," +
               escaparCSV(rs.getString("observaciones")) + "," +
               escaparCSV(rs.getString("tipo"));
    }

    /**
     * Escapa un valor para que sea seguro dentro de un fichero CSV.
     *@see 
     * Reglas aplicadas (estándar RFC 4180):
     * <ul>
     *   <li>Si el valor es {@code null} → devuelve cadena vacía.</li>
     *   <li>Si el valor contiene comas, comillas o saltos de línea
     *       → lo envuelve entre comillas dobles.</li>
     *   <li>Las comillas dobles que ya había dentro se duplican
     *       ({@code "} → {@code ""}) para que no se confundan con
     *       las comillas del envoltorio.</li>
     * </ul>
     *
     * Ejemplos:
     * 
     *   "Cable RJ45"      →   Cable RJ45          (sin cambios)
     *   "Cable, RJ45"     →   "Cable, RJ45"        (envuelto por la coma)
     *   null              →                        (cadena vacía)
     * 
     *
     * @param valor campo a escapar; puede ser {@code null}
     * @return      valor listo para insertar en una línea CSV
     */
    private static String escaparCSV(String valor) {

        // Los campos nulos (por ejemplo id_balda en estaciones) se dejan vacíos
        if (valor == null) {
            return "";
        }

        // Comprobamos si hay caracteres que puedan romper el formato CSV
        boolean necesitaComillas = valor.contains(",")
                                || valor.contains("\"")
                                || valor.contains("\n")
                                || valor.contains("\r");

        if (necesitaComillas) {
            // Duplicamos las comillas internas y envolvemos el valor
            return "\"" + valor.replace("\"", "\"\"") + "\"";
        }

        // Si no hay caracteres conflictivos, el valor se escribe tal cual
        return valor;
    }
}
