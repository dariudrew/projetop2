package br.ufal.ic.p2.jackut.modelo.usuario;

public class Cliente extends Usuario {


    public Cliente (int id, String nome, String email, String senha, String endereco){
        super(id, nome, email, senha, endereco);

    }

    public String getTipoObjeto(){
        return "cliente";
    }

}
