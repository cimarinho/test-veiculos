package br.com.m.leilaoveiculos.presentation.request;

import br.com.m.config.LocalDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class LeilaoVeiculoRequest {

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDateTime dataLance;
    @Min(1)
    @NotNull(message = "Valor Lance é um campo obrigatório")
    private Double valorLance;
    @NotNull(message = "Usuario Lance é um campo obrigatório")
    private String usuarioLance;
    @Valid
    @NotNull(message = "lote é obrigatorio")
    private LoteRequest lote;
    private String id;

    @Getter
    public static class LoteRequest {
        @NotNull(message = "Lote é um campo obrigatório")
        private String idLote;
        @Valid
        @NotNull(message = "veiculos é obrigatorio")
        private VeiculoRequest veiculos;
    }

    @Getter
    public static class VeiculoRequest {
        @NotNull(message = "Codigo Controle é um campo obrigatório")

        private String codigoControle;
        @NotNull(message = "Ano Fabricacao é um campo obrigatório")
        private Integer anoFabricacao;
        @Valid
        @NotNull(message = "marca é obrigatorio")
        private MarcaRequest marca;
        @Valid
        @NotNull(message = "modelo é obrigatorio")
        private ModeloRequest modelo;
    }

    @Getter
    public static class MarcaRequest {
        @NotNull(message = "Marca é um campo obrigatório")
        private String marca;
    }

    @Getter
    public static class ModeloRequest {
        @NotNull(message = "Modelo é um campo obrigatório")
        private String modelo;
        @NotNull(message = "Ano é um campo obrigatório")
        private Integer ano;
    }



}
