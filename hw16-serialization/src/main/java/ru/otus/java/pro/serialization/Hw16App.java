package ru.otus.java.pro.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import ru.otus.java.pro.serialization.deserialization.JsonSmsData;
import ru.otus.java.pro.serialization.deserialization.JsonSmsDeserializer;
import ru.otus.java.pro.serialization.deserialization.StatisticDeserializer;
import ru.otus.java.pro.serialization.processors.DataProcessor;
import ru.otus.java.pro.serialization.processors.MessageInfo;
import ru.otus.java.pro.serialization.serialization.SerializationFormat;
import ru.otus.java.pro.serialization.serialization.StatisticSerializer;

import java.io.*;
import java.util.List;
import java.util.Map;

public class Hw16App {
    public static void main(String[] args) throws IOException {
        String FILE_PATH = "hw16-serialization/src/main/resources/sms.json";
        String JSON_FILE_RESULT_PATH = "hw16-serialization/src/main/resources/result.json";

        //JSON
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);

        JsonSmsDeserializer jsonSmsDeserializer = new JsonSmsDeserializer();
        File file = new File(FILE_PATH);
        JsonSmsData jsonData = jsonSmsDeserializer.deserialize(jsonMapper, file);

        DataProcessor dataProcessor = new DataProcessor();
        Map<String, List<MessageInfo>> statistic = dataProcessor.process(jsonData);

        StatisticSerializer statisticSerializer = new StatisticSerializer();
        statisticSerializer.serialize(SerializationFormat.JSON, jsonMapper, statistic);

        StatisticDeserializer statisticDeserializer = new StatisticDeserializer();
        Map<String, List<MessageInfo>> jsonResult = statisticDeserializer.deserialize(jsonMapper, JSON_FILE_RESULT_PATH);

        System.out.println(jsonResult);

        //XML
        ObjectMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        statisticSerializer.serialize(SerializationFormat.XML, xmlMapper, statistic);
    }
}
