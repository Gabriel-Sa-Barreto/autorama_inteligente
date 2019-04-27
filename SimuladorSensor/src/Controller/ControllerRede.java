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
    
    /**Método responsável por enviar pacotes ao servidor
     * @param conexao - parâmatro do objeto socket do cliente 
     * @param pacote  - pacote a ser enviado no formato de uma string
    */
    public void enviarDado(Socket conexao , String pacote){
        try{
            DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
            saida.writeUTF(pacote);
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
    
}
