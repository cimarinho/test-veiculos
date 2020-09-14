package br.com.m.application.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeilaoRequest {

    @JsonProperty("OPERACAO")
    private String operacao;
    @JsonProperty("VEICULO")
    private VeiculoRequest veiculo;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VeiculoRequest {
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

}
