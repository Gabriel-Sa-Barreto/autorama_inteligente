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
        Corrida recebido = new Corrida(strToInt(corrida[1].trim() , 10));
        return recebido;
    }
    
    /**Método para transformar o pacote em um Piloto.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @return Piloto - piloto enviado pelo server
    */
    public Piloto transformarPiloto(String  pacote){
        String dadosPiloto[] = pacote.split(";");
        Piloto piloto = new Piloto(dadosPiloto[1].trim());
        return piloto;
    }
    
     /**Método para transformar o pacote em um Administrador.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @return Administrador - administrador enviado pelo server
    */
    public Administrador transformarAdm(String  pacote){
        String dadosAdm[] = pacote.split(";");
        Administrador adm = new Administrador(dadosAdm[1].trim(),dadosAdm[2].trim());
        return adm;
    }
 
    
    /**Método para transformar o pacote em um Carro.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @return Carro - carro enviado pelo server
    */
    public Carro transformarCarro(String pacote){
        String carro[] = pacote.split(";");
        Carro car = new Carro(carro[1].trim() , carro[2].trim() , carro[3].trim());
        if(carro.length == 5){ //caso seja um pacote para competidor de uma corrida que seja iniciada
            Piloto carPiloto = new Piloto(carro[4].trim());
            car.setPiloto(carPiloto);
        }
        return car;
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
    
    /**Método para transformar o pacote em dados de record.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @return Record  - record recebido
    */
    public Record transformarRecord(String pacote){
        Record novo;
        String record[] = pacote.split(";");
        novo = new Record(record[1].trim() , record[2].trim());
        return novo;
    }
    
    /**Método para pegar o nome do piloto recebido pelo pacote.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param pacote String - pacote enviado pelo server.
    * @return String - nome do piloto que esta no pacote
    */
    public String nomePiloto(String pacote){
        String record[] = pacote.split(";");
        return record[3].trim();
    }
    
    
}
