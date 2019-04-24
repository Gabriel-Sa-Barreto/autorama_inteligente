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
    
    public static void escreverPiloto(String caminho , List<Piloto> pilotos) throws IOException{
        BufferedWriter write = new BufferedWriter(new FileWriter(caminho));
        String linha = "";
        for(Piloto piloto : pilotos){
            write.append(piloto.toString()+ "\n");
        }
        write.close();
    }
    
    public static void escreverCarro(String caminho , List<Carro> carros) throws IOException{
        BufferedWriter write = new BufferedWriter(new FileWriter(caminho));
        String linha = "";
        for(Carro carro : carros){
            write.append(carro.toString()+ "\n");
        }
        write.close();
    }
    
    public static void escreverAdm(String caminho , List<Administrador> adms) throws IOException{
        BufferedWriter write = new BufferedWriter(new FileWriter(caminho));
        String linha = "";
        for(Administrador adm : adms){
            write.append(adms.toString()+ "\n");
        }
        write.close();
    }
    
    public static void leitorPiloto(String caminho , List<Piloto> pilotos) throws FileNotFoundException, IOException{
        BufferedReader read = new BufferedReader(new FileReader(caminho));
        String linha = "";
        while(true){
            if(linha != null){
                pilotos.add(salvarPiloto(linha));
            }
            else
                break;
            linha = read.readLine();
        }
        read.close();
    }
    
    public static void leitorCarro(String caminho , List<Carro> carros) throws FileNotFoundException, IOException{
        BufferedReader read = new BufferedReader(new FileReader(caminho));
        String linha = "";
        while(true){
            if(linha != null){
                carros.add(salvarCarro(linha));
            }
            else
                break;
            linha = read.readLine();
        }
        read.close();
    }
    
    public static void leitorAdm(String caminho , List<Administrador> adms) throws FileNotFoundException, IOException{
        BufferedReader read = new BufferedReader(new FileReader(caminho));
        String linha = "";
        while(true){
            if(linha != null){
                adms.add(salvarAdm(linha));
            }
            else
                break;
            linha = read.readLine();
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
