package ru.otus.java.pro.serialization.deserialization;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class JsonSmsDeserializer {
    public JsonSmsData deserialize(ObjectMapper objectMapper, File file) throws IOException {
        try(InputStream in = new FileInputStream(file)) {
            return objectMapper.readValue(in, JsonSmsData.class);
        }
    }
}
