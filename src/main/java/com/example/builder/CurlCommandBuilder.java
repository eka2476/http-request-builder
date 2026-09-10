package com.example.builder;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Concrete builder #2: assembles the very same construction steps into
 * a text representation of the request - a ready-to-run cURL command
 * (representation 2).
 */
public class CurlCommandBuilder implements HttpRequestBuilder {

    private static final String CURL_COMMAND = "curl";
    private static final String METHOD_FLAG = "-X";
    private static final String HEADER_FLAG = "-H";
    private static final String DATA_FLAG = "-d";
    private static final String LINE_CONTINUATION = " \\\n  ";

    private HttpMethod method;
    private String url;
    private final Map<String, String> headers = new LinkedHashMap<>();
    private String body;

    @Override
    public CurlCommandBuilder setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    @Override
    public CurlCommandBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public CurlCommandBuilder addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    @Override
    public CurlCommandBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    /**
     * Validates the accumulated state and renders it as a cURL command.
     */
    public String getResult() {
        if (method == null) {
            throw new InvalidRequestException("HTTP method is required");
        }
        if (url == null || url.isBlank()) {
            throw new InvalidRequestException("URL is required and cannot be blank");
        }

        StringBuilder command = new StringBuilder(CURL_COMMAND)
                .append(LINE_CONTINUATION)
                .append(METHOD_FLAG).append(' ').append(method)
                .append(LINE_CONTINUATION)
                .append(quote(url));

        for (Map.Entry<String, String> header : headers.entrySet()) {
            command.append(LINE_CONTINUATION)
                    .append(HEADER_FLAG).append(' ')
                    .append(quote(header.getKey() + ": " + header.getValue()));
        }

        if (body != null && !body.isBlank()) {
            command.append(LINE_CONTINUATION)
                    .append(DATA_FLAG).append(' ')
                    .append(quote(body));
        }

        return command.toString();
    }

    private String quote(String value) {
        return "'" + value.replace("'", "'\\''") + "'";
    }
}
