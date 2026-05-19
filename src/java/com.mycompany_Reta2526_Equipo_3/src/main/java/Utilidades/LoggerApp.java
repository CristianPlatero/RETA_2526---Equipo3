/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import javax.swing.JTextArea;

public class LoggerApp {

    private static JTextArea areaLogs;

    public static void inicializar(JTextArea area) {
        areaLogs = area;
    }

    public static void log(String mensaje) {

        if(areaLogs == null) return;

        String hora = java.time.LocalTime.now()
                .withNano(0)
                .toString();

        areaLogs.append("[" + hora + "] " + mensaje + "\n");

        areaLogs.setCaretPosition(
                areaLogs.getDocument().getLength());
    }
}