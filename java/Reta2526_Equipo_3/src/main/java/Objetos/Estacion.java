/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

import Validador.Validador;
import Enum.Tipos;
import Excepciones.DescripcionInvalidaException;
import Excepciones.IdInvalidoException;
import Excepciones.TipoInvalidoException;

/**
 *
 * @author DAW120
 */
public class Estacion extends Ubicacion {

    private Tipos tipo;

    public Estacion(String id_ubi, String nombre, String descripcion, String tipo) throws TipoInvalidoException, DescripcionInvalidaException, IdInvalidoException {
        super(id_ubi, nombre, descripcion);
        setTipo(tipo);
    }

    public Tipos getTipo() {
        return tipo;
    }

    /**
     *METODO QUE VALIDA, PARSEA Y ASIGNA EL ATRIBUTO tipo
     * @param tipo
     * @throws TipoInvalidoException
     * @throws DescripcionInvalidaException
     */
    public void setTipo(String tipo) throws TipoInvalidoException, DescripcionInvalidaException {
        Validador.validaTipo(tipo);
        this.tipo = Tipos.valueOf(tipo);
    }

}
