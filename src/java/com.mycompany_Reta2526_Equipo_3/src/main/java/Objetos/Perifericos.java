/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

import Validador.Validador;
import Enum.Conexion;
import Excepciones.CantidadInvalidaException;
import Excepciones.CategoriaInvalidaException;
import Excepciones.DescripcionInvalidaException;
import Excepciones.EstadoInvalidoException;
import Excepciones.FechaInvalidaException;
import Excepciones.IdInvalidoException;
import Excepciones.NombreInvalidoException;
import java.util.ArrayList;
import java.util.List;


public class Perifericos extends MaterialInventario {
// Tal vez se deberia quitar la herencia paracrear un array de id_pc
    
    private Conexion conexion;
    private ArrayList<Integer> pcs;
    private Integer id_pc;
    
//    public Perifericos(String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones, String conexion, ArrayList<Integer> pcs) throws IdInvalidoException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException, CategoriaInvalidaException {
//        super(nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
//        setConexion(conexion);
//        setPcs(pcs);
//    }

    
//    public Perifericos(String id_matTa, String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones, String conexion,ArrayList<Integer> pcs) throws IdInvalidoException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException, CategoriaInvalidaException {
//        super(id_matTa, nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
//        setConexion(conexion);
//        setPcs(pcs);
//    }

    public Perifericos( String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones, String conexion) throws NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, IdInvalidoException, FechaInvalidaException, CategoriaInvalidaException {
        super(nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
        setConexion(conexion);
    }
/**
     *CONSTRUCTOR SIN ID PARA INSERCION
     * @param nombre
     * @param descripcion
     * @param estado
     * @param cantidad
     * @param id_ubi
     * @param id_balda
     * @param fecha_alta
     * @param observaciones
     * @param id_pc
     * @param conexion
     * @throws IdInvalidoException
     * @throws NombreInvalidoException
     * @throws DescripcionInvalidaException
     * @throws EstadoInvalidoException
     * @throws CantidadInvalidaException
     * @throws FechaInvalidaException
     * @throws CategoriaInvalidaException
     */
    public Perifericos( String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones, String conexion,String id_pc) throws NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, IdInvalidoException, FechaInvalidaException, CategoriaInvalidaException {
        super(nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
       setConexion(conexion);
        setId_pc(id_pc);
    }
/**
     *CONSTRUCTOR CON ID PARA LECTURA
     * @param id_matTa
     * @param nombre
     * @param descripcion
     * @param estado
     * @param cantidad
     * @param id_ubi
     * @param id_balda
     * @param fecha_alta
     * @param observaciones
     * @param id_pc
     * @param conexion
     * @throws IdInvalidoException
     * @throws NombreInvalidoException
     * @throws DescripcionInvalidaException
     * @throws EstadoInvalidoException
     * @throws CantidadInvalidaException
     * @throws FechaInvalidaException
     * @throws CategoriaInvalidaException
     */
    public Perifericos(String id_matTa, String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones, String conexion,String id_pc) throws IdInvalidoException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException, CategoriaInvalidaException {
        super(id_matTa, nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
         setConexion(conexion);
        setId_pc(id_pc);
    }

    
    
    
    
   
    public Conexion getConexion() {
        return conexion;
    }

    /**
     *METODO QUE VALIDA, PARSEA Y ASIGNA EL ATRIBUTO conexion
     * @param conexion
     * @throws CategoriaInvalidaException
     * @throws DescripcionInvalidaException
     */
    public void setConexion(String conexion) throws CategoriaInvalidaException, DescripcionInvalidaException {
        Validador.validaTipoConexion(conexion);
        this.conexion = Conexion.valueOf(conexion);
    }

//    public ArrayList<Integer> getPcs() {
//        return pcs;
//    }
//
//    public void setPcs(ArrayList<Integer> pcs) throws IdInvalidoException {
//        if(!pcs.isEmpty()){
//            for (Integer pc : pcs) {
//                Validador.validaPc(pc.toString());
//            }
//        }
//        this.pcs = pcs;
//    }

    public Integer getId_pc() {
        return id_pc;
    }

    /**
     *METODO QUE VALIDA, PARSEA Y ASIGNA EL ATRIBUTO id_pc
     * @param id_pc id a validar
     * 
     * @throws IdInvalidoException
     * 
     * @see 
     * <br>Utiliza el METODO validaPcPerifericos(id_pc) para comprobar el formato
     * <br>Si id_pc es NULL, asigna al atributo id_pc el valor null
     * <br>Si no, comprueba si esta en blanco, asignando null al atributo id_pc si lo esta
     * <br>En caso contrario, le asigna el id_pc parseado a Integer
     */
    public void setId_pc(String id_pc) throws IdInvalidoException {
        Validador.validaPcPerifericos(id_pc);
        if(id_pc == null){
            this.id_pc = null;
        }else{
            if(id_pc.isBlank()){
                this.id_pc = null;
            }else{
              this.id_pc =  Integer.parseInt(id_pc);   
            }
           
        }
        
        
    }
    
    
    
    

}
