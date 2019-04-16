/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiclient;

import java.io.IOException;

/**
 *
 * @author gabriel
 */
public class MultiClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //Starta o servidor e prepara para novos clientes
        Servidor servidor = new Servidor(12345);   
    }
}
