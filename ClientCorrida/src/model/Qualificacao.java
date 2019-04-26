/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.ControllerCorrida;
import java.util.Iterator;
import java.util.List;
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
    
    public Qualificacao(ControllerCorrida corrida, javax.swing.JTable qualificacao){
        this.corrida        = corrida;
        this.qualificacao   = (DefaultTableModel) qualificacao.getModel();
        this.jTQualificacao = qualificacao;
    }   
    @Override
    public void run() {
         while(corrida.partidaEmAdamento()){
            try{
                if(!ControllerCorrida.isPacoteSensor()){
                    qualificacao.setRowCount(corrida.competidores().size());
                    List<Volta> voltas = corrida.getVoltas();
                    //synchronized(voltas){
                        if(!voltas.isEmpty()){
                            int i = 0;
                            //jLabelSessao.setText("Sessão de Qualificacao: " + voltas.get(0).getQuantidade() + "/" + corrida.quantidadeTotal());
                            for(Iterator<Volta> it2 = voltas.iterator(); it2.hasNext();){
                                if(ControllerCorrida.isPacoteSensor()){
                                    Thread.sleep(1500);
                                    break;
                                }else{
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
    }
    
}
