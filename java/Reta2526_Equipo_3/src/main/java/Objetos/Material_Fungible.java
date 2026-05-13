/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

import Validador.Validador;
import Enum.EstadosFungible;
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
public class Material_Fungible extends MaterialInventario {

    private EstadosFungible estadoFungible;

    public Material_Fungible(String id_matTa, String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones, String estadoFungible) throws IdInvalidoException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException {
        super(id_matTa, nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
        setEstadoFungible(estadoFungible);
    }

    public EstadosFungible getEstadoFungible() {
        return estadoFungible;
    }

    public void setEstadoFungible(String estado) throws EstadoInvalidoException, DescripcionInvalidaException {
        Validador.validaEstadoFungible(estado);
        this.estadoFungible = EstadosFungible.valueOf(estado);
    }

}
