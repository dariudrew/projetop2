package br.ufal.ic.p2.jackut.modelo;

import br.ufal.ic.p2.jackut.modelo.empresa.Empresa;
import br.ufal.ic.p2.jackut.modelo.empresa.produto.Produto;
import br.ufal.ic.p2.jackut.modelo.pedido.Pedido;
import br.ufal.ic.p2.jackut.modelo.usuario.Usuario;

import java.util.HashMap;
import java.util.Map;

public class SistemaDados {


    int contadorID = 1;
    int contadorIdEmpresa = 1;
    int contadorIdProduto = 1;
    int contadorIdPedido = 1;
    Map<Integer, Empresa> empresasPorID = new HashMap<>();
    Map<Integer, Usuario> usuariosPorID = new HashMap<>();
    Map<Integer, Produto> produtosPorID = new HashMap<>();
    Map<Integer, Pedido> pedidosPorID = new HashMap<>();
   // XML xml = new XML();
}
