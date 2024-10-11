package br.ufal.ic.p2.jackut.modelo;


import br.ufal.ic.p2.jackut.modelo.exception.*;

import br.ufal.ic.p2.jackut.modelo.usuario.Cliente;
import br.ufal.ic.p2.jackut.modelo.usuario.DonoRestaurante;
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

            if(dados.contadorID > 0){
                dados.usuariosPorID.remove(dados.contadorID);

            }
            if(dados.contadorIdEmpresa > 0){
                dados.empresasPorID.remove(dados.contadorIdEmpresa);
            }
            if(dados.contadorIdProduto > 0){
                dados.produtosPorID.remove(dados.contadorIdProduto);
            }

        }

        dados.contadorID = 1;
        dados.contadorIdEmpresa = 1;
        dados.contadorIdProduto = 1;
    }

    public void criarUsuario(String nome, String email, String senha, String endereco)
            throws NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidaException, EmailJaExisteException {

        validaDados(nome, email, senha, endereco);
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
        DonoRestaurante donoRestaurante = new DonoRestaurante(dados.contadorID, nome, email, senha, endereco, cpf);
        //xml.inserirUsuario(donoRestaurante);

        dados.usuariosPorID.put(dados.contadorID, donoRestaurante);
        dados.contadorID+=1;
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

        if(verificaUsuario("Email", email) >= 0){
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
}
