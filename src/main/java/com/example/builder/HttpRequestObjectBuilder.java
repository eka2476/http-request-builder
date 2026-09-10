package com.example.builder;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Concrete builder #1: assembles the same construction steps into a
 * real, immutable {@link HttpRequest} object (representation 1).
 */
public class HttpRequestObjectBuilder implements HttpRequestBuilder {

    private HttpMethod method;
    private String url;
    private final Map<String, String> headers = new LinkedHashMap<>();
    private String body;

    @Override
    public HttpRequestObjectBuilder setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    @Override
    public HttpRequestObjectBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public HttpRequestObjectBuilder addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    @Override
    public HttpRequestObjectBuilder setBody(String body) {
        this.body = body;
        return this;
    }

    /**
     * Validates the accumulated state and returns the final product.
     * Throws a clear exception on invalid input instead of returning
     * a half-built or null object.
     */
    public HttpRequest getResult() {
        if (method == null) {
            throw new InvalidRequestException("HTTP method is required");
        }
        if (url == null || url.isBlank()) {
            throw new InvalidRequestException("URL is required and cannot be blank");
        }
        return new HttpRequest(method, url, headers, body);
    }
}
