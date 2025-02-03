package ru.otus.java.pro.serialization.serialization;

import lombok.Getter;

@Getter
public enum SerializationFormat {
    JSON("hw16-serialization/src/main/resources/result.json"),
    XML("hw16-serialization/src/main/resources/result.xml");

    private final String filePath;

    SerializationFormat(String filePath) {
        this.filePath = filePath;
    }
}
