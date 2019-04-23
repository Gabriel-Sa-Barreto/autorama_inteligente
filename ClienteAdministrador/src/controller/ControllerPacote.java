/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Administrador;
import model.Carro;
import model.Corrida;
import model.Piloto;
import model.Record;

/**
 *
 * @author lablenda3
 */
public class ControllerPacote {
    
    public Corrida trabsformarCorrida(String pacote){
        String corrida[] = pacote.split(";");
        Corrida recebido = new Corrida(strToInt(corrida[1] , 10));
        return recebido;
    }
    
    public Carro transformarCarro(String pacote){
        String carro[] = pacote.split(";");
        Carro car = new Carro(carro[1] , carro[2] , carro[3]);
        if(carro.length == 5){
            Piloto piloto = new Piloto(carro[4]);
            car.setPiloto(piloto);
        }    
        return car;
    }
    
    public Piloto transformarPiloto(String pacote){
        Piloto novo;
        String piloto[] = pacote.split(";");
        novo = new Piloto(piloto[1]);
        if(piloto.length == 4){
            Record record = new Record(piloto[2] , piloto[3]);
            novo.setRecord(record);
        }
        return novo;
    }
    
    public Administrador transformarAdm(String pacote){
        Administrador novo;
        String adm[] = pacote.split(";");
        novo = new Administrador(adm[1] , adm[2]);
        return novo;
    }
    
    
    public String acao(String pacote){
        String receber[] = pacote.split(";");
        return receber[0];
    }
    
    private int strToInt(String valor, int padrao) {
        try {
            return Integer.valueOf(valor); // Para retornar um Integer, use Integer.parseInt
        } 
        catch (NumberFormatException e) {  // Se houver erro na conversão, retorna o valor padrão
            return padrao;
        }
    }
}
