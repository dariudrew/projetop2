package br.ufal.ic.p2.jackut;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLReadWriteExample {

    public static void main(String[] args) {
        try {
            // Criação de um novo arquivo XML
            String filePath = "arquivo.xml";
            createXMLFile(filePath);

            // Leitura do arquivo XML criado
            readXMLFile(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para criar um arquivo XML
    private static void createXMLFile(String filePath) throws Exception {
        // Criação de um DocumentBuilderFactory e DocumentBuilder
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

        // Criação de um documento XML
        Document document = documentBuilder.newDocument();

        // Criação do elemento raiz
        Element root = document.createElement("myfood");
        document.appendChild(root);

        // Criação de um elemento filho
        Element usuario = document.createElement("usuario");
        root.appendChild(usuario);

        // Adição de atributos ao elemento livro
        int contador = 0;
        usuario.setAttribute("id", String.valueOf(contador));

        // Criação dos sub-elementos do livro
        Element id = document.createElement("id");
        id.appendChild(document.createTextNode(String.valueOf(contador)));
        usuario.appendChild(id);

        Element nome = document.createElement("nome");
        nome.appendChild(document.createTextNode("John Doe"));
        usuario.appendChild(nome);

        Element email = document.createElement("email");
        email.appendChild(document.createTextNode("aw@ufal.ic.br"));
        usuario.appendChild(email);

        Element endereco = document.createElement("endereco");
        endereco.appendChild(document.createTextNode("rua fulano de tal"));
        usuario.appendChild(endereco);




        // Criação de um TransformerFactory e Transformer para escrever o XML no arquivo
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(filePath));

        // Escrever o conteúdo do documento XML no arquivo
        transformer.transform(domSource, streamResult);

        System.out.println("Arquivo XML criado com sucesso em: " + filePath);
    }

    // Método para ler o conteúdo de um arquivo XML
    private static void readXMLFile(String filePath) throws Exception {
        // Criação de um DocumentBuilderFactory e DocumentBuilder
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

        // Parse do documento XML a partir do arquivo
        Document document = documentBuilder.parse(new File(filePath));

        // Normalização do documento
        document.getDocumentElement().normalize();

        // Obtenção do elemento raiz
        Element root = document.getDocumentElement();
        System.out.println("Elemento raiz: " + root.getNodeName());

        // Obtenção de uma lista de nós do elemento "livro"
        NodeList nodeList = document.getElementsByTagName("usuario");

        // Loop por cada elemento "livro"
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                /* como modificar os dados do xml, apos converter o nodelist em node, node em element, buscar usando a tag
                e usar setTextContent("fazer a alteração"), exemplo a seguir:
                 element.getElementsByTagName("nome").item(0).setTextContent("teste de modificação");*/

                // Leitura dos dados do livro
                String id = element.getAttribute("id");
                String nome = element.getElementsByTagName("nome").item(0).getTextContent();
                String email = element.getElementsByTagName("email").item(0).getTextContent();
                String endereco = element.getElementsByTagName("endereco").item(0).getTextContent();

                // Exibição dos dados do livro
                System.out.println("ID: " + id);
                System.out.println("Nome: " + nome);
                System.out.println("Email: " + email);
                System.out.println("Endereco: " + endereco);
            }
        }
    }
}
