package br.ufal.ic.p2.jackut.modelo;

import br.ufal.ic.p2.jackut.modelo.usuario.Cliente;
import br.ufal.ic.p2.jackut.modelo.usuario.DonoRestaurante;
import br.ufal.ic.p2.jackut.modelo.usuario.Usuario;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XML{





    public void criarXML()
    {

        try{


            // Criação de um DocumentBuilderFactory e DocumentBuilder
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            // Criação de um documento XML
            Document document = documentBuilder.newDocument();
            Element raiz = document.createElement("myfood");



            //Criação de um TransformerFactory e Transformer para escrever o XML no arquivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("arquivo.xml"));
            Element root = document.createElement("myfood");
            document.appendChild(root);

            // Escrever o conteúdo do documento XML no arquivo
            transformer.transform(domSource, streamResult);

        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    public void inserirUsuario(Usuario usuario)
    {
        try {
            // Criação de um DocumentBuilderFactory e DocumentBuilder
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            // Parse do documento XML a partir do arquivo
            Document document = documentBuilder.parse(new File("arquivo.xml"));

            // Normalização do documento
            document.getDocumentElement().normalize();

            // Obtenção do elemento raiz
            Element raiz = document.getDocumentElement();

            Element usuarios = document.createElement("usuario");
            usuarios.setAttribute("id", String.valueOf(usuario.getId()));


            Element id = document.createElement("id");
            id.appendChild(document.createTextNode(String.valueOf(usuario.getId())));
            usuarios.appendChild(id);

            Element nome = document.createElement("nome");
            nome.appendChild(document.createTextNode(usuario.getNome()));
            usuarios.appendChild(nome);

            Element email = document.createElement("email");
            email.appendChild(document.createTextNode(usuario.getEmail()));
            usuarios.appendChild(email);

            Element senha = document.createElement("senha");
            senha.appendChild(document.createTextNode(usuario.getSenha()));
            usuarios.appendChild(senha);

            Element endereco = document.createElement("endereco");
            endereco.appendChild(document.createTextNode(usuario.getEndereco()));
            usuarios.appendChild(endereco);


            if(usuario instanceof DonoRestaurante){
                Element cpf = document.createElement("cpf");
                cpf.appendChild(document.createTextNode(((DonoRestaurante) usuario).getCpf()));
                usuarios.appendChild(endereco);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("arquivo.xml"));

            raiz.appendChild(usuarios);

            // Escrever o conteúdo do documento XML no arquivo
            transformer.transform(domSource, streamResult);

        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }


}
