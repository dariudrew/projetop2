package br.ufal.ic.p2.jackut.modelo;

import br.ufal.ic.p2.jackut.modelo.exception.*;
import br.ufal.ic.p2.jackut.modelo.usuario.Cliente;
import br.ufal.ic.p2.jackut.modelo.usuario.DonoRestaurante;
import br.ufal.ic.p2.jackut.modelo.usuario.Usuario;

//xml
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.security.auth.login.LoginException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

import java.util.HashMap;
import java.util.Map;

public class Sistema {

    int contadorID = 0;
    Map<Integer, Usuario> usuariosPorID = new HashMap<>();
    XML xml = new XML();



    public void zerarSistema(){
       for(int i = 0; i <= usuariosPorID.size(); i++) {
            usuariosPorID.remove(i);
        }
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
        if(nome == null ||nome.isEmpty())
        {
            throw new NomeInvalidoException();
        }
        if(validaSenha(senha))
        {
            throw new SenhaInvalidaException();
        }
        if(endereco == null || endereco.trim().isEmpty()){
            throw new EnderecoInvalidoException();
        }
        if(validaEmail(email))
        {
            throw new EmailInvalidoException();
        }

        if(verificaEmail(email) >= 0)
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
        if(email == null || email.isEmpty() || !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")){
            return true;
        }
        return false;
    }
    public int verificaEmail(String email){
        if(!usuariosPorID.isEmpty())
        {
            for (Usuario usuario : usuariosPorID.values()) {
                if (usuario.getEmail().equals(email)) {
                    return usuario.getId();
                }
            }
        }
        return -1;

    }
    public boolean validaSenha(String senha) {
        if(senha == null || senha.trim().isEmpty()){
            return true;
        }
        return false;
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
             int id = verificaEmail(email);
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




}
