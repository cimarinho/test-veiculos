package br.com.magalu.leilaoveiculos.application.mapper;

import br.com.magalu.config.exception.LeilaoVeiculosException;
import br.com.magalu.leilaoveiculos.application.domain.LeilaoVeiculo;
import br.com.magalu.leilaoveiculos.infraestructure.dto.LeilaoLegadoDto;
import br.com.magalu.leilaoveiculos.infraestructure.dto.LeilaoLegadoResponseDto;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class LeilaoLegadoMapper {

    public LeilaoLegadoDto mapAlterarLegado(LeilaoVeiculo lance, String id) {
        LeilaoLegadoDto.VeiculoLegadoRequest veiculos = LeilaoLegadoDto.VeiculoLegadoRequest.builder()
                .id(id)
                .anoFabricacao(lance.getLote().getVeiculos().getAnoFabricacao())
                .anoModelo(lance.getLote().getVeiculos().getModelo().getAno())
                .codigoControle(lance.getLote().getVeiculos().getCodigoControle())
                .lote(lance.getLote().getIdLote())
                .dataLance(transformDataLegado(lance.getDataLance()))
                .usuarioLance(lance.getUsuarioLance())
                .marca(lance.getLote().getVeiculos().getMarca().getMarca())
                .valorLance(lance.getValorLance())
                .dataLance(transformDataLegado(lance.getDataLance()))
                .modelo(lance.getLote().getVeiculos().getModelo().getModelo())
                .build();
        return LeilaoLegadoDto.builder().operacao("ALTERAR").veiculo(veiculos).build();
    }

    public LeilaoLegadoDto mapCriarLegado(LeilaoVeiculo lance) {
        LeilaoLegadoDto.VeiculoLegadoRequest veiculos = LeilaoLegadoDto.VeiculoLegadoRequest.builder()
                .anoFabricacao(lance.getLote().getVeiculos().getAnoFabricacao())
                .anoModelo(lance.getLote().getVeiculos().getModelo().getAno())
                .codigoControle(lance.getLote().getVeiculos().getCodigoControle())
                .lote(lance.getLote().getIdLote())
                .dataLance(transformDataLegado(lance.getDataLance()))
                .usuarioLance(lance.getUsuarioLance())
                .marca(lance.getLote().getVeiculos().getMarca().getMarca())
                .valorLance(lance.getValorLance())
                .dataLance(transformDataLegado(lance.getDataLance()))
                .modelo(lance.getLote().getVeiculos().getModelo().getModelo())
                .build();
        return LeilaoLegadoDto.builder().operacao("CRIAR").veiculo(veiculos).build();
    }

    public List<LeilaoVeiculo> buscarPorFabricacaoInicialFinal(List<LeilaoLegadoResponseDto> leilaoLegado, Integer faixaInicial, Integer faixaFinal, String sort) {
        List<LeilaoLegadoResponseDto> lista = new ArrayList<>();
        leilaoLegado.forEach(itera -> {
            if (itera.getAnoFabricacao() >= faixaInicial && itera.getAnoFabricacao() <= faixaFinal) {
                lista.add(itera);
            }
        });
        if (lista.isEmpty()) {
            throw new LeilaoVeiculosException("AnoFabricacao/Modelo do modelo não encontrado");
        }
        return mapToLeilao(lista, sort);
    }

    public List<LeilaoVeiculo> buscarPorFabricacaoModelo(List<LeilaoLegadoResponseDto> leilaoLegado, Integer anoFabricacao, Integer anoModelo, String sort) {
        List<LeilaoLegadoResponseDto> lista = new ArrayList<>();
        leilaoLegado.forEach(itera -> {
            if (itera.getAnoFabricacao().equals(anoFabricacao) && itera.getAnoModelo().equals(anoModelo)) {
                lista.add(itera);
            }
        });
        if (lista.isEmpty()) {
            throw new LeilaoVeiculosException("AnoFabricacao/Modelo do modelo não encontrado");
        }
        return mapToLeilao(lista, sort);
    }


    public List<LeilaoVeiculo> mapToLeilao(List<LeilaoLegadoResponseDto> leilaoLegado, String sort) {
        List<LeilaoVeiculo> lanceVeiculos = new ArrayList<>();
        leilaoLegado.forEach(itera -> {
            LeilaoVeiculo.Lote veiculos = LeilaoVeiculo.Lote.builder().idLote(itera.getLote()).veiculos(LeilaoVeiculo.Veiculo.builder()
                    .codigoControle(itera.getCodigoControle())
                    .anoFabricacao(itera.getAnoFabricacao())
                    .marca(LeilaoVeiculo.Marca.builder().marca(itera.getMarca()).build())
                    .modelo(LeilaoVeiculo.Modelo.builder().modelo(itera.getModelo()).ano(itera.getAnoModelo()).build())
                    .build()).build();
            lanceVeiculos.add(LeilaoVeiculo.builder()
                    .id(itera.getId())
                    .totalRegistro(leilaoLegado.size())
                    .dataLance(transformDataLegado(itera.getDataLance()))
                    .valorLance(itera.getValorLance())
                    .usuarioLance(itera.getUsuarioLance())
                    .lote(veiculos)
                    .build());
        });
        if ("desc".equals(sort)) {
            lanceVeiculos.sort(Comparator.comparing(LeilaoVeiculo::getDataLance).reversed());
        } else {
            lanceVeiculos.sort(Comparator.comparing(LeilaoVeiculo::getDataLance));
        }
        return lanceVeiculos;
    }

    LocalDateTime transformDataLegado(String dateTime) {
        dateTime = dateTime.replace("- ", "");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm");
        return LocalDateTime.parse(dateTime, formatter);
    }

    String transformDataLegado(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy - HH:mm");
        return formatter.format(dateTime);
    }
}
