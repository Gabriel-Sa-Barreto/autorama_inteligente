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
    
    Corrida corrida;
    
    
    public Corrida cadastrarCorrida(int voltas){
        corrida = new Corrida(voltas);
        return corrida;
    }
    
    public Carro cadastraCompetidor(Carro carro , Piloto piloto){
            carro.setPiloto(piloto);
            List<Carro> carros = corrida.getCompetidores();
            carros.add(carro);
            corrida.setCompetidores(carros);
            carros.forEach(u -> System.out.println(u.getId()));
            return carro;
    }
    
    public boolean comecouCorrida(){
        return corrida.isEstado();
    }
    
    public boolean estaNaCorrida(Carro carro){
        List<Carro> carros = corrida.getCompetidores();
        for (Iterator<Carro> it = carros.iterator(); it.hasNext();) {
            Carro prova = it.next();
            System.out.println(prova.equals(carro));
            if(prova.equals(carro)){
                System.out.println("TEste");
                return true;
            }
        }
        return false;
    }
}
