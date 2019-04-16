/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ControllerCorrida;
import controller.ControllerPacotes;
import controller.ControllerRecord;
import model.Volta;

/**
 *
 * @author lsjsa
 */
public class ClientCorrida {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //11 cadastrar corrida
        //12 adicionar competidores
        //13 comecar partida
        //14 pausar partida
        //15 acabar corrida
        //21 cadastrar volta
        int i = 0;
        ControllerPacotes pacotes = new ControllerPacotes();
        ControllerCorrida corrida = new ControllerCorrida();
        
        String comandos[] = new String[15];
        comandos[0] = "11;30";
        comandos[1] = "12;12345;Ferrari;123;Samuel";
        comandos[2] = "12;56789;Ferrari;133;Gabriel";
        comandos[3] = "12;1000;Mercedes;133;Rafael";
        comandos[4] = "13;";
        comandos[5] = "16;10/12/2002;00:00:10;Rafael";
        comandos[6] = "21;12345;1:21:32";
        comandos[7] = "21;56789;1:21:33";
        comandos[8] = "21;1000;1:21:34";
        comandos[9] = "21;12345;1:21:43";
        comandos[10] ="21;56789;1:21:45";
        comandos[11] ="21;1000;1:21:44";
        comandos[12] ="21;12345;1:21:53";
        comandos[13] = "14;";
        comandos[14] ="21;1000;1:22:04";
        
        while(i < 15){
            String opcao =  pacotes.acao(comandos[i]);
            System.out.println(opcao);
            switch(opcao){
                case "11":
                    corrida.receberCorrida(pacotes.transformarCorrida(comandos[i]));
                    break;
                case "12":
                    if(!corrida.partidaEmAdamento())
                        corrida.adicionarCompetidor(pacotes.transformarCarro(comandos[i], corrida.competidores()));
                    break;
                case "13":
                    corrida.comecarPartida();
                    break;
                case "14":
                    corrida.pausarCorrida();
                    break;
                case "15":
                    corrida.acabarCorrida();
                    break;
                case "16":
                    corrida.adicionarRecordGeral(pacotes.transformarRecord(comandos[i]));
                    break;
                case "21":
                    System.out.println(corrida.partidaEmAdamento());
                    System.out.println(corrida.estaPausado());
                    if(corrida.partidaEmAdamento() && !corrida.estaPausado()){
                        Volta volta = pacotes.transformarVolta(comandos[i], corrida.competidores());
                        if(volta != null)
                            corrida.voltaCompleta(volta);
                    }
                    break;
            }
            i++;
        }
    }
    
}
