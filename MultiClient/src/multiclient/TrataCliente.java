/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiclient;

import java.io.InputStream;
import java.io.*;
import Controller.ControllerPacotes;
import Controller.ControllerGerenciador;
import Controller.ControllerCorrida;
import model.*;

/**
 *
 * @author gabriel
 */
public class TrataCliente implements Runnable {
    private ControllerPacotes pacotes = null;
    private static ControllerGerenciador gerenciador = null;
    private static ControllerCorrida   corridaController = null;
    private String acao;
    private InputStream cliente;    
    private Servidor servidor;
    
    public TrataCliente(InputStream cliente, Servidor servidor){
        this.cliente  = cliente;
        this.servidor = servidor;
        this.pacotes  = new ControllerPacotes();
        this.gerenciador = new ControllerGerenciador();
        this.corridaController = new ControllerCorrida();
    } 

    @Override
    public void run() {
        DataInputStream entrada = new DataInputStream(cliente);
        boolean start = true;
        while (start) {
            try {
                String pacote = entrada.readUTF();
                System.out.println(pacote);
                acao = pacotes.acao(pacote); //devolve a ação que deve ser realizada.
                switch(acao){
                    case "11": //cadastro de carro
                        Carro car = gerenciador.cadastrarCarro(pacotes.transformarCarro(pacote));
                        servidor.distribuiMensagem(car.toString(), acao);
                        break;
                    case "12": //cadastro de piloto
                        gerenciador.cadastrarPiloto(pacotes.transformarPiloto(pacote));
                        
                        break;
                    case "13": //cadastro de adm
                        gerenciador.cadastrarAdministrador(pacotes.transformarAdm(pacote));
                        break;
                    case "21": //remoção de piloto
                        gerenciador.removerPiloto(pacotes.transformarPiloto(pacote).getNome());
                        break;
                    
                    case "22": //remoção de carro
                        String dadosCarro[] = pacote.split(";");
                        gerenciador.removerCarro(dadosCarro[1]); //remove carro do sistema
                        break;
                        
                        
                    case "30": //cadastro de corrida feito por um Adm
                        String voltasCorrida[] = pacote.split(";");
                        int voltas = pacotes.strToInt(voltasCorrida[1], 10); //caso a conversão dê errado, um valor padrão de 10 voltas é colocado.
                        corridaController.cadastrarCorrida(voltas);
                        break;
                     
                    case "31": //iniciar corrida
                        break;
                        
                    case "32": //cadastro de corredor para a nova corrida
                        corridaController.salvarCompetidor(pacotes.transformarCarro(pacote));
                        break;
                        
                    case "33": //pausar
                        corridaController.pausar_reiniciar();
                        break;
        
                    case "34":  //reiniciar
                        corridaController.pausar_reiniciar();
                        break;
                    case "00": //sai da thread do cliente (desconectar)
                        start = false;
                        break;
                        
                    case "41": //contagem de voltas do sensor
                        
                         break;
                        
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }  
}    
