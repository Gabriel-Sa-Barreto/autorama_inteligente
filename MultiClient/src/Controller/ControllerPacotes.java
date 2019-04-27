/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.util.Iterator;
import java.util.List;
import model.Carro;
import model.Corrida;
import model.Piloto;
import model.Administrador;
import model.Record;
//import model.Volta;

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
        String corrida[] = pacote.split(";");
        Corrida recebido = new Corrida(strToInt(corrida[1] , 10));
        return recebido;
    }
    
    /**Método para transformar o pacote em uma Piloto.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @return Piloto - piloto enviado pelo server
    */
    public Piloto transformarPiloto(String  pacote){
        String dadosPiloto[] = pacote.split(";");
        Piloto piloto = new Piloto(dadosPiloto[1]);
        return piloto;
    }
    
     /**Método para transformar o pacote em uma Administrador.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @return Administrador - administrador enviado pelo server
    */
    public Administrador transformarAdm(String  pacote){
        String dadosAdm[] = pacote.split(";");
        Administrador adm = new Administrador(dadosAdm[1],dadosAdm[2]);
        return adm;
    }
    
    /*public Volta transformarVolta(String pacote , List<Carro> competidores){
        String volta[] = pacote.split(";");
        Carro carro = carro(competidores , volta[1]);
        if(carro == null)
            return null;
        Volta recebida = new Volta(carro , volta[2]);
        return recebida;
    }*/
    
    /**Método para transformar o pacote em uma Carro.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @return Carro - carro enviado pelo server
    */
    public Carro transformarCarro(String pacote){
        String carro[] = pacote.split(";");
        Carro car = new Carro(carro[1] , carro[2] , carro[3]);
        if(carro.length == 5){ //caso seja um pacote para competidor de uma corrida que seja iniciada
            Piloto carPiloto = new Piloto(carro[4]);
            car.setPiloto(carPiloto);
        }
        return car;
    }
    
    /*public Record transformarRecord(String pacote){
        String record[] = pacote.split(";");
        Record geral = new Record(record[1] , record[2] , record[3]);
        return geral;
    }*/
    
    /**Método para transformar o pacote em uma Volta.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @return String  - o tipo do pacote
    */
    public String acao(String pacote){
        String receber[] = pacote.split(";");
        return receber[0];
    }
    
    /**Método auxiliar na conversao de String para int
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param valor List - receber a String a ser convertida.
    * @param padrao int - caso aconteça uma exceção um valor padrão que possa ser inserido.
    * @return int - retornar o inteiro.
    */    
    public int strToInt(String valor, int padrao) {
        try {
            return Integer.valueOf(valor); // Para retornar um Integer, use Integer.parseInt
        } 
        catch (NumberFormatException e) {  // Se houver erro na conversão, retorna o valor padrão
            return padrao;
        }
    }
    
    /**Método para enviar um carro cadastrado com o id
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param id String - id do carro
    * @param carros list - id do carro
    * @return Carro - retornar o que foi achado na pesquisa da lista de carros
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
