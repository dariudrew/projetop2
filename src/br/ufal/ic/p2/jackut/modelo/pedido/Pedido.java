package br.ufal.ic.p2.jackut.modelo.pedido;

public class Pedido {
    int numeroPedido;
    int idCliente;
    int idEmpresa;
    String nomeCliente;
    String nomeEmpresa;
    String estadoPedido;
    String produtos = "{[]}";
    float valorPedido = 0.0f;

    public Pedido(int numeroPedido, String nomeCliente, String nomeEmpresa, String estadoPedido, int idCliente, int idEmpresa){
        this.numeroPedido = numeroPedido;
        this.nomeCliente = nomeCliente;
        this.nomeEmpresa = nomeEmpresa;
        this.estadoPedido = estadoPedido;
        this.idCliente = idCliente;
        this.idEmpresa = idEmpresa;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public void setProdutos(String produtos) {
        this.produtos = produtos;
    }

    public void setValorPedido(float valorPedido) {
        this.valorPedido += valorPedido;
    }

    public int getNumeroPedido(){
        return numeroPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public int getIdEmpresa(){
        return idEmpresa;
    }

    public String getNomeCliente(){
        return nomeCliente;
    }

    public String getNomeEmpresa(){
        return nomeEmpresa;
    }

    public String getEstadoPedido(){
        return estadoPedido;
    }

    public String getProdutos(){
        return produtos;
    }

    public float getValorPedido(){
        return valorPedido;
    }
}
