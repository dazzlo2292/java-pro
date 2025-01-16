package ru.otus.java.pro.serialization.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.java.pro.serialization.processors.MessageInfo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class StatisticSerializer {
    private String filePath;

    public void serialize(SerializationFormat format, ObjectMapper objectMapper, Map<String, List<MessageInfo>> statistic) {
        checkFormat(format);

        try (FileWriter writer = new FileWriter(filePath, false)) {
            objectMapper.writeValue(writer, statistic);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkFormat(SerializationFormat format) {
        switch (format) {
            case JSON:
                filePath = "hw16-serialization/src/main/resources/result.json";
                break;
            case XML:
                filePath = "hw16-serialization/src/main/resources/result.xml";
                break;
        }
    }
}
