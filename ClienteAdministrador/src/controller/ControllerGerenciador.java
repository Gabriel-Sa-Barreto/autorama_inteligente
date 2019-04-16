/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Administrador;
import model.Carro;
import model.Piloto;

/**
 *
 * @author lsjsa
 */
public class ControllerGerenciador {
    
    List<Carro> carros;
    List<Piloto> pilotos;
    List<Administrador> adms;

    public ControllerGerenciador() {
        carros = new ArrayList<>();
        pilotos = new ArrayList<>();
        adms = new ArrayList<>();
    }
    
    
    
    public Carro cadastrarCarro(String tag , String equipe , String numero){
        Carro adicionar = new Carro(tag , equipe , numero);
        carros.add(adicionar);
        return adicionar;
    }
    
    public boolean existeCarro(String id){
        if(carros.isEmpty())
            return false;
        for (Iterator<Carro> it = carros.iterator(); it.hasNext();) {
            Carro carro = it.next();
            if(carro.getId().equals(id))
                return true;
        }
        return false;
    }
    
    public Piloto cadastrarPiloto(String nome){
        Piloto adicionar = new Piloto(nome);
        pilotos.add(adicionar);
        return adicionar;
    }
    
    public boolean existePiloto(String nome){
        for (Iterator<Piloto> it = pilotos.iterator(); it.hasNext();) {
            Piloto piloto = it.next();
            if(piloto.getNome().equals(nome))
                return true;
        }
        return false;
    }
    
    public Administrador cadastrarAdministrador(String nome){
        Administrador adicionar = new Administrador(nome);
        adms.add(adicionar);
        return adicionar;
    }
    
    public boolean existeAdm(String nome){
        for (Iterator<Administrador> it = adms.iterator(); it.hasNext();) {
            Administrador adm = it.next();
            if(adm.getNome().equals(nome))
                return true;
        }
        return false;
    }
    
    public Piloto piloto(String nome){
        for (Iterator<Piloto> it = pilotos.iterator(); it.hasNext();) {
            Piloto piloto = it.next();
            if(piloto.getNome().equals(nome))
                return piloto;
        }
        return null;
    }
    
    public Carro carro(String nome){
        for (Iterator<Carro> it = carros.iterator(); it.hasNext();) {
            Carro carro = it.next();
            if(carro.getId().equals(nome))
                return carro;
        }
        return null;
    }
    
    public void removerPiloto(String nome){
        pilotos.removeIf( u -> u.getNome().equals(nome));
    }
    
    public void removerCarro(String id){
        carros.removeIf( u -> u.getId().equals(id));
    }
    
    
}
