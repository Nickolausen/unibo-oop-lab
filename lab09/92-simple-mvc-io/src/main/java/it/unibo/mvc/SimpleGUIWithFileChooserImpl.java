package it.unibo.mvc;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooserImpl {

    private static final String TITLE = "My second java graphical interface";
    private static final int PROPORTION = 5;
    private final JFrame frame = new JFrame(TITLE);
    private final Controller controller;

    /**
     * Default constructor.
     */
    public SimpleGUIWithFileChooserImpl() {
        this.controller = new Controller();
        setupView();
    }

    /**
     * Initializes the elements in the view.
     */
    private void setupView() {
        final JPanel canvas = new JPanel();
        canvas.setLayout(new BorderLayout());
        final JTextArea textArea = new JTextArea();
        final JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    controller.writeContent(textArea.getText());
                } catch (final IOException ex) {
                    JOptionPane.showMessageDialog(frame, ex, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        final JPanel horizontalTopPanel = new JPanel();
        horizontalTopPanel.setLayout(new BorderLayout());
        final JTextField displayFilePath = new JTextField();
        displayFilePath.setEditable(false);
        final JButton browseFile = new JButton("Browse...");
        browseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Only .txt files", "txt"));
                final int fileChooserResult = fileChooser.showSaveDialog(canvas);
                switch (fileChooserResult) {
                    case JFileChooser.APPROVE_OPTION:
                    controller.setCurrentFile(fileChooser.getSelectedFile());
                    displayFilePath.setText(controller.getCurrentFilePath());
                    break;

                    case JFileChooser.CANCEL_OPTION:
                    break;

                    case JFileChooser.ERROR_OPTION:
                    JOptionPane.showMessageDialog(frame, "Something went wrong!", "Error", JOptionPane.ERROR_MESSAGE);
                    break;

                    default:
                    break;
                } 
            }
        });
        horizontalTopPanel.add(displayFilePath, BorderLayout.CENTER);
        horizontalTopPanel.add(browseFile, BorderLayout.LINE_END);
        canvas.add(horizontalTopPanel, BorderLayout.NORTH);
        canvas.add(textArea, BorderLayout.CENTER);
        canvas.add(save, BorderLayout.SOUTH);
        this.frame.add(canvas);
    }

    /**
     * Renders the main frame.
     */
    public void display() {
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        frame.setSize(sw / PROPORTION, sh / PROPORTION);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    /**
     * Runs the application.
     * 
     * @param args ignored
     */
    public static void main(final String... args) {
        new SimpleGUIWithFileChooserImpl().display();
    }
}
