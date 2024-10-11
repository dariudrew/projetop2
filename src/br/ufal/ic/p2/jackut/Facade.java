package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.modelo.*;
import br.ufal.ic.p2.jackut.modelo.exception.*;


public class Facade {
    private SistemaDados dados;
    private SistemaUsuario sistemaUsuario;
    private SistemaEmpresa sistemaEmpresa;
    private SistemaProduto sistemaProduto;
    private SistemaPedido sistemaPedido;

    public Facade(){
        dados = new SistemaDados();
        sistemaUsuario = new SistemaUsuario(dados);
        sistemaEmpresa = new SistemaEmpresa(dados);
        sistemaProduto = new SistemaProduto(dados);
        sistemaPedido  = new SistemaPedido(dados);

    }


    public void zerarSistema(){
        sistemaUsuario.zerarSistema();
    }

    public void encerrarSistema(){
         sistemaUsuario.encerrarSistema();
    }

    public String getAtributoUsuario(int id, String nome) throws UsuarioNaoCadastradoException {
        return sistemaUsuario.getAtributoUsuario(id, nome);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco)
            throws NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidaException, EmailJaExisteException {
        sistemaUsuario.criarUsuario(nome, email, senha, endereco);

    }
    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidaException, EmailJaExisteException, CPFInvalidoException {
        sistemaUsuario.criarUsuario(nome, email, senha, endereco, cpf);

    }
    public int login(String email, String senha) throws LoginSenhaException {
        return sistemaUsuario.login(email, senha);
    }
    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha)
            throws EmpresaEnderecoInvalidoException, UsuarioNaoCriaEmpresaException, EmpresaNomeEnderecoEmUsoException,
            EmpresaNomeInvalidoException, EmpresaTipoCozinhaInvalidoException, UsuarioNaoCadastradoException, EmpresaNomeExisteException, TipoEmpresaInvalidoException {
        //return sistemaEmpresa.criarEmpresa(tipoEmpresa, dono, nome, endereco, tipoCozinha);
        return sistemaEmpresa.criarEmpresa(tipoEmpresa, dono, nome, endereco, tipoCozinha);
    }
    public String getEmpresasDoUsuario(int idDono) throws UsuarioNaoCriaEmpresaException {
       return sistemaEmpresa.getEmpresasDoUsuario(idDono);
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws UsuarioNaoCadastradoException, NomeInvalidoException, UsuarioNaoCriaEmpresaException, IndiceInvalidoException, NaoExisteEmpresaException, IndiceMaiorException {
        return sistemaEmpresa.getIdEmpresa(idDono, nome, indice);
    }

    public String getAtributoEmpresa(int empresa, String atributo) throws EmpresaNaoCadastradaException, AtributoInvalidoException {
        return sistemaEmpresa.getAtributoEmpresa(empresa, atributo);
    }
    public int criarProduto(int idEmpresa, String nomeProduto, float valorProduto, String categoriaProduto) throws EmpresaNaoCadastradaException, NomeInvalidoException, ProdutoValorInvalidoExcepion, ProdutoCategoriaInvalidaException, ProdutoJaExisteNaEmpresaException {
        return sistemaProduto.criarProduto(idEmpresa, nomeProduto, valorProduto, categoriaProduto);
    }
    public void editarProduto(int idProduto, String nomeProduto, float valorProduto, String categoriaProduto) throws ProdutoNaoCadastradoException, NomeInvalidoException, ProdutoValorInvalidoExcepion, ProdutoCategoriaInvalidaException {
        sistemaProduto.editarProduto(idProduto, nomeProduto,valorProduto, categoriaProduto);
    }
    public String getProduto(String  nomeProduto, int idEmpresa, String atributo) throws EmpresaNaoCadastradaException, ProdutoAtributoNaoExisteException, NomeInvalidoException, ProdutoNaoEncontradoException {
        return sistemaProduto.getProduto(nomeProduto, idEmpresa, atributo);
    }
    public String listarProdutos(int idEmpresa) throws EmpresaNaoEncontradaException{
        return sistemaProduto.listarProdutos(idEmpresa);
    }

    public int criarPedido(int idCliente, int idEmpresa) throws EmpresaNaoCadastradaException, DonoNaoFazPedidoException, UsuarioNaoCadastradoException, AtributoInvalidoException, NaoPermitidoPedidosAbertoMesmaEmpresaException {
        return sistemaPedido.criarPedido(idCliente, idEmpresa);
    }

    public int getNumeroPedido(int idCliente, int idEmpresa, int indice) throws PedidoNaoEncontradoException {

        return sistemaPedido.getNumeroPedido(idCliente, idEmpresa, indice);
    }

    public void adicionarProduto(int numeroPedido, int idProduto) throws NaoExistePedidoAbertoException, ProdutoNaoEncontradoException, ProdutoNaoPerteceEmpresaException, PedidoFechadoException {
        sistemaPedido.adicionarProduto(numeroPedido, idProduto);
    }

    public String getPedidos(int numeroPedido, String atributo) throws AtributoInvalidoException, ProdutoAtributoNaoExisteException, PedidoNaoEncontradoException {

        return sistemaPedido.getPedidos(numeroPedido, atributo);
    }

    public void fecharPedido(int numeroPedido) throws PedidoNaoEncontradoException {
        sistemaPedido.fecharPedido(numeroPedido);
    }

    public void removerProduto(int numeroPedido, String produto) throws ProdutoInvalidoException, PedidoNaoEncontradoException, NaoPossivelRemoverProdutoException, ProdutoNaoEncontradoException, EmpresaNaoCadastradaException, ProdutoAtributoNaoExisteException, NomeInvalidoException {
        sistemaPedido.removerProduto(numeroPedido, produto);
    }

}
