package Interfaz;

// ══════════════════════════════════════════════════════════════════════════════
//  IMPORTS — Aquí indicamos a Java qué clases externas vamos a usar.
//  Sin estos imports el compilador no encontraría las clases.
// ══════════════════════════════════════════════════════════════════════════════
import AccesoBD.AccesoBaseDatos;
import DAO.AdministradorDAO;           // Clase que conecta con la base de datos
import DAO.PcDAO;
import DAO.UbicacionDAO;
import Objetos.MaterialInventario;     // Clase padre de todos los materiales
import Objetos.Perifericos;            // Subclase: periférico (ratón, teclado...)
import Objetos.Cableado;               // Subclase: cable (HDMI, USB...)
import Objetos.Componentes;            // Subclase: componente de PC (RAM, GPU...)
import Objetos.Herramientas;           // Subclase: herramienta (soldador...)
import Objetos.Material_Fungible;      // Subclase: material fungible (pasta térmica...)
import Objetos.Equipos_en_red;         // Subclase: equipo de red (switch, router...)
import Excepciones.*;                  // Todas las excepciones personalizadas del Validador
import Objetos.Pc;
import Utilidades.LoggerApp;

import javax.swing.*;                  // Todo lo visual de Swing: JFrame, JPanel, JButton...
import javax.swing.border.*;           // Bordes especiales: EmptyBorder, TitledBorder...
import java.awt.*;                     // Layouts, colores, fuentes: BorderLayout, Color, Font...
import java.awt.event.*;               // Eventos de ratón y teclado: ActionListener, MouseAdapter...
import java.awt.Desktop;               // Permite abrir el navegador del sistema operativo
import java.net.URI;                   // Representa una URL (usada para abrir la web)
import java.time.LocalDate;            // Fecha actual del sistema
import java.time.format.DateTimeFormatter; // Para formatear fechas como "dd-MM-yyyy"
import java.util.List;                 // Para trabajar con listas de objetos

/**
 * ══════════════════════════════════════════════════════════════════════════════
 * CLASE PRINCIPAL: InventarioApp
 * ══════════════════════════════════════════════════════════════════════════════
 *
 * Esta clase ES la ventana principal de la aplicación. Extiende JFrame, que en
 * Swing es la clase que representa una ventana de escritorio.
 *
 * ESTRUCTURA VISUAL DE LA VENTANA:
 *
 * ┌──────────────────────────────────────────────────────────┐ │ BANNER (parte
 * de arriba: logo, título, botón de salir) │ ← BorderLayout.NORTH
 * ├────────────┬─────────────────────────────────────────────┤ │ │ │ │ MENÚ │
 * ÁREA DE TRABAJO │ ← BorderLayout.CENTER │ LATERAL │ (aquí aparecen los
 * paneles al pulsar │ │ │ los botones del menú) │
 * └────────────┴─────────────────────────────────────────────┘ ↑
 * BorderLayout.WEST
 *
 * ROLES DE USUARIO: - ADMINISTRADOR → ve todos los botones del menú (consulta +
 * edición) - PROFESOR → solo ve los botones de consulta (sin
 * añadir/modificar/eliminar)
 */
public class InventarioApp extends JFrame {

    // ══════════════════════════════════════════════════════════════════════
    //  CONSTANTES DE DIMENSIONES
    //  Controlan el tamaño de la ventana y sus partes.
    //  🎨 DISEÑO: Cambia estos números para hacer la ventana más grande/pequeña.
    // ══════════════════════════════════════════════════════════════════════
    private static final int ANCHO_VENTANA = 900;  // 🎨 Ancho total de la ventana en píxeles
    private static final int ALTO_VENTANA = 600;  // 🎨 Alto total de la ventana en píxeles
    private static final int ANCHO_MENU = 190;  // 🎨 Ancho del menú lateral izquierdo
    private static final int ALTO_BANNER = 120;  // 🎨 Alto del banner superior

    // ══════════════════════════════════════════════════════════════════════
    //  URL DE LA WEB LOCAL
    //  🎨 DISEÑO: Cambia esta URL si tu servidor local usa otro puerto.
    //  Ejemplos:
    //    "http://localhost"        → servidor en el puerto 80 (por defecto)
    //    "http://localhost:8080"   → servidor en el puerto 8080 (Tomcat, Spring...)
    //    "http://localhost:3000"   → servidor Node.js típico
    //    "http://127.0.0.1:80"     → misma cosa que localhost
    // ══════════════════════════════════════════════════════════════════════
    private static final String URL_WEB_LOCAL = "http://10.0.10.69";

    // ══════════════════════════════════════════════════════════════════════
    //  PALETA DE COLORES
    //  Todos los colores de la interfaz están aquí centralizados.
    //  🎨 DISEÑO: Modifica estos colores para cambiar el tema visual completo.
    // ══════════════════════════════════════════════════════════════════════
    private static final Color COLOR_BANNER = new Color(30, 30, 46);  // 🎨 Fondo del banner (azul muy oscuro/morado)
    private static final Color COLOR_MENU_BG = new Color(44, 44, 64);  // 🎨 Fondo del menú lateral
    private static final Color COLOR_MENU_BTN = new Color(64, 64, 96);  // 🎨 Color de los separadores del menú
    private static final Color COLOR_MENU_HOVER = new Color(90, 90, 140);  // 🎨 Color del botón al pasar el ratón por encima
    private static final Color COLOR_MENU_ACTIVO = new Color(114, 90, 200);  // 🎨 Color del botón seleccionado (morado)
    private static final Color COLOR_TRABAJO_BG = new Color(245, 245, 252); // 🎨 Fondo del área de trabajo (blanco azulado)
    private static final Color COLOR_TEXTO_CLARO = Color.WHITE;              // 🎨 Texto blanco (sobre fondos oscuros)
    private static final Color COLOR_TEXTO_GRIS = new Color(180, 180, 200); // 🎨 Texto gris suave (subtítulos, hints)
    private static final Color COLOR_BADGE_ADMIN = new Color(114, 90, 200);  // 🎨 Pastilla "ADMINISTRADOR" (morado)
    private static final Color COLOR_BADGE_PROF = new Color(50, 140, 100);  // 🎨 Pastilla "PROFESOR" (verde)
    private static final Color COLOR_ERROR = new Color(180, 60, 60);  // 🎨 Texto de error (rojo oscuro)
    private static final Color COLOR_OK = new Color(40, 140, 80);  // 🎨 Texto de éxito (verde)

    // ══════════════════════════════════════════════════════════════════════
    //  ATRIBUTOS DE ESTADO
    //  Variables que guardan información mientras la aplicación está abierta.
    // ══════════════════════════════════════════════════════════════════════
    // El rol del usuario que ha iniciado sesión (ADMINISTRADOR o PROFESOR).
    // Es "final" porque no cambia durante la sesión.
    private final Rol rolActual;

    // El panel del centro de la ventana. Lo guardamos como atributo porque
    // lo necesitamos en mostrarPanel() para poder cambiar su contenido.
    private JPanel areaTrabajoContenedor;

    // El botón del menú que está actualmente seleccionado (resaltado en morado).
    // Lo guardamos para poder "des-resaltar" el anterior al pulsar otro.
    private JButton botonActivo = null;

    // ══════════════════════════════════════════════════════════════════════
    //  Area de Logs
    //  Compartido por todos los paneles para mostrar/parsear fechas igual.
    //  Es un JTextArea para mostrar mensajes de error de consola y mensajes de validación sin abrir un Jdialog
    // ══════════════════════════════════════════════════════════════════════
    private JTextArea areaLogs;

    // ══════════════════════════════════════════════════════════════════════
    //  DAO — Punto de acceso a la base de datos
    //  Se crea una sola vez aquí y se reutiliza en todos los paneles.
    //  Todos los métodos que guardan, listan o eliminan de la BD pasan por aquí.
    // ══════════════════════════════════════════════════════════════════════
    private final AdministradorDAO dao = new AdministradorDAO();
    private final PcDAO pcDao = new PcDAO();
    private final UbicacionDAO ubiDao = new UbicacionDAO();
    // ══════════════════════════════════════════════════════════════════════
    //  FORMATO DE FECHA
    //  Compartido por todos los paneles para mostrar/parsear fechas igual.
    //  🎨 DISEÑO: Cambia el patrón para otro formato:
    //    "dd/MM/yyyy"  → 17/05/2025
    //    "yyyy-MM-dd"  → 2025-05-17  (formato ISO, útil para la BD)
    //    "dd 'de' MMMM 'de' yyyy" → 17 de mayo de 2025 (requiere Locale)
    // ══════════════════════════════════════════════════════════════════════
    private static final DateTimeFormatter FMT_FECHA = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // ══════════════════════════════════════════════════════════════════════
    //  CONSTRUCTOR
    //  Es lo primero que se ejecuta cuando se crea un objeto InventarioApp.
    //  Recibe el Rol del usuario que viene del LoginDialog.
    //  Su trabajo es: guardar el rol, construir la ventana y mostrarla.
    // ══════════════════════════════════════════════════════════════════════
    /**
     * Construye la ventana principal con el rol del usuario autenticado.
     *
     * @param rol El rol del usuario (Rol.ADMINISTRADOR o Rol.PROFESOR), que
     * determina qué botones aparecen en el menú.
     */
    public InventarioApp(Rol rol) {
        
       this.rolActual = rol;           // Guardamos el rol para usarlo en otros métodos

        configurarVentana();            // Paso 1: tamaño, título, comportamiento al cerrar

        // Paso 2: añadimos las tres zonas principales de la ventana.
        // BorderLayout divide la ventana en: NORTH, SOUTH, EAST, WEST, CENTER.
        add(crearBanner(), BorderLayout.NORTH);   // Banda de arriba (logo + título)
        add(crearMenuLateral(), BorderLayout.WEST);    // Panel de botones de la izquierda
        areaTrabajoContenedor = crearAreaTrabajo();       // Panel vacío del centro
        add(areaTrabajoContenedor, BorderLayout.CENTER);
        add(crearPanelLogs(), BorderLayout.SOUTH); // Panel de Logs abajo
        // Arrancar con el Logger cargado: 
        // forzar la inicialización del singleton DESPUÉS de que LoggerApp esté listo
        SwingUtilities.invokeLater(() -> {
            AccesoBaseDatos db = AccesoBaseDatos.getInstance();
            if (db.getConn() != null) {
                LoggerApp.log("✅ Conexión activa con la base de datos.");
            } else {
                LoggerApp.log("❌ Sin conexión a la base de datos.");
            }
        });
        // Paso 3: al abrir, mostramos la pantalla de bienvenida en el centro
        mostrarPanel(crearPanelBienvenida());
    }

    // ══════════════════════════════════════════════════════════════════════
    //  CONFIGURACIÓN BÁSICA DE LA VENTANA
    // ══════════════════════════════════════════════════════════════════════
    /**
     * Configura las propiedades básicas del JFrame (la ventana).
     *
     * 🎨 DISEÑO: En este método puedes cambiar: - El título de la barra de
     * título de Windows - El tamaño inicial y mínimo de la ventana - Lo que
     * pasa al pulsar la X (aquí: cierra el programa)
     */
    private void configurarVentana() {
        // Texto que aparece en la barra de título de Windows/Linux/Mac
        // 🎨 Cambia el texto "Gestión de Inventario" por el nombre de tu app
        setTitle("Gestión de Inventario — " + nombreRol());

        // Tamaño inicial al abrir (usa las constantes de arriba para cambiarlo)
        setSize(ANCHO_VENTANA, ALTO_VENTANA);

        // EXIT_ON_CLOSE: al pulsar la X, termina el programa.
        // Alternativa: DISPOSE_ON_CLOSE (solo cierra esta ventana, no el programa)
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Centra la ventana en la pantalla al abrirla (null = centrar respecto al escritorio)
        setLocationRelativeTo(null);

        // Tamaño mínimo: el usuario no podrá hacer la ventana más pequeña que esto
        // 🎨 Ajusta estos valores si tu interfaz necesita más espacio mínimo
        setMinimumSize(new Dimension(700, 450));

        // Definimos que la ventana usa BorderLayout para organizar sus zonas
        setLayout(new BorderLayout());
    }

    /**
     * Devuelve el nombre legible del rol actual como texto. Se usa para
     * mostrarlo en el título y en el badge del banner.
     */
    private String nombreRol() {
        return (rolActual == Rol.ADMINISTRADOR) ? "Administrador" : "Profesor";
    }

    // ══════════════════════════════════════════════════════════════════════
    //  BANNER SUPERIOR
    //  La franja de arriba que contiene: imagen, título y botón de cerrar sesión.
    // ══════════════════════════════════════════════════════════════════════
    /**
     * Construye y devuelve el panel del banner superior.
     *
     * Organización interna del banner (también usa BorderLayout): WEST →
     * espacio reservado para la imagen/logo CENTER → título, subtítulo y
     * pastilla de rol EAST → botón "Cerrar sesión"
     *
     * 🎨 DISEÑO: Puedes cambiar aquí: - El color de fondo: COLOR_BANNER - El
     * grosor/color de la línea inferior del banner: createMatteBorder - El
     * tamaño del hueco para la imagen: setPreferredSize del lblImagen - El
     * texto del título y subtítulo - El tamaño de las fuentes: new Font("Segoe
     * UI", Font.BOLD, 24) Formato: Font("NombreFuente", Estilo, Tamaño)
     * Estilos: Font.PLAIN, Font.BOLD, Font.ITALIC
     */
    private JPanel crearBanner() {
        // Panel raíz del banner con BorderLayout
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_BANNER);  // 🎨 Fondo oscuro del banner
        panel.setPreferredSize(new Dimension(ANCHO_VENTANA, ALTO_BANNER));

        // Borde: solo por debajo (bottom), con 2 píxeles de grosor y color morado
        // createMatteBorder(arriba, izq, abajo, der, color)
        // 🎨 Cambia el grosor (2) o el color (COLOR_MENU_ACTIVO) de esta línea decorativa
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_MENU_ACTIVO));

        // ── ZONA IZQUIERDA: imagen / logo ──────────────────────────────────
        // Actualmente muestra un placeholder con texto "[TU IMAGEN]".
        //
        // 🎨 PARA PONER TU PROPIA IMAGEN, sustituye las 4 líneas del JLabel por esto:
        //   ImageIcon icono = new ImageIcon("src/resources/logo.png"); // ruta de tu imagen
        //   Image imgEscalada = icono.getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH);
        //   JLabel lblImagen = new JLabel(new ImageIcon(imgEscalada));
        //   lblImagen.setPreferredSize(new Dimension(110, ALTO_BANNER));
        //
        // 🎨 Cambia 90, 90 por el tamaño en píxeles que quieras para tu logo.
        JLabel lblImagen = new JLabel("[TU IMAGEN]", SwingConstants.CENTER);
        lblImagen.setForeground(COLOR_TEXTO_GRIS);
        lblImagen.setFont(new Font("Segoe UI Emoji", Font.ITALIC, 11));
        lblImagen.setPreferredSize(new Dimension(110, ALTO_BANNER)); // 🎨 Ancho del hueco para imagen
        // Borde punteado decorativo alrededor del placeholder
        lblImagen.setBorder(BorderFactory.createDashedBorder(COLOR_MENU_HOVER, 2, 4));

        // ── ZONA CENTRAL: título + subtítulo + pastilla de rol ─────────────
        // Usamos BoxLayout en Y para apilar los elementos verticalmente
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        panelTitulo.setOpaque(false); // fondo transparente para que se vea el color del banner
        panelTitulo.setBorder(new EmptyBorder(16, 20, 10, 0)); // margen interno: top, left, bottom, right

        // Título principal
        // 🎨 Cambia el texto, la fuente ("Segoe UI Emoji") o el tamaño (24)
        JLabel lblTitulo = new JLabel("Sistema de Inventario");
        lblTitulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24)); // 🎨 Fuente y tamaño del título
        lblTitulo.setForeground(COLOR_TEXTO_CLARO);

        // Subtítulo (texto pequeño debajo del título)
        // 🎨 Cambia el texto o tamaño (12)
        JLabel lblSub = new JLabel("Gestión de materiales y Pcs del Taller de Informática del IES Miguel Herrero");
        lblSub.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12)); // 🎨 Fuente del subtítulo
        lblSub.setForeground(COLOR_TEXTO_GRIS);

        // Pastilla coloreada que muestra el rol activo (ej: "ADMINISTRADOR")
        // 🎨 El color cambia automáticamente según el rol (morado/verde), definido en las constantes
        JLabel lblBadge = new JLabel("  " + nombreRol().toUpperCase() + "  ");
        lblBadge.setFont(new Font("Segoe UI Emoji", Font.BOLD, 11)); // 🎨 Tamaño del texto del badge
        lblBadge.setForeground(COLOR_TEXTO_CLARO);
        lblBadge.setOpaque(true); // necesario para que setBackground() funcione en un JLabel
        // Elige el color según el rol:
        lblBadge.setBackground(rolActual == Rol.ADMINISTRADOR ? COLOR_BADGE_ADMIN : COLOR_BADGE_PROF);
        lblBadge.setBorder(new EmptyBorder(3, 8, 3, 8)); // pequeño padding interno de la pastilla

        // Panel envolvente del badge para alinearlo a la izquierda
        JPanel panelBadge = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 6));
        panelBadge.setOpaque(false);
        panelBadge.add(lblBadge);

        // Apilamos verticalmente: título → espacio → subtítulo → badge
        panelTitulo.add(lblTitulo);
        panelTitulo.add(Box.createVerticalStrut(4)); // 🎨 Espacio entre título y subtítulo (4px)
        panelTitulo.add(lblSub);
        panelTitulo.add(panelBadge);

        // ── ZONA DERECHA: botón "Cerrar sesión" ────────────────────────────
        // 🎨 Puedes cambiar el icono (⏻), el texto o los colores del botón
        JButton btnLogout = new JButton("↩  Cerrar sesión");
        btnLogout.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 11));
        btnLogout.setForeground(COLOR_TEXTO_GRIS);
        btnLogout.setBackground(new Color(55, 55, 75)); // 🎨 Color del botón de logout
        btnLogout.setBorderPainted(false);  // sin borde visible
        btnLogout.setFocusPainted(false);   // sin rectángulo de foco al hacer clic
        btnLogout.setOpaque(true);
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // cursor de mano al pasar
        // Al pulsar el botón, llama al método cerrarSesion()
        btnLogout.addActionListener(e -> cerrarSesion());

        JPanel panelLogout = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelLogout.setOpaque(false);
        panelLogout.add(btnLogout);

        // Montamos las tres zonas en el panel del banner
        panel.add(lblImagen, BorderLayout.WEST);
        panel.add(panelTitulo, BorderLayout.CENTER);
        panel.add(panelLogout, BorderLayout.EAST);
        return panel;
    }

    /**
     * Cierra la sesión actual y vuelve al LoginDialog.
     *
     * Flujo: 1. Pregunta al usuario si está seguro (JOptionPane de
     * confirmación) 2. Si dice SÍ: cierra esta ventana (dispose) y abre de
     * nuevo el LoginDialog 3. Si dice NO: no hace nada
     */
    private void cerrarSesion() {
        // showConfirmDialog devuelve JOptionPane.YES_OPTION o NO_OPTION según lo que pulse el usuario
        int conf = JOptionPane.showConfirmDialog(this,
                "¿Deseas cerrar la sesión actual?", // mensaje
                "Cerrar sesión", // título del diálogo
                JOptionPane.YES_NO_OPTION);         // botones: Sí / No

        if (conf == JOptionPane.YES_OPTION) {
            dispose();  // Cierra ESTA ventana (libera la memoria que ocupa)
            // Abre de nuevo la pantalla de login. "null" = sin ventana padre
            LoginDialog login = new LoginDialog(null);
            login.setVisible(true);
        }
    }

    // ══════════════════════════════════════════════════════════════════════
    //  MENÚ LATERAL
    //  La columna de la izquierda con los botones de navegación.
    //  Muestra diferentes botones según el rol del usuario.
    // ══════════════════════════════════════════════════════════════════════
    /**
     * Construye y devuelve el panel del menú lateral izquierdo.
     *
     * Estructura: - Sección "CONSULTA" (visible para todos los roles): 📋
     * Listar · 🔍 Buscar · 🔎 Filtrar · 📄 Informe - Sección "EDICIÓN" (solo
     * para ADMINISTRADOR): ➕ Añadir · ✏️ Modificar · 🗑️ Eliminar - Sección
     * "OTROS": 🌐 Web del proyecto
     *
     * Cada botón, al pulsarse, llama a mostrarPanel() con el panel
     * correspondiente.
     *
     * 🎨 DISEÑO: Puedes: - Cambiar los emojis/iconos de los botones - Cambiar
     * los textos de los botones - Añadir nuevos botones creando más
     * crearBotonMenu(...) - Cambiar los nombres de las secciones (CONSULTA,
     * EDICIÓN, OTROS) - Cambiar el ancho del menú: constante ANCHO_MENU
     */
    private JPanel crearMenuLateral() {
        JPanel panel = new JPanel();
        // BoxLayout en Y: apila los elementos uno encima de otro (verticalmente)
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_MENU_BG);
        // setPreferredSize fija el ancho; el alto (0) lo gestiona el layout
        panel.setPreferredSize(new Dimension(ANCHO_MENU, 0));
        // Borde de 1px a la derecha para separar visualmente el menú del área de trabajo
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, COLOR_MENU_BTN));

        // ── Sección CONSULTA: visible para todos ──────────────────────────
        panel.add(crearEtiquetaSeccion("CONSULTA")); // título de la sección
        // Cada línea: texto del botón → qué panel mostrar al pulsarlo
        panel.add(crearBotonMenu("📋  Listar todo", e -> mostrarPanel(crearPanelListar())));
        panel.add(crearBotonMenu("🔍  Buscar", e -> mostrarPanel(crearPanelBuscar())));
        panel.add(crearBotonMenu("🔎  Filtrar", e -> mostrarPanel(crearPanelFiltrar())));
        panel.add(crearBotonMenu("📄  Generar informe", e -> mostrarPanel(crearPanelInforme())));

        // ── Sección EDICIÓN: solo si es ADMINISTRADOR ─────────────────────
        if (rolActual == Rol.ADMINISTRADOR) {
            panel.add(Box.createVerticalStrut(6)); // 🎨 Espacio de 6px antes del separador
            panel.add(crearSeparador());           // línea horizontal separadora
            panel.add(crearEtiquetaSeccion("EDICIÓN"));
            panel.add(crearBotonMenu("➕  Añadir", e -> mostrarPanel(crearPanelAnadir())));
            panel.add(crearBotonMenu("✏️  Modificar", e -> mostrarPanel(crearPanelModificar())));
            panel.add(crearBotonMenu("🗑️  Eliminar", e -> mostrarPanel(crearPanelEliminar())));
        }

        // ── Sección PC's: visible para todos ──────────────────────────
        panel.add(Box.createVerticalStrut(6));
        panel.add(crearSeparador());
        panel.add(crearEtiquetaSeccion("PCS"));
        panel.add(crearBotonMenu("🖥️  Listar PCs", e -> mostrarPanel(crearPanelListarPCs())));
        panel.add(crearBotonMenu("➕  Añadir PC", e -> mostrarPanel(crearPanelAnadirPC())));

        // ── Sección OTROS: visible para todos ─────────────────────────────
        panel.add(Box.createVerticalStrut(6));
        panel.add(crearSeparador());
        panel.add(crearEtiquetaSeccion("OTROS"));
        panel.add(crearBotonMenu("🌐  Web del proyecto", e -> mostrarPanel(crearPanelWeb())));

        // Box.createVerticalGlue() empuja todo hacia arriba, dejando el espacio sobrante abajo.
        // Sin esto, los botones se repartirían por toda la altura del menú.
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    /**
     * Crea el texto pequeño de cabecera de cada sección del menú (ej:
     * "CONSULTA").
     *
     * 🎨 DISEÑO: Cambia la fuente, tamaño o color para distinguir más las
     * secciones. - Font.BOLD → negrita (actual) - Font.ITALIC → cursiva -
     * Tamaño 10 → muy pequeño; 12 sería más visible
     *
     * @param texto El nombre de la sección en mayúsculas
     */
    private JLabel crearEtiquetaSeccion(String texto) {
        JLabel lbl = new JLabel("  " + texto); // dos espacios de sangría
        lbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 10)); // 🎨 Fuente y tamaño del encabezado de sección
        lbl.setForeground(COLOR_TEXTO_GRIS);              // 🎨 Color del texto (gris suave)
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBorder(new EmptyBorder(14, 8, 6, 0));      // 🎨 Espaciado: top 14px, left 8px
        return lbl;
    }

    /**
     * Crea una línea horizontal separadora para el menú.
     *
     * 🎨 DISEÑO: Cambia el color (COLOR_MENU_BTN) o el grosor (ANCHO_MENU -
     * 20).
     */
    private JSeparator crearSeparador() {
        JSeparator sep = new JSeparator();
        sep.setForeground(COLOR_MENU_BTN); // 🎨 Color de la línea separadora
        // La línea ocupa el ancho del menú menos 20px de margen a cada lado
        sep.setMaximumSize(new Dimension(ANCHO_MENU - 20, 1)); // 🎨 Grosor: cambia el 1 por 2 para más visible
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);
        return sep;
    }

    /**
     * Crea un botón estilizado para el menú lateral.
     *
     * Comportamiento: - Al pasar el ratón por encima: cambia al color HOVER
     * (azul medio) - Al pulsar: se queda resaltado en morado
     * (COLOR_MENU_ACTIVO) - El botón anterior pierde el resaltado al pulsar uno
     * nuevo
     *
     * 🎨 DISEÑO: Cambia aquí para modificar el aspecto de TODOS los botones del
     * menú: - Fuente: new Font("Segoe UI Emoji", Font.PLAIN, 13) → tamaño, negrita...
     * - Alto de cada botón: setMaximumSize y setPreferredSize (actualmente
     * 42px) - Sangría del texto: new EmptyBorder(0, 16, 0, 0) → los 16px del
     * lado izquierdo
     *
     * @param texto Texto del botón (puede incluir emoji)
     * @param accion Qué hacer al pulsar el botón (una expresión lambda)
     */
    private JButton crearBotonMenu(String texto, ActionListener accion) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13)); // 🎨 Fuente de los botones del menú
        btn.setForeground(COLOR_TEXTO_CLARO);
        btn.setBackground(COLOR_MENU_BG);
        btn.setOpaque(true);
        btn.setBorderPainted(false);  // sin borde
        btn.setFocusPainted(false);   // sin rectángulo de foco
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT); // texto alineado a la izquierda
        btn.setMaximumSize(new Dimension(ANCHO_MENU, 42));   // 🎨 Alto del botón (42px)
        btn.setPreferredSize(new Dimension(ANCHO_MENU, 42)); // 🎨 Mismo alto aquí también
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setBorder(new EmptyBorder(0, 16, 0, 0)); // 🎨 Sangría del texto: 16px desde la izquierda

        // MouseListener para el efecto hover (cambio de color al pasar el ratón)
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Solo cambia a hover si NO es el botón actualmente activo
                if (btn != botonActivo) {
                    btn.setBackground(COLOR_MENU_HOVER);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Al salir, solo restaura si NO es el activo
                if (btn != botonActivo) {
                    btn.setBackground(COLOR_MENU_BG);
                }
            }
        });

        // ActionListener: qué pasa al hacer clic
        btn.addActionListener(e -> {
            // Des-resalta el botón que estaba activo antes
            if (botonActivo != null) {
                botonActivo.setBackground(COLOR_MENU_BG);
            }
            // Marca este como el nuevo activo
            botonActivo = btn;
            btn.setBackground(COLOR_MENU_ACTIVO); // 🎨 Color del botón activo (morado)
            // Ejecuta la acción que se pasó como parámetro (abre el panel)
            accion.actionPerformed(e);
        });
        return btn;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  ÁREA DE TRABAJO
    //  El panel central que actúa como "pantalla" donde aparecen los paneles.
    // ══════════════════════════════════════════════════════════════════════
    /**
     * Crea el panel vacío del área de trabajo central. Es simplemente un panel
     * con BorderLayout que actúa como contenedor. Su contenido se reemplaza
     * cada vez que el usuario pulsa un botón del menú.
     */
    private JPanel crearAreaTrabajo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_TRABAJO_BG); // 🎨 Color de fondo del área de trabajo
        return panel;
    }

    /**
     * Reemplaza el contenido del área de trabajo con un nuevo panel.
     *
     * Se llama cada vez que el usuario pulsa un botón del menú. Proceso: 1.
     * Elimina el panel anterior del contenedor 2. Añade el nuevo panel en el
     * centro 3. revalidate() → recalcula el layout (reorganiza los componentes)
     * 4. repaint() → redibuja la ventana en pantalla
     *
     * @param nuevoPanel El panel que queremos mostrar en el área central
     */
    private void mostrarPanel(JPanel nuevoPanel) {
        areaTrabajoContenedor.removeAll();                             // Elimina lo que había
        areaTrabajoContenedor.add(nuevoPanel, BorderLayout.CENTER);   // Pone el nuevo
        areaTrabajoContenedor.revalidate();                           // Recalcula layout
        areaTrabajoContenedor.repaint();                              // Redibuja
    }

    // ══════════════════════════════════════════════════════════════════════
    //  PANEL DE BIENVENIDA
    //  Pantalla inicial que se muestra al abrir la app.
    // ══════════════════════════════════════════════════════════════════════
    /**
     * Crea el panel de bienvenida con emoji, nombre de rol y permisos.
     *
     * 🎨 DISEÑO: Puedes cambiar: - Los emojis según el rol (🛠️ admin / 📚
     * profesor) - El tamaño del emoji: Font.PLAIN, 40 - El texto de bienvenida
     * y permisos - El color del texto de permisos (gris-azulado actualmente)
     */
    private JPanel crearPanelBienvenida() {
        // GridBagLayout sin restricciones centra el contenido horizontal y verticalmente
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_TRABAJO_BG);

        // Contenedor interno con BoxLayout vertical para apilar los elementos
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setOpaque(false);

        // Emoji grande según el rol
        // 🎨 Cambia los emojis: "🛠️" admin, "📚" profesor → usa cualquier emoji
        JLabel lblEmoji = new JLabel(
                rolActual == Rol.ADMINISTRADOR ? "🛠️" : "📚", SwingConstants.CENTER);
        lblEmoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40)); // 🎨 Tamaño del emoji
        lblEmoji.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Texto "Bienvenido, Administrador/Profesor"
        JLabel lblBien = new JLabel("Bienvenido, " + nombreRol());
        lblBien.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20)); // 🎨 Tamaño del saludo
        lblBien.setForeground(new Color(60, 60, 80));         // 🎨 Color del saludo
        lblBien.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Descripción de permisos según el rol
        String permisos = (rolActual == Rol.ADMINISTRADOR)
                ? "Acceso completo: consulta, edición y generación de informes."
                : "Acceso de solo lectura: listar, buscar, filtrar y generar informes.";
        // <html><center> permite texto multilínea y centrado en un JLabel
        JLabel lblPermisos = new JLabel("<html><center>" + permisos + "</center></html>");
        lblPermisos.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        lblPermisos.setForeground(new Color(130, 130, 160)); // 🎨 Color del texto de permisos
        lblPermisos.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Apilamos: emoji → espacio → saludo → espacio → permisos
        contenido.add(lblEmoji);
        contenido.add(Box.createVerticalStrut(12)); // 🎨 Espacio entre emoji y saludo (12px)
        contenido.add(lblBien);
        contenido.add(Box.createVerticalStrut(8));  // 🎨 Espacio entre saludo y permisos (8px)
        contenido.add(lblPermisos);
        panel.add(contenido);
        return panel;
    }

    private JPanel crearPanelLogs() {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBorder(BorderFactory.createMatteBorder(
                1, 0, 0, 0,
                new Color(180, 180, 200)));

        areaLogs = new JTextArea(4, 20);

        LoggerApp.inicializar(areaLogs);

        areaLogs.setEditable(false);

        areaLogs.setFont(new Font("Consolas", Font.PLAIN, 12));

        areaLogs.setBackground(new Color(35, 35, 35));

        areaLogs.setForeground(new Color(220, 220, 220));

        areaLogs.setLineWrap(true);

        areaLogs.setWrapStyleWord(true);

        areaLogs.setText("Sistema iniciado...\n");

        JScrollPane scroll = new JScrollPane(areaLogs);

        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  PANEL LISTAR
    //  Muestra TODOS los materiales de la BD en una tabla.
    //  Llama a: dao.listarMaterial()
    // ══════════════════════════════════════════════════════════════════════
    /**
     * Crea el panel con la tabla de todos los materiales del inventario.
     *
     * Proceso: 1. Llama a dao.listarMaterial() → obtiene una
     * List<MaterialInventario> de la BD 2. Convierte esa lista en un array
     * Object[][] que entiende JTable 3. Crea la tabla con crearTabla() y la
     * envuelve en un JScrollPane (el scroll es necesario cuando hay muchos
     * registros) 4. Añade un contador de registros y un botón para recargar los
     * datos
     *
     * 🎨 DISEÑO: Puedes: - Cambiar las columnas mostradas (array cols[]) -
     * Cambiar qué datos van en cada columna (el bucle for) - Añadir más
     * columnas si añades más campos al array y al bucle
     */
    private JPanel crearPanelListar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_TRABAJO_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // margen interior del panel
        panel.add(crearTituloPanel("Listado de materiales"), BorderLayout.NORTH);

        // ── Definición de columnas de la tabla ────────────────────────────
        // 🎨 Cambia los nombres de las columnas aquí para que aparezcan diferente en la cabecera
        String[] cols = {"ID", "Nombre", "Descripción", "Estado", "Cantidad", "Ubicación", "Balda", "Fecha alta"};

        // ── Obtención de datos reales de la BD ────────────────────────────
        List<MaterialInventario> lista = dao.listarMaterial();

        // Creamos el array de datos con tantas filas como materiales haya
        // y tantas columnas como definimos en cols[]
        Object[][] datos = new Object[lista.size()][cols.length];
        for (int i = 0; i < lista.size(); i++) {
            MaterialInventario m = lista.get(i);
            // Cada posición [i][j] corresponde a la columna j de la fila i
            datos[i][0] = m.getId_matTa();
            datos[i][1] = m.getNombre();
            datos[i][2] = m.getDescripcion();
            datos[i][3] = m.getEstado();
            datos[i][4] = m.getCantidad();
            datos[i][5] = m.getId_ubi();
            // getId_balda() puede ser null (la balda es opcional), mostramos "—" si no tiene
            datos[i][6] = m.getId_balda() != null ? m.getId_balda() : "—";
            datos[i][7] = m.getFecha_alta().format(FMT_FECHA); // formateamos la fecha
        }

        // JScrollPane envuelve la tabla para que aparezca una barra de scroll cuando haya muchos registros
        panel.add(new JScrollPane(crearTabla(datos, cols)), BorderLayout.CENTER);

        // ── Zona inferior: contador y botón actualizar ────────────────────
        JLabel lblTotal = new JLabel("Total registros: " + lista.size());
        lblTotal.setFont(new Font("Segoe UI Emoji", Font.ITALIC, 12));
        lblTotal.setForeground(new Color(120, 120, 140)); // 🎨 Color del contador

        JButton btnActualizar = crearBotonAccion("🔄 Actualizar");
        // Al pulsar Actualizar, recrea el panel entero (vuelve a consultar la BD)
        btnActualizar.addActionListener(e -> mostrarPanel(crearPanelListar()));

        JPanel sur = new JPanel(new BorderLayout());
        sur.setOpaque(false);
        sur.add(lblTotal, BorderLayout.WEST);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setOpaque(false);
        btnPanel.add(btnActualizar);
        sur.add(btnPanel, BorderLayout.EAST);
        panel.add(sur, BorderLayout.SOUTH);
        return panel;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  PANEL BUSCAR
    //  Permite buscar un material por su ID y ver todos sus datos.
    //
    //  ⚠️ NOTA TÉCNICA: porIdMaterial() no está implementado en el DAO.
    //  Como alternativa, se obtiene la lista completa y se busca en memoria.
    //  Cuando implementes porIdMaterial() en AdministradorDAO, puedes
    //  simplificar este método.
    // ══════════════════════════════════════════════════════════════════════
    /**
     * Crea el panel de búsqueda por ID.
     *
     * Proceso al pulsar "Buscar" (o Enter): 1. Lee el ID del JTextField y lo
     * valida 2. Obtiene la lista completa de la BD 3. Recorre la lista buscando
     * el ID 4. Si lo encuentra, muestra todos sus campos en el JTextArea 5. Si
     * no lo encuentra, muestra un mensaje de error
     *
     * 🎨 DISEÑO: Puedes cambiar: - El formato de visualización del resultado
     * (el String largo con "═══") - La fuente del área de resultados
     * (Monospaced actualmente) - El color de fondo del área: new Color(230,
     * 230, 240)
     */
    private JPanel crearPanelBuscar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_TRABAJO_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(crearTituloPanel("Buscar material por ID"), BorderLayout.NORTH);

        // ── Barra superior: campo de texto + botón buscar ─────────────────
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        barra.setOpaque(false);
        barra.add(etiqueta("ID (número entre 0 y 99):"));
        JTextField txtId = new JTextField(10);
        txtId.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        txtId.setPreferredSize(new Dimension(120, 30));
        JButton btnBuscar = crearBotonAccion("Buscar");
        barra.add(txtId);
        barra.add(btnBuscar);

        // ── Área de texto donde aparece el resultado ──────────────────────
        // JTextArea: texto multilínea de solo lectura
        JTextArea area = new JTextArea(10, 40);
        area.setEditable(false); // el usuario no puede escribir aquí
        area.setFont(new Font("Monospaced", Font.PLAIN, 13)); // 🎨 Fuente monoespaciada para alinear los datos
        area.setBackground(new Color(230, 230, 240));         // 🎨 Color de fondo del área de resultado
        area.setText("Introduce un ID y pulsa Buscar...");

        // ── Lógica del botón Buscar ───────────────────────────────────────
        btnBuscar.addActionListener(e -> {
            String idTexto = txtId.getText().trim(); // .trim() elimina espacios iniciales/finales
            if (idTexto.isEmpty()) {
                area.setText("⚠ Introduce un ID válido.");
                return; // return dentro de un ActionListener sale solo de la lambda, no del método
            }
            int id;
            try {
                id = Integer.parseInt(idTexto); // Convertimos el texto a número
            } catch (NumberFormatException ex) {
                // Si el texto no es un número válido, parseInt lanza NumberFormatException
                area.setText("⚠ El ID debe ser un número entero.");
                return;
            }

            // Buscamos en la lista completa (alternativa a porIdMaterial no implementado)
            List<MaterialInventario> lista = dao.listarMaterial();
            MaterialInventario encontrado = null;
            for (MaterialInventario m : lista) {
                if (m.getId_matTa() == id) {
                    encontrado = m;
                    break; // encontramos el que buscamos, salimos del bucle
                }
            }

            if (encontrado == null) {
                area.setText("❌ No se encontró ningún material con ID " + id + ".");
                return;
            }

            // Mostramos los datos formateados en el área de texto
            // 🎨 Cambia el formato de esta cadena para que los datos se muestren diferente
            MaterialInventario m = encontrado;
            area.setText(
                    "══════════════════════════════════════\n"
                    + "  MATERIAL ENCONTRADO  (ID: " + m.getId_matTa() + ")\n"
                    + "══════════════════════════════════════\n"
                    + "  Nombre       : " + m.getNombre() + "\n"
                    + "  Descripción  : " + m.getDescripcion() + "\n"
                    + "  Estado       : " + m.getEstado() + "\n"
                    + "  Cantidad     : " + m.getCantidad() + "\n"
                    + "  Ubicación    : " + m.getId_ubi() + "\n"
                    + "  Balda        : " + (m.getId_balda() != null ? m.getId_balda() : "—") + "\n"
                    + "  Fecha alta   : " + m.getFecha_alta().format(FMT_FECHA) + "\n"
                    + "  Observaciones: " + m.getObservaciones() + "\n"
            );
        });

        // Atajo de teclado: pulsar Enter en el campo de texto hace lo mismo que el botón
        txtId.addActionListener(e -> btnBuscar.doClick());

        JPanel centro = new JPanel(new BorderLayout(0, 12));
        centro.setOpaque(false);
        centro.add(barra, BorderLayout.NORTH);
        centro.add(new JScrollPane(area), BorderLayout.CENTER);
        panel.add(centro, BorderLayout.CENTER);
        return panel;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  PANEL FILTRAR
    //  Permite filtrar los materiales por estado y cantidad mínima.
    //  Estados disponibles: OPERATIVO, REPARACION, OBSOLETO
    // ══════════════════════════════════════════════════════════════════════
    /**
     * Crea el panel de filtrado de materiales.
     *
     * Componentes de filtro: - JComboBox de estado: "Todos" o un estado
     * específico del enum Estados - JSpinner de cantidad mínima: un número
     * entre 0 y 9999
     *
     * Proceso al pulsar "Aplicar filtros": 1. Obtiene la lista completa de la
     * BD 2. Recorre la lista y comprueba cada material contra los filtros 3.
     * Los materiales que pasan los filtros se añaden a filasFiltradas 4. Se
     * actualiza el modelo de la tabla (DefaultTableModel) con los resultados →
     * Esto es diferente a crearPanelListar(), donde se crea una tabla nueva;
     * aquí actualizamos el modelo de una tabla ya existente
     *
     * 🎨 DISEÑO: Puedes: - Añadir más criterios de filtro (ej: filtrar por
     * nombre, por fecha...) Para cada filtro nuevo: añade un campo de entrada,
     * una variable booleana en el bucle y una condición en el if final -
     * Cambiar las columnas de la tabla de resultados (array cols[])
     */
    private JPanel crearPanelFiltrar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_TRABAJO_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(crearTituloPanel("Filtrar materiales"), BorderLayout.NORTH);

        // ── Panel de controles de filtro ──────────────────────────────────
        JPanel filtros = new JPanel(new GridBagLayout());
        filtros.setOpaque(false);
        // Borde con título: crea un recuadro con texto en la esquina superior izquierda
        filtros.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 215)), // 🎨 Color del borde del recuadro
                "Criterios de filtro", // 🎨 Texto del título del recuadro
                0, 0,
                new Font("Segoe UI Emoji", Font.BOLD, 12), // 🎨 Fuente del título del recuadro
                new Color(80, 80, 100)));              // 🎨 Color del título del recuadro

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 10, 7, 10); // margen alrededor de cada celda
        gbc.anchor = GridBagConstraints.WEST;  // alineación a la izquierda

        // Fila 0: etiqueta + combo de estado
        gbc.gridx = 0;
        gbc.gridy = 0;
        filtros.add(etiqueta("Estado:"), gbc);
        gbc.gridx = 1;
        // Los valores deben coincidir exactamente con el enum Estados: OPERATIVO, REPARACION, OBSOLETO
        // 🎨 Cambia "Todos" por otro texto si prefieres otro nombre para la opción por defecto
        String[] estados = {"Todos", "OPERATIVO", "REPARACION", "OBSOLETO"};
        JComboBox<String> comboEstado = new JComboBox<>(estados);
        comboEstado.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        filtros.add(comboEstado, gbc);

        // Fila 1: etiqueta + spinner de cantidad mínima
        gbc.gridx = 0;
        gbc.gridy = 1;
        filtros.add(etiqueta("Cantidad mínima:"), gbc);
        gbc.gridx = 1;
        // SpinnerNumberModel(valorInicial, mínimo, máximo, paso)
        // 🎨 Cambia el máximo (9999) o el paso (1) si necesitas otro rango
        JSpinner spinnerMin = new JSpinner(new SpinnerNumberModel(0, 0, 9999, 1));
        spinnerMin.setPreferredSize(new Dimension(80, 28));
        filtros.add(spinnerMin, gbc);

        // Fila 2: Etiqueta y spinner de Ubicación
        gbc.gridx = 0;
        gbc.gridy = 2;
        filtros.add(etiqueta("Ubicación:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 1;

        JComboBox<String> comboUbicacion = new JComboBox<>();
        comboUbicacion.addItem("Todas");
        // Cargar ubicaciones desde la BD
        for (String ubi : ubiDao.listarUbicaciones()) {
            comboUbicacion.addItem(ubi);
        }

        comboUbicacion.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        filtros.add(comboUbicacion, gbc);

        // Fila 3: botón de filtrar (ocupa 2 columnas)
        gbc.gridx = 0;
        gbc.gridy = 3;

        gbc.gridwidth = 2;
        JButton btnFiltrar = crearBotonAccion("Aplicar filtros");
        filtros.add(btnFiltrar, gbc);

        // ── Tabla de resultados (inicialmente vacía) ───────────────────────
        // 🎨 Cambia las columnas que se muestran en los resultados del filtro
        String[] cols = {"ID", "Nombre", "Estado", "Cantidad", "Ubicación", "Fecha alta"};
        JTable tablaRes = crearTabla(new Object[][]{}, cols); // tabla vacía al inicio
        JScrollPane scroll = new JScrollPane(tablaRes);

        // Contador de resultados
        JLabel lblResultados = new JLabel("Resultados: 0 materiales");
        lblResultados.setFont(new Font("Segoe UI Emoji", Font.ITALIC, 12));
        lblResultados.setForeground(new Color(100, 100, 130)); // 🎨 Color del contador de resultados

        // ── Lógica del botón Aplicar filtros ──────────────────────────────
        btnFiltrar.addActionListener(e -> {
            String estadoFiltro = (String) comboEstado.getSelectedItem();
            int cantMinima = (int) spinnerMin.getValue(); // getValue() de un JSpinner devuelve Object, lo casteamos

            String ubicacionFiltro
                    = comboUbicacion
                            .getSelectedItem()
                            .toString();
            List<MaterialInventario> lista = dao.listarMaterial();
            // Usamos ArrayList para ir acumulando las filas que pasen los filtros
            java.util.List<Object[]> filasFiltradas = new java.util.ArrayList<>();

            for (MaterialInventario m : lista) {
                // Condición 1: el estado coincide, o el filtro es "Todos"
                boolean okEstado = estadoFiltro.equals("Todos")
                        || m.getEstado().toString().equals(estadoFiltro);
                // Condición 2: la cantidad es mayor o igual al mínimo pedido
                boolean okCantidad = m.getCantidad() >= cantMinima;
                // Condición 3: la ubicacion coincide o el filtro es "Todas"
                boolean okUbicacion = ubicacionFiltro.equals("Todas") || m.getId_ubi().equals(ubicacionFiltro);

                // Solo añadimos si AMBAS condiciones se cumplen
                if (okEstado && okCantidad && okUbicacion) {
                    filasFiltradas.add(new Object[]{
                        m.getId_matTa(),
                        m.getNombre(),
                        m.getEstado(),
                        m.getCantidad(),
                        m.getId_ubi(),
                        m.getFecha_alta().format(FMT_FECHA)
                    });
                }
            }

            // Convertimos la lista a array y actualizamos el modelo de la tabla
            // DefaultTableModel es el modelo de datos que usa JTable internamente
            // Al llamar setModel(), la tabla se redibuja automáticamente con los nuevos datos
            Object[][] datos = filasFiltradas.toArray(new Object[0][]);
            tablaRes.setModel(new javax.swing.table.DefaultTableModel(datos, cols));
            lblResultados.setText("Resultados: " + filasFiltradas.size() + " materiales");
        });

        JPanel sur = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sur.setOpaque(false);
        sur.add(lblResultados);

        // Panel central: filtros arriba, tabla en el centro, contador abajo
        JPanel centro = new JPanel(new BorderLayout(0, 10));
        centro.setOpaque(false);
        centro.add(filtros, BorderLayout.NORTH);
        centro.add(scroll, BorderLayout.CENTER);
        centro.add(sur, BorderLayout.SOUTH);
        panel.add(centro, BorderLayout.CENTER);
        return panel;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  PANEL INFORME
    //  Genera un resumen de texto del inventario y permite exportarlo a .txt
    // ══════════════════════════════════════════════════════════════════════
    /**
     * Crea el panel de generación de informes.
     *
     * Tipos de informe disponibles (JRadioButton): - "Inventario completo":
     * lista todos los materiales - "Filtrado por estado": lista solo los de un
     * estado concreto
     *
     * Proceso al pulsar "Generar": 1. Obtiene la lista de la BD 2. Construye un
     * StringBuilder con el texto del informe 3. Lo muestra en un JTextArea
     * (previsualización) 4. Habilita el botón "Exportar .txt"
     *
     * Proceso al pulsar "Exportar .txt": 1. Abre un JFileChooser para que el
     * usuario elija dónde guardar 2. Escribe el texto del JTextArea en el
     * archivo con FileWriter
     *
     * 🎨 DISEÑO: Puedes cambiar el formato del informe en el StringBuilder (sb)
     * dentro del ActionListener del btnGenerar.
     */
    private JPanel crearPanelInforme() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_TRABAJO_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(crearTituloPanel("Generar informe"), BorderLayout.NORTH);

        // ── Opciones del tipo de informe ──────────────────────────────────
        JPanel opciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        opciones.setOpaque(false);
        opciones.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 215)),
                "Tipo de informe", 0, 0,
                new Font("Segoe UI Emoji", Font.BOLD, 12), new Color(80, 80, 100)));

        // ButtonGroup agrupa los radios para que solo uno pueda estar seleccionado a la vez
        ButtonGroup grupo = new ButtonGroup();
        JRadioButton rbCompleto = new JRadioButton("Inventario completo", true); // seleccionado por defecto
        JRadioButton rbEstado = new JRadioButton("Filtrado por estado");
        for (JRadioButton rb : new JRadioButton[]{rbCompleto, rbEstado}) {
            rb.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
            rb.setOpaque(false);
            grupo.add(rb);   // los añadimos al grupo
            opciones.add(rb); // los añadimos al panel visual
        }

        // Combo de estado: solo se habilita cuando se selecciona "Filtrado por estado"
        String[] estadosInforme = {"OPERATIVO", "REPARACION", "OBSOLETO"};
        JComboBox<String> comboEstadoInforme = new JComboBox<>(estadosInforme);
        comboEstadoInforme.setEnabled(false); // deshabilitado al inicio
        opciones.add(comboEstadoInforme);
        // Al seleccionar el radio de "por estado", habilitamos el combo
        rbEstado.addActionListener(e -> comboEstadoInforme.setEnabled(true));
        // Al volver a "completo", deshabilitamos el combo
        rbCompleto.addActionListener(e -> comboEstadoInforme.setEnabled(false));

        // ── Área de previsualización del informe ──────────────────────────
        JTextArea areaPreview = new JTextArea();
        areaPreview.setEditable(false);
        areaPreview.setFont(new Font("Monospaced", Font.PLAIN, 12)); // 🎨 Fuente del informe
        areaPreview.setBackground(new Color(230, 230, 242));         // 🎨 Color de fondo del informe
        areaPreview.setText("Pulsa 'Generar' para previsualizar el informe aquí.");

        JButton btnGenerar = crearBotonAccion("Generar");
        JButton btnExportar = crearBotonAccion("Exportar .txt");
        btnExportar.setEnabled(false); // deshabilitado hasta que se genere el informe

        // ── Lógica del botón Generar ──────────────────────────────────────
        btnGenerar.addActionListener(e -> {
            List<MaterialInventario> lista = dao.listarMaterial();
            StringBuilder sb = new StringBuilder(); // StringBuilder es eficiente para concatenar strings
            String fecha = LocalDate.now().format(FMT_FECHA); // fecha actual formateada

            if (rbCompleto.isSelected()) {
                // 🎨 Cambia el formato del encabezado y las líneas del informe completo
                sb.append("══════════════════════════════════════════════════\n");
                sb.append("  INFORME COMPLETO DE INVENTARIO\n");
                sb.append("  Fecha de generación: ").append(fecha).append("\n");
                sb.append("  Total de registros : ").append(lista.size()).append("\n");
                sb.append("══════════════════════════════════════════════════\n\n");
                for (MaterialInventario m : lista) {
                    // String.format: %2d = entero de 2 dígitos, %-30s = texto de 30 chars alineado a izquierda
                    sb.append(String.format("  [%2d] %-30s Estado: %-12s  Cant: %d%n",
                            m.getId_matTa(), m.getNombre(), m.getEstado(), m.getCantidad()));
                }
            } else {
                // Informe filtrado por estado
                String filtro = (String) comboEstadoInforme.getSelectedItem();
                sb.append("══════════════════════════════════════════════════\n");
                sb.append("  INFORME FILTRADO — Estado: ").append(filtro).append("\n");
                sb.append("  Fecha: ").append(fecha).append("\n");
                sb.append("══════════════════════════════════════════════════\n\n");
                int count = 0;
                for (MaterialInventario m : lista) {
                    if (m.getEstado().toString().equals(filtro)) {
                        sb.append(String.format("  [%2d] %-30s  Cant: %d  Ubi: %s%n",
                                m.getId_matTa(), m.getNombre(), m.getCantidad(), m.getId_ubi()));
                        count++;
                    }
                }
                sb.append("\n  Registros filtrados: ").append(count).append("\n");
            }

            sb.append("\n══════════════════════════════════════════════════\n");
            areaPreview.setText(sb.toString()); // mostramos el informe en el área de texto
            btnExportar.setEnabled(true);       // habilitamos el botón de exportar
        });

        // ── Lógica del botón Exportar .txt ────────────────────────────────
        btnExportar.addActionListener(e -> {
            // JFileChooser: diálogo del sistema para elegir dónde guardar
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new java.io.File("informe_inventario.txt")); // nombre por defecto
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                // FileWriter escribe texto en un archivo. try-with-resources lo cierra automáticamente.
                try (java.io.FileWriter fw = new java.io.FileWriter(chooser.getSelectedFile())) {
                    fw.write(areaPreview.getText()); // escribimos el contenido del área de texto
                    JOptionPane.showMessageDialog(this,
                            "✅ Informe guardado en:\n" + chooser.getSelectedFile().getAbsolutePath(),
                            "Exportado", JOptionPane.INFORMATION_MESSAGE);
                } catch (java.io.IOException ex) {
                    JOptionPane.showMessageDialog(this,
                            "❌ Error al guardar: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.setOpaque(false);
        botones.add(btnGenerar);
        botones.add(btnExportar);

        panel.add(opciones, BorderLayout.NORTH);
        panel.add(new JScrollPane(areaPreview), BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  PANEL AÑADIR
    //  Formulario dinámico para insertar materiales en la BD.
    //  Llama a: dao.guardarMaterial(subclase)
    // ══════════════════════════════════════════════════════════════════════
    /**
     * Crea el panel contenedor para añadir materiales.
     *
     * Este panel tiene dos partes: 1. Un JComboBox para elegir el tipo de
     * material 2. Un panel dinámico que cambia según el tipo elegido
     *
     * Cuando el usuario cambia el tipo en el combo, se llama a
     * crearFormularioPorTipo() y se reemplaza el formulario interior.
     *
     * 🎨 DISEÑO: Los textos de los tipos deben coincidir con el switch en
     * crearFormularioPorTipo(). Si añades un nuevo tipo: 1. Añádelo al array
     * tipos[] 2. Añade un case en el switch de crearFormularioPorTipo() 3. Crea
     * la subclase correspondiente en el switch del ActionListener
     */
    private JPanel crearPanelAnadir() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_TRABAJO_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // ── Selector de tipo ──────────────────────────────────────────────
        JPanel barraSelector = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        barraSelector.setOpaque(false);
        barraSelector.add(etiqueta("Tipo de material:"));

        // 🎨 Cambia los textos del combo para que aparezcan con otro nombre en la interfaz
        // ⚠️ No cambies el orden sin actualizar el switch en crearFormularioPorTipo()
        String[] tipos = { "Periférico", "Cableado", "Componente",
            "Herramienta", "Material Fungible", "Equipo en red"};
        JComboBox<String> comboTipo = new JComboBox<>(tipos);
        comboTipo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        barraSelector.add(comboTipo);

        // ── Panel que contiene el formulario (cambia según el tipo) ───────
        JPanel panelFormulario = new JPanel(new BorderLayout());
        panelFormulario.setOpaque(false);
        // Cargamos el formulario base de MaterialInventario al abrir el panel
        panelFormulario.add(crearFormularioPorTipo("MaterialInventario", panelFormulario));

        // Al cambiar el combo, reconstruimos el formulario con los campos del nuevo tipo
        comboTipo.addActionListener(e -> {
            String tipo = (String) comboTipo.getSelectedItem();
            panelFormulario.removeAll();                                 // Eliminamos el formulario anterior
            panelFormulario.add(crearFormularioPorTipo(tipo, panelFormulario)); // Creamos el nuevo
            panelFormulario.revalidate(); // Recalculamos el layout
            panelFormulario.repaint();    // Redibujamos
        });

        JPanel norte = new JPanel(new BorderLayout());
        norte.setOpaque(false);
        norte.add(crearTituloPanel("Añadir nuevo material"), BorderLayout.NORTH);
        norte.add(barraSelector, BorderLayout.SOUTH);

        panel.add(norte, BorderLayout.NORTH);
        panel.add(panelFormulario, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Construye el formulario de campos específico para cada tipo de material.
     *
     * CAMPOS COMUNES (filas 0-7, presentes siempre): Nombre, Descripción,
     * Estado, Cantidad, ID Ubicación, ID Balda, Fecha alta, Observaciones
     *
     * CAMPOS ESPECÍFICOS (a partir de la fila 8, según el tipo): - Periférico:
     * conexión (combo) + ID del PC - Cableado: longitud + conector1 + conector2
     * - Componente: ID del PC - Herramienta: tipo herramienta (combo: SOLDADURA
     * / GENERALES) - Mat. Fungible: estado fungible (combo: LLENO / VACIO /
     * MEDIO) - Equipo en red: número de puertos - MaterialInventario: sin
     * campos extra
     *
     * Al pulsar "Guardar": 1. Lee todos los campos de texto 2. Intenta crear la
     * subclase correspondiente → el constructor del objeto llama al Validador
     * internamente y lanza una excepción si algo está mal 3. Si todo es válido:
     * llama a dao.guardarMaterial() 4. Si hay error: llama a
     * mostrarErrorCampo() para indicar el campo fallido
     *
     * 🎨 DISEÑO: Para cada campo puedes cambiar: - El texto de la etiqueta
     * (primer argumento de etiqueta()) - El ancho del JTextField (número entre
     * paréntesis: new JTextField(20)) - Los valores de los JComboBox
     *
     * @param tipo Nombre del tipo seleccionado en el combo
     * @param contenedor El panel padre (necesario para revalidar si se
     * reconstruye)
     */
    private JPanel crearFormularioPorTipo(String tipo, JPanel contenedor) {
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8); // 🎨 Margen entre celdas: top, left, bottom, right
        gbc.anchor = GridBagConstraints.WEST; // alineación a la izquierda
        gbc.fill = GridBagConstraints.HORIZONTAL; // los campos de texto se estiran en horizontal

        // ── CAMPOS COMUNES (filas 0 a 7) ──────────────────────────────────
        // Patrón para cada campo: etiqueta en gridx=0, campo en gridx=1
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0; // weightx=0: la etiqueta no se estira
        form.add(etiqueta("Nombre:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1; // weightx=1: el campo sí se estira
        JTextField txtNombre = new JTextField(20); // 🎨 Ancho base del campo (20 columnas)
        txtNombre.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        form.add(txtNombre, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        form.add(etiqueta("Descripción:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        JTextField txtDesc = new JTextField(20);
        txtDesc.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        form.add(txtDesc, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        // 🎨 Cambia el texto de la etiqueta para ayudar al usuario
        form.add(etiqueta("Estado (OPERATIVO / REPARACION / OBSOLETO / AVERIADO):"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        // JComboBox con los valores del enum Estados (deben ser exactamente iguales al enum)
        JComboBox<String> comboEstado = new JComboBox<>(new String[]{"OPERATIVO", "REPARACION", "OBSOLETO","AVERIADO"});
        comboEstado.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        form.add(comboEstado, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        form.add(etiqueta("Cantidad:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        JTextField txtCantidad = new JTextField(10);
        txtCantidad.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        form.add(txtCantidad, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        form.add(etiqueta("ID Ubicación (ej: A1, B2...):"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        JComboBox<String> comboUbi = new JComboBox<>(new String[]{"ARM01", "ARM02", "ARM03", "ARM04", "ARM05",
                                                                   "EST01","EST02","EST03","EST04","EST05","EST06","EST07","EST08",});
        comboUbi.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        form.add(comboUbi, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        form.add(etiqueta("ID Balda (dejar vacío si no aplica):"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        JTextField txtBalda = new JTextField(10);
        txtBalda.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        form.add(txtBalda, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0;
        form.add(etiqueta("Fecha alta (dd-MM-yyyy):"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        // Prellenamos con la fecha de hoy como valor por defecto
        JTextField txtFecha = new JTextField(LocalDate.now().format(FMT_FECHA), 10);
        txtFecha.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        form.add(txtFecha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 0;
        form.add(etiqueta("Observaciones:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        JTextField txtObs = new JTextField(20);
        txtObs.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        form.add(txtObs, gbc);

        // ── CAMPOS ESPECÍFICOS ─────────────────────────────────────────────
        // Los declaramos aquí para que sean accesibles dentro del ActionListener del botón Guardar.
        // Solo los que se añadan al form según el tipo tendrán valores introducidos.
        JComboBox<String> comboConexion = new JComboBox<>(new String[]{"INALAMBRICA", "CABLE"});
        JTextField txtIdPcPeri = new JTextField(10); // ID del PC para Periférico
        JTextField txtLongitud = new JTextField(10); // Longitud del cable en metros
        JTextField txtConector1 = new JTextField(10); // Conector de un extremo
        JTextField txtConector2 = new JTextField(10); // Conector del otro extremo
        JTextField txtIdPcComp = new JTextField(10); // ID del PC para Componente
        JComboBox<String> comboTipoH = new JComboBox<>(new String[]{"SOLDADURA", "GENERALES"}); // Tipo de herramienta
        JComboBox<String> comboEstadoF = new JComboBox<>(new String[]{"LLENO", "VACIO", "MEDIO"}); // Estado fungible
        JTextField txtNumPuertos = new JTextField(10); // Número de puertos de un switch/router

        // switch de expresión (Java 14+): añade los campos específicos según el tipo
        switch (tipo) {
            case "Periférico" -> {
                gbc.gridx = 0;
                gbc.gridy = 8;
                gbc.weightx = 0;
                form.add(etiqueta("Conexión (INALAMBRICA / CABLE):"), gbc);
                gbc.gridx = 1;
                gbc.weightx = 1;
                comboConexion.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
                form.add(comboConexion, gbc);

                gbc.gridx = 0;
                gbc.gridy = 9;
                gbc.weightx = 0;
                form.add(etiqueta("ID del PC asociado:"), gbc);
                gbc.gridx = 1;
                gbc.weightx = 1;
                txtIdPcPeri.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
                form.add(txtIdPcPeri, gbc);
            }
            case "Cableado" -> {
                gbc.gridx = 0;
                gbc.gridy = 8;
                gbc.weightx = 0;
                form.add(etiqueta("Longitud (metros, ej: 1.5):"), gbc);
                gbc.gridx = 1;
                gbc.weightx = 1;
                txtLongitud.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
                form.add(txtLongitud, gbc);

                gbc.gridx = 0;
                gbc.gridy = 9;
                gbc.weightx = 0;
                form.add(etiqueta("Conector extremo 1:"), gbc);
                gbc.gridx = 1;
                gbc.weightx = 1;
                txtConector1.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
                form.add(txtConector1, gbc);

                gbc.gridx = 0;
                gbc.gridy = 10;
                gbc.weightx = 0;
                form.add(etiqueta("Conector extremo 2:"), gbc);
                gbc.gridx = 1;
                gbc.weightx = 1;
                txtConector2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
                form.add(txtConector2, gbc);
            }
            case "Componente" -> {
                gbc.gridx = 0;
                gbc.gridy = 8;
                gbc.weightx = 0;
                form.add(etiqueta("ID del PC al que pertenece:"), gbc);
                gbc.gridx = 1;
                gbc.weightx = 1;
                txtIdPcComp.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
                form.add(txtIdPcComp, gbc);
            }
            case "Herramienta" -> {
                gbc.gridx = 0;
                gbc.gridy = 8;
                gbc.weightx = 0;
                form.add(etiqueta("Tipo (SOLDADURA / GENERALES):"), gbc);
                gbc.gridx = 1;
                gbc.weightx = 1;
                comboTipoH.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
                form.add(comboTipoH, gbc);
            }
            case "Material Fungible" -> {
                gbc.gridx = 0;
                gbc.gridy = 8;
                gbc.weightx = 0;
                form.add(etiqueta("Estado fungible (LLENO / VACIO / MEDIO):"), gbc);
                gbc.gridx = 1;
                gbc.weightx = 1;
                comboEstadoF.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
                form.add(comboEstadoF, gbc);
            }
            case "Equipo en red" -> {
                gbc.gridx = 0;
                gbc.gridy = 8;
                gbc.weightx = 0;
                form.add(etiqueta("Número de puertos:"), gbc);
                gbc.gridx = 1;
                gbc.weightx = 1;
                txtNumPuertos.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
                form.add(txtNumPuertos, gbc);
            }
            // Caso "MaterialInventario": no añade campos extra, los comunes son suficientes
        }

        // ── Botón guardar + etiqueta de estado ────────────────────────────
        JButton btnGuardar = crearBotonAccion("✅ Guardar material");
        JLabel lblMsg = new JLabel(" "); // mensaje de éxito o error (empieza vacío)
        lblMsg.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));

        // ── Lógica del botón Guardar ──────────────────────────────────────
        btnGuardar.addActionListener(e -> {
            // Leemos todos los campos comunes de texto
            String nombre = txtNombre.getText().trim();
            String desc = txtDesc.getText().trim();
            String estado = ((String) comboEstado.getSelectedItem()).trim();
            String cant = txtCantidad.getText().trim();
//            String ubi = txtUbi.getText().trim();
            String ubi = (String) comboUbi.getSelectedItem();
            String balda = txtBalda.getText().trim();
            String fecha = txtFecha.getText().trim();
            String obs = txtObs.getText().trim();

            try {
                MaterialInventario material;

                // Según el tipo seleccionado, creamos la subclase correspondiente.
                // El constructor de cada clase llama a los setters,
                // que a su vez llaman al Validador.
                // Si algo no es válido, el Validador lanza una excepción
                // y el catch correspondiente la captura.
                switch (tipo) {
                    case "Periférico" ->
                        material = new Perifericos(
                                nombre, desc, estado, cant, ubi, balda, fecha, obs,
                                txtIdPcPeri.getText().trim(),
                                (String) comboConexion.getSelectedItem()
                        );
                    case "Cableado" ->
                        material = new Cableado(
                                nombre, desc, estado, cant, ubi, balda, fecha, obs,
                                txtLongitud.getText().trim(),
                                txtConector1.getText().trim(),
                                txtConector2.getText().trim()
                        );
                    case "Componente" ->
                        material = new Componentes(
                                nombre, desc, estado, cant, ubi, balda, fecha, obs,
                                txtIdPcComp.getText().trim()
                        );
                    case "Herramienta" ->
                        material = new Herramientas(
                                nombre, desc, estado, cant, ubi, balda, fecha, obs,
                                (String) comboTipoH.getSelectedItem()
                        );
                    case "Material Fungible" ->
                        material = new Material_Fungible(
                                nombre, desc, estado, cant, ubi, balda, fecha, obs,
                                (String) comboEstadoF.getSelectedItem()
                        );
                    case "Equipo en red" ->
                        material = new Equipos_en_red(
                                nombre, desc, estado, cant, ubi, balda, fecha, obs,
                                txtNumPuertos.getText().trim()
                        );
                    default ->
                        material = new MaterialInventario(
                                nombre, desc, estado, cant, ubi, balda, fecha, obs
                        );
                }

                // Si llegamos aquí, todos los campos son válidos → guardamos en la BD
                dao.guardarMaterial(material);
                lblMsg.setForeground(COLOR_OK);
                lblMsg.setText("✅ Material \"" + nombre + "\" guardado correctamente.");

                // Limpiamos todos los JTextField del formulario para poder introducir otro
                for (java.awt.Component c : form.getComponents()) {
                    if (c instanceof JTextField tf) {
                        tf.setText(""); // Pattern matching (Java 16+)
                    }
                }
                txtFecha.setText(LocalDate.now().format(FMT_FECHA)); // Restauramos la fecha de hoy

            } catch (NombreInvalidoException ex) {
                // El Validador.validaNombre() lanzó NombreInvalidoException
                mostrarErrorCampo(lblMsg, "Nombre inválido: " + ex.getMessage(), txtNombre);
            } catch (DescripcionInvalidaException ex) {
                mostrarErrorCampo(lblMsg, "Descripción inválida: " + ex.getMessage(), txtDesc);
            } catch (EstadoInvalidoException ex) {
                mostrarErrorCampo(lblMsg, "Estado inválido: " + ex.getMessage(), null);
            } catch (CantidadInvalidaException ex) {
                mostrarErrorCampo(lblMsg, "Cantidad inválida: " + ex.getMessage(), txtCantidad);
            } catch (IdInvalidoException ex) {
                mostrarErrorCampo(lblMsg, "ID inválido: " + ex.getMessage(), null);
            } catch (FechaInvalidaException ex) {
                mostrarErrorCampo(lblMsg, "Fecha inválida (usa dd-MM-yyyy): " + ex.getMessage(), txtFecha);
            } catch (Exception ex) {
                // Captura cualquier otra excepción no prevista (red caída, BD desconectada...)
                mostrarErrorCampo(lblMsg, "Error inesperado: " + ex.getMessage(), null);
            }
        });

        // Calculamos en qué fila colocar el mensaje y el botón
        // (debe ser debajo del último campo, que varía según el tipo)
        int filaLibre = switch (tipo) {
            case "Periférico" ->
                11; // 8 comunes + 2 específicos + fila libre = 11
            case "Cableado" ->
                12; // 8 comunes + 3 específicos + fila libre = 12
            case "Componente" ->
                10; // 8 comunes + 1 específico  + fila libre = 10
            case "Herramienta" ->
                10;
            case "Material Fungible" ->
                10;
            case "Equipo en red" ->
                10;
            default ->
                9; // 8 comunes + fila libre = 9
        };

        // Mensaje de error/éxito (ocupa las 2 columnas)
        gbc.gridx = 0;
        gbc.gridy = filaLibre;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        form.add(lblMsg, gbc);

        // Botón guardar, alineado a la derecha
        gbc.gridy = filaLibre + 1;
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        botones.setOpaque(false);
        botones.add(btnGuardar);
        form.add(botones, gbc);

        // Envolvemos el formulario en un JScrollPane para que sea desplazable
        // (útil si el formulario es más alto que el área de trabajo)
        JScrollPane scroll = new JScrollPane(form);
        scroll.setBorder(null); // sin borde alrededor del scroll
        scroll.getViewport().setOpaque(false);

        JPanel envuelto = new JPanel(new BorderLayout());
        envuelto.setOpaque(false);
        envuelto.add(scroll, BorderLayout.CENTER);
        return envuelto;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  PANEL MODIFICAR
    //  ⚠️ INCOMPLETO: modificarMaterial() NO existe en el DAO.
    //  La carga de datos sí funciona. El guardado muestra un aviso con el SQL.
    // ══════════════════════════════════════════════════════════════════════
    /**
     * Crea el panel para modificar un material existente.
     *
     * Flujo de uso: 1. El usuario escribe el ID en el campo y pulsa "Cargar
     * datos" 2. El sistema busca ese ID en la BD y rellena los campos del
     * formulario 3. El usuario edita los campos que quiera 4. Pulsa "Guardar
     * cambios" → ⚠️ actualmente muestra un aviso porque falta implementar
     * modificarMaterial() en AdministradorDAO
     *
     * 🎨 DISEÑO: El aviso naranja (lblAviso) es temporal. Una vez implementes
     * modificarMaterial() en el DAO, elimina ese JLabel y sustituye el
     * contenido del ActionListener del btnGuardar por la llamada real al DAO
     * (el SQL está comentado dentro del método).
     */
    private JPanel crearPanelModificar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_TRABAJO_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(crearTituloPanel("Modificar material"), BorderLayout.NORTH);

        // ── Barra para introducir el ID ───────────────────────────────────
        JPanel barraId = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        barraId.setOpaque(false);
        barraId.add(etiqueta("ID a modificar:"));
        JTextField txtId = new JTextField(8);
        txtId.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        txtId.setPreferredSize(new Dimension(100, 30));
        JButton btnCargar = crearBotonAccion("Cargar datos");
        barraId.add(txtId);
        barraId.add(btnCargar);

        // ── Formulario de edición (deshabilitado hasta cargar un material) ─
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiquetas de los campos del formulario de edición
        // 🎨 Cambia estos textos para mostrarlos diferente en la interfaz
        String[] etiquetas = {
            "Nombre:", "Descripción:", "Estado:", "Cantidad:",
            "ID Ubicación:", "ID Balda:", "Fecha alta:", "Observaciones:"
        };
        // Array de campos de texto, uno por cada etiqueta
        JTextField[] campos = new JTextField[etiquetas.length];
        for (int i = 0; i < etiquetas.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0;
            form.add(etiqueta(etiquetas[i]), gbc);
            gbc.gridx = 1;
            gbc.weightx = 1;
            campos[i] = new JTextField(20);
            campos[i].setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
            campos[i].setEnabled(false); // deshabilitados hasta que se cargue un material
            form.add(campos[i], gbc);
        }

        // Etiqueta para mensajes de error/éxito
        JLabel lblMsg = new JLabel(" ");
        lblMsg.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));

        // ── Lógica del botón "Cargar datos" ───────────────────────────────
        btnCargar.addActionListener(e -> {
            String idTexto = txtId.getText().trim();
            if (idTexto.isEmpty()) {
                lblMsg.setForeground(COLOR_ERROR);
                lblMsg.setText("⚠ Introduce un ID.");
                return;
            }
            int id;
            try {
                id = Integer.parseInt(idTexto);
            } catch (NumberFormatException ex) {
                lblMsg.setForeground(COLOR_ERROR);
                lblMsg.setText("⚠ El ID debe ser un número.");
                return;
            }

            // Buscamos el material en la lista 
            MaterialInventario encontrado = dao.porIdMaterial(id);

            if (encontrado == null) {
                lblMsg.setForeground(COLOR_ERROR);
                lblMsg.setText("❌ No se encontró el ID " + id);
                return;
            }

            // Rellenamos los campos del formulario con los datos actuales del material
            // Los índices [0]..[7] corresponden al orden del array etiquetas[]
            campos[0].setText(encontrado.getNombre());
            campos[1].setText(encontrado.getDescripcion());
            campos[2].setText(encontrado.getEstado().toString());
            campos[3].setText(String.valueOf(encontrado.getCantidad()));
            campos[4].setText(encontrado.getId_ubi());
            // getId_balda() puede ser null → mostramos cadena vacía en ese caso
            campos[5].setText(encontrado.getId_balda() != null ? encontrado.getId_balda().toString() : "");
            campos[6].setText(encontrado.getFecha_alta().format(FMT_FECHA));
            campos[7].setText(encontrado.getObservaciones());

            // Habilitamos todos los campos para que el usuario pueda editarlos
            for (JTextField c : campos) {
                c.setEnabled(true);
            }
            lblMsg.setForeground(COLOR_OK);
            lblMsg.setText("✅ Material cargado. Edita los campos y pulsa Guardar.");
        });

        // ── Lógica del botón "Guardar cambios" ────────────────────────────
        JButton btnGuardar = crearBotonAccion("💾 Guardar cambios");
        btnGuardar.addActionListener(e -> {
            // Comprobamos que hay un material cargado (campos habilitados)
            String idTexto = txtId.getText().trim();
            if (idTexto.isEmpty() || !campos[0].isEnabled()) {
                lblMsg.setForeground(COLOR_ERROR);
                lblMsg.setText("⚠ Primero carga un material.");
                return;
            }

            try {
                MaterialInventario m = new MaterialInventario(
                        idTexto, // id_matTa
                        campos[0].getText(), // nombre
                        campos[1].getText(), // descripcion
                        campos[2].getText(), // estado
                        campos[3].getText(), // cantidad
                        campos[4].getText(), // id_ubi
                        campos[5].getText(), // id_balda
                        campos[6].getText(), // fecha_alta
                        campos[7].getText() // observaciones
                );
                dao.actualizarPorID(m);
                lblMsg.setForeground(COLOR_OK);
                lblMsg.setText("✅ Material modificado correctamente.");
            } catch (IdInvalidoException ex) {
                lblMsg.setForeground(COLOR_ERROR);
                lblMsg.setText("⚠ ID inválido: " + ex.getMessage());
            } catch (NombreInvalidoException ex) {
                lblMsg.setForeground(COLOR_ERROR);
                lblMsg.setText("⚠ Nombre inválido: " + ex.getMessage());
            } catch (DescripcionInvalidaException ex) {
                lblMsg.setForeground(COLOR_ERROR);
                lblMsg.setText("⚠ Descripción inválida: " + ex.getMessage());
            } catch (EstadoInvalidoException ex) {
                lblMsg.setForeground(COLOR_ERROR);
                lblMsg.setText("⚠ Estado inválido: " + ex.getMessage());
            } catch (CantidadInvalidaException ex) {
                lblMsg.setForeground(COLOR_ERROR);
                lblMsg.setText("⚠ Cantidad inválida: " + ex.getMessage());
            } catch (FechaInvalidaException ex) {
                lblMsg.setForeground(COLOR_ERROR);
                lblMsg.setText("⚠ Fecha inválida: " + ex.getMessage());
            }
        });

        // ── Montaje del panel ──────────────────────────────────────────────
        JPanel norte = new JPanel(new BorderLayout(0, 8));
        norte.setOpaque(false);
        norte.add(barraId, BorderLayout.SOUTH); // barra de ID debajo del aviso

        JPanel sur = new JPanel(new BorderLayout());
        sur.setOpaque(false);
        sur.add(lblMsg, BorderLayout.WEST); // mensaje de estado a la izquierda
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setOpaque(false);
        btnPanel.add(btnGuardar);
        sur.add(btnPanel, BorderLayout.EAST); // botón a la derecha

        panel.add(norte, BorderLayout.NORTH);
        panel.add(new JScrollPane(form), BorderLayout.CENTER); // formulario con scroll
        panel.add(sur, BorderLayout.SOUTH);
        return panel;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  PANEL ELIMINAR
    //  Elimina un material de la BD por su ID.
    //  Llama a: dao.eliminarMaterial(id)
    // ══════════════════════════════════════════════════════════════════════
    /**
     * Crea el panel para eliminar un material de la BD.
     *
     * Flujo: 1. El usuario introduce el ID 2. Pulsa "Eliminar definitivamente"
     * (o Enter en el campo) 3. Aparece un JOptionPane de confirmación (¿Estás
     * seguro?) 4. Si confirma: llama a dao.eliminarMaterial(id) y limpia el
     * campo
     *
     * ⚠️ La BD elimina en cascada los registros de las tablas hijas gracias a
     * la configuración de clave foránea (ON DELETE CASCADE).
     *
     * 🎨 DISEÑO: Puedes cambiar: - El color del botón de eliminar (actualmente
     * rojo: new Color(200, 60, 60)) - El texto del aviso y del botón - El
     * mensaje de confirmación del JOptionPane
     */
    private JPanel crearPanelEliminar() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(COLOR_TRABAJO_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(crearTituloPanel("Eliminar material"), BorderLayout.NORTH);

        // GridBagLayout para centrar todo en el panel
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Aviso de acción irreversible (fila 0, ocupa 2 columnas)
        JLabel lblAviso = new JLabel("⚠️  Esta acción elimina el registro de forma permanente.");
        lblAviso.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
        lblAviso.setForeground(COLOR_ERROR); // 🎨 Color del texto de aviso de eliminación
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centro.add(lblAviso, gbc);

        // Campo de texto para el ID (fila 1, 2 columnas)
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        centro.add(etiqueta("ID del material a eliminar:"), gbc);
        gbc.gridx = 1;
        JTextField txtId = new JTextField(12);
        txtId.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));
        txtId.setPreferredSize(new Dimension(140, 30));
        centro.add(txtId, gbc);

        // Etiqueta de mensaje de estado (fila 2)
        JLabel lblMsg = new JLabel(" ");
        lblMsg.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        centro.add(lblMsg, gbc);

        // Botón de eliminar con estilo rojo llamativo
        // 🎨 Cambia el color de este botón: new Color(200, 60, 60) = rojo
        JButton btnEliminar = new JButton("🗑️  Eliminar definitivamente");
        btnEliminar.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13));
        btnEliminar.setBackground(new Color(200, 60, 60)); // 🎨 Fondo rojo del botón eliminar
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setOpaque(true);
        btnEliminar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnEliminar.setPreferredSize(new Dimension(240, 36)); // 🎨 Tamaño del botón eliminar

        // ── Lógica del botón Eliminar ─────────────────────────────────────
        btnEliminar.addActionListener(e -> {
            String idTexto = txtId.getText().trim();
            if (idTexto.isEmpty()) {
                lblMsg.setForeground(COLOR_ERROR);
                lblMsg.setText("⚠ Introduce un ID válido.");
                return;
            }
            int id;
            try {
                id = Integer.parseInt(idTexto);
            } catch (NumberFormatException ex) {
                lblMsg.setForeground(COLOR_ERROR);
                lblMsg.setText("⚠ El ID debe ser un número entero.");
                return;
            }

            // Pedimos confirmación antes de una acción irreversible
            // showConfirmDialog devuelve YES_OPTION o NO_OPTION
            int conf = JOptionPane.showConfirmDialog(this,
                    "¿Eliminar el material con ID " + id + "?\nEsta acción no se puede deshacer.",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE); // icono de advertencia ⚠️

            if (conf == JOptionPane.YES_OPTION) {
                dao.eliminarMaterial(id); // eliminación real en la BD
                lblMsg.setForeground(COLOR_OK);
                lblMsg.setText("✅ Material con ID " + id + " eliminado.");
                txtId.setText(""); // limpiamos el campo
            }
        });

        // Atajo: pulsar Enter en el campo dispara el botón
        txtId.addActionListener(e -> btnEliminar.doClick());

        gbc.gridy = 3;
        centro.add(btnEliminar, gbc);
        panel.add(centro, BorderLayout.CENTER);
        return panel;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  PANELES PC
    //  Paneles para listar y añadir PCs
    // ══════════════════════════════════════════════════════════════════════
    private JPanel crearPanelListarPCs() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));

        panel.setBackground(COLOR_TRABAJO_BG);

        panel.setBorder(new EmptyBorder(20, 20, 20, 20)
        );

        panel.add(crearTituloPanel("Listado de PCs"), BorderLayout.NORTH);

        String[] cols = {
            "ID",
            "Nombre",
            "Estado",
            "Categoría",
            "Estación"
        };

        java.util.List<Pc> lista = pcDao.listarPc();

        Object[][] datos = new Object[lista.size()][cols.length];

        for (int i = 0; i < lista.size(); i++) {

            Pc pc = lista.get(i);
            datos[i][0] = pc.getId_pc();
            datos[i][1] = pc.getNombre();
            datos[i][2] = pc.getEstado();
            datos[i][3] = pc.getCategoria();
            datos[i][4] = pc.getId_estacion();
        }

        panel.add(new JScrollPane(crearTabla(datos, cols)), BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelAnadirPC() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_TRABAJO_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;
        JTextField txtNombre = new JTextField(20);
        JTextField txtDesc = new JTextField(20);

        JComboBox<String> comboEstado
                = new JComboBox<>(
                        new String[]{
                            "OPERATIVO",
                            "REPARACION",
                            "OBSOLETO"
                        }
                );

        JComboBox<String> comboCategoria
                = new JComboBox<>(
                        new String[]{
                            "PORTATIL",
                            "SOBREMESA"
                        }
                );

        JComboBox<String> comboEstacion
                = new JComboBox<>();

        for (String est : ubiDao.listarEstaciones()) {
            comboEstacion.addItem(est);
        }

        JTextField txtFecha = new JTextField(LocalDate.now().format(FMT_FECHA), 10);

        JTextField txtObs = new JTextField(20);

        int y = 0;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(etiqueta("Nombre:"), gbc);

        gbc.gridx = 1;
        panel.add(txtNombre, gbc);

        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(etiqueta("Descripción:"), gbc);

        gbc.gridx = 1;
        panel.add(txtDesc, gbc);

        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(etiqueta("Estado:"), gbc);

        gbc.gridx = 1;
        panel.add(comboEstado, gbc);

        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(etiqueta("Categoría:"), gbc);

        gbc.gridx = 1;
        panel.add(comboCategoria, gbc);

        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(etiqueta("Estación:"), gbc);

        gbc.gridx = 1;
        panel.add(comboEstacion, gbc);

        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(etiqueta("Fecha:"), gbc);

        gbc.gridx = 1;
        panel.add(txtFecha, gbc);

        y++;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(etiqueta("Observaciones:"), gbc);

        gbc.gridx = 1;
        panel.add(txtObs, gbc);

        JButton btnGuardar
                = crearBotonAccion(
                        "💾 Guardar PC"
                );

        y++;

        gbc.gridx = 1;
        gbc.gridy = y;

        panel.add(btnGuardar, gbc);

        btnGuardar.addActionListener(e -> {
            try {
                Pc pc = new Pc(
                        txtNombre.getText(),
                        txtDesc.getText(),
                        comboEstado
                                .getSelectedItem()
                                .toString(),
                        comboCategoria
                                .getSelectedItem()
                                .toString(),
                        comboEstacion
                                .getSelectedItem()
                                .toString(),
                        txtFecha.getText(),
                        txtObs.getText()
                );

                pcDao.guardarPc(pc);

            } catch (CategoriaInvalidaException | DescripcionInvalidaException | EstadoInvalidoException | FechaInvalidaException | IdInvalidoException | NombreInvalidoException ex) {
                LoggerApp.log(
                        "❌ Error creando PC: "
                        + ex.getMessage()
                );
            }
        });

        return panel;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  PANEL WEB
    //  Abre la web local del proyecto en el navegador predeterminado del sistema.
    // ══════════════════════════════════════════════════════════════════════
    /**
     * Crea el panel "Web del proyecto" que abre la web local en el navegador.
     *
     * Funcionamiento: - Usa Desktop.getDesktop().browse(URI) que es la forma
     * estándar de abrir el navegador del sistema en Java - Si Desktop no está
     * soportado (algunos Linux headless), tiene un fallback con
     * Runtime.exec(xdg-open) que abre el navegador en Linux - El campo "URL:"
     * permite cambiar la URL sin tocar el código (pero se reinicia al abrir el
     * panel de nuevo)
     *
     * 🎨 DISEÑO: Puedes cambiar: - El emoji del icono grande (🌐) - Los textos
     * de información - El tamaño del botón "Abrir web": setPreferredSize(new
     * Dimension(240, 38)) - El valor permanente de la URL: constante
     * URL_WEB_LOCAL al inicio del archivo
     */
    private JPanel crearPanelWeb() {
        // GridBagLayout sin restricciones: centra el contenido en el panel
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COLOR_TRABAJO_BG);

        // BoxLayout en Y: apila los elementos verticalmente y los centra
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setOpaque(false);

        // Emoji grande como icono visual
        // 🎨 Cambia el emoji por otro: 🖥️, 🔗, 🌍, etc.
        JLabel lblIcono = new JLabel("🌐", SwingConstants.CENTER);
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48)); // 🎨 Tamaño del icono emoji
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Título del panel
        JLabel lblTitulo = new JLabel("Web del proyecto");
        lblTitulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20)); // 🎨 Tamaño del título
        lblTitulo.setForeground(new Color(60, 60, 80));          // 🎨 Color del título
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Muestra la URL configurada
        JLabel lblUrl = new JLabel(URL_WEB_LOCAL);
        lblUrl.setFont(new Font("Segoe UI Emoji", Font.ITALIC, 13));
        lblUrl.setForeground(new Color(100, 100, 180)); // 🎨 Color del texto de la URL (azul suave)
        lblUrl.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Texto de ayuda
        JLabel lblInfo = new JLabel(
                "<html><center>Se abrirá tu navegador predeterminado en la URL indicada.<br>"
                + "Asegúrate de que el servidor local esté en ejecución.</center></html>");
        lblInfo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        lblInfo.setForeground(new Color(130, 130, 160)); // 🎨 Color del texto de ayuda
        lblInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón principal de apertura del navegador
        JButton btnAbrir = crearBotonAccion("🚀 Abrir web en el navegador");
        btnAbrir.setPreferredSize(new Dimension(240, 38)); // 🎨 Tamaño del botón de abrir web
        btnAbrir.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Etiqueta de estado (éxito o error al abrir)
        JLabel lblMsg = new JLabel(" ");
        lblMsg.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        lblMsg.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ── Lógica del botón Abrir ────────────────────────────────────────
        btnAbrir.addActionListener(e -> {
            try {
                if (Desktop.isDesktopSupported()
                        && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    // Forma estándar: funciona en Windows, macOS y la mayoría de Linux con GUI
                    Desktop.getDesktop().browse(new URI(URL_WEB_LOCAL));
                    lblMsg.setForeground(COLOR_OK);
                    lblMsg.setText("✅ Navegador abierto en " + URL_WEB_LOCAL);
                } else {
                    // Fallback para Linux sin soporte Desktop (xdg-open es el comando estándar de Linux)
                    Runtime.getRuntime().exec(new String[]{"xdg-open", URL_WEB_LOCAL});
                    lblMsg.setForeground(COLOR_OK);
                    lblMsg.setText("✅ Abierto con xdg-open.");
                }
            } catch (Exception ex) {
                lblMsg.setForeground(COLOR_ERROR);
                lblMsg.setText("❌ No se pudo abrir el navegador: " + ex.getMessage());
                JOptionPane.showMessageDialog(this,
                        "No se pudo abrir el navegador.\n"
                        + "URL: " + URL_WEB_LOCAL + "\n"
                        + "Error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // ── Campo para cambiar la URL en tiempo de ejecución ─────────────
        // Útil para probar sin recompilar. El cambio no es permanente
        // (se reinicia al volver a abrir el panel o la app).
        // 🎨 Si no quieres este campo, elimina el panelUrl y su contenido.add(panelUrl)
        JPanel panelUrl = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        panelUrl.setOpaque(false);
        JLabel lblCambia = new JLabel("URL:");
        lblCambia.setFont(new Font("Segoe UI Emoji", Font.BOLD, 12));
        JTextField txtUrlEditable = new JTextField(URL_WEB_LOCAL, 22);
        txtUrlEditable.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        btnActualizar.addActionListener(e -> {
            String nuevaUrl = txtUrlEditable.getText().trim();
            if (!nuevaUrl.isEmpty()) {
                lblUrl.setText(nuevaUrl); // actualizamos el label visible
                // Añadimos un nuevo listener al botón Abrir con la URL nueva
                // Nota: esto acumula listeners; para evitarlo, se necesitaría
                // eliminar el listener anterior con removeActionListener()
                btnAbrir.addActionListener(ev -> {
                    try {
                        Desktop.getDesktop().browse(new URI(nuevaUrl));
                    } catch (Exception ex) {
                        lblMsg.setForeground(COLOR_ERROR);
                        lblMsg.setText("❌ " + ex.getMessage());
                    }
                });
                lblMsg.setForeground(new Color(100, 100, 180));
                lblMsg.setText("URL actualizada a: " + nuevaUrl);
            }
        });
        panelUrl.add(lblCambia);
        panelUrl.add(txtUrlEditable);
        panelUrl.add(btnActualizar);

        // Apilamos todos los elementos con espacios entre ellos
        contenido.add(lblIcono);
        contenido.add(Box.createVerticalStrut(12)); // 🎨 Espacio debajo del icono
        contenido.add(lblTitulo);
        contenido.add(Box.createVerticalStrut(6));
        contenido.add(lblUrl);
        contenido.add(Box.createVerticalStrut(10));
        contenido.add(lblInfo);
        contenido.add(Box.createVerticalStrut(18)); // 🎨 Espacio antes del botón
        contenido.add(btnAbrir);
        contenido.add(Box.createVerticalStrut(8));
        contenido.add(lblMsg);
        contenido.add(Box.createVerticalStrut(14));
        contenido.add(panelUrl);

        panel.add(contenido);
        return panel;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  MÉTODOS UTILITARIOS DE UI
    //  Métodos reutilizados por varios paneles para crear componentes
    //  con un aspecto consistente en toda la aplicación.
    // ══════════════════════════════════════════════════════════════════════
    /**
     * Crea el título de un panel con texto grande y una línea separadora
     * debajo.
     *
     * Se usa al inicio de cada panel de trabajo para identificarlo.
     *
     * 🎨 DISEÑO: Cambia aquí para modificar el aspecto de TODOS los títulos de
     * panel: - Fuente: new Font("Segoe UI Emoji", Font.BOLD, 18) - Color del texto:
     * new Color(50, 50, 70) - Color de la línea: new Color(200, 190, 230) -
     * Separación inferior: new EmptyBorder(0, 0, 14, 0)
     *
     * @param texto El texto del título a mostrar
     */
    private JPanel crearTituloPanel(String texto) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(0, 0, 14, 0)); // 🎨 Margen debajo del título (14px)
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18)); // 🎨 Tamaño del título de panel
        lbl.setForeground(new Color(50, 50, 70));         // 🎨 Color del título de panel
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(200, 190, 230));       // 🎨 Color de la línea bajo el título
        p.add(lbl, BorderLayout.CENTER);
        p.add(sep, BorderLayout.SOUTH);
        return p;
    }

    /**
     * Crea un JLabel con el estilo de etiqueta de formulario (negrita).
     *
     * Se usa para los textos a la izquierda de los campos en los formularios.
     *
     * 🎨 DISEÑO: Cambia la fuente o el tamaño para modificar TODAS las
     * etiquetas: - Font.BOLD → negrita (actual) - Font.PLAIN → normal (más
     * sutil) - Tamaño 13 → aumenta para etiquetas más grandes
     *
     * @param texto El texto de la etiqueta
     */
    private JLabel etiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13)); // 🎨 Fuente de las etiquetas de formulario
        return lbl;
    }

    /**
     * Crea un botón de acción con el estilo morado de la aplicación.
     *
     * Se usa para todos los botones de acción dentro de los paneles (Guardar,
     * Buscar, Actualizar, Generar, Exportar...). El efecto hover (oscurece al
     * pasar el ratón) está incluido.
     *
     * 🎨 DISEÑO: Cambia aquí para modificar el aspecto de TODOS los botones de
     * acción: - Color normal: COLOR_MENU_ACTIVO (morado) - Color hover:
     * COLOR_MENU_HOVER (morado más oscuro) - Fuente: new Font("Segoe UI Emoji",
     * Font.BOLD, 13) - Tamaño: setPreferredSize(new Dimension(180, 34)) Los
     * 180px son el ancho base; cada botón puede sobreescribirlo con otro
     * setPreferredSize()
     *
     * @param texto El texto del botón
     */
    private JButton crearBotonAccion(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 13)); // 🎨 Fuente de los botones de acción
        btn.setBackground(COLOR_MENU_ACTIVO);             // 🎨 Color de fondo (morado)
        btn.setForeground(Color.WHITE);                   // 🎨 Color del texto (blanco)
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(180, 34)); // 🎨 Ancho y alto del botón de acción
        // Efecto hover: oscurece al pasar el ratón
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(COLOR_MENU_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(COLOR_MENU_ACTIVO);
            }
        });
        return btn;
    }

    /**
     * Crea una JTable con el estilo visual de la aplicación.
     *
     * Características: - Las celdas NO son editables (solo lectura) →
     * isCellEditable devuelve false - Se puede ordenar por columna haciendo
     * clic en la cabecera (setAutoCreateRowSorter) - Filas de 28px de alto
     *
     * 🎨 DISEÑO: Cambia aquí para modificar el aspecto de TODAS las tablas: -
     * Altura de fila: setRowHeight(28) → sube para filas más altas - Fuente del
     * cuerpo: new Font("Segoe UI Emoji", Font.PLAIN, 13) - Fuente de la cabecera: new
     * Font("Segoe UI Emoji", Font.BOLD, 13) - Color de selección:
     * setSelectionBackground(new Color(200, 190, 240)) - Color de las líneas de
     * la cuadrícula: setGridColor(new Color(220, 220, 230))
     *
     * @param datos Array 2D con los datos de las filas
     * @param cols Array con los nombres de las columnas
     */
    private JTable crearTabla(Object[][] datos, String[] cols) {
        JTable tabla = new JTable(datos, cols) {
            // Sobrescribimos isCellEditable para que ninguna celda sea editable
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tabla.setRowHeight(28);                                          // 🎨 Alto de cada fila
        tabla.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 13));            // 🎨 Fuente del contenido
        tabla.getTableHeader().setFont(new Font("Segoe UI Emoji", Font.BOLD, 13)); // 🎨 Fuente de la cabecera
        tabla.setSelectionBackground(new Color(200, 190, 240));          // 🎨 Color al seleccionar una fila
        tabla.setGridColor(new Color(220, 220, 230));                    // 🎨 Color de las líneas de la rejilla
        tabla.setAutoCreateRowSorter(true); // permite ordenar columnas haciendo clic en la cabecera
        return tabla;
    }

    /**
     * Muestra un mensaje de error en una etiqueta de estado y mueve el foco al
     * campo de texto que causó el error.
     *
     * Se usa en los catch de los formularios para indicar visualmente qué campo
     * está incorrecto.
     *
     * Comportamiento: - Cambia el color del lblMsg a rojo (COLOR_ERROR) - Pone
     * el mensaje de error (ex.getMessage() viene del Validador) - Si se pasa un
     * campo: mueve el cursor al campo y selecciona su texto (así el usuario
     * puede corregirlo directamente sin hacer clic)
     *
     * @param lblMsg La etiqueta donde mostrar el mensaje de error
     * @param mensaje El texto del error (normalmente ex.getMessage())
     * @param campo El JTextField con el valor incorrecto (puede ser null si no
     * aplica)
     */
    private void mostrarErrorCampo(JLabel lblMsg, String mensaje, JTextField campo) {
        lblMsg.setForeground(COLOR_ERROR);       // 🎨 Color de los mensajes de error
        lblMsg.setText("❌ " + mensaje);
        if (campo != null) {
            campo.requestFocus(); // mueve el cursor al campo con error
            campo.selectAll();    // selecciona todo el texto del campo para fácil corrección
        }
    }

}
