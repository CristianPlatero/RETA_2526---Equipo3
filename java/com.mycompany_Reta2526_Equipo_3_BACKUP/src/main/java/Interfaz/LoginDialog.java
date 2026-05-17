package Interfaz;

import Usuarios.Administrador;
import Usuarios.GestionUsuarios;
import Usuarios.Usuario;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * =====================================================================
 *  VENTANA DE LOGIN
 * =====================================================================
 *  Es un JDialog (no un JFrame) porque es una ventana auxiliar modal:
 *  bloquea la ejecución hasta que el usuario inicia sesión o cancela.
 *
 *  Flujo:
 *    1. Main crea LoginDialog y llama a setVisible(true) → bloquea.
 *    2. Usuario escribe credenciales y pulsa "Entrar".
 *    3. Si son correctas, se guarda el rol en this.rolAutenticado.
 *    4. Main recupera getRolAutenticado() y abre InventarioApp con ese rol.
 *
 *  Credenciales hardcodeadas (para un proyecto de 1º de Java):
 *    · admin   / admin123   → Rol.ADMINISTRADOR
 *    · profesor / profe123  → Rol.PROFESOR
 *
 *  TODO (para proyectos más avanzados):
 *    - Mover la validación a una clase UsuarioDAO que consulte BD o fichero.
 *    - Guardar contraseñas como hash (BCrypt, SHA-256), nunca en plano.
 * =====================================================================
 */
public class LoginDialog extends JDialog {

    // ── Colores (misma paleta que InventarioApp para coherencia) ──────
    private static final Color COLOR_FONDO      = new Color(30, 30, 46);
    private static final Color COLOR_TARJETA    = new Color(44, 44, 64);
    private static final Color COLOR_ACENTO     = new Color(114, 90, 200);
    private static final Color COLOR_HOVER      = new Color(90, 90, 140);
    private static final Color COLOR_ERROR      = new Color(220, 80, 80);
    private static final Color COLOR_TEXTO      = Color.WHITE;
    private static final Color COLOR_GRIS       = new Color(180, 180, 200);
    private static final Color COLOR_CAMPO_BG   = new Color(55, 55, 80);
    private static final Color COLOR_CAMPO_BORDE = new Color(90, 90, 130);

    // ── Resultado del diálogo ──────────────────────────────────────────
    // null  → usuario canceló o cerró la ventana sin autenticarse
    // valor → rol del usuario autenticado
    private Rol rolAutenticado = null;

    // ── Componentes que necesitamos fuera de sus métodos ──────────────
    private JTextField     txtUsuario;
    private JPasswordField txtContrasena;
    private JLabel         lblError;

    // ======================================================================
    //  CONSTRUCTOR
    // ======================================================================
    /**
     * @param parent  Ventana padre (puede ser null si aún no existe JFrame)
     */
    public LoginDialog(Frame parent) {
        // true = modal: bloquea al padre hasta que este diálogo se cierre
        super(parent, "Iniciar sesión", true);

        construirUI();
        pack();                          // ajusta el tamaño al contenido
        setResizable(false);
        setLocationRelativeTo(parent);   // centra en pantalla
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    // ======================================================================
    //  CONSTRUCCIÓN DE LA INTERFAZ
    // ======================================================================
    private void construirUI() {
        // Fondo general del diálogo
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(COLOR_FONDO);
        fondo.setBorder(new EmptyBorder(30, 40, 30, 40));
        setContentPane(fondo);

        // ── Tarjeta central ────────────────────────────────────────────
        JPanel tarjeta = new JPanel();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBackground(COLOR_TARJETA);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 120), 1),
            new EmptyBorder(30, 35, 30, 35)
        ));

        // Icono / emoji de acceso
        JLabel lblIcono = new JLabel("🔐", SwingConstants.CENTER);
        lblIcono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Título
        JLabel lblTitulo = new JLabel("Acceso al sistema");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtítulo
        JLabel lblSub = new JLabel("Inventario Taller");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(COLOR_GRIS);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ── Campo usuario ──────────────────────────────────────────────
        JLabel lblU = crearEtiqueta("Usuario");
        txtUsuario = crearCampoTexto("admin  ó  profesor");

        // ── Campo contraseña ───────────────────────────────────────────
        JLabel lblP = crearEtiqueta("Contraseña");
        txtContrasena = new JPasswordField(18);
        estilizarCampo(txtContrasena, "••••••••");
        // Permite confirmar con Enter en el campo contraseña
        txtContrasena.addActionListener(e -> intentarLogin());

        // ── Mensaje de error (oculto hasta que falle) ──────────────────
        lblError = new JLabel(" ");   // espacio para que no colapse el layout
        lblError.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblError.setForeground(COLOR_ERROR);
        lblError.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ── Botón entrar ───────────────────────────────────────────────
        JButton btnEntrar = crearBotonEntrar();

        // ── Hint de credenciales (útil en fase de desarrollo) ─────────
        // TODO: eliminar este bloque cuando el proyecto sea definitivo
        JLabel lblHint = new JLabel("<html><center><font color='#9090aa'>"
            + "admin / admin123 &nbsp;|&nbsp; profesor / profe123"
            + "</font></center></html>");
        lblHint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblHint.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ── Montaje de la tarjeta ──────────────────────────────────────
        tarjeta.add(lblIcono);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(lblTitulo);
        tarjeta.add(Box.createVerticalStrut(4));
        tarjeta.add(lblSub);
        tarjeta.add(Box.createVerticalStrut(22));
        tarjeta.add(lblU);
        tarjeta.add(Box.createVerticalStrut(4));
        tarjeta.add(txtUsuario);
        tarjeta.add(Box.createVerticalStrut(14));
        tarjeta.add(lblP);
        tarjeta.add(Box.createVerticalStrut(4));
        tarjeta.add(txtContrasena);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(lblError);
        tarjeta.add(Box.createVerticalStrut(16));
        tarjeta.add(btnEntrar);
        tarjeta.add(Box.createVerticalStrut(18));
        tarjeta.add(lblHint);

        fondo.add(tarjeta);
    }

    // ======================================================================
    //  LÓGICA DE AUTENTICACIÓN
    // ======================================================================
    /**
     * Valida las credenciales introducidas.
     * Si son correctas guarda el rol y cierra el diálogo.
     * Si no, muestra el mensaje de error sin cerrar.
     *
     * TODO: reemplaza este método por una consulta a BD o fichero cuando
     *       tu proyecto lo requiera. La firma no cambia, solo el interior.
     */
    private void intentarLogin() {
        String usuario    = txtUsuario.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        // ── Tabla de credenciales ──────────────────────────────────────
        // Para añadir más usuarios añade más else-if aquí,
        // o mejor aún: extrae esto a un método validar(usuario, pass) → Rol
        
        GestionUsuarios gestor= new GestionUsuarios();
        Usuario u = gestor.login(usuario, contrasena);
        
        if (u != null){
            if(u instanceof Administrador){
            rolAutenticado = Rol.ADMINISTRADOR;    
            }
            dispose();
            
            
            
        }else {
            lblError.setText("Usuario o contraseña incorrectos.");
            txtContrasena.setText("");
            txtUsuario.requestFocus();
            
            // Sacudir la ventana (efecto "shake") para indicar error
            sacudirVentana();
            
        }
        
       
    }

    /**
     * Efecto visual de sacudida horizontal cuando el login falla.
     * Usa un Timer de Swing para no bloquear el hilo de eventos.
     */
    private void sacudirVentana() {
        final int[] pasos = {0};
        final int AMPLITUD = 8;   // píxeles de desplazamiento
        final int PASOS_TOTAL = 10;
        Point posOriginal = getLocation();

        Timer timer = new Timer(30, null);
        timer.addActionListener(e -> {
            if (pasos[0] >= PASOS_TOTAL) {
                setLocation(posOriginal);   // volver a posición original
                timer.stop();
                return;
            }
            // Mover izquierda/derecha alternativamente
            int dx = (pasos[0] % 2 == 0) ? AMPLITUD : -AMPLITUD;
            setLocation(posOriginal.x + dx, posOriginal.y);
            pasos[0]++;
        });
        timer.start();
    }

    // ======================================================================
    //  GETTER — el Main usa esto para saber quién ha iniciado sesión
    // ======================================================================
    /**
     * @return El rol del usuario autenticado, o null si canceló.
     */
    public Rol getRolAutenticado() {
        return rolAutenticado;
    }

    // ======================================================================
    //  UTILIDADES DE UI
    // ======================================================================
    private JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(COLOR_GRIS);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JTextField crearCampoTexto(String placeholder) {
        JTextField campo = new JTextField(18);
        estilizarCampo(campo, placeholder);
        // Permite confirmar con Enter también en el campo usuario
        campo.addActionListener(e -> intentarLogin());
        return campo;
    }

    /** Aplica el estilo oscuro a cualquier JTextField o JPasswordField */
    private void estilizarCampo(JTextField campo, String placeholder) {
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBackground(COLOR_CAMPO_BG);
        campo.setForeground(COLOR_TEXTO);
        campo.setCaretColor(COLOR_TEXTO);
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_CAMPO_BORDE, 1),
            new EmptyBorder(6, 10, 6, 10)
        ));
        campo.setPreferredSize(new Dimension(260, 38));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Efecto placeholder: texto gris que desaparece al escribir
        campo.setText(placeholder);
        campo.setForeground(COLOR_GRIS);
        campo.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(COLOR_TEXTO);
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(COLOR_GRIS);
                }
            }
        });
    }

    private JButton crearBotonEntrar() {
        JButton btn = new JButton("Entrar →");
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(COLOR_ACENTO);
        btn.setForeground(COLOR_TEXTO);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(COLOR_HOVER); }
            @Override public void mouseExited(MouseEvent e)  { btn.setBackground(COLOR_ACENTO); }
        });

        btn.addActionListener(e -> intentarLogin());
        return btn;
    }
}
