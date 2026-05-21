/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

import Validador.Validador;
import Excepciones.IdInvalidoException;


public abstract class Ubicacion {

    private String id_ubi;
    private String nombre;
    private String descripcion;

    
    public Ubicacion(String id_ubi, String nombre, String descripcion) throws IdInvalidoException {
        setId_ubi(id_ubi);
        setNombre(nombre);
        setDescripcion(descripcion);

    }

    
    public String getId_ubi() {

        return id_ubi;
    }

    
    public void setId_ubi(String id_ubi) throws IdInvalidoException {
        Validador.validaUbi(id_ubi);
        this.id_ubi = id_ubi;
    }

    
    public String getNombre() {
        return nombre;
    }

   
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

   
    public String getDescripcion() {
        return descripcion;
    }

    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    
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
