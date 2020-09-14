package br.com.m.template.controller;

import br.com.m.leilaoveiculos.presentation.request.LeilaoVeiculoRequest;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class LoteRequestTemplate implements TemplateLoader {

    public static final String LOTEREQUEST = "LOTEREQUEST";

    @Override
    public void load() {
        Fixture.of(LeilaoVeiculoRequest.LoteRequest.class).addTemplate(LOTEREQUEST, new Rule() {{
            add("idLote", "2121");
            add("veiculos", one(LeilaoVeiculoRequest.VeiculoRequest.class,  VeiculoRequestTemplate.VEICULOREQUEST));
        }});
    }
}

