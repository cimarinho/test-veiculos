package br.com.magalu.leilaoveiculos.infraestructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import java.io.Serializable;

@Builder
@Getter
public class LeilaoLegadoResponseDto implements Serializable {
    @JsonProperty("ID")
    private String id;
    @JsonProperty("DATALANCE")
    private String dataLance;
    @JsonProperty("LOTE")
    private String lote;
    @JsonProperty("CODIGOCONTROLE")
    private String codigoControle;
    @JsonProperty("MARCA")
    private String marca;
    @JsonProperty("MODELO")
    private String modelo;
    @JsonProperty("ANOFABRICACAO")
    private Integer anoFabricacao;
    @JsonProperty("ANOMODELO")
    private Integer anoModelo;
    @JsonProperty("VALORLANCE")
    private Double valorLance;
    @JsonProperty("USUARIOLANCE")
    private String usuarioLance;
}
