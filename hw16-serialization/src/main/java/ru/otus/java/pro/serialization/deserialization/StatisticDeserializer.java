package ru.otus.java.pro.serialization.deserialization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.java.pro.serialization.processors.MessageInfo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class StatisticDeserializer {
    public Map<String, List<MessageInfo>> deserialize(ObjectMapper objectMapper, String filePath) throws IOException {
        File file = new File(filePath);
        return objectMapper.readValue(file, new TypeReference<>(){});
    }
}
