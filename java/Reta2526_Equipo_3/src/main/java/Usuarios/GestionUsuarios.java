/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuarios;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author DAW126
 */
public class GestionUsuarios {

    private static final String ARCHIVO_DAT = "usuarios.dat";

    /**
     *
     * @param nuevoUsuario
     */
    public void registrarUsuario(Usuario nuevoUsuario) {

        ArrayList<Usuario> usuarios = leerUsuarios();
        usuarios.add(nuevoUsuario);
        guardarUsuarios(usuarios);

    }

    /**
     * NOTA IMPORTANTE PARA EL EQUIPO (Desarrollo del Main / Interfaz Swing):
     *
     * Para que el sistema de login funcione correctamente, las contraseñas
     * DEBEN encriptarse en la interfaz/controlador ANTES de crear el objeto
     * Usuario.Ejemplo de uso en tu formulario Swing (Botón Registrar):
     * -------------------------------------------------------------------------
     * String passPlana = txtPassword.getText(); String hashBCrypt =
     * org.mindrot.jbcrypt.BCrypt.hashpw(passPlana,
     * org.mindrot.jbcrypt.BCrypt.gensalt());
     *
     * // Ahora creas el objeto con la contraseña ya protegida y lo mandas al
     * gestor: Usuario nuevo = new Profesor(txtNombre.getText(), hashBCrypt);
     * gestor.registrarUsuario(nuevo);
     * -------------------------------------------------------------------------
   
   
     * Al hacer el LOGIN, pasa la contraseña en TEXTO PLANO.
     *
     * Este gestor se encarga de compararla automáticamente con el Hash del
     * archivo .dat.
     *
     * @param nombre
     */
    public Usuario login(String nombre, String contrasenaPlana) {
        ArrayList<Usuario> usuarios = leerUsuarios();

        for (Usuario u : usuarios) {
            if (u.getNombre().equals(nombre)) {
                if (BCrypt.checkpw(contrasenaPlana, u.getContrasena())) {
                    return u;
                }
            }
        }

        return null; // login fallido

    }

    /**
     *
     * @return
     */
    public ArrayList<Usuario> leerUsuarios() {

        //leemos el archivo .DAT
        File file = new File(ARCHIVO_DAT);
        if (!file.exists()) {

            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));) {

            return (ArrayList<Usuario>) ois.readObject(); // devuelve la lista con sus objetos reales (Administradores y profesores)

        } catch (Exception e) {
            System.out.println("Error al leer el archivo binario: " + e.getMessage());
            return new ArrayList<>();
        }

    }

    private void guardarUsuarios(ArrayList<Usuario> usuarios) {

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DAT));) {

            oos.writeObject(usuarios);

        } catch (IOException ex) {
            System.out.println("Error al guardar en el archivo binario: " + ex.getMessage());

        }

    }

}
