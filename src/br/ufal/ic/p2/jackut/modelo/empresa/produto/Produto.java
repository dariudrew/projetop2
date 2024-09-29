package br.ufal.ic.p2.jackut.modelo.empresa.produto;

public class Produto {
    int idProduto;
    String nomeProduto;
    float valorProduto;
    String categoriaProduto;

    public Produto(int idProduto, String nomeProduto, float valorProduto, String categoriaProduto){
        this.idProduto = idProduto;
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
}
