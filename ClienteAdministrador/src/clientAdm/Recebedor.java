package clientAdm;

import controller.ControllerCorrida;
import controller.ControllerGerenciador;
import controller.ControllerPacote;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author gabriel
 */
public class Recebedor implements Runnable {

    private InputStream servidor;
    
    public Recebedor(InputStream servidor) {
        this.servidor = servidor;
    }
    
    public void run() {
        // recebe msgs do servidor e imprime na tela    
        String pacote , opcao;
        ControllerPacote pacotes = new ControllerPacote();
        ControllerGerenciador gerenciador = new ControllerGerenciador();
        ControllerCorrida corrida = new ControllerCorrida();
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
                case "31"://salvar corrida
                    corrida.salvarCorrida(pacotes.trabsformarCorrida(pacote));
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

