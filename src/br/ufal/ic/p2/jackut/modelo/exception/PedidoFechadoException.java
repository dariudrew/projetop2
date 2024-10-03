package br.ufal.ic.p2.jackut.modelo.exception;

public class PedidoFechadoException extends Exception{
    public PedidoFechadoException(){
        super("Nao e possivel adicionar produtos a um pedido fechado");
    }

}
