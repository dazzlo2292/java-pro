package ru.otus.java.pro.serialization.deserialization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;


public class JsonSmsDeserializer {
    public JsonSmsData deserialize(ObjectMapper objectMapper, String filePath) throws IOException {
        File file = new File(filePath);
        return objectMapper.readValue(file, new TypeReference<>(){});
    }
}
