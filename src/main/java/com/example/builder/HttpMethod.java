package com.example.builder;

/**
 * Supported HTTP methods.
 * Using an enum instead of raw strings removes "magic strings"
 * such as "GET" / "POST" scattered across the codebase.
 */
public enum HttpMethod {
    GET,
    POST,
    PUT,
    PATCH,
    DELETE
}
