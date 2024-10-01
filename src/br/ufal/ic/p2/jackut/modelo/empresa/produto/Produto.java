package br.ufal.ic.p2.jackut.modelo.empresa.produto;

import br.ufal.ic.p2.jackut.modelo.empresa.Empresa;

public class Produto {
    int idProduto;
    int idEmpresa;
    String nomeProduto;
    float valorProduto;
    String categoriaProduto;

    public Produto(int idProduto,  String nomeProduto, float valorProduto, String categoriaProduto,int idEmpresa){
        this.idProduto = idProduto;
        this.idEmpresa = idEmpresa;
        this.nomeProduto = nomeProduto;
        this.valorProduto = valorProduto;
        this.categoriaProduto = categoriaProduto;

    }

    public int getIdProduto() {
        return idProduto;
    }

    public String getNomeProduto(){
        return nomeProduto;
    }

    public float getValorProduto() {
        return valorProduto;
    }
    public String getCategoria(){
        return categoriaProduto;
    }
    public int getIdEmpresa(){
        return idEmpresa;
    }
    public void setNomeProduto(String nomeProduto){
        this.nomeProduto = nomeProduto;
    }

    public void setValorProduto(float valorProduto) {
        this.valorProduto = valorProduto;
    }

    public void setCategoriaProduto(String categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }
}
