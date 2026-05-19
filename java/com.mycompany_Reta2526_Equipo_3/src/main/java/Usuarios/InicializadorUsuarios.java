/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Usuarios;

import Utilidades.LoggerApp;

import java.io.File;

public class InicializadorUsuarios {

    private static final String ARCHIVO ="usuarios.dat";

    public static void inicializarUsuariosBase() {

        File file = new File(ARCHIVO);
        // Si YA existe, no hacemos nada
        if(file.exists()) {

            LoggerApp.log("ℹ usuarios.dat ya existe.");
            return;
        }

        LoggerApp.log("⚙ Creando usuarios iniciales...");

        GestionUsuarios gestor =new GestionUsuarios();

        // ─────────────────────────────
        // ADMIN
        // contraseña: admin123
        // ─────────────────────────────

        String hashAdmin =org.mindrot.jbcrypt.BCrypt.hashpw("admin123",org.mindrot.jbcrypt.BCrypt.gensalt());
        Administrador admin = new Administrador("admin",hashAdmin);
        gestor.registrarUsuario(admin);

        // ─────────────────────────────
        // PROFESOR
        // contraseña: profesor123
        // ─────────────────────────────

        String hashProfesor =org.mindrot.jbcrypt.BCrypt.hashpw("profesor123",org.mindrot.jbcrypt.BCrypt.gensalt());
        Profesor profesor = new Profesor("profesor", hashProfesor);
        gestor.registrarUsuario(profesor);
        LoggerApp.log("✅ Usuarios iniciales creados.");
    }
}
