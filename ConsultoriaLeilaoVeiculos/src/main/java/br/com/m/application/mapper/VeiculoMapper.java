package br.com.m.application.mapper;

import br.com.m.application.controller.request.LeilaoRequest;
import br.com.m.application.controller.response.VeiculoResponse;
import org.mapstruct.Mapper;

@Mapper
public interface VeiculoMapper {

    VeiculoResponse mapToReponse(LeilaoRequest.VeiculoRequest veiculoRequest);

}
