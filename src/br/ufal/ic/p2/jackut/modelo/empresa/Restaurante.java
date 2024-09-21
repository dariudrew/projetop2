package br.ufal.ic.p2.jackut.modelo.empresa;

public class Restaurante extends Empresa{
     String tipoCozinha;
    public Restaurante(int idEmpresa, String nomeEmpresa, String enderecoEmpresa, String tipoCozinha){
        super(idEmpresa, nomeEmpresa, enderecoEmpresa);
        this.tipoCozinha = tipoCozinha;
    }
}
