package ru.otus.java.pro.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.pro.http.processors.*;

import java.io.IOException;
import java.io.OutputStream;

public class Dispatcher {
    private static final Logger logger = LogManager.getLogger(Dispatcher.class.getName());

    private final RequestProcessor rootRequestProcessor;
    private final RequestProcessor internalServerRequestProcessor;
    private final RequestProcessor shutdownRequestProcessor;

    public Dispatcher(Server server) {
        this.rootRequestProcessor = new RootRequestProcessor();
        this.internalServerRequestProcessor = new InternalServerErrorRequestProcessor();
        this.shutdownRequestProcessor = new ShutdownRequestProcessor(server);
    }

    public void execute(HttpRequest httpRequest, OutputStream out) throws IOException {
        try {
            if (httpRequest.getMethod().equals(HttpMethod.GET) && httpRequest.getUri().equals("/shutdown")) {
                shutdownRequestProcessor.execute(httpRequest, out);
                return;
            }
            rootRequestProcessor.execute(httpRequest, out);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            internalServerRequestProcessor.execute(httpRequest, out);
        }
    }
}
