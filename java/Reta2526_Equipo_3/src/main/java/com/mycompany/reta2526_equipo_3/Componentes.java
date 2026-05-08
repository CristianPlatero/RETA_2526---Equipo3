/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reta2526_equipo_3;

import com.mycompany.reta2526_equipo_3.Excepciones.IdInvalidoException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author DAW120
 */
public class Componentes extends MaterialInventario {

    private int id_pc;

    public Componentes(String id_inv, String nombre, String descripcion, String estado, String cantidad, String id_estacion, String id_armario, String id_balda, String fecha_alta, String observaciones, String id_pc) throws IdInvalidoException {
        super(id_inv, nombre, descripcion, estado, cantidad, id_estacion, id_armario, id_balda, fecha_alta, observaciones);
    }

    public int getId_pc() {
        return id_pc;
    }

    public void setId_pc(String id_pc) throws IdInvalidoException {
        Validador.validaPc(id_pc);
        this.id_pc = Integer.parseInt(id_pc);
    }

   

   
    
    
    
    

}
