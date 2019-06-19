/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import cliente.Cliente;
import controller.ControllerCorrida;
import controller.ControllerPacotes;
import controller.ControllerRecord;
import controller.ControllerRede;
import java.io.IOException;
import java.util.Scanner;
import model.Volta;

/**
 *
 * @author lsjsa
 */
public class ClientCorrida {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        ControllerRede rede = new ControllerRede();
        Cliente cliente = new Cliente("10.0.0.102",12345);
        cliente.executa();
        //ControllerPacotes pacotes = new ControllerPacotes();
        int i , laco = 0;
        Scanner scan = new Scanner(System.in);
        while(laco == 0){
            System.out.println("<1> Sair");
            i = scan.nextInt();
            switch(i){
                case 1:
                    laco = 1;
                    rede.enviarDado(cliente.getCliente(), "", "00");
                    break;
            }
        }
        cliente.fecharConexão();   
    }
    
}
