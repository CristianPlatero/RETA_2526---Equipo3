/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

import Validador.Validador;
import Excepciones.CantidadInvalidaException;
import Excepciones.DescripcionInvalidaException;
import Excepciones.EstadoInvalidoException;
import Excepciones.FechaInvalidaException;
import Excepciones.IdInvalidoException;
import Excepciones.NombreInvalidoException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author DAW120
 */
public class Componentes extends MaterialInventario {

    private int id_pc;

    /**
     *CONSTRUCTOR CON ID PARA LECTURA
     * @param id_matTa
     * @param nombre
     * @param descripcion
     * @param estado
     * @param cantidad
     * @param id_ubi
     * @param id_balda
     * @param fecha_alta
     * @param observaciones
     * @param id_pc
     * @throws IdInvalidoException
     * @throws NombreInvalidoException
     * @throws DescripcionInvalidaException
     * @throws EstadoInvalidoException
     * @throws CantidadInvalidaException
     * @throws FechaInvalidaException
     */
    public Componentes(String id_matTa, String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones, String id_pc) throws IdInvalidoException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException {
        super(id_matTa, nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
        setId_pc(id_pc);
    }

    /**
     *CONSTRUCTOR SIN ID PARA INSERCION
     * @param nombre
     * @param descripcion
     * @param estado
     * @param cantidad
     * @param id_ubi
     * @param id_balda
     * @param fecha_alta
     * @param observaciones
     * @param id_pc
     * @throws NombreInvalidoException
     * @throws DescripcionInvalidaException
     * @throws EstadoInvalidoException
     * @throws CantidadInvalidaException
     * @throws IdInvalidoException
     * @throws FechaInvalidaException
     */
    public Componentes(String nombre, String descripcion, String estado, String cantidad, String id_ubi, String id_balda, String fecha_alta, String observaciones, String id_pc) throws NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, IdInvalidoException, FechaInvalidaException {
        super(nombre, descripcion, estado, cantidad, id_ubi, id_balda, fecha_alta, observaciones);
        setId_pc(id_pc);
    }

    /**
     *
     * @return
     */
    public int getId_pc() {
        return id_pc;
    }

    /**
     *METODO QUE VALIDA, PARSEA Y ASIGNA EL ATRIBUTO id_pc
     * @param id_pc
     * @throws IdInvalidoException
     */
    public void setId_pc(String id_pc) throws IdInvalidoException {
        Validador.validaPc(id_pc);
        this.id_pc = Integer.parseInt(id_pc);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Componentes{");
        sb.append("id_pc=").append(id_pc);
        sb.append('}');
        return sb.toString();
    }
    
    

}
