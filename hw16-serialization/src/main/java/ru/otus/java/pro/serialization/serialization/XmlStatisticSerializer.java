package ru.otus.java.pro.serialization.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.java.pro.serialization.processors.MessageInfo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class XmlStatisticSerializer {
    public void serialize(ObjectMapper objectMapper, Map<String, List<MessageInfo>> statistic) {
        try (FileWriter writer = new FileWriter("hw16-serialization/src/main/resources/result_xml.xml", false))
        {
            String xml = objectMapper.writeValueAsString(statistic);
            writer.write(xml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
