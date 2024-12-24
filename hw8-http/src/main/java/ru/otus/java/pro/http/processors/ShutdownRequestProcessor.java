package ru.otus.java.pro.http.processors;

import ru.otus.java.pro.http.HttpRequest;
import ru.otus.java.pro.http.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ShutdownRequestProcessor implements RequestProcessor{
    Server server;

    public ShutdownRequestProcessor(Server server) {
        this.server = server;
    }

    @Override
    public void execute(HttpRequest httpRequest, OutputStream out) throws IOException {
        try {
            server.setActive(false);
            String response = """
                    HTTP/1.1 200 OK
                    Content-type: text/html
                    
                    <html><body><h1>Server shutdown</h1></body></html>""";

            out.write(response.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
