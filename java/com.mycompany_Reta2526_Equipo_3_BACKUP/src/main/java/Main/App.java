/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import InterfazNueva.InventarioApp;
import InterfazNueva.LoginDialog;
import InterfazNueva.Rol;
import Usuarios.Administrador;
import Usuarios.GestionUsuarios;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class App {

    public static void main(String[] args) {
        GestionUsuarios gestor = new GestionUsuarios();

String hash = org.mindrot.jbcrypt.BCrypt.hashpw(
    "admin123",
    org.mindrot.jbcrypt.BCrypt.gensalt()
);

Administrador admin = new Administrador("admin", hash);

gestor.registrarUsuario(admin);
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

                InventarioApp app = new InventarioApp(rol);
                app.setVisible(true);

            }
        });
    }
}