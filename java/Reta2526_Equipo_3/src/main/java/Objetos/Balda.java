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
public class Balda extends Armario {

    private Integer id_balda;

    /**
     *
     * @param id_ubi
     * @param nombre
     * @param descripcion
     * @param movilidad
     * @param id_balda
     * @throws IdInvalidoException
     */
    public Balda(String id_ubi, String nombre, String descripcion, String movilidad, String id_balda) throws IdInvalidoException {
        super(id_ubi, nombre, descripcion, movilidad);
        setId_balda(id_ubi,id_balda);
    }

    /**
     *
     * @return
     */
    public Integer getId_balda() {
        return id_balda;
    }

    /**
     *METODO QUE VALIDA, PARSEA Y ASIGNA EL ATRIBUTO id_balda
     * @param ubi
     * @param id_balda
     * @throws IdInvalidoException
     */
    public void setId_balda(String ubi,String id_balda) throws IdInvalidoException {
        Validador.validaBalda(ubi,id_balda);
        if(id_balda == null){
            this.id_balda = null;
        }else{
            this.id_balda = Integer.valueOf(id_balda);
        }
        
    }

}
