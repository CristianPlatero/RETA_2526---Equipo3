/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reta2526_equipo_3;

import com.mycompany.reta2526_equipo_3.Excepciones.IdInvalidoException;

/**
 *
 * @author DAW120
 */
public class Cableado extends MaterialInventario{
    
    private double longitud;
    private String conector1;
    private String conector2;
    
    
    public Cableado(String id_inv, String nombre, String descripcion, String estado, String cantidad, String id_estacion, String id_armario, String id_balda, String fecha_alta, String observaciones, String longitud, String conector1, String conector2) throws IdInvalidoException {
        super(id_inv, nombre, descripcion, estado, cantidad, id_estacion, id_armario, id_balda, fecha_alta, observaciones);
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
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
