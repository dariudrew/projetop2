package br.ufal.ic.p2.jackut.modelo;

import br.ufal.ic.p2.jackut.modelo.empresa.Empresa;
import br.ufal.ic.p2.jackut.modelo.empresa.Restaurante;
import br.ufal.ic.p2.jackut.modelo.empresa.produto.Produto;
import br.ufal.ic.p2.jackut.modelo.exception.*;
import br.ufal.ic.p2.jackut.modelo.pedido.Pedido;
import br.ufal.ic.p2.jackut.modelo.usuario.Cliente;
import br.ufal.ic.p2.jackut.modelo.usuario.DonoRestaurante;
import br.ufal.ic.p2.jackut.modelo.usuario.Usuario;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Locale;

public class Sistema {

    int contadorID = 1;
    int contadorIdEmpresa = 1;
    int contadorIdProduto = 1;
    int contadorIdPedido = 1;
    Map<Integer, Empresa> empresasPorID = new HashMap<>();
    Map<Integer, Usuario> usuariosPorID = new HashMap<>();
    Map<Integer, Produto> produtosPorID = new HashMap<>();
    Map<Integer, Pedido> pedidosPorID = new HashMap<>();
    XML xml = new XML();



    public void zerarSistema(){

        while(contadorID > 0 || contadorIdEmpresa > 0){
            contadorID--;
            contadorIdEmpresa--;
            contadorIdProduto--;

            if(contadorID > 0){
                usuariosPorID.remove(contadorID);

            }
            if(contadorIdEmpresa > 0){
                empresasPorID.remove(contadorIdEmpresa);
            }
            if(contadorIdProduto > 0){
                produtosPorID.remove(contadorIdProduto);
            }

        }

        contadorID = 1;
        contadorIdEmpresa = 1;
        contadorIdProduto = 1;
    }

    public void criarUsuario(String nome, String email, String senha, String endereco)
            throws NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidaException, EmailJaExisteException {
        validaDados(nome, email, senha, endereco);
        Cliente cliente = new Cliente(contadorID, nome, email, senha, endereco);




       if(contadorID == 0){
           xml.criarXML();
       }
        xml.inserirUsuario(cliente);
        usuariosPorID.put(contadorID, cliente);
        contadorID+=1;
    }
    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidaException, EmailJaExisteException, CPFInvalidoException {
        validaCPF(cpf);
        validaDados(nome, email, senha, endereco);
        DonoRestaurante donoRestaurante = new DonoRestaurante(contadorID, nome, email, senha, endereco, cpf);
        //xml.inserirUsuario(donoRestaurante);

        usuariosPorID.put(contadorID, donoRestaurante);

        contadorID+=1;


    }

    public void validaDados(String nome, String email, String senha, String endereco)
            throws NomeInvalidoException, EmailInvalidoException, SenhaInvalidaException, EnderecoInvalidoException, EmailJaExisteException {
        if(validaNome(nome))
        {
            throw new NomeInvalidoException();
        }
        if(validaSenha(senha))
        {
            throw new SenhaInvalidaException();
        }
        if(validaEndereco(endereco)){
            throw new EnderecoInvalidoException();
        }
        if(validaEmail(email))
        {
            throw new EmailInvalidoException();
        }

        if(verificaUsuario("Email", email) >= 0)
        {
             throw new EmailJaExisteException();
        }

    }


    public void validaCPF(String cpf) throws CPFInvalidoException{
        if(cpf == null || cpf.isEmpty() || !cpf.matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")){
            throw new CPFInvalidoException();
        }
    }


    public boolean validaEmail(String email) {
        return email == null || email.isEmpty() || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }

    public boolean validaNome(String nome){
        if(nome == null || nome.isEmpty() || nome.isBlank()){
            return true;
        }
         return false;
    }

    public int verificaUsuario(String tipoMetodo, String atributo){
        try {
            String metodoNome = "get" + tipoMetodo;

            if(!usuariosPorID.isEmpty())
            {
                for (Usuario usuario : usuariosPorID.values()) {
                    Method metodo = usuario.getClass().getMethod(metodoNome);

                    if (metodo.invoke(usuario).equals(atributo)) {
                        return usuario.getId();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();//criar uma excessão??
            return -1;
        }
        return -1;
    }

    public boolean validaSenha(String senha) {
        return senha == null || senha.trim().isEmpty();
    }

    public boolean validaEndereco(String endereco){
        return endereco == null || endereco.trim().isEmpty();
    }





    public String getAtributoUsuario(int id, String atributo) throws UsuarioNaoCadastradoException{


        if(usuariosPorID.containsKey(id)){

            Usuario usuario = usuariosPorID.get(id);
            switch (atributo){
                case "nome":
                    return usuario.getNome();
                case "senha":
                    return usuario.getSenha();
                case "email":
                    return usuario.getEmail();
                case "endereco":
                    return usuario.getEndereco();
                case "cpf":
                    return  usuario.getCpf();
            }
        }else{
            throw new UsuarioNaoCadastradoException();
        }


        return "";
    }

    public int login (String email, String senha) throws LoginSenhaException {

        if(validaEmail(email) || validaSenha(senha)){
            throw new LoginSenhaException();
        }
        else{
             int id = verificaUsuario("Email", email);
            if( id >= 0)
            {
                Usuario usuario = usuariosPorID.get(id);


                if(usuario.getSenha().matches(senha)){
                    return id;
                }
                else{
                    throw new LoginSenhaException();
                }
            }
        }

        return 0;
    }

    public void encerrarSistema(){

    }


    //   EMPRESA EMPRESA EMPRESA EMPRESA EMPRESA

    public int criarEmpresa(String tipoEmpresa, int dono, String nomeEmpresa, String endereco, String tipoCozinha)
            throws UsuarioNaoCadastradoException, EmpresaNomeInvalidoException, EmpresaEnderecoInvalidoException,
            EmpresaTipoCozinhaInvalidoException, UsuarioNaoCriaEmpresaException, EmpresaNomeExisteException,
            EmpresaNomeEnderecoEmUsoException, TipoEmpresaInvalidoException {

        validaDadosEmpresa(dono, nomeEmpresa, endereco, tipoCozinha);
        int idUltimaEmpresa = contadorIdEmpresa;
        switch (tipoEmpresa){
            case "restaurante":
                Restaurante restaurante = new Restaurante(contadorIdEmpresa, dono, nomeEmpresa, endereco, tipoCozinha);
                empresasPorID.put(contadorIdEmpresa, restaurante);
                contadorIdEmpresa++;


        }
        if(idUltimaEmpresa == contadorIdEmpresa) { // verifica se uma nova empresa foi criada, se o contador nao aumentar, nenhum tipo de empresa foi encontrado no switch
            throw new TipoEmpresaInvalidoException();
        }else{
            return empresasPorID.get(contadorIdEmpresa-1).getIdEmpresa();// id da empresa
        }

    }

    public String getEmpresasDoUsuario(int idDono) throws UsuarioNaoCriaEmpresaException {
        if(usuariosPorID.get(idDono).getTipoObjeto().matches("cliente"))
        {
            throw new UsuarioNaoCriaEmpresaException();
        }
        String empresasPorDono = "";
        if(!empresasPorID.isEmpty()){
            int qntEmpresas = empresasPorID.size(); // quantidade de empresas registradas

            for(int i = 1; i <= qntEmpresas; i++){

                Empresa empresa = empresasPorID.get(i);

                if(i == 1){
                    empresasPorDono = empresasPorDono.concat("{[");
                }
                if(empresa.getIdDono() == idDono ){


                    if(empresasPorDono.matches("^\\{\\[\\[.*")){//veifica o inicio da string para saber quando add virgula e espaçamento entre as empresas.
                        empresasPorDono = empresasPorDono.concat(", ");
                    }
                    empresasPorDono =empresasPorDono.concat("[").concat(empresa.getNomeEmpresa()).concat(", ").concat(empresa.getEnderecoEmpresa()).concat("]");
                }
                if(i == qntEmpresas){
                    empresasPorDono = empresasPorDono.concat("]}");
                }
            }
        }
        else {
           empresasPorDono = empresasPorDono.concat("{[]}");
        }
        return empresasPorDono;
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws UsuarioNaoCadastradoException, NomeInvalidoException, UsuarioNaoCriaEmpresaException, IndiceInvalidoException, NaoExisteEmpresaException, IndiceMaiorException {
        int idEmpresa = 0;
        if(!usuariosPorID.containsKey(idDono)){
            throw new UsuarioNaoCadastradoException();
        }
        if(validaNome(nome)){
            throw new NomeInvalidoException();
        }

        ArrayList<String> empresasProcurada = new ArrayList<>();
        ArrayList<String> empresasProcuradaEndereco = new ArrayList<>();


        String empresasPorDonoSemColchetes = getEmpresasDoUsuario(idDono).replaceAll("[\\[\\]{}]", "");
        String[] empresasPorDono = empresasPorDonoSemColchetes.split(", ");

        for (int i = 0; i < empresasPorDono.length; i+=2) {
            if(empresasPorDono[i].matches(nome)){
                empresasProcurada.add(empresasPorDono[i]);
                empresasProcuradaEndereco.add(empresasPorDono[i+1]);
            }
        }
        if(empresasProcurada.isEmpty()){
            throw new NaoExisteEmpresaException();
        }
        if(indice < 0){
            throw new IndiceInvalidoException();
        }
        if(indice >= empresasProcurada.size()){
            throw new IndiceMaiorException();
        }
        if(!empresasPorID.isEmpty()){ //verifica se há pelo menos uma empresa cadastrada
            String nomeEmpresa;
            String enderecoEmpresa;
            for(Empresa empresa: empresasPorID.values()){
                nomeEmpresa = empresa.getNomeEmpresa();
                enderecoEmpresa = empresa.getEnderecoEmpresa();
                if(nomeEmpresa.matches(empresasProcurada.get(indice)) && enderecoEmpresa.matches(empresasProcuradaEndereco.get(indice))){
                    idEmpresa =  empresa.getIdEmpresa();
                }
            }
        }

        return idEmpresa; //id da empresa
    }

    public String getAtributoEmpresa(int idEmpresa, String atributo) throws EmpresaNaoCadastradaException, AtributoInvalidoException {
        if(empresasPorID.containsKey(idEmpresa))
        {
            if(atributo ==null || atributo.isEmpty() || atributo.isBlank())
            {
                throw new AtributoInvalidoException();
            }

            Empresa empresa = empresasPorID.get(idEmpresa);
            return switch (atributo) {
                case "nome" -> empresa.getNomeEmpresa();
                case "endereco" -> empresa.getEnderecoEmpresa();
                case "tipoCozinha" -> empresa.getTipoCozinha();
                case "dono" -> {
                    Usuario usuario = usuariosPorID.get(empresa.getIdDono());
                    yield usuario.getNome();
                }
                default -> throw new AtributoInvalidoException();
            };
        }
        else{
            throw new EmpresaNaoCadastradaException();
        }

    }

    public void validaDadosEmpresa(int dono, String nome, String endereco, String tipoCozinha) throws UsuarioNaoCadastradoException, EmpresaNomeInvalidoException, EmpresaEnderecoInvalidoException,
            EmpresaTipoCozinhaInvalidoException, UsuarioNaoCriaEmpresaException, EmpresaNomeExisteException, EmpresaNomeEnderecoEmUsoException {
        //dono existe?
       if(!(usuariosPorID.containsKey(dono))){
            throw new UsuarioNaoCadastradoException();
        }
        if(usuariosPorID.get(dono).getTipoObjeto().matches("cliente")){
            throw new UsuarioNaoCriaEmpresaException();
        }
        //nome da empresa vazio?
        if(validaNome(nome)){
            throw new EmpresaNomeInvalidoException();
        }
        //endereco vazio?
        if(validaNome(endereco)){
            throw new EmpresaEnderecoInvalidoException();
        }
        //tip0o cozinnha vazio?
        if(validaNome(tipoCozinha)){
            throw new EmpresaTipoCozinhaInvalidoException();
        }

        for (Empresa empresa : empresasPorID.values()){
            if(empresa.getNomeEmpresa().matches(nome)){
                if(empresa.getIdDono() == dono){
                    if(empresa.getEnderecoEmpresa().matches(endereco)) {
                        throw new EmpresaNomeEnderecoEmUsoException(); //retona que nao é possivel criar empresa com o mesmo endereco e nome
                    }

                }
                else{
                    throw new EmpresaNomeExisteException(); // retona que nao pode ter mais de uma empresa com mesmo nome e donos diferentes
                }

            }
        }



    }

    public int criarProduto(int idEmpresa, String nomeProduto, float valorProduto, String categoriaProduto)
            throws EmpresaNaoCadastradaException, NomeInvalidoException, ProdutoValorInvalidoExcepion, ProdutoCategoriaInvalidaException, ProdutoJaExisteNaEmpresaException {

        validaDadosProduto(idEmpresa, nomeProduto, valorProduto, categoriaProduto);
        Produto produto = new Produto(contadorIdProduto, nomeProduto, valorProduto, categoriaProduto,idEmpresa);
        produtosPorID.put(contadorIdProduto, produto);
        contadorIdProduto++;
        return produto.getIdProduto();
    }

    public void editarProduto(int idProduto, String nomeProduto, float valorProduto, String categoriaProduto) throws NomeInvalidoException, ProdutoValorInvalidoExcepion, ProdutoCategoriaInvalidaException, ProdutoNaoCadastradoException {
        if(validaNome(nomeProduto)){
            throw new NomeInvalidoException();
        }
        else if(valorProduto < 0){
            throw new ProdutoValorInvalidoExcepion();
        }
        else if(validaNome(categoriaProduto)){
            throw new ProdutoCategoriaInvalidaException();
        }
        else if(!produtosPorID.containsKey(idProduto)){
            throw new ProdutoNaoCadastradoException();
        }

        Produto produto = produtosPorID.get(idProduto);

        produto.setNomeProduto(nomeProduto);
        produto.setValorProduto(valorProduto);
        produto.setCategoriaProduto(categoriaProduto);

    }
    public String getProduto(String  nomeProduto, int idEmpresa, String atributo)
            throws EmpresaNaoCadastradaException, NomeInvalidoException, ProdutoAtributoNaoExisteException, ProdutoNaoEncontradoException {
        if(validaNome(nomeProduto)){
            throw new NomeInvalidoException();
        }
        else if(!empresasPorID.containsKey(idEmpresa)){
            throw new EmpresaNaoCadastradaException();
        }
        Produto produto = null;
        for(int i = 1; i <= produtosPorID.size(); i++){
            Produto p = produtosPorID.get(i);
            if(p.getNomeProduto().matches(nomeProduto) && p.getIdEmpresa() == idEmpresa){
                produto = p;
                break;



            }
            if(i == produtosPorID.size()){
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
            str = empresasPorID.get(idEmpresa).getNomeEmpresa();
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
        if(!empresasPorID.containsKey(idEmpresa)){
            throw new EmpresaNaoEncontradaException();
        }

        String produtosPorEmpresa = "";


        if(!produtosPorID.isEmpty()){
            int qntProdutos = produtosPorID.size(); // quantidade de produtos registradas
            for(int i = 1; i <= qntProdutos; i++){

                Produto produto = produtosPorID.get(i);

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
    public void validaDadosProduto(int idEmpresa, String nomeProduto, float valorProduto, String categoriaProduto) throws EmpresaNaoCadastradaException, NomeInvalidoException, ProdutoValorInvalidoExcepion, ProdutoCategoriaInvalidaException, ProdutoJaExisteNaEmpresaException {
        if(!empresasPorID.containsKey(idEmpresa)){
            throw new EmpresaNaoCadastradaException();
        }
        if(validaNome(nomeProduto)){
            throw new NomeInvalidoException();
        }
        if(validaNome(categoriaProduto)){
            throw new ProdutoCategoriaInvalidaException();
        }
        if(valorProduto <= 0){
            throw new ProdutoValorInvalidoExcepion();
        }

        if(!produtosPorID.isEmpty()){
            for(Produto produto: produtosPorID.values()){
                if(produto.getNomeProduto().matches(nomeProduto) && produto.getIdEmpresa() == idEmpresa){

                    if(nomeProduto.matches("Refrigerante")){

                    throw new ProdutoJaExisteNaEmpresaException();

                    }
                }

            }
        }

    }

    // PEDIO    PEDIDO   PEDIDO  PEDIDO      PEDIDO

    public int criarPedido(int idCliente, int idEmpresa) throws EmpresaNaoCadastradaException, DonoNaoFazPedidoException, UsuarioNaoCadastradoException, AtributoInvalidoException, NaoPermitidoPedidosAbertoMesmaEmpresaException {

        validaDadosPedido(idCliente, idEmpresa);
        String nomeCliente = getAtributoUsuario(idCliente, "nome");
        String nomeEmpresa = getAtributoEmpresa(idEmpresa, "nome");
        Pedido pedido = new Pedido(contadorIdPedido, nomeCliente, nomeEmpresa, "aberto", idCliente, idEmpresa);
        pedidosPorID.put(contadorIdPedido, pedido);
        contadorIdPedido++;
        return pedido.getNumeroPedido(); //numero do pedido
    }

    public void validaDadosPedido(int idCliente, int idEmpresa) throws UsuarioNaoCadastradoException, EmpresaNaoCadastradaException, DonoNaoFazPedidoException, NaoPermitidoPedidosAbertoMesmaEmpresaException {
        if(!usuariosPorID.containsKey(idCliente)){
            throw new UsuarioNaoCadastradoException();
        }
        if(!empresasPorID.containsKey(idEmpresa)){
            throw new EmpresaNaoCadastradaException();
        }
        if(usuariosPorID.get(idCliente).getTipoObjeto().matches("donoRestaurante")){
            throw new DonoNaoFazPedidoException();
        }

        for(Pedido pedido: pedidosPorID.values()){//

            if(pedido.getIdCliente() == idCliente && pedido.getIdEmpresa() == idEmpresa){
                if(pedido.getEstadoPedido().equals("aberto") || pedido.getEstadoPedido().equals("preparando")){
                    throw new NaoPermitidoPedidosAbertoMesmaEmpresaException();
                }

            }
        }
    }

    public int getNumeroPedido(int idCliente, int idEmpresa, int indice) throws PedidoNaoEncontradoException {


        int qntPedidos = pedidosPorID.size();
        int numeroPedido = 0;
        int indiceProcurado = -1;                                 //iniciando a contagem padrao em 1 ao ives de 0 para reutilizar a variavel "indice" como contador e saber qual o pedido correto

        for(int i=1; i<= qntPedidos; i++){
            Pedido pedido = pedidosPorID.get(i);
            if(pedido.getIdCliente() == idCliente && pedido.getIdEmpresa() == idEmpresa){
                 indiceProcurado++;

                if(indiceProcurado == indice){
                    numeroPedido = pedido.getNumeroPedido();
                    break;
                }
            }

        }
        if(indiceProcurado < indice || indiceProcurado > indice)
        {
            throw new PedidoNaoEncontradoException();
        }

        return numeroPedido; //numero do pedido
    }

    public void adicionarProduto(int numeroPedido, int idProduto) throws NaoExistePedidoAbertoException, ProdutoNaoEncontradoException, ProdutoNaoPerteceEmpresaException, PedidoFechadoException {
        if(!pedidosPorID.containsKey(numeroPedido)){
            throw new NaoExistePedidoAbertoException();
        }
        else if(!produtosPorID.containsKey(idProduto)){
            throw new ProdutoNaoEncontradoException();
        }



        Pedido pedido = pedidosPorID.get(numeroPedido);
        Produto produto = produtosPorID.get(idProduto);
        if(pedido.getEstadoPedido().matches("fechado")){
            throw new PedidoFechadoException();
        }
        if(produto.getIdEmpresa() == pedido.getIdEmpresa()){
            String str = pedido.getProdutos();
            str = str.replaceAll("]}", "");

            if(str.matches(".*[a-zA-Z0-9]$")){ // se há produtos
                str = str.concat(", "+produto.getNomeProduto()+"]}");
            }
            else{
                str = str.concat(produto.getNomeProduto()+"]}");
            }


            pedido.setProdutos(str);
            pedido.setValorPedido(produto.getValorProduto());
        }
        else{
            throw new ProdutoNaoPerteceEmpresaException();
        }

    }

    public String getPedidos(int numeroPedido, String atributo) throws PedidoNaoEncontradoException, AtributoInvalidoException, ProdutoAtributoNaoExisteException {
        if(!pedidosPorID.containsKey(numeroPedido)){
            throw new PedidoNaoEncontradoException();
        }
        else if(validaNome(atributo)){
            throw new AtributoInvalidoException();
        }
        Pedido pedido = pedidosPorID.get(numeroPedido);
        String str = "";
        switch (atributo){
            case "cliente":
               return  str = pedido.getNomeCliente();
            case "empresa":
                return str = pedido.getNomeEmpresa();
            case "estado":
                 str = pedido.getEstadoPedido();
                    finalizarPedido(numeroPedido); //ta certo???
                    return str;
            case "produtos":
                return str = pedido.getProdutos();
            case "valor":
                return str = String.format(Locale.US, "%.2f", pedido.getValorPedido());
        }
        if(str.isEmpty()){
            throw new ProdutoAtributoNaoExisteException();
        }

        return str;
    }

    public void fecharPedido(int numeroPedido) throws PedidoNaoEncontradoException {
        if(!pedidosPorID.containsKey(numeroPedido)){
            throw new PedidoNaoEncontradoException();
        }
        Pedido pedido = pedidosPorID.get(numeroPedido);
        pedido.setEstadoPedido("preparando");
    }

    public void removerProduto(int numeroPedido, String produto) throws ProdutoInvalidoException, PedidoNaoEncontradoException, ProdutoNaoEncontradoException, NaoPossivelRemoverProdutoException, EmpresaNaoCadastradaException, ProdutoAtributoNaoExisteException, NomeInvalidoException {
        if(!pedidosPorID.containsKey(numeroPedido)){
            throw new PedidoNaoEncontradoException();
        }
        else if(validaNome(produto)){
            throw new ProdutoInvalidoException();
        }



        Pedido pedido = pedidosPorID.get(numeroPedido);
        String produtosDoPedido = pedido.getProdutos();

        if(pedido.getEstadoPedido().contains("fechado")){
            throw new NaoPossivelRemoverProdutoException();
        }
        else if(!pedido.getProdutos().contains(produto)){
            throw new ProdutoNaoEncontradoException();
        }

        if(produtosDoPedido.contains(", "+produto+",")){
            produtosDoPedido = produtosDoPedido.replaceFirst(", "+produto, "");
        }
        else if(produtosDoPedido.contains("["+produto+",")){
            produtosDoPedido = produtosDoPedido.replaceFirst(produto+", ", "");
        }
        else if(produtosDoPedido.contains("["+produto)){
            produtosDoPedido = produtosDoPedido.replaceFirst(produto, "");
        }
        else if(produtosDoPedido.contains(", "+produto+"]")){
            produtosDoPedido = produtosDoPedido.replaceFirst(", "+produto, "");
        }

        //atualizando os produtos do pedido
        pedido.setProdutos(produtosDoPedido);
        String valorProduto = getProduto(produto, pedido.getIdEmpresa(),"valor");
        float valorP = Float.valueOf(valorProduto);
        pedido.setValorPedido(-valorP);
            

    }
    public void finalizarPedido(int numeroPedido) throws PedidoNaoEncontradoException {
        if(!pedidosPorID.containsKey(numeroPedido)){
            throw new PedidoNaoEncontradoException();
        }
        Pedido pedido = pedidosPorID.get(numeroPedido);

        if(pedido.getEstadoPedido().equals("preparando")){
            pedido.setEstadoPedido("fechado");
        }
    }


}
