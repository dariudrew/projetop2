package br.ufal.ic.p2.jackut.modelo.empresa;

public class Farmacia extends Empresa{
    private boolean aberto24Horas;
    private int numeroFuncionarios;

    public Farmacia(int idEmpresa, int idDono, String nomeEmpresa, String enderecoEmpresa, boolean aberto24Horas, int numeroFuncionarios){
        super(idEmpresa, idDono, nomeEmpresa, enderecoEmpresa);
        this.aberto24Horas = aberto24Horas;
        this.numeroFuncionarios = numeroFuncionarios;
    }
    public boolean getAberto24Horas(){
        return aberto24Horas;
    }
    public int getNumeroFuncionarios(){
        return numeroFuncionarios;
    }
    public String getTipoEmpresa(){
        return "farmacia";
    }
}
