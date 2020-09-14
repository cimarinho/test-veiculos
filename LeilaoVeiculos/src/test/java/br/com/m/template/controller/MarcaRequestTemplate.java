package br.com.m.template.controller;

import br.com.m.leilaoveiculos.presentation.request.LeilaoVeiculoRequest;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class MarcaRequestTemplate implements TemplateLoader {

    public static final String MARCAREQUEST = "MARCAREQUEST";

    @Override
    public void load() {
        Fixture.of(LeilaoVeiculoRequest.MarcaRequest.class).addTemplate(MARCAREQUEST, new Rule() {{
            add("marca", "Fiat");
        }});
    }
}