package model;

/**Classe para objetos do tipo Carro, onde serão contidos, valores e métodos primarios(getter , setter , equals e toString).
* @author Manoel Pimentel
* @version 1.05
* @since Release 02 da aplicação
*/
public class Carro {
    
    private String id;
    private String equipe;
    private String numero;
    private Piloto piloto;  
    
    public Carro(String id, String equipe, String numero) {
        this.id = id;
        this.equipe = equipe;
        this.numero = numero;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Piloto getPiloto() {
        return piloto;
    }

    public void setPiloto(Piloto piloto) {
        this.piloto = piloto;
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
        final Carro comparar = (Carro) obj;
        return this.id.equals(comparar.getId());
    }
 
    @Override
    public String toString() {
        String mandar =  id + ";" + equipe + ";" + numero;
        if(piloto == null)
            return mandar;
        return mandar + ";" + piloto.getNome();
    }
}
