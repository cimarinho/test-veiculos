package br.com.m.leilaoveiculos.presentation.mapper;

import br.com.m.leilaoveiculos.application.domain.LeilaoVeiculo;
import br.com.m.leilaoveiculos.presentation.request.LeilaoVeiculoRequest;
import br.com.m.leilaoveiculos.presentation.response.LeilaoVeiculoResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface LeilaoVeiculoRequestMapper {

    LeilaoVeiculo mapToLance(LeilaoVeiculoRequest lanceVeiculoRequest);

    List<LeilaoVeiculoResponse> mapToResponse(List<LeilaoVeiculo> lanceVeiculo);

    LeilaoVeiculoResponse mapToResponse(LeilaoVeiculo lanceVeiculo);

}
