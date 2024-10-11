package br.ufal.ic.p2.jackut.modelo;

import br.ufal.ic.p2.jackut.modelo.empresa.Empresa;
import br.ufal.ic.p2.jackut.modelo.empresa.Restaurante;
import br.ufal.ic.p2.jackut.modelo.exception.*;
import br.ufal.ic.p2.jackut.modelo.usuario.Usuario;

import java.util.ArrayList;

public class SistemaEmpresa {

    private SistemaDados dados;
    private SistemaUsuario sistemaUsuario;

    public SistemaEmpresa(SistemaDados dados){
        this.dados = dados;
        this.sistemaUsuario = new SistemaUsuario(dados);
    }

    //   EMPRESA EMPRESA EMPRESA EMPRESA EMPRESA

    public int criarEmpresa(String tipoEmpresa, int dono, String nomeEmpresa, String endereco, String tipoCozinha)
            throws UsuarioNaoCadastradoException, EmpresaNomeInvalidoException, EmpresaEnderecoInvalidoException,
            EmpresaTipoCozinhaInvalidoException, UsuarioNaoCriaEmpresaException, EmpresaNomeExisteException,
            EmpresaNomeEnderecoEmUsoException, TipoEmpresaInvalidoException {

        validaDadosEmpresa(dono, nomeEmpresa, endereco, tipoCozinha);
        int idUltimaEmpresa = dados.contadorIdEmpresa;
        switch (tipoEmpresa){
            case "restaurante":
                Restaurante restaurante = new Restaurante(dados.contadorIdEmpresa, dono, nomeEmpresa, endereco, tipoCozinha);
                dados.empresasPorID.put(dados.contadorIdEmpresa, restaurante);
                dados.contadorIdEmpresa++;


        }
        if(idUltimaEmpresa == dados.contadorIdEmpresa) { // verifica se uma nova empresa foi criada, se o contador nao aumentar, nenhum tipo de empresa foi encontrado no switch
            throw new TipoEmpresaInvalidoException();
        }else{
            return dados.empresasPorID.get(dados.contadorIdEmpresa-1).getIdEmpresa();// id da empresa
        }

    }

    public String getEmpresasDoUsuario(int idDono) throws UsuarioNaoCriaEmpresaException {
        if(dados.usuariosPorID.get(idDono).getTipoObjeto().matches("cliente"))
        {
            throw new UsuarioNaoCriaEmpresaException();
        }
        String empresasPorDono = "";
        if(!dados.empresasPorID.isEmpty()){
            int qntEmpresas = dados.empresasPorID.size(); // quantidade de empresas registradas

            for(int i = 1; i <= qntEmpresas; i++){

                Empresa empresa = dados.empresasPorID.get(i);

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
        if(!dados.usuariosPorID.containsKey(idDono)){
            throw new UsuarioNaoCadastradoException();
        }
        if(sistemaUsuario.validaNome(nome)){
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
        if(!dados.empresasPorID.isEmpty()){ //verifica se há pelo menos uma empresa cadastrada
            String nomeEmpresa;
            String enderecoEmpresa;
            for(Empresa empresa: dados.empresasPorID.values()){
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
        if(dados.empresasPorID.containsKey(idEmpresa))
        {
            if(atributo ==null || atributo.isEmpty() || atributo.isBlank())
            {
                throw new AtributoInvalidoException();
            }

            Empresa empresa = dados.empresasPorID.get(idEmpresa);
            return switch (atributo) {
                case "nome" -> empresa.getNomeEmpresa();
                case "endereco" -> empresa.getEnderecoEmpresa();
                case "tipoCozinha" -> empresa.getTipoCozinha();
                case "dono" -> {
                    Usuario usuario = dados.usuariosPorID.get(empresa.getIdDono());
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

        if(!(dados.usuariosPorID.containsKey(dono))){
            throw new UsuarioNaoCadastradoException();
        }
        if(dados.usuariosPorID.get(dono).getTipoObjeto().matches("cliente")){
            throw new UsuarioNaoCriaEmpresaException();
        }

        if(sistemaUsuario.validaNome(nome)){
            throw new EmpresaNomeInvalidoException();
        }

        if(sistemaUsuario.validaNome(endereco)){
            throw new EmpresaEnderecoInvalidoException();
        }

        if(sistemaUsuario.validaNome(tipoCozinha))
        {
            throw new EmpresaTipoCozinhaInvalidoException();
        }

        for (Empresa empresa : dados.empresasPorID.values())
        {
            if(empresa.getNomeEmpresa().matches(nome)){
                if(empresa.getIdDono() == dono)
                {
                    if(empresa.getEnderecoEmpresa().matches(endereco))
                    {
                        throw new EmpresaNomeEnderecoEmUsoException(); //retona que nao é possivel criar empresa com o mesmo endereco e nome
                    }
                }
                else
                {
                    throw new EmpresaNomeExisteException(); // retona que nao pode ter mais de uma empresa com mesmo nome e donos diferentes
                }
            }
        }
    }
}
