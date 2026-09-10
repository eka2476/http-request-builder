package com.example.builder;

/**
 * Client: one Director, two different builders, two representations
 * of the very same request.
 */
public class Main {

    public static void main(String[] args) {
        RequestDirector director = new RequestDirector();

        // --- Representation 1: a real HttpRequest object ---------------
        HttpRequestObjectBuilder objectBuilder = new HttpRequestObjectBuilder();
        director.makeAuthenticatedPostRequest(
                objectBuilder,
                "https://api.example.com/orders",
                "eyJhbGciOi...",
                "{\"item\":\"keyboard\",\"qty\":1}"
        );
        HttpRequest request = objectBuilder.getResult();
        System.out.println("=== HttpRequest object ===");
        System.out.println(request);

        // --- Representation 2: a ready-to-run cURL command -------------
        CurlCommandBuilder curlBuilder = new CurlCommandBuilder();
        director.makeAuthenticatedPostRequest(
                curlBuilder,
                "https://api.example.com/orders",
                "eyJhbGciOi...",
                "{\"item\":\"keyboard\",\"qty\":1}"
        );
        String curlCommand = curlBuilder.getResult();
        System.out.println("\n=== cURL command ===");
        System.out.println(curlCommand);

        // --- A second, simpler configuration ----------------------------
        CurlCommandBuilder getBuilder = new CurlCommandBuilder();
        director.makeGetJsonRequest(getBuilder, "https://api.example.com/orders/42");
        System.out.println("\n=== cURL command (GET) ===");
        System.out.println(getBuilder.getResult());
    }
}
