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
import static Validador.Validador.validaPc;
import java.util.ArrayList;

/**
 *
 * @author DAW120
 */
public class Perifericos extends MaterialInventario {
// Tal vez se deberia quitar la herencia paracrear un array de id_pc

    private Conexion conexion;

    private ArrayList<Integer> pcs;

    public Perifericos(String id_matTa, String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones, String conexion, ArrayList<Integer> pcs) throws IdInvalidoException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException, CategoriaInvalidaException {
        super(id_matTa, nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
        setConexion(conexion);
        setPcs(pcs);
    }

    public Perifericos(String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones, String conexion, ArrayList<Integer> pcs) throws NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, IdInvalidoException, FechaInvalidaException, CategoriaInvalidaException {
        super(nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
        setConexion(conexion);
        setPcs(pcs);
    }

    public Perifericos(String id_matTa, String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones, String conexion) throws IdInvalidoException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException, CategoriaInvalidaException {
        super(id_matTa, nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
        setConexion(conexion);
    }

    /**
     *
     * @return
     */
    public Conexion getConexion() {
        return conexion;
    }

    /**
     * METODO QUE VALIDA, PARSEA Y ASIGNA EL ATRIBUTO conexion
     *
     * @param conexion
     * @throws CategoriaInvalidaException
     * @throws DescripcionInvalidaException
     */
    public void setConexion(String conexion) throws CategoriaInvalidaException, DescripcionInvalidaException {
        Validador.validaTipoConexion(conexion);
        this.conexion = Conexion.valueOf(conexion);
    }

    public ArrayList<Integer> getPcs() {
        return pcs;
    }

    public void setPcs(ArrayList<Integer> pcs) throws IdInvalidoException {
        if (!pcs.isEmpty()) {
            for (Integer pc : pcs) {
                validaPc(pc.toString());
            }
        }
        this.pcs = pcs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("\n---Periferico---");
        sb.append("\nConexion: ").append(conexion);
        return sb.toString();
    }

}
