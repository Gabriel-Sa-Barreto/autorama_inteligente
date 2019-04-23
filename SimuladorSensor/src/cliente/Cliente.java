package cliente;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author gabriel
 */
public class Cliente {
    private String host;
    private int porta;
    Socket cliente;
    
    public Cliente (String host, int porta) throws IOException {
        this.host = host;
        this.porta = porta;
        this.conexao();
    }
    
    private void conexao() throws IOException{
        cliente = new Socket(this.host, this.porta);
        System.out.println("O cliente se conectou ao servidor!");
    }
        
    public Socket getCliente() {
        return cliente;
    }

    public void setCliente(Socket cliente) {
        this.cliente = cliente;
    }

    public void fecharConexão() throws IOException{
        cliente.close();
    }
}