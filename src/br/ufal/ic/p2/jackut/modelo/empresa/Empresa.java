package br.ufal.ic.p2.jackut.modelo.empresa;

public class Empresa {

    private int idEmpresa;
    private int idDono;
    private String nomeEmpresa;
    private String enderecoEmpresa;


    public Empresa(int id, int idDono, String nome, String endereco){
        this.idEmpresa = id;
        this.idDono = idDono;
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
    public int getIdDono(){
        return idDono;
    }
public String getTipoEmpresa(){
        return "empresa";
}
    public String getTipoCozinha(){
        return "";
    }
}
