/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AccesoBD;

import Utilidades.LoggerApp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author DAW126
 */
public class AccesoBaseDatos {
     private static final String BD = "inventario_taller";
    private static final String URL = "jdbc:mysql://localhost:3306/" + BD;

    // Mejor dejar estos datos en constantes separadas y claras
    private static final String USUARIO = "root";
    private static final String CLAVE = "mysql";

    private Connection conn;

    private AccesoBaseDatos() {
        abrirConexion();
    }

    private void abrirConexion() {
        try {
            Properties properties = new Properties();
            properties.setProperty("user", USUARIO);
            properties.setProperty("password", CLAVE);
            properties.setProperty("useSSL", "false");
            properties.setProperty("serverTimezone", "Europe/Madrid");

            conn = DriverManager.getConnection(URL, properties);
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
