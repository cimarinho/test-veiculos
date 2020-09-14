package br.com.m.leilaoveiculos.application.domain;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class LeilaoVeiculo {

    private String id;
    private Integer totalRegistro;
    private LocalDateTime dataLance;
    private Double valorLance;
    private String usuarioLance;
    private Lote lote;

    @Getter
    @Builder
    public static class Lote {
        private LeilaoVeiculo lanceVeiculo;
        private String idLote;
        private Veiculo veiculos;
    }

    @Getter
    @Builder
    public static class Veiculo {
        private String codigoControle;
        private Integer anoFabricacao;
        private Lote lote;
        private Modelo modelo;
        private Marca marca;
    }

    @Getter
    @Builder
    public static class Marca {
        private String marca;
        private List<Veiculo> veiculos;
    }

    @Getter
    @Builder
    public static class Modelo {
        private String modelo;
        private Integer ano;
        private List<Veiculo> veiculos;
    }

}
