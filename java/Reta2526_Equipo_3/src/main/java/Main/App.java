/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import DAO.AdministradorDAO;
import Excepciones.CantidadInvalidaException;
import Excepciones.CategoriaInvalidaException;
import Excepciones.DescripcionInvalidaException;
import Excepciones.EstadoInvalidoException;
import Excepciones.FechaInvalidaException;
import Excepciones.IdInvalidoException;
import Excepciones.NombreInvalidoException;
import Interfaz.Marco;
import Objetos.Perifericos;
import java.sql.SQLException;

/**
 *
 * @author DAW102
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IdInvalidoException, SQLException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException, CategoriaInvalidaException {
        //Marco miMarco = new Marco();
        AdministradorDAO ad = new AdministradorDAO();
        Perifericos p = new Perifericos("1", "no", "des", "OBSOLETO", "1", "ARM02", "1", "12-12-2010", "obs", "1", "INALAMBRICA");
        ad.guardarPeriferico(p);
        
        
        
    }
    
}
