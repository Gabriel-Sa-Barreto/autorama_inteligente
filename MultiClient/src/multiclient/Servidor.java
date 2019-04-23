/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiclient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gabriel
 */
public class Servidor implements Runnable {
    private int porta;
    private List<PrintStream> clientes;
    private ServerSocket servidor;
    
    public Servidor (int porta) throws IOException{
        this.porta = porta; //Porta ao qual o servidor irá se associar.
        this.clientes = new ArrayList<PrintStream>();
        criarConexão();
        new Thread(this).start(); //executa a thread do servidor 
    }
    
    private void criarConexão() throws IOException{
        servidor = new ServerSocket(this.porta);
        System.out.println("Porta 12345 aberta!");    
    }

    @Override
    public void run() {
        while (true) {
            //aceita um cliente
            Socket cliente = null;
            try {
                cliente = servidor.accept();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());
            
            // adiciona saida do cliente à lista
            PrintStream ps = null;
            try {
                ps = new PrintStream(cliente.getOutputStream());
            } catch (IOException ex) {
               ex.printStackTrace();
            }
            this.clientes.add(ps);
            
            // cria tratador de cliente numa nova thread
            TrataCliente tc = null;
            try {
               tc = new TrataCliente(cliente.getInputStream(), this);
            } catch (IOException ex) {
               ex.printStackTrace();
            }
            //nova thread que irá tratar um novo cliente que foi conectado.
            new Thread(tc).start();
        }
    }
    
    public synchronized void distribuiMensagem(String msg) throws IOException {
        // envia msg para todo mundo
        for (PrintStream cliente : this.clientes) {
            //cliente.println(opcao +";"+msg);
            //System.out.println("entra");
            DataOutputStream saida = new DataOutputStream(cliente);
            saida.writeUTF(msg);
        }
    }
}
