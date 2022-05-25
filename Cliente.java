import java.io.IOException;
import java.io.ObjectInputStream;
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
    protected static JButton botEnviar, botChecarConec;
    protected static JScrollPane scroll;
    protected static JTextArea areaDeTexto;
    protected static JLabel receberMensagens;
    protected static JPanel painelPrinc;

    private static void aplicativo() {
        JFrame janelaPrinc = new JFrame();
        janelaPrinc.setVisible(true);
        janelaPrinc.setBounds(0, 0, 600, 1000);

        JPanel painelPrinc = new JPanel();
        painelPrinc.setBounds(0, 0, 600, 1000);
        painelPrinc.setBackground(new Color(0,0,0));

        botEnviar = new JButton();
        botEnviar.setText("Enviar");
        botEnviar.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent conex) {
                enviar(areaDeTexto.getText());
            }
        });
        

        botChecarConec = new JButton();
        botChecarConec.setText("Check conexão");
        botChecarConec.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent checkConex) {
                enviar(areaDeTexto.getText());
            }
        });

        
        receberMensagens = new JLabel();
        receberMensagens.setSize(25, 10);
        receberMensagens.setText(testarConexao());

        areaDeTexto = new JTextArea();
        areaDeTexto.setColumns(20);
        areaDeTexto.setRows(6);

        
        

        scroll = new JScrollPane();;
        

        painelPrinc.add(botEnviar);
        painelPrinc.add(areaDeTexto);
        painelPrinc.add(botChecarConec);
        painelPrinc.add(receberMensagens);
        painelPrinc.add(scroll);
        
        janelaPrinc.setContentPane(painelPrinc);
        janelaPrinc.pack();
    }

    private static String testarConexao() {
        if (isConectado == true) {
            return "conectado com sucesso";
        }else {
            return "erro na conexão";
        }

    }

    private static String enviar(String dadosOut) {
        System.out.println("iniciando cliente...");

        try {
            Socket cliente = new Socket("LocalHost", 34442);

            ObjectInputStream entradaDados = new ObjectInputStream(cliente.getInputStream());
            Object leitorDeDados = entradaDados.readObject();
            String dadosConvertidos = leitorDeDados.toString();
            System.out.println(dadosConvertidos);
            cliente.close();
            entradaDados.close();
            areaDeTexto.setText("");
            isConectado = false;
            return null;

        } catch (UnknownHostException u) {
            System.out.println(u); 
            return null;

        } catch (IOException i) {
            System.out.println(i);
            return null;

        } catch (ClassNotFoundException cnf) {
            System.out.println(cnf);
            return null;
        }
    }
    public static void main(String[] args) throws Exception {
        Cliente.aplicativo();

    }
}
