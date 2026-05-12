/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reta2526_equipo_3;

import Excepciones.CantidadInvalidaException;
import Excepciones.DescripcionInvalidaException;
import Excepciones.EstadoInvalidoException;
import Excepciones.FechaInvalidaException;
import Excepciones.IdInvalidoException;
import Excepciones.NombreInvalidoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DAW120
 */
public class AdministradorDAO implements RepositorioMaterial<MaterialInventario>, RepositorioPc<Pc> {

    private Connection getConnection() {
        return AccesoBaseDatos.getInstance().getConn();
    }

    @Override
    public List<MaterialInventario> listarMaterial() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public MaterialInventario porIdMaterial(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void guardarMaterial(MaterialInventario t) {
 String sql = "INSERT INTO materialesTaller (nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones) VALUES (?,?,?,?,?,?,?,?)";
 
       try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
           ps.setString(1, t.getNombre());
           ps.setString(2, t.getDescripcion());
           ps.setString(3, t.getEstado().toString());
           ps.setInt(4, t.getCantidad());
           ps.setString(5, t.getId_ubi());
           ps.setInt(6, t.getId_balda());
           
           ps.setString(7, t.getFecha_alta().toString());
           
           ps.setString(8, t.getObservaciones());
           
           
           
           int filas = ps.executeUpdate();
           if (filas != 1) {
               System.out.println("No se ha insertado correctamente");
           }
       } catch (SQLException ex) {
           System.out.println("ERROR: " + ex.getMessage());
       }    }


    @Override
    public void eliminarMaterial(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Pc> listarPc() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Pc porIdPc(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void guardarPc(Pc t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminarPc(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // =========================================================================
    
    private MaterialInventario crearMaterialBD(ResultSet rs) throws SQLException, IdInvalidoException, NombreInvalidoException, CantidadInvalidaException, DescripcionInvalidaException, EstadoInvalidoException, FechaInvalidaException {
//        Estados estado = Estados.valueOf(rs.getString("estado"));
 
        return new MaterialInventario(rs.getString("id_matTa"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getString("estado"),
                rs.getString("cantidad"),
                rs.getString("id_armario"),
                rs.getString("id_balda"),
                rs.getString("fecha_alta"),
                rs.getString("observaciones"));
    }
    
    public void guardarPeriferico(Perifericos t) throws SQLException {
    String sql = "INSERT INTO perifericos (id_matTa, conexion) VALUES (last_insert_id(),?)";
 
       try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
           ps.setString(1, t.getConexion().toString());
           
           int filas = ps.executeUpdate();
           if (filas != 1) {
               System.out.println("No se ha insertado correctamente");
           }
           
           String sql2 = "INSERT INTO perifericos (id_matTa, conexion) VALUES (last_insert_id(),?)";
 
       try (PreparedStatement ps2 = getConnection().prepareStatement(sql)) {
           ps.setString(1, t.getConexion().toString());
           
           int filas2 = ps2.executeUpdate();
            if (filas != 1) {
                System.out.println("No se ha insertado correctamente");
            }
           
           
         } catch (SQLException ex) {
             System.out.println("ERROR: " + ex.getMessage());
           }    
       }catch (SQLException ex) {
           System.out.println("ERROR: " + ex.getMessage());
       }    
           
       } 
    
    
    
    
    
}
