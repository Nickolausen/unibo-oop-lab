package it.unibo.mvc;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {
    
    private static final String FILE_PATH = "config.yml";
    private final DrawNumber model;
    private final List<DrawNumberView> views;

    /**
     * @param views
     *            the views to attach
     */
    public DrawNumberApp(final String filePath, final DrawNumberView... views) {
        /*
        * Side-effect proof
        */
        this.views = Arrays.asList(Arrays.copyOf(views, views.length));
        for (final DrawNumberView view: views) {
            view.setObserver(this);
            view.start();
        }
        final ResourceLoader loader = new ResourceLoader(filePath);
        final Configuration config = this.buildConfigFromFile(loader);
        this.model = new DrawNumberImpl(
            config.getMin(),
            config.getMax(),
            config.getAttempts()
        );
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            for (final DrawNumberView view: views) {
                view.result(result);
            }
        } catch (IllegalArgumentException e) {
            for (final DrawNumberView view: views) {
                view.numberIncorrect();
            }
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @Override
    public void quit() {
        /*
         * A bit harsh. A good application should configure the graphics to exit by
         * natural termination when closing is hit. To do things more cleanly, attention
         * should be paid to alive threads, as the application would continue to persist
         * until the last thread terminates.
         */
        System.exit(0);
    }

    /**
     * @param args
     *            ignored
     * @throws FileNotFoundException 
     */
    public static void main(final String... args) throws FileNotFoundException {
        new DrawNumberApp(FILE_PATH, new DrawNumberViewImpl());
    }

    @Override
    public Configuration buildConfigFromFile(final ResourceLoader loader) {
        final Configuration.Builder outputConfigurationBuilder = new Configuration.Builder();
        outputConfigurationBuilder.setMin(loader.getParameter("minimum"));
        outputConfigurationBuilder.setMax(loader.getParameter("maximum"));
        outputConfigurationBuilder.setAttempts(loader.getParameter("attempts"));
        return outputConfigurationBuilder.build();
    }
}
