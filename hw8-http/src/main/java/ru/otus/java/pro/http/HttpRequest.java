package ru.otus.java.pro.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final Logger logger = LogManager.getLogger(HttpRequest.class.getName());

    private final String request;
    private String uri;
    private HttpMethod method;
    private String body;
    private Map<String, String> parameters;
    private Map<String, String> headers;

    public HttpRequest(String request) {
        this.request = request;
        parse(request);
    }

    public String getUri() {
        return uri;
    }

    public HttpMethod getMethod() {
        return method;
    }

    private void parse(String rawRequest) {
        int startIndex = rawRequest.indexOf(" ");
        int endIndex = rawRequest.indexOf(' ', startIndex + 1);
        this.method = HttpMethod.valueOf(rawRequest.substring(0, startIndex));
        this.uri = rawRequest.substring(startIndex + 1, endIndex);
        this.parameters = new HashMap<>();
        this.headers = new HashMap<>();

        int startHeadersIndex = rawRequest.indexOf("\r\n") + 2;
        int endHeadersIndex = rawRequest.indexOf("\r\n\r\n");
        String allHeaders = rawRequest.substring(startHeadersIndex, endHeadersIndex);
        String[] differentHeaders = allHeaders.split("\r\n");
        for (String header : differentHeaders) {
            String headerKey;
            String headerValue;
            String[] keyValue = header.split(":",2);
            headerKey = keyValue[0].trim();
            headerValue = keyValue[1].trim();
            headers.put(headerKey, headerValue);
        }

        if (uri.contains("?")) {
            String[] rawParams = uri.split("[?]");
            this.uri = rawParams[0];
            String[] differentParams = rawParams[1].split("&");
            for (String param : differentParams)
            {
                String[] keyValue = param.split("=");
                this.parameters.put(keyValue[0], keyValue[1]);
            }
        }
        if (this.method == HttpMethod.POST) {
            this.body = rawRequest.substring(rawRequest.indexOf("\r\n\r\n") + 4);
        }

        logger.info("\nMethod: {}\nURI: {}\nBody: \n{}", method, uri, body);
        logger.debug(request);
    }
}
