/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

import Validador.Validador;
import Enum.Estados;
import Enum.Categorias;
import Excepciones.CantidadInvalidaException;
import Excepciones.CategoriaInvalidaException;
import Excepciones.DescripcionInvalidaException;
import Excepciones.EstadoInvalidoException;
import Excepciones.FechaInvalidaException;
import Excepciones.IdInvalidoException;
import Excepciones.NombreInvalidoException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author DAW120
 */
public class Pc {

    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private int id_pc;
    private String nombre;
    private String descripcion;
    private Estados estado;
    
    private Categorias categoria;
    private String id_estacion;
    private LocalDate fecha_alta;
    private String observaciones;

    /**
     *CONSTRUCTOR CON ID PARA LECTURA
     * @param id_pc
     * @param nombre
     * @param descripcion
     * @param estado
     * @param categoria
     * @param id_estacion
     * @param fecha_alta
     * @param observaciones
     * @throws IdInvalidoException
     * @throws NombreInvalidoException
     * @throws DescripcionInvalidaException
     * @throws EstadoInvalidoException
     * @throws CategoriaInvalidaException
     * @throws FechaInvalidaException
     */
    public Pc(String id_pc, String nombre, String descripcion, String estado, String categoria, String id_estacion, String fecha_alta, String observaciones) throws IdInvalidoException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CategoriaInvalidaException, FechaInvalidaException {
        setId_pc(id_pc);
        setNombre(nombre);
        setDescripcion(descripcion);
        setEstado(estado);
        setCategoria(categoria);
        setId_estacion(id_estacion);
        setFecha_alta(fecha_alta);
        setObservaciones(observaciones);
    }

    /**
     *CONSTRUCTOR SIN ID PARA INSERCION
     * @param nombre
     * @param descripcion
     * @param estado
     * @param categoria
     * @param id_estacion
     * @param fecha_alta
     * @param observaciones
     * @throws NombreInvalidoException
     * @throws DescripcionInvalidaException
     * @throws EstadoInvalidoException
     * @throws CategoriaInvalidaException
     * @throws IdInvalidoException
     * @throws FechaInvalidaException
     */
    public Pc(String nombre, String descripcion, String estado, String categoria, String id_estacion, String fecha_alta, String observaciones) throws NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CategoriaInvalidaException, IdInvalidoException, FechaInvalidaException {
       setNombre(nombre);
        setDescripcion(descripcion);
        setEstado(estado);
        setCategoria(categoria);
        setId_estacion(id_estacion);
        setFecha_alta(fecha_alta);
        setObservaciones(observaciones);
    }

    
    
    
    /**
     *
     * @return
     */
    public int getId_pc() {
        return id_pc;
    }

    /**
     *METODO QUE VALIDA, PARSEA Y ASIGNA EL ATRIBUTO id_pc
     * @param id_pc
     * @throws IdInvalidoException
     */
    public void setId_pc(String id_pc) throws IdInvalidoException {
        Validador.validaPc(id_pc);
        this.id_pc = Integer.parseInt(id_pc);
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *METODO QUE VALIDA Y ASIGNA EL ATRIBUTO nombre
     * @param nombre
     * @throws NombreInvalidoException
     */
    public void setNombre(String nombre) throws NombreInvalidoException {
        Validador.validaNombre(nombre);
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     *METODO QUE VALIDA Y ASIGNA EL ATRIBUTO descripcion
     * @param descripcion
     * @throws DescripcionInvalidaException
     */
    public void setDescripcion(String descripcion) throws DescripcionInvalidaException {
        Validador.validaDescripcion(descripcion);
        this.descripcion = descripcion;
    }

    /**
     *
     * @return
     */
    public Estados getEstado() {
        return estado;
    }

    /**
     *METODO QUE VALIDA, PARSEA Y ASIGNA EL ATRIBUTO estado
     * @param estado
     * @throws EstadoInvalidoException
     * @throws DescripcionInvalidaException
     */
    public void setEstado(String estado) throws EstadoInvalidoException, DescripcionInvalidaException {
        Validador.validaEstado(estado);
        this.estado = Estados.valueOf(estado);
    }

    /**
     *
     * @return
     */
    public Categorias getCategoria() {
        return categoria;
    }

    /**
     *METODO QUE VALIDA, PARSEA Y ASIGNA EL ATRIBUTO categoria
     * @param categoria
     * @throws CategoriaInvalidaException
     * @throws DescripcionInvalidaException
     */
    public void setCategoria(String categoria) throws CategoriaInvalidaException, DescripcionInvalidaException {
        Validador.validaCategoria(categoria);
        this.categoria = Categorias.valueOf(categoria);
    }

    /**
     *
     * @return
     */
    public String getId_estacion() {
        return id_estacion;
    }

    /**
     *METODO QUE VALIDA Y ASIGNA EL ATRIBUTO id_estacion
     * @param id_estacion
     * @throws IdInvalidoException
     */
    public void setId_estacion(String id_estacion) throws IdInvalidoException {
        Validador.validaEstacion(id_estacion);
        this.id_estacion = id_estacion;
    }

    /**
     *
     * @return
     */
    public LocalDate getFecha_alta() {
        return fecha_alta;
    }

    /**
     *METODO QUE VALIDA, PARSEA Y ASIGNA EL ATRIBUTO fecha_alta
     * @param fecha_alta
     * @throws FechaInvalidaException
     */
    public void setFecha_alta(String fecha_alta) throws FechaInvalidaException {
        Validador.validaFecha(fecha_alta);
        this.fecha_alta = LocalDate.parse(nombre, formato);
    }

    /**
     *
     * @return
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     *METODO QUE VALIDA Y ASIGNA EL ATRIBUTO observaciones
     * @param observaciones
     * @throws DescripcionInvalidaException
     */
    public void setObservaciones(String observaciones) throws DescripcionInvalidaException {
        Validador.validaDescripcion(observaciones);
        this.observaciones = observaciones;
    }

}
