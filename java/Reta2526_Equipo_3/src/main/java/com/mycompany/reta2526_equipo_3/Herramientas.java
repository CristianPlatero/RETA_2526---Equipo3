/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reta2526_equipo_3;

import Enum.TiposHerramienta;
import Excepciones.CantidadInvalidaException;
import Excepciones.CategoriaInvalidaException;
import Excepciones.DescripcionInvalidaException;
import Excepciones.EstadoInvalidoException;
import Excepciones.FechaInvalidaException;
import Excepciones.IdInvalidoException;
import Excepciones.NombreInvalidoException;

/**
 *
 * @author DAW120
 */
public class Herramientas extends MaterialInventario{
    
   private TiposHerramienta tipo;

    public Herramientas(String id_matTa, String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones, String tipo) throws IdInvalidoException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException, CategoriaInvalidaException {
        super(id_matTa, nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
        setTipoH(estado);
    }

    public TiposHerramienta getTipo() {
        return tipo;
    }

    public void setTipoH(String tipo) throws CategoriaInvalidaException, DescripcionInvalidaException {
        Validador.validaTipoHerramienta(tipo);
        this.tipo = TiposHerramienta.valueOf(tipo);
    }
   
   
    
    
    
}
