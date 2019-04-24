package cliente;

import controller.ControllerCorrida;
import controller.ControllerPacotes;
import controller.ControllerRecord;
import model.Volta;
import java.io.*;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabriel
 */
public class Recebedor implements Runnable {

    private InputStream servidor;
    private boolean start;
    private ControllerPacotes pacotes = null;
    private static ControllerRecord gerenciador = null;
    private static ControllerCorrida corrida = null;
    
    public Recebedor(InputStream servidor) {
        this.servidor = servidor;
        this.start    = true;
        pacotes = new ControllerPacotes();
        gerenciador = new ControllerRecord();
        corrida = new ControllerCorrida();
    }

    public void setStart(boolean start) {
        this.start = start;
    }
    
    @Override
    public void run() {
        while(start){
            String pacote , opcao;
            DataInputStream entrada = new DataInputStream(this.servidor);
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
                        if(volta != null){
                            ControllerCorrida.setPacoteSensor(true);
                            Thread.sleep(500);
                            corrida.voltaCompleta(volta);
                            ControllerCorrida.setPacoteSensor(false);
                        }
                        break;
                }
            }catch(Exception ex){
                System.out.println(ex.toString());
            }
            
            try {
                sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(Recebedor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

