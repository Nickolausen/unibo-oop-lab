package it.unibo.mvc;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Toolkit;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUI {

    private static final String TITLE = "My first java graphical interface";
    private static final int PROPORTION = 5;
    private final JFrame frame = new JFrame(TITLE);
    private final SimpleController controller;

    /**
    * Default constructor.
    */
    public SimpleGUI() {
        this.controller = new SimpleController();
        setupView();
    }

    /**
     * Initializes the elements in the view.
     */
    private void setupView() {
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JPanel canvas = new JPanel();
        canvas.setLayout(new BorderLayout());
        this.frame.add(canvas);
        final JTextField textField = new JTextField();
        canvas.add(textField, BorderLayout.NORTH);
        final JTextArea textArea = new JTextArea();
        canvas.add(textArea, BorderLayout.CENTER);
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        canvas.add(bottomPanel, BorderLayout.SOUTH);
        final JButton printButton = new JButton("Print");
        bottomPanel.add(printButton, BorderLayout.EAST);
        printButton.addActionListener((e) -> {
            this.controller.setNextString(textField.getText());
            try {
                this.controller.printCurrentString();
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(frame, ex, "Error!", JOptionPane.ERROR_MESSAGE);
            }
        });
        final JButton historyButton = new JButton("Show history");
        bottomPanel.add(historyButton, BorderLayout.WEST);
        historyButton.addActionListener((e) -> {
            // From the traditional toString() result for a list,
            // let's remove the first and the last '[]' + the middle ", "
            final String historyString = this.controller
                .getHistory()
                .toString()
                .replace(", ", "\n");
            textArea.setText(historyString.substring(1, historyString.length() - 1));
        });
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
        new SimpleGUI().display();
    }
}
