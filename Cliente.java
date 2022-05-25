import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Cliente {
    /*variaveis*/

    //vars comum

    String dadosOut = null;
    static boolean isConectado = true;

    // vars swing
    protected static JButton botEnviar, botChecarConec, botReceberMensagens;
    protected static JScrollPane scroll;
    protected static JTextArea areaDeTexto;
    protected static JLabel checarConec, mensagemRecebida;
    protected static JPanel painelPrinc;

    private static void aplicativo(int width, int height) {
        JFrame janelaPrinc = new JFrame();
        janelaPrinc.setVisible(true);
        janelaPrinc.setSize(width, height);
        janelaPrinc.setTitle("maximizar a janela, por favor");
        janelaPrinc.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        

        JPanel painelPrinc = new JPanel();
        painelPrinc.setSize(1000, 1000);


        botEnviar = new JButton();
        botEnviar.setText("Enviar");
        botEnviar.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent conex) {
                String dadosRecebidos = enviar(areaDeTexto.getText());
                mensagemRecebida.setText(dadosRecebidos);
            }
        });
        

        botChecarConec = new JButton();
        botChecarConec.setText("Check conexão");
        botChecarConec.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent checkConex) {
               testarConexao();
               checarConec.setText(testarConexao());
            }
        });

        botReceberMensagens = new JButton();
        botReceberMensagens.setText("Reiniciar servidor");
        botReceberMensagens.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent reiniciarServer) {
                if ( isConectado == false) {
                    server.inicServer();
                } else {
                    System.exit(0);
                } 
            }
        });
        

        
        checarConec = new JLabel();
        checarConec.setSize(100, 10);
        checarConec.setText("");

        mensagemRecebida = new JLabel();
        mensagemRecebida.setSize(100, 10);
        mensagemRecebida.setText("");

        areaDeTexto = new JTextArea();
        areaDeTexto.setColumns(10);
        areaDeTexto.setRows(6);

        
        

        scroll = new JScrollPane();;
        

        painelPrinc.add(botEnviar);
        painelPrinc.add(areaDeTexto);
        painelPrinc.add(botChecarConec);
        painelPrinc.add(checarConec);
        painelPrinc.add(botReceberMensagens);
        painelPrinc.add(mensagemRecebida);
        painelPrinc.add(scroll);
        
        janelaPrinc.setContentPane(painelPrinc);
        janelaPrinc.pack();
    }

    private static String testarConexao() {
        if (isConectado == true) {
            return "Conectado com sucesso";
        }else {
            return "erro na conexão";
        }

    }

    private static String dadosRecebidos(String dadosConvertidos) {
        return dadosConvertidos;

    }

    private static String enviar(String dadosOut) {
        System.out.println("iniciando cliente...");

        try {
            Socket cliente = new Socket("LocalHost", 34442);
            ObjectOutputStream saidaDados = new ObjectOutputStream(cliente.getOutputStream());
            saidaDados.flush();
            saidaDados.writeObject(dadosOut);
            

            Socket receber = new Socket("LocalHost", 40000);
            ObjectInputStream entradaDados = new ObjectInputStream(receber.getInputStream());
            Object leitorDeDados = entradaDados.readObject();
            String dadosConvertidos = leitorDeDados.toString();
            System.out.println(dadosConvertidos);

            
            areaDeTexto.setText("");
            isConectado = false;

            entradaDados.close();
            saidaDados.close();
            receber.close();
            cliente.close();
            return dadosRecebidos(dadosConvertidos);

        } catch (UnknownHostException u) {
            System.out.println(u); 
            return null;

        } catch (IOException i) {
            System.out.println(i);
            return null;

        } catch (ClassNotFoundException cnf) {
            System.out.println(cnf);
            return null;
        } finally {
            
        }
    }
    public static void main(String[] args) throws Exception {
        Cliente.aplicativo(1000,1000);
        server.inicServer();

    }
}
