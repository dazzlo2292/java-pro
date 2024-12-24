package ru.otus.java.pro.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class RequestHandler {
    private static final Logger logger = LogManager.getLogger(RequestHandler.class.getName());

    private final Socket connection;
    private final OutputStream out;
    private final InputStream in;

    public RequestHandler(ExecutorService pool, Socket connection, Dispatcher dispatcher, int maxRequestSize) throws IOException {
        this.connection = connection;
        this.out = connection.getOutputStream();
        this.in = connection.getInputStream();

        pool.execute(()-> {
            try {
                HttpRequest httpRequest = new HttpRequest(in, maxRequestSize);

                dispatcher.execute(httpRequest, out);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            } finally {
                disconnect();
            }
        });
    }

    private void disconnect() {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }

        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }

        try {
            connection.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}