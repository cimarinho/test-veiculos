package br.com.magalu.leilaoveiculos.presentation;

import br.com.magalu.leilaoveiculos.CustomLocalDateTimeDeserializer;
import br.com.magalu.leilaoveiculos.presentation.request.LeilaoVeiculoRequest;
import br.com.magalu.template.controller.LeilaoVeiculoRequestTemplate;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LeilaoVeiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Before
    public void setup() {
        FixtureFactoryLoader.loadTemplates("br.com.magalu.template.controller");
    }

    @Test
    public void create() throws Exception {
        LeilaoVeiculoRequest request = Fixture.from(LeilaoVeiculoRequest.class).gimme(LeilaoVeiculoRequestTemplate.LEILAOVEICULOREQUEST);
        String json = getJson(request);
        mockMvc.perform(post("/leilaoVeiculos")
                .content(json)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.links", notNullValue()));
    }

    @Test
    public void create400() throws Exception {
        LeilaoVeiculoRequest request = Fixture.from(LeilaoVeiculoRequest.class).gimme(LeilaoVeiculoRequestTemplate.LEILAOVEICULOREQUEST400);
        String json = getJson(request);
        mockMvc.perform(post("/leilaoVeiculos")
                .content(json)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(400));
    }

    String getJson(LeilaoVeiculoRequest request) throws Exception {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new CustomLocalDateTimeDeserializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(module);
        return objectMapper.writeValueAsString(request);
    }
}
