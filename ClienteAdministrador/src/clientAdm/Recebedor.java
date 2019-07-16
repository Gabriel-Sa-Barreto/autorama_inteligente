package clientAdm;

import controller.ControllerCorrida;
import controller.ControllerGerenciador;
import controller.ControllerPacote;
import java.io.*;
import model.Administrador;

/**
 *
 * @author gabriel
 */
public class Recebedor implements Runnable {

    /**
     * Atributo que receberá o Inputstream do servidor conectado.
     */
    private InputStream servidor;
    
    /**
     * Atributo que configura se as atividades da serão executadas.
     */
    private boolean start;
    
    /**
     * Atributo responsável pelo pacotes recebidos do servidor.
     */
    private ControllerPacote pacotes = null;
    
    /**Atributo responsável por controlar ações que devem ser tomadas apartir do pacote recebido.
     */
    private static ControllerGerenciador gerenciador = null;
    
    /**Atributo responsável por controlar ações da corrida e ver seu status.
     */
    private static ControllerCorrida corrida = null;
    
    /**Construtor da classe Recebedor que inicia todos os atributos. 
     * @author Samuel Vitorio Lima e Gabriel Sá Barreto 
     * @param servidor - Atributo responsável por recebdor o objeto InputStream do servidor
     */
    public Recebedor(InputStream servidor) {
        this.servidor = servidor;
        this.start    = true;
        pacotes = new ControllerPacote();
        gerenciador = new ControllerGerenciador();
        corrida = new ControllerCorrida();
    }

    /**Método responsável por configurar se as atividades que a classe executa serão implementadas pelo
     * método run da interace Runnable.
     * @author Samuel Vitorio Lima e Gabriel Sá Barreto 
     * @param start 
     */
    public void setStart(boolean start) {
        this.start = start;
    }
    
    /**Método da interface Runnable responsável por executar todas as atividades da thread quando for iniciada.
     * @author Samuel Vitorio Lima e Gabriel Sá Barreto 
     */
    @Override
    public void run() {
        while(start){
            // recebe msgs do servidor e imprime na tela    
            String pacote , opcao;
            DataInputStream entrada = new DataInputStream(this.servidor);
            try{
                pacote = entrada.readUTF();
                opcao = pacotes.acao(pacote);
                switch(opcao){
                    case "09": //pacote com ID do carro para um possível cadastro.
                        String iDCarro = pacotes.pegaIDCarro(pacote);
                        ControllerGerenciador.setIdCarroCad(iDCarro);
                    case "11"://cadastrar carro
                        if(!gerenciador.existeCarro(pacotes.transformarCarro(pacote).getId())){
                            //caso o carro não esteja cadastrado no sistema.
                            gerenciador.salvarCarro(pacotes.transformarCarro(pacote));
                        }
                        break;
                    case "12"://cadastrar piloto
                        if(!gerenciador.existePiloto(pacotes.transformarPiloto(pacote).getNome())){
                            //caso o piloto não esteja cadastrado no sistema.
                            gerenciador.salvarPiloto(pacotes.transformarPiloto(pacote));
                        }
                        break;
                    case "13"://cadastrar adm
                        Administrador adm = pacotes.transformarAdm(pacote);
                        if(!gerenciador.existeAdm(adm.getNome(), adm.getSenha())){
                            //caso o adm não esteja cadastrado no sistema.
                            gerenciador.salvarAdm(adm);
                        }
                        break;
                    case "21"://remover piloto
                        gerenciador.removerPiloto(pacotes.transformarPiloto(pacote).getNome());
                        break;
                    case "22"://remover carro
                        gerenciador.removerCarro(pacotes.transformarCarro(pacote).getId());
                        break;
                    case "30"://salvar corrida
                        corrida.salvarCorrida(pacotes.transformarCorrida(pacote));
                        break;
                    case "31"://aviso que uma corrida começou
                        corrida.comecarPartida();
                        break;
                    case "32"://salvar competidor
                        corrida.salvarCompetidor(pacotes.transformarCarro(pacote));
                        break;
                    case "50"://verifica se bateu record
                        gerenciador.bateuRecord(pacotes.transformarRecord(pacote), pacotes.nomePiloto(pacote));
                        break;
                }
            }catch(Exception ex){
                System.out.println(ex.toString());
            }
        }
    }
}

