package com.example.builder;

/**
 * Director: knows the recipes (the order and combination of steps)
 * for common requests, but knows nothing about how those steps are
 * actually rendered. It depends only on the {@link HttpRequestBuilder}
 * interface, so any current or future builder can be driven through it.
 */
public class RequestDirector {

    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String JSON_CONTENT_TYPE = "application/json";
    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    /**
     * Builds a request that fetches a JSON resource.
     */
    public void makeGetJsonRequest(HttpRequestBuilder builder, String url) {
        builder.setMethod(HttpMethod.GET)
                .setUrl(url)
                .addHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE);
    }

    /**
     * Builds a request that creates a resource with an authenticated,
     * JSON-encoded payload.
     */
    public void makeAuthenticatedPostRequest(HttpRequestBuilder builder,
                                              String url,
                                              String bearerToken,
                                              String jsonBody) {
        builder.setMethod(HttpMethod.POST)
                .setUrl(url)
                .addHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
                .addHeader(AUTH_HEADER, BEARER_PREFIX + bearerToken)
                .setBody(jsonBody);
    }
}
