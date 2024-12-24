package ru.otus.java.pro.http;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.pro.http.exceptions.HttpFormatException;
import ru.otus.java.pro.http.exceptions.RequestSizeException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final Logger logger = LogManager.getLogger(HttpRequest.class.getName());

    private String host;
    private String endpoint;
    private HttpMethod method;
    private StringBuffer body;
    private Map<String, String> parameters;
    private Map<String, String> headers;

    private int requestContentLength = 0;
    private int maxContentLength;

    public HttpRequest(InputStream request, int maxContentLength) {
        try {
            this.maxContentLength = maxContentLength;
            parse(request);
        } catch (IOException e) {
            logger.error("Error while parsing request: ", e);
        }
    }

    private void parse(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String firstLine = readLine(reader);
        while (true) {
            if (firstLine != null) {
                setMethod(firstLine);
                break;
            }
            firstLine = readLine(reader);
        }

        this.headers = new HashMap<>();
        String header = readLine(reader);
        while (!header.isEmpty()) {
            if (header.split(": ")[0].equals("Host")) {
                setHost(header);
            }
            addHeaderParameter(header);
            header = readLine(reader);
        }

        if (method != HttpMethod.GET && method != HttpMethod.HEAD) {
            this.body = new StringBuffer();
            String bodyLine = readLine(reader);
            while (bodyLine != null) {
                appendMessageBody(bodyLine);
                bodyLine = readLine(reader);
            }
        }

        logger.info("\nMethod: {}\nHost: {}\nEndpoint: {}\nHeaders: {}\nParameters: {}\nBody: \n{}", method, host, endpoint, headers, parameters, body);
    }

    private String readLine(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        if (line != null) {
            requestContentLength += line.getBytes().length;
            if (requestContentLength > maxContentLength) {
                throw new RequestSizeException("Request size > " + maxContentLength);
            }
            return line;
        }
        return null;
    }

    private void setMethod(String requestLine) {
        String[] parts = requestLine.split(" ");
        this.method = HttpMethod.valueOf(parts[0]);
        if (parts[1].contains("?")) {
            this.parameters = new HashMap<>();
            setParams(parts[1]);
        } else {
            this.endpoint = parts[1];
        }
    }

    private void setParams(String paramsLine) {
        this.parameters = new HashMap<>();
        String[] uriParts = paramsLine.split("[?]");
        setEndpoint(uriParts[0]);
        String rawParams = uriParts[1];
        String[] differentParams = rawParams.split("&");
        for (String param : differentParams) {
            int index = param.indexOf("=");
            this.parameters.put(param.substring(0, index), param.substring(index + 1));
        }
    }

    private void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    private void setHost(String requestLine) {
        this.host= requestLine.substring(requestLine.indexOf(" "));
    }

    private void addHeaderParameter(String header) {
        int index = header.indexOf(":");
        if (index == -1) {
            throw new HttpFormatException("Invalid Header Parameter: " + header);
        }
        headers.put(header.substring(0, index), header.substring(index + 1).trim());
    }

    private void appendMessageBody(String bodyLine) {
        body.append(bodyLine).append("\r\n");
    }

    public String getHost() {
        return host;
    }

    public HttpMethod getMethod() {
        return method;
    }
}
