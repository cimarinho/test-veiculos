package br.com.m.leilaoveiculos.infraestructure;

import br.com.m.leilaoveiculos.infraestructure.dto.LegadoMensagemSucesso;
import br.com.m.leilaoveiculos.infraestructure.dto.LeilaoLegadoDto;
import br.com.m.leilaoveiculos.infraestructure.dto.LeilaoLegadoResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class LeilaoLegadoService {

    @Value("${magalu.url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    public String criar(LeilaoLegadoDto leilaoLegadoDto) {
        Object retornoLegado = restTemplate.postForObject(url, leilaoLegadoDto, Object.class);
        return retornoLegado!= null ? (String) (((LinkedHashMap) retornoLegado).get("ID")) : "";
    }

    public String alterar(LeilaoLegadoDto leilaoLegadoDto) {
        Object retornoLegado = restTemplate.postForObject(url, leilaoLegadoDto, Object.class);
        return retornoLegado!= null ? (String) (((LinkedHashMap) retornoLegado).get("ID")) : "";
    }

    public List<LeilaoLegadoResponseDto> consultar() {
        LeilaoLegadoDto legado = LeilaoLegadoDto.builder().operacao("CONSULTAR").build();
        List<LeilaoLegadoResponseDto> lista = new ArrayList<>();
        Object[] retornoLegado = restTemplate.postForObject(url, legado, Object[].class);
        if (retornoLegado!= null){
            for (int i = 0; i < retornoLegado.length; i++) {
                LeilaoLegadoResponseDto leilaoLegadoResponseDto = LeilaoLegadoResponseDto.builder()
                        .id((String) ((LinkedHashMap) retornoLegado[i]).get("ID"))
                        .dataLance((String) ((LinkedHashMap) retornoLegado[i]).get("DATALANCE"))
                        .lote((String) ((LinkedHashMap) retornoLegado[i]).get("LOTE"))
                        .codigoControle((String) ((LinkedHashMap) retornoLegado[i]).get("CODIGOCONTROLE"))
                        .marca((String) ((LinkedHashMap) retornoLegado[i]).get("MARCA"))
                        .modelo((String) ((LinkedHashMap) retornoLegado[i]).get("MODELO"))
                        .anoFabricacao((Integer) ((LinkedHashMap) retornoLegado[i]).get("ANOFABRICACAO"))
                        .anoModelo((Integer) ((LinkedHashMap) retornoLegado[i]).get("ANOMODELO"))
                        .valorLance((Double) ((LinkedHashMap) retornoLegado[i]).get("VALORLANCE"))
                        .usuarioLance((String) ((LinkedHashMap) retornoLegado[i]).get("USUARIOLANCE"))
                        .build();
                lista.add(leilaoLegadoResponseDto);
            }
        }
        return lista;
    }

    public LegadoMensagemSucesso deletar(String id) {
        LeilaoLegadoDto legado = LeilaoLegadoDto.builder().operacao("DELETAR")
                .veiculo(LeilaoLegadoDto.VeiculoLegadoRequest.builder().id(id).build())
                .build();
        return restTemplate.postForObject(url, legado, LegadoMensagemSucesso.class);
    }


}
