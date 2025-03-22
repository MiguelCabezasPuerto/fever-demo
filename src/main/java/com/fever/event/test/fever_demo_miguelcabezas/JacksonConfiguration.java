package com.fever.event.test.fever_demo_miguelcabezas;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.openapitools.jackson.nullable.JsonNullableModule;

@Configuration
public class JacksonConfiguration {
    @Bean
    public JsonNullableModule jsonNullableModule() {
        return new JsonNullableModule();
    }
}
