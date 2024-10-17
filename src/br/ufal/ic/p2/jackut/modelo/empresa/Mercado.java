package br.ufal.ic.p2.jackut.modelo.empresa;

public class Mercado extends Empresa{
   private String abre;
   private String fecha;
   private String tipoMercado;

   public Mercado(int idEmpresa, int idDono, String nomeEmpresa, String enderecoEmpresa, String abre, String fecha, String tipoMercado){
        super(idEmpresa, idDono, nomeEmpresa, enderecoEmpresa);
        this.abre = abre;
        this.fecha = fecha;
        this.tipoMercado = tipoMercado;
    }

    public String getAbre() {
        return abre;
    }
    public String getFecha(){
        return fecha;
    }
    public String getTipoMercado(){
        return tipoMercado;
    }
    public void setAbre(String abre){
       this.abre = abre;
    }
    public void setFecha(String fecha){
        this.fecha = fecha;
    }
    public String getTipoEmpresa(){
       return "mercado";
    }

}
