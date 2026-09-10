# HTTP Request Builder

Assignment 1 - Builder design pattern (Java).

Product: an **HTTP request**. The same construction steps (method, URL,
headers, body) are used to produce **two different representations**:

1. `HttpRequest` - an immutable object you could actually pass to an HTTP
   client.
2. A ready-to-run **cURL command** (`String`) - a text preview of the very
   same request.

## Why this pattern fits here

Building a request step by step (method → URL → headers → body) is exactly
the kind of multi-step, optional-parts construction the Builder pattern is
for. A single "God constructor" with 4+ parameters (some optional, like
`body`) would be error-prone and hard to read at the call site. The Builder
pattern also lets the *same* sequence of steps be replayed against two
different builders to get two different outputs, without duplicating the
step logic.

## Structure

```
HttpRequestBuilder            <<interface>>  (fluent steps: setMethod, setUrl, addHeader, setBody)
        ^
        |-- HttpRequestObjectBuilder  -> getResult(): HttpRequest   (representation 1)
        |-- CurlCommandBuilder        -> getResult(): String        (representation 2)

RequestDirector   -- depends only on HttpRequestBuilder
                  -- makeGetJsonRequest(builder, url)
                  -- makeAuthenticatedPostRequest(builder, url, token, jsonBody)

HttpRequest       -- the immutable Product (private constructor, final fields, no setters)
```

`getResult()` is declared on each **concrete** builder, not on the shared
interface, because the two products have different types (`HttpRequest`
vs `String`). The Director never calls `getResult()` - it only drives the
shared steps declared in `HttpRequestBuilder`.

## Requirements checklist

- Fluent API - every step returns the builder (`return this;`).
- `HttpRequestBuilder` interface + two concrete builders producing two
  different representations.
- `RequestDirector` with two ready configurations, depending only on the
  builder interface.
- `HttpRequest` is immutable: package-private constructor, `final` fields,
  no public setters, defensive copy of the headers map.
- `getResult()` validates state and throws `InvalidRequestException`
  (a clear, specific exception) on invalid input.
- No magic numbers/strings - header names, flags, and defaults are named
  constants (`HttpMethod` enum, `CONTENT_TYPE_HEADER`, `METHOD_FLAG`, etc.).

## Build & run

```bash
mvn compile exec:java
```

## Run tests

```bash
mvn test
```

## Sample output

```
=== HttpRequest object ===
HttpRequest{method=POST, url='https://api.example.com/orders', headers={Content-Type=application/json, Authorization=Bearer eyJhbGciOi...}, body='{"item":"keyboard","qty":1}'}

=== cURL command ===
curl \
  -X POST \
  'https://api.example.com/orders' \
  -H 'Content-Type: application/json' \
  -H 'Authorization: Bearer eyJhbGciOi...' \
  -d '{"item":"keyboard","qty":1}'
```

## Package layout

```
src/main/java/com/example/builder/
    HttpMethod.java
    InvalidRequestException.java
    HttpRequest.java
    HttpRequestBuilder.java
    HttpRequestObjectBuilder.java
    CurlCommandBuilder.java
    RequestDirector.java
    Main.java
src/test/java/com/example/builder/
    HttpRequestBuilderTest.java
```
