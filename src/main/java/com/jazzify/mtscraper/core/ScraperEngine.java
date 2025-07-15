package com.jazzify.mtscraper.core;

import java.net.URI;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.jazzify.mtscraper.http.HttpClientManager;

public class ScraperEngine {
    private final String[] urls;
    private final HttpClientManager httpClient = new HttpClientManager();

    public ScraperEngine(String[] urls) {
        this.urls = urls;
    }

    public String[] scrapeUrls() {
        List<URI> failedUris = new ArrayList<URI>();
        List<URI> visitedUris = new ArrayList<URI>();
        List<String> results = new ArrayList<String>();

        for (String url : this.urls) {
            try {
                URI uri = URI.create(url);
                if (visitedUris.contains(uri)) {
                    System.out.println("Skipping already visited URL: " + url);
                    continue;
                }
                System.out.println("Processing URL: " + url);
                HttpResponse<String> response = httpClient.get(uri);
                if (response.statusCode() != 200) {
                    failedUris.add(uri);
                }
                System.out.println("Successfully scraped URL: " + response.body());
                visitedUris.add(uri);
                results.add(response.body());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to scrape URL: " + url, e);
            }
        }
        return results.toArray(new String[results.size()]);
    }
}
