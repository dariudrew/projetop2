package br.ufal.ic.p2.jackut.modelo.empresa;

public class Empresa {

    private int idEmpresa;
    private String nomeEmpresa;
    private String enderecoEmpresa;


    public Empresa(int id, String nome, String endereco){
        this.idEmpresa = id;
        this.nomeEmpresa = nome;
        this.enderecoEmpresa = endereco;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public String getEnderecoEmpresa() {
        return enderecoEmpresa;
    }

}
