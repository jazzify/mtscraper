package com.jazzify.mtscraper.output;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TextOutputWriter extends OutputWriter {
    public TextOutputWriter(String outputLocation, String outputFileName) {
        super(outputLocation, outputFileName);
    }

    public String processOutputText(String[] textLines, boolean overrideFile) {
        this.createOutputDirectory();

        String outputFilePath = this.outputLocation + this.outputFileName;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, !overrideFile))) {
            for (String line : textLines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
        return this.outputLocation + this.outputFileName;
    }
}
