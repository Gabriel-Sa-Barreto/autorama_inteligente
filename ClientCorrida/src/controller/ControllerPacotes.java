/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Iterator;
import java.util.List;
import model.Carro;
import model.Corrida;
import model.Piloto;
import model.Record;
import model.Volta;

/**
 *
 * @author lsjsa
 */
public class ControllerPacotes {
    
    /**Método para transformar o pacote em uma Corrida.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @return Corrida - corrida a ser realizada
    */
    public Corrida transformarCorrida(String pacote){
        String corrida[] = pacote.split(";"); //os dados são dividos por ";" por isso a realização do split
        Corrida recebido = new Corrida(strToInt(corrida[1] , 10)); // cadastro da corrida com a quantidade de voltas
        return recebido;
    }
    
    /**Método para transformar o pacote em uma Volta.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @param competidores List - lista com os competidores da corrida.
    * @return Volta - detecção da passagem de um carro pelo sensor
    */
    public Volta transformarVolta(String pacote , List<Carro> competidores){
        String volta[] = pacote.split(";");
        Carro carro = carro(competidores , volta[1]); //verificação se aquele carro esta na corrida
        if(carro == null) //se for null é porque não esta na corrida
            return null;
        Volta recebida = new Volta(carro , volta[2]); // insere a volta
        return recebida;
    }
    
    /**Método para transformar o pacote em um Carro.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @param competidores List - lista com os competidores da corrida.
    * @return Carro - competidor enviado pelo server
    */
    public Carro transformarCarro(String pacote , List<Carro> competidores){
        String carro[] = pacote.split(";");
        if(carro(competidores , carro[1]) == null){ //verificação se aquele carro esta na corrida
            Carro competidor = new Carro(carro[1] , carro[2] , carro[3]); // cadastro da competidor
            Piloto piloto = new Piloto(carro[4]); // cadastrado do piloto
            competidor.setPiloto(piloto); //inserção do piloto no carro
            return competidor;
        }
        return null;
    }
    
    /**Método para transformar o pacote em dados de record.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @return Record  - record recebido
    */
    public Record transformarRecord(String pacote){
        String record[] = pacote.split(";");
        Record geral = new Record(record[1] , record[2] , record[3]); //cadastro do record 
        return geral;
    }
    
    /**Método para transformar o pacote em uma ação que será realizada.
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
    
    /**Método private para retornar um carro desejado.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param carros List - a lista de competidores que estam na corrida.
    * @param id int - o id do carro a ser pesquisado.
    * @return Carro - retornar o carro obtido na pesquisa na lista
    */
    private Carro carro(List<Carro> carros , String id){
        for (Iterator<Carro> it = carros.iterator(); it.hasNext();) {
            Carro carro = it.next();
            if(carro.getId().equals(id))
                return carro;
        }
        return null;
    }
    
    
}
