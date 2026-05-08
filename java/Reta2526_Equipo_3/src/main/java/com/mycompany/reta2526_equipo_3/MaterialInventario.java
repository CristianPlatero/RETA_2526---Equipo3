/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reta2526_equipo_3;

import com.mycompany.reta2526_equipo_3.Excepciones.CantidadInvalidaException;
import com.mycompany.reta2526_equipo_3.Excepciones.DescripcionInvalidaException;
import com.mycompany.reta2526_equipo_3.Excepciones.EstadoInvalidoException;
import com.mycompany.reta2526_equipo_3.Excepciones.FechaInvalidaException;
import com.mycompany.reta2526_equipo_3.Excepciones.IdInvalidoException;
import com.mycompany.reta2526_equipo_3.Excepciones.NombreInvalidoException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author DAW120
 */
public class MaterialInventario {
    
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private int id_inv;
    private String nombre;
    private String descripcion;
    private Estados estado;
    private int cantidad;
    private String id_estacion;
    private String id_armario;
    private int id_balda;
    private LocalDate fecha_alta;
    private String observaciones;
    
    public MaterialInventario(String id_inv, String nombre, String descripcion, String estado, String cantidad, String id_estacion, String id_armario, String id_balda, String fecha_alta, String observaciones) throws IdInvalidoException {
        
        setId_inv(id_inv);
    }
    
    public int getId_inv() {
        return id_inv;
    }
    
    public void setId_inv(String id_inv) throws IdInvalidoException {
        Validador.validaInventario(id_inv);
        this.id_inv = Integer.parseInt(id_inv);
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
    
    public String getId_estacion() {
        return id_estacion;
    }
    
    public void setId_estacion(String id_estacion) {
        this.id_estacion = id_estacion;
    }
    
    public String getId_armario() {
        return id_armario;
    }
    
    public void setId_armario(String id_armario) throws IdInvalidoException {
        Validador.validaArmario(id_armario);
        this.id_armario = id_armario;
    }
    
    public int getId_balda() {
        return id_balda;
    }
    
    public void setId_balda(String id_balda) throws IdInvalidoException {
        Validador.validaBalda(id_balda);
        this.id_balda = Integer.parseInt(id_balda);
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
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MaterialInventario{");
        sb.append("id_inv=").append(id_inv);
        sb.append(", nombre=").append(nombre);
        sb.append(", descripcion=").append(descripcion);
        sb.append(", estado=").append(estado);
        sb.append(", cantidad=").append(cantidad);
        sb.append(", id_estacion=").append(id_estacion);
        sb.append(", id_armario=").append(id_armario);
        sb.append(", id_balda=").append(id_balda);
        sb.append(", fecha_alta=").append(fecha_alta);
        sb.append(", observaciones=").append(observaciones);
        
        return sb.toString();
    }
    
}
