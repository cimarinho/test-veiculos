package br.com.magalu.leilaoveiculos.application;

import br.com.magalu.leilaoveiculos.application.domain.LeilaoVeiculo;

import java.util.List;

public interface LeilaoVeiculoApplication {

    String cadastrarVeiculo(LeilaoVeiculo lance);

    void deletarVeiculo(String id);

    String alterarVeiculo(LeilaoVeiculo lance, String id);

    LeilaoVeiculo buscarVeiculo(String id);

    List<LeilaoVeiculo> buscarPorModelo(String  letra, Integer offset, Integer limit, String sort );

    List<LeilaoVeiculo> buscarPorLote(String lote, Integer offset, Integer limit, String sort );

    List<LeilaoVeiculo> buscarPorMarca(String marca, Integer offset, Integer limit, String sort );

    List<LeilaoVeiculo> buscarTodos(Integer offset, Integer size, String sort);

    List<LeilaoVeiculo> buscarPorFabricacaoModelo(Integer anoFabricao, Integer anoModelo, Integer offset, Integer limit, String sort );

    List<LeilaoVeiculo> buscarPorFabricacaoFaixa(Integer faxaInicial, Integer faxaFinal, Integer offset, Integer limit, String sort );
}
