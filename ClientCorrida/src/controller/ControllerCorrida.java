/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import model.Carro;
import model.Corrida;
import model.Record;
import model.Volta;

/**
 *
 * @author lsjsa
 */
public class ControllerCorrida {
    
    private static Corrida corrida;
    private static ControllerRecord record = new ControllerRecord(); 
    
    public void receberCorrida(Corrida nova){
	corrida = nova;
    }
    
    public void voltaCompleta(Volta completada){
	List<Volta> voltas = getVoltas();
        Volta volta;
        LocalDate data = LocalDate.now();
	if(voltas.isEmpty()){
            completada.setQuantidade(quantidadeTotal());
            voltas.add(completada);
            //record.adicionarRecord(completada, data.toString());
	}
	else{
            volta = procurarVolta(voltas , completada);
            if(volta == null){
                completada.setQuantidade(quantidadeTotal());
		voltas.add(completada);
                //record.adicionarRecord(completada, data.toString());
            }
            else{
                if(deuUmaVolta(completada , volta)){
                    completada.setTempoVolta(tempoVolta(volta , completada));
                    completada.setQuantidade(volta.getQuantidade() - 1);
                    corrida.setCompletadas(volta.getQuantidade() - 1);
                    voltas.removeIf(u -> u.equals(completada));
                    voltas.add(completada);
                    record.adicionarRecord(completada, data.toString());
                }
            }	
	}
        voltas.sort(Comparator.comparingInt( u -> u.getQuantidade()));
        //System.out.println(voltas.size());
        voltas.forEach(u -> System.out.println( "v id:" + u.getCarro().getId() + " " + "te: " + u.getTempoVolta() + " " + "volt: " + u.getQuantidade()));
        corrida.setVoltas(voltas);
        linhaFinal();
    }
    
    private boolean deuUmaVolta(Volta teste , Volta anterior){
	String resultTest[] = teste.getTempo().split(":");
	String resultAnterior[] = anterior.getTempo().split(":");
        int minutosTeste = strToInt(resultTest[1] , 10);
        int minutosAnterior = strToInt(resultAnterior[1] , 10);
        int diferencaM = minutosTeste - minutosAnterior;
        System.out.println(diferencaM);
        if(diferencaM == 0){
            int secundosTeste = strToInt(resultTest[2] , 10);
            int secundosAnterior = strToInt(resultAnterior[2] , 10);
            int diferencaS = secundosTeste - secundosAnterior;
            System.out.println(diferencaS);
            if(diferencaS < 8)
                return false;
        }
        else if(diferencaM < 0)
            return false;
        return true;
    }
    
    public void acabarCorrida(){
        corrida.setEstado(false);
    }
    
    public synchronized int quantidadeTotal(){//
        return corrida.getTotaisDeVoltas();
    }
    
    public void linhaFinal(){
        if(corrida.getCompletadas() == corrida.getTotaisDeVoltas())
            corrida.setEstado(false);
    }
    
    public void pausar_reiniciarCorrida(){
        if(!estaPausado())
            corrida.setPause(true);
        else
            corrida.setPause(false);
    }
    
    public boolean estaPausado(){
        return corrida.isPause();
    }
    
    public void comecarPartida(){
        corrida.setEstado(true);
        System.out.println(corrida.isEstado());
    }
    
    public List<Carro> competidores(){
        return corrida.getCompetidores();
    }
    
    public synchronized boolean partidaEmAdamento(){ //
        return corrida.isEstado();
    }
    
    public List<Volta> getVoltas(){ 
        return corrida.getVoltas();
    }
    
    public String getRecord(String piloto){ 
        return record.recordPiloto(piloto);
    }
    
    public void adicionarCompetidor(Carro carro){
        if(carro != null){
            List<Carro> carros = corrida.getCompetidores();
            carros.add(carro);
            corrida.setCompetidores(carros);
        }
    }
    
    public void adicionarRecordGeral(Record geral){
        record.cadastrarRecordGeral(geral);
    }
    
    
    private Volta procurarVolta(List<Volta> lista , Volta comparar){
        for (Iterator<Volta> it1 = lista.iterator(); it1.hasNext();) {
            Volta volta = it1.next();
            if(volta.equals(comparar))
                return volta;
        }
        return null;
    }

    private int strToInt(String valor, int padrao) {
        try {
            return Integer.valueOf(valor); // Para retornar um Integer, use Integer.parseInt
        } 
        catch (NumberFormatException e) {  // Se houver erro na conversão, retorna o valor padrão
            return padrao;
        }
    }
    
    private String tempoVolta(Volta anterior , Volta posterior){
        String tempo;
        int resultado;
        String tempoAnterior[] =  anterior.getTempo().split(":");
        String tempoPosterior[] =  posterior.getTempo().split(":");
        int minutosAnterior = strToInt(tempoAnterior[1] , 10);
        int minutosPosterior = strToInt(tempoPosterior[1] , 10);
        int secundosAnterior = strToInt(tempoAnterior[2] , 10);
        int secundosPosterior = strToInt(tempoPosterior[2] , 10);
        resultado = minutosPosterior - minutosAnterior;
        if(minutosAnterior == minutosPosterior){
            tempo = Integer.toString(resultado) + ":";
            resultado = secundosPosterior - secundosAnterior;
            tempo = tempo + Integer.toString(resultado);
        }
        else{
            int minutosParaSegundo = resultado * 60;
            resultado = secundosPosterior - secundosAnterior;
            resultado = Math.abs(resultado);
            resultado = minutosParaSegundo - resultado;
            if(resultado < 60){
                tempo = "00:";
                tempo = tempo + Integer.toString(resultado);
            }
            else{
                int minutos = resultado / 60;
                int resto = resultado % 60;
                tempo = Integer.toString(minutos) + ":" + Integer.toString(resto);
            }
        }    
        return tempo;
    }
}
