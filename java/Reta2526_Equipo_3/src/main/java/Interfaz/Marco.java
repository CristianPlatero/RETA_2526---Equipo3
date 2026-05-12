/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaz;

import javax.swing.*;
import java.awt.*;

/**
 * Clase central de la interfaz gráfica
 *
 * @author Usuario
 */
public class Marco extends JFrame {

    private JPanel panelContenido;
    private CardLayout cardLayout;

    public Marco() {

        // Settings del marco principal
        setTitle("Inventario Taller");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Cabecera fija
        JPanel cabecera = new JPanel();
        cabecera.setLayout(new BorderLayout());
        //aqui va la imagen o banner

        //menu lateral fijo
        JPanel menu = new JPanel();

        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        JButton btn1 = new JButton("Acción 1");
        JButton btn2 = new JButton("Acción 2");
        JButton btn3 = new JButton("Acción 3");
        JButton btn4 = new JButton("Acción 4");
        btn1.setSize(200,1000);
        //aqui podemos editar los botones con btn1.setPROPERTY que corresponda
        menu.add(btn1);
        menu.add(btn2);
        menu.add(btn3);
        menu.add(btn4);

        // Area de trabajo dinámica
        panelContenido = new JPanel();
        panelContenido.setBackground(Color.lightGray);

        // Contenido dinámico
        cardLayout = new CardLayout();

        panelContenido = new JPanel(cardLayout);

        // TARJETA 1
        JPanel Accion1 = new JPanel();
        //aqui van todas las acciones que necesitemos de nuestro programa
        Accion1.setBackground(Color.WHITE);
        Accion1.add(new JLabel("Pantalla Acción1"));

        // TARJETA 2
        JPanel Accion2 = new JPanel();
        //aqui van todas las acciones que necesitemos de nuestro programa
        Accion2.setBackground(Color.BLUE);
        Accion2.add(new JLabel("Pantalla Acción2"));

        panelContenido.add(Accion1, "Accion1");
        panelContenido.add(Accion2, "Accion2");

        // Eventos de botones
        btn1.addActionListener(e -> {
            cardLayout.show(panelContenido, "Accion1"); // nos lleva a la TARJETA 1
        });

        btn2.addActionListener(e -> {
            cardLayout.show(panelContenido, "Accion2"); // nos lleva a la TARJETA 2
        });

        // construye los 3 paneles
        add(cabecera, BorderLayout.NORTH);
        add(menu, BorderLayout.WEST);
        add(panelContenido, BorderLayout.CENTER);

        // crear pantalla, siempre al final o no renderiza bien
        setVisible(true);
    }

}
