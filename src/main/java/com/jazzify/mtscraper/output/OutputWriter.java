package com.jazzify.mtscraper.output;

public abstract class OutputWriter {
    protected String outputLocation;
    protected String outputFileName;

    public OutputWriter(String outputLocation, String outputFileName) {
        this.outputLocation = "tmp/scraped_results/" + outputLocation;
        this.outputFileName = outputFileName;
    }

    public abstract String processOutputText(String[] urls, boolean overrideFile);

    protected void createOutputDirectory() {
        java.io.File dir = new java.io.File(this.outputLocation);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
