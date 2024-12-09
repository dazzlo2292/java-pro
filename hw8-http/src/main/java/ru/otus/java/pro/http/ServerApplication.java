package ru.otus.java.pro.http;

public class ServerApplication {
    public static void main(String[] args) {
        Configuration config = new Configuration();
        new Server(config).start();
    }
}
