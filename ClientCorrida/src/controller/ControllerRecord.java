/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import model.Record;
import model.Volta;

/**
 *
 * @author lsjsa
 */
public class ControllerRecord {
    
    /**
     * Atributo para lista de records.
     */
    List<Record> records;
    
    /**Método para iniciar a lista de record
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    */
    public ControllerRecord() {
        records = new ArrayList<>();
    }
    
    /**Método para adicionar um novo record
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param volta Volta - verificar a volta realizada foi o record
    * @param data String  - a data do possivel record.
    */
    public void adicionarRecord(Volta volta , String data){
        Record adicionar = new Record(data , volta.getTempoVolta() , volta.getCarro().getPiloto().getNome()); //cria uma instância do possivel record
        if(records.isEmpty()){ //verifica se a lista esta vazia 
            records.add(adicionar); //adicionar o record
        }
        else{
            if(bateuRecord(volta)){ // verificar se bateu o record
                records.removeIf(u -> u.getPiloto().equals(volta.getCarro().getPiloto().getNome())); //remove o record antigo via lambda , com o criterio pelo nome do piloto
                records.add(adicionar); //adiciona o record
            }
        }
        Collections.sort(records);
    }
    
    /**Método para verificar se bateu o record
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param volta Volta - verificar a volta realizada foi o record
    * @return boolean  - se bateu o record ou não
    */
    public boolean bateuRecord(Volta volta){ 
        for (Iterator<Record> it4 = records.iterator(); it4.hasNext();) { //anda na lista de record
            Record prova = it4.next(); //pega o conteudo de cada celula
            if(prova.getPiloto().equals(volta.getCarro().getPiloto().getNome())){ //verifica o piloto
                if(prova.compareTo(volta.getTempo()) == 1) //se o tempo passado for maior é porque bateu o record
                    return true; 
                else
                    return false;
                
            }
        }
        return true;
    }
    
    /**Método para devolver o record de um pilotot especificado
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param nome String - o nome do piloto solicitado
    * @return String - retorna o tempo do record
    */
    public String recordPiloto(String nome){ 
        for (Iterator<Record> it5 = records.iterator(); it5.hasNext();) {
            Record prova = it5.next();
            if(prova.getPiloto().equals(nome)){ //verifica via nome do piloto
                return prova.getTempo();
            }
        }
        return null;
    }
    
    /**Método para retornar a lista de records da corrida.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @return List - retorna a lista de records.
    */
    public List<Record> records(){
        return records;
    }
    
    /**Método para limpar a lista
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    */
    public void limparRecord(){
        records.clear();
    }
    
}
