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
    
    private List<Carro> competidores;
    private int voltas;
    private boolean comecou;
    private boolean status;

    public Corrida(int voltas) {
        this.voltas = voltas;
        competidores = new ArrayList<>();
        comecou = false;
        this.status = true;
    }

    public List<Carro> getCompetidores() {
        return competidores;
    }

    public void setCompetidores(List<Carro> competidores) {
        this.competidores = competidores;
    }

    public int getVoltas() {
        return voltas;
    }

    public void setVoltas(int voltas) {
        this.voltas = voltas;
    }

    public boolean isComecou() {
        return comecou;
    }

    public void setComecou(boolean comecou) {
        this.comecou = comecou;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        String mandar = Integer.toString(voltas) + ";" + comecou;
        return mandar;
    }
}
