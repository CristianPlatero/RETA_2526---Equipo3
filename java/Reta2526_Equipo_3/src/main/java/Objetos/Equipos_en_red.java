/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

import Validador.Validador;
import Excepciones.CantidadInvalidaException;
import Excepciones.DescripcionInvalidaException;
import Excepciones.EstadoInvalidoException;
import Excepciones.FechaInvalidaException;
import Excepciones.IdInvalidoException;
import Excepciones.NombreInvalidoException;

/**
 *
 * @author DAW120
 */
public class Equipos_en_red extends MaterialInventario{
    
    private  int numPuertos;

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
     * @param numPuertos
     * @throws IdInvalidoException
     * @throws NombreInvalidoException
     * @throws DescripcionInvalidaException
     * @throws EstadoInvalidoException
     * @throws CantidadInvalidaException
     * @throws FechaInvalidaException
     */
    public Equipos_en_red(String id_matTa, String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones,String numPuertos) throws IdInvalidoException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException {
        super(id_matTa, nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
        setNumPuertos(numPuertos);
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
     * @param numPuertos
     * @throws NombreInvalidoException
     * @throws DescripcionInvalidaException
     * @throws EstadoInvalidoException
     * @throws CantidadInvalidaException
     * @throws IdInvalidoException
     * @throws FechaInvalidaException
     */
    public Equipos_en_red(String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones,String numPuertos) throws NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, IdInvalidoException, FechaInvalidaException {
        super(nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
        setNumPuertos(numPuertos);
    }
    
    

    public int getNumPuertos() {
        return numPuertos;
    }

    /**
     *METODO QUE VALIDA, PARSEA Y ASIGNA EL ATRIBUTO numPuertos
     * @param numPuertos
     * @throws CantidadInvalidaException
     */
    public void setNumPuertos(String numPuertos) throws CantidadInvalidaException {
        Validador.validaNumPuertos(numPuertos);
        this.numPuertos = Integer.parseInt(numPuertos);
    }
    
    
    
}
