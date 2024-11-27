package ru.otus.java.pro.http.processors;

import ru.otus.java.pro.http.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;

public interface RequestProcessor {
    void execute(HttpRequest httpRequest, OutputStream out) throws IOException;
}
