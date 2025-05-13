package com.tekton.challenge.support;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class JsonTestUtils {


    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);

    public static <T> T readJsonFromFile(String path, Class<T> clazz) throws IOException {
        InputStream is = JsonTestUtils.class.getClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new FileNotFoundException("Resource not found: " + path);
        }
        return objectMapper.readValue(is, clazz);
    }

    public static <T> T readJsonFromFile(String path, TypeReference<T> typeRef) throws IOException {
        InputStream is = JsonTestUtils.class.getClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new FileNotFoundException("Resource not found: " + path);
        }
        return objectMapper.readValue(is, typeRef);
    }

}
