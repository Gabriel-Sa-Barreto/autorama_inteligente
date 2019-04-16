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
