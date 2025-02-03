package ru.otus.java.pro.serialization.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.java.pro.serialization.processors.MessageInfo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class StatisticSerializer {
    public void serialize(SerializationFormat format, ObjectMapper objectMapper, Map<String, List<MessageInfo>> statistic) {
        try (FileWriter writer = new FileWriter(format.getFilePath(), false)) {
            objectMapper.writeValue(writer, statistic);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
