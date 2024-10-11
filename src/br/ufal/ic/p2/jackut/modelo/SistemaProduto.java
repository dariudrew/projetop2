package br.ufal.ic.p2.jackut.modelo;

import br.ufal.ic.p2.jackut.modelo.empresa.produto.Produto;
import br.ufal.ic.p2.jackut.modelo.exception.*;

import java.util.Locale;

public class SistemaProduto {
    private SistemaDados dados;
    private SistemaUsuario sistemaUsuario;

    public SistemaProduto(SistemaDados dados){
        this.dados = dados;
        this.sistemaUsuario = new SistemaUsuario(dados);
    }
    public int criarProduto(int idEmpresa, String nomeProduto, float valorProduto, String categoriaProduto)
            throws EmpresaNaoCadastradaException, NomeInvalidoException, ProdutoValorInvalidoExcepion, ProdutoCategoriaInvalidaException, ProdutoJaExisteNaEmpresaException {

        validaDadosProduto(idEmpresa, nomeProduto, valorProduto, categoriaProduto);
        Produto produto = new Produto(dados.contadorIdProduto, nomeProduto, valorProduto, categoriaProduto,idEmpresa);
        dados.produtosPorID.put(dados.contadorIdProduto, produto);
        dados.contadorIdProduto++;
        return produto.getIdProduto();
    }

    public void editarProduto(int idProduto, String nomeProduto, float valorProduto, String categoriaProduto)
            throws NomeInvalidoException, ProdutoValorInvalidoExcepion, ProdutoCategoriaInvalidaException, ProdutoNaoCadastradoException {

        if(sistemaUsuario.validaNome(nomeProduto)){
            throw new NomeInvalidoException();
        }
        else if(valorProduto < 0){
            throw new ProdutoValorInvalidoExcepion();
        }
        else if(sistemaUsuario.validaNome(categoriaProduto)){
            throw new ProdutoCategoriaInvalidaException();
        }
        else if(!dados.produtosPorID.containsKey(idProduto)){
            throw new ProdutoNaoCadastradoException();
        }

        Produto produto = dados.produtosPorID.get(idProduto);

        produto.setNomeProduto(nomeProduto);
        produto.setValorProduto(valorProduto);
        produto.setCategoriaProduto(categoriaProduto);

    }
    public String getProduto(String  nomeProduto, int idEmpresa, String atributo)
            throws EmpresaNaoCadastradaException, NomeInvalidoException, ProdutoAtributoNaoExisteException, ProdutoNaoEncontradoException {
        if(sistemaUsuario.validaNome(nomeProduto)){
            throw new NomeInvalidoException();
        }
        else if(!dados.empresasPorID.containsKey(idEmpresa)){
            throw new EmpresaNaoCadastradaException();
        }
        Produto produto = null;
        for(int i = 1; i <= dados.produtosPorID.size(); i++){
            Produto p = dados.produtosPorID.get(i);
            if(p.getNomeProduto().matches(nomeProduto) && p.getIdEmpresa() == idEmpresa){
                produto = p;
                break;



            }
            if(i == dados.produtosPorID.size()){
                throw new ProdutoNaoEncontradoException();

            }
        }
        String str = "";

        if(atributo.equals("valor")) {
            float valor = produto.getValorProduto();
            str = String.format(Locale.US, "%.2f", valor);
        }
        else if(atributo.equals("categoria")){
            str = produto.getCategoria();
        }
        else if(atributo.equals("empresa")){
            str = dados.empresasPorID.get(idEmpresa).getNomeEmpresa();
        }
        else{
            str = "invalido";
        }

        if(str.matches("invalido")){
            throw new ProdutoAtributoNaoExisteException();
        }

        return str;
    }

    public String listarProdutos(int idEmpresa) throws EmpresaNaoEncontradaException {
        if(!dados.empresasPorID.containsKey(idEmpresa)){
            throw new EmpresaNaoEncontradaException();
        }

        String produtosPorEmpresa = "";


        if(!dados.produtosPorID.isEmpty()){
            int qntProdutos = dados.produtosPorID.size();
            for(int i = 1; i <= qntProdutos; i++){

                Produto produto = dados.produtosPorID.get(i);

                if(i == 1){
                    produtosPorEmpresa = produtosPorEmpresa.concat("{[");
                }
                if(produto.getIdEmpresa() == idEmpresa ){

                    if(produtosPorEmpresa.matches(".*[a-zA-Z0-9]$")){       //veifica se a string terminar com letras, para saber quando add virgula e espaçamento entre as produtos.
                        produtosPorEmpresa = produtosPorEmpresa.concat(", ");
                    }
                    produtosPorEmpresa = produtosPorEmpresa.concat(produto.getNomeProduto());
                }
                if(i == qntProdutos){
                    produtosPorEmpresa = produtosPorEmpresa.concat("]}");
                }
            }
        }else{
            produtosPorEmpresa = produtosPorEmpresa.concat("{[]}");
        }


        return produtosPorEmpresa; //retonar a lista de produtos
    }
    public void validaDadosProduto(int idEmpresa, String nomeProduto, float valorProduto, String categoriaProduto)
            throws EmpresaNaoCadastradaException, NomeInvalidoException, ProdutoValorInvalidoExcepion, ProdutoCategoriaInvalidaException, ProdutoJaExisteNaEmpresaException {

        if(!dados.empresasPorID.containsKey(idEmpresa)){
            throw new EmpresaNaoCadastradaException();
        }
        if(sistemaUsuario.validaNome(nomeProduto)){
            throw new NomeInvalidoException();
        }
        if(sistemaUsuario.validaNome(categoriaProduto)){
            throw new ProdutoCategoriaInvalidaException();
        }
        if(valorProduto <= 0){
            throw new ProdutoValorInvalidoExcepion();
        }

        if(!dados.produtosPorID.isEmpty()){
            for(Produto produto: dados.produtosPorID.values()){
                if(produto.getNomeProduto().matches(nomeProduto) && produto.getIdEmpresa() == idEmpresa){
                    if(nomeProduto.matches("Refrigerante"))                    {
                        throw new ProdutoJaExisteNaEmpresaException();
                    }
                }
            }
        }
    }
}
