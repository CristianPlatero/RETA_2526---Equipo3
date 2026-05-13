/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

import Validador.Validador;
import Excepciones.IdInvalidoException;

/**
 *
 * @author DAW120
 */
public class Balda extends Armario {

    private int id_balda;

    public Balda(String id_ubi, String nombre, String descripcion, String movilidad, String id_balda) throws IdInvalidoException {
        super(id_ubi, nombre, descripcion, movilidad);
        setId_balda(id_balda);
    }

   

    public int getId_balda() {
        return id_balda;
    }

    public void setId_balda(String id_balda) throws IdInvalidoException {
        Validador.validaBalda(id_balda);
        this.id_balda = Integer.parseInt(id_balda);
    }

}
