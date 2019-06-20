/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author lsjsa
 */
public class Piloto implements Comparable<Piloto>{
    
    private String nome;
    private Record record;

    public Piloto(String nome) {
        this.nome = nome;
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Piloto testar = (Piloto) obj;
        return this.nome.equals(testar.getNome());
    }
    
    @Override
    public String toString() {
        if(record != null)
            return nome + ";" + record.getData() + ";" + record.getTempo();
        else 
            return nome;
    }

    @Override
    public int compareTo(Piloto t) {
        if(this.getRecord() == null)
            return 1;
        if(t.getRecord() == null)
            return -1;
        String resultThis[] = this.record.getTempo().split(":"); //realizar o split para pegar separadamente os minutos e segundos do tempo
	String resultComparar[] = t.getRecord().getTempo().split(":");//realizar o split para pegar separadamente os minutos e segundos do tempo
        int minutosThis = strToInt(resultThis[0] , 10); //transformar a String em int via funcao strToInt
        int minutosComparar = strToInt(resultComparar[0] , 10);//transformar a String em int via funcao strToInt
        int diferencaM = minutosThis - minutosComparar; // verificar a diferença de minutos entre as voltas
        if(diferencaM == 0){
            int secundosThis = strToInt(resultThis[1] , 10);//transformar a String em int via funcao strToInt
            int secundosComparar = strToInt(resultComparar[1] , 10);//transformar a String em int via funcao strToInt
            int diferencaS = secundosThis - secundosComparar;// verificar a diferença de segundos entre as voltas
            if(diferencaS < 0)
                return -1;
            else if(diferencaS > 0)
                return 1;
            else
                return -1;
        }
        else if(diferencaM < 0)
            return -1;
        else
            return 1;
                    
    }
    
    private int strToInt(String valor, int padrao) {
        try {
            return Integer.valueOf(valor); // Para retornar um Integer, use Integer.parseInt
        } 
        catch (NumberFormatException e) {  // Se houver erro na conversão, retorna o valor padrão
            return padrao;
        }
    }
}
