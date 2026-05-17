/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import Interfaz.InventarioApp;
import Interfaz.LoginDialog;
import Interfaz.Rol;
import Usuarios.InicializadorUsuarios;
import Utilidades.ExportadorCSV;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class App {

    public static void main(String[] args) {

        // Inicializamos los usuarios para su login correcto
        InicializadorUsuarios.inicializarUsuariosBase();

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            LoginDialog login = new LoginDialog(null);
            login.setVisible(true);

            Rol rol = login.getRolAutenticado();

            if (rol != null) {
                //ExportadorCSV.exportarTodo();
                InventarioApp app = new InventarioApp(rol);
                app.setVisible(true);

            }
        });

    }
}
