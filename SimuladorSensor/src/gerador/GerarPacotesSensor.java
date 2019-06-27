/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gerador;
import Controller.ControllerRede;
import cliente.Cliente;
import java.io.IOException;
import java.util.Random;
/**
 *
 * @author lsjsa
 */
public class GerarPacotesSensor {
    
   public static void main(String[] Args) throws IOException, InterruptedException{
       
       String[] id = new String[4];
       id[0] = "100000";
       id[1] = "100001";
       id[2] = "100002";
       id[3] = "100003";
       String tempo;
       String pacote;
       
       Random random = new Random();
       
       int hora = random.nextInt(24);
       int idEscolhido;
       int i = 0;
       
       Cliente cliente = new Cliente("127.0.0.1", 12345);
       ControllerRede rede = new ControllerRede();
      
       while(i < 20){
       
           idEscolhido = random.nextInt(4);
           int minutos = random.nextInt(60);
           
           for(int j = 0 ; j < 3 ; j++){
               tempo = Integer.toString(hora) + ":" + Integer.toString(minutos) + ":" + Integer.toString(random.nextInt(60));
               pacote = "41;" + id[idEscolhido] + ";" + tempo;
               rede.enviarDado(cliente.getCliente(), pacote);
               System.out.println(pacote);
           }
           Thread.sleep(4000);
           i++;
       }
       rede.enviarDado(cliente.getCliente(), "00;");
       cliente.fecharConexão();
   }
    
}
