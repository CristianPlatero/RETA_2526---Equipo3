/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.reta2526_equipo_3;

import Excepciones.CantidadInvalidaException;
import Excepciones.CategoriaInvalidaException;
import Excepciones.DescripcionInvalidaException;
import Excepciones.EstadoInvalidoException;
import Excepciones.FechaInvalidaException;
import Excepciones.IdInvalidoException;
import Excepciones.NombreInvalidoException;
import Interfaz.Marco;
import java.sql.SQLException;

/**
 *
 * @author DAW102
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IdInvalidoException, SQLException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException, CategoriaInvalidaException {
        //Marco miMarco = new Marco();
        
        Perifericos p = new Perifericos("1", "no", "d", "obsoleto", "1", "ARM01", "1", "12/01/2025", "o", "1", "inalambrica");
        
        AdministradorDAO.guardarPeriferico(p);
        
        
    }
    
}
