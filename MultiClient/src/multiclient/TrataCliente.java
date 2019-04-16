/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiclient;

import java.io.InputStream;
//import java.util.Scanner;
import java.io.*;
import Controller.ControllerPacotes;
/**
 *
 * @author gabriel
 */
public class TrataCliente implements Runnable {
    private ControllerPacotes pacotes = null;
    private String acao;
    private InputStream cliente;    
    private Servidor servidor;
    
    public TrataCliente(InputStream cliente, Servidor servidor){
        this.cliente  = cliente;
        this.servidor = servidor;
        this.pacotes  = new ControllerPacotes();
    } 

    @Override
    public void run() {
        DataInputStream entrada = new DataInputStream(cliente);
        // quando chegar uma msg, distribui pra todos
        //Scanner s = new Scanner(this.cliente)
        while (true) {
            try {
                //System.out.println(entrada.readUTF());
                String pacote = entrada.readUTF();
                acao = pacotes.acao(pacote); //devolve a ação que deve ser realizada.
                switch(acao){
                    case "11": //cadastro de carro
                        pacotes.transformarCarro(pacote);
                        break;
                    case "12": //cadastro de piloto
                        break;
                    case "13": //cadastro de adm
                        break;
                    case "21": //remoção de piloto
                        break;
                    case "22": //remoção de carro
                        break;
                    case "31": //cadastro de corrida
                        break;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }  
}    
