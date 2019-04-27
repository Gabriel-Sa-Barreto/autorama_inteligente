/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import model.Administrador;
import model.Carro;
import model.Piloto;
import model.Record;

/**
 *
 * @author lsjsa
 */
public class ControllerArquivo {
    
    /**Método para escrever os pilotos no arquivo
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param caminho String - caminho onde estara o arquivo
    * @param piloto Piloto - piloto a ser salvo
     * @throws java.io.IOException
    */
    public static void escreverPiloto(String caminho , Piloto piloto) throws IOException{
        BufferedWriter write = new BufferedWriter(new FileWriter(caminho,true));
        write.append(piloto.toString()+ "\n");
        write.close();
    }
    
    /**Método para escrever os carros no arquivo
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param caminho String - caminho onde estara o arquivo
    * @param carro Carro - carro a ser salvo
     * @throws java.io.IOException
    */
    public static void escreverCarro(String caminho , Carro carro) throws IOException{
        BufferedWriter write = new BufferedWriter(new FileWriter(caminho,true));
        write.append(carro.toString()+ "\n");
        write.close();
    }
    
    /**Método para escrever os adms no arquivo
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param caminho String - caminho onde estara o arquivo
    * @param adm Administrador - administrador a ser salvo
     * @throws java.io.IOException
    */
    public static void escreverAdm(String caminho , Administrador adm) throws IOException{
        BufferedWriter write = new BufferedWriter(new FileWriter(caminho , true));
        write.append(adm.toString()+ "\n");
        write.close();
    }
    
    
    /**Método para remover piloto do arquivo
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param caminho String - caminho onde estara o arquivo
    * @param piloto Piloto - piloto a ser excluido
     * @throws java.io.IOException
    */
    public static void removerPiloto(String caminho , Piloto piloto) throws IOException{
        ArrayList<Piloto> pilotos = new ArrayList(); //vetor para salvar o que esta armazenado
        BufferedReader read = new BufferedReader(new FileReader(caminho));
        String linha = "";
        while((linha = read.readLine()) != null){
            pilotos.add(salvarPiloto(linha)); //salva no vetor
        }
        read.close();
        pilotos.removeIf( u -> u.getNome().equals(piloto.getNome())); //exclui o piloto desejado via lambda
        BufferedWriter write = new BufferedWriter(new FileWriter(caminho , false));
        for(Piloto inserir : pilotos){
            write.append(inserir.toString() + "\n"); //reescrever com a lista atualizada
        }
        write.close();
    }
    
    /**Método para remover carro do arquivo
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param caminho String - caminho onde estara o arquivo
    * @param id String - id do carro a ser excluido
     * @throws java.io.IOException
    */
    public static void removerCarro(String caminho , String id) throws IOException{
        ArrayList<Carro> carros = new ArrayList();
        BufferedReader read = new BufferedReader(new FileReader(caminho));
        String linha = "";
        while((linha = read.readLine()) != null){
            carros.add(salvarCarro(linha));
        }
        read.close();
        carros.removeIf( u -> u.getId().equals(id));
        BufferedWriter write = new BufferedWriter(new FileWriter(caminho , false));
        for(Carro inserir : carros){
            write.append(inserir.toString() + "\n");
        }
        write.close();
    }
    
    /**Método para ler do arquivo e salvar na lista de pilotos quando o server for iniciado
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param caminho String - caminho onde estara o arquivo
    * @param gerenciador ControllerGerenciador - instancia do controller para salvar 
     * @throws java.io.FileNotFoundException 
    */
    public static void leitorPiloto(String caminho , ControllerGerenciador gerenciador) throws FileNotFoundException, IOException{
        BufferedReader read = new BufferedReader(new FileReader(caminho));
        String linha = "";
        while((linha = read.readLine()) != null){
            gerenciador.cadastrarPiloto(salvarPiloto(linha));
        }
        read.close();
    }
    
    /**Método para ler do arquivo e salvar na lista de carro quando o server for iniciado
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param caminho String - caminho onde estara o arquivo
    * @param gerenciador ControllerGerenciador - instancia do controller para salvar 
     * @throws java.io.FileNotFoundException 
    */
    public static void leitorCarro(String caminho , ControllerGerenciador gerenciador) throws FileNotFoundException, IOException{
        BufferedReader read = new BufferedReader(new FileReader(caminho));
        String linha = "";
        while((linha = read.readLine()) != null){
            gerenciador.cadastrarCarro(salvarCarro(linha));
        }
        read.close();
    }
    
    /**Método para ler do arquivo e salvar na lista de adms quando o server for iniciado
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param caminho String - caminho onde estara o arquivo
    * @param gerenciador ControllerGerenciador - instancia do controller para salvar 
     * @throws java.io.FileNotFoundException 
    */
    public static void leitorAdm(String caminho , ControllerGerenciador gerenciador) throws FileNotFoundException, IOException{
        BufferedReader read = new BufferedReader(new FileReader(caminho));
        String linha = "";
        while((linha = read.readLine()) != null){
            gerenciador.cadastrarAdministrador(salvarAdm(linha));
        }
        read.close();
    } 
    
    /**Método para ler do arquivo a linha e criar instância de piloto quando o server for iniciado
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param linha String - caminho onde estara o arquivo
    */
    private static Piloto salvarPiloto(String linha){
        String[] split = linha.split(";");
        Piloto piloto;
        piloto = new Piloto(split[0]);
        if(split.length > 1){
            Record record = new Record(split[1] , split[2]);
            piloto.setRecord(record);
        }
        return piloto;
    }
    
    /**Método para ler do arquivo a linha e criar instância de carro quando o server for iniciado
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param linha String - caminho onde estara o arquivo
    */
    private static Carro salvarCarro(String linha){
        String[] split = linha.split(";");
        Carro carro;
        carro = new Carro(split[0] ,split[1] , split[2]);
        return carro;
    }
    
    /**Método para ler do arquivo a linha e criar instância de administrador quando o server for iniciado
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param linha String - caminho onde estara o arquivo
    */
    private static Administrador salvarAdm(String linha){
        String[] split = linha.split(";");
        Administrador adm;
        adm = new Administrador(split[0] , split[1]);
        return adm;
    }
    
}
