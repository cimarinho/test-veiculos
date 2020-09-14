package br.com.m.template.controller;

import br.com.m.leilaoveiculos.presentation.request.LeilaoVeiculoRequest;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class VeiculoRequestTemplate implements TemplateLoader {

    public static final String VEICULOREQUEST = "VEICULOREQUEST";

    @Override
    public void load() {
        Fixture.of(LeilaoVeiculoRequest.VeiculoRequest.class).addTemplate(VEICULOREQUEST, new Rule() {{
            add("codigoControle", "1123");
            add("anoFabricacao", 1995);
            add("marca", one(LeilaoVeiculoRequest.MarcaRequest.class, MarcaRequestTemplate.MARCAREQUEST));
            add("modelo", one(LeilaoVeiculoRequest.ModeloRequest.class, ModeloRequestTemplate.MODELOREQUEST));
        }});
    }
}
