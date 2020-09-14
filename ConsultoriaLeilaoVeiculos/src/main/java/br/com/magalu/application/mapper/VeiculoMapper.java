package br.com.magalu.application.mapper;

import br.com.magalu.application.controller.request.LeilaoRequest;
import br.com.magalu.application.controller.response.VeiculoResponse;
import org.mapstruct.Mapper;

@Mapper
public interface VeiculoMapper {

    VeiculoResponse mapToReponse(LeilaoRequest.VeiculoRequest veiculoRequest);

}
