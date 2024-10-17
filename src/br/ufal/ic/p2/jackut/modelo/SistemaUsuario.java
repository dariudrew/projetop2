package br.ufal.ic.p2.jackut.modelo;


import br.ufal.ic.p2.jackut.modelo.empresa.Empresa;
import br.ufal.ic.p2.jackut.modelo.empresa.produto.Produto;
import br.ufal.ic.p2.jackut.modelo.exception.*;


import br.ufal.ic.p2.jackut.modelo.usuario.Cliente;
import br.ufal.ic.p2.jackut.modelo.usuario.DonoEmpresa;
import br.ufal.ic.p2.jackut.modelo.usuario.Entregador;
import br.ufal.ic.p2.jackut.modelo.usuario.Usuario;


import java.lang.reflect.Method;


public class SistemaUsuario {
    private SistemaDados dados;
  public SistemaUsuario(SistemaDados dados){
      this.dados = dados;
  }


    public void zerarSistema(){

        while(dados.contadorID > 0 || dados.contadorIdEmpresa > 0){
            dados.contadorID--;
            dados.contadorIdEmpresa--;
            dados.contadorIdProduto--;
            dados.contadorIdPedido--;

            if(dados.contadorID > 0){
                dados.usuariosPorID.remove(dados.contadorID);
            }
            if(dados.contadorIdEmpresa > 0){
                dados.empresasPorID.remove(dados.contadorIdEmpresa);
            }
            if(dados.contadorIdProduto > 0){
                dados.produtosPorID.remove(dados.contadorIdProduto);
            }
            if(dados.contadorIdPedido > 0){
                dados.pedidosPorID.remove(dados.contadorIdPedido);
            }

        }

        dados.contadorID = 1;
        dados.contadorIdEmpresa = 1;
        dados.contadorIdProduto = 1;
        dados.contadorIdPedido = 1;

    }

    public void criarUsuario(String nome, String email, String senha, String endereco)
            throws NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidaException, EmailJaExisteException {

        validaDados(nome, email, senha, endereco);
        if(getIdUsuario("Email", email) > 0){
            throw new EmailJaExisteException();
        }
        Cliente cliente = new Cliente(dados.contadorID, nome, email, senha, endereco);

       if(dados.contadorID == 0){
           //xml.criarXML();
       }
        //xml.inserirUsuario(cliente);
        dados.usuariosPorID.put(dados.contadorID, cliente);
        dados.contadorID+=1;
    }
    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidaException, EmailJaExisteException, CPFInvalidoException {
        validaCPF(cpf);
        validaDados(nome, email, senha, endereco);
        if(getIdUsuario("Email", email) > 0){
            throw new EmailJaExisteException();
        }
        DonoEmpresa donoEmpresa = new DonoEmpresa(dados.contadorID, nome, email, senha, endereco, cpf);
        //xml.inserirUsuario(donoEmpresa);

        dados.usuariosPorID.put(dados.contadorID, donoEmpresa);
        dados.contadorID+=1;
    }
    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws EmailJaExisteException, NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidaException, NomeVeiculoInvalidoException, PlacaInvalidoException, FormatoPlacaException {
        validaDados(nome, email, senha, endereco);
        if(validaNome(veiculo)){
            throw new NomeVeiculoInvalidoException();
        }
        else if(validaNome(placa) || getIdUsuario("Placa", placa) > 0){
            throw new PlacaInvalidoException();
        }

        if(getIdUsuario("Email", email) > 0){
            throw new EmailJaExisteException();
        }
        /*if(!placa.matches("^BRA %d{4}$")){
            throw new PlacaInvalidoException();
        }*/


        Entregador entregador = new Entregador(dados.contadorID, nome, email, senha, endereco, veiculo, placa);
        dados.usuariosPorID.put(dados.contadorID, entregador);
        dados.contadorID++;
    }

    public void validaDados(String nome, String email, String senha, String endereco)
            throws NomeInvalidoException, EmailInvalidoException, SenhaInvalidaException, EnderecoInvalidoException, EmailJaExisteException {
        if(validaNome(nome)){
            throw new NomeInvalidoException();
        }
        if(validaSenha(senha)){
            throw new SenhaInvalidaException();
        }
        if(validaEndereco(endereco)){
            throw new EnderecoInvalidoException();
        }
        if(validaEmail(email)){
            throw new EmailInvalidoException();
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

    public int getIdUsuario(String tipoMetodo, String atributo){
        try {
            String metodoNome = "get" + tipoMetodo;

            if(!dados.usuariosPorID.isEmpty())
            {
                for (Usuario usuario : dados.usuariosPorID.values()) {
                    Method metodo = usuario.getClass().getMethod(metodoNome);

                    if (metodo.invoke(usuario).equals(atributo)) {
                        return usuario.getId();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();//criar uma excessão??

        }
        return 0;
    }

    public boolean validaSenha(String senha) {
        return senha == null || senha.trim().isEmpty();
    }

    public boolean validaEndereco(String endereco){
        return endereco == null || endereco.trim().isEmpty();
    }

    public String getAtributoUsuario(int id, String atributo) throws UsuarioNaoCadastradoException{

        if(dados.usuariosPorID.containsKey(id)){

            Usuario usuario = dados.usuariosPorID.get(id);
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
                case "veiculo":
                    return usuario.getVeiculo();
                case "placa":
                    return usuario.getPlaca();
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
             int id = getIdUsuario("Email", email);
            if( id > 0)
            {
                Usuario usuario = dados.usuariosPorID.get(id);
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

    public void cadastrarEntregador(int idEmpresa, int entregador) throws EmpresaNaoEncontradaException, UsuarioNaoCadastradoException, UsuarioNaoEntregadorException {

      if(dados.empresasPorID.isEmpty()|| !dados.empresasPorID.containsKey(idEmpresa)){
          throw new EmpresaNaoEncontradaException();
      }
      if(dados.usuariosPorID.isEmpty() || !dados.usuariosPorID.containsKey(entregador)){
          throw new UsuarioNaoCadastradoException();
      }

      Usuario usuario = dados.usuariosPorID.get(entregador);
      if(!usuario.getTipoObjeto().matches("entregador")){
          throw new UsuarioNaoEntregadorException();
      }


        Empresa empresa = dados.empresasPorID.get(idEmpresa);

        String str = empresa.getEntregadoresVinculados(); //empresa.entregadoresa
        str = str.replaceAll("]}", "");
        if(str.matches(".*[a-zA-Z0-9]$")){ // se há produtos
            str = str.concat(", "+usuario.getEmail()+"]}");
        }
        else{
            str = str.concat(usuario.getEmail()+"]}");
        }
        empresa.setEntregadoresVinculados(str);
    }//fazer método "remover entregador"

    public String getEntregadores(int idEmpresa) throws EmpresaNaoEncontradaException {
      if(dados.empresasPorID.isEmpty() || !dados.empresasPorID.containsKey(idEmpresa)){
          throw new EmpresaNaoEncontradaException();
      }
      Empresa empresa = dados.empresasPorID.get(idEmpresa);
      return empresa.getEntregadoresVinculados();
    }

    public String getEmpresas(int entregador) throws UsuarioNaoCadastradoException, EmpresaNaoCadastradaException, UsuarioNaoEntregadorException {
      if(dados.usuariosPorID.isEmpty() || !dados.usuariosPorID.containsKey(entregador)){
          throw new UsuarioNaoCadastradoException();
      }
      else if(dados.empresasPorID.isEmpty()){
          throw new EmpresaNaoCadastradaException();
      }
      else if(!dados.usuariosPorID.get(entregador).getTipoObjeto().matches("entregador")){
          throw new UsuarioNaoEntregadorException();
      }
      String listaEmpresas = "";
      Usuario usuario = dados.usuariosPorID.get(entregador);

        int qntEmpresas = dados.empresasPorID.size();

        for(int i = 1; i <= qntEmpresas; i++) {

            String listaEntregadores;
            Empresa empresa = dados.empresasPorID.get(i);
            listaEntregadores = empresa.getEntregadoresVinculados();
            listaEntregadores = listaEntregadores.replaceAll("[\\[\\]{}]", "");

            String[] listaEntregadoresaArray = listaEntregadores.split(", ");

            if (i == 1) {
                listaEmpresas = listaEmpresas.concat("{[");
            }
            for (int j = 0; j < listaEntregadoresaArray.length; j++) {

                if (usuario.getEmail().matches(listaEntregadoresaArray[j])) {
                    if (listaEmpresas.matches("^\\{\\[\\[.*")) {       //veifica se a string terminar com letras, para saber quando add virgula e espaçamento entre as produtos.
                        listaEmpresas = listaEmpresas.concat(", ");
                    }

                    listaEmpresas = listaEmpresas.concat("[" + empresa.getNomeEmpresa() + ", " + empresa.getEnderecoEmpresa() + "]");
                    break;
                }
            }
            if (i == qntEmpresas) {
                listaEmpresas = listaEmpresas.concat("]}");
            }

        }
        return listaEmpresas;
    }
}
