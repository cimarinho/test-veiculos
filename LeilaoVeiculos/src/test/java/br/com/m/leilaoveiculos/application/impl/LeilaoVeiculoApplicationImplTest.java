package br.com.m.leilaoveiculos.application.impl;

import br.com.m.config.exception.LeilaoVeiculosException;
import br.com.m.leilaoveiculos.application.domain.LeilaoVeiculo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class LeilaoVeiculoApplicationImplTest {

    @InjectMocks
    private LeilaoVeiculoApplicationImpl application;


    @Test
    public void calcular() {
        List<LeilaoVeiculo> lista = Arrays.asList(new LeilaoVeiculo[11]);
        for (int i = 0; i < 10; i++) {
            List<LeilaoVeiculo> calcular = application.subList(lista, i, 5);
            assertNotNull(calcular);
        }
    }

    @Test(expected = LeilaoVeiculosException.class)
    public void calcular2() {
        List<LeilaoVeiculo> lista = Arrays.asList(new LeilaoVeiculo[11]);
        Integer offset = 11;
        application.subList(lista, offset, 5);

    }

    @Test
    public void buscarModeloPorLetra() {
        assertTrue(application.buscarModeloPorLetra("TESTE", "T"));
        assertTrue(application.buscarModeloPorLetra("TESTE", "TE"));
        assertTrue(application.buscarModeloPorLetra("TESTE", "TES"));
        assertTrue(application.buscarModeloPorLetra("TESTE", "TEST"));
        assertTrue(application.buscarModeloPorLetra("TESTE", "TESTE"));
        assertFalse(application.buscarModeloPorLetra("TESTE", "TESTE1"));

    }


}
