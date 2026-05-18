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
public abstract class Ubicacion {

    private String id_ubi;
    private String nombre;
    private String descripcion;

    /**
     *
     * @param id_ubi
     * @param nombre
     * @param descripcion
     * @throws IdInvalidoException
     */
    public Ubicacion(String id_ubi, String nombre, String descripcion) throws IdInvalidoException {
        setId_ubi(id_ubi);
        setNombre(nombre);
        setDescripcion(descripcion);

    }

    /**
     *
     * @return
     */
    public String getId_ubi() {

        return id_ubi;
    }

    /**
     *
     * @param id_ubi
     * @throws IdInvalidoException
     */
    public void setId_ubi(String id_ubi) throws IdInvalidoException {
        Validador.validaUbi(id_ubi);
        this.id_ubi = id_ubi;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     *
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Ubicacion{");
        sb.append("id_ubi=").append(id_ubi);
        sb.append(", nombre=").append(nombre);
        sb.append(", descripcion=").append(descripcion);
        return sb.toString();
    }

}
