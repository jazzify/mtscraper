package com.mtscraper.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpClientManager {
    private final HttpClient httpClient;
    private final Duration connectionTimeout = Duration.ofSeconds(10);

    public HttpClientManager() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(connectionTimeout)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();
    }

    public HttpResponse<String> get(URI uri) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .build();
        try {
            return this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("HTTP request failed for URL: " + uri.toString(), e.getCause());
        }
    }
}
