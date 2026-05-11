/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reta2526_equipo_3;

import com.mycompany.reta2526_equipo_3.Excepciones.IdInvalidoException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DAW120
 */
public class AdministradorDAO {

    private Connection getConnection() {
        return AccesoBaseDatos.getInstance().getConn();
    }
    
  
    public List<MaterialInventario> listar() throws IdInvalidoException {
    List<MaterialInventario> materiales = new ArrayList<>();
        String sql = "SELECT id_inv, nombre, descripcion, estado, cantidad, id_estacion, id_armario, id_balda, fecha_alta, observaciones FROM materialesTaller";
 
        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                materiales.add(crearMaterial(rs));
            }

        } catch (SQLException ex) {
            System.out.println("Error al listar usuarios: " + ex.getMessage());
        }

        return materiales;    
    }

   
    public List<MaterialInventario> ordenarAsc(String id) {
    List<MaterialInventario> materiales = new ArrayList<>();
        String sql = "SELECT id_inv, nombre, descripcion, estado, cantidad, id_estacion, id_armario, id_balda, fecha_alta, observaciones FROM materialesTaller order by ?";
        return null;
     }

  
    public void guardar(Administrador t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
    public void eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
   
    private MaterialInventario crearMaterial(ResultSet rs) throws SQLException, IdInvalidoException{
//        Estados estado = Estados.valueOf(rs.getString("estado"));
      
        return new MaterialInventario(rs.getString("id_inv"), 
                rs.getString("nombre"), 
                rs.getString("descripcion"), 
                rs.getString("estado"), 
                rs.getString("cantidad"), 
                rs.getString("id_estacion"), 
                rs.getString("id_armario"), 
                rs.getString("id_balda"), 
                rs.getString("fecha_alta"), 
                rs.getString("observaciones"));
    }
    
}
