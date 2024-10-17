package br.ufal.ic.p2.jackut.modelo;

import br.ufal.ic.p2.jackut.modelo.exception.*;
import br.ufal.ic.p2.jackut.modelo.pedido.Pedido;

public class SistemaEntrega {
    private SistemaDados dados;
    private SistemaUsuario sistemaUsuario;
    private SistemaEmpresa sistemaEmpresa;
    private SistemaProduto sistemaProduto;
    private SistemaPedido sistemaPedido;

    public SistemaEntrega(SistemaDados dados){
        this.dados = dados;
        this.sistemaUsuario = new SistemaUsuario(dados);
        this.sistemaEmpresa = new SistemaEmpresa(dados);
        this.sistemaProduto = new SistemaProduto(dados);
        this.sistemaPedido = new SistemaPedido(dados);
    }
    public int criarEntrega(int pedido, int idEntregador, String destino) throws PedidoNaoEncontradoException, UsuarioNaoCadastradoException, UsuarioNaoEntregadorException, EnderecoInvalidoException {
        if(dados.pedidosPorID.isEmpty() || !dados.pedidosPorID.containsKey(pedido)){
            throw new PedidoNaoEncontradoException();
        }
        else if(!dados.usuariosPorID.containsKey(idEntregador)){
            throw new UsuarioNaoCadastradoException();
        }
        else if(!dados.usuariosPorID.get(idEntregador).getTipoObjeto().matches("entregador")){
            throw new UsuarioNaoEntregadorException();
        }
        if(sistemaUsuario.validaNome(destino)){
            throw new EnderecoInvalidoException();
        }
        //entregador ta livre?
        return 0; // id do produto a ser entregue
    }

    public void liberarPedido(int numero) throws PedidoNaoEncontradoException {
        if(dados.pedidosPorID.isEmpty() || !dados.pedidosPorID.containsKey(numero)){
            throw new PedidoNaoEncontradoException();
        }
        Pedido pedido = dados.pedidosPorID.get(numero);
        if(pedido.getEstadoPedido().matches("preparando")){
            pedido.setEstadoPedido("pronto");
        }


    }

    public int obterPedido(int idEntregador){
        return 0; //retorna o id de um pedido pronto
    }

    public String getEntrega(int idEntrega, String atributo){
        return ""; // Retorna uma string com o valor do atributo.
    }

    public void entregar(int idEntrega){

    }

}
