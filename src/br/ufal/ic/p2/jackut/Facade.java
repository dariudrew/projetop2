package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.modelo.Sistema;
import br.ufal.ic.p2.jackut.modelo.exception.*;


public class Facade {
    Sistema sistema = new Sistema();
    //SistemaEmpresa sistemaEmpresa = new SistemaEmpresa();

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
    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha)
            throws EmpresaEnderecoInvalidoException, UsuarioNaoCriaEmpresaException, EmpresaNomeEnderecoEmUsoException,
            EmpresaNomeInvalidoException, EmpresaTipoCozinhaInvalidoException, UsuarioNaoCadastradoException, EmpresaNomeExisteException, TipoEmpresaInvalidoException {
        //return sistemaEmpresa.criarEmpresa(tipoEmpresa, dono, nome, endereco, tipoCozinha);
        return sistema.criarEmpresa(tipoEmpresa, dono, nome, endereco, tipoCozinha);
    }
    public String getEmpresasDoUsuario(int idDono) throws UsuarioNaoCriaEmpresaException {
       return sistema.getEmpresasDoUsuario(idDono);
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws UsuarioNaoCadastradoException, NomeInvalidoException, UsuarioNaoCriaEmpresaException, IndiceInvalidoException, NaoExisteEmpresaException, IndiceMaiorException {
        return sistema.getIdEmpresa(idDono, nome, indice);
    }

    public String getAtributoEmpresa(int empresa, String atributo) throws EmpresaNaoCadastradaException, AtributoInvalidoException {
        return sistema.getAtributoEmpresa(empresa, atributo);
    }
    public int criarProduto(int idEmpresa, String nomeProduto, float valorProduto, String categoriaProduto) throws EmpresaNaoCadastradaException, NomeInvalidoException, ProdutoValorInvalidoExcepion, ProdutoCategoriaInvalidaException, ProdutoJaExisteNaEmpresaException {
        return sistema.criarProduto(idEmpresa, nomeProduto, valorProduto, categoriaProduto);
    }
    public void editarProduto(int idProduto, String nomeProduto, float valorProduto, String categoriaProduto) throws ProdutoNaoCadastradoException, NomeInvalidoException, ProdutoValorInvalidoExcepion, ProdutoCategoriaInvalidaException {
        sistema.editarProduto(idProduto, nomeProduto,valorProduto, categoriaProduto);
    }
    public String getProduto(String  nomeProduto, int idEmpresa, String atributo) throws EmpresaNaoCadastradaException, ProdutoAtributoNaoExisteException, NomeInvalidoException, ProdutoNaoEncontradoException {
        return sistema.getProduto(nomeProduto, idEmpresa, atributo);
    }
    public String listarProdutos(int idEmpresa) throws EmpresaNaoEncontradaException{
        return sistema.listarProdutos(idEmpresa);
    }


}
