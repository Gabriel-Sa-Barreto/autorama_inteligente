/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

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
    
    private static List<Carro> carros;
    private static List<Piloto> pilotos;
    private static List<Administrador> adms;

    public ControllerGerenciador() {
        carros = new ArrayList<>();
        pilotos = new ArrayList<>();
        adms = new ArrayList<>();
    }
    
    
    
    public synchronized Carro cadastrarCarro(Carro novoCarro){
        carros.add(novoCarro); //salva o novo carro em sua respectiva lista
        carros.forEach(u -> System.out.println(u.getId()));
        return novoCarro;
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
    
    public synchronized void cadastrarPiloto(Piloto novoPiloto){
        pilotos.add(novoPiloto); //salva um novo piloto em sua respectiva lista
        pilotos.forEach(u -> System.out.println(u.getNome()));
    }
    
    public boolean existePiloto(String nome){
        for (Iterator<Piloto> it = pilotos.iterator(); it.hasNext();) {
            Piloto piloto = it.next();
            if(piloto.getNome().equals(nome))
                return true;
        }
        return false;
    }
    
    public synchronized void cadastrarAdministrador(Administrador adm){
        adms.add(adm); //salva um novo Adm em sua respectiva lista
        adms.forEach(u -> System.out.println(u.getNome()));
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
    
    /*Método para remoção de um carro do sistema*/
    public synchronized void removerPiloto(String nome){
        pilotos.removeIf( u -> u.getNome().equals(nome));
    }
    
    public synchronized void removerCarro(String id){
        carros.removeIf( u -> u.getId().equals(id));
    }
    
    
}
