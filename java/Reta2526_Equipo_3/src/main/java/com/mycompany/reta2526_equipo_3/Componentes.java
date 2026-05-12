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
import Excepciones.NombreInvalidoException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author DAW120
 */
public class Componentes extends MaterialInventario {

    private int id_pc;

    public Componentes( String id_matTa, String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones,String id_pc) throws IdInvalidoException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException {
        super(id_matTa, nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
        setId_pc(id_pc);
    }

    

    public int getId_pc() {
        return id_pc;
    }

    public void setId_pc(String id_pc) throws IdInvalidoException {
        Validador.validaPc(id_pc);
        this.id_pc = Integer.parseInt(id_pc);
    }

   

   
    
    
    
    

}
