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


/**
 *
 * @author lsjsa
 */
public class ControllerCorrida {
    
    /**
     * Atributo para guardar dados da corrida.
     */
    private static Corrida corrida = null;
    
    
    /**Método para cadastrar a corrida.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param voltas int - quanitdade de voltas da corrida
    */
    public synchronized void cadastrarCorrida(int voltas){
        if(corrida != null){ //verificar se pode cadastrar corrida
            if(corrida.isStatus()){
                return;
            }
        }
        corrida = new Corrida(voltas); //cadastra nova corrida
    }
    
    /**Método para comecar a partida
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    */
    public void comecarPartida(){
        corrida.setComecou(true);
    }
    
    public void terminarCorrida(){
        corrida = null;
    }
    
    /**Método que salva um novo corredor na lista de competidores da nova corrida que será iniciada
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param carro Carro - salvar competidor
    */
    public synchronized void salvarCompetidor(Carro carro){
        List<Carro> carros = corrida.getCompetidores();
        carros.add(carro);
        corrida.setCompetidores(carros);
    }
    
    /**Método que verifica se ja possui uma corrida em adamento
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @return boolean - retorna o estado da corrida
    */
    public boolean comecouCorrida(){
        return corrida.isComecou();
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
            System.out.println(prova.equals(carro));
            if(prova.equals(carro)){
                return true;
            }
        }
        return false;
    }
    
    /**Método para quando o adm quiser pausar ou reiniciar corrida.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    */
    public void pausar_reiniciar(){
        if(corrida.isComecou()){
           corrida.setComecou(false); //pausar
        }else{
           corrida.setComecou(true); //reiniciar
        }
    }
    
}
