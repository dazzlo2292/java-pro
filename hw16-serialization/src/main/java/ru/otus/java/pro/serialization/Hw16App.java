package ru.otus.java.pro.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ru.otus.java.pro.serialization.deserialization.JsonSmsData;
import ru.otus.java.pro.serialization.deserialization.JsonSmsDeserializer;
import ru.otus.java.pro.serialization.deserialization.StatisticDeserializer;
import ru.otus.java.pro.serialization.processors.DataProcessor;
import ru.otus.java.pro.serialization.processors.MessageInfo;
import ru.otus.java.pro.serialization.serialization.JsonStatisticSerializer;
import ru.otus.java.pro.serialization.serialization.XmlStatisticSerializer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Hw16App {
    public static void main(String[] args) throws IOException {
        String FILE_PATH = "hw16-serialization/src/main/resources/sms.json";
        String JSON_FILE_RESULT_PATH = "hw16-serialization/src/main/resources/result_json.json";
        String XML_FILE_RESULT_PATH = "hw16-serialization/src/main/resources/result_xml.xml";

        //JSON
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);

        JsonSmsDeserializer jsonSmsDeserializer = new JsonSmsDeserializer();
        JsonSmsData jsonData = jsonSmsDeserializer.deserialize(jsonMapper, FILE_PATH);

        DataProcessor dataProcessor = new DataProcessor();
        Map<String, List<MessageInfo>> statistic = dataProcessor.process(jsonData);

        JsonStatisticSerializer jsonStatisticSerializer = new JsonStatisticSerializer();
        jsonStatisticSerializer.serialize(jsonMapper, statistic);

        StatisticDeserializer statisticDeserializer = new StatisticDeserializer();
        Map<String, List<MessageInfo>> jsonResult = statisticDeserializer.deserialize(jsonMapper, JSON_FILE_RESULT_PATH);

        System.out.println(jsonResult);

        //XML
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        XmlStatisticSerializer xmlStatisticSerializer = new XmlStatisticSerializer();
        xmlStatisticSerializer.serialize(xmlMapper, statistic);
/// Если убрать комментарий, то будет ошибка! Почему именно для XML?
//        Map<String, List<MessageInfo>> xmlResult = statisticDeserializer.deserialize(xmlMapper, XML_FILE_RESULT_PATH);

//        System.out.println(xmlResult);
    }
}
