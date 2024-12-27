package ru.otus.java.pro.http.exceptions;

public class RequestSizeException extends RuntimeException {
    public RequestSizeException(String message) {
        super(message);
    }
}
