/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

import Validador.Validador;
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
public class Equipos_en_red extends MaterialInventario{
    
    private  int numPuertos;

    public Equipos_en_red(int numPuertos, String id_matTa, String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones) throws IdInvalidoException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException {
        super(id_matTa, nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
        setNumPuertos(nombre);
    }

    public int getNumPuertos() {
        return numPuertos;
    }

    public void setNumPuertos(String numPuertos) throws CantidadInvalidaException {
        Validador.validaNumPuertos(numPuertos);
        this.numPuertos = Integer.parseInt(numPuertos);
    }
    
    
    
}
