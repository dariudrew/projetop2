package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.modelo.Sistema;
import br.ufal.ic.p2.jackut.modelo.exception.*;


public class Facade {
    Sistema sistema = new Sistema();

    public void zerarSistema()
    {
        return;
    }

    public String getAtributoUsuario(int id, String nome) throws UsuarioNaoCadastradoException {
        return sistema.getAtributoUsuario(id, nome);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco)
            throws NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidaException, EmailJaExisteException {
        sistema.criarUsuario(nome, email, senha, endereco);

    }
    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidaException, EmailJaExisteException {
        sistema.criarUsuario(nome, email, senha, endereco, cpf);

    }
    public int login(String email, String senha)
    {
        return sistema.login(email, senha);
    }

}
