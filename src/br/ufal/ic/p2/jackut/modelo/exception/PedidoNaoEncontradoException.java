package br.ufal.ic.p2.jackut.modelo.exception;

public class PedidoNaoEncontradoException extends Exception{
    public PedidoNaoEncontradoException(){
        super("Pedido nao encontrado");
    }
}
