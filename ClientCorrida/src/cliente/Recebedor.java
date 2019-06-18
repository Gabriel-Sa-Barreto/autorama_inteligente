package cliente;

import controller.ControllerCorrida;
import controller.ControllerPacotes;
import model.Volta;
import java.io.*;
import java.net.Socket;

/**
 *
 * @author gabriel
 */
public class Recebedor implements Runnable {

    /**
     * Atributo que receberá o Inputstream do servidor conectado.
     */
    private InputStream servidorIN;
    private Socket servidorOUT;
    /**
     * Atributo que configura se as atividades da serão executadas.
     */
    private boolean start;
    
    /**
     * Atributo responsável pelo pacotes recebidos do servidor.
     */
    private ControllerPacotes pacotes = null;
    
    /**Atributo responsável por controlar ações da corrida e ver seu status.
     */
    private static ControllerCorrida corrida = null;
    
    /**Construtor da classe Recebedor que inicia todos os atributos. 
     * @author Samuel Vitorio Lima e Gabriel Sá Barreto 
     * @param entrada - Atributo responsável por recebdor o objeto InputStream do servidor
     * @param saida - Atributo responsável por recebdor o objeto Socket do servidor
     */
    public Recebedor(InputStream entrada , Socket saida) {
        this.servidorIN = entrada;
        this.servidorOUT = saida;
        this.start    = true;
        pacotes = new ControllerPacotes();
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
            String pacote , opcao;
            DataInputStream entrada = new DataInputStream(this.servidorIN);
            // recebe msgs do servidor e imprime na tela    
            try{
                pacote = entrada.readUTF();
                opcao = pacotes.acao(pacote);
                switch(opcao){
                    case "30":
                        corrida.receberCorrida(pacotes.transformarCorrida(pacote));  
                        break;
                    case "31":
                        corrida.comecarPartida();
                        break;
                    case "32":
                        corrida.adicionarCompetidor(pacotes.transformarCarro(pacote, corrida.competidores()));
                        break;
                    case "33":
                        corrida.pausar_reiniciarCorrida();
                        break;
                    case "34":
                        corrida.pausar_reiniciarCorrida();
                        break;
                    case "41":
                        Volta volta = pacotes.transformarVolta(pacote, corrida.competidores());
                        if(volta != null && !corrida.estaPausado() && corrida.partidaEmAdamento()){
                            System.out.println("Foi");
                            ControllerCorrida.setPacoteSensor(true);
                            corrida.voltaCompleta(volta , servidorOUT);
                            ControllerCorrida.setPacoteSensor(false);
                        }
                        break;
                }
            }catch(Exception ex){
                System.out.println(ex.toString());
            }
        }
    }
}

