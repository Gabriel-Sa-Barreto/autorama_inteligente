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
    
    private static Corrida corrida = null;
    
    
    public Corrida cadastrarCorrida(int voltas){
        Corrida nova = new Corrida(voltas);
        return nova;
    }
    
    public void salvarCorrida(Corrida nova){
        corrida = nova;
        System.out.println(corrida.isEstado());
    }
    
     /*Método que associa um carro ao seu piloto que será enviado para uma corrida*/
    public Carro cadastraCompetidor(Carro carro , Piloto piloto){
            carro.setPiloto(piloto); //associação de um piloto ao carro
            if(estaNaCorrida(carro)){//verifica se já está cadastrado na corrida
                return null;
            }
            return carro;
    }
    
    /*Método que salva todos os pilotos que serão enviados para uma corrida*/
    public void salvarCompetidor(Carro carro){
            List<Carro> carros = corrida.getCompetidores();
            carros.add(carro);
            corrida.setCompetidores(carros);
    }
    
    public boolean isNull(){
        if(this.corrida == null){
            return true;
        }
        return false;
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
            if(prova.equals(carro))
                return true;
        }
        return false;
    }
}
