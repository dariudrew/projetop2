package br.ufal.ic.p2.jackut.modelo.exception;

public class NaoPossivelRemoverProdutoException extends Exception {
    public NaoPossivelRemoverProdutoException(){
        super("Nao e possivel remover produtos de um pedido fechado");
    }

}
