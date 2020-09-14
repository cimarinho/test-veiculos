package br.com.m.leilaoveiculos.presentation;

import br.com.m.config.exception.LeilaoVeiculosException;
import br.com.m.leilaoveiculos.application.LeilaoVeiculoApplication;
import br.com.m.leilaoveiculos.application.domain.LeilaoVeiculo;
import br.com.m.leilaoveiculos.presentation.interfaces.AbstractController;
import br.com.m.leilaoveiculos.presentation.mapper.LeilaoVeiculoRequestMapper;
import br.com.m.leilaoveiculos.presentation.request.LeilaoVeiculoRequest;
import br.com.m.leilaoveiculos.presentation.response.LeilaoVeiculoResponse;
import br.com.m.leilaoveiculos.presentation.response.LinkResponse;
import br.com.m.leilaoveiculos.presentation.response.ListLeilaVeiculoResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@Validated
@Api(value = "Leilao de Veiculos")
@RequestMapping("/v1/leilaoVeiculos")
@Slf4j
public class LeilaoVeiculoController implements AbstractController {

    @Autowired
    private LeilaoVeiculoApplication leilaoVeiculoApplication;

    private LeilaoVeiculoRequestMapper lanceVeiculoRequestMapper = Mappers.getMapper(LeilaoVeiculoRequestMapper.class);

    @Value("${magalu.limit}")
    private Integer maxLimit;

    @PostMapping
    @ApiOperation(value = "Inclusao de veiculos.")
    public ResponseEntity<LinkResponse> criar(@RequestBody @Valid LeilaoVeiculoRequest request) {
        if (leilaoVeiculoApplication.existeCodigoControle(request.getLote().getVeiculos().getCodigoControle())) {
            throw new LeilaoVeiculosException("Codigo Controle deve ser unico.");
        }
        String id = leilaoVeiculoApplication.cadastrarVeiculo(lanceVeiculoRequestMapper.mapToLance(request));
        return retornaLink(String.format("%s/%s", ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString(), id), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Alterar veiculo por id.")
    public ResponseEntity<LeilaoVeiculoResponse> alterar(@NotEmpty(message = "Id deve ser fornecido") @PathVariable(value = "id") String id,
                                                         @RequestBody @Valid LeilaoVeiculoRequest request) {
        leilaoVeiculoApplication.alterarVeiculo(lanceVeiculoRequestMapper.mapToLance(request), id);
        return retornaLink(String.format("%s", ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString()), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Deletar veiculo por id.")
    public ResponseEntity<ListLeilaVeiculoResponse> deletar(@NotEmpty(message = "Id deve ser fornecido") @PathVariable(value = "id") String id) {
        leilaoVeiculoApplication.deletarVeiculo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Buscar veiculo por id.")
    public ResponseEntity<LeilaoVeiculoResponse> buscar(@NotEmpty(message = "Id deve ser fornecido") @PathVariable(value = "id") String id) {
        LeilaoVeiculo lanceVeiculo = this.leilaoVeiculoApplication.buscarVeiculo(id);
        LeilaoVeiculoResponse lanceVeiculoResponse = this.lanceVeiculoRequestMapper.mapToResponse(lanceVeiculo);
        return retorna(lanceVeiculoResponse);
    }

    @GetMapping
    @ApiOperation(value = "Buscar Todos os veiculos. Tamanho maximo da lista é 5 registro por request, caso o limit recebido for maior será retornado o valor padrão")
    public ResponseEntity<ListLeilaVeiculoResponse> buscarTodos(@RequestParam(value = "offset", defaultValue = "${magalu.offset}") Integer offset,
                                                                @RequestParam(value = "limit", defaultValue = "${magalu.limit}") Integer limit,
                                                                @RequestParam(value = "sort", defaultValue = "${magalu.sort}") String sort) {
        limit = validarSize(limit, offset);
        List<LeilaoVeiculo> veiculoResponse = leilaoVeiculoApplication.buscarTodos(offset, limit, sort);
        return retorna(veiculoResponse, offset, limit);
    }

    @GetMapping(value = "/lote/{lote}")
    @ApiOperation(value = "Buscar veiculos por lote. Tamanho maximo da lista é 5 registro por request, caso o limit recebido for maior será retornado o valor padrão")
    public ResponseEntity<ListLeilaVeiculoResponse> buscarLote(@RequestParam(value = "offset", defaultValue = "${magalu.offset}") Integer offset,
                                                               @RequestParam(value = "limit", defaultValue = "${magalu.limit}") Integer limit,
                                                               @RequestParam(value = "sort", defaultValue = "${magalu.sort}") String sort,
                                                               @PathVariable(value = "lote") String lote) {
        limit = validarSize(limit, offset);
        List<LeilaoVeiculo> veiculoResponse = leilaoVeiculoApplication.buscarPorLote(lote, offset, limit, sort);
        return retorna(veiculoResponse, offset, limit);
    }

    @GetMapping(value = "/marca/{marca}")
    @ApiOperation(value = "Buscar veiculos por marca. Tamanho maximo da lista é 5 registro por request, caso o limit recebido for maior será retornado o valor padrão")
    public ResponseEntity<ListLeilaVeiculoResponse> buscarMarca(@RequestParam(value = "offset", defaultValue = "${magalu.offset}") Integer offset,
                                                                @RequestParam(value = "limit", defaultValue = "${magalu.limit}") Integer limit,
                                                                @RequestParam(value = "sort", defaultValue = "${magalu.sort}") String sort,
                                                                @PathVariable(value = "marca") String marca) {
        limit = validarSize(limit, offset);
        List<LeilaoVeiculo> veiculoResponse = leilaoVeiculoApplication.buscarPorMarca(marca, offset, limit, sort);
        return retorna(veiculoResponse, offset, limit);
    }

    @GetMapping(value = "/modelo")
    @ApiOperation(value = "Buscar veiculos pela letra inicial do modelo. Tamanho maximo da lista é 5 registro por request, caso o limit recebido for maior será retornado o valor padrão")
    public ResponseEntity<ListLeilaVeiculoResponse> buscarModelo(@RequestParam(value = "offset", defaultValue = "${magalu.offset}") Integer offset,
                                                                 @RequestParam(value = "limit", defaultValue = "${magalu.limit}") Integer limit,
                                                                 @RequestParam(value = "sort", defaultValue = "${magalu.sort}") String sort,
                                                                 @ApiParam(value = "letra deve ser fornecido", required = true) @RequestParam(value = "letra") String letra) {
        limit = validarSize(limit, offset);
        List<LeilaoVeiculo> veiculoResponse = leilaoVeiculoApplication.buscarPorModelo(letra, offset, limit, sort);
        return retorna(veiculoResponse, offset, limit);
    }


    @GetMapping(value = "/fabricacao/{anoFabricao}/modelo/{anoModelo}")
    @ApiOperation(value = "Buscar veiculos pelo ano de fabricação e pelo ano do modelo. Tamanho maximo da lista é 5 registro por request, caso o limit recebido for maior será retornado o valor padrão")
    public ResponseEntity<ListLeilaVeiculoResponse> buscarModelo(@RequestParam(value = "offset", defaultValue = "${magalu.offset}") Integer offset,
                                                                 @RequestParam(value = "limit", defaultValue = "${magalu.limit}") Integer limit,
                                                                 @RequestParam(value = "sort", defaultValue = "${magalu.sort}") String sort,
                                                                 @PathVariable(value = "anoFabricao") Integer anoFabricao,
                                                                 @PathVariable(value = "anoModelo") Integer anoModelo) {
        limit = validarSize(limit, offset);
        List<LeilaoVeiculo> veiculoResponse = leilaoVeiculoApplication.buscarPorFabricacaoModelo(anoFabricao, anoModelo, offset, limit, sort);
        return retorna(veiculoResponse, offset, limit);
    }

    @GetMapping(value = "/fabricacao")
    @ApiOperation(value = "Buscar veiculos pela faixa inicial e final do ano de fabricação. Tamanho maximo da lista é 5 registro por request, caso o limit recebido for maior será retornado o valor padrão")
    public ResponseEntity<ListLeilaVeiculoResponse> buscarPorFabricao(@RequestParam(value = "offset", defaultValue = "${magalu.offset}") Integer offset,
                                                                      @RequestParam(value = "limit", defaultValue = "${magalu.limit}") Integer limit,
                                                                      @RequestParam(value = "sort", defaultValue = "${magalu.sort}") String sort,
                                                                      @RequestParam(value = "faixaInicial") Integer faixaInicial,
                                                                      @RequestParam(value = "faixaFinal") Integer faixaFinal) {
        limit = validarSize(limit, offset);
        List<LeilaoVeiculo> veiculoResponse = leilaoVeiculoApplication.buscarPorFabricacaoFaixa(faixaInicial, faixaFinal, offset, limit, sort);
        return retorna(veiculoResponse, offset, limit);
    }

    Integer validarSize(Integer paramSize, Integer offset) {
        if (offset < 0) {
            throw new LeilaoVeiculosException("Offset invalido");
        }
        return paramSize > this.maxLimit ? this.maxLimit : paramSize;
    }

    ResponseEntity<ListLeilaVeiculoResponse> retorna(List<LeilaoVeiculo> veiculoResponse, Integer offset, Integer limit) {
        List<LeilaoVeiculoResponse> lanceVeiculoResponses = this.lanceVeiculoRequestMapper.mapToResponse(veiculoResponse);
        return retorna(ListLeilaVeiculoResponse.builder().totalRegistro(veiculoResponse.get(0).getTotalRegistro())
                .veiculos(lanceVeiculoResponses).build(), offset, limit);
    }
}