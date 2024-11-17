package it.unibo.mvc.api;

/**
 * Controller interface.
 */
public interface DrawNumberController {

    /**
     * Makes a guess.
     * 
     * @param n the attempt
     */
    void newAttempt(int n);

    /**
     * Resets the current game (if any is running) and starts a new one.
     */
    void resetGame();

    /**
     * Gracefully quits from the application.
     */
    void quit();

    /**
     * Adds a new view.
     *
     * @param newView the view to be added
     */
    void addView(DrawNumberView newView);


    /**
     * Deletes a certain view.
     * 
     * @param viewToDelete the view to be deleted
     * @return true if viewToDelete has been removed, false otherwise
     */
    boolean removeView(DrawNumberView viewToDelete);
}
