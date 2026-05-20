/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AccesoBD;

import Utilidades.LoggerApp;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author DAW126
 */
public class AccesoBaseDatos {

    private static final String CONFIG_FILE = "/db.properties";

    private Connection conn;

    private AccesoBaseDatos() {
        abrirConexion();
    }

    private void abrirConexion() {
        Properties config = new Properties();

        try (InputStream input = AccesoBaseDatos.class.getResourceAsStream(CONFIG_FILE);) {

            if (input == null) {
                LoggerApp.log("No se encontró el archivo" + CONFIG_FILE);
                conn = null;
                return;
            }
            config.load(input);
            LoggerApp.log("Properties cargadas correctamente.");
        } catch (IOException ex) {
            LoggerApp.log("Error al leer el archivo de configuración.");
            LoggerApp.log("Mensaje: " + ex.getMessage());
            conn = null;
            return;
        }

        // ahora se construye la URL con los valores del .properties
        String host = config.getProperty("db.host");
        String puerto = config.getProperty("db.puerto");
        String nombre = config.getProperty("db.nombre");
        String timezone = config.getProperty("db.serverTimezone");

        //LA URL FINAL
        String url = "jdbc:mysql://" + host + ":" + puerto + "/" + nombre
                + "?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone="
                + timezone;

        // CREDENCIALES
        Properties properties = new Properties();
        properties.setProperty("user", config.getProperty("db.usuario"));
        properties.setProperty("password", config.getProperty("db.clave"));
        properties.setProperty("useSSL", "false");
        properties.setProperty("serverTimezone", timezone);

        try {
            conn = DriverManager.getConnection(url, properties);
            LoggerApp.log("Conexión correcta a la base de datos.");

        } catch (SQLException ex) {
            LoggerApp.log("Error al conectar con la base de datos.");
            LoggerApp.log("Mensaje: " + ex.getMessage());
            conn = null;
        }
    }

    /**
     *
     * @return
     */
    public static AccesoBaseDatos getInstance() {
        return AccesoBaseDatosHolder.INSTANCE;
    }

    private static class AccesoBaseDatosHolder {

        private static final AccesoBaseDatos INSTANCE = new AccesoBaseDatos();
    }

    /**
     *
     * @return
     */
    public Connection getConn() {
        try {
            if (conn == null || conn.isClosed()) {
                abrirConexion();
            }
        } catch (SQLException ex) {
            LoggerApp.log("Error al comprobar el estado de la conexión.");
            LoggerApp.log("Mensaje: " + ex.getMessage());
        }
        return conn;
    }

    /**
     *
     * @return
     */
    public boolean cerrar() {
        if (conn == null) {
            return true;
        }

        try {
            conn.close();
            return true;
        } catch (SQLException ex) {
            LoggerApp.log("Error al cerrar la conexión.");
            LoggerApp.log("Mensaje: " + ex.getMessage());
            return false;
        }
    }
}
