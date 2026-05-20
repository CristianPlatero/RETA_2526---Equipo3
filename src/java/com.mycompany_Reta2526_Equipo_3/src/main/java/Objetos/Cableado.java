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
 *
 * @author DAW120
 */
public class Cableado extends MaterialInventario{
    
    private double longitud;
    private String conector1;
    private String conector2;

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
     * @param longitud
     * @param conector1
     * @param conector2
     * @throws IdInvalidoException
     * @throws NombreInvalidoException
     * @throws DescripcionInvalidaException
     * @throws EstadoInvalidoException
     * @throws CantidadInvalidaException
     * @throws FechaInvalidaException
     * @throws LongitudInvalidaException
     */
    public Cableado(String id_matTa, String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones, String longitud, String conector1, String conector2) throws IdInvalidoException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException, LongitudInvalidaException, ConectorInvalidoException {
        super(id_matTa, nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
        setLongitud(longitud);
        setConector1(conector1);
        setConector2(conector2);
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
     * @param longitud
     * @param conector1
     * @param conector2
     * @throws NombreInvalidoException
     * @throws DescripcionInvalidaException
     * @throws EstadoInvalidoException
     * @throws CantidadInvalidaException
     * @throws IdInvalidoException
     * @throws FechaInvalidaException
     * @throws LongitudInvalidaException
     */
    public Cableado(String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones, String longitud, String conector1, String conector2) throws NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, IdInvalidoException, FechaInvalidaException, LongitudInvalidaException, ConectorInvalidoException {
        super(nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
         setLongitud(longitud);
        setConector1(conector1);
        setConector2(conector2);
    }
    
    /**
     *
     * @return
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     *METODO QUE VALIDA, PARSEA Y ASIGNA EL ATRIBUTO longitud
     * @param longitud
     * @throws CantidadInvalidaException
     * @throws LongitudInvalidaException
     */
    public void setLongitud(String longitud) throws CantidadInvalidaException, LongitudInvalidaException {
        Validador.validaLongitud(longitud);
        this.longitud = Double.parseDouble(longitud);
    }

    /**
     *
     * @return
     */
    public String getConector1() {
        return conector1;
    }

    /**
     *
     * @param conector1
     */
    public void setConector1(String conector1) throws ConectorInvalidoException {
        Validador.validaConector(conector1);
        this.conector1 = conector1;
    }

    /**
     *
     * @return
     */
    public String getConector2() {
        return conector2;
    }

    /**
     *
     * @param conector2
     */
    public void setConector2(String conector2) throws ConectorInvalidoException {
        Validador.validaConector(conector2);
        this.conector2 = conector2;
    }
    
    
    
}
