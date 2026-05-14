/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

import Validador.Validador;
import Enum.Movilidades;
import Excepciones.DescripcionInvalidaException;
import Excepciones.IdInvalidoException;
import Excepciones.MovilidadInvalidaException;

/**
 *
 * @author DAW120
 */
public class Armario extends Ubicacion {

    private Movilidades movilidad;

    public Armario(String id_ubi, String nombre, String descripcion, String movilidad) throws IdInvalidoException {
        super(id_ubi, nombre, descripcion);

    }

    public Movilidades getMovilidad() {
        return movilidad;
    }

    /**
     *METODO QUE VALIDA, PARSEA Y ASIGNA EL ATRIBUTO movilidad
     * @param movilidad
     * @throws MovilidadInvalidaException
     * @throws DescripcionInvalidaException
     */
    public void setMovilidad(String movilidad) throws MovilidadInvalidaException, DescripcionInvalidaException {
        Validador.validaMovilidad(movilidad);
        this.movilidad = Movilidades.valueOf(movilidad);
    }

}
