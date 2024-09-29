package br.ufal.ic.p2.jackut.modelo.empresa;

public class Restaurante extends Empresa{
     String tipoCozinha;
    public Restaurante(int idEmpresa, int idDono, String nomeEmpresa, String enderecoEmpresa, String tipoCozinha){
        super(idEmpresa, idDono, nomeEmpresa, enderecoEmpresa);
        this.tipoCozinha = tipoCozinha;
    }

    public String getTipoCozinha(){
        return tipoCozinha;
    }
    public String getTipoEmpresa(){
        return "restaurante";
    }
}
