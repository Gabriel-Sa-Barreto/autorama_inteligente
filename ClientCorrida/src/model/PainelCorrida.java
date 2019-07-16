/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.ControllerCorrida;
import java.util.Iterator;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lablenda3
 */
public class PainelCorrida implements Runnable {

    private ControllerCorrida corrida;
    private javax.swing.JTable jTCorrida;
    private DefaultTableModel  painelCorrida;
    private JLabel sessao;
    private JLabel record;
    private JLabel autor;
    
    public PainelCorrida(ControllerCorrida corrida, javax.swing.JTable corridaTable, JLabel sessao, JLabel record, JLabel autor){
        this.corrida        = corrida;
        this.painelCorrida   = (DefaultTableModel) corridaTable.getModel();
        this.jTCorrida = corridaTable;
        this.sessao = sessao;
        this.record = record;
        this.autor  = autor;
    }   
    @Override
    public void run() {
        try{
            while(corrida.partidaEmAdamento()){
            try{
                if(!ControllerCorrida.isPacoteSensor()){
                    painelCorrida.setRowCount(corrida.competidores().size());
                    List<Volta> voltas = corrida.getVoltas();
                    //synchronized(voltas){
                    if(!voltas.isEmpty()){
                        int i = 0;
                        for(Iterator<Volta> it2 = voltas.iterator(); it2.hasNext();){
                            if(ControllerCorrida.isPacoteSensor()){
                                Thread.sleep(1500);
                                break;
                            }else{
                                Record recorde = null;
                                if(!corrida.temRecord())
                                    recorde = corrida.getRecord(0);
                                    sessao.setText("Sessão de Corrida: " + voltas.get(0).getQuantidade() + "/" + corrida.quantidadeTotal());
                                    if(recorde != null){
                                        record.setText("Record: " + recorde.getTempo());
                                        autor.setText("Autor: " + recorde.getPiloto());
                                    }
                                    
                                    Volta volta = it2.next();
                                    String nome = volta.getCarro().getPiloto().getNome();
                                    jTCorrida.setValueAt(i, i, 0);
                                    jTCorrida.setValueAt(nome, i, 1);
                                    jTCorrida.setValueAt(volta.getCarro().getEquipe() , i, 2);
                                    jTCorrida.setValueAt(volta.getTempoVolta() , i, 4);
                                    if(corrida.getRecord(nome) != null)
                                        jTCorrida.setValueAt(corrida.getRecord(nome) , i, 3);
                                    else
                                        jTCorrida.setValueAt("00:00" , i, 4);
                                    jTCorrida.setValueAt(volta.getQuantidade() , i, 5);
                                    i++;
                                }
                            }
                        }
                    }
                }catch(Exception ex){
                }    
            }
        }catch(Exception e){
        
        }
    }   
}
