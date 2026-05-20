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
        InicializadorUsuarios.inicializarUsuariosBase();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(App::mostrarLogin);
    }

    // Se llama al arrancar y cada vez que el usuario cierra sesión
    public static void mostrarLogin() {
        LoginDialog login = new LoginDialog(null);
        login.setVisible(true); // bloquea hasta que el diálogo se cierra

        Rol rol = login.getRolAutenticado();

        if (rol == null) {
            System.exit(0); // cerró el login sin entrar → fin del programa
        }

        ExportadorCSV.exportarTodo();
        new InventarioApp(rol).setVisible(true);
        // InventarioApp al cerrar sesión llamará a App.mostrarLogin() de nuevo
    }
}
