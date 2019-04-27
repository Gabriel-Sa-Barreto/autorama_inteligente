/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author lsjsa
 */
public class ControllerRede {
    
    /**Método para enviar os pacotes desejados ao servidor.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param conexao Socket - obter a conexao com o server para enviar os pacotes para ele via TCP
    * @param dado String - o dado a ser enviado
    * @param opcao String - o tipo do pacote
    */
    public void enviarDado(Socket conexao , String dado , String opcao){
        try{
            String mandar = opcao + ";" + dado;
            DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
            saida.writeUTF(mandar);
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    } 
}
