/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Repositorio;

import java.util.List;


public interface RepositorioPc<T> {
    // 
    // 

    /**
     *método para listar todos los objetos T
     * @return
     * para listar todos los registros de una tabla
     */

    public List<T> listarPc();

    
     

    /**
     * método para recuperar un objeto por su ID
     * @param id
     * @return
     * nos recupera un registro de la base de datos por clave primaria
     *
     */
    public T porIdPc(int id);

     

    /**
     *método en este caso puede ser tanto para realizar la inserción o modificación de un objeto
     * @param t
     * 
     * @see 
     * aunque también se pueden crear un método para añadir un objeto y otro para modificar
     inserta un registro en la tabla o bien lo modifica
     * 
     */
    public void guardarPc(T t);

    

    /**
     * método para borrar un objeto por su ID  
     * @param id
     * @see 
     * nos permite borrar un registro de la base de datos por clave primaria
     */
    public void eliminarPc(int id);
}
