/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

import Validador.Validador;
import Enum.Estados;
import Excepciones.CantidadInvalidaException;
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
public class MaterialInventario {

    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private int id_matTa;
    private String nombre;
    private String descripcion;
    private Estados estado;
    private int cantidad;

    private String id_ubi;
    private int id_balda;
    private LocalDate fecha_alta;
    private String observaciones;

    public MaterialInventario(String id_matTa, String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones) throws IdInvalidoException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException {

        setId_matTa(id_matTa);
        setNombre(nombre);
        setDescripcion(descripcion);
        setEstado(estado);
        setCantidad(cantidad);
        setId_ubi(id_ubi);
        setId_balda(id_balda);
        setFecha_alta(fecha_alta);
        setObservaciones(observaciones);

    }

    public MaterialInventario(String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones) throws NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, IdInvalidoException, FechaInvalidaException {
        setNombre(nombre);
        setDescripcion(descripcion);
        setEstado(estado);
        setCantidad(cantidad);
        setId_ubi(id_ubi);
        setId_balda(id_balda);
        setFecha_alta(fecha_alta);
        setObservaciones(observaciones);

    }

    public int getId_matTa() {
        return id_matTa;
    }

    public void setId_matTa(String id_matTa) throws IdInvalidoException {
        Validador.validaInventario(id_matTa);
        this.id_matTa = Integer.parseInt(id_matTa);
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

    public String getId_ubi() {
        return id_ubi;
    }

    public void setId_ubi(String id_ubi) throws IdInvalidoException {
        Validador.validaUbi(id_ubi);
        this.id_ubi = id_ubi;
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
        this.fecha_alta = LocalDate.parse(fecha_alta, formato);
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
        sb.append("id_matTa=").append(id_matTa);
        sb.append(", nombre=").append(nombre);
        sb.append(", descripcion=").append(descripcion);
        sb.append(", estado=").append(estado);
        sb.append(", cantidad=").append(cantidad);
        sb.append(", id_balda=").append(id_balda);
        sb.append(", fecha_alta=").append(fecha_alta);
        sb.append(", observaciones=").append(observaciones);

        return sb.toString();
    }

}
