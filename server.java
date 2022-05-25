import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
//import java.util.Scanner;

public class server {

    public static void inicServer() {
        System.out.println("Iniciando servidor....");
            
        try {
            ServerSocket socketServidor = new ServerSocket(34442);
            System.out.println("esperando cliente...");
            ServerSocket enviarServidor = new ServerSocket(40000);


            Socket conectCliente = socketServidor.accept();
            Socket enviarCliente = enviarServidor.accept();

            
            System.out.println("Conectado...");
            ObjectInputStream entradaDados = new ObjectInputStream(conectCliente.getInputStream());
            Object entradaDeDados = entradaDados.readObject();
            
            ObjectOutputStream saidaDados = new ObjectOutputStream(enviarCliente.getOutputStream());
            saidaDados.flush();
            saidaDados.writeObject(entradaDeDados);
            System.out.println("Dados enviados.");
            entradaDados.close();
            saidaDados.close();
            conectCliente.close();
            socketServidor.close();
            enviarCliente.close();
            enviarServidor.close();
                
        } catch (IOException i) {
            System.out.println("server "+i);
        } catch (ClassNotFoundException z) {
            System.out.println("server "+z);
        }
    }
    public static void main(String[] args) throws Exception {
        server.inicServer();
    }
}

