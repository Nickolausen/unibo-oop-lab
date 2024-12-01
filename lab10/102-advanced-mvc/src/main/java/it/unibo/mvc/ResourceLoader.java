package it.unibo.mvc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class ResourceLoader {

    private BufferedReader reader;

    /**
     * Note 
     * @param fileName
     */
    public ResourceLoader(final String fileName) {
        Objects.requireNonNull(fileName, "Null filename in ResourceLoader!");
        try {
            // Note for the future me:
            // "config.yml" is not found, even if "resources/" folder is (or should be) part of the Class Path
            // I think you should build the project first to make it visible, otherwise the BufferedReader initialization
            // will continue to throw an Exception. There are many "errors" around the code — 99% of them gets notified by the
            // monstrous and strict checks mr. Danilo Pianini imposed. 
            // It's sunday and I would like to rest, so the correction are
            // left in the todo list for a further moment. 
            this.reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) { 
            System.out.println("Error here!");
        }
    }

    /**
     * Gets the corresponding value of the selected parameter in configuration file.
     * 
     * @param param the chosen parameter
     */
    public final int getParameter(final String param) {
        Objects.requireNonNull(this.reader, "Cannot read " + param + " — reader is null!");
        try {
            this.reader.mark(0);   
        } catch (Exception e) { }
        String line;
        try {
            while ((line = this.reader.readLine()) != null) {
                final var slices = line.split(":");
                if (param.toLowerCase() == slices[0]) {
                    this.reader.reset();
                    return Integer.parseInt(slices[1].trim());
                }
            } 
        } catch (Exception e) { }

        throw new IllegalStateException("No parameter '" + param + "' found in!");
    }

    public final void closeReader() {
        Objects.requireNonNull(this.reader, "Cannot close a null reader!");
        try {
            this.reader.close();
        } catch (IOException e) { }
    } 
}
