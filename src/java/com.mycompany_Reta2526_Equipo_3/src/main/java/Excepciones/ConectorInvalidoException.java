/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *FALLO DE CONECTOR
 * @author DAW120
 */
public class ConectorInvalidoException extends Exception{

    /**
     *CONSTRUCTOR CON MENSAJE EDITABLE 
     * @param message El mensaje que muestra
     */
    public ConectorInvalidoException(String message) {
        super(message);
    }
    
}
