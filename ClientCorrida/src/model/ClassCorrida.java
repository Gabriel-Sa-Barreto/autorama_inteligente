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
import static view.ClientRace.corrida;

/**
 *
 * @author lsjsa
 */
public class ClassCorrida implements Runnable{
    
    private ControllerCorrida corrida;
    private javax.swing.JTable jTCorrida;
    private DefaultTableModel classCorrida;
    
    public ClassCorrida(ControllerCorrida corrida, javax.swing.JTable qualificacao){
        this.corrida        = corrida;
        this.classCorrida   = (DefaultTableModel) qualificacao.getModel();
        this.jTCorrida = qualificacao;
    }

    @Override
    public void run() {
        while(corrida.partidaEmAdamento()){
            try{
                if(corrida.partidaEmAdamento()){
                    classCorrida.setRowCount(corrida.competidores().size());
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
                                    jTCorrida.setValueAt(i, i, 0);
                                    jTCorrida.setValueAt(nome, i, 1);
                                    jTCorrida.setValueAt(volta.getCarro().getEquipe() , i, 2);
                                    jTCorrida.setValueAt(volta.getTempoVolta() , i, 3);
                                    if(corrida.getRecord(nome) != null)
                                        jTCorrida.setValueAt(corrida.getRecord(nome) , i, 4);
                                    else
                                        jTCorrida.setValueAt("00:00" , i, 4);
                                    jTCorrida.setValueAt(volta.getQuantidade() , i, 5);
                                    i++;
                                }
                            }
                        }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Não tem corrida no momento");
                    break;
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null,ex);
            }    
        }
    }
    
}
