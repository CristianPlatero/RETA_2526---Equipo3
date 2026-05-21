/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Excepciones;

/**
 *FALLO EN CATEGORIA
 * @author DAW120
 */
public class CategoriaInvalidaException extends Exception {

   /**
     *CONSTRUCTOR CON MENSAJE EDITABLE 
     * @param message El mensaje que muestra
     */
    public CategoriaInvalidaException(String message) {
        super(message);
    }

}
