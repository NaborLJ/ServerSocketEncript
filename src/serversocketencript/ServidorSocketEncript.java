package serversocketencript;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.swing.JOptionPane;

/**
 *
 * @author Nabor
 */
public class ServidorSocketEncript{

    public static void main(String[] args) throws IOException {
        System.setProperty("javax.net.ssl.keyStore", "keystore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "password");
        String texto;
        String mensaje = null;
        Scanner entrada = new Scanner(System.in);
        try {
            System.out.println("Obteniendo factoria de sockets servidor");
            SSLServerSocketFactory serverSocketFactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            
            System.out.println("Creando socket servidor");
            SSLServerSocket serverSocket=(SSLServerSocket)serverSocketFactory.createServerSocket();

            System.out.println("Realizando la unión");
            InetSocketAddress addr = new InetSocketAddress("localhost",5555);
            serverSocket.bind(addr);

            System.out.println("Aceptando conexiones");

            SSLSocket newSocket=(SSLSocket) serverSocket.accept();

            System.out.println("Conexión recibida");

            DataInputStream is = new DataInputStream(newSocket.getInputStream());
            DataOutputStream os = new DataOutputStream(newSocket.getOutputStream());

            mensaje = is.readUTF();
            System.out.println("Mensaje recibido: " + new String(mensaje));

            while (!"Apagar".equals(mensaje)) {
                System.out.println("Introducir mensaje /Servidor/");
                texto = entrada.next();
                //JOptionPane.showInputDialog("Introducir mensaje /Servidor/");
                os.writeUTF(texto);
                os.flush();

                mensaje = is.readUTF();
                System.out.println("Mensaje recibido: " + (mensaje));
            }
            System.out.println("Cerrando el nuevo socket");

            newSocket.close();

            System.out.println("Cerrando el socket servidor");

            serverSocket.close();

            System.out.println("Terminado");

        } catch (IOException e) {
        }
    }

}