/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author lsjsa
 */
public class ControllerRede {
     
    public void enviarDado(Socket conexao , String pacote){
        try{
            DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
            saida.writeUTF(pacote);
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
    
}
