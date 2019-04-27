/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import model.Administrador;
import model.Carro;
import model.Piloto;
import multiclient.Servidor;

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
    
    /**Método para cadastrar carro.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param novoCarro Carro - carro a ser adicionado
    */
    public synchronized void cadastrarCarro(Carro novoCarro){
        carros.add(novoCarro); //salva o novo carro em sua respectiva lista
    }
    
    /**Método para verificar se ja existe um carro cadastrado com aquele id
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param id String - id do carro
    * @return boolean - retornar true ja achou e false caso não
    */
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
    
    /**Método para cadastrar piloto.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param novoPiloto Piloto - nome do piloto
    */
    public synchronized void cadastrarPiloto(Piloto novoPiloto){
        pilotos.add(novoPiloto); //salva um novo piloto em sua respectiva lista
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
    
    /**Método para cadastrar adm.
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param adm Administrador - adm a ser salvo
    */
    public synchronized void cadastrarAdministrador(Administrador adm){
        adms.add(adm); //salva um novo Adm em sua respectiva lista
        adms.forEach(u -> System.out.println(u.getNome()));
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
            if(adm.getNome().equals(nome)&& adm.getSenha().equals(senha))
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
    public synchronized void removerPiloto(String nome){
        pilotos.removeIf( u -> u.getNome().equals(nome));
    }
    
    /**Método para remover um carro cadastrado com aquele id
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param id String - id do carro
    */
    public synchronized void removerCarro(String id){
        carros.removeIf( u -> u.getId().equals(id));
    }
    
    /**Método para remover um carro cadastrado com aquele id
    * @author Samuel Vitorio Lima e Gabriel Sá Barreto
    * @param servidor Servidor - poder chamar o metodo para enviar os dados solicitados
    */
    public void atualizarCliente(Servidor servidor) throws IOException{
        for(Carro carro : carros){
            servidor.distribuiMensagem("11;"+carro.toString());
        }
        for(Piloto piloto : pilotos){
            servidor.distribuiMensagem("12;"+piloto.toString());
        }
        for(Administrador adm : adms){
            servidor.distribuiMensagem("13;"+adm.toString());
        }
    }
}
