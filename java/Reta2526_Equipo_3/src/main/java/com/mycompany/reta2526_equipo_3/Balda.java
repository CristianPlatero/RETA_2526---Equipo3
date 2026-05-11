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
public class Balda extends Ubicacion {

    private int id_balda;

    public Balda(int id_balda, String id_ubi, String nombre, String descripcion) {
        super(id_ubi, nombre, descripcion);
        this.id_balda = id_balda;
    }

    public int getId_balda() {
        return id_balda;
    }

    public void setId_balda(String id_balda) throws IdInvalidoException {
        Validador.validaBalda(id_balda);
        this.id_balda = Integer.parseInt(id_balda);
    }

}
