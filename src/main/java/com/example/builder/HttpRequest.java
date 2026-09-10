package com.example.builder;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The Product of the Builder pattern.
 *
 * Immutable by design:
 *  - all fields are final;
 *  - the constructor is package-private, so instances can only be
 *    created from within this package (i.e. by a builder);
 *  - there are no public setters;
 *  - the headers map is copied and wrapped as unmodifiable, so external
 *    code cannot mutate the internal state through the getter.
 */
public final class HttpRequest {

    private final HttpMethod method;
    private final String url;
    private final Map<String, String> headers;
    private final String body;

    HttpRequest(HttpMethod method, String url, Map<String, String> headers, String body) {
        this.method = method;
        this.url = url;
        this.headers = Collections.unmodifiableMap(new LinkedHashMap<>(headers));
        this.body = body;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HttpRequest)) {
            return false;
        }
        HttpRequest that = (HttpRequest) other;
        return method == that.method
                && Objects.equals(url, that.url)
                && Objects.equals(headers, that.headers)
                && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, url, headers, body);
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "method=" + method +
                ", url='" + url + '\'' +
                ", headers=" + headers +
                ", body='" + body + '\'' +
                '}';
    }
}
