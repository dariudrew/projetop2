package br.ufal.ic.p2.jackut.modelo.exception;

public class ProdutoNaoEncontradoException extends Exception{
    public  ProdutoNaoEncontradoException(){
        super("Produto nao encontrado");
    }
}
