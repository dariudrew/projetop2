package br.ufal.ic.p2.jackut.modelo;

public class Entrega {
    int idEntrega;
    String nomeCliente;
    String nomeEmpresa;
    int idPedido;
    int idEntregador;
    String destino;
    String produtos;

    public Entrega(int idEntrega, String nomeCliente, String nomeEmpresa, int idPedido, int idEntregador, String destino, String produtos){
        this.idEntrega = idEntrega;
        this.nomeCliente = nomeCliente;
        this.nomeEmpresa = nomeEmpresa;
        this.idPedido = idPedido;
        this.idEntregador = idEntregador;
        this.destino = destino;
        this.produtos = produtos;
    }
    public int getIdEntrega(){
        return idEntrega;
    }
    public String getNomeCliente(){
        return nomeCliente;
    }
    public String getNomeEmpresa(){
        return nomeEmpresa;
    }
    public int getIdPedido(){
        return idPedido;
    }
    public int getIdEntregador(){
        return idEntregador;
    }
    public String getProdutos(){
        return produtos;
    }

}
