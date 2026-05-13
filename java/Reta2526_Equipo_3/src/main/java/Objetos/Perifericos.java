/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

import Validador.Validador;
import Enum.Conexion;
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
public class Perifericos extends Componentes {

    private Conexion conexion;

    public Perifericos(String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones, String id_pc, String conexion) throws IdInvalidoException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException, CategoriaInvalidaException {
        super(nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones, id_pc);
        setConexion(conexion);
    }

    public Perifericos(String id_matTa, String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones, String id_pc, String conexion) throws IdInvalidoException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException, CategoriaInvalidaException {
        super(id_matTa, nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones, id_pc);
        setConexion(conexion);
    }

    public Conexion getConexion() {
        return conexion;
    }

    public void setConexion(String conexion) throws CategoriaInvalidaException, DescripcionInvalidaException {
        Validador.validaTipoConexion(conexion);
        this.conexion = Conexion.valueOf(conexion);
    }

}
