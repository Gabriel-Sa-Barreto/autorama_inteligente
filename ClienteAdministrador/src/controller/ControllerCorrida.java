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
    
    /**Método para cadastrar a corrida.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param voltas int - quanitdade de voltas da corrida
    * @return Corrida - a instância da corrida realizada
    */
    public Corrida cadastrarCorrida(int voltas){
        Corrida nova = new Corrida(voltas);
        return nova;
    }
    
    /**Método para cadastrar a corrida.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param nova Corrida - salvar a corrida recebida pelo server
    */
    public void salvarCorrida(Corrida nova){
        corrida = nova;
    }
    
    /**Método que associa um carro ao seu piloto que será enviado para uma corrida
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param carro Carro - carro escolhido
    * @param piloto piloto - piloto escolhido
    * @return Carro - a instância do carro que vai competir na corrida realizada
    */
    public Carro cadastraCompetidor(Carro carro , Piloto piloto){
            carro.setPiloto(piloto); //associação de um piloto ao carro
            if(estaNaCorrida(carro)){//verifica se já está cadastrado na corrida
                return null;
            }
            return carro;
    }
    
    /**Método que salva todos os pilotos que serão enviados para uma corrida
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param carro Carro - competidor enviado pelo server
    */
    public void salvarCompetidor(Carro carro){
            List<Carro> carros = corrida.getCompetidores();
            carros.add(carro);
            corrida.setCompetidores(carros);
    }
    
    /**Método que verifica se ja possui uma corrida sendo cadastrada
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    */
    public boolean isNull(){
        if(this.corrida == null){
            return true;
        }
        return false;
    }
    
    /**Método que verifica se ja possui uma corrida em adamento
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @return boolean - retorna o estado da corrida
    */
    public boolean comecouCorrida(){
        return corrida.isEstado();
    }
    
    /**Método que inicia a corrida
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    */
    public void comecarPartida(){
        corrida.setEstado(true);
    }
    
    /**Método que verifica se aquele carro ja foi cadastrado como competidor
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param carro Carro - carro a ser inserido
    * @return boolean - se ja foi cadastrado ou não o carro na corrida
    */
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
