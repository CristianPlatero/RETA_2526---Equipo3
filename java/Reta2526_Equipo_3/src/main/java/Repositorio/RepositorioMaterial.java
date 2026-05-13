/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Repositorio;

import java.util.List;

/**
 *
 * @author DAW120
 */
public interface RepositorioMaterial<T> {
    // método para listar todos los objetos T
    // para listar todos los registros de una tabla
    public List<T> listarMaterial();
    // método para recuperar un objeto por su ID
    // nos recupera un registro de la base de datos por clave primaria
    public T porIdMaterial( int id);
    // método en este caso puede ser tanto para realizar la inserción o modificación de un objeto
    // aunque también se pueden crear un método para añadir un objeto y otro para modificar
    // inserta un registro en la tabla o bien lo modifica
    public void guardarMaterial(T t);
    // método para borrar un objeto por su ID
    // nos permite borrar un registro de la base de datos por clave primaria
    public void eliminarMaterial( int  id);
}
