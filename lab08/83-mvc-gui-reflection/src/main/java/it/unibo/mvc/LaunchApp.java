package it.unibo.mvc;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;

import java.util.List;
/**
 * Application entry-point.
 */
public final class LaunchApp {
    
    private static List<String> IMPLEMENTED_VIEWS = List.of("DrawNumberStdoutView", "DrawNumberSwingView");
    private static Integer NR_VIEWS_PER_KIND = 3;

    private LaunchApp() { }
    
    /**
     * Runs the application.
     *
     * @param args ignored
     * @throws ClassNotFoundException if the fetches class does not exist
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalAccessException in case of reflection issues
     * @throws IllegalArgumentException in case of reflection issues
     */
    @SuppressWarnings("deprecated")
    public static void main(final String... args) {
        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);
        for (final var viewClass: IMPLEMENTED_VIEWS) {
            for (int i = 0; i < NR_VIEWS_PER_KIND; i++) {
                try {
                    final var newView = Class.forName("it.unibo.mvc.view." + viewClass).newInstance(); 
                    app.addView((DrawNumberView)newView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
