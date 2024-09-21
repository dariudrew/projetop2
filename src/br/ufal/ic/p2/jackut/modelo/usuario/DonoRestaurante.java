package br.ufal.ic.p2.jackut.modelo.usuario;

public class DonoRestaurante extends Usuario {

    String cpf;
    public DonoRestaurante(int id, String nome, String email, String senha, String endereco, String cpf){
        super(id, nome, email, senha, endereco);
         this.cpf = cpf;
    }
    public String getCpf(){ return cpf;}
    public String getTipoObjeto(){
        return "DonoRestaurante";
    }

}
