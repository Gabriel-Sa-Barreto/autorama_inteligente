/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lsjsa
 */
public class Corrida {
    
    private List<Volta> voltas;
    private List<Carro> competidores;
    private int totaisDeVoltas;
    private int completadas;
    private boolean comecou;
    private boolean pause;

    public Corrida(int voltas) {
        this.totaisDeVoltas = voltas;
        competidores = new ArrayList<>();
        this.voltas = new ArrayList<>();
        comecou = false;
        completadas = 0;
        pause = false;
    }

    public List<Carro> getCompetidores() {
        return competidores;
    }

    public void setCompetidores(List<Carro> competidores) {
        this.competidores = competidores;
    }

    public int getTotaisDeVoltas() {
        return totaisDeVoltas;
    }

    public void setTotaisDeVoltas(int voltas) {
        this.totaisDeVoltas = voltas;
    }

    public boolean isEstado() {
        return comecou;
    }

    public void setEstado(boolean comecou) {
        this.comecou = comecou;
    }

    public List<Volta> getVoltas() {
        return voltas;
    }

    public void setVoltas(List<Volta> voltas) {
        this.voltas = voltas;
    }

    public int getCompletadas() {
        return completadas;
    }

    public void setCompletadas(int completadas) {
        this.completadas = completadas;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }
    
    public void limparCompetidores(){
        competidores.clear();
    }
    
    public void limparVoltas(){
        voltas.clear();
    }
    
}
