/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Excepciones.CantidadInvalidaException;
import Excepciones.DescripcionInvalidaException;
import Excepciones.EstadoInvalidoException;
import Excepciones.FechaInvalidaException;
import Excepciones.IdInvalidoException;
import Excepciones.NombreInvalidoException;
import AccesoBD.AccesoBaseDatos;
import Objetos.Cableado;
import Objetos.Componentes;
import Objetos.Equipos_en_red;
import Objetos.Herramientas;
import Objetos.MaterialInventario;
import Objetos.Material_Fungible;
import Objetos.Pc;
import Objetos.Perifericos;
import Repositorio.RepositorioMaterial;
import Repositorio.RepositorioPc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

        try (PreparedStatement ps = getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getDescripcion());
            ps.setString(3, t.getEstado().toString().toLowerCase());
            ps.setInt(4, t.getCantidad());
            ps.setString(5, t.getId_ubi());
            ps.setInt(6, t.getId_balda());

            ps.setString(7, t.getFecha_alta().toString());

            ps.setString(8, t.getObservaciones());
           
            int filas = ps.executeUpdate();
            
             
            
            if (filas != 1) {
                System.out.println("No se ha insertado correctamente en materialesTaller");
            }
            ResultSet rs = ps.getGeneratedKeys();
            int idAI = rs.getInt(1);
            switch (t) {
                case Perifericos pe ->
                    guardarPeriferico(pe, idAI);
                case Cableado ca ->
                    guardarCableado(ca);
                case Componentes co ->
                    guardarComponente(co);
                case Herramientas he ->
                    guardarHerramienta(he);
                case Material_Fungible mf ->
                    guardarMaterialFungible(mf);
                default -> {
                    System.out.println("Ha ocurrido un error.");
                }
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

    }

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

    //
    //
    public void guardarPeriferico(Perifericos t, int id) throws SQLException {

        String sql = "INSERT INTO perifericos (id_matTa, conexion) VALUES (?,?)";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, t.getConexion().toString().toLowerCase());

            int filas = ps.executeUpdate();
            if (filas != 1) {
                System.out.println("No se ha insertado correctamente en perifericos.");
            }

            String sql2 = "INSERT INTO perifericos_pcs (id_periferico, id_pc) VALUES (last_insert_id(),?)";

            try (PreparedStatement ps2 = getConnection().prepareStatement(sql2)) {
                ps.setInt(1, t.getId_pc());

                int filas2 = ps2.executeUpdate();
                if (filas != 1) {
                    System.out.println("No se ha insertado correctamente en perifericos_pcs");
                }

            } catch (SQLException ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

    }

    public void guardarComponente(Componentes t) {
        String sql = "INSERT INTO componentes (id_matTa, id_pc) VALUES (last_insert_id(),?)";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, t.getId_pc());

            int filas = ps.executeUpdate();
            if (filas != 1) {
                System.out.println("No se ha insertado correctamente en componentes");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    public void guardarEquipoRed(Equipos_en_red t) {
        String sql = "INSERT INTO equipos_red (id_matTa, num_puertos) VALUES (last_insert_id(),?)";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, t.getNumPuertos());

            int filas = ps.executeUpdate();
            if (filas != 1) {
                System.out.println("No se ha insertado correctamente en equiops_red.");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    public void guardarCableado(Cableado t) {
        String sql = "INSERT INTO cableado (id_matTa, longitud, conector1, conector2) VALUES (last_insert_id(),?,?,?)";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setDouble(1, t.getLongitud());
            ps.setString(2, t.getConector1());
            ps.setString(3, t.getConector2());

            int filas = ps.executeUpdate();
            if (filas != 1) {
                System.out.println("No se ha insertado correctamente en cableado");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    public void guardarHerramienta(Herramientas t) {
        String sql = "INSERT INTO herramientas (id_matTa, tipo) VALUES (last_insert_id(),?)";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, t.getTipo().toString());

            int filas = ps.executeUpdate();
            if (filas != 1) {
                System.out.println("No se ha insertado correctamente en herramientas.");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    public void guardarMaterialFungible(Material_Fungible t) {
        String sql = "INSERT INTO material_fungible (id_matTa, estado) VALUES (last_insert_id(),?)";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setString(1, t.getEstadoFungible().toString());

            int filas = ps.executeUpdate();
            if (filas != 1) {
                System.out.println("No se ha insertado correctamente en material fungible.");
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

}
