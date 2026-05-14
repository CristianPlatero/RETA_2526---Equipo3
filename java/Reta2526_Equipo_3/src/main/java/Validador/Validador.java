/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Validador;

import Excepciones.CantidadInvalidaException;
import Excepciones.CategoriaInvalidaException;
import Excepciones.ConectorInvalidoException;
import Excepciones.DescripcionInvalidaException;
import Excepciones.EstadoInvalidoException;
import Excepciones.FechaInvalidaException;
import Excepciones.IdInvalidoException;
import Excepciones.LongitudInvalidaException;
import Excepciones.MovilidadInvalidaException;
import Excepciones.NombreInvalidoException;
import Excepciones.TipoInvalidoException;
import java.time.LocalDate;
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

    public static void validaTipo(String tipo) throws TipoInvalidoException, DescripcionInvalidaException {
        tipo = tipo.toUpperCase().trim();
        if (tipo == null || tipo.isBlank()) {
            throw new DescripcionInvalidaException("El estado no puede estar vacio.");
        }
        switch (tipo) {
            case "PRUEBAS" -> {

            }
            case "EN_USO" -> {

            }
            case "REPARACION" -> {

            }
            default -> {

                throw new TipoInvalidoException("El estado debe ser EN_USO, PRUEBAS O REPARACION");
            }
        }

    }

    public static void validaCategoria(String categoria) throws CategoriaInvalidaException, DescripcionInvalidaException {
        categoria = categoria.toUpperCase().trim();
        if (categoria == null || categoria.isBlank()) {
            throw new DescripcionInvalidaException("La categoría no puede estar vacía.");
        }
        switch (categoria) {
            case "PORTATIL" -> {

            }
            case "SOBREMESA" -> {

            }

            default -> {

                throw new CategoriaInvalidaException("La categoría debe ser PORTATIL O SOBREMESA");
            }
        }

    }

    public static void validaMovilidad(String movilidad) throws MovilidadInvalidaException, DescripcionInvalidaException {
        movilidad = movilidad.toUpperCase().trim();
        if (movilidad == null || movilidad.isBlank()) {
            throw new DescripcionInvalidaException("La movilidad no puede estar vacía.");
        }
        switch (movilidad) {
            case "FIJA" -> {

            }
            case "MOVIL" -> {

            }

            default -> {

                throw new MovilidadInvalidaException("La movilidad debe ser MOVIL O FIJA");
            }
        }

    }

    public static void validaEstadoFungible(String estado) throws EstadoInvalidoException, DescripcionInvalidaException {
        estado = estado.toUpperCase().trim();
        if (estado == null || estado.isBlank()) {
            throw new DescripcionInvalidaException("El estado no puede estar vacio.");
        }
        switch (estado) {
            case "LLENO" -> {

            }
            case "VACIO" -> {

            }
            case "MEDIO" -> {

            }
            default -> {

                throw new EstadoInvalidoException("El estado debe ser LLENO, VACIO O MEDIO");
            }
        }

    }

    public static void validaTipoHerramienta(String tipoHerramienta) throws CategoriaInvalidaException, DescripcionInvalidaException {
        tipoHerramienta = tipoHerramienta.toUpperCase().trim();
        if (tipoHerramienta == null || tipoHerramienta.isBlank()) {
            throw new DescripcionInvalidaException("El tipo de herramienta no puede estar vacio.");
        }
        switch (tipoHerramienta) {
            case "SOLDADURA" -> {

            }
            case "GENERALES" -> {

            }

            default -> {

                throw new CategoriaInvalidaException("El tipo de herramienta debe ser SOLDADURA O GENERALES");
            }
        }

    }

    public static void validaTipoConexion(String conexion) throws CategoriaInvalidaException, DescripcionInvalidaException {
        conexion = conexion.toUpperCase().trim();
        if (conexion == null || conexion.isBlank()) {
            throw new DescripcionInvalidaException("El tipo de conexion no puede estar vacio.");
        }
        switch (conexion) {
            case "INALAMBRICA" -> {

            }
            case "CABLE" -> {

            }

            default -> {

                throw new CategoriaInvalidaException("El tipo de conexion debe ser INALAMBRICA O CABLE");
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

    public static void validaNumPuertos(String numPuertos) throws CantidadInvalidaException {
        if (numPuertos == null || numPuertos.isBlank()) {
            throw new CantidadInvalidaException("Debe introducir un numero de puertos.");
        }
        try {
            int valor = Integer.parseInt(numPuertos.trim());
            if (valor < 0 || valor > 24) {
                throw new CantidadInvalidaException("El numero de puertos debe ser entre 0 y 24.");
            }
        } catch (NumberFormatException e) {
            throw new CantidadInvalidaException("El numero de puertos debe ser un número entero válido.");
        }
    }

    public static void validaEstacion(String estacion) throws IdInvalidoException {
        if (estacion == null || estacion.isBlank()) {
            throw new IdInvalidoException("El ID de la estación no puede estar vacío.");
        }

        if (!estacion.matches("^(EST)0[1-8]$")) {
            throw new IdInvalidoException("El ID de la estación debe tener el siguiente formato: EST01 hasta EST08.");
        }
    }

    public static void validaArmario(String armario) throws IdInvalidoException {
        if (armario == null || armario.isBlank()) {
            throw new IdInvalidoException("El ID del armario no puede estar vacío.");
        }
// ! CAMBIAR CHECK A MAYUSCULAS EN BASE DE DATOS MYSQL
        if (!armario.matches("^(ARM)0[1-6]$")) {
            throw new IdInvalidoException("El ID del armario debe tener el siguiente formato: ARM01 hasta ARM06.");
        }
    }

    public static void validaBalda(String ubi, String balda) throws IdInvalidoException {
        
       
        
        if (ubi.matches("^(ARM)0[1-6]$")) {
            if (balda == null) {
                throw new IdInvalidoException("El ID de la balda no puede estar vacío, cuando la ubicacion es un armario");
            }
            if (!balda.matches("^[1-6]")) {
                throw new IdInvalidoException("El ID de la balda debe tener el siguiente formato: 1 hasta 6");
            }
        }

        if (ubi.matches("^(EST)0[1-8]$")) {
            if (balda != null  ) {
                throw new IdInvalidoException("El ID de la balda debe estar vacío, cuando la ubicacion es una estacion");
                
            }

        }

    }

    public static void validaUbi(String ubi) throws IdInvalidoException {
        if (ubi == null || ubi.isBlank()) {
            throw new IdInvalidoException("El ID de la balda no puede estar vacío.");
        }

        if (!ubi.matches("^(ARM)0[1-6]$") && !ubi.matches("^(EST)0[1-8]$")) {
            throw new IdInvalidoException("El ID de la ubicacion debe tener el siguiente formato: ARM01 hasta ARM06 o EST01 hasta EST08");
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
            LocalDate.parse(fecha, formato);
        } catch (DateTimeParseException e) {
            throw new FechaInvalidaException("La fecha debe tener el formato dd-MM-yyyy.");
        }
    }

    public static void validaLongitud(String longitud) throws LongitudInvalidaException {
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

    public static void validaConector(String conector) throws ConectorInvalidoException {
        if (conector == null || conector.isBlank()) {
            throw new ConectorInvalidoException("El conector no puede estar vacío.");
        }
        if (conector.length() < 2 || conector.length() > 50) {
            throw new ConectorInvalidoException("El conector debe tener entre 2 y 50 caracteres.");
        }
    }
}
