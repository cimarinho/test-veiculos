package br.com.magalu.leilaoveiculos.presentation.mapper;

import br.com.magalu.leilaoveiculos.application.domain.LeilaoVeiculo;
import br.com.magalu.leilaoveiculos.presentation.request.LeilaoVeiculoRequest;
import br.com.magalu.leilaoveiculos.presentation.response.LeilaoVeiculoResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface LeilaoVeiculoRequestMapper {

    LeilaoVeiculo mapToLance(LeilaoVeiculoRequest lanceVeiculoRequest);

    List<LeilaoVeiculoResponse> mapToResponse(List<LeilaoVeiculo> lanceVeiculo);

    LeilaoVeiculoResponse mapToResponse(LeilaoVeiculo lanceVeiculo);

}
