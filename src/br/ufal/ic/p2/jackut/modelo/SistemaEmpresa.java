package br.ufal.ic.p2.jackut.modelo;

import br.ufal.ic.p2.jackut.modelo.empresa.Empresa;
import br.ufal.ic.p2.jackut.modelo.empresa.Farmacia;
import br.ufal.ic.p2.jackut.modelo.empresa.Mercado;
import br.ufal.ic.p2.jackut.modelo.empresa.Restaurante;
import br.ufal.ic.p2.jackut.modelo.exception.*;
import br.ufal.ic.p2.jackut.modelo.usuario.Usuario;


import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.lang.Math;

public class SistemaEmpresa {
    private SistemaDados dados;
    private SistemaUsuario sistemaUsuario;

    public SistemaEmpresa(SistemaDados dados){
        this.dados = dados;
        this.sistemaUsuario = new SistemaUsuario(dados);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nomeEmpresa, String endereco, String tipoCozinha) throws
            UsuarioNaoCadastradoException, EmpresaEnderecoInvalidoException, EmpresaTipoCozinhaInvalidoException, UsuarioNaoCriaEmpresaException,
            EmpresaNomeExisteException, EmpresaNomeEnderecoEmUsoException, TipoEmpresaInvalidoException, NomeInvalidoException {
        validaDadosRestaurante(tipoEmpresa, dono, nomeEmpresa, endereco, tipoCozinha);
        Restaurante restaurante = new Restaurante(dados.contadorIdEmpresa, dono, nomeEmpresa, endereco, tipoCozinha);

        if (tipoEmpresa.matches("restaurante")){
            dados.empresasPorID.put(dados.contadorIdEmpresa, restaurante);
            dados.contadorIdEmpresa++;
        }
        else{
            throw new TipoEmpresaInvalidoException();
        }
        return restaurante.getIdEmpresa();
    }
    public void validaDadosRestaurante(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha)
            throws UsuarioNaoCadastradoException, UsuarioNaoCriaEmpresaException, EmpresaEnderecoInvalidoException, EmpresaNomeEnderecoEmUsoException, EmpresaNomeExisteException, EmpresaTipoCozinhaInvalidoException, TipoEmpresaInvalidoException, NomeInvalidoException {
        validaDadosGeraisEmpresa(tipoEmpresa, dono, nome, endereco);
        if(sistemaUsuario.validaNome(tipoCozinha)){
            throw new EmpresaTipoCozinhaInvalidoException();
        }
        verificaEmpresa(nome, dono, endereco);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nomeMercado, String endereco, String abre, String fecha, String tipoMercado)
            throws UsuarioNaoCriaEmpresaException, EmpresaEnderecoInvalidoException, EmpresaNomeInvalidoException, UsuarioNaoCadastradoException, EmpresaNomeEnderecoEmUsoException, EmpresaNomeExisteException, FormatoHoraInvalidoException, HorarioInvalidoException, TipoEmpresaInvalidoException, NomeInvalidoException, TipoMercadoInvalidoException {

        validaDadosMercado(tipoEmpresa, dono, nomeMercado, endereco, abre, fecha,tipoMercado);
        Mercado mercado = new Mercado(dados.contadorIdEmpresa, dono, nomeMercado, endereco,abre, fecha, tipoMercado);
        if(tipoEmpresa.matches("mercado")){
            dados.empresasPorID.put(dados.contadorIdEmpresa, mercado);
            dados.contadorIdEmpresa++;
        }
        else{
            throw new TipoEmpresaInvalidoException();
        }

        return mercado.getIdEmpresa();
    }

    public void validaDadosMercado(String tipoEmpresa, int dono, String nomeMercado, String endereco, String abre, String fecha, String tipoMercado) throws TipoEmpresaInvalidoException, HorarioInvalidoException, FormatoHoraInvalidoException, EmpresaNomeEnderecoEmUsoException, EmpresaNomeExisteException, EmpresaEnderecoInvalidoException, EmpresaNomeInvalidoException, UsuarioNaoCriaEmpresaException, UsuarioNaoCadastradoException, NomeInvalidoException, TipoMercadoInvalidoException {
            validaDadosGeraisEmpresa(tipoEmpresa, dono, nomeMercado, endereco);
            if(sistemaUsuario.validaNome(tipoMercado)){
                throw new TipoMercadoInvalidoException();
            }
            horarioFormato(abre, fecha);
            horarioNull(abre, fecha);

            horarioPadrao(abre, fecha);
            verificaEmpresa(nomeMercado,dono,endereco);

    }
    public int criarEmpresa(String tipoEmpresa, int dono, String nomeFarmacia, String endereco,  boolean aberto24Horarios, int numeroFuncionarios) throws UsuarioNaoCriaEmpresaException, EmpresaEnderecoInvalidoException, NomeInvalidoException, UsuarioNaoCadastradoException, TipoEmpresaInvalidoException, HorarioInvalidoException, EmpresaNomeEnderecoEmUsoException, NumeroFuncionariosException, EmpresaNomeExisteException {
        validaDadosFarmacia(tipoEmpresa, dono, nomeFarmacia, endereco, numeroFuncionarios);
        Farmacia farmacia = new Farmacia(dados.contadorIdEmpresa, dono, nomeFarmacia, endereco, aberto24Horarios, numeroFuncionarios);
        if(tipoEmpresa.matches("farmacia")){
            dados.empresasPorID.put(dados.contadorIdEmpresa, farmacia);
            dados.contadorIdEmpresa++;
        }
        else{
            throw new TipoEmpresaInvalidoException();
        }
        return farmacia.getIdEmpresa();
    }
    public void validaDadosFarmacia(String tipoEmpresa, int dono, String nomeFarmacia, String endereco, int numeroFuncionarios) throws UsuarioNaoCadastradoException, UsuarioNaoCriaEmpresaException, TipoEmpresaInvalidoException, NomeInvalidoException, EmpresaEnderecoInvalidoException, NumeroFuncionariosException, EmpresaNomeEnderecoEmUsoException, EmpresaNomeExisteException {
        validaDadosGeraisEmpresa(tipoEmpresa, dono, nomeFarmacia, endereco);
        if(numeroFuncionarios < 1){
            throw new NumeroFuncionariosException();
        }
        verificaEmpresa(nomeFarmacia, dono, endereco);
    }
    public String getEmpresasDoUsuario(int idDono) throws UsuarioNaoCriaEmpresaException {
        if(dados.usuariosPorID.get(idDono).getTipoObjeto().matches("cliente"))
        {
            throw new UsuarioNaoCriaEmpresaException();
        }
        String empresasPorDono = "";
        if(!dados.empresasPorID.isEmpty()){
            int qntEmpresas = dados.empresasPorID.size();

            for(int i = 1; i <= qntEmpresas; i++){

                Empresa empresa = dados.empresasPorID.get(i);

                if(i == 1){
                    empresasPorDono = empresasPorDono.concat("{[");
                }
                if(empresa.getIdDono() == idDono ){


                    if(empresasPorDono.matches("^\\{\\[\\[.*")){
                        empresasPorDono = empresasPorDono.concat(", ");
                    }
                    empresasPorDono = empresasPorDono.concat("["+empresa.getNomeEmpresa()+", "+empresa.getEnderecoEmpresa()+"]");
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
        if(indice < 0){
            throw new IndiceInvalidoException();
        }
        if(empresasProcurada.isEmpty()){
            throw new NaoExisteEmpresaException();
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

        return idEmpresa;
    }
    public String getAtributoEmpresa(int idEmpresa, String atributo) throws EmpresaNaoCadastradaException, AtributoInvalidoException {
        if(dados.empresasPorID.containsKey(idEmpresa)) {
            if(sistemaUsuario.validaNome(atributo)) {
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
                case "abre" -> empresa.getAbre();
                case "fecha" -> empresa.getFecha();
                case "tipoMercado" -> empresa.getTipoMercado();
                case "aberto24Horas" -> empresa.getAberto24Horas() + "";
                case "numeroFuncionarios" -> empresa.getNumeroFuncionarios() + "";

                default -> throw new AtributoInvalidoException();
            };
        }
        else{
            throw new EmpresaNaoCadastradaException();
        }

    }
    public void alterarFuncionamento(int idEmpresa, String abre, String fecha) throws EmpresaNaoEncontradaException, FormatoHoraInvalidoException, HorarioInvalidoException, NaoMercadoValidoException {
        if(!dados.empresasPorID.containsKey(idEmpresa)){
            throw new EmpresaNaoEncontradaException();
        }
        horarioFormato(abre, fecha);
        horarioNull(abre, fecha);
        horarioPadrao(abre, fecha);

        Empresa empresa = dados.empresasPorID.get(idEmpresa);
        if(!empresa.getTipoEmpresa().matches("mercado")){
            throw new NaoMercadoValidoException();
        }
        empresa.setAbre(abre);
        empresa.setFecha(fecha);

    }
    public void horarioFormato(String abre, String fecha) throws FormatoHoraInvalidoException{
        if((abre != null && fecha != null) && (abre.isEmpty() || fecha.isEmpty() || !abre.matches("^\\d{2}:\\d{2}$") || !fecha.matches("^\\d{2}:\\d{2}$"))){
            throw new FormatoHoraInvalidoException();
        }

    }
    public void horarioNull(String abre, String fecha) throws HorarioInvalidoException {
        if(abre == null || fecha == null ) {
            throw new HorarioInvalidoException();
        }

    }
    public void horarioPadrao(String abre, String fecha) throws HorarioInvalidoException {
        int horaAbre = Integer.parseInt(abre.substring(0, 2));
        int horaFecha = Integer.parseInt(fecha.substring(0, 2));
        int minutoAbre = Integer.parseInt(abre.substring(3, 5));
        int minutoFecha = Integer.parseInt(fecha.substring(3, 5));
        int diferencaMinutos = 0;
        if (horaAbre > 23 || horaFecha > 23 || minutoAbre > 59 || minutoFecha > 59) {
            throw new HorarioInvalidoException();
        } else if (horaAbre < 0 || horaFecha < 0 || minutoAbre < 0 || minutoFecha < 0) {
            throw new HorarioInvalidoException();
        }


        LocalTime inicio = LocalTime.of(horaAbre, minutoAbre);
        LocalTime fim = LocalTime.of(horaFecha, minutoFecha);


        Duration duracao = Duration.between(inicio, fim);
        if (duracao.isNegative()) {
            duracao = duracao.plusHours(24);
        }
        long horas = duracao.toHours();
        long minutos = duracao.toMinutesPart();

        diferencaMinutos = Math.toIntExact(horas) * 60 + Math.toIntExact(minutos);

        if (diferencaMinutos < 360 || horaAbre > 12) {
            throw new HorarioInvalidoException();
        }
    }
    public void validaDadosGeraisEmpresa(String tipoEmpresa, int dono, String nomeEmpresa, String endereco)
            throws UsuarioNaoCadastradoException, UsuarioNaoCriaEmpresaException, TipoEmpresaInvalidoException, NomeInvalidoException, EmpresaEnderecoInvalidoException {

        if(!(dados.usuariosPorID.containsKey(dono))){
            throw new UsuarioNaoCadastradoException();
        }
        else if(!dados.usuariosPorID.get(dono).getTipoObjeto().matches("donoEmpresa")){
            throw new UsuarioNaoCriaEmpresaException();
        }
        else if(sistemaUsuario.validaNome(tipoEmpresa)){
            throw new TipoEmpresaInvalidoException();
        }
        else if(sistemaUsuario.validaNome(nomeEmpresa)){
            throw new NomeInvalidoException();
        }
        else if(sistemaUsuario.validaNome(endereco)){
            throw new EmpresaEnderecoInvalidoException();
        }
    }
    public void verificaEmpresa(String nome, int dono, String endereco) throws EmpresaNomeEnderecoEmUsoException, EmpresaNomeExisteException {
        for (Empresa empresa : dados.empresasPorID.values())
        {

            if(empresa.getNomeEmpresa().matches(nome)){
                if(empresa.getIdDono() == dono)
                {
                    if(empresa.getEnderecoEmpresa().matches(endereco))
                    {
                        throw new EmpresaNomeEnderecoEmUsoException();
                    }
                }
                else
                {
                    throw new EmpresaNomeExisteException();
                }
            }
        }
    }
}
