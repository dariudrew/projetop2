package br.ufal.ic.p2.jackut.modelo.exception;

public class NaoPermitidoPedidosAbertoMesmaEmpresaException extends Exception{
    public NaoPermitidoPedidosAbertoMesmaEmpresaException(){
        super("Nao e permitido ter dois pedidos em aberto para a mesma empresa");
    }

}
