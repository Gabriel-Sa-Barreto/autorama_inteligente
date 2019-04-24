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
import java.util.List;
import model.Administrador;
import model.Carro;
import model.Piloto;
import model.Record;

/**
 *
 * @author lsjsa
 */
public class ControllerArquivo {
    
    public static void escreverPiloto(String caminho , Piloto piloto) throws IOException{
        BufferedWriter write = new BufferedWriter(new FileWriter(caminho,true));
        write.append(piloto.toString()+ "\n");
        write.close();
    }
    
    public static void escreverCarro(String caminho , Carro carro) throws IOException{
        BufferedWriter write = new BufferedWriter(new FileWriter(caminho,true));
        write.append(carro.toString()+ "\n");
        write.close();
    }
    
    public static void escreverAdm(String caminho , Administrador adm) throws IOException{
        BufferedWriter write = new BufferedWriter(new FileWriter(caminho , true));
        write.append(adm.toString()+ "\n");
        write.close();
    }
    
    public static void removerPiloto(String caminho , Piloto piloto) throws IOException{
        ArrayList<Piloto> pilotos = new ArrayList();
        BufferedReader read = new BufferedReader(new FileReader(caminho));
        String linha = "";
        while((linha = read.readLine()) != null){
            pilotos.add(salvarPiloto(linha));
        }
        read.close();
        pilotos.removeIf( u -> u.getNome().equals(piloto.getNome()));
        BufferedWriter write = new BufferedWriter(new FileWriter(caminho , false));
        for(Piloto inserir : pilotos){
            write.append(inserir.toString() + "\n");
        }
        write.close();
    }
    
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
    
    public static void leitorPiloto(String caminho , ControllerGerenciador gerenciador) throws FileNotFoundException, IOException{
        BufferedReader read = new BufferedReader(new FileReader(caminho));
        String linha = "";
        while((linha = read.readLine()) != null){
            gerenciador.cadastrarPiloto(salvarPiloto(linha));
        }
        read.close();
    }
    
    public static void leitorCarro(String caminho , ControllerGerenciador gerenciador) throws FileNotFoundException, IOException{
        BufferedReader read = new BufferedReader(new FileReader(caminho));
        String linha = "";
        while((linha = read.readLine()) != null){
            gerenciador.cadastrarCarro(salvarCarro(linha));
        }
        read.close();
    }
    
    public static void leitorAdm(String caminho , ControllerGerenciador gerenciador) throws FileNotFoundException, IOException{
        BufferedReader read = new BufferedReader(new FileReader(caminho));
        String linha = "";
        while((linha = read.readLine()) != null){
            gerenciador.cadastrarAdministrador(salvarAdm(linha));
        }
        read.close();
    } 
    
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
    
    private static Carro salvarCarro(String linha){
        String[] split = linha.split(";");
        Carro carro;
        carro = new Carro(split[0] ,split[1] , split[2]);
        return carro;
    }
    
    private static Administrador salvarAdm(String linha){
        String[] split = linha.split(";");
        Administrador adm;
        adm = new Administrador(split[0] , split[1]);
        return adm;
    }
    
}
