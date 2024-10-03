package br.ufal.ic.p2.jackut.modelo.exception;

public class ProdutoNaoPerteceEmpresaException extends Exception{
    public ProdutoNaoPerteceEmpresaException(){
        super("O produto nao pertence a essa empresa");
    }
}
