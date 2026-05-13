/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reta2526_equipo_3;

import Excepciones.CantidadInvalidaException;
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

    public Cableado(String id_matTa, String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones, String longitud, String conector1, String conector2) throws IdInvalidoException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException, LongitudInvalidaException {
        super(id_matTa, nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
        setLongitud(longitud);
        this.conector1 = conector1;
        this.conector2 = conector2;
    }
    
    

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) throws CantidadInvalidaException, LongitudInvalidaException {
        Validador.validaLongitud(longitud);
        this.longitud = Double.parseDouble(longitud);
    }

    public String getConector1() {
        return conector1;
    }

    public void setConector1(String conector1) {
        this.conector1 = conector1;
    }

    public String getConector2() {
        return conector2;
    }

    public void setConector2(String conector2) {
        this.conector2 = conector2;
    }
    
    
    
}
