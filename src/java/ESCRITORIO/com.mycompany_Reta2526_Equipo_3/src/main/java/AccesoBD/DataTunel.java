/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AccesoBD;




import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;  // ← Session de JSch, NO de MySQL

public class DataTunel {

    private static final String SSH_USER     = "mv2_servidor_de_servicios";
    private static final String SSH_HOST     = "10.0.10.69";
    private static final int    SSH_PORT     = 22;
    private static final String SSH_PASSWORD = "Usuario@1";
    private static final String DB_HOST      = "192.168.10.10";
    private static final int    DB_PORT      = 3306;
    private static final int    LOCAL_PORT   = 5433;

    private Session sshSession;  // ← com.jcraft.jsch.Session

    public void abrirTunel() throws Exception {
        JSch jsch = new JSch();
        sshSession = jsch.getSession(SSH_USER, SSH_HOST, SSH_PORT);
        sshSession.setPassword(SSH_PASSWORD);
        sshSession.setConfig("StrictHostKeyChecking", "no");
        sshSession.connect(5000);
        sshSession.setPortForwardingL(LOCAL_PORT, DB_HOST, DB_PORT);
        System.out.println("Túnel SSH abierto: localhost:" + LOCAL_PORT + " -> " + DB_HOST + ":" + DB_PORT);
    }

    public void cerrarTunel() {
        if (sshSession != null && sshSession.isConnected()) {
            sshSession.disconnect();
            System.out.println("Túnel SSH cerrado.");
        }
    }

    public boolean estaConectado() {
        return sshSession != null && sshSession.isConnected();
    }
}