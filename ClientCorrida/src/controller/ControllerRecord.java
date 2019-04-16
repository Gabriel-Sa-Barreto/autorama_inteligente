/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Record;
import model.Volta;

/**
 *
 * @author lsjsa
 */
public class ControllerRecord {
    
    List<Record> records;
    Record geral;

    public ControllerRecord() {
        records = new ArrayList<>();
    }
    
    public void adicionarRecord(Volta volta , String data){
        Record adicionar = new Record(data , volta.getTempoVolta() , volta.getCarro().getPiloto().getNome());
        if(records.isEmpty()){
            records.add(adicionar);
        }
        else{
            if(bateuRecord(volta)){
                records.removeIf(u -> u.getPiloto().equals(volta.getCarro().getPiloto().getNome()));
                records.add(adicionar);
            }
            if(bateuRecordGeral(volta.getTempo())){
                geral.setData(data);
                geral.setPiloto(volta.getCarro().getPiloto().getNome());
                geral.setTempo(volta.getTempo());
            }
        }
        records.forEach(u -> System.out.println("r" + u.getPiloto() + "t" + u.getTempo()));    
    }
    
    public boolean bateuRecord(Volta volta){
        for (Iterator<Record> it = records.iterator(); it.hasNext();) {
            Record prova = it.next();
            if(prova.getPiloto().equals(volta.getCarro().getPiloto().getNome())){
                if(prova.compareTo(volta.getTempo()) == -1)
                    return true;
                else
                    return false;
                
            }
        }
        return true;
    }
    
    public String recordTempo(String tempo){
        for (Iterator<Record> it = records.iterator(); it.hasNext();) {
            Record prova = it.next();
            if(prova.compareTo(tempo) == 0){
                return prova.getTempo();
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
