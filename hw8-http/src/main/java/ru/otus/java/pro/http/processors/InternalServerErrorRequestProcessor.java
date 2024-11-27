package ru.otus.java.pro.http.processors;

import ru.otus.java.pro.http.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class InternalServerErrorRequestProcessor implements RequestProcessor{
    @Override
    public void execute(HttpRequest httpRequest, OutputStream out) throws IOException {
        String response = """
                    HTTP/1.1 500 Internal Server Error
                    Content-type: text/html
                    
                    <html><body><h1>Internal Server Error</h1></body></html>""";

        out.write(response.getBytes(StandardCharsets.UTF_8));
    }
}