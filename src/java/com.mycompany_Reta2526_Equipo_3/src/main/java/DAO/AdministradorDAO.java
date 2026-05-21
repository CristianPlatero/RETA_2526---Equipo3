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
import Excepciones.CategoriaInvalidaException;
import Excepciones.ConectorInvalidoException;
import Excepciones.LongitudInvalidaException;
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
import Utilidades.LoggerApp;

/**
 *CLASE CON LAS FUNCIONES DE MANIPULACION EN LA BD
 * @author DAW120
 */
public class AdministradorDAO implements RepositorioMaterial<MaterialInventario>, RepositorioPc<Pc> {

    private Connection getConnection() {
        return AccesoBaseDatos.getInstance().getConn();
    }

    //====================================================
    // MATERIALES
    //====================================================
    /**
     *METODO PARA CREAR UNA LISTA CON LOS MATERIALES DE LA BD
     * 
     * 
     * @return Una lista de Objetos 'MaterialInventario' llamada (materiales).
     * 
     * @see 
     * <p>
     * <br>Crea una lista de Objetos 'MaterialInventario' llamada (materiales)
     * <br>Crea una sentencia SQL para crear una consulta con todos los campos de la tabla materialesTaller
     * <br>
     * <br>Se conecta a la BD (getConnection) y se prepara para ejecutar la sentencia
     * <br>*Crea un ResultSet(rs) que almacena el resultado de ejecutar la sentencia
     * <br>*Empezando desde la primera fila de (rs) añade a (materiales) un Objeto creado con el METODO crearMaterialBD(rs)
     * <br>*Se repite mientras (rs) tenga una fila despues
     * <br>En caso de fallo lanza la excepcion correspondiente
     * 
     * <br>Devuelve (materiales)
     * </p>
     */
    @Override
    public List<MaterialInventario> listarMaterial() {
        List<MaterialInventario> materiales = new ArrayList<>();

        String sql = "SELECT id_matTa,nombre,descripcion,estado,cantidad,id_ubi,id_balda,fecha_alta,observaciones FROM materialesTaller";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql); 
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                materiales.add(crearMaterialBD(rs));
            }

        } catch (SQLException ex) {
            LoggerApp.log("Error al listar materiales" + ex.getMessage());
        } catch (IdInvalidoException ex) {
            LoggerApp.log("Error con el id " + ex.getMessage());
        } catch (NombreInvalidoException ex) {
            LoggerApp.log("Error con el nombre " + ex.getMessage());
        } catch (CantidadInvalidaException ex) {
            LoggerApp.log("Error con la cantidad " + ex.getMessage());
        } catch (DescripcionInvalidaException ex) {
            LoggerApp.log("Error con la descripcion " + ex.getMessage());
        } catch (EstadoInvalidoException ex) {
            LoggerApp.log("Error con el estado " + ex.getMessage());
        } catch (FechaInvalidaException ex) {
            LoggerApp.log("Error con la fecha " + ex.getMessage());
        }

        return materiales;
    }

    
    public List<Perifericos> listarPerifericos() throws CategoriaInvalidaException {
        List<Perifericos> perifericos = new ArrayList<>();

        String sql = "SELECT m.id_matTa,m.nombre,m.descripcion,m.estado,m.cantidad,m.id_ubi,m.id_balda,m.fecha_alta,m.observaciones,p.conexion FROM materialesTaller m JOIN perifericos p ON p.id_matTa = m.id_matTa";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                perifericos.add(crearPerifericosBD(rs));
            }

        } catch (SQLException ex) {
            System.out.println("Error al listar materiales" + ex.getMessage());
        } catch (IdInvalidoException ex) {
            System.out.println("Error con el id " + ex.getMessage());
        } catch (NombreInvalidoException ex) {
            System.out.println("Error con el nombre " + ex.getMessage());
        } catch (CantidadInvalidaException ex) {
            System.out.println("Error con la cantidad " + ex.getMessage());
        } catch (DescripcionInvalidaException ex) {
            System.out.println("Error con la descripcion " + ex.getMessage());
        } catch (EstadoInvalidoException ex) {
            System.out.println("Error con el estado " + ex.getMessage());
        } catch (FechaInvalidaException ex) {
            System.out.println("Error con la fecha " + ex.getMessage());
        }

        return perifericos;
    }
                     
    public List<Componentes> listarComponentes() throws CategoriaInvalidaException {
        List<Componentes> componentes = new ArrayList<>();

        String sql = "SELECT m.id_matTa,m.nombre,m.descripcion,m.estado,m.cantidad,m.id_ubi,m.id_balda,m.fecha_alta,m.observaciones,p.id_pc FROM materialesTaller m JOIN componentes p ON p.id_matTa = m.id_matTa";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                componentes.add(crearComponenteBD(rs));
            }

        } catch (SQLException ex) {
            System.out.println("Error al listar materiales" + ex.getMessage());
        } catch (IdInvalidoException ex) {
            System.out.println("Error con el id " + ex.getMessage());
        } catch (NombreInvalidoException ex) {
            System.out.println("Error con el nombre " + ex.getMessage());
        } catch (CantidadInvalidaException ex) {
            System.out.println("Error con la cantidad " + ex.getMessage());
        } catch (DescripcionInvalidaException ex) {
            System.out.println("Error con la descripcion " + ex.getMessage());
        } catch (EstadoInvalidoException ex) {
            System.out.println("Error con el estado " + ex.getMessage());
        } catch (FechaInvalidaException ex) {
            System.out.println("Error con la fecha " + ex.getMessage());
        }

        return componentes;
    }                
                         
                         
    /**
     *METODO QUE BUSCA UN MATERIAL POR SU ID EN LA BD
     * @param id
     * El id que busca
     * @return
     * Un Objeto 'MaterialInventario'
     * @see 
     * <br>Instancia un Objeto 'MaterialInventario'(m) como NULL
     * <br>Crea una sentencia SQL incompleta para crear una consulta que muestre todos los campos de la tabla materialesTaller
     * <br>*Donde el campo id_matTa sea igual a (?)
     * 
     * <br>Se conecta a la BD y se prepara para ejecutar la sentencia
     * <br>*Sustituye el primer (?) por el id
     * <br>*Crea un ResultSet(rs) que almacena el resultado de ejecutar la sentencia
     * <br>Si (rs) tiene una fila le da a (m) el RETURN del METODO crearMaterialBD(rs) 
     * <br>En caso de fallo lanza la excepcion correspondiente
     * 
     * <br>Devuelve (m)
     */
    @Override
    public MaterialInventario porIdMaterial(int id) {

        MaterialInventario m = null;
        String sql = "SELECT id_matTa,nombre,descripcion,estado,cantidad,id_ubi,id_balda,fecha_alta,observaciones "
                     + " FROM materialesTaller WHERE id_matTa = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                m = crearMaterialBD(rs);
            }

        } catch (SQLException ex) {
            LoggerApp.log("Error al listar materiales" + ex.getMessage());
        } catch (IdInvalidoException ex) {
            LoggerApp.log("Error con el id " + ex.getMessage());
        } catch (NombreInvalidoException ex) {
            LoggerApp.log("Error con el nombre " + ex.getMessage());
        } catch (CantidadInvalidaException ex) {
            LoggerApp.log("Error con la cantidad " + ex.getMessage());
        } catch (DescripcionInvalidaException ex) {
            LoggerApp.log("Error con la descripcion " + ex.getMessage());
        } catch (EstadoInvalidoException ex) {
            LoggerApp.log("Error con el estado " + ex.getMessage());
        } catch (FechaInvalidaException ex) {
            LoggerApp.log("Error con la fecha " + ex.getMessage());
        }

        return m;
    }
    
    /**
     *METODO QUE BUSCA LOS DATOS DE UN CABLE POR SU ID EN LA BD
     * @param id
     * El id que busca
     * @return
     * Un Objeto 'Cableado'
     * @throws CategoriaInvalidaException
     * @throws LongitudInvalidaException
     * @throws ConectorInvalidoException
     * @see 
     * <br>Instancia un Objeto 'Cableado'(m) como NULL
     * <br>Crea una sentencia SQL incompleta para crear una consulta que muestre todos los campos de la tabla materialesTaller
     * <br>*Y todos los campos de la tabla cableado menos el id_matTa, mediante una Subconsulta
     * <br>*Donde el campo de id_matTa de las dos tablas coincida
     * <br>*Donde el campo id_matTa de cableado sea igual a (?)
     * 
     * <br>Se conecta a la BD y se prepara para ejecutar la sentencia
     * <br>*Sustituye el primer (?) por el id
     * <br>*Crea un ResultSet(rs) que almacena el resultado de ejecutar la sentencia
     * <br>Si (rs) tiene una fila le da a (m) el RETURN del METODO crearCableadoBD(rs) 
     * <br>En caso de fallo lanza la excepcion correspondiente
     * 
     * <br>Devuelve (m)
     */
    public Cableado porIdMaterialCable(int id) throws CategoriaInvalidaException, LongitudInvalidaException, ConectorInvalidoException {

        Cableado m = null;
        String sql = "SELECT m.id_matTa,nombre,descripcion,estado,cantidad,id_ubi,id_balda,fecha_alta,observaciones,longitud,conector1,conector2 "
                     + "FROM materialestaller m JOIN cableado c ON m.id_matTa = c.id_matTa "
                     + "WHERE c.id_matTa = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                m = crearCableadoBD(rs);
            }

        } catch (SQLException ex) {
            LoggerApp.log("Error al listar materiales" + ex.getMessage());
        } catch (IdInvalidoException ex) {
            LoggerApp.log("Error con el id " + ex.getMessage());
        } catch (NombreInvalidoException ex) {
            LoggerApp.log("Error con el nombre " + ex.getMessage());
        } catch (CantidadInvalidaException ex) {
            LoggerApp.log("Error con la cantidad " + ex.getMessage());
        } catch (DescripcionInvalidaException ex) {
            LoggerApp.log("Error con la descripcion " + ex.getMessage());
        } catch (EstadoInvalidoException ex) {
            LoggerApp.log("Error con el estado " + ex.getMessage());
        } catch (FechaInvalidaException ex) {
            LoggerApp.log("Error con la fecha " + ex.getMessage());
        }

        return m;
    }
    
    /**
     *METODO QUE BUSCA LOS DATOS DE UN EQUIPO DE RED POR SU ID EN LA BD
     * @param id
     * El id que busca
     * @return
     * Un Objeto 'Equipos_en_red'
     * @throws CategoriaInvalidaException
     * @throws LongitudInvalidaException
     * @throws ConectorInvalidoException
     * @see 
     * <br>Instancia un Objeto 'Equipos_en_red'(m) como NULL
     * <br>Crea una sentencia SQL incompleta para crear una consulta que muestre todos los campos de la tabla materialesTaller
     * <br>*Y todos los campos de la tabla equipos_red menos el id_matTa, mediante una Subconsulta
     * <br>*Donde el campo de id_matTa de las dos tablas coincida
     * <br>*Donde el campo id_matTa de equipos_red sea igual a (?)
     * 
     * <br>Se conecta a la BD y se prepara para ejecutar la sentencia
     * <br>*Sustituye el primer (?) por el id
     *<br> *Crea un ResultSet(rs) que almacena el resultado de ejecutar la sentencia
     * <br>Si (rs) tiene una fila le da a (m) el RETURN del METODO crearEquipoRedBD(rs) 
     *<br> En caso de fallo lanza la excepcion correspondiente
     * 
     * <br>Devuelve (m)
     */
    public Equipos_en_red porIdEquiposRed(int id) throws CategoriaInvalidaException, LongitudInvalidaException, ConectorInvalidoException {

        Equipos_en_red m = null;
        String sql = "SELECT m.id_matTa,nombre,descripcion,estado,cantidad,id_ubi,id_balda,fecha_alta,observaciones,num_puertos FROM materialestaller m JOIN equipos_red c ON m.id_matTa = c.id_matTa WHERE c.id_matTa = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                m = crearEquipoRedBD(rs);
            }

        } catch (SQLException ex) {
            LoggerApp.log("Error al listar materiales" + ex.getMessage());
        } catch (IdInvalidoException ex) {
            LoggerApp.log("Error con el id " + ex.getMessage());
        } catch (NombreInvalidoException ex) {
            LoggerApp.log("Error con el nombre " + ex.getMessage());
        } catch (CantidadInvalidaException ex) {
            LoggerApp.log("Error con la cantidad " + ex.getMessage());
        } catch (DescripcionInvalidaException ex) {
            LoggerApp.log("Error con la descripcion " + ex.getMessage());
        } catch (EstadoInvalidoException ex) {
            LoggerApp.log("Error con el estado " + ex.getMessage());
        } catch (FechaInvalidaException ex) {
            LoggerApp.log("Error con la fecha " + ex.getMessage());
        }

        return m;
    }
    
    /**
     *METODO QUE BUSCA LOS DATOS DE UNA HERRAMIENTA POR SU ID EN LA BD
     * @param id
     * El id que busca
     * @return
     * Un Objeto 'Herramientas'
     * @throws CategoriaInvalidaException
     * @throws LongitudInvalidaException
     * @throws ConectorInvalidoException
     * @see 
     * <br>Instancia un Objeto 'Herramientas'(m) como NULL
     * <br>Crea una sentencia SQL incompleta para crear una consulta que muestre todos los campos de la tabla materialesTaller
     * <br>*Y todos los campos de la tabla herramientas menos el id_matTa, mediante una Subconsulta
     * <br>*Donde el campo de id_matTa de las dos tablas coincida
     * <br>*Donde el campo id_matTa de herramientas sea igual a (?)
     * 
     * <br>Se conecta a la BD y se prepara para ejecutar la sentencia
     * <br>*Sustituye el primer (?) por el id
     * <br>*Crea un ResultSet(rs) que almacena el resultado de ejecutar la sentencia
     * <br>Si (rs) tiene una fila le da a (m) el RETURN del METODO crearHerramientaBD(rs) 
     * <br>En caso de fallo lanza la excepcion correspondiente
     * 
     * <br>Devuelve (m)
     */
    public Herramientas porIdHerramientas(int id) throws CategoriaInvalidaException, LongitudInvalidaException, ConectorInvalidoException {

        Herramientas m = null;
        String sql = "SELECT m.id_matTa,nombre,descripcion,estado,cantidad,id_ubi,id_balda,fecha_alta,observaciones,tipo FROM materialestaller m JOIN herramientas c ON m.id_matTa = c.id_matTa WHERE c.id_matTa = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                m = crearHerramientaBD(rs);
            }

        } catch (SQLException ex) {
            LoggerApp.log("Error al listar materiales" + ex.getMessage());
        } catch (IdInvalidoException ex) {
            LoggerApp.log("Error con el id " + ex.getMessage());
        } catch (NombreInvalidoException ex) {
            LoggerApp.log("Error con el nombre " + ex.getMessage());
        } catch (CantidadInvalidaException ex) {
            LoggerApp.log("Error con la cantidad " + ex.getMessage());
        } catch (DescripcionInvalidaException ex) {
            LoggerApp.log("Error con la descripcion " + ex.getMessage());
        } catch (EstadoInvalidoException ex) {
            LoggerApp.log("Error con el estado " + ex.getMessage());
        } catch (FechaInvalidaException ex) {
            LoggerApp.log("Error con la fecha " + ex.getMessage());
        }

        return m;
    }
    
    /**
     *METODO QUE BUSCA LOS DATOS DE UN MATERIAL FUNGIBLE POR SU ID EN LA BD
     * @param id
     * El id que busca
     * @return
     * Un Objeto 'Material_Fungible'
     * @throws CategoriaInvalidaException
     * @throws LongitudInvalidaException
     * @throws ConectorInvalidoException
     * @see 
     * <br>Instancia un Objeto 'Material_Fungible'(m) como NULL
     *<br> Crea una sentencia SQL incompleta para crear una consulta que muestre todos los campos de la tabla materialesTaller
     * <br>*Y todos los campos de la tabla material_fungible menos el id_matTa, mediante una Subconsulta
     * <br>*Donde el campo de id_matTa de las dos tablas coincida
     * <br>*Donde el campo id_matTa de material_fungible sea igual a (?)
     * 
     * <br>Se conecta a la BD y se prepara para ejecutar la sentencia
     * <br>*Sustituye el primer (?) por el id
     * <br>*Crea un ResultSet(rs) que almacena el resultado de ejecutar la sentencia
     * <br>Si (rs) tiene una fila le da a (m) el RETURN del METODO crearMaterialFBD(rs) 
     * <br>En caso de fallo lanza la excepcion correspondiente
     * 
     * <br>Devuelve (m)
     */
    public Material_Fungible porIdMaterialF(int id) throws CategoriaInvalidaException, LongitudInvalidaException, ConectorInvalidoException {

        Material_Fungible m = null;
        String sql = "SELECT m.id_matTa,nombre,descripcion,m.estado,cantidad,id_ubi,id_balda,fecha_alta,observaciones,c.estado as estadof FROM materialestaller m JOIN material_fungible c ON m.id_matTa = c.id_matTa WHERE c.id_matTa = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                m = crearMaterialFBD(rs);
            }

        } catch (SQLException ex) {
            LoggerApp.log("Error al listar materiales" + ex.getMessage());
        } catch (IdInvalidoException ex) {
            LoggerApp.log("Error con el id " + ex.getMessage());
        } catch (NombreInvalidoException ex) {
            LoggerApp.log("Error con el nombre " + ex.getMessage());
        } catch (CantidadInvalidaException ex) {
            LoggerApp.log("Error con la cantidad " + ex.getMessage());
        } catch (DescripcionInvalidaException ex) {
            LoggerApp.log("Error con la descripcion " + ex.getMessage());
        } catch (EstadoInvalidoException ex) {
            LoggerApp.log("Error con el estado " + ex.getMessage());
        } catch (FechaInvalidaException ex) {
            LoggerApp.log("Error con la fecha " + ex.getMessage());
        }

        return m;
    }
    
    /**
     *METODO QUE BUSCA LOS DATOS DE UN COMPONENTE POR SU ID EN LA BD
     * @param id
     * El id que busca
     * @return
     * Un Objeto 'Componentes'
     * @throws CategoriaInvalidaException
     * @throws LongitudInvalidaException
     * @throws ConectorInvalidoException
     * @see 
     * <br>Instancia un Objeto 'Componentes'(m) como NULL
     * <br>Crea una sentencia SQL incompleta para crear una consulta que muestre todos los campos de la tabla materialesTaller
     * <br>*Y todos los campos de la tabla componentes menos el id_matTa, mediante una Subconsulta
     * <br>*Donde el campo de id_matTa de las dos tablas coincida
     * <br>*Donde el campo id_matTa de componentes sea igual a (?)
     * 
     * <br>Se conecta a la BD y se prepara para ejecutar la sentencia
     * <br>*Sustituye el primer (?) por el id
     * <br>*Crea un ResultSet(rs) que almacena el resultado de ejecutar la sentencia
     * <br>Si (rs) tiene una fila le da a (m) el RETURN del METODO crearComponenteBD(rs) 
     * <br>En caso de fallo lanza la excepcion correspondiente
     * 
     * <br>Devuelve (m)
     */
    public Componentes porIdComponentes(int id) throws CategoriaInvalidaException, LongitudInvalidaException, ConectorInvalidoException {

        Componentes m = null;
        String sql = "SELECT m.id_matTa,nombre,descripcion,estado,cantidad,id_ubi,id_balda,fecha_alta,observaciones,id_pc FROM materialestaller m JOIN componentes c ON m.id_matTa = c.id_matTa WHERE c.id_matTa = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                m = crearComponenteBD(rs);
            }

        } catch (SQLException ex) {
            LoggerApp.log("Error al listar materiales" + ex.getMessage());
        } catch (IdInvalidoException ex) {
            LoggerApp.log("Error con el id " + ex.getMessage());
        } catch (NombreInvalidoException ex) {
            LoggerApp.log("Error con el nombre " + ex.getMessage());
        } catch (CantidadInvalidaException ex) {
            LoggerApp.log("Error con la cantidad " + ex.getMessage());
        } catch (DescripcionInvalidaException ex) {
            LoggerApp.log("Error con la descripcion " + ex.getMessage());
        } catch (EstadoInvalidoException ex) {
            LoggerApp.log("Error con el estado " + ex.getMessage());
        } catch (FechaInvalidaException ex) {
            LoggerApp.log("Error con la fecha " + ex.getMessage());
        }

        return m;
    }
    
    /**
     *METODO QUE BUSCA LOS DATOS DE UN PERIFERICO POR SU ID EN LA BD
     * @param id
     * El id que busca
     * @return
     * Un Objeto 'Perifericos'
     * @throws CategoriaInvalidaException
     * @throws LongitudInvalidaException
     * @throws ConectorInvalidoException
     * @see 
     * <br>Instancia un Objeto 'Perifericos'(m) como NULL
     * <br>Crea una sentencia SQL incompleta para crear una consulta que muestre todos los campos de la tabla materialesTaller
     * <br>*Y todos los campos de la tabla perifericos menos el id_matTa, mediante una Subconsulta
     * <br>*Donde el campo de id_matTa de las dos tablas coincida
     * <br>*Donde el campo id_matTa de perifericos sea igual a (?)
     * 
     * <br>Se conecta a la BD y se prepara para ejecutar la sentencia
     * <br>*Sustituye el primer (?) por el id
     * <br>*Crea un ResultSet(rs) que almacena el resultado de ejecutar la sentencia
     * <br>Si (rs) tiene una fila le da a (m) el RETURN del METODO crearPerifericoBD(rs) 
     * <br>En caso de fallo lanza la excepcion correspondiente
     * 
     * <br>Devuelve (m)
     */
    public Perifericos porIdPerifericos(int id) throws CategoriaInvalidaException, LongitudInvalidaException, ConectorInvalidoException {

        Perifericos m = null;
        String sql = "SELECT m.id_matTa,nombre,descripcion,estado,cantidad,id_ubi,id_balda,fecha_alta,observaciones,conexion FROM materialestaller m JOIN perifericos c ON m.id_matTa = c.id_matTa WHERE c.id_matTa = ?";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                m = crearPerifericosBD(rs);
            }

        } catch (SQLException ex) {
            LoggerApp.log("Error al listar materiales" + ex.getMessage());
        } catch (IdInvalidoException ex) {
            LoggerApp.log("Error con el id " + ex.getMessage());
        } catch (NombreInvalidoException ex) {
            LoggerApp.log("Error con el nombre " + ex.getMessage());
        } catch (CantidadInvalidaException ex) {
            LoggerApp.log("Error con la cantidad " + ex.getMessage());
        } catch (DescripcionInvalidaException ex) {
            LoggerApp.log("Error con la descripcion " + ex.getMessage());
        } catch (EstadoInvalidoException ex) {
            LoggerApp.log("Error con el estado " + ex.getMessage());
        } catch (FechaInvalidaException ex) {
            LoggerApp.log("Error con la fecha " + ex.getMessage());
        }

        return m;
    }
    
    
    

    /**
     * Metodo que inserta un objeto en la tabla materialesTaller y en la tabla
     * hija correspondiente
     *
     * 
     *
     * @param t
     * 
     * Tiene de atributo un Objeto de la clase MaterialInventario 
     * @see 
     * <br>Emplea una
     * sentencia Mysql para insertar sin valores <br>Se conecta a la base y prepara
     * la sentencia <br>*Junto a la sentencia se escribe un statement para que
     * genere la clave AI <br>Se le colocan los valores a insertar desde el objeto
     * <br>*Se crea un result set que devuelve la clave AI <br>*Al crearse se crea un
     * int que guarde la clave <br>Mediante un Switch se comprueba a que clase hija
     * pertenece el objeto <br>Y en funcion de la clase se ejecuta otra insercion a
     * traves de otro metodo
     */
    @Override

    // SUGERENCIA DE CAMBIO DE METODO
    // Para invocar mensajes correctos de guardado o fallo: 
    // ==========================================
    //    public boolean guardarMaterial(MaterialInventario t)
    //Y en el catch del método:
    //java} catch (SQLException ex) {
    //    LoggerApp.log("ERROR: " + ex.getMessage());
    //    return false;   // ← añadir esto
    //}
    
    //// Al final del método (si todo fue bien):
    //return true;
    // =========================================
    //  Luego en InventarioApp.java, en el ActionListener del botón Guardar:
    //java// ANTES
    //dao.guardarMaterial(material);
    //lblMsg.setForeground(COLOR_OK);
    //lblMsg.setText("✅ Material guardado correctamente.");
    //
    //// DESPUÉS
    //boolean ok = dao.guardarMaterial(material);
    //if (ok) {
    //    lblMsg.setForeground(COLOR_OK);
    //    lblMsg.setText("✅ Material guardado correctamente.");
    //} else {
    //    lblMsg.setForeground(COLOR_ERROR);
    //    lblMsg.setText("❌ Error al guardar. Revisa los logs.");
    //}
    //==============================================
    public void guardarMaterial(MaterialInventario t) {
        // comando de insercion en Mysql
        String sql = "INSERT INTO materialesTaller (nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones) VALUES (?,?,?,?,?,?,?,?)";

        try (PreparedStatement ps = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
            if (rs.next()) {
                int idAI = rs.getInt(1);

                // Error al mostrar mensaje de guardado correcto, hay que aplicar un booleano
                //comprobación de guardado en las tablas hijas
                //            boolean exito = true;
                //    try {
                //        switch (t) {
                //            case Perifericos pe  -> guardarPeriferico(pe, idAI);
                //            case Cableado ca     -> guardarCableado(ca, idAI);
                //            case Componentes co  -> guardarComponente(co, idAI);
                //            case Herramientas he -> guardarHerramienta(he, idAI);
                //            case Material_Fungible mf -> guardarMaterialFungible(mf, idAI);
                //            case Equipos_en_red er    -> guardarEquipoRed(er, idAI);
                //            default -> { }
                //        }
                //    } catch (Exception ex) {
                //        exito = false;
                //        LoggerApp.log("❌ Error en tabla hija: " + ex.getMessage());
                //    }
                //    if (exito) LoggerApp.log("✅ Se ha insertado correctamente.");
                //}
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
     * Metodo para eliminar un objeto de la tabla materialesTaller por la id
     *
     * 
     *
     * @param id
     * El id que busca
     * @return int 
     * Devuelve -1 / 0 / 1 dependiendo de como resulto el DELETE
     * @see 
     * <br>Tiene el atributo id Emplea una sentencia Mysql para eliminar un objeto
     * por el id <br>Se conecta a la BD y prepara la sentencia <br>Sustituye el simbolo
     * designado por defecto por el id <br>Ejecuta la sentencia
     *
     *<br> Se eliminan el resto de entradas del objeto con ese id por la
     * configuracion de la BD
     * 
     */
    @Override
   public int eliminarMaterial(int id) {
    String sql = "DELETE FROM materialesTaller WHERE id_matTa = ?";
    try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
        ps.setInt(1, id);
        int filas = ps.executeUpdate();
        if (filas == 0) {
            LoggerApp.log("No se ha eliminado ningun registro en materialesTaller");
        } else if (filas > 1) {
            LoggerApp.log("Se han eliminado inesperadamente mas de un registro");
        }
        return filas; // ← devuelve 0 si no existía, 1 si se eliminó
    } catch (SQLException ex) {
        LoggerApp.log("❌ Error en la Base de Datos: " + ex.getMessage());
        return -1; // ← indica error de BD
    }
}

    /**
     *METODO QUE MODIFICA LOS VALORES DE LOS CAMPOS DE UN MATERIAL
     * @param t
     * Un Objeto 'MaterialInventario' de donde saca los datos
     * @see 
     * <br>Crea una sentencia SQL incompleta que actualiza los datos de la tabla materialesTaller
     * <br>*Los datos se convertiran en (?) x8
     * <br>*Donde el id_matTa sea igual a (?)
     * 
     * <br>Se conecta a la BD y prepara la sentencia
     * <br>*Sustituye los primeros 8 (?) por el atributo correspondiente de t
     * <br>*Sustituye el ultimo (?) por el id_matTa de t
     */
    @Override
    public void actualizarPorID(MaterialInventario t) {
        String sql = "UPDATE  materialesTaller SET "
                + "nombre = ?,"
                + "descripcion = ?,"
                + "estado = ?,"
                + "cantidad = ?,"
                + "id_ubi = ?,"
                + "id_balda = ?,"
                + "fecha_alta = ?,"
                + "observaciones = ?"
                + " WHERE id_matTa = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setString(1, t.getNombre());
            ps.setString(2, t.getDescripcion());
            ps.setString(3, t.getEstado().toString().toLowerCase());
            ps.setInt(4, t.getCantidad());
            ps.setString(5, t.getId_ubi());
            ps.setObject(6, t.getId_balda());
            ps.setString(7, t.getFecha_alta().toString());
            ps.setString(8, t.getObservaciones());
            ps.setInt(9, t.getId_matTa());

            int filas = ps.executeUpdate();

            if (filas == 0) {
                LoggerApp.log("No se ha actualizado ningun registro en materialesTaller");
            } else if (filas > 1) {
                LoggerApp.log("Se han actualizado inesperadamente mas de un registro");
            }
        } catch (SQLException ex) {
            LoggerApp.log(
                    "❌ Error en la Base de Datos: "
                    + ex.getMessage());
        }

    }

    // =========================================================================
    private MaterialInventario crearMaterialBD(ResultSet rs) throws SQLException, IdInvalidoException, NombreInvalidoException,CantidadInvalidaException, DescripcionInvalidaException,EstadoInvalidoException, FechaInvalidaException {
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
    
     private Perifericos crearPerifericosBD(ResultSet rs) throws SQLException, IdInvalidoException, NombreInvalidoException, CantidadInvalidaException, DescripcionInvalidaException, CategoriaInvalidaException, EstadoInvalidoException, FechaInvalidaException {
//        Estados estado = Estados.valueOf(rs.getString("estado"));

        return new Perifericos(rs.getString("id_matTa"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getString("estado").toUpperCase(),
                rs.getString("cantidad"),
                rs.getString("id_ubi"),
                rs.getString("id_balda"),
                rs.getString("fecha_alta"),
                rs.getString("observaciones"),
                rs.getString("conexion").toUpperCase(),
                null
           
        );
    }
    
     private Componentes crearComponenteBD(ResultSet rs) throws SQLException, IdInvalidoException, NombreInvalidoException, CantidadInvalidaException, DescripcionInvalidaException, CategoriaInvalidaException, EstadoInvalidoException, FechaInvalidaException {
//        Estados estado = Estados.valueOf(rs.getString("estado"));

        return new Componentes(rs.getString("id_matTa"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getString("estado").toUpperCase(),
                rs.getString("cantidad"),
                rs.getString("id_ubi"),
                rs.getString("id_balda"),
                rs.getString("fecha_alta"),
                rs.getString("observaciones"),
                rs.getString("id_pc")
        );
    }
    
     private Cableado crearCableadoBD(ResultSet rs) throws SQLException, IdInvalidoException, NombreInvalidoException, CantidadInvalidaException, DescripcionInvalidaException, CategoriaInvalidaException, EstadoInvalidoException, FechaInvalidaException, LongitudInvalidaException, ConectorInvalidoException {
//        Estados estado = Estados.valueOf(rs.getString("estado"));

        return new Cableado(rs.getString("id_matTa"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getString("estado").toUpperCase(),
                rs.getString("cantidad"),
                rs.getString("id_ubi"),
                rs.getString("id_balda"),
                rs.getString("fecha_alta"),
                rs.getString("observaciones"),
                rs.getString("longitud"),
                rs.getString("conector1"),
                rs.getString("conector2")
        );
    }
    
     private Equipos_en_red crearEquipoRedBD(ResultSet rs) throws SQLException, IdInvalidoException, NombreInvalidoException, CantidadInvalidaException, DescripcionInvalidaException, CategoriaInvalidaException, EstadoInvalidoException, FechaInvalidaException {
//        Estados estado = Estados.valueOf(rs.getString("estado"));

        return new Equipos_en_red(rs.getString("id_matTa"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getString("estado").toUpperCase(),
                rs.getString("cantidad"),
                rs.getString("id_ubi"),
                rs.getString("id_balda"),
                rs.getString("fecha_alta"),
                rs.getString("observaciones"),
                rs.getString("num_puertos")
        );
    }
    
     private Herramientas crearHerramientaBD(ResultSet rs) throws SQLException, IdInvalidoException, NombreInvalidoException, CantidadInvalidaException, DescripcionInvalidaException, CategoriaInvalidaException, EstadoInvalidoException, FechaInvalidaException {
//        Estados estado = Estados.valueOf(rs.getString("estado"));

        return new Herramientas(rs.getString("id_matTa"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getString("estado").toUpperCase(),
                rs.getString("cantidad"),
                rs.getString("id_ubi"),
                rs.getString("id_balda"),
                rs.getString("fecha_alta"),
                rs.getString("observaciones"),
                rs.getString("tipo").toUpperCase()
        );
    }
     
     private Material_Fungible crearMaterialFBD(ResultSet rs) throws SQLException, IdInvalidoException, NombreInvalidoException, CantidadInvalidaException, DescripcionInvalidaException, CategoriaInvalidaException, EstadoInvalidoException, FechaInvalidaException {
//        Estados estado = Estados.valueOf(rs.getString("estado"));

        return new Material_Fungible(rs.getString("id_matTa"),
                rs.getString("nombre"),
                rs.getString("descripcion"),
                rs.getString("estado").toUpperCase(),
                rs.getString("cantidad"),
                rs.getString("id_ubi"),
                rs.getString("id_balda"),
                rs.getString("fecha_alta"),
                rs.getString("observaciones"),
                rs.getString("estadof").toUpperCase()
        );
    }
     
    
//=====================================================================
    //METODOS DE GUARDADO ENCAPSULADOS

    /**
     * Metodo para guardar un Objeto Periferico en la tabla perifericos de la BD
     *
     * @param t Es el Objeto Periferico
     * @param id Es un Int que es el id que tendra asignado en la tabla
     * @throws SQLException
     * @see 
     * <br>Emplea una sentencia de Mysql para insertar en la tabla perifericos<br> Se
     * conecta a la BD y prepara la sentencia <br>Los valores a insertar no estan
     * introducidos(?) <br>Se inserta como un numero entero(int) en el primer (?) el
     * id <br>En el segundo (?) se inserta el atributo Conexion que es un Enum <br>*como
     * una cadena de texto(String) en minusculas(lowerCase) <br>Se ejecuta la
     * sentencia
     *
     * <br>Se crea una segunda sentencia SQL para insertar en la tabla
     * perifericos_pcs (Una tabla intermedia) <br>Se conecta a la BD y prepara la
     * segunda sentencia <br>Los valores a insertar no estan introducidos(?) <br>El
     * primer (?) es el id <br>El segundo (?) es el id_pc de t <br>Se ejecuta la
     * sentencia
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
            
            if(t.getId_pc() != null){
//              if(!t.getPcs().isEmpty()){
//                for(int pc : t.getPcs()){
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
                
            }
//           
        } catch (SQLException ex) {
            LoggerApp.log("ERROR: " + ex.getMessage());
        }

    }

    /**
     * METODO PARA INSERTAR UN OBJETO COMPONENTE EN LA TABLA componentes DE LA
     * BD
     *
     * @param t Es el Objeto Componente que se va a insertar
     * @param id Es el id con el que va a ser insertado
     *@see 
     * <br>Se crea una sentencia SQL para insertar como cadena de texto(String)
     * <br>*INSERT INTO componentes (id_matTa, id_pc) VALUES (?,?) **(?) es un
     * simbolo que puede ser cambiado por datos <br>Se conecta a la BD y prepara la
     * sentencia para manipularla <br>Al primer (?) se le sustituye por id <br>Al
     * segundo (?) se le sustituye por el atributo id_pc de t <br>*Ambos son valores
     * enteros (int)
     *
     * <br>Se crea un entero(int) que recoge el numero de filas afectadas al
     * ejecutarse la sentencia
     *
     */
    public void guardarComponente(Componentes t, int id) {
        String sql = "INSERT INTO componentes (id_matTa, id_pc) VALUES (?,?)";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, t.getId_pc());

            int filas = ps.executeUpdate();
            if (filas != 1) {
                LoggerApp.log("No se ha insertado correctamente en componentes");
            }
        } catch (SQLException ex) {
            LoggerApp.log("ERROR: " + ex.getMessage());
        }
    }

    /**
     * METODO PARA INSERTAR UN OBJETO EQUIPO_DE_RED EN LA TABLA equipos_red DE
     * LA BD
     *
     * @param t Es el Objeto Equipos_en_red
     * @param id Es el id con el que va a ser insertado
     *@see 
     * <br>Se crea una sentencia SQL para insertar como cadena de texto(String)
     * <br>*INSERT INTO equipos_red (id_matTa, num_puertos) VALUES (?,?) **(?) es un
     * simbolo que puede ser cambiado por datos Se conecta a la BD y prepara la
     * sentencia para manipularla <br>Al primer (?) se le sustituye por id <br>Al
     * segundo (?) se le sustituye por el atributo numPuertos de t <br>*Ambos son
     * valores enteros (int)
     *
     * <br>Se crea un entero(int) que recoge el numero de filas afectadas al
     * ejecutarse la sentencia
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
     *METODO ESTATICO PARA INSERTAR UN CABLE EN LA BD
     * @param t
     * Es el Objeto Cableado del que sacar los datos
     * @param id
     * Es el id con el que se va a insertar
     * @see 
     * <br>Se crea una sentencia SQL incompleta para insertar en la tabla cableado los datos (?)x4
     * <br>Se conecta a la BD y se prepara para ejecutar la sentencia
     * <br>Sustituye el primer (?) por el id y el resto por el atributo de t correspondiente
     * <br>Ejecuta la sentencia
     * 
     */
    public void guardarCableado(Cableado t, int id) {
        String sql = "INSERT INTO cableado (id_matTa, longitud, conector1, conector2) VALUES (?,?,?,?)";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setDouble(2, t.getLongitud());
            ps.setString(3, t.getConector1());
            ps.setString(4, t.getConector2());

            int filas = ps.executeUpdate();
            if (filas != 1) {
                LoggerApp.log("No se ha insertado correctamente en cableado");
            }
        } catch (SQLException ex) {
            LoggerApp.log("ERROR: " + ex.getMessage());
        }
    }

    /**
     *METODO ESTATICO PARA INSERTAR UNA HERRAMIENTA EN LA BD
     * @param t
     * Es el Objeto Herramientas del que sacar los datos
     * @param id
     * Es el id con el que se va a insertar
     * @see 
     * <br>Se crea una sentencia SQL incompleta para insertar en la tabla herramientas los datos (?)x4
     * <br>Se conecta a la BD y se prepara para ejecutar la sentencia
     * <br>Sustituye el primer (?) por el id y el resto por el atributo de t correspondiente
     * <br>Ejecuta la sentencia
     * 
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
     *METODO ESTATICO PARA INSERTAR UN MATERIAL FUNGIBLE EN LA BD
     * @param t
     * Es el Objeto Material_Fungible del que sacar los datos
     * @param id
     * Es el id con el que se va a insertar
     * @see 
     * <br>Se crea una sentencia SQL incompleta para insertar en la tabla material_fungible los datos (?)x4
     * <br>Se conecta a la BD y se prepara para ejecutar la sentencia
     * <br>Sustituye el primer (?) por el id y el resto por el atributo de t correspondiente
     * <br>Ejecuta la sentencia
     * 
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

    //====================================================
    // PCS
    //====================================================

    /**
     *METODO QUE CREA UNA LISTA DE PC CON LOS DATOS DE LA BD
     * @return
     * Una lista de Objetos 'Pc'
     * @see 
     * <br>Crea una lista de Objetos 'Pc' llamada (lista)
     * <br>Crea una sentencia SQL para crear una consulta con todos los campos de la tabla pcs
     * 
     * <br>Se conecta a la BD (getConnection) y se prepara para ejecutar la sentencia
     * <br>*Crea un ResultSet(rs) que almacena el resultado de ejecutar la sentencia
     * <br>*Empezando desde la primera fila de (rs) añade a (materiales) un Objeto creado con el METODO crearMaterialBD(rs)
     * <br>*Se repite mientras (rs) tenga una fila despues
     * <br>En caso de fallo lanza la excepcion correspondiente
     * 
     *<br> Devuelve (lista)
     */
    @Override
    public List<Pc> listarPc() {

        List<Pc> lista = new ArrayList<>();

        String sql = 
            "SELECT id_pc,nombre,descripcion,estado,categoria,id_estacion,fecha_alta,observaciones "
                + "FROM pcs";

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

    /**
     *METODO QUE BUSCA UN PC POR SU ID EN LA BD
     * @param id
     * El id que busca
     * @return
     * Un Objeto 'Pc'
     * @see 
     * 
     * <br>Crea una sentencia SQL incompleta para crear una consulta que muestre todos los campos de la tabla pcs
     * <br>*Donde el campo id_pc sea igual a (?)
     * 
     * <br>Se conecta a la BD y se prepara para ejecutar la sentencia
     * <br>*Sustituye el primer (?) por el id
     * <br>*Crea un ResultSet(rs) que almacena el resultado de ejecutar la sentencia
     * <br>Si (rs) tiene una fila crea un Objeto 'Pc'(pc) con id_pc como id, y el resto de atributos obtenidos del (rs) 
     * <br>Devuelve (pc)
     * <br>En caso de fallo lanza la excepcion correspondiente
     * 
     * <br>Y devuelve NULL
     */
    @Override
    public Pc porIdPc(int id) {

        String sql = "SELECT id_pc, nombre, descripcion, estado, categoria, id_estacion, fecha_alta, observaciones FROM pcs WHERE id_pc = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

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
                return pc;
            }

        } catch (SQLException ex) {
            LoggerApp.log("Error al listar Pcs" + ex.getMessage());
        } catch (IdInvalidoException ex) {
            LoggerApp.log("Error con el id " + ex.getMessage());
        } catch (NombreInvalidoException ex) {
            LoggerApp.log("Error con el nombre " + ex.getMessage());
        } catch (DescripcionInvalidaException ex) {
            LoggerApp.log("Error con la descripcion " + ex.getMessage());
        } catch (EstadoInvalidoException ex) {
            LoggerApp.log("Error con el estado " + ex.getMessage());
        } catch (FechaInvalidaException ex) {
            LoggerApp.log("Error con la fecha " + ex.getMessage());
        } catch (CategoriaInvalidaException ex) {
            LoggerApp.log("Error con la categoria " + ex.getMessage());
        }
        return null;

    }

     /**
     *METODO QUE MODIFICA LOS VALORES DE LOS CAMPOS DE UN PC
     * @param t
     * Un Objeto 'Pc' de donde saca los datos
     * @see 
     * <br>Crea una sentencia SQL incompleta que actualiza los datos de la tabla pcs
     * <br>*Los datos se convertiran en (?) x6
     * <br>*Donde el id_matTa sea igual a (?)
     * 
     * <br>Se conecta a la BD y prepara la sentencia
     * <br>*Sustituye los primeros 6 (?) por el atributo correspondiente de t
     * <br>*Sustituye el ultimo (?) por el id_pc de t
     */
    public void actualizarPcPorID(Pc t) {
        String sql = "UPDATE  pcs SET "
                + "nombre = ?,"
                + "descripcion = ?,"
                + "estado = ?,"
                + "id_estacion = ?,"
                + "fecha_alta = ?,"
                + "observaciones = ?"
                + " WHERE id_pc = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            
            ps.setString(1, t.getNombre());
            ps.setString(2, t.getDescripcion());
            ps.setString(3, t.getEstado().toString().toLowerCase());
            ps.setString(4, t.getId_estacion());
            ps.setString(5, t.getFecha_alta().toString());
            ps.setString(6, t.getObservaciones());
            ps.setInt(7, t.getId_pc());

            int filas = ps.executeUpdate();

            if (filas == 0) {
                LoggerApp.log("No se ha actualizado ningun registro en Pcs");
            } else if (filas > 1) {
                LoggerApp.log("Se han actualizado inesperadamente mas de un registro de Pcs");
            }
        } catch (SQLException ex) {
            LoggerApp.log(
                    "❌ Error en la Base de Datos: "
                    + ex.getMessage());
        }

    }

    /**
     *METODO PARA GUARDAR UN PC EN LA BD
     * @param pc
     * Un Objeto 'Pc' de donde se obtienen los datos
     * @see 
     * <br>Se crea una sentencia SQL incompleta para insertar los datos (?)x7 en la tabla pcs
     * <br>*En todos los campos de una fila menos id_pc 
     * <br>Se conecta a la BD y se prepara para ejecutar la sentencia
     * <br>Sustituye (?) por los atributos de pc
     * <br>Ejecuta la sentencia
     */
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

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {

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

    /**
     *METODO PARA ELIMINAR UN PC EN LA BD
     * @param id
     * El id del pc a eliminar
     * @see 
     * <br>Crea una sentencia SQL incompleta para eliminar de la tabla pcs donde el id_pc sea (?)
     * <br>Se conecta a la BD y prepara la sentencia
     * <br>Sustituye (?) por id
     * <br>Ejecuta la sentencia
     */
    @Override
    public void eliminarPc(int id) {
        String sql = "DELETE FROM pcs WHERE id_pc = ?";

        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {

            ps.setInt(1, id);

            int filas = ps.executeUpdate();

            if (filas == 0) {
                LoggerApp.log("No se ha eliminado ningun Pc del inventario");
            } else if (filas > 1) {
                LoggerApp.log("Se han eliminado inesperadamente mas de un Pc");
            }
        } catch (SQLException ex) {
            LoggerApp.log(
                    "❌ Error en la Base de Datos: "
                    + ex.getMessage());

        }

    }

    //====================================================
    // UBICACIONES 
    //====================================================

    /**
     *METODO PARA CREAR UNA LISTA DE UBICACIONES
     * @return
     * Una lista de String con el id_ubi
     * @see 
     * <br>Crea un ArrayList de String(lista)
     * <br>Crea una sentencia SQL que crea una consulta que muestra los id_ubi de la tabla ubicacion ordenados
     * <br>Se conecta a la BD, prepara la sentencia y la ejecuta guardandola en un ResultSet(rs)
     * <br>Mientras (rs) tenga filas, las va añadiendo a (lista)
     * 
     * <br>Devuelve (lista)
     */
    
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
    /**
     *METODO PARA CREAR UNA LISTA DE ESTACIONES
     * @return
     * Una lista de String con el id_ubi
     * @see 
     * <br>Crea un ArrayList de String(lista)
     * <br>Crea una sentencia SQL que crea una consulta que muestra los id_ubi de la tabla estacion ordenados
     * <br>Se conecta a la BD, prepara la sentencia y la ejecuta guardandola en un ResultSet(rs)
     * <br>Mientras (rs) tenga filas, las va añadiendo a (lista)
     * 
     * <br>Devuelve (lista)
     */
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
