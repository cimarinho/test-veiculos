package br.com.m.template.controller;

import br.com.m.leilaoveiculos.presentation.request.LeilaoVeiculoRequest;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.time.LocalDateTime;

public class LeilaoVeiculoRequestTemplate implements TemplateLoader {

    public static final String LEILAOVEICULOREQUEST = "LEILAOVEICULOREQUEST";
    public static final String LEILAOVEICULOREQUEST400 = "LEILAOVEICULOREQUEST400";

    @Override
    public void load() {
        Fixture.of(LeilaoVeiculoRequest.class).addTemplate(LEILAOVEICULOREQUEST, new Rule() {{
            add("dataLance", LocalDateTime.now());
            add("valorLance", Double.valueOf(200.22));
            add("usuarioLance", "Joao");
            add("lote",one(LeilaoVeiculoRequest.LoteRequest.class,  LoteRequestTemplate.LOTEREQUEST));
        }});

        Fixture.of(LeilaoVeiculoRequest.class).addTemplate(LEILAOVEICULOREQUEST400, new Rule() {{
            add("dataLance", LocalDateTime.now());
            add("valorLance", Double.valueOf(200.22));
            add("usuarioLance", "Joao");
        }});
    }
}


