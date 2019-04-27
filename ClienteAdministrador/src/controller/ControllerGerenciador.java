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
        //por ser recurso compartilhado o recurso usamos o Collection.synchronizedList
        carros = Collections.synchronizedList(new ArrayList<Carro>());
        pilotos = Collections.synchronizedList(new ArrayList<Piloto>());
        adms = Collections.synchronizedList(new ArrayList<Administrador>());
    }
    
    /**Método para cadastrar carro.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param tag String - tag do chip do carro
    * @param equipe String - equipe do carro
    * @param numero String - numero do carro
    * @return Carro - a instância do carro realizada
    */
    public Carro cadastrarCarro(String tag , String equipe , String numero){ //cadastrando pessoalmente
        Carro adicionar = new Carro(tag , equipe , numero);
        return adicionar;
    }
    
    /**Método para salvar carro.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param novo Carro - carro recebido pelo server
    */
    public void salvarCarro(Carro novo){ //receber pacote
        synchronized(carros){ //sincroniza o acesso à lista de carro
            carros.add(novo);
        }
    }
    
    /**Método para verificar se ja existe um carro cadastrado com aquele id
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param id String - id do carro
    * @return boolean - retornar true ja achou e false caso não
    */
    public boolean existeCarro(String id){
        if(carros.isEmpty()) //se estiver vazia ja manda false
            return false;
        for (Iterator<Carro> it = carros.iterator(); it.hasNext();) {
            Carro carro = it.next(); //pega cada celula da lista
            if(carro.getId().equals(id)) //procura pelo id
                return true;
        }
        return false;
    }
    
    /**Método para cadastrar piloto.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param nome String - nome do piloto
    * @return Piloto - a instância do piloto realizada
    */
    public Piloto cadastrarPiloto(String nome){ //cadastrando pessoalmente
        Piloto adicionar = new Piloto(nome);
        return adicionar;
    }
    
    /**Método para salvar piloto.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param novo Piloto - piloto recebido pelo server
    */
    public void salvarPiloto(Piloto novo){ //receber pacote
        synchronized(pilotos){ //sincroniza o acesso à lista de piloto
            pilotos.add(novo);
        }
    }
    
    /**Método para verificar se ja existe um piloto cadastrado com aquele nome
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param nome String - nome do piloto
    * @return boolean - retornar true ja achou e false caso não
    */
    public boolean existePiloto(String nome){
        for (Iterator<Piloto> it = pilotos.iterator(); it.hasNext();) {
            Piloto piloto = it.next();
            if(piloto.getNome().equals(nome))
                return true;
        }
        return false;
    }
    
    /**Método para retornar a lista de pilotos cadastrados.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @return List - retorna a lista de pilotos.
    */
    public List<Piloto> getPilotos(){
        return pilotos;
    }
    
    /**Método para retornar a lista de carros cadastrados.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @return List - retorna a lista de carros.
    */
    public List<Carro> getCarros(){
        return carros;
    }
     
    /**Método para cadastrar piloto.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param nome String - nome do adm
    * @param senha String - senha do adm
    * @return Administrador - a instância do piloto realizada
    */
    public Administrador cadastrarAdministrador(String nome , String senha){ //cadastrando pessoalmente
        Administrador adicionar = new Administrador(nome , senha);
        return adicionar;
    }
    
    /**Método para salvar adm.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param novo Administrador - adm recebido pelo server
    */
    public void salvarAdm(Administrador novo){ //receber pacote
        synchronized(adms){
            adms.add(novo);
        }
    }
    
    /**Método para verificar se ja existe um piloto cadastrado com aquele nome
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param nome String - nome do adm
    * @param senha String - senha do adm
    * @return boolean - retornar true ja achou e false caso não
    */
    public boolean existeAdm(String nome, String senha){
        for (Iterator<Administrador> it = adms.iterator(); it.hasNext();) {
            Administrador adm = it.next();
            if(adm.getNome().equals(nome) && adm.getSenha().equals(senha))
                return true;
        }
        return false;
    }
    
    /**Método para enviar um piloto cadastrado com aquele nome
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param nome String - nome do piloto
    * @return Piloto - retornar o que foi achado na pesquisa da lista de pilotos
    */
    public Piloto piloto(String nome){
        for (Iterator<Piloto> it = pilotos.iterator(); it.hasNext();) {
            Piloto piloto = it.next();
            if(piloto.getNome().equals(nome))
                return piloto;
        }
        return null;
    }
    
    /**Método para enviar um carro cadastrado com aquele id
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param idCarro String - id do carro
    * @return Carro - retornar o que foi achado na pesquisa da lista de carros
    */
    public Carro carro(String idCarro){
        for (Iterator<Carro> it = carros.iterator(); it.hasNext();) {
            Carro carro = it.next();
            if(carro.getId().equals(idCarro))
                return carro;
        }
        return null;
    }
    
    /**Método para remover um piloto cadastrado com aquele nome
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param nome String - nome do piloto
    */
    public void removerPiloto(String nome){
        synchronized(pilotos){
            pilotos.removeIf( u -> u.getNome().equals(nome)); //remoção via lambda com o nome do piloto
        }
    }
    
    /**Método para remover um carro cadastrado com aquele id
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param id String - id do carro
    */
    public void removerCarro(String id){
        synchronized(carros){
            carros.removeIf( u -> u.getId().equals(id));//remoção via lambda com o nome do carro
        }
    }
    
    /**Método private para auxiliar na conversao de String para int
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param valor List - receber a String a ser convertida.
    * @param padrao int - caso aconteça uma exceção um valor padrão que possa ser inserido.
    * @return int - retornar o inteiro.
    */
    public int strToInt(String valor, int padrao) {
        try {
            return Integer.valueOf(valor); // Para retornar um Integer, use Integer.parseInt
        } 
        catch (NumberFormatException e) {  // Se houver erro na conversão, retorna o valor padrão
            return padrao;
        }
    }
    
    /**Método que limpa a tabela nas interfaces
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param table javax.swing.JTable - limpar a tabela passada
    */
    public void limpaTabela(javax.swing.JTable table){
        DefaultTableModel tblRemove = (DefaultTableModel) table.getModel();
        while(tblRemove.getRowCount() != 0){ //remover todas celulas da tabela
            tblRemove.removeRow(0);
        }            
    }
}
