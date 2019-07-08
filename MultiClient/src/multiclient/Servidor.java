/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiclient;

import Controller.ControllerArquivo;
import Controller.ControllerGerenciador;
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
    
    /**Atributo que guarda a porta de conexão do servidor.
     */
    private int porta;
    
    /**Atributo que guarda a lista de cliente conectados com o servidor.
     */
    private List<PrintStream> clientes;
    
    /**Atributo que guarda o objeto ServerSocket do servidor.
     */
    private ServerSocket servidor;
    
    /**Atributo reponsável por gerenciar as ações que devem ser feitas de acordo aos pacotes recebidos pelos clientes.
     */
    private static ControllerGerenciador gerenciador = new ControllerGerenciador();
    
    /**Construtor que inicializa os atributos da classe
     * @param porta  -parâmetro que informa a porta de conexão do servidor
     * @throws java.io.IOException     
    */
    public Servidor (int porta) throws IOException{
        this.porta = porta; //Porta ao qual o servidor irá se associar.
        this.clientes = new ArrayList<PrintStream>();
        criarConexão();
        ControllerArquivo.leitorAdm("/home/tec502/Documentos/autorama_inteligente-sam-sensor/Arquivos/adms.txt",gerenciador);
        ControllerArquivo.leitorCarro("/home/tec502/Documentos/autorama_inteligente-sam-sensor/Arquivos/carros.txt",gerenciador);
        ControllerArquivo.leitorPiloto("/home/tec502/Documentos/autorama_inteligente-sam-sensor/Arquivos/pilotos.txt",gerenciador);
        new Thread(this).start(); //executa a thread do servidor 
    }
    
    /***Método que starta o servidor.
     * @author Gabriel Sá Barreto Alves e Samuel Vitorio Lima
     */
    private void criarConexão() throws IOException{
        servidor = new ServerSocket(this.porta);
        System.out.println("Porta 12345 aberta!");    
    }

    /**Método da interface Runnable responsável por executar todas as atividades da thread quando for iniciada.
     * @author Samuel Vitorio Lima e Gabriel Sá Barreto 
     */
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
               tc = new TrataCliente(cliente.getInputStream(), this , gerenciador);
            } catch (IOException ex) {
               ex.printStackTrace();
            }
            //nova thread que irá tratar um novo cliente que foi conectado.
            new Thread(tc).start();
        }
    }
    
    /**Método que envia a todos os cliente conectados pacotes específicos para armazenamento de informações e outros dados.
     */
    public synchronized void distribuiMensagem(String msg) throws IOException {
        // envia msg a todos os clientes conectados
        for (PrintStream cliente : this.clientes) {
            DataOutputStream saida = new DataOutputStream(cliente);
            saida.writeUTF(msg);
        }
    }
}
