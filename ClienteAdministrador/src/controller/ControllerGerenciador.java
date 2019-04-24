/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.swing.table.DefaultTableModel;
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
        carros = Collections.synchronizedList(new ArrayList<Carro>());
        pilotos = Collections.synchronizedList(new ArrayList<Piloto>());
        adms = Collections.synchronizedList(new ArrayList<Administrador>());
    }
    
    public Carro cadastrarCarro(String tag , String equipe , String numero){ //cadastrando pessoalmente
        Carro adicionar = new Carro(tag , equipe , numero);
        return adicionar;
    }
    
    public void salvarCarro(Carro novo){ //receber pacote
        synchronized(carros){ //sincroniza o acesso à lista de carro
            carros.add(novo);
        }
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
    
    public Piloto cadastrarPiloto(String nome){ //cadastrando pessoalmente
        Piloto adicionar = new Piloto(nome);
        return adicionar;
    }
    
    public void salvarPiloto(Piloto novo){ //receber pacote
        synchronized(pilotos){ //sincroniza o acesso à lista de piloto
            pilotos.add(novo);
        }
    }
    
    public boolean existePiloto(String nome){
        for (Iterator<Piloto> it = pilotos.iterator(); it.hasNext();) {
            Piloto piloto = it.next();
            if(piloto.getNome().equals(nome))
                return true;
        }
        return false;
    }
    
    public List<Piloto> getPilotos(){
        return pilotos;
    }
    
    public List<Carro> getCarros(){
        return carros;
    }
                    
    public Administrador cadastrarAdministrador(String nome , String senha){ //cadastrando pessoalmente
        Administrador adicionar = new Administrador(nome , senha);
        return adicionar;
    }
    
    public void salvarAdm(Administrador novo){ //receber pacote
        synchronized(adms){
            adms.add(novo);
        }
    }
    
    public boolean existeAdm(String nome, String senha){
        for (Iterator<Administrador> it = adms.iterator(); it.hasNext();) {
            Administrador adm = it.next();
            if(adm.getNome().equals(nome) && adm.getSenha().equals(senha))
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
    
    public Carro carro(String idCarro){
        for (Iterator<Carro> it = carros.iterator(); it.hasNext();) {
            Carro carro = it.next();
            if(carro.getId().equals(idCarro))
                return carro;
        }
        return null;
    }
    
    public void removerPiloto(String nome){
        synchronized(pilotos){
            pilotos.removeIf( u -> u.getNome().equals(nome));
        }
    }
    
    public void removerCarro(String id){
        synchronized(carros){
            carros.removeIf( u -> u.getId().equals(id));
        }
    }
    
    public int strToInt(String valor, int padrao) {
        try {
            return Integer.valueOf(valor); // Para retornar um Integer, use Integer.parseInt
        } 
        catch (NumberFormatException e) {  // Se houver erro na conversão, retorna o valor padrão
            return padrao;
        }
    }
    
    /*Método que limpa a tabela nas interfaces*/
    public void limpaTabela(javax.swing.JTable table){
        DefaultTableModel tblRemove = (DefaultTableModel) table.getModel();
        while(tblRemove.getRowCount() != 0){
            tblRemove.removeRow(0);
        }            
    }
}
