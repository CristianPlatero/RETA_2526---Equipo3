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
import Excepciones.LongitudInvalidaException;
import Excepciones.NombreInvalidoException;
import Interfaz.Marco;
import Objetos.Cableado;
import Objetos.Componentes;
import Objetos.Equipos_en_red;
import Objetos.Herramientas;
import Objetos.MaterialInventario;
import Objetos.Material_Fungible;
import Objetos.Perifericos;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author DAW102
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IdInvalidoException, SQLException, NombreInvalidoException, DescripcionInvalidaException, EstadoInvalidoException, CantidadInvalidaException, FechaInvalidaException, CategoriaInvalidaException, LongitudInvalidaException {
        //Marco miMarco = new Marco();
        AdministradorDAO ad = new AdministradorDAO();
//       
//        ArrayList<Integer> pcs= new  ArrayList<>();
//        pcs.add(1);
//        pcs.add(2);
//        Perifericos p = new Perifericos("no", "des", "OBSOLETO", "1", "ARM02", "1", "2001-12-10", "obs", "INALAMBRICA",pcs);   
//        ad.guardarMaterial(p);
//        
        Componentes c = new Componentes("nombre", "descripcion", "OBSOLETO", "2", "EST01", "", "2012-12-12", "observaciones", "1");
        ad.guardarMaterial(c);
//        
//        Equipos_en_red er = new Equipos_en_red("nombre", "descripcion", "OPERATIVO", "3", "ARM02", "2", "12-12-2010", "observaciones", "4");
//        ad.guardarMaterial(er);
//        
//        Cableado ca = new Cableado("nombre", "descripcion", "OPERATIVO", "3", "ARM02", "2", "12-12-2010", "observaciones", "1.5", "conector1", "conector2");
//        ad.guardarMaterial(ca);
//        
//            
//            Herramientas h = new Herramientas("nombre", "descripcion", "OPERATIVO", "3", "ARM02", "2", "12-12-2010", "observaciones", "SOLDADURA");
//        ad.guardarMaterial(h);
//
//
//        Material_Fungible mf = new Material_Fungible("nombre", "descripcion", "OPERATIVO", "1", "ARM02", "1", "12-12-2010", "observaciones", "LLENO");
//        ad.guardarMaterial(mf);
        
    //   ad.eliminarMaterial(11);



//       var lista = ad.listarMaterial();
//       
//       if(lista.isEmpty()){
//           
//       }else{
//           for(MaterialInventario m : lista){
//               System.out.println(m);
//           }
//       }
       
//       var lista = ad.listarPerifericos();
//       
//       if(lista.isEmpty()){
//           
//       }else{
//           for(Perifericos m : lista){
//               System.out.println(m);
//           }
//       }
       
       var lista = ad.listarComponentes();
       
       if(lista.isEmpty()){
           
       }else{
           for(Componentes m : lista){
               System.out.println(m);
           }
       }
       
    }
    
}
