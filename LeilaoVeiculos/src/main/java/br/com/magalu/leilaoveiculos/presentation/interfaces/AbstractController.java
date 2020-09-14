package br.com.magalu.leilaoveiculos.presentation.interfaces;

import br.com.magalu.leilaoveiculos.presentation.response.LeilaoVeiculoResponse;
import br.com.magalu.leilaoveiculos.presentation.response.LinkResponse;
import br.com.magalu.leilaoveiculos.presentation.response.ListLeilaVeiculoResponse;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.UUID;

public interface AbstractController {

    default ResponseEntity retornaLink(String url, HttpStatus httpStatus) {
        LinkResponse link = new LinkResponse();
        link.add(new Link(url).withSelfRel());
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("correlationId", UUID.randomUUID().toString());
        return new ResponseEntity<>(link, headers, httpStatus);
    }

    default ResponseEntity retorna(LeilaoVeiculoResponse t1) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("correlationId", UUID.randomUUID().toString());
        return new ResponseEntity<>(t1, headers, HttpStatus.OK);
    }

    default ResponseEntity retorna(ListLeilaVeiculoResponse response, Integer offset, Integer limit) {
        String url = String.format("%s?offset=%d&limit=%s", ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString(), offset, limit);
        response.add(new Link(url).withSelfRel());

        Integer incrementOffset = offset + limit ;

        if (offset < response.getTotalRegistro() && incrementOffset < response.getTotalRegistro()) {
            Integer valueOffset =  offset + limit ;
            url = String.format("%s?offset=%d&limit=%s", ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString(), valueOffset, limit);
            Link nextLink = new Link(url).withRel("next");
            response.add(nextLink);
        }

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("correlationId", UUID.randomUUID().toString());
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}
