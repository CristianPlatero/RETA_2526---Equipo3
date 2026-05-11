/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reta2526_equipo_3;

import com.mycompany.reta2526_equipo_3.Excepciones.CantidadInvalidaException;
import com.mycompany.reta2526_equipo_3.Excepciones.CategoriaInvalidaException;
import com.mycompany.reta2526_equipo_3.Excepciones.DescripcionInvalidaException;
import com.mycompany.reta2526_equipo_3.Excepciones.EstadoInvalidoException;
import com.mycompany.reta2526_equipo_3.Excepciones.FechaInvalidaException;
import com.mycompany.reta2526_equipo_3.Excepciones.NombreInvalidoException;
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
    private int cantidad;
    private Categorias categoria;
    private String id_estacion;
    private LocalDate fecha_alta;
    private String observaciones;

    public Pc(String id_pc, String nombre, String descripcion, String estado, String cantidad, String categoria, String id_estacion, String fecha_alta, String observaciones) {

    }

    public int getId_pc() {
        return id_pc;
    }

    public void setId_pc(int id_pc) {
        this.id_pc = id_pc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws NombreInvalidoException {
        Validador.validaNombre(nombre);
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) throws DescripcionInvalidaException {
        Validador.validaDescripcion(descripcion);
        this.descripcion = descripcion;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(String estado) throws EstadoInvalidoException, DescripcionInvalidaException {
        Validador.validaEstado(estado);
        this.estado = Estados.valueOf(estado);
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) throws CantidadInvalidaException {
        Validador.validaCantidad(cantidad);
        this.cantidad = Integer.parseInt(cantidad);
    }

    public Categorias getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) throws CategoriaInvalidaException, DescripcionInvalidaException {
        Validador.validaCategoria(categoria);
        this.categoria = Categorias.valueOf(categoria);
    }

    public String getId_estacion() {
        return id_estacion;
    }

    public void setId_estacion(String id_estacion) {
        this.id_estacion = id_estacion;
    }

    public LocalDate getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(String fecha_alta) throws FechaInvalidaException {
        Validador.validaFecha(fecha_alta);
        this.fecha_alta = LocalDate.parse(nombre, formato);
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) throws DescripcionInvalidaException {
        Validador.validaDescripcion(observaciones);
        this.observaciones = observaciones;
    }

}
