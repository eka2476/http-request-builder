package com.example.builder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpRequestBuilderTest {

    private final RequestDirector director = new RequestDirector();

    @Test
    void objectBuilderProducesCorrectRequest() {
        HttpRequestObjectBuilder builder = new HttpRequestObjectBuilder();
        director.makeGetJsonRequest(builder, "https://api.example.com/users/1");

        HttpRequest request = builder.getResult();

        assertEquals(HttpMethod.GET, request.getMethod());
        assertEquals("https://api.example.com/users/1", request.getUrl());
        assertEquals("application/json", request.getHeaders().get("Content-Type"));
    }

    @Test
    void curlBuilderProducesRunnableCommand() {
        CurlCommandBuilder builder = new CurlCommandBuilder();
        director.makeAuthenticatedPostRequest(
                builder, "https://api.example.com/orders", "token123", "{\"a\":1}");

        String command = builder.getResult();

        assertTrue(command.startsWith("curl"));
        assertTrue(command.contains("-X POST"));
        assertTrue(command.contains("Authorization: Bearer token123"));
        assertTrue(command.contains("{\"a\":1}"));
    }

    @Test
    void sameStepsProduceTwoConsistentRepresentations() {
        HttpRequestObjectBuilder objectBuilder = new HttpRequestObjectBuilder();
        CurlCommandBuilder curlBuilder = new CurlCommandBuilder();

        director.makeGetJsonRequest(objectBuilder, "https://api.example.com/ping");
        director.makeGetJsonRequest(curlBuilder, "https://api.example.com/ping");

        assertEquals("https://api.example.com/ping", objectBuilder.getResult().getUrl());
        assertTrue(curlBuilder.getResult().contains("https://api.example.com/ping"));
    }

    @Test
    void missingUrlThrowsClearException() {
        HttpRequestObjectBuilder builder = new HttpRequestObjectBuilder();
        builder.setMethod(HttpMethod.GET);

        InvalidRequestException exception =
                assertThrows(InvalidRequestException.class, builder::getResult);

        assertTrue(exception.getMessage().toLowerCase().contains("url"));
    }

    @Test
    void missingMethodThrowsClearException() {
        CurlCommandBuilder builder = new CurlCommandBuilder();
        builder.setUrl("https://api.example.com");

        assertThrows(InvalidRequestException.class, builder::getResult);
    }
}
