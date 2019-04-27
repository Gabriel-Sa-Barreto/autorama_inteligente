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
public class Record {
    
    private String piloto;
    private String data;
    private String tempo;

    public Record(String data, String tempo , String piloto) {
        this.data = data;
        this.tempo = tempo;
        this.piloto = piloto;
    }
    
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getPiloto() {
        return piloto;
    }

    public void setPiloto(String piloto) {
        this.piloto = piloto;
    }
    
    

    @Override
    public String toString() {
        return data + ";" + tempo + ";"+ piloto;
    }

    public int compareTo(String tempo) {
        String comparar[] = tempo.split(":");
        String este[] = this.tempo.split(":");
        if(strToInt(este[0] , 10) > strToInt(comparar[0] , 10))
            return 1;
        else if(strToInt(este[0] , 10) == strToInt(comparar[0] , 10)){
            if(strToInt(este[1] , 10) > strToInt(comparar[1] , 10))
                return 1;
            else if(strToInt(este[1] , 10) < strToInt(comparar[1] , 10))
                return -1;
            else
                return 0;
        }
        return -1;
    }
    
    public int strToInt(String valor, int padrao) {
        try {
            return Integer.valueOf(valor); // Para retornar um Integer, use Integer.parseInt
        } 
        catch (NumberFormatException e) {  // Se houver erro na conversão, retorna o valor padrão
            return padrao;
        }
    }
    
}
