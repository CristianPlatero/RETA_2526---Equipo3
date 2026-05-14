/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

import Validador.Validador;
import Enum.EstadosFungible;
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
public class Material_Fungible extends MaterialInventario {

    private EstadosFungible estadoFungible;

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
     * @param estadoFungible
     * @throws IdInvalidoException
     * @throws NombreInvalidoException
     * @throws DescripcionInvalidaException
     * @throws EstadoInvalidoException
     * @throws CantidadInvalidaException
     * @throws FechaInvalidaException
     */
    public Material_Fungible(String id_matTa, String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones, String estadoFungible) throws IdInvalidoException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException {
        super(id_matTa, nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
        setEstadoFungible(estadoFungible);
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
     * @param estadoFungible
     * @throws NombreInvalidoException
     * @throws DescripcionInvalidaException
     * @throws EstadoInvalidoException
     * @throws CantidadInvalidaException
     * @throws IdInvalidoException
     * @throws FechaInvalidaException
     */
    public Material_Fungible(String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones, String estadoFungible) throws NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, IdInvalidoException, FechaInvalidaException {
        super(nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
        setEstadoFungible(estadoFungible);
    }
    
    
    
    public EstadosFungible getEstadoFungible() {
        return estadoFungible;
    }

    /**
     *METODO QUE VALIDA, PARSEA Y ASIGNA EL ATRIBUTO estadoFungible
     * @param estado
     * @throws EstadoInvalidoException
     * @throws DescripcionInvalidaException
     */
    public void setEstadoFungible(String estado) throws EstadoInvalidoException, DescripcionInvalidaException {
        Validador.validaEstadoFungible(estado);
        this.estadoFungible = EstadosFungible.valueOf(estado);
    }

}
