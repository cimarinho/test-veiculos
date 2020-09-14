package br.com.magalu.template.controller;

import br.com.magalu.leilaoveiculos.presentation.request.LeilaoVeiculoRequest;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class ModeloRequestTemplate implements TemplateLoader {

    public static final String MODELOREQUEST = "ModeloRequest";

    @Override
    public void load() {
        Fixture.of(LeilaoVeiculoRequest.ModeloRequest.class).addTemplate(MODELOREQUEST, new Rule() {{
            add("modelo", "Palio");
            add("ano", 2011);
        }});
    }
}