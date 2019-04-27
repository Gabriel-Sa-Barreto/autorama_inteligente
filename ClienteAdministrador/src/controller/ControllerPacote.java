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
        Corrida recebido = new Corrida(strToInt(corrida[1] , 10));
        return recebido;
    }
    
    /**Método para transformar o pacote em uma Carro.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @return Carro - carro enviado pelo server
    */
    public Carro transformarCarro(String pacote){
        String carro[] = pacote.split(";");
        Carro car = new Carro(carro[1] , carro[2] , carro[3]);
        if(carro.length == 5){
            Piloto piloto = new Piloto(carro[4]);
            car.setPiloto(piloto);
        }    
        return car;
    }
    
    /**Método para transformar o pacote em uma Piloto.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @return Piloto - piloto enviado pelo server
    */
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
    
    /**Método para transformar o pacote em uma Administrador.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @return Administrador - administrador enviado pelo server
    */
    public Administrador transformarAdm(String pacote){
        Administrador novo;
        String adm[] = pacote.split(";");
        novo = new Administrador(adm[1] , adm[2]);
        return novo;
    }
    
    
    /**Método para transformar o pacote em uma Volta.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @return String  - o tipo do pacote
    */
    public String acao(String pacote){
        String receber[] = pacote.split(";");
        return receber[0];
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
