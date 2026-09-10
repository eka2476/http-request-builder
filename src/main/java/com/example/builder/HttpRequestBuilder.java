package com.example.builder;

/**
 * Builder interface shared by every concrete builder.
 *
 * Fluent API: every step returns the builder itself so calls can be
 * chained (b.setMethod(...).setUrl(...).addHeader(...)).
 *
 * getResult() is intentionally NOT declared here: the two concrete
 * builders produce different result types (HttpRequest vs String),
 * so each of them exposes its own getResult() with its own return type.
 * The Director only ever needs the steps declared in this interface.
 */
public interface HttpRequestBuilder {

    HttpRequestBuilder setMethod(HttpMethod method);

    HttpRequestBuilder setUrl(String url);

    HttpRequestBuilder addHeader(String name, String value);

    HttpRequestBuilder setBody(String body);
}
