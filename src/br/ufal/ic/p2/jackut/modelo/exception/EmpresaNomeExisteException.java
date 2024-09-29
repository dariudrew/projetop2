package br.ufal.ic.p2.jackut.modelo.exception;

public class EmpresaNomeExisteException extends Exception{
    public EmpresaNomeExisteException(){
        super("Empresa com esse nome ja existe");
    }
}
