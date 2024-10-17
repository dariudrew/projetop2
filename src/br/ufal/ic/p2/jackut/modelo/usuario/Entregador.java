package br.ufal.ic.p2.jackut.modelo.usuario;

public class Entregador extends Usuario{
    private String veiculo;
    private String placa;

    public Entregador(int id, String nome, String email, String senha, String endereco, String veiculo, String placa){
        super(id, nome, email, senha, endereco);
        this.veiculo = veiculo;
        this.placa = placa;
    }

    @Override
    public String getVeiculo() {
        return veiculo;
    }
    public String getPlaca(){
        return placa;
    }
    public String getTipoObjeto(){
        return "entregador";
    }
}
