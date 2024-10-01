package br.ufal.ic.p2.jackut.modelo.exception;

public class ProdutoAtributoNaoExisteException extends Exception{
    public ProdutoAtributoNaoExisteException(){
        super("Atributo nao existe");
    }
}
