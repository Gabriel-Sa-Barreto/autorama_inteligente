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
public class Volta {
    
    private Carro carro;
    private String tempo;
    private String tempoVolta;
    private int quantidade;

    public Volta(Carro carro, String tempo) {
        this.carro = carro;
        this.tempo = tempo;
        this.tempoVolta = "00:00";
    }
    
    
    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getTempoVolta() {
        return tempoVolta;
    }

    public void setTempoVolta(String tempoVolta) {
        this.tempoVolta = tempoVolta;
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
        final Volta outro = (Volta) obj;
        return outro.getCarro().getId().equals(this.carro.getId());
    }
    
    
    
    
}
