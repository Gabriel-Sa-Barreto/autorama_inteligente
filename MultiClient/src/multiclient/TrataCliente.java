/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiclient;

import Controller.ControllerArquivo;
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
    private static ControllerCorrida corridaController = null;
    private String acao;
    private InputStream cliente;    
    private Servidor servidor;
    
    public TrataCliente(InputStream cliente, Servidor servidor , ControllerGerenciador gerenciador){
        this.cliente  = cliente;
        this.servidor = servidor;
        this.pacotes  = new ControllerPacotes();
        this.gerenciador = gerenciador;
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
                        gerenciador.cadastrarCarro(pacotes.transformarCarro(pacote));
                        servidor.distribuiMensagem(pacote);
                        ControllerArquivo.escreverCarro("C:\\Users\\lsjsa\\OneDrive\\Área de Trabalho\\Codigo\\Pbl Redes\\autorama_inteligente\\Arquivos\\carros.txt" , pacotes.transformarCarro(pacote) );
                        break;
                    case "12": //cadastro de piloto
                        gerenciador.cadastrarPiloto(pacotes.transformarPiloto(pacote));
                        servidor.distribuiMensagem(pacote);
                        ControllerArquivo.escreverPiloto("C:\\Users\\lsjsa\\OneDrive\\Área de Trabalho\\Codigo\\Pbl Redes\\autorama_inteligente\\Arquivos\\pilotos.txt" , pacotes.transformarPiloto(pacote));
                        break;
                    case "13": //cadastro de adm
                        gerenciador.cadastrarAdministrador(pacotes.transformarAdm(pacote));
                        servidor.distribuiMensagem(pacote);
                        ControllerArquivo.escreverAdm("C:\\Users\\lsjsa\\OneDrive\\Área de Trabalho\\Codigo\\Pbl Redes\\autorama_inteligente\\Arquivos\\adms.txt" , pacotes.transformarAdm(pacote));
                        break;
                    case "21": //remoção de piloto
                        gerenciador.removerPiloto(pacotes.transformarPiloto(pacote).getNome());
                        servidor.distribuiMensagem(pacote);
                        ControllerArquivo.removerPiloto("C:\\Users\\lsjsa\\OneDrive\\Área de Trabalho\\Codigo\\Pbl Redes\\autorama_inteligente\\Arquivos\\pilotos.txt" , pacotes.transformarPiloto(pacote));
                        break;
                    case "22": //remoção de carro
                        String dadosCarro[] = pacote.split(";");
                        gerenciador.removerCarro(dadosCarro[1]); //remove carro do sistema
                        servidor.distribuiMensagem(pacote);
                        ControllerArquivo.removerCarro("C:\\Users\\lsjsa\\OneDrive\\Área de Trabalho\\Codigo\\Pbl Redes\\autorama_inteligente\\Arquivos\\carros.txt" ,dadosCarro[1]);
                        break;
                    case "30": //cadastro de corrida feito por um Adm
                        String voltasCorrida[] = pacote.split(";");
                        int voltas = pacotes.strToInt(voltasCorrida[1], 10); //caso a conversão dê errado, um valor padrão de 10 voltas é colocado.
                        corridaController.cadastrarCorrida(voltas);
                        servidor.distribuiMensagem(pacote);
                        break;
                    case "31": //comecar corrida
                        corridaController.comecarPartida();
                        servidor.distribuiMensagem(pacote);
                        break;
                    case "32": //cadastro de corredor para a nova corrida
                        if(!corridaController.comecouCorrida()){
                            corridaController.salvarCompetidor(pacotes.transformarCarro(pacote));
                            servidor.distribuiMensagem(pacote);
                        }
                        break;
                    case "33": //pausar
                        corridaController.pausar_reiniciar();
                        servidor.distribuiMensagem(pacote);
                        break;
                    case "34":  //reiniciar
                        corridaController.pausar_reiniciar();
                        servidor.distribuiMensagem(pacote);
                        break;
                    case "00": //sai da thread do cliente (desconectar)
                        start = false;
                        break;
                    case "41": //contagem de voltas do sensor
                        servidor.distribuiMensagem(pacote);
                        break;
                    case "100":
                       gerenciador.atualizarCliente(servidor);
                       break;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }  
}    
