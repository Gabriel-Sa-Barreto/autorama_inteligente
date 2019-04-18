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

/**
 *
 * @author lsjsa
 */
public class ControllerCorrida {
    
    private static Corrida corrida;
    
    
    public synchronized void cadastrarCorrida(int voltas){
        if(corrida.isStatus()){
            return;
        }
        corrida = new Corrida(voltas); //cadastra nova corrida
    }
    
    /*Método que salva um novo corredor na lista de competidores da nova corrida que será iniciada*/
    public synchronized void salvarCompetidor(Carro carro){
        List<Carro> carros = corrida.getCompetidores();
        carros.add(carro);
        corrida.setCompetidores(carros);
    }
    
    public boolean comecouCorrida(){
        return corrida.isComecou();
    }
    
    public boolean estaNaCorrida(Carro carro){
        List<Carro> carros = corrida.getCompetidores();
        for (Iterator<Carro> it = carros.iterator(); it.hasNext();) {
            Carro prova = it.next();
            System.out.println(prova.equals(carro));
            if(prova.equals(carro)){
                return true;
            }
        }
        return false;
    }
    
    public void pausar_reiniciar(){
        if(corrida.isComecou()){
           corrida.setComecou(false); //pausar
        }else{
           corrida.setComecou(true); //reiniciar
        }
    }
    
}
