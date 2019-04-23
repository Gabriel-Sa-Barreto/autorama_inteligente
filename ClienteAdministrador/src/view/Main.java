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
        Cliente adm = new Cliente("127.0.0.1" , 12345);
        adm.executa();
        int controlador = 0;
        String dado;
        Scanner scan = new Scanner(System.in);
        int escolha;
        int porta = 12345;
        while(controlador == 0){
            System.out.println("<1> Adicionar Carro\n");
            System.out.println("<2> Adicionar Piloto\n");
            System.out.println("<3> Adicionar Adm\n");
            System.out.println("<4> Remover piloto\n");
            System.out.println("<5> Remover carro\n");
            System.out.println("<6> Cadastrar corrida\n");
            System.out.println("<7> Cadastrar competidor\n");
            System.out.println("<8> Sair\n");
            escolha = scan.nextInt();
            switch(escolha){
                case 1:
                    if(!gerenciador.existeCarro("12345")){
                        rede.enviarDado(adm.getCliente() , gerenciador.cadastrarCarro("12345", "Ferrari", "123").toString() , "11");
                        //gerenciador.cadastrarCarro("12345", "Ferrari", "123");
                        //gerenciador.cadastrarCarro("56789", "Ferrari", "124");
                    }
                    break;
                case 2:
                    if(!gerenciador.existePiloto("Samuel")){
                        rede.enviarDado(adm.getCliente() , gerenciador.cadastrarPiloto("Samuel").toString() , "12");
                        //gerenciador.cadastrarPiloto("Samuel");
                        //gerenciador.cadastrarPiloto("Gabriel");
                    }
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
                    rede.enviarDado(adm.getCliente() , corrida.cadastrarCorrida(20).toString() , "31");
                    break;
                case 7:
                    if(!corrida.comecouCorrida()){
                        if(!corrida.estaNaCorrida(gerenciador.carro("12345"))){
                            rede.enviarDado(adm.getCliente() , corrida.cadastraCompetidor(gerenciador.carro("12345"), gerenciador.piloto("Samuel")).toString() , "31");
                        }
                    }
                case 8:
                    rede.enviarDado(adm.getCliente(), "", "00");
                    controlador = 1;
                    break;
            }    
        }
        adm.fecharConexão();
    }
    
}
