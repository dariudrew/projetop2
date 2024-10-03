package br.ufal.ic.p2.jackut.modelo.exception;

public class DonoNaoFazPedidoException extends Exception{
    public DonoNaoFazPedidoException(){
        super("Dono de empresa nao pode fazer um pedido");
    }
}
