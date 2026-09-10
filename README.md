# HTTP Request Builder

Assignment 1 - Builder pattern in Java.

Product: HTTP request. Two builders make two different things from the same steps:
- HttpRequestObjectBuilder -> HttpRequest object
- CurlCommandBuilder -> cURL command as a string

RequestDirector has two ready configs (GET, authenticated POST).

HttpRequest is immutable, getResult() throws InvalidRequestException if method/url are missing.
