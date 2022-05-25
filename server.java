import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
//import java.util.Scanner;

public class server {
    public static void main(String[] args) throws Exception {
        System.out.println("Iniciando servidor....");
            
        try {
            ServerSocket socketServidor = new ServerSocket(34442);
            System.out.println("esperando cliente...");
            


            Socket conectCliente = socketServidor.accept();
            System.out.println("Conectado...");

            ObjectOutputStream saidaDados = new ObjectOutputStream(conectCliente.getOutputStream());
            saidaDados.flush();
            saidaDados.writeObject("Está funcionando!");
            System.out.println("Dados enviados.");
            saidaDados.close();
            conectCliente.close();
            socketServidor.close();
                
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}

