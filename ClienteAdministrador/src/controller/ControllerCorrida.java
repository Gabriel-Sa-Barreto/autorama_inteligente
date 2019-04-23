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

/**
 *
 * @author lsjsa
 */
public class ControllerCorrida {
    
    private static Corrida corrida;
    
    
    public Corrida cadastrarCorrida(int voltas){
        Corrida nova = new Corrida(voltas);
        return nova;
    }
    
    public void salvarCorrida(Corrida nova){
        corrida = nova;
        System.out.println(corrida.isEstado());
    }
    
    public Carro cadastraCompetidor(Carro carro , Piloto piloto){
            carro.setPiloto(piloto);
            return carro;
    }
    
    public void salvarCompetidor(Carro carro){
            List<Carro> carros = corrida.getCompetidores();
            carros.add(carro);
            corrida.setCompetidores(carros);
    }
    
    public boolean comecouCorrida(){
        return corrida.isEstado();
    }
    
     public void comecarPartida(){
        corrida.setEstado(true);
    }
    
    public boolean estaNaCorrida(Carro carro){
        List<Carro> carros = corrida.getCompetidores();
        for (Iterator<Carro> it = carros.iterator(); it.hasNext();) {
            Carro prova = it.next();
            System.out.println(prova.equals(carro));
            if(prova.equals(carro))
                return true;
        }
        return false;
    }
}
