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


public class MaterialInventario {

    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private int id_matTa;
    private String nombre;
    private String descripcion;
    private Estados estado;
    private int cantidad;

    private String id_ubi;
    private Integer id_balda;
    //Es Integer porque acepta null
    private LocalDate fecha_alta;
    private String observaciones;

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
     * @throws IdInvalidoException
     * @throws NombreInvalidoException
     * @throws DescripcionInvalidaException
     * @throws EstadoInvalidoException
     * @throws CantidadInvalidaException
     * @throws FechaInvalidaException
     */
    public MaterialInventario(String id_matTa, String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones) throws IdInvalidoException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException {

        setId_matTa(id_matTa);
        setNombre(nombre);
        setDescripcion(descripcion);
        setEstado(estado);
        setCantidad(cantidad);
        setId_ubi(id_ubi);
        setId_balda(id_ubi, id_balda);
        setFecha_alta(fecha_alta);
        setObservaciones(observaciones);

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
     * @throws NombreInvalidoException
     * @throws DescripcionInvalidaException
     * @throws EstadoInvalidoException
     * @throws CantidadInvalidaException
     * @throws IdInvalidoException
     * @throws FechaInvalidaException
     */
    public MaterialInventario(String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones) throws NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, IdInvalidoException, FechaInvalidaException {
        setNombre(nombre);
        setDescripcion(descripcion);
        setEstado(estado);
        setCantidad(cantidad);
        setId_ubi(id_ubi);
        setId_balda(id_ubi, id_balda);
        setFecha_alta(fecha_alta);
        setObservaciones(observaciones);

    }

    //Sobrecarga

    
    public int getId_matTa() {
        return id_matTa;
    }

    /**
     *METODO QUE VALIDA, PARSEA Y ASIGNA EL ATRIBUTO id_matTa
     * @param id_matTa
     * @throws IdInvalidoException
     */
    public void setId_matTa(String id_matTa) throws IdInvalidoException {
        Validador.validaInventario(id_matTa);
        this.id_matTa = Integer.parseInt(id_matTa);
    }

    
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
        this.estado = Estados.valueOf(estado.toUpperCase().trim());
    }

    
    public int getCantidad() {
        return cantidad;
    }

    /**
     *METODO QUE VALIDA, PARSEA Y ASIGNA EL ATRIBUTO cantidad
     * @param cantidad
     * @throws CantidadInvalidaException
     */
    public void setCantidad(String cantidad) throws CantidadInvalidaException {
        Validador.validaCantidad(cantidad);
        this.cantidad = Integer.parseInt(cantidad);
    }

    
    public String getId_ubi() {
        return id_ubi;
    }

    /**
     *METODO QUE VALIDA Y ASIGNA EL ATRIBUTO id_ubi
     * @param id_ubi
     * @throws IdInvalidoException
     */
    public void setId_ubi(String id_ubi) throws IdInvalidoException {
        Validador.validaUbi(id_ubi);
        this.id_ubi = id_ubi;
    }

    
    public Integer getId_balda() {
        return id_balda;
    }

    /**
     *METODO QUE VALIDA, PARSEA Y ASIGNA EL ATRIBUTO id_balda
     * @param ubi
     * Es el id de la ubicacion del cual depende el formato del id_balda
     * @param id_balda
     * Es el id de la balda que debe validar
     * @throws IdInvalidoException
     * @see 
     * 
     * <br>Si el id_balda existe(!null) pero esta en blanco(isBlank)
     * <br>*Lo vuelve NULL
     * 
     * <br>Llama al metodo validaBalda de la clase Validador
     * <br>*Para que valide el id_balda dependiendo de ubi
     * 
     * <br>Si id_balda es null el valor asignado al id_balda es null
     * <br>Si no, es el id_balda convertido en numero
     * 
     */
    public void setId_balda(String ubi, String id_balda) throws IdInvalidoException {
        
        if(id_balda != null && id_balda.isBlank()){
            id_balda = null;
        }
        
        Validador.validaBalda(ubi, id_balda);

        if (id_balda == null) {
            this.id_balda = null;
        } 
        else if (id_balda.isBlank()) {
            this.id_balda = null;
        } 
        else {
            this.id_balda = Integer.valueOf(id_balda);
        }

    }

    
    public LocalDate getFecha_alta() {
        return fecha_alta;
    }

    /**
     *METODO QUE VALIDA, PARSEA Y ASIGNA EL ATRIBUTO fecha_alta
     * @param fecha_alta
     * @throws FechaInvalidaException
     */
//    public void setFecha_alta(String fecha_alta) throws FechaInvalidaException {
//        Validador.validaFecha(fecha_alta);
//        this.fecha_alta = LocalDate.parse(fecha_alta, formato);
//    }
    public void setFecha_alta(String fecha_alta) throws FechaInvalidaException {
    Validador.validaFecha(fecha_alta);

    // acepta formato BD yyyy-MM-dd
    if (fecha_alta.matches("\\d{4}-\\d{2}-\\d{2}")) {
        this.fecha_alta = LocalDate.parse(fecha_alta); // formato automático BD
    } else {
        this.fecha_alta = LocalDate.parse(fecha_alta, formato); // dd-MM-yyyy
    }
}

    
    public String getObservaciones() {
        return observaciones;
    }

    /**
     *METODO QUE VALIDA Y ASIGNA EL ATRIBUTO observaciones
     * @param observaciones
     * @throws DescripcionInvalidaException
     */
    public void setObservaciones(String observaciones) throws DescripcionInvalidaException {
        Validador.validaObservacion(observaciones);
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
