/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reta2526_equipo_3;

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

    public void setMovilidad(String movilidad) throws MovilidadInvalidaException, DescripcionInvalidaException {
        Validador.validaMovilidad(movilidad);
        this.movilidad = Movilidades.valueOf(movilidad);
    }

}
