package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.modelo.Sistema;
import br.ufal.ic.p2.jackut.modelo.empresa.SistemaEmpresa;
import br.ufal.ic.p2.jackut.modelo.exception.*;


public class Facade {
    Sistema sistema = new Sistema();
    SistemaEmpresa sistemaEmpresa = new SistemaEmpresa();

    public void zerarSistema()
    {
        sistema.zerarSistema();
    }


    public void encerrarSistema(){
         sistema.encerrarSistema();
    }

    public String getAtributoUsuario(int id, String nome) throws UsuarioNaoCadastradoException {
        return sistema.getAtributoUsuario(id, nome);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco)
            throws NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidaException, EmailJaExisteException {
        sistema.criarUsuario(nome, email, senha, endereco);

    }
    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidaException, EmailJaExisteException, CPFInvalidoException {
        sistema.criarUsuario(nome, email, senha, endereco, cpf);

    }
    public int login(String email, String senha) throws LoginSenhaException {
        return sistema.login(email, senha);
    }
    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha){
        return sistemaEmpresa.criarEmpresa(tipoEmpresa, dono, nome, endereco, tipoCozinha);
    }
    public String getEmpresasDoUsuario(int idDono){
       return sistemaEmpresa.getEmpresasDoUsuario(idDono);
    }

    public int getIdEmpresa(int idDono, String nome, int indice){
        return sistemaEmpresa.getIdEmpresa(idDono, nome, indice);
    }

    public String getAtributoEmpresa(int empresa, String atributo){
        return sistemaEmpresa.getAtributoEmpresa(empresa, atributo);
    }


}
