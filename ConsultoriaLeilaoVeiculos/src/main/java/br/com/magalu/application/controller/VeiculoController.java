package br.com.magalu.application.controller;

import br.com.magalu.application.controller.request.LeilaoRequest;
import br.com.magalu.application.controller.response.Mensagem;
import br.com.magalu.application.controller.response.VeiculoResponse;
import br.com.magalu.application.mapper.VeiculoMapper;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class VeiculoController {

    static List<VeiculoResponse> lista = new ArrayList<>();

    private VeiculoMapper veiculoMapper = Mappers.getMapper(VeiculoMapper.class);

    @PostMapping(value = "/veiculos")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity create(@RequestBody LeilaoRequest request) {
        if ("CRIAR".equalsIgnoreCase(request.getOperacao())) {
            VeiculoResponse veiculoResponse = veiculoMapper.mapToReponse(request.getVeiculo());
            UUID uuid = UUID.randomUUID();
            veiculoResponse.setId(uuid.toString());
            lista.add(veiculoResponse);
            return ResponseEntity.ok(veiculoResponse);
        } else if ("CONSULTAR".equalsIgnoreCase(request.getOperacao())) {
            return ResponseEntity.ok(lista);
        } else if ("DELETAR".equalsIgnoreCase(request.getOperacao())) {
            String id = request.getVeiculo().getId();
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getId().equalsIgnoreCase(id)) {
                    lista.remove(i);
                    return ResponseEntity.ok(Mensagem.builder().mensagem("sucesso").build());
                }
            }
            return ResponseEntity.ok(Mensagem.builder().mensagem("Id não existe").build());
        } else if ("ALTERAR".equalsIgnoreCase(request.getOperacao())) {
            String id = request.getVeiculo().getId();
            for (int i = 0; i < lista.size(); i++) {
                if (lista.get(i).getId().equalsIgnoreCase(id)) {
                    lista.remove(i);
                    VeiculoResponse veiculoResponse = veiculoMapper.mapToReponse(request.getVeiculo());
                    veiculoResponse.setId(id);
                    lista.add(veiculoResponse);
                    return ResponseEntity.ok(veiculoResponse);
                }
            }
            return ResponseEntity.ok(Mensagem.builder().mensagem("Id não existe").build());
        }
        throw new RuntimeException("Operação não existe");
    }
}













