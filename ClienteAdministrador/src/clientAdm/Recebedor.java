package clientAdm;

import controller.ControllerCorrida;
import controller.ControllerGerenciador;
import controller.ControllerPacote;
import java.io.*;

/**
 *
 * @author gabriel
 */
public class Recebedor implements Runnable {

    private InputStream servidor;
    private boolean start;
    private ControllerPacote pacotes = null;
    private static ControllerGerenciador gerenciador = null;
    private static ControllerCorrida corrida = null;
    
    public Recebedor(InputStream servidor) {
        this.servidor = servidor;
        this.start    = true;
        pacotes = new ControllerPacote();
        gerenciador = new ControllerGerenciador();
        corrida = new ControllerCorrida();
    }

    public void setStart(boolean start) {
        this.start = start;
    }
    
    @Override
    public void run() {
        while(start){
            // recebe msgs do servidor e imprime na tela    
            String pacote , opcao;
            DataInputStream entrada = new DataInputStream(this.servidor);
            try{
                pacote = entrada.readUTF();
                System.out.println(pacote);
                opcao = pacotes.acao(pacote);
                switch(opcao){
                    case "11": //cadastrar carro
                        gerenciador.salvarCarro(pacotes.transformarCarro(pacote));
                        break;
                    case "12": //cadastrar piloto
                        gerenciador.salvarPiloto(pacotes.transformarPiloto(pacote));
                        break;
                    case "13"://cadastrar adm
                        gerenciador.salvarAdm(pacotes.transformarAdm(pacote));
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
                }
            }catch(Exception ex){
                System.out.println(ex.toString());
            }
        }
    }
}

