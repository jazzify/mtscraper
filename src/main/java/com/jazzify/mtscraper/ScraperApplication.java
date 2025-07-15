package com.jazzify.mtscraper;

import java.util.concurrent.Callable;

import com.jazzify.mtscraper.core.ScraperEngine;
import com.jazzify.mtscraper.output.TextOutputWriter;

import picocli.CommandLine;

public class ScraperApplication implements Callable<Integer> {
    @CommandLine.Option(names = { "-u", "--urls" }, split = ",", description = "comma-separated urls", required = true)
    private String[] urls;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new ScraperApplication()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        this.validateUrls(this.urls);
        try {
            ScraperEngine scraperEngine = new ScraperEngine(this.urls);
            String[] scrapedData = scraperEngine.scrapeUrls();

            TextOutputWriter outputText = new TextOutputWriter("text/", "output.txt");
            String outputFilePath = outputText.processOutputText(scrapedData, true);

            System.out.println("Output written to: " + outputFilePath);
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    private void validateUrls(String[] urls) {
        for (String url : urls) {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                throw new IllegalArgumentException("Invalid URL: " + url);
            }
        }
    }
}
