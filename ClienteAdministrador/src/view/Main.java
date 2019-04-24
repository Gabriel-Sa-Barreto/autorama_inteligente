/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ControllerGerenciador;
import controller.ControllerRede;
import controller.ControllerCorrida;
import java.util.Scanner;
import clientAdm.Cliente;
import java.io.IOException;
/**
 *
 * @author lsjsa
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        ControllerGerenciador gerenciador = new ControllerGerenciador();
        ControllerRede rede = new ControllerRede();
        ControllerCorrida corrida = new ControllerCorrida();
        Cliente adm = new Cliente("192.168.25.3" , 12345);
        adm.executa();
        int controlador = 0;
        String dado;
        Scanner scan = new Scanner(System.in);
        int escolha;
        int porta = 12345;
        while(controlador == 0){
            System.out.println("<1> Adicionar Carro\n");      //OK
            System.out.println("<2> Adicionar Piloto\n");     //OK
            System.out.println("<3> Adicionar Adm\n");        //OK
            System.out.println("<4> Remover piloto\n");       //OK
            System.out.println("<5> Remover carro\n");        //OK
            System.out.println("<6> Cadastrar corrida\n");    //OK
            System.out.println("<7> Cadastrar competidor\n"); //OK
            System.out.println("<8> Começar corrida\n");      //ok
            System.out.println("<9> Atualizar\n");          //ok
            System.out.println("<10> Sair\n");                 //OK
            escolha = scan.nextInt();
            switch(escolha){
                case 1:
                    /*if(!gerenciador.existeCarro("12345")){
                        rede.enviarDado(adm.getCliente() , gerenciador.cadastrarCarro("12345", "Ferrari", "123").toString() , "11");
                    }*/
                    rede.enviarDado(adm.getCliente() , gerenciador.cadastrarCarro("100000", "Ferrari", "123").toString() , "11");
                    rede.enviarDado(adm.getCliente() , gerenciador.cadastrarCarro("100001", "Mercedes","456").toString() , "11");
                    rede.enviarDado(adm.getCliente() , gerenciador.cadastrarCarro("100002", "Ferrari", "789").toString() , "11");
                    rede.enviarDado(adm.getCliente() , gerenciador.cadastrarCarro("100003", "Ferrari", "101").toString() , "11");
                    break;
                case 2:
                    /*if(!gerenciador.existePiloto("Samuel")){
                        rede.enviarDado(adm.getCliente() , gerenciador.cadastrarPiloto("Samuel").toString() , "12");
                    }*/
                    rede.enviarDado(adm.getCliente() , gerenciador.cadastrarPiloto("Samuel").toString() , "12");
                    rede.enviarDado(adm.getCliente() , gerenciador.cadastrarPiloto("Rafael").toString() , "12");
                    rede.enviarDado(adm.getCliente() , gerenciador.cadastrarPiloto("Gabriel").toString() , "12");
                    rede.enviarDado(adm.getCliente() , gerenciador.cadastrarPiloto("Xande").toString() , "12");
                    break;
                case 3:
                    if(!gerenciador.existeAdm("Rafel")){
                        rede.enviarDado(adm.getCliente() , gerenciador.cadastrarAdministrador("Rafael" , "12345").toString() ,"13");
                    }
                    break;    
                case 4:
                    if(gerenciador.existePiloto("Samuel")){
                       rede.enviarDado(adm.getCliente() , gerenciador.piloto("Samuel").toString() , "21");
                       //gerenciador.removerPiloto("Samuel");
                    }
                    break;
                case 5:
                    if(gerenciador.existeCarro("12345")){
                       rede.enviarDado(adm.getCliente() , gerenciador.carro("12345").toString() , "22");
                       //gerenciador.removerCarro("12345");
                    }
                    break;    
                case 6:
                    rede.enviarDado(adm.getCliente() , corrida.cadastrarCorrida(20).toString() , "30");
                    break;
                case 7:
                    if(!corrida.comecouCorrida()){
                        /*if(!corrida.estaNaCorrida(gerenciador.carro("12345"))){
                            rede.enviarDado(adm.getCliente() , corrida.cadastraCompetidor(gerenciador.carro("12345"), gerenciador.piloto("Samuel")).toString() , "32");
                        }*/
                        rede.enviarDado(adm.getCliente() , corrida.cadastraCompetidor(gerenciador.carro("100000"), gerenciador.piloto("Samuel")).toString() , "32");
                        rede.enviarDado(adm.getCliente() , corrida.cadastraCompetidor(gerenciador.carro("100001"), gerenciador.piloto("Rafael")).toString() , "32");
                        rede.enviarDado(adm.getCliente() , corrida.cadastraCompetidor(gerenciador.carro("100002"), gerenciador.piloto("Gabriel")).toString() , "32");
                        rede.enviarDado(adm.getCliente() , corrida.cadastraCompetidor(gerenciador.carro("100003"), gerenciador.piloto("Xande")).toString() , "32");
                    }
                    break;
                case 8:
                    rede.enviarDado(adm.getCliente(), "", "31");
                    break;
                case 9:
                    rede.enviarDado(adm.getCliente(), "", "100");
                    break;
                case 10:
                    rede.enviarDado(adm.getCliente(), "", "00");
                    controlador = 1;
                    break;
            }    
        }
        adm.fecharConexão();
    }
    
}
