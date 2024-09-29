package br.ufal.ic.p2.jackut.modelo;

import br.ufal.ic.p2.jackut.modelo.empresa.Empresa;
import br.ufal.ic.p2.jackut.modelo.empresa.Restaurante;
import br.ufal.ic.p2.jackut.modelo.empresa.produto.Produto;
import br.ufal.ic.p2.jackut.modelo.exception.*;
import br.ufal.ic.p2.jackut.modelo.usuario.Cliente;
import br.ufal.ic.p2.jackut.modelo.usuario.DonoRestaurante;
import br.ufal.ic.p2.jackut.modelo.usuario.Usuario;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Sistema {

    int contadorID = 1;
    int contadorIdEmpresa = 1;
    int contadorIdProduto = 1;
    Map<Integer, Empresa> empresasPorID = new HashMap<>();
    Map<Integer, Usuario> usuariosPorID = new HashMap<>();
    Map<Integer, Produto> produtosPorID = new HashMap<>();
    XML xml = new XML();



    public void zerarSistema(){
       /*for(int i = 0; i <= usuariosPorID.size(); i++) {
            usuariosPorID.remove(i);
        }
        for(int i = 0; i <= empresasPorID.size(); i++) {
            empresasPorID.remove(i);
        }*/
        while(contadorID > 0 || contadorIdEmpresa > 0){
            contadorID--;
            contadorIdEmpresa--;
            if(contadorID > 0){
                usuariosPorID.remove(contadorID);

            }
            if(contadorIdEmpresa > 0){
                empresasPorID.remove(contadorIdEmpresa);
            }
        }

        contadorID =1;
        contadorIdEmpresa = 1;
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
        return nome == null || nome.isEmpty() ||nome.isBlank();
    }
   /* public int verificaEmail(String email){
        if(!usuariosPorID.isEmpty())
        {
            for (Usuario usuario : usuariosPorID.values()) {

                if (usuario.getEmail().equals(email)) {
                    return usuario.getId();
                }
            }
        }
        return -1;


    }*/

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
                //System.out.println("EMPRESA TESTE: "+restaurante.getNomeEmpresa());
                contadorIdEmpresa++;


        }
        if(idUltimaEmpresa == contadorIdEmpresa) { // verifica se uma nova empresa foi criada, se o contador nao aumentar, nenhum tipo de empresa foi encontrado no switch
            throw new TipoEmpresaInvalidoException();
        }else{
            return empresasPorID.get(contadorIdEmpresa-1).getIdEmpresa();// id da empresa
        }

    }

    public String getEmpresasDoUsuario(int idDono) throws UsuarioNaoCriaEmpresaException {
        if(usuariosPorID.get(idDono).getTipoObjeto().matches("Cliente"))
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
        if(usuariosPorID.get(dono).getTipoObjeto().matches("Cliente")){
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
            throws EmpresaNaoCadastradaException, NomeInvalidoException, ProdutoValorInvalidoExcepion, ProdutoCategoriaInvalidaException {

        validaDadosProduto(idEmpresa, nomeProduto, valorProduto, categoriaProduto);
        Produto produto = new Produto(idEmpresa, nomeProduto, valorProduto, categoriaProduto);
        //produtosPorID.put(contadorIdProduto, produto);
        contadorIdProduto++;
        return produto.getIdProduto();
    }

    public void editarProduto(int idProduto, String nomeProduto, float valorProduto, String categoriaProduto){

    }
    public String getProduto(String  nomeProduto, int IdEmpresa, String atributo){
        return ""; //retorna a informação do atributo
    }
    public String listarProdutos(int empresa){
        return ""; //retonar a lista de produtos
    }
    public void validaDadosProduto(int idEmpresa, String nomeProduto, float valorProduto, String categoriaProduto) throws EmpresaNaoCadastradaException, NomeInvalidoException, ProdutoValorInvalidoExcepion, ProdutoCategoriaInvalidaException {
        if(!empresasPorID.containsKey(idEmpresa)){
            throw new EmpresaNaoCadastradaException();
        }
        if(validaNome(nomeProduto)){
            throw new NomeInvalidoException();
        }
        if(valorProduto <= 0){
            throw new ProdutoValorInvalidoExcepion();
        }
        if(validaNome(categoriaProduto)){
            throw new ProdutoCategoriaInvalidaException();
        }
        //Map<Produto, Integer> invertendoChaveValor =
                produtosPorID.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
       //if(invertendoChaveValor.containsKey(nomeProduto))
        //conciuir listar Produtos
    }

}
