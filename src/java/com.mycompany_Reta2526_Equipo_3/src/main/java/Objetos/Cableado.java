/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

import Validador.Validador;
import Excepciones.CantidadInvalidaException;
import Excepciones.ConectorInvalidoException;
import Excepciones.DescripcionInvalidaException;
import Excepciones.EstadoInvalidoException;
import Excepciones.FechaInvalidaException;
import Excepciones.IdInvalidoException;
import Excepciones.LongitudInvalidaException;
import Excepciones.NombreInvalidoException;

/**
 *CLASE CABLEADO
 * @author DAW126
 */
public class Cableado extends MaterialInventario{
    
    private double longitud;
    private String conector1;
    private String conector2;

    /**
     *CONSTRUCTOR CON ID PARA LECTURA
     * @param id_matTa String
     * @param nombre String
     * @param descripcion String
     * @param estado String
     * @param cantidad String
     * @param id_ubi String
     * @param id_balda String
     * @param fecha_alta String
     * @param observaciones String
     * @param longitud String
     * @param conector1 String
     * @param conector2 String
     * @throws IdInvalidoException ID no valido
     * @throws NombreInvalidoException NOMBRE no valido
     * @throws DescripcionInvalidaException DESCRIPCION no valido
     * @throws EstadoInvalidoException ESTADO no valido
     * @throws CantidadInvalidaException CANTIDAD no valido
     * @throws FechaInvalidaException FECHA no valido
     * @throws LongitudInvalidaException LONGITUD no valido
     * @throws Excepciones.ConectorInvalidoException CONECTOR no valido
     */
    public Cableado(String id_matTa, String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones, String longitud, String conector1, String conector2) throws IdInvalidoException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException, LongitudInvalidaException, ConectorInvalidoException {
        super(id_matTa, nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
        setLongitud(longitud);
        setConector1(conector1);
        setConector2(conector2);
    }

    /**
     *CONSTRUCTOR SIN ID PARA INSERCION
     * @param nombre String
     * @param descripcion String
     * @param estado String
     * @param cantidad String
     * @param id_ubi String
     * @param id_balda String
     * @param fecha_alta String
     * @param observaciones String
     * @param longitud String
     * @param conector1 String
     * @param conector2 String
     * @throws NombreInvalidoException NOMBRE no valido
     * @throws DescripcionInvalidaException DESCRIPCION no valido
     * @throws EstadoInvalidoException ESTADO no valido
     * @throws CantidadInvalidaException CANTIDAD no valido
     * @throws FechaInvalidaException FECHA no valido
     * @throws LongitudInvalidaException LONGITUD no valido
     * @throws Excepciones.ConectorInvalidoException CONECTOR no valido
     * @throws Excepciones.IdInvalidoException no valido
     */
    public Cableado(String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones, String longitud, String conector1, String conector2) throws NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException, LongitudInvalidaException, ConectorInvalidoException, IdInvalidoException {
        super(nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
         setLongitud(longitud);
        setConector1(conector1);
        setConector2(conector2);
    }
    
    /**
     *DEVUELVE LONGITUD
     * @return double
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     *METODO QUE VALIDA, PARSEA Y ASIGNA EL ATRIBUTO longitud
     * @param longitud String
     * @throws CantidadInvalidaException  CANTIDAD no valido
     * @throws LongitudInvalidaException LONGITUD no valido
     */
    public void setLongitud(String longitud) throws CantidadInvalidaException, LongitudInvalidaException {
        Validador.validaLongitud(longitud);
        this.longitud = Double.parseDouble(longitud);
    }

    /**
     *DEVUELVE conector1
     * @return String
     */
    public String getConector1() {
        return conector1;
    }

    
    public void setConector1(String conector1) throws ConectorInvalidoException {
        Validador.validaConector(conector1);
        this.conector1 = conector1;
    }

    /**
     *DEVUELVE conector2
     * @return String
     */
    public String getConector2() {
        return conector2;
    }

    
    public void setConector2(String conector2) throws ConectorInvalidoException {
        Validador.validaConector(conector2);
        this.conector2 = conector2;
    }
    
    
    
}
