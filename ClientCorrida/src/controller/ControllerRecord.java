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
    
    private static List<Record> records;
    Record geral;

    public ControllerRecord() {
        records = Collections.synchronizedList(new ArrayList<Record>());
    }
    
    public void adicionarRecord(Volta volta , String data){
        Record adicionar = new Record(data , volta.getTempoVolta() , volta.getCarro().getPiloto().getNome());
        synchronized(records()){
            if(records.isEmpty()){
                records.add(adicionar);
            }
            else{
                if(bateuRecord(volta)){
                    records.removeIf(u -> u.getPiloto().equals(volta.getCarro().getPiloto().getNome()));
                    records.add(adicionar);
                }
                /*if(bateuRecordGeral(volta.getTempo())){
                    System.out.println("Aqui3");
                    geral.setData(data);
                    geral.setPiloto(volta.getCarro().getPiloto().getNome());
                    geral.setTempo(volta.getTempo());
                }*/
            }
            records.forEach(u -> System.out.println("r" + u.getPiloto() + "t" + u.getTempo()));
        }    
    }
    
    public boolean bateuRecord(Volta volta){ 
        for (Iterator<Record> it4 = records.iterator(); it4.hasNext();) {
            Record prova = it4.next();
            if(prova.getPiloto().equals(volta.getCarro().getPiloto().getNome())){
                if(prova.compareTo(volta.getTempo()) == 1)
                    return true; 
                else
                    return false;
                
            }
        }
        return true;
    }
    
    public String recordPiloto(String nome){ 
        synchronized(records()){
            for (Iterator<Record> it5 = records.iterator(); it5.hasNext();) {
                Record prova = it5.next();
                if(prova.getPiloto().equals(nome)){
                    return prova.getTempo();
                }
            }
        }
        return null;
    }
    
    private boolean bateuRecordGeral(String tempo){
        return (geral.compareTo(tempo) == -1);
    }
    
    public void cadastrarRecordGeral(Record novo){
        this.geral = new Record(novo.getData() , novo.getTempo() , novo.getPiloto()); 
    }
    
    public List<Record> records(){
        return records;
    }
    
    public Record recordGeral(){
        return geral;
    }
}
