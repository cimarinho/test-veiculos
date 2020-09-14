package br.com.m.leilaoveiculos.presentation.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class LeilaoVeiculoResponse {

    @JsonFormat(pattern="dd/MM/yyyy hh:mm")
    private LocalDateTime dataLance;
    private String id;
    private Double valorLance;
    private String usuarioLance;
    private LoteResponse lote;

    @Builder
    @Getter
    public static class LoteResponse {
        private String idLote;
        private VeiculoResponse veiculos;
    }

    @Builder
    @Getter
    public static class VeiculoResponse  {
        private String codigoControle;
        private Integer anoFabricacao;
        private MarcaResponse marca;
        private ModeloResponse modelo;
    }

    @Builder
    @Getter
    public static class MarcaResponse  {
        private String marca;
    }

    @Builder
    @Getter
    public static  class ModeloResponse  {
        private String modelo;
        private Integer ano;
    }
}
