# HTTP Request Builder

Assignment 1 - Builder pattern in Java.

Product: HTTP request. Two builders make two different things from the same steps:
- `HttpRequestObjectBuilder` -> `HttpRequest` object
- `CurlCommandBuilder` -> cURL command as a string

`RequestDirector` has two ready configs (GET, authenticated POST).

`HttpRequest` is immutable, `getResult()` throws `InvalidRequestException` if method/url are missing.

## Run

```
mvn compile exec:java
```

## Test

```
mvn test
```

## Example output

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
