package br.com.m.leilaoveiculos.presentation.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Builder
public class ListLeilaVeiculoResponse extends RepresentationModel {

    @JsonIgnore
    private Integer totalRegistro;

    private List<LeilaoVeiculoResponse> veiculos;

}
