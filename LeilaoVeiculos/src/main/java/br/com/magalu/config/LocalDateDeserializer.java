package br.com.magalu.config;

import br.com.magalu.config.exception.LeilaoVeiculosException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateDeserializer extends StdDeserializer<LocalDateTime> {

    protected LocalDateDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        try {
            String str = parser.readValueAs(String.class);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
            return dateTime;
        } catch (Exception e) {
            throw new LeilaoVeiculosException("Data Lance invalida");
        }

    }
}