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
    
    /**Método para transformar o pacote em uma Corrida.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @return Corrida - corrida a ser realizada
    */
    public Corrida transformarCorrida(String pacote){
        String corrida[] = pacote.split(";");
        Corrida recebido = new Corrida(strToInt(corrida[1].trim() , 10));
        return recebido;
    }
    
    /**Método para transformar o pacote em uma Carro.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @return Carro - carro enviado pelo server
    */
    public Carro transformarCarro(String pacote){
        String carro[] = pacote.split(";");
        Carro car = new Carro(carro[1].trim() , carro[2].trim() , carro[3].trim());
        if(carro.length == 5){
            Piloto piloto = new Piloto(carro[4].trim());
            car.setPiloto(piloto);
        }    
        return car;
    }
    
    /**Método para transformar o pacote em um Piloto.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @return Piloto - piloto enviado pelo server
    */
    public Piloto transformarPiloto(String pacote){
        Piloto novo;
        String piloto[] = pacote.split(";");
        novo = new Piloto(piloto[1].trim());
        if(piloto.length == 4){
            Record record = new Record(piloto[2].trim() , piloto[3].trim());
            novo.setRecord(record);
        }
        return novo;
    }
    
    /**Método para transformar o pacote em um Administrador.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @return Administrador - administrador enviado pelo server
    */
    public Administrador transformarAdm(String pacote){
        Administrador novo;
        String adm[] = pacote.split(";");
        novo = new Administrador(adm[1].trim() , adm[2].trim());
        return novo;
    }
    
    /**
     * Método que retira do pacote de dados recebidos o ID do carro.
     * @param pacote
     * @return returna o ID do carro recebido pelo pacote de dados. 
     */
    public String pegaIDCarro(String pacote){
        String received[] = pacote.split(";");
        return received[1].trim();
    }
    
    
    public Record transformarRecord(String pacote){
        Record novo;
        String record[] = pacote.split(";");
        novo = new Record(record[1].trim() , record[2].trim());
        return novo;
    }
    
    public String nomePiloto(String pacote){
        String record[] = pacote.split(";");
        return record[3].trim();
    }
    
    
    /**Método para transformar o pacote em uma Volta.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @return String  - o tipo do pacote
    */
    public String acao(String pacote){
        String action = null;
        String receber[] = pacote.split(";");
        try{
            int num = Integer.parseInt(receber[0]);
        }catch(NumberFormatException e){
            action = receber[0];
            action = action.substring(2);
        }
        if(action == null)
            return receber[0];
        return action;
    }
    
    /**Método private para auxiliar na conversao de String para int
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param valor List - receber a String a ser convertida.
    * @param padrao int - caso aconteça uma exceção um valor padrão que possa ser inserido.
    * @return int - retornar o inteiro.
    */
    private int strToInt(String valor, int padrao) {
        try {
            return Integer.valueOf(valor); // Para retornar um Integer, use Integer.parseInt
        } 
        catch (NumberFormatException e) {  // Se houver erro na conversão, retorna o valor padrão
            return padrao;
        }
    }
}
