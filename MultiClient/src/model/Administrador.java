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
public class Administrador {
    private String nome;
    private String senha;
    
    public Administrador(String nome, String senha) {
        this.nome  = nome;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
        final Administrador comparar = (Administrador) obj;
   
        return (this.nome.equals(comparar.getNome()) && this.nome.equals(comparar.getSenha()));
    }
    
    @Override
    public String toString() {
       return nome + ";" + senha;
    }
    
    
}
