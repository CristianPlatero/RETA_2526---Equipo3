/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *FALLO EN LA CANTIDAD
 * @author DAW120
 */
public class CantidadInvalidaException extends Exception {

    /**
     *CONSTRUCTOR CON MENSAJE EDITABLE 
     * @param message El mensaje que muestra
     */
    public CantidadInvalidaException(String message) {
        super(message);
    }

}
