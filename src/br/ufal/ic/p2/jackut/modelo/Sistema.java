package br.ufal.ic.p2.jackut.modelo;

import br.ufal.ic.p2.jackut.modelo.exception.*;
import br.ufal.ic.p2.jackut.modelo.usuario.Cliente;
import br.ufal.ic.p2.jackut.modelo.usuario.Usuario;

import java.util.HashMap;
import java.util.Map;

public class Sistema {

    int contadorID = 0;
    Map<String, Usuario> usuariosPorID = new HashMap<>();


    public void zerarSistema(){

    }

    public void criarUsuario(String nome, String email, String senha, String endereco)
            throws NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidaException, EmailJaExisteException {
        validaDados(nome, email, senha, endereco);
        Cliente cliente = new Cliente(contadorID, nome, email, senha, endereco);
        usuariosPorID.put(String.valueOf(contadorID), cliente);

        for(int i = 1; i <= usuariosPorID.size(); i++){

            Usuario usuario = usuariosPorID.get(String.valueOf(i));
            System.out.println(i + " - email: " + usuario.getEmail());
        }
        contadorID+=1;
    }
    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws NomeInvalidoException, EmailInvalidoException, EnderecoInvalidoException, SenhaInvalidaException, EmailJaExisteException {
        validaDados(nome, email, senha, endereco);
        Cliente cliente = new Cliente(contadorID, nome, email, senha, endereco);
        usuariosPorID.put(String.valueOf(contadorID), cliente);
        for(int i = 1; i <= usuariosPorID.size(); i++){

            Usuario clienteMap = usuariosPorID.get(String.valueOf(i));
            System.out.println(i + " - email: " + clienteMap.getEmail());
        }

        contadorID+=1;


    }

    public void validaDados(String nome, String email, String senha, String endereco)
            throws NomeInvalidoException, EmailInvalidoException, SenhaInvalidaException, EnderecoInvalidoException, EmailJaExisteException {
        if(nome == null ||nome.isEmpty())
        {
            throw new NomeInvalidoException();
        }
        if(email == null || email.isEmpty() || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")){
            throw new EmailInvalidoException();
        }

        for(int i = 1; i <= usuariosPorID.size(); i++){

            Usuario usuario = usuariosPorID.get(String.valueOf(i));
            if(usuario.getEmail().contains(email))
            {
                throw new EmailJaExisteException();
            }
        }

        if(senha == null || senha.trim().isEmpty())
        {
            throw new SenhaInvalidaException();
        }
        if(endereco == null || endereco.trim().isEmpty()){
            throw new EnderecoInvalidoException();
        }
    }

    public String getAtributoUsuario(int id, String atributo) throws UsuarioNaoCadastradoException{
        System.out.println("teste "+ id);
        if(usuariosPorID.containsKey(id)){

            Usuario usuario = usuariosPorID.get(id);

            switch (atributo){
                case "nome":
                    return usuario.getNome();
                case "senha":
                    return usuario.getSenha();
                case "email":
                    return usuario.getEmail();
            }
        }else{
            throw new UsuarioNaoCadastradoException();
        }


        return "";
    }
    public int login (String email, String senha){
        return 0;
    }



}
