package it.unibo.oop.reactivegui03;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Second example of reactive GUI.
 */
@SuppressWarnings("PMD.AvoidPrintStackTrace")
public final class AnotherConcurrentGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final double WIDTH_PERC = 0.2;
    private static final double HEIGHT_PERC = 0.1;
    private final JLabel display = new JLabel();
    private final JButton stop = new JButton("stop");
    private final JButton up = new JButton("up");
    private final JButton down = new JButton("down");
    private final Counter counter = new Counter(0);

    /**
     * Builds a new Concurrent GUI.
     */
    public AnotherConcurrentGUI() {
        super();
        final JPanel panel = new JPanel();
        panel.add(display);
        panel.add(up);
        panel.add(down);
        panel.add(stop);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) (screenSize.getWidth() * WIDTH_PERC), (int) (screenSize.getHeight() * HEIGHT_PERC));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().add(panel);
        this.setVisible(true);
        /*
         * Create the counter agent and start it. This is actually not so good:
         * thread management should be left to
         * java.util.concurrent.ExecutorService
         */
        final CounterAgent counterAgent = new CounterAgent(counter);
        final StopAgent stopAgent = new StopAgent(counterAgent);
        new Thread(counterAgent).start();
        new Thread(stopAgent).start();
        /*
         * Register a listener that stops it
         */
        stop.addActionListener((e) -> {
            counterAgent.stopCounting();
            stop.setEnabled(false);
            up.setEnabled(false);
            down.setEnabled(false);
        });
        /*
         * Register listeners to increment or decrement the counter
         */
        up.addActionListener((e) -> counterAgent.shouldIncrement());
        down.addActionListener((e) -> counterAgent.shouldDecrement());
    }

    private class CounterAgent implements Runnable {
        /*
         * Stop is volatile to ensure visibility. Look at:
         * 
         * http://archive.is/9PU5N - Sections 17.3 and 17.4
         * 
         * For more details on how to use volatile:
         * 
         * http://archive.is/4lsKW
         * 
         */
        private volatile boolean stop;
        private volatile boolean shouldIncrement = true;
        private final Counter internalCounter;

        CounterAgent(final Counter c) {
            this.internalCounter = Objects.requireNonNull(c, "Null counter set!");
        }

        @Override
        public void run() {
            while (!this.stop) {
                try {
                    if (shouldIncrement) { 
                        increment();
                    } else {
                        decrement();
                    }
                } catch (InvocationTargetException | InterruptedException ex) {
                    /*
                     * This is just a stack trace print, in a real program there
                     * should be some logging and decent error reporting
                     */
                    ex.printStackTrace();
                }
            }
        }

        private void increment() throws InvocationTargetException, InterruptedException {
            // The EDT doesn't access `counter` anymore, it doesn't need to be volatile 
            final var nextText = Integer.toString(this.internalCounter.getValue());
            SwingUtilities.invokeAndWait(() -> AnotherConcurrentGUI.this.display.setText(nextText));
            this.internalCounter.increment();
            Thread.sleep(100);
        }

        private void decrement() throws InvocationTargetException, InterruptedException {
            // The EDT doesn't access `counter` anymore, it doesn't need to be volatile 
            final var nextText = Integer.toString(this.internalCounter.getValue());
            SwingUtilities.invokeAndWait(() -> AnotherConcurrentGUI.this.display.setText(nextText));
            this.internalCounter.decrement();
            Thread.sleep(100);
        }

        /**
         * External command to stop counting.
         */
        public synchronized void stopCounting() {
            this.stop = true;
        }

        /**
         * External command to increment counter.
         */
        public void shouldIncrement() {
            this.shouldIncrement = true;
        }

        /**
         * External command to decrement counter.
         */
        public void shouldDecrement() {
            this.shouldIncrement = false;
        }
    }

    private class StopAgent implements Runnable {

        private final CounterAgent internalAgent;
        private static final long MAX_RUNNING_TIME_MS = 10_000;

        StopAgent(final CounterAgent agent) {
            this.internalAgent = agent;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(MAX_RUNNING_TIME_MS);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            internalAgent.stopCounting();
            SwingUtilities.invokeLater(() -> {
                up.setEnabled(false);
                down.setEnabled(false);
                stop.setEnabled(false);
            });
        }
    }
}
