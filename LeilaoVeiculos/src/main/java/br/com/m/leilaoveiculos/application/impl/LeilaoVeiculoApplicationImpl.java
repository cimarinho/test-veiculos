package br.com.m.leilaoveiculos.application.impl;

import br.com.m.config.exception.LeilaoVeiculosException;
import br.com.m.leilaoveiculos.application.LeilaoVeiculoApplication;
import br.com.m.leilaoveiculos.application.domain.LeilaoVeiculo;
import br.com.m.leilaoveiculos.application.mapper.LeilaoLegadoMapper;
import br.com.m.leilaoveiculos.infraestructure.LeilaoLegadoService;
import br.com.m.leilaoveiculos.infraestructure.dto.LegadoMensagemSucesso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class LeilaoVeiculoApplicationImpl implements LeilaoVeiculoApplication {
    @Value("${magalu.mensagemLegadoSucesso}")
    private String mensagemLegado;

    @Autowired
    private LeilaoLegadoService leilaoLegadoService;

    @Autowired
    private LeilaoLegadoMapper leilaoLegadoMapper;

    @Override
    public Boolean  existeCodigoControle(String codigoControle) {
        List<LeilaoVeiculo> consultar = leilaoLegadoMapper.mapToLeilao(this.leilaoLegadoService.consultar(), null);
        for (LeilaoVeiculo itera: consultar){
            if (itera.getLote().getVeiculos().getCodigoControle().equals(codigoControle)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public String cadastrarVeiculo(LeilaoVeiculo lance) {
        String criar = leilaoLegadoService.criar(leilaoLegadoMapper.mapCriarLegado(lance));
        if (Objects.nonNull(criar)) {
            return criar;
        }
        throw new LeilaoVeiculosException("Erro ao cadastrar veiculo.");
    }

    @Override
    public void deletarVeiculo(String id) {
        buscarVeiculo(id);
        LegadoMensagemSucesso legadoMensagemSucesso = this.leilaoLegadoService.deletar(id);
        if (!mensagemLegado.equalsIgnoreCase(legadoMensagemSucesso.getMensagem())) {
            throw new LeilaoVeiculosException("Erro ao deletar veiculo.");
        }
    }

    @Override
    public String alterarVeiculo(LeilaoVeiculo lance, String id) {
        buscarVeiculo(id);
        return leilaoLegadoService.alterar(leilaoLegadoMapper.mapAlterarLegado(lance, id));
    }

    @Override
    public LeilaoVeiculo buscarVeiculo(String id) {
        for (LeilaoVeiculo lance : leilaoLegadoMapper.mapToLeilao(this.leilaoLegadoService.consultar(), null)) {
            if (id.equals(lance.getId())) {
                return lance;
            }
        }
        throw new LeilaoVeiculosException("Veiculo não encontrado");
    }

    @Override
    public List<LeilaoVeiculo> buscarPorModelo(String letra, Integer offset, Integer limit, String sort) {
        List<LeilaoVeiculo> consultar = leilaoLegadoMapper.mapToLeilao(this.leilaoLegadoService.consultar(), sort);
        consultar = consultar.stream().filter(it -> buscarModeloPorLetra(it.getLote().getVeiculos().getModelo().getModelo(), letra))
                .collect(Collectors.toList());
        if (!consultar.isEmpty()) {
            return subList(consultar, offset, limit);
        }
        throw new LeilaoVeiculosException("Modelo não encontrado");
    }

    @Override
    public List<LeilaoVeiculo> buscarPorLote(String lote, Integer offset, Integer limit, String sort) {
        List<LeilaoVeiculo> consultar = leilaoLegadoMapper.mapToLeilao(this.leilaoLegadoService.consultar(), sort);
        consultar = consultar.stream().filter(it -> it.getLote().getIdLote().equalsIgnoreCase(lote)).collect(Collectors.toList());
        if (!consultar.isEmpty()) {
            return subList(consultar, offset, limit);
        }
        throw new LeilaoVeiculosException("Lote não encontrado");
    }

    @Override
    public List<LeilaoVeiculo> buscarPorMarca(String marca, Integer offset, Integer limit, String sort) {
        List<LeilaoVeiculo> consultar = leilaoLegadoMapper.mapToLeilao(this.leilaoLegadoService.consultar(), sort);
        consultar = consultar.stream().filter(it -> it.getLote().getVeiculos().getMarca().getMarca().equalsIgnoreCase(marca))
                .collect(Collectors.toList());
        if (!consultar.isEmpty()) {
            return subList(consultar, offset, limit);
        }
        throw new LeilaoVeiculosException("Lote não encontrado");
    }

    @Override
    public List<LeilaoVeiculo> buscarTodos(Integer offset, Integer limit, String sort) {
        List<LeilaoVeiculo> consultar = leilaoLegadoMapper.mapToLeilao(this.leilaoLegadoService.consultar(), sort);
        if (consultar.isEmpty()) {
            throw new LeilaoVeiculosException("Veiculo não encontrado");
        }
        return subList(consultar, offset, limit);
    }

    @Override
    public List<LeilaoVeiculo> buscarPorFabricacaoModelo(Integer anoFabricao, Integer anoModelo, Integer offset, Integer limit, String sort) {
        return leilaoLegadoMapper.buscarPorFabricacaoModelo(this.leilaoLegadoService.consultar(), anoFabricao, anoModelo, sort);
    }

    @Override
    public List<LeilaoVeiculo> buscarPorFabricacaoFaixa(Integer faxaInicial, Integer faxaFinal, Integer offset, Integer limit, String sort) {
        return leilaoLegadoMapper.buscarPorFabricacaoInicialFinal(this.leilaoLegadoService.consultar(), faxaInicial, faxaFinal, sort);
    }


    List<LeilaoVeiculo> subList(List<LeilaoVeiculo> lista, Integer offset, Integer limit) {
        if (lista.size() <= offset) {
            throw new LeilaoVeiculosException("Veiculo não encontrado");
        }
        Integer calculo = lista.size() - offset;
        if (calculo >= limit) {
            return lista.subList(offset, offset + limit);
        }
        return lista.subList(offset, lista.size());
    }

    Boolean buscarModeloPorLetra(String modelo, String letra) {
        if (letra.length() > modelo.length()) {
            return Boolean.FALSE;
        }
        String substring = modelo.substring(0, letra.length());
        return substring.equalsIgnoreCase(letra) ? Boolean.TRUE : Boolean.FALSE;
    }


}
