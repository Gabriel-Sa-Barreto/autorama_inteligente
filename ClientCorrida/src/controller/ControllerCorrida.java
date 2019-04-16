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
    
    Corrida corrida;
    ControllerRecord record = new ControllerRecord(); 
    
    public void receberCorrida(Corrida nova){
	corrida = nova;
    }
    
    public void voltaCompleta(Volta completada){
	List<Volta> voltas = corrida.getVoltas();
        Volta volta;
        LocalDate data = LocalDate.now();
	if(voltas.isEmpty()){
            completada.setQuantidade(corrida.getTotaisDeVoltas());
            voltas.add(completada);
            //record.adicionarRecord(completada, data.toString());
	}
	else{
            volta = procurarVolta(voltas , completada);
            if(volta == null){
                completada.setQuantidade(corrida.getTotaisDeVoltas());
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
        voltas.forEach(u -> System.out.println( "v" + u.getCarro().getId() + u.getTempoVolta() + u.getQuantidade()));
        corrida.setVoltas(voltas);
        linhaFinal();
    }
    
    public boolean deuUmaVolta(Volta teste , Volta anterior){
	String resultTest[] = teste.getTempo().split(":");
	String resultAnterior[] = anterior.getTempo().split(":");
        int secundosTeste = strToInt(resultTest[2] , 10);
        int secundosAnterior = strToInt(resultAnterior[2] , 10);
        int diferenca = secundosTeste - secundosAnterior;
        if(diferenca < 2)
            return false;
        return true;
    }
    
    public void acabarCorrida(){
        corrida.setEstado(false);
    }
    
    public void linhaFinal(){
        if(corrida.getCompletadas() == corrida.getTotaisDeVoltas())
            corrida.setEstado(false);
    }
    
    public void pausarCorrida(){
        corrida.setPause(true);
    }
    
    public boolean estaPausado(){
        return corrida.isPause();
    }
    
    public void comecarPartida(){
        corrida.setEstado(true);
    }
    
    public List<Carro> competidores(){
        return corrida.getCompetidores();
    }
    
    public boolean partidaEmAdamento(){
        return corrida.isEstado();
    }
    
    public void adicionarCompetidor(Carro carro){
        if(carro != null){
            List<Carro> carros = corrida.getCompetidores();
            carros.add(carro);
            corrida.setCompetidores(carros);
            carros.forEach(u -> System.out.println( "c" + u.getId() + "n" + u.getPiloto().getNome()));
        }
    }
    
    public void adicionarRecordGeral(Record geral){
        record.cadastrarRecordGeral(geral);
    }
    
    private Volta procurarVolta(List<Volta> lista , Volta comparar){
        for (Iterator<Volta> it = lista.iterator(); it.hasNext();) {
            Volta volta = it.next();
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
        resultado = minutosPosterior - minutosAnterior;
        tempo = Integer.toString(resultado) + ":";
        int secundosAnterior = strToInt(tempoAnterior[2] , 10);
        int secundosPosterior = strToInt(tempoPosterior[2] , 10);
        resultado = secundosPosterior - secundosAnterior;
        tempo = tempo + Integer.toString(resultado);
        return tempo;
    }
}
