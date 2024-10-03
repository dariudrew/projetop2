package br.ufal.ic.p2.jackut.modelo.exception;

public class NaoExistePedidoAbertoException extends Exception{
    public NaoExistePedidoAbertoException(){
        super("Nao existe pedido em aberto");
    }
}
