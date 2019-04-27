/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.Socket;
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
    private static ControllerRede rede = new ControllerRede();
    private static boolean pacoteSensor = false;
    
    /**Método para receber a corrida cadastrada pelo adm.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    */
    public void receberCorrida(Corrida nova){
	corrida = nova;
    }
    
    /**Método para informar as threads se o recurso da lista de voltas esta ocupado
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @return boolean - Valor dizendo se esta ocupado o recurso.
    */
    public static boolean isPacoteSensor() {
        return pacoteSensor;
    }

    /**Método para alterar o estado do recurso que as as threads compartilham.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    */
    public static void setPacoteSensor(boolean mudar) {
        pacoteSensor = mudar;
    }
    
    /**Método para um possivel registro de uma volta de um carro na lista.
    * A lista só guardara um registro de volta para cada piloto.
    * Quando detectar uma volta realizada a antiga vai ser sobrescrita pela nova.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param completada Volta - Uma volta enviada via pacote pelo server.
    */
    public void voltaCompleta(Volta completada){
	List<Volta> voltas = getVoltas(); //pega a lista de voltas da corrida
        Volta volta;
        LocalDate data = LocalDate.now(); // a data atual
	if(voltas.isEmpty()){ //verifica se a lista esta vazia
            completada.setQuantidade(quantidadeTotal());//essa volta armazenada é na largada por isso não se subtrai a quantidade de voltas qur falta
            voltas.add(completada); //adiciona na lista
	}
	else{
            volta = procurarVolta(voltas , completada); // verifica se já possui uma volta registrada na lista
            if(volta == null){ // se for null é porque não achou
                completada.setQuantidade(quantidadeTotal());//essa volta armazenada é na largada por isso não se subtrai a quantidade de voltas qur falta
		voltas.add(completada);// adiciona na lista
                //record.adicionarRecord(completada, data.toString());
            }
            else{
                // caso já possua uma volta na lista verifica se pelo tempo ele ja poderia ter dado uma volta
                if(deuUmaVolta(completada , volta)){ //caso retorne true é porque já deu tempo de ter feito uma volta
                    completada.setTempoVolta(tempoVolta(volta , completada)); // insere o tempo da volta via o calculo realizado pela função tempoVolta()
                    completada.setQuantidade(volta.getQuantidade() - 1); // diminuir um em volta que faltam o piloto
                    corrida.setCompletadas(volta.getQuantidade() - 1); // diminuir um em volta que faltam na corrida
                    voltas.removeIf(u -> u.equals(completada)); //uso de lambda para a remoção da celula ja existente atraves do id
                    voltas.add(completada); // adiciona a volta nova
                    record.adicionarRecord(completada, data.toString()); //verifica se bateu o record
                }
            }	
	}
        voltas.sort(Comparator.comparingInt( u -> u.getQuantidade())); //uso de lambda para ordenar a lista via quantidade de voltas que faltam
        //mostrar no controle o gerenciamento das voltas via uso de lambda
        voltas.forEach(u -> System.out.println( "v id:" + u.getCarro().getId() + " " + "te: " + u.getTempoVolta() + " " + "volt: " + u.getQuantidade()));
        corrida.setVoltas(voltas); // devolve a lista modificada
    }
    
    /**Método para verificar se um carro já teria realizado uma volta.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param teste Volta - Uma volta recebida via pacote.
    * @param anterior Volta - Uma volta armazenada daquele carro na lista.
    * @return boolean - Valor dizendo se realizou uma volta ou não.
    */
    private boolean deuUmaVolta(Volta teste , Volta anterior){
	String resultTest[] = teste.getTempo().split(":"); //realizar o split para pegar separadamente os minutos e segundos do tempo
	String resultAnterior[] = anterior.getTempo().split(":");//realizar o split para pegar separadamente os minutos e segundos do tempo
        int minutosTeste = strToInt(resultTest[1] , 10); //transformar a String em int via funcao strToInt
        int minutosAnterior = strToInt(resultAnterior[1] , 10);//transformar a String em int via funcao strToInt
        int diferencaM = minutosTeste - minutosAnterior; // verificar a diferença de minutos entre as voltas
        //se forem iguais os minutos vai para etapa de verificar os segundos
        if(diferencaM == 0){
            int secundosTeste = strToInt(resultTest[2] , 10);//transformar a String em int via funcao strToInt
            int secundosAnterior = strToInt(resultAnterior[2] , 10);//transformar a String em int via funcao strToInt
            int diferencaS = secundosTeste - secundosAnterior;// verificar a diferença de segundos entre as voltas
            if(diferencaS < 8) //se for menor que não realizou uma volta
                return false;
        }
        else if(diferencaM < 0) //se for negativo o valor retornara false
            return false;
        return true; // caso minutos não for iguais e a diferença maior que 8 retornará true dizendo que realizou uma volta
    }
    
    /**Método para encerrar a corrida colocando o atributo comecou da classe Corrida em false.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    */
    public void acabarCorrida(){
        corrida.setEstado(false);
    }
    
    /**Método para retornar a quantidade de voltas que falta.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @return int - Quantidade de voltas que faltam a ser realizadas.
    */
    public int quantidadeTotal(){//
        return corrida.getTotaisDeVoltas();
    }
    
    /**Método para quando encerrar a corrida enviar os records dos pilotos nessa corrida e limpar as listas de competidores e voltas.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param socket Socket - O socket para o envio dos records.
    */
    public void linhaFinal(Socket socket){
        if(corrida.getCompletadas() == 0){
            corrida.setEstado(false); //encerrar corrida
            enviarRecords(socket); //enviar os records para o server
            corrida.limparCompetidores(); // limpar a lista de competidores
            corrida.limparVoltas();// limpar a lista de voltas
        }    
    }
    
    /**Método para quando o adm quiser pausar ou reiniciar corrida.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    */
    public void pausar_reiniciarCorrida(){
        if(!estaPausado()) //se o atributo pausado da classe Corrida estiver false é porque está em andamento
            corrida.setPause(true); // então ira realizar o pause da corrida
        else
            corrida.setPause(false); // então ira realizar o reiniciar da corrida
    }
    
    /**Método para informar se a corrida esta pausada.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @return boolean - Valor dizendo se esta pausada a corrida.
    */
    public boolean estaPausado(){
        return corrida.isPause();
    }
    
    /**Método para quando o adm quiser começar a corrida após o cadastros dos competidores da corrida.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    */
    public void comecarPartida(){
        corrida.setEstado(true);
    }
    
    /**Método para retornar a lista de competidores da corrida.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @return List - retorna a lista de competidores.
    */
    public List<Carro> competidores(){
        return corrida.getCompetidores();
    }
    
    /**Método para retornar se a corrida esta em andamento.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @return boolean - retornar se a corrida esta em andamento.
    */
    public boolean partidaEmAdamento(){ 
        return corrida.isEstado();
    }
    
    /**Método para retornar a lista de voltas da corrida.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @return List - retorna a lista de voltas da corrida.
    */
    public List<Volta> getVoltas(){ 
        return corrida.getVoltas();
    }
    
    /**Método para retornar o record de um piloto na corrida.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param piloto String - o nome do piloto que se deseja o record.
    * @return String - retorna o record do piloto na volta.
    */
    public String getRecord(String piloto){ 
        return record.recordPiloto(piloto);
    }
    
    /**Método para adicionar um competidor na corrida.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param carro Carro - o carro juntamente inserido com seu piloto na corrida.
    */
    public void adicionarCompetidor(Carro carro){
        if(carro != null){ //se não for null insere na lista de competidor
            List<Carro> carros = corrida.getCompetidores(); // recebe a lista
            carros.add(carro); // inserir na lista
            corrida.setCompetidores(carros); //devolve a lista
        }
    }
    
    /**Método para adicionar um recorde geral do circuito do autorama.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param geral Record - receber o record geral do jogo via server.
    */
    public void adicionarRecordGeral(Record geral){
        record.cadastrarRecordGeral(geral);
    }
    
    /**Método para retornar uma volta ja cadastrada na lista de voltas.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param lista List - receber a lista de voltas da corrida.
    * @param comparar Volta - volta recebida pelo sensor.
    */
    private Volta procurarVolta(List<Volta> lista , Volta comparar){
        for (Iterator<Volta> it1 = lista.iterator(); it1.hasNext();) { //percorre a lista de voltas
            Volta volta = it1.next(); // pega a volta cadastrada
            if(volta.equals(comparar)) // comparar para ver se e do mesmo piloto
                return volta; // caso ache do mesmo piloto retorna ela
        }
        return null; // caso não ache retornar null
    }
    
    /**Método private para auxiliar na conversao de String para int
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param valor List - receber a String a ser convertida.
    * @param padrao int - caso aconteça uma exceção um valor padrão que possa ser inserido.
    * @return int - retornar o inteiro.
    */
    private int strToInt(String valor, int padrao) {
        try {
            return Integer.valueOf(valor); // Para retornar um Integer, use Integer.parseInt
        } 
        catch (NumberFormatException e) {  // Se houver erro na conversão, retorna o valor padrão
            return padrao;
        }
    }
    
    /**Método private para auxiliar na verificação do tempo de uma volta feito por um carro.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param anterior Volta - volta anterior.
    * @param posterior Volta - volta a ser inserida.
    * @return String - retorna o tempo da volta do piloto na volta.
    */
    private String tempoVolta(Volta anterior , Volta posterior){
        String tempo;
        int resultado;
        String tempoAnterior[] =  anterior.getTempo().split(":"); //´split na string de tempo
        String tempoPosterior[] =  posterior.getTempo().split(":");//´split na string de tempo
        int minutosAnterior = strToInt(tempoAnterior[1] , 10);  // pega os minutos
        int minutosPosterior = strToInt(tempoPosterior[1] , 10);// pega os minutos
        int secundosAnterior = strToInt(tempoAnterior[2] , 10);// pega os segundos
        int secundosPosterior = strToInt(tempoPosterior[2] , 10);// pega os segundos
        resultado = minutosPosterior - minutosAnterior; //subtração dos minutos obtidos
        if(minutosAnterior == minutosPosterior){ // se forem iguais a diferença vai ser só de segundo
            tempo = Integer.toString(resultado) + ":"; // insere o "00" obtido na subtração
            resultado = secundosPosterior - secundosAnterior; // pega a diferença de segundos
            tempo = tempo + Integer.toString(resultado); // concatena e assim obtenhe o tempo
        }
        else{
            int minutosParaSegundo = resultado * 60; // caso nao seja igual transforma a diferença de minutos em segundos
            resultado = secundosPosterior - secundosAnterior; // pega a diferença de segundos
            resultado = Math.abs(resultado); // pega o modulo de segundos
            resultado = minutosParaSegundo - resultado; // subtrair a diferença em minutosParaSegundo por resultado
            if(resultado < 60){ // se for menor que 60 a diferença é menor que um minuto
                tempo = "00:"; // diferenca de um minuto
                tempo = tempo + Integer.toString(resultado);// concatena e assim obtenhe o tempo
            }
            else{ //caso gor maior que um minuto
                int minutos = resultado / 60; //divide por 60 para obter os minutos na diferenca
                int resto = resultado % 60; // pega o resto da divisão
                tempo = Integer.toString(minutos) + ":" + Integer.toString(resto);// concatena e assim obtenhe o tempo
            }
        }    
        return tempo; //retorna o tempo obtido
    }
    
    /**Método private para auxiliar no envio dos records
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param socket Socket - O socket para o envio dos records.
    */
    private void enviarRecords(Socket socket){
        List<Record> records = record.records();
        for(Record record : records){
            rede.enviarDado(socket, record.toString(), "50");
        }
    }
}
