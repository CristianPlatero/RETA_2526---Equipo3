/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reta2526_equipo_3;

import com.mycompany.reta2526_equipo_3.Excepciones.CantidadInvalidaException;
import com.mycompany.reta2526_equipo_3.Excepciones.ConectorInvalidoException;
import com.mycompany.reta2526_equipo_3.Excepciones.DescripcionInvalidaException;
import com.mycompany.reta2526_equipo_3.Excepciones.EstadoInvalidoException;
import com.mycompany.reta2526_equipo_3.Excepciones.FechaInvalidaException;
import com.mycompany.reta2526_equipo_3.Excepciones.IdInvalidoException;
import com.mycompany.reta2526_equipo_3.Excepciones.LongitudInvalidaException;
import com.mycompany.reta2526_equipo_3.Excepciones.NombreInvalidoException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author DAW120
 */
public class Validador {

    public static void validaInventario(String inventario) throws IdInvalidoException {
        if (inventario == null || inventario.isBlank()) {
            throw new IdInvalidoException("Debe introducir un ID de inventario.");
        }
        try {
            int valor = Integer.parseInt(inventario.trim());
            if (valor < 0 || valor > 99) {
                throw new IdInvalidoException("El ID de inventario debe ser entre 0 y 99.");
            }
        } catch (NumberFormatException e) {
            throw new IdInvalidoException("El ID de inventario debe ser un número entero válido.");
        }
    }

    public static void validaNombre(String nombre) throws NombreInvalidoException {
        if (nombre == null || nombre.isBlank()) {
            throw new NombreInvalidoException("El nombre no puede estar vacío.");
        }
        if (nombre.length() < 2 || nombre.length() > 50) {
            throw new NombreInvalidoException("El nombre debe tener entre 2 y 50 caracteres.");
        }
        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s\\-]+$")) {
            throw new NombreInvalidoException("El nombre solo puede contener letras, espacios y guiones.");
        }
    }

    public static void validaDescripcion(String descripcion) throws DescripcionInvalidaException {
        if (descripcion == null || descripcion.isBlank()) {
            throw new DescripcionInvalidaException("La descripcion no puede estar vacía.");
        }
        if (descripcion.length() < 2 || descripcion.length() > 50) {
            throw new DescripcionInvalidaException("La descripcion debe tener entre 2 y 50 caracteres.");
        }

    }

    public static void validaEstado(String estado) throws EstadoInvalidoException, DescripcionInvalidaException {
        estado = estado.toUpperCase().trim();
        if (estado == null || estado.isBlank()) {
            throw new DescripcionInvalidaException("El estado no puede estar vacía.");
        }
        switch (estado) {
            case "OBSOLETO" -> {

            }
            case "OPERATIVO" -> {

            }
            case "REPARACION" -> {

            }
            default -> {

                throw new EstadoInvalidoException("El estado debe ser OBSOLETO, OPERATIVO O REPARACION");
            }
        }

    }

    public static void validaCantidad(String cantidad) throws CantidadInvalidaException {
        if (cantidad == null || cantidad.isBlank()) {
            throw new CantidadInvalidaException("Debe introducir una cantidad.");
        }
        try {
            int valor = Integer.parseInt(cantidad.trim());
            if (valor < 0 || valor > 120) {
                throw new CantidadInvalidaException("La cantidad debe ser entre 0 y 99.");
            }
        } catch (NumberFormatException e) {
            throw new CantidadInvalidaException("La cantidad debe ser un número entero válido.");
        }
    }

    public static void validaEstacion(String estacion) throws IdInvalidoException {
        if (estacion == null || estacion.isBlank()) {
            throw new IdInvalidoException("El ID de la estación no puede estar vacío.");
        }

        if (!estacion.matches("^[EST]0[1-8]$")) {
            throw new IdInvalidoException("El ID de la estación debe tener el siguiente formato: EST01 hasta EST08.");
        }
    }

    public static void validaArmario(String armario) throws IdInvalidoException {
        if (armario == null || armario.isBlank()) {
            throw new IdInvalidoException("El ID del armario no puede estar vacío.");
        }

        if (!armario.matches("^[ARM]0[1-6]$")) {
            throw new IdInvalidoException("El ID del armario debe tener el siguiente formato: ARM01 hasta ARM06.");
        }
    }

    public static void validaBalda(String balda) throws IdInvalidoException {
        if (balda == null || balda.isBlank()) {
            throw new IdInvalidoException("El ID de la balda no puede estar vacío.");
        }

        if (!balda.matches("^[1-6]")) {
            throw new IdInvalidoException("El ID de la balda debe tener el siguiente formato: 1 hasta 6");
        }
    }

    public static void validaPc(String pc) throws IdInvalidoException {
        if (pc == null || pc.isBlank()) {
            throw new IdInvalidoException("Debe introducir un ID de PC.");
        }
        try {
            int valor = Integer.parseInt(pc.trim());
            if (valor < 0 || valor > 99) {
                throw new IdInvalidoException("El ID del PC debe ser entre 0 y 99.");
            }
        } catch (NumberFormatException e) {
            throw new IdInvalidoException("El ID del PC debe ser un número entero válido.");
        }
    }

    public static void validaFecha(String fecha) throws FechaInvalidaException {
        if (fecha == null || fecha.isBlank()) {
            throw new FechaInvalidaException("Debe introducir una fecha.");
        }
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDateTime.parse(fecha, formato);
        } catch (DateTimeParseException e) {
            throw new FechaInvalidaException("La fecha debe tener el formato dd-MM-yyyy.");
        }
    }

    
    
    public static void validaLongitud(String longitud) throws LongitudInvalidaException{
        if (longitud == null || longitud.isBlank()) {
            throw new LongitudInvalidaException("Debe introducir una longitud.");
        }
        try {
            double valor = Double.parseDouble(longitud.trim());
            if (valor < 0 || valor > 99) {
                throw new LongitudInvalidaException("La longitud debe ser entre 0 y 99.");
            }
        } catch (NumberFormatException e) {
            throw new LongitudInvalidaException("La longitud debe ser un número decimal válido.");
        }
    }
    
    public static void validaConector(String conector) throws ConectorInvalidoException{
        if (conector == null || conector.isBlank()) {
            throw new ConectorInvalidoException("El conector no puede estar vacío.");
        }
        if (conector.length() < 2 || conector.length() > 50) {
            throw new ConectorInvalidoException("El conector debe tener entre 2 y 50 caracteres.");
        }
    }
}
