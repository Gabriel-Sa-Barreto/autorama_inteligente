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
                byte receber[] = new byte[100];
                int tamanho = entrada.read(receber);
                if(tamanho != 0){
                    String pacote = new String(receber, "ASCII");
                    acao = pacotes.acao(pacote); //devolve a ação que deve ser realizada.
                    switch(acao){
                        case "10": //requerimento de leitura do sensor para o cadastro de um carro.
                            ControllerGerenciador.setLerIDCarro(true);                    
                            //System.out.println("recebeu pacote 10");
                            break;
                        case "11": //cadastro de carro
                            gerenciador.cadastrarCarro(pacotes.transformarCarro(pacote));
                            servidor.distribuiMensagem(pacote);
                            ControllerArquivo.escreverCarro("../Arquivos/carros.txt" , pacotes.transformarCarro(pacote) );
                            //C:\\Users\\lsjsa\\OneDrive\\Área de Trabalho\\Codigo\\PBL Redes\\autorama_inteligente\\Arquivos\\carros.txt
                            break;
                        case "12": //cadastro de piloto
                            gerenciador.cadastrarPiloto(pacotes.transformarPiloto(pacote));
                            servidor.distribuiMensagem(pacote);
                            ControllerArquivo.escreverPiloto("../Arquivos/pilotos.txt" , pacotes.transformarPiloto(pacote));
                            break;
                        case "13": //cadastro de adm
                            gerenciador.cadastrarAdministrador(pacotes.transformarAdm(pacote));
                            servidor.distribuiMensagem(pacote);
                            ControllerArquivo.escreverAdm("../Arquivos/adms.txt" , pacotes.transformarAdm(pacote));
                            break;
                        case "21": //remoção de piloto
                            gerenciador.removerPiloto(pacotes.transformarPiloto(pacote).getNome());
                            //System.out.println("Foi");
                            servidor.distribuiMensagem(pacote);
                            ControllerArquivo.removerPiloto("../Arquivos/pilotos.txt" , pacotes.transformarPiloto(pacote));
                            break;
                        case "22": //remoção de carro
                            String dadosCarro[] = pacote.split(";");
                            gerenciador.removerCarro(dadosCarro[1]); //remove carro do sistema
                            servidor.distribuiMensagem(pacote);
                            ControllerArquivo.removerCarro("../Arquivos/carros.txt" ,dadosCarro[1]);
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
                                ControllerArquivo.removerPiloto("../Arquivos/pilotos.txt" ,record);
                                ControllerArquivo.escreverPiloto("../Arquivos/pilotos.txt" , record);
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
                            if(ControllerGerenciador.isLerIDCarro()){//caso um pedido de leitura do sensor para ler o ID de um carro foi requerido pelo adm.
                                String ID = "09;" + pacotes.pegaIDCarro(pacote);
                                servidor.distribuiMensagem(ID);
                                ControllerGerenciador.setLerIDCarro(false); //depois de atendido o pedido, configura novamente como false.
                            }
                            servidor.distribuiMensagem(pacote);
                            break;   
                        case "100"://atualização dos dados para os clientes
                           gerenciador.atualizarCliente(servidor);
                           break;    
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }  
}    
