package ru.otus.java.pro.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Logger logger = LogManager.getLogger(Server.class.getName());

    private final int port;
    private final ExecutorService connectionsPool;
    private final int maxRequestSize;
    private final Dispatcher dispatcher;

    private boolean active;

    public Server(Configuration config) {
        this.port = Integer.parseInt(config.getProperty("port"));
        this.connectionsPool = Executors.newFixedThreadPool(Integer.parseInt(config.getProperty("maxThreadsCount")));
        this.maxRequestSize = Integer.parseInt(config.getProperty("maxRequestSize"));
        this.dispatcher = new Dispatcher(this);
        this.active = true;
    }

    public void start() {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Server started to port {}", port);
            while (active) {
                Socket connection = serverSocket.accept();
                new RequestHandler(connectionsPool, connection, dispatcher, maxRequestSize);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            connectionsPool.shutdown();
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}