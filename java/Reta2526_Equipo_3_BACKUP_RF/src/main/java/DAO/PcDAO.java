/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import AccesoBD.AccesoBaseDatos;
import Objetos.Pc;
import Repositorio.RepositorioPc;
import Utilidades.LoggerApp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PcDAO implements RepositorioPc<Pc>{

    private Connection getConnection() {
        return AccesoBaseDatos.getInstance().getConn();
    }

    @Override
    public List<Pc> listarPc() {

        List<Pc> lista = new ArrayList<>();

        String sql = """
            SELECT *
            FROM pcs
        """;

        try (PreparedStatement stmt = getConnection().prepareStatement(sql);) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                Pc pc = new Pc(
                        String.valueOf(rs.getInt("id_pc")),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getString("estado"),
                        rs.getString("categoria"),
                        rs.getString("id_estacion"),
                        rs.getDate("fecha_alta").toLocalDate()
                                .format(java.time.format.DateTimeFormatter
                                        .ofPattern("dd-MM-yyyy")),
                        rs.getString("observaciones")
                );
                lista.add(pc);
            }
        } catch (Exception e) {
            LoggerApp.log(
                    "❌ Error listando PCs: "
                    + e.getMessage()
            );
        }
        return lista;
    }

    @Override
    public void guardarPc(Pc pc) {

        String sql = """
            INSERT INTO pcs
            (
                nombre,
                descripcion,
                estado,
                categoria,
                id_estacion,
                fecha_alta,
                observaciones
            )
            VALUES
            (?, ?, ?, ?, ?, ?, ?)
        """;

        try ( PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setString(1, pc.getNombre());
            stmt.setString(2, pc.getDescripcion());
            stmt.setString(3, pc.getEstado().toString());
            stmt.setString(4, pc.getCategoria().toString());
            stmt.setString(5, pc.getId_estacion());
            stmt.setDate(
                    6,
                    java.sql.Date.valueOf(pc.getFecha_alta())
            );
            stmt.setString(7, pc.getObservaciones());
            stmt.executeUpdate();
            LoggerApp.log("✅ PC guardado correctamente.");

        } catch (SQLException e) {
            LoggerApp.log(
                    "❌ Error guardando PC: "
                    + e.getMessage()
            );
        }
    }

   

    @Override
    public Pc porIdPc(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   

    @Override
    public void eliminarPc(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
