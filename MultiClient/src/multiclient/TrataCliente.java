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
import model.Piloto;

/**
 *
 * @author gabriel
 */
public class TrataCliente implements Runnable {
    /**
     * Controller para gerenciar tratar os dados recebidos no pacote recebido.
     */
    private ControllerPacotes pacotes = null;
    
    /**
     * Controller para gerenciar as ações que devem ser feitas de acordo aos pacotes recebidos.
     */
    private static ControllerGerenciador gerenciador = null;
    
    /**
     * Controller para gerenciar as ações ou eventos de uma corrida.
     */
    private static ControllerCorrida   corridaController = null;
    
    /**
     * Atributo que informa qual ação deve ser realizada.
     */
    private String acao;
    
    /**
     * InputStream do cliente.
     */
    private InputStream cliente;

    /**
     * Objeto do Servidor para realizar ações de envio de mensagem a todos os cliente conectados.
     */
    private Servidor servidor;
    
    /**
     * Construtor que inicializa todos os atributos da classe.
     * @param cliente  - InputStream do cliente conectado.
     * @param servidor - Objeto servidor
     * @param gerenciador - Controller para gerenciar as ações que devem ser feitas de acordo aos pacotes recebidos.
     */
    public TrataCliente(InputStream cliente, Servidor servidor , ControllerGerenciador gerenciador){
        this.cliente  = cliente;
        this.servidor = servidor;
        this.pacotes  = new ControllerPacotes();
        this.gerenciador = gerenciador;
        this.corridaController = new ControllerCorrida();
    } 

    /**Método da interface Runnable responsável por executar todas as atividades da thread quando for iniciada,
     * sua principal função é receber os pacotes do cliente e verificar qual ação deve ser realizada apartir dos dados recebidos. 
     * @author Samuel Vitorio Lima e Gabriel Sá Barreto 
     */
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
                        ControllerArquivo.escreverCarro("/home/gabriel/Documentos/Pbl Redes/autorama_inteligente/Arquivos/carros.txt" , pacotes.transformarCarro(pacote) );
                        break;
                    case "12": //cadastro de piloto
                        gerenciador.cadastrarPiloto(pacotes.transformarPiloto(pacote));
                        servidor.distribuiMensagem(pacote);
                        ControllerArquivo.escreverPiloto("/home/gabriel/Documentos/Pbl Redes/autorama_inteligente/Arquivos/pilotos.txt" , pacotes.transformarPiloto(pacote));
                        break;
                    case "13": //cadastro de adm
                        gerenciador.cadastrarAdministrador(pacotes.transformarAdm(pacote));
                        servidor.distribuiMensagem(pacote);
                        ControllerArquivo.escreverAdm("/home/gabriel/Documentos/Pbl Redes/autorama_inteligente/Arquivos/adms.txt" , pacotes.transformarAdm(pacote));
                        break;
                    case "21": //remoção de piloto
                        gerenciador.removerPiloto(pacotes.transformarPiloto(pacote).getNome());
                        servidor.distribuiMensagem(pacote);
                        ControllerArquivo.removerPiloto("/home/gabriel/Documentos/Pbl Redes/autorama_inteligente/Arquivos/pilotos.txt" , pacotes.transformarPiloto(pacote));
                        break;
                    case "22": //remoção de carro
                        String dadosCarro[] = pacote.split(";");
                        gerenciador.removerCarro(dadosCarro[1]); //remove carro do sistema
                        servidor.distribuiMensagem(pacote);
                        ControllerArquivo.removerCarro("/home/gabriel/Documentos/Pbl Redes/autorama_inteligente/Arquivos\\carros.txt" ,dadosCarro[1]);
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
                    case "50":
                        Piloto record = gerenciador.bateuRecord(pacotes.transformarRecord(pacote), pacotes.nomePiloto(pacote));
                        if(record != null){
                            ControllerArquivo.removerPiloto("/home/gabriel/Documentos/Pbl Redes/autorama_inteligente/Arquivos/pilotos.txt" ,record);
                            ControllerArquivo.escreverPiloto("/home/gabriel/Documentos/Pbl Redes/autorama_inteligente/Arquivos/pilotos.txt" , record);
                        }
                        servidor.distribuiMensagem(pacote);
                        break;
                    case "00": //sai da thread do cliente (desconectar)
                        start = false;
                        break;
                    case "01":
                        start = false;
                        if(corridaController.comecouCorrida())
                            corridaController.terminarCorrida();
                        break;
                    case "41": //contagem de voltas do sensor
                        servidor.distribuiMensagem(pacote);
                        break;   
                    case "100"://atualização dos dados para os clientes
                       gerenciador.atualizarCliente(servidor);
                       break;    
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }  
}    
