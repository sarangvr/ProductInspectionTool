package com.ensat.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

public class CustomObjectMapper {

    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Register the JavaTimeModule
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        // Register LocalDate serializer and deserializer with custom date format
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));

        // Register the modules with the ObjectMapper
        objectMapper.registerModule(javaTimeModule);

        return objectMapper;
    }
}
