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
    
    public Corrida transformarCorrida(String pacote){
        String corrida[] = pacote.split(";");
        Corrida recebido = new Corrida(strToInt(corrida[1] , 10));
        return recebido;
    }
    
    public Volta transformarVolta(String pacote , List<Carro> competidores){
        String volta[] = pacote.split(";");
        Carro carro = carro(competidores , volta[1]);
        if(carro == null)
            return null;
        Volta recebida = new Volta(carro , volta[2]);
        return recebida;
    }
    
    public Carro transformarCarro(String pacote , List<Carro> competidores){
        String carro[] = pacote.split(";");
        if(carro(competidores , carro[1]) == null){
            Carro competidor = new Carro(carro[1] , carro[2] , carro[3]);
            Piloto piloto = new Piloto(carro[4]);
            competidor.setPiloto(piloto);
            return competidor;
        }
        return null;
    }
    
    public Record transformarRecord(String pacote){
        String record[] = pacote.split(";");
        Record geral = new Record(record[1] , record[2] , record[3]);
        return geral;
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
    
    private Carro carro(List<Carro> carros , String id){
        for (Iterator<Carro> it = carros.iterator(); it.hasNext();) {
            Carro carro = it.next();
            if(carro.getId().equals(id))
                return carro;
        }
        return null;
    }
    
    
}
