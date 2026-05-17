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
import Enum.Estados;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import Utilidades.LoggerApp;

/**
 *
 * @author DAW120
 */
public class AdministradorDAO implements RepositorioMaterial<MaterialInventario>, RepositorioPc<Pc> {

    private Connection getConnection() {
        return AccesoBaseDatos.getInstance().getConn();
    }

    /**
     *
     * @return
     */
    @Override
    public List<MaterialInventario> listarMaterial() {
        List<MaterialInventario> materiales = new ArrayList<>();
        
        String sql = "SELECT id_matTa,nombre,descripcion,estado,cantidad,id_ubi,id_balda,fecha_alta,observaciones FROM materialesTaller";
        
        try (PreparedStatement stmt = getConnection().prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()){
            
            while(rs.next()){
                materiales.add(crearMaterialBD(rs));
            }
            
        } catch (SQLException ex) {
           LoggerApp.log("Error al listar materiales"+ex.getMessage());
        } catch (IdInvalidoException ex) {
            LoggerApp.log("Error con el id "+ex.getMessage());
        } catch (NombreInvalidoException ex) {
            LoggerApp.log("Error con el nombre "+ex.getMessage());
        } catch (CantidadInvalidaException ex) {
            LoggerApp.log("Error con la cantidad "+ex.getMessage());
        } catch (DescripcionInvalidaException ex) {
            LoggerApp.log("Error con la descripcion "+ex.getMessage());
        } catch (EstadoInvalidoException ex) {
            LoggerApp.log("Error con el estado "+ex.getMessage());
        } catch (FechaInvalidaException ex) {
            LoggerApp.log("Error con la fecha "+ex.getMessage());
        }
        
        
        return materiales;
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public MaterialInventario porIdMaterial(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     *Metodo que inserta un objeto en la tabla materialesTaller
     * y en la tabla hija correspondiente
     * 
     * Tiene de atributo un Objeto de la clase MaterialInventario
     * Emplea una sentencia Mysql para insertar sin valores
     * Se conecta a la base y prepara la sentencia
     * *Junto a la sentencia se escribe un statement para que genere la clave AI
     * Se le colocan los valores a insertar desde el objeto
     * *Se crea un result set que devuelve la clave AI
     * *Al crearse se crea un int que guarde la clave
     * Mediante un Switch se comprueba a que clase hija pertenece el objeto
     * Y en funcion de la clase se ejecuta otra insercion a traves de otro metodo
     * @param t
     */
    @Override
    public void guardarMaterial(MaterialInventario t) {
           // comando de insercion en Mysql
        String sql = "INSERT INTO materialesTaller (nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones) VALUES (?,?,?,?,?,?,?,?)";
        
        try (PreparedStatement ps = getConnection().prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getDescripcion());
            ps.setString(3, t.getEstado().toString().toLowerCase());
            ps.setInt(4, t.getCantidad());
            ps.setString(5, t.getId_ubi());
            ps.setObject(6, t.getId_balda());

            ps.setString(7, t.getFecha_alta().toString());

            ps.setString(8, t.getObservaciones());
           
            int filas = ps.executeUpdate();
            
            if (filas != 1) {
                LoggerApp.log("No se ha insertado correctamente en materialesTaller");
            }
            
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
            int idAI = rs.getInt(1);
                
            
            switch (t) {
                case Perifericos pe ->
                    guardarPeriferico(pe, idAI);
                    
                case Cableado ca ->
                    guardarCableado(ca, idAI);
                case Componentes co ->
                    guardarComponente(co, idAI);
                case Herramientas he ->
                    guardarHerramienta(he, idAI);
                case Material_Fungible mf ->
                    guardarMaterialFungible(mf, idAI);
                case Equipos_en_red er ->
                    guardarEquipoRed(er, idAI);
                default -> {
                    LoggerApp.log("Ha ocurrido un error.");
                }
            }
                LoggerApp.log("Se ha insertado correctamente");
            }
        } catch (SQLException ex) {
            LoggerApp.log("ERROR: " + ex.getMessage());
        }

    }

    /**
     *Metodo para eliminar un objeto de la tabla materialesTaller por la id
     * 
     * Tiene el atributo id
     * Emplea una sentencia Mysql para eliminar un objeto por el id
     * Se conecta a la BD y prepara la sentencia
     * Sustituye el simbolo designado por defecto por el id
     * Ejecuta la sentencia
     * 
     * Se eliminan el resto de entradas del objeto con ese id
     * por la configuracion de la BD
     * @param id
     */
    @Override
    public void eliminarMaterial(int id) {
        String sql = "DELETE FROM materialesTaller WHERE id_matTa = ?";
        
        try(PreparedStatement ps = getConnection().prepareStatement(sql)){
            
            ps.setInt(1, id);
            
            int filas = ps.executeUpdate();
            
            if (filas == 0) {
                LoggerApp.log("No se ha eliminado ningun registro en materialesTaller");
            }else if(filas > 1){
                LoggerApp.log("Se han eliminado inesperadamente mas de un registro");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdministradorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     *
     * @return
     */
    @Override
    public List<Pc> listarPc() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Pc porIdPc(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     *
     * @param t
     */
    @Override
    public void guardarPc(Pc t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     *
     * @param id
     */
    @Override
    public void eliminarPc(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // =========================================================================
    
    
    
    
       
        private MaterialInventario crearMaterialBD(ResultSet rs)
        throws SQLException, IdInvalidoException, NombreInvalidoException,
               CantidadInvalidaException, DescripcionInvalidaException,
               EstadoInvalidoException, FechaInvalidaException {
 // Estados estado = Estados.valueOf(rs.getString("estado"));
        //Estados estado = Estados.valueOf(rs.getString("estado").toUpperCase().trim();
    Estados estado = Estados.valueOf(
            rs.getString("estado")
              .toUpperCase()
              .replace(" ", "_")
    );

    return new MaterialInventario(
            rs.getString("id_matTa"),
            rs.getString("nombre"),
            rs.getString("descripcion"),
            estado.name(),
            rs.getString("cantidad"),
            rs.getString("id_ubi"),
            rs.getString("id_balda"),
            rs.getString("fecha_alta"),
            rs.getString("observaciones")
    );
}
//=====================================================================
    //METODOS DE GUARDADO ENCAPSULADOS
    
    /**
     *Metodo para guardar un Objeto Periferico en la tabla perifericos de la BD
     * @param t
     * Es el Objeto Periferico
     * @param id
     * Es un Int que es el id que tendra asignado en la tabla
     * @throws SQLException
     * 
     * Emplea una sentencia de Mysql para insertar en la tabla perifericos
     * Se conecta a la BD y prepara la sentencia
     * Los valores a insertar no estan introducidos(?)
     * Se inserta como un numero entero(int) en el primer (?) el id
     * En el segundo (?) se inserta el atributo Conexion que es un Enum
     * *como una cadena de texto(String) en minusculas(lowerCase)
     * Se ejecuta la sentencia
     * 
     * Se crea una segunda sentencia SQL para insertar en la tabla perifericos_pcs
     * *Una tabla intermedia
     * Se conecta a la BD y prepara la segunda sentencia
     * Los valores a insertar no estan introducidos(?)
     * El primer (?) es el id
     * El segundo (?) es el id_pc de t
     * Se ejecuta la sentencia
     */
    public void guardarPeriferico(Perifericos t, int id) throws SQLException {

        String sql = "INSERT INTO perifericos (id_matTa, conexion) VALUES (?,?)";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, t.getConexion().toString().toLowerCase());

            int filas = ps.executeUpdate();
            if (filas != 1) {
                LoggerApp.log("No se ha insertado correctamente en perifericos.");
            }

            String sql2 = "INSERT INTO perifericos_pcs (id_periferico, id_pc) VALUES (?,?)";

            try (PreparedStatement ps2 = getConnection().prepareStatement(sql2)) {
                ps2.setInt(1, id);
                ps2.setInt(2, t.getId_pc());

                int filas2 = ps2.executeUpdate();
                if (filas2 != 1) {
                    LoggerApp.log("No se ha insertado correctamente en perifericos_pcs");
                }

            } catch (SQLException ex) {
                LoggerApp.log("ERROR: " + ex.getMessage());
            }
        } catch (SQLException ex) {
            LoggerApp.log("ERROR: " + ex.getMessage());
        }

    }

    /**
     *METODO PARA INSERTAR UN OBJETO COMPONENTE EN LA TABLA componentes DE LA BD
     * @param t
     * Es el Objeto Componente que se va a insertar
     * @param id
     * Es el id con el que va a ser insertado
     * 
     * Se crea una sentencia SQL para insertar como cadena de texto(String) 
     * *INSERT INTO componentes (id_matTa, id_pc) VALUES (?,?)
     * **(?) es un simbolo que puede ser cambiado por datos
     * Se conecta a la BD y prepara la sentencia para manipularla
     * Al primer (?) se le sustituye por id
     * Al segundo (?) se le sustituye por el atributo id_pc de t
     * *Ambos son valores enteros (int)
     * 
     * Se crea un entero(int) 
     * que recoge el numero de filas afectadas al ejecutarse la sentencia
     * 
     */
    public void guardarComponente(Componentes t, int id) {
        String sql = "INSERT INTO componentes (id_matTa, id_pc) VALUES (?,?)";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, t.getId_pc());

            int filas = ps.executeUpdate();
            if (filas != 1) {
                System.out.println("No se ha insertado correctamente en componentes");
            }
        } catch (SQLException ex) {
            LoggerApp.log("ERROR: " + ex.getMessage());
        }
    }

    /**
     *METODO PARA INSERTAR UN OBJETO EQUIPO_DE_RED EN LA TABLA equipos_red DE LA BD
     * @param t
     * Es el Objeto Equipos_en_red
     * @param id
     * Es el id con el que va a ser insertado
     * 
     * Se crea una sentencia SQL para insertar como cadena de texto(String) 
     * *INSERT INTO equipos_red (id_matTa, num_puertos) VALUES (?,?)
     * **(?) es un simbolo que puede ser cambiado por datos
     * Se conecta a la BD y prepara la sentencia para manipularla
     * Al primer (?) se le sustituye por id
     * Al segundo (?) se le sustituye por el atributo numPuertos de t
     * *Ambos son valores enteros (int)
     * 
     * Se crea un entero(int) 
     * que recoge el numero de filas afectadas al ejecutarse la sentencia
     * 
     */
    public void guardarEquipoRed(Equipos_en_red t, int id) {
        String sql = "INSERT INTO equipos_red (id_matTa, num_puertos) VALUES (?,?)";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
             ps.setInt(1, id);
            ps.setInt(2, t.getNumPuertos());

            int filas = ps.executeUpdate();
            if (filas != 1) {
                LoggerApp.log("No se ha insertado correctamente en equiops_red.");
            }
        } catch (SQLException ex) {
            LoggerApp.log("ERROR: " + ex.getMessage());
        }
    }

    /**
     *
     * @param t
     * @param id
     */
    public void guardarCableado(Cableado t, int id) {
        String sql = "INSERT INTO cableado (id_matTa, longitud, conector1, conector2) VALUES (?,?,?,?)";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)){
            ps.setInt(1, id);
            ps.setDouble(2, t.getLongitud());
            ps.setString(3, t.getConector1());
            ps.setString(4, t.getConector2());

            int filas = ps.executeUpdate();
            if (filas != 1) {
                LoggerApp.log("No se ha insertado correctamente en cableado");
            }
        }catch (SQLException ex) {
            LoggerApp.log("ERROR: " + ex.getMessage());
        }
    }

    /**
     *
     * @param t
     * @param id
     */
    public void guardarHerramienta(Herramientas t, int id) {
        String sql = "INSERT INTO herramientas (id_matTa, tipo) VALUES (?,?)";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, t.getTipo().toString().toLowerCase());

            int filas = ps.executeUpdate();
            if (filas != 1) {
                LoggerApp.log("No se ha insertado correctamente en herramientas.");
            }
        } catch (SQLException ex) {
            LoggerApp.log("ERROR: " + ex.getMessage());
        }
    }

    /**
     *
     * @param t
     * @param id
     */
    public void guardarMaterialFungible(Material_Fungible t, int id) {
        String sql = "INSERT INTO material_fungible (id_matTa, estado) VALUES (?,?)";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
             ps.setInt(1, id);
            ps.setString(2, t.getEstadoFungible().toString().toLowerCase());

            int filas = ps.executeUpdate();
            if (filas != 1) {
                LoggerApp.log("No se ha insertado correctamente en material fungible.");
            }
        } catch (SQLException ex) {
         
            LoggerApp.log("ERROR: " + ex.getMessage());
        }
    }

}
