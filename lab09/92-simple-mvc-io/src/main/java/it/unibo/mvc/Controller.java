package it.unibo.mvc;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import javax.swing.JOptionPane;

/**
 * Application controller. Performs the I/O.
 */
public class Controller {

    private static String DEFAULT_CURRENT_FILE_PATH = System.getProperty("user.home") + File.separator + "output.txt";
    private File currentFile = new File(DEFAULT_CURRENT_FILE_PATH);

    public Controller() { }

    /**
     * Sets the current file the application should consider
     * 
     * @param cf file to set
     */
    public void setCurrentFile(final File cf) {
        this.currentFile = Objects.requireNonNull(cf, "Trying to set a null file!");
    }

    /**
     * Gets the current file the application is considering
     * 
     * @return the current file
     */
    public File getCurrentFile() {
        return this.currentFile;
    }

    /**
     * Gets the path of the current file, in form of a String
     * 
     * @return the path of the current file, in form of a String
     */
    public String getCurrentFilePath() {
        return this.currentFile.getPath();
    }

    /**
     * Writes something on the current file.
     * 
     * @param content what should be written.
     * @throws IOException if the PrintStream could not be opened, or something unexpected happens.
     */
    public void writeContent(final String content) throws IOException {
        try (PrintStream ps = new PrintStream(getCurrentFilePath(), StandardCharsets.UTF_8)) {
            ps.print(content);
        }
    }
}