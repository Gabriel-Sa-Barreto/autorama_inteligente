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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lablenda3
 */
public class Qualificacao implements Runnable {

    private ControllerCorrida corrida;
    private javax.swing.JTable jTQualificacao;
    private DefaultTableModel  qualificacao;
    private JLabel sessao;
    private JLabel record;
    private JLabel autor;
    
    public Qualificacao(ControllerCorrida corrida, javax.swing.JTable qualificacao , JLabel sessao , JLabel record ,JLabel autor ){
        this.corrida        = corrida;
        this.qualificacao   = (DefaultTableModel) qualificacao.getModel();
        this.jTQualificacao = qualificacao;
        this.sessao = sessao;
        this.record = record;
        this.autor = autor;
    }   
    @Override
    public void run() {
        try{
            while(corrida.partidaEmAdamento()){
                try{
                    if(!ControllerCorrida.isPacoteSensor()){
                    qualificacao.setRowCount(corrida.competidores().size());
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
                                    sessao.setText("Sessão de Qualificacao: " + voltas.get(0).getQuantidade() + "/" + corrida.quantidadeTotal());
                                    if(recorde != null){
                                        record.setText("Record: " + recorde.getTempo());
                                        autor.setText("Autor: " + recorde.getPiloto());
                                    }
                                    Volta volta = it2.next();
                                    String nome = volta.getCarro().getPiloto().getNome();
                                    jTQualificacao.setValueAt(i, i, 0);
                                    jTQualificacao.setValueAt(nome, i, 1);
                                    jTQualificacao.setValueAt(volta.getCarro().getEquipe() , i, 2);
                                    if(corrida.getRecord(nome) != null)
                                        jTQualificacao.setValueAt(corrida.getRecord(nome) , i, 3);
                                    else
                                        jTQualificacao.setValueAt("00:00" , i, 3);
                                    jTQualificacao.setValueAt(volta.getQuantidade() , i, 4);
                                    i++;
                                }
                            }
                        }
                    //} 
                    }
                }catch(Exception ex){
                }    
        }
        }catch(Exception e){
        }
    }
    
}
