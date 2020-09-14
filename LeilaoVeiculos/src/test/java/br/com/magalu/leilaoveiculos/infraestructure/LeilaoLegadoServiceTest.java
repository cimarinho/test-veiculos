package br.com.magalu.leilaoveiculos.infraestructure;

import br.com.magalu.leilaoveiculos.infraestructure.dto.LeilaoLegadoDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LeilaoLegadoServiceTest {

    @InjectMocks
    private LeilaoLegadoService application;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void criar() {
        when(restTemplate.postForObject(any(), any(), any())).thenReturn(new ResponseEntity(new HashMap<>(), HttpStatus.OK));
        assertTrue(application.criar(LeilaoLegadoDto.builder().build()).isEmpty());
    }


}
