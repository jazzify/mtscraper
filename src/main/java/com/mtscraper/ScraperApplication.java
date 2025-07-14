package com.mtscraper;

import com.mtscraper.core.ScraperEngine;
import com.mtscraper.output.OutputText;

import picocli.CommandLine;

import java.util.concurrent.Callable;

public class ScraperApplication implements Callable<Integer> {
    @CommandLine.Option(
        names={"-u", "--urls"},
        split=",",
        description="pipe-separated urls",
        required=true
    )
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

            OutputText outputText = new OutputText("text/", "output.txt");
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
