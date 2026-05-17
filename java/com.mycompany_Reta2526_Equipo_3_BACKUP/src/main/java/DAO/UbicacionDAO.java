/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import AccesoBD.AccesoBaseDatos;

import Utilidades.LoggerApp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UbicacionDAO {

    private Connection getConnection() {
        return AccesoBaseDatos.getInstance().getConn();
    }

    public List<String> listarUbicaciones() {

        List<String> lista = new ArrayList<>();

        String sql = """
            SELECT id_ubi
            FROM ubicacion
            ORDER BY id_ubi
        """;

        try (PreparedStatement stmt = getConnection().prepareStatement(sql);) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(
                        rs.getString("id_ubi")
                );
            }
        } catch (SQLException e) {
            LoggerApp.log(
                    "❌ Error listando ubicaciones: "
                    + e.getMessage()
            );
        }
        return lista;
    }

    public List<String> listarEstaciones() {

        List<String> lista = new ArrayList<>();

        String sql = """
            SELECT id_ubi
            FROM estacion
            ORDER BY id_ubi
        """;

        try (PreparedStatement stmt = getConnection().prepareStatement(sql);) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(
                        rs.getString("id_ubi")
                );
            }
        } catch (SQLException e) {
            LoggerApp.log(
                    "❌ Error listando estaciones: "
                    + e.getMessage()
            );
        }
        return lista;
    }
}
