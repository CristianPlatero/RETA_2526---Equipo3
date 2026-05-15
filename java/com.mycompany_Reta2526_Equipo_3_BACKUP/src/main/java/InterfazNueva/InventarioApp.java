package InterfazNueva;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * =====================================================================
 *  VENTANA PRINCIPAL - Aplicación de Gestión de Inventario
 * =====================================================================
 *  Estructura de la interfaz:
 *
 *  +--------------------------------------------------+
 *  |              BANNER (imagen/logo)   [rol] [exit] |
 *  +------------+-------------------------------------+
 *  |            |                                     |
 *  |   MENÚ     |        ÁREA DE TRABAJO              |
 *  |  LATERAL   |   (cambia según el botón pulsado)   |
 *  | (según rol)|                                     |
 *  +------------+-------------------------------------+
 *
 *  Roles y permisos:
 *  ┌──────────────────┬─────────────────────────────────────────────┐
 *  │ ADMINISTRADOR    │ Listar · Buscar · Filtrar · Informes +      │
 *  │                  │ Añadir · Modificar · Eliminar               │
 *  ├──────────────────┼─────────────────────────────────────────────┤
 *  │ PROFESOR         │ Listar · Buscar · Filtrar · Informes        │
 *  │                  │ (solo lectura, sin edición de BD)           │
 *  └──────────────────┴─────────────────────────────────────────────┘
 *
 *  El constructor recibe el Rol del usuario ya autenticado (viene del
 *  LoginDialog) y construye el menú lateral en consecuencia.
 * =====================================================================
 */
public class InventarioApp extends JFrame {

    // ── Dimensiones ───────────────────────────────────────────────────
    private static final int ANCHO_VENTANA = 900;
    private static final int ALTO_VENTANA  = 600;
    private static final int ANCHO_MENU    = 190;
    private static final int ALTO_BANNER   = 120;

    // ── Paleta de colores ─────────────────────────────────────────────
    private static final Color COLOR_BANNER       = new Color(30,  30,  46);
    private static final Color COLOR_MENU_BG      = new Color(44,  44,  64);
    private static final Color COLOR_MENU_BTN     = new Color(64,  64,  96);
    private static final Color COLOR_MENU_HOVER   = new Color(90,  90, 140);
    private static final Color COLOR_MENU_ACTIVO  = new Color(114, 90, 200);
    private static final Color COLOR_TRABAJO_BG   = new Color(245, 245, 252);
    private static final Color COLOR_TEXTO_CLARO  = Color.WHITE;
    private static final Color COLOR_TEXTO_GRIS   = new Color(180, 180, 200);
    private static final Color COLOR_BADGE_ADMIN  = new Color(114, 90, 200);  // morado
    private static final Color COLOR_BADGE_PROF   = new Color(50, 140, 100);  // verde

    // ── Estado ────────────────────────────────────────────────────────
    private final Rol rolActual;
    private JPanel    areaTrabajoContenedor;
    private JButton   botonActivo = null;

    // ======================================================================
    //  CONSTRUCTOR
    // ======================================================================
    /**
     * @param rol  Rol del usuario autenticado (viene de LoginDialog)
     */
    public InventarioApp(Rol rol) {
        this.rolActual = rol;
        configurarVentana();
        add(crearBanner(),         BorderLayout.NORTH);
        add(crearMenuLateral(),    BorderLayout.WEST);
        areaTrabajoContenedor = crearAreaTrabajo();
        add(areaTrabajoContenedor, BorderLayout.CENTER);
        mostrarPanel(crearPanelBienvenida());
    }

    // ======================================================================
    //  CONFIGURACIÓN DE LA VENTANA
    // ======================================================================
    private void configurarVentana() {
        setTitle("Gestión de Inventario — " + nombreRol());
        setSize(ANCHO_VENTANA, ALTO_VENTANA);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(700, 450));
        setLayout(new BorderLayout());
    }

    private String nombreRol() {
        return (rolActual == Rol.ADMINISTRADOR) ? "Administrador" : "Profesor";
    }

    // ======================================================================
    //  BANNER SUPERIOR
    // ======================================================================
    private JPanel crearBanner() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_BANNER);
        panel.setPreferredSize(new Dimension(ANCHO_VENTANA, ALTO_BANNER));
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_MENU_ACTIVO));

        // ── Hueco para imagen ──────────────────────────────────────────
        // TODO: para añadir tu imagen real sustituye el JLabel por:
        //   ImageIcon icono = new ImageIcon("ruta/imagen.png");
        //   Image img = icono.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        //   JLabel lblImagen = new JLabel(new ImageIcon(img));
        JLabel lblImagen = new JLabel("[TU IMAGEN]", SwingConstants.CENTER);
        lblImagen.setForeground(COLOR_TEXTO_GRIS);
        lblImagen.setFont(new Font("Monospaced", Font.ITALIC, 11));
        lblImagen.setPreferredSize(new Dimension(110, ALTO_BANNER));
        lblImagen.setBorder(BorderFactory.createDashedBorder(COLOR_MENU_HOVER, 2, 4));

        // ── Título + badge de rol ──────────────────────────────────────
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        panelTitulo.setOpaque(false);
        panelTitulo.setBorder(new EmptyBorder(16, 20, 10, 0));

        JLabel lblTitulo = new JLabel("Sistema de Inventario");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(COLOR_TEXTO_CLARO);

        JLabel lblSub = new JLabel("Gestión de productos · CRUD");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(COLOR_TEXTO_GRIS);

        // Pastilla coloreada que indica el rol activo
        JLabel lblBadge = new JLabel("  " + nombreRol().toUpperCase() + "  ");
        lblBadge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblBadge.setForeground(COLOR_TEXTO_CLARO);
        lblBadge.setOpaque(true);
        lblBadge.setBackground(rolActual == Rol.ADMINISTRADOR ? COLOR_BADGE_ADMIN : COLOR_BADGE_PROF);
        lblBadge.setBorder(new EmptyBorder(3, 8, 3, 8));
        JPanel panelBadge = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 6));
        panelBadge.setOpaque(false);
        panelBadge.add(lblBadge);

        panelTitulo.add(lblTitulo);
        panelTitulo.add(Box.createVerticalStrut(4));
        panelTitulo.add(lblSub);
        panelTitulo.add(panelBadge);

        // ── Botón cerrar sesión ────────────────────────────────────────
        JButton btnLogout = new JButton("⏻  Cerrar sesión");
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnLogout.setForeground(COLOR_TEXTO_GRIS);
        btnLogout.setBackground(new Color(55, 55, 75));
        btnLogout.setBorderPainted(false);
        btnLogout.setFocusPainted(false);
        btnLogout.setOpaque(true);
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> cerrarSesion());

        JPanel panelLogout = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelLogout.setOpaque(false);
        panelLogout.add(btnLogout);

        panel.add(lblImagen,    BorderLayout.WEST);
        panel.add(panelTitulo,  BorderLayout.CENTER);
        panel.add(panelLogout,  BorderLayout.EAST);
        return panel;
    }

    /** Cierra esta ventana y vuelve al login sin reiniciar el proceso. */
private void cerrarSesion() {

    int conf = JOptionPane.showConfirmDialog(
        this,
        "¿Deseas cerrar la sesión actual?",
        "Cerrar sesión",
        JOptionPane.YES_NO_OPTION
    );

    if (conf == JOptionPane.YES_OPTION) {

        dispose();

        LoginDialog login = new LoginDialog(null);
        login.setVisible(true);

    }
}

    // ======================================================================
    //  MENÚ LATERAL  (diferente según el rol)
    // ======================================================================
    private JPanel crearMenuLateral() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_MENU_BG);
        panel.setPreferredSize(new Dimension(ANCHO_MENU, 0));
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, COLOR_MENU_BTN));

        // ── Sección CONSULTA: visible para AMBOS roles ─────────────────
        panel.add(crearEtiquetaSeccion("CONSULTA"));
        panel.add(crearBotonMenu("📋  Listar todo",     e -> mostrarPanel(crearPanelListar())));
        panel.add(crearBotonMenu("🔍  Buscar",          e -> mostrarPanel(crearPanelBuscar())));
        panel.add(crearBotonMenu("🔎  Filtrar",         e -> mostrarPanel(crearPanelFiltrar())));
        panel.add(crearBotonMenu("📄  Generar informe", e -> mostrarPanel(crearPanelInforme())));

        // ── Sección EDICIÓN: visible solo para ADMINISTRADOR ───────────
        if (rolActual == Rol.ADMINISTRADOR) {
            panel.add(Box.createVerticalStrut(6));
            panel.add(crearSeparador());
            panel.add(crearEtiquetaSeccion("EDICIÓN"));
            panel.add(crearBotonMenu("➕  Añadir",      e -> mostrarPanel(crearPanelAnadir())));
            panel.add(crearBotonMenu("✏️  Modificar",   e -> mostrarPanel(crearPanelModificar())));
            panel.add(crearBotonMenu("🗑️  Eliminar",    e -> mostrarPanel(crearPanelEliminar())));
        }

        panel.add(Box.createVerticalStrut(6));
        panel.add(crearSeparador());
        panel.add(crearEtiquetaSeccion("OTROS"));
        panel.add(crearBotonMenu("ℹ️  Acerca de", e -> mostrarPanel(crearPanelBienvenida())));
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JLabel crearEtiquetaSeccion(String texto) {
        JLabel lbl = new JLabel("  " + texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lbl.setForeground(COLOR_TEXTO_GRIS);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBorder(new EmptyBorder(14, 8, 6, 0));
        return lbl;
    }

    private JSeparator crearSeparador() {
        JSeparator sep = new JSeparator();
        sep.setForeground(COLOR_MENU_BTN);
        sep.setMaximumSize(new Dimension(ANCHO_MENU - 20, 1));
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);
        return sep;
    }

    private JButton crearBotonMenu(String texto, ActionListener accion) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(COLOR_TEXTO_CLARO);
        btn.setBackground(COLOR_MENU_BG);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(ANCHO_MENU, 42));
        btn.setPreferredSize(new Dimension(ANCHO_MENU, 42));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setBorder(new EmptyBorder(0, 16, 0, 0));

        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                if (btn != botonActivo) btn.setBackground(COLOR_MENU_HOVER);
            }
            @Override public void mouseExited(MouseEvent e) {
                if (btn != botonActivo) btn.setBackground(COLOR_MENU_BG);
            }
        });
        btn.addActionListener(e -> {
            if (botonActivo != null) botonActivo.setBackground(COLOR_MENU_BG);
            botonActivo = btn;
            btn.setBackground(COLOR_MENU_ACTIVO);
            accion.actionPerformed(e);
        });
        return btn;
    }

    // ======================================================================
    //  ÁREA DE TRABAJO
    // ======================================================================
    private JPanel crearAreaTrabajo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_TRABAJO_BG);
        return panel;
    }

    /** Intercambia el panel visible en el área de trabajo. */
    private void mostrarPanel(JPanel nuevoPanel) {
        areaTrabajoContenedor.removeAll();
        areaTrabajoContenedor.add(nuevoPanel, BorderLayout.CENTER);
        areaTrabajoContenedor.revalidate();
        areaTrabajoContenedor.repaint();
    }

    // ======================================================================
    //  PANELES DE TRABAJO
    // ======================================================================

    /** Pantalla inicial con descripción de permisos según rol */
    private JPanel crearPanelBienvenida() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_TRABAJO_BG);

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setOpaque(false);

        JLabel lblEmoji = new JLabel(
            rolActual == Rol.ADMINISTRADOR ? "🛠️" : "📚", SwingConstants.CENTER);
        lblEmoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        lblEmoji.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblBien = new JLabel("Bienvenido, " + nombreRol());
        lblBien.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblBien.setForeground(new Color(60, 60, 80));
        lblBien.setAlignmentX(Component.CENTER_ALIGNMENT);

        String permisos = (rolActual == Rol.ADMINISTRADOR)
            ? "Acceso completo: consulta, edición y generación de informes."
            : "Acceso de solo lectura: listar, buscar, filtrar y generar informes.";
        JLabel lblPermisos = new JLabel("<html><center>" + permisos + "</center></html>");
        lblPermisos.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblPermisos.setForeground(new Color(130, 130, 160));
        lblPermisos.setAlignmentX(Component.CENTER_ALIGNMENT);

        contenido.add(lblEmoji);
        contenido.add(Box.createVerticalStrut(12));
        contenido.add(lblBien);
        contenido.add(Box.createVerticalStrut(8));
        contenido.add(lblPermisos);
        panel.add(contenido);
        return panel;
    }

    // ── Paneles de CONSULTA (ambos roles) ─────────────────────────────

    /** READ: tabla con todos los registros */
    private JPanel crearPanelListar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_TRABAJO_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(crearTituloPanel("Listado de productos"), BorderLayout.NORTH);

        // TODO: sustituir por datos reales de tu colección/BD
        String[] cols  = {"ID", "Nombre", "Categoría", "Cantidad", "Precio (€)"};
        Object[][] datos = {
            {"001", "Teclado mecánico",  "Periféricos", 15, "79,99"},
            {"002", "Monitor 24\"",      "Pantallas",    8, "249,00"},
            {"003", "Ratón inalámbrico", "Periféricos", 30, "34,50"},
            {"004", "Cable HDMI 2m",     "Cables",      50, "8,99"},
        };
        panel.add(new JScrollPane(crearTabla(datos, cols)), BorderLayout.CENTER);

        JButton btnActualizar = crearBotonAccion("Actualizar lista");
        btnActualizar.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Aquí llamarías a tu método listar()"));
        JPanel sur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sur.setOpaque(false);
        sur.add(btnActualizar);
        panel.add(sur, BorderLayout.SOUTH);
        return panel;
    }

    /** READ por ID */
    private JPanel crearPanelBuscar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_TRABAJO_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(crearTituloPanel("Buscar producto por ID"), BorderLayout.NORTH);

        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        barra.setOpaque(false);
        barra.add(etiqueta("ID:"));
        JTextField txtId = new JTextField(10);
        txtId.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtId.setPreferredSize(new Dimension(120, 30));
        JButton btnBuscar = crearBotonAccion("Buscar");
        barra.add(txtId);
        barra.add(btnBuscar);

        JTextArea area = new JTextArea(8, 40);
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 13));
        area.setBackground(new Color(230, 230, 240));
        area.setText("El resultado aparecerá aquí...");

        btnBuscar.addActionListener(e -> {
            String id = txtId.getText().trim();
            if (id.isEmpty()) { area.setText("Introduce un ID válido."); return; }
            // TODO: reemplazar por buscarPorId(id)
            area.setText("Resultado para ID \"" + id + "\":\n\n"
                + "  Nombre    : Producto de ejemplo\n"
                + "  Categoría : Ejemplo\n"
                + "  Cantidad  : 10\n"
                + "  Precio    : 9,99 €");
        });

        JPanel centro = new JPanel(new BorderLayout(0, 12));
        centro.setOpaque(false);
        centro.add(barra, BorderLayout.NORTH);
        centro.add(new JScrollPane(area), BorderLayout.CENTER);
        panel.add(centro, BorderLayout.CENTER);
        return panel;
    }

    /** FILTRAR: búsqueda por categoría, stock y precio */
    private JPanel crearPanelFiltrar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_TRABAJO_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(crearTituloPanel("Filtrar productos"), BorderLayout.NORTH);

        JPanel filtros = new JPanel(new GridBagLayout());
        filtros.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        filtros.add(etiqueta("Categoría:"), gbc);
        gbc.gridx = 1;
        String[] cats = {"Todas", "Periféricos", "Pantallas", "Cables", "Otros"};
        JComboBox<String> comboCat = new JComboBox<>(cats);
        comboCat.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        filtros.add(comboCat, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        filtros.add(etiqueta("Cantidad mínima:"), gbc);
        gbc.gridx = 1;
        JSpinner spinnerMin = new JSpinner(new SpinnerNumberModel(0, 0, 9999, 1));
        spinnerMin.setPreferredSize(new Dimension(80, 28));
        filtros.add(spinnerMin, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        filtros.add(etiqueta("Precio máximo (€):"), gbc);
        gbc.gridx = 1;
        JTextField txtPrecio = new JTextField("999,99", 10);
        txtPrecio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        filtros.add(txtPrecio, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JButton btnFiltrar = crearBotonAccion("Aplicar filtros");
        filtros.add(btnFiltrar, gbc);

        String[] cols = {"ID", "Nombre", "Categoría", "Cantidad", "Precio (€)"};
        JTable tablaRes = crearTabla(new Object[][]{}, cols);
        JScrollPane scroll = new JScrollPane(tablaRes);

        btnFiltrar.addActionListener(e ->
            // TODO: filtrar tu colección y actualizar el modelo de la tabla
            JOptionPane.showMessageDialog(this,
                "Filtros:\n  Categoría : " + comboCat.getSelectedItem()
                + "\n  Cant. mín : " + spinnerMin.getValue()
                + "\n  Precio máx: " + txtPrecio.getText() + " €\n\n"
                + "(Aquí actualizarías la tabla con resultados reales)")
        );

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, filtros, scroll);
        split.setDividerLocation(180);
        split.setOpaque(false);
        split.setBorder(null);
        panel.add(split, BorderLayout.CENTER);
        return panel;
    }

    /** INFORME: previsualización y exportación a .txt */
    private JPanel crearPanelInforme() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_TRABAJO_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(crearTituloPanel("Generar informe"), BorderLayout.NORTH);

        JPanel opciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        opciones.setOpaque(false);
        opciones.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 215)),
            "Tipo de informe", 0, 0,
            new Font("Segoe UI", Font.BOLD, 12), new Color(80, 80, 100)));
        ButtonGroup grupo = new ButtonGroup();
        JRadioButton rbComp  = new JRadioButton("Inventario completo", true);
        JRadioButton rbStock = new JRadioButton("Productos con stock bajo");
        JRadioButton rbCat   = new JRadioButton("Agrupado por categoría");
        for (JRadioButton rb : new JRadioButton[]{rbComp, rbStock, rbCat}) {
            rb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            rb.setOpaque(false);
            grupo.add(rb);
            opciones.add(rb);
        }

        JTextArea areaPreview = new JTextArea();
        areaPreview.setEditable(false);
        areaPreview.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaPreview.setBackground(new Color(230, 230, 242));
        areaPreview.setText("Pulsa 'Generar' para previsualizar el informe aquí.");

        JButton btnGenerar  = crearBotonAccion("Generar");
        JButton btnExportar = crearBotonAccion("Exportar .txt");
        btnExportar.setEnabled(false);

        btnGenerar.addActionListener(e -> {
            String tipo = rbComp.isSelected() ? "Inventario completo"
                        : rbStock.isSelected() ? "Stock bajo" : "Por categoría";
            // TODO: generar el informe real a partir de tu colección
            areaPreview.setText(
                "======================================\n"
                + "  INFORME: " + tipo + "\n"
                + "  Fecha  : 15/05/2026\n"
                + "======================================\n\n"
                + "  001  Teclado mecánico   Periféricos  15 uds   79,99 €\n"
                + "  002  Monitor 24\"        Pantallas     8 uds  249,00 €\n"
                + "  003  Ratón inalámbrico  Periféricos  30 uds   34,50 €\n"
                + "  004  Cable HDMI 2m      Cables       50 uds    8,99 €\n\n"
                + "  Total productos: 4\n"
                + "======================================\n");
            btnExportar.setEnabled(true);
        });

        btnExportar.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new java.io.File("informe.txt"));
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                // TODO: usar FileWriter para escribir areaPreview.getText()
                JOptionPane.showMessageDialog(this,
                    "Informe guardado en:\n" + chooser.getSelectedFile().getAbsolutePath()
                    + "\n\n(Aquí usarías FileWriter para escribir el texto)");
            }
        });

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.setOpaque(false);
        botones.add(btnGenerar);
        botones.add(btnExportar);

        panel.add(opciones,           BorderLayout.NORTH);
        panel.add(new JScrollPane(areaPreview), BorderLayout.CENTER);
        panel.add(botones,            BorderLayout.SOUTH);
        return panel;
    }

    // ── Paneles de EDICIÓN (solo ADMINISTRADOR) ────────────────────────

    /** CREATE */
    private JPanel crearPanelAnadir() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_TRABAJO_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(crearTituloPanel("Añadir nuevo producto"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        String[] etiquetas = {"Nombre:", "Categoría:", "Cantidad:", "Precio (€):"};
        JTextField[] campos = new JTextField[etiquetas.length];
        for (int i = 0; i < etiquetas.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0;
            form.add(etiqueta(etiquetas[i]), gbc);
            gbc.gridx = 1; gbc.weightx = 1;
            campos[i] = new JTextField(20);
            campos[i].setFont(new Font("Segoe UI", Font.PLAIN, 13));
            campos[i].setPreferredSize(new Dimension(200, 30));
            form.add(campos[i], gbc);
        }

        JPanel env = new JPanel(new FlowLayout(FlowLayout.LEFT));
        env.setOpaque(false);
        env.add(form);
        panel.add(env, BorderLayout.CENTER);

        JButton btnGuardar = crearBotonAccion("Guardar producto");
        btnGuardar.addActionListener(e -> {
            String nombre = campos[0].getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // TODO: llamar a tu método añadir(new Producto(...))
            JOptionPane.showMessageDialog(this,
                "Producto \"" + nombre + "\" guardado.\n(Aquí llamarías a añadir())");
            for (JTextField c : campos) c.setText("");
        });

        JPanel sur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sur.setOpaque(false);
        sur.add(btnGuardar);
        panel.add(sur, BorderLayout.SOUTH);
        return panel;
    }

    /** UPDATE */
    private JPanel crearPanelModificar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_TRABAJO_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(crearTituloPanel("Modificar producto"), BorderLayout.NORTH);

        JPanel barraId = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        barraId.setOpaque(false);
        barraId.add(etiqueta("ID a modificar:"));
        JTextField txtId = new JTextField(8);
        txtId.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtId.setPreferredSize(new Dimension(100, 30));
        JButton btnCargar = crearBotonAccion("Cargar");
        barraId.add(txtId);
        barraId.add(btnCargar);

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill   = GridBagConstraints.HORIZONTAL;
        String[] etiquetas = {"Nombre:", "Categoría:", "Cantidad:", "Precio (€):"};
        JTextField[] campos = new JTextField[etiquetas.length];
        for (int i = 0; i < etiquetas.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0;
            form.add(etiqueta(etiquetas[i]), gbc);
            gbc.gridx = 1; gbc.weightx = 1;
            campos[i] = new JTextField(18);
            campos[i].setFont(new Font("Segoe UI", Font.PLAIN, 13));
            campos[i].setEnabled(false);
            form.add(campos[i], gbc);
        }

        btnCargar.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Introduce un ID."); return;
            }
            // TODO: buscarPorId(id) y rellenar con datos reales
            campos[0].setText("Producto cargado"); campos[1].setText("Ejemplo");
            campos[2].setText("5");                campos[3].setText("12,50");
            for (JTextField c : campos) c.setEnabled(true);
        });

        JButton btnGuardar = crearBotonAccion("Guardar cambios");
        btnGuardar.addActionListener(e ->
            // TODO: llamar a tu método modificar(producto)
            JOptionPane.showMessageDialog(this,
                "Cambios guardados.\n(Aquí llamarías a modificar())")
        );

        JPanel centro = new JPanel(new BorderLayout(0, 12));
        centro.setOpaque(false);
        JPanel env = new JPanel(new FlowLayout(FlowLayout.LEFT));
        env.setOpaque(false);
        env.add(form);
        centro.add(barraId, BorderLayout.NORTH);
        centro.add(env,     BorderLayout.CENTER);
        panel.add(centro, BorderLayout.CENTER);

        JPanel sur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sur.setOpaque(false);
        sur.add(btnGuardar);
        panel.add(sur, BorderLayout.SOUTH);
        return panel;
    }

    /** DELETE */
    private JPanel crearPanelEliminar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_TRABAJO_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(crearTituloPanel("Eliminar producto"), BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblAviso = new JLabel("⚠️  Esta acción no se puede deshacer.");
        lblAviso.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblAviso.setForeground(new Color(180, 80, 60));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        centro.add(lblAviso, gbc);

        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        centro.add(etiqueta("ID del producto a eliminar:"), gbc);
        gbc.gridx = 1;
        JTextField txtId = new JTextField(12);
        txtId.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtId.setPreferredSize(new Dimension(140, 30));
        centro.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JButton btnEliminar = new JButton("Eliminar definitivamente");
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEliminar.setBackground(new Color(200, 60, 60));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setOpaque(true);
        btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnEliminar.setPreferredSize(new Dimension(220, 36));
        btnEliminar.addActionListener(e -> {
            String id = txtId.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Introduce un ID válido."); return;
            }
            int conf = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el producto con ID \"" + id + "\"?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            if (conf == JOptionPane.YES_OPTION) {
                // TODO: llamar a tu método eliminar(id)
                JOptionPane.showMessageDialog(this,
                    "Producto eliminado.\n(Aquí llamarías a eliminar(id))");
                txtId.setText("");
            }
        });
        centro.add(btnEliminar, gbc);
        panel.add(centro, BorderLayout.CENTER);
        return panel;
    }

    // ======================================================================
    //  UTILIDADES DE UI
    // ======================================================================
    private JPanel crearTituloPanel(String texto) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(0, 0, 14, 0));
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setForeground(new Color(50, 50, 70));
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(200, 190, 230));
        p.add(lbl, BorderLayout.CENTER);
        p.add(sep, BorderLayout.SOUTH);
        return p;
    }

    private JLabel etiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return lbl;
    }

    private JButton crearBotonAccion(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(COLOR_MENU_ACTIVO);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 34));
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(COLOR_MENU_HOVER); }
            @Override public void mouseExited(MouseEvent e)  { btn.setBackground(COLOR_MENU_ACTIVO); }
        });
        return btn;
    }

    private JTable crearTabla(Object[][] datos, String[] cols) {
        JTable tabla = new JTable(datos, cols);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabla.setSelectionBackground(new Color(200, 190, 240));
        tabla.setGridColor(new Color(220, 220, 230));
        return tabla;
    }
}
