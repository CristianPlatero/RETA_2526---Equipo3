/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *FALLO EN FECHA
 * @author DAW120
 */
public class FechaInvalidaException extends Exception {

    /**
     *CONSTRUCTOR CON MENSAJE EDITABLE 
     * @param message El mensaje que muestra
     */
    public FechaInvalidaException(String message) {
        super(message);
    }

}
