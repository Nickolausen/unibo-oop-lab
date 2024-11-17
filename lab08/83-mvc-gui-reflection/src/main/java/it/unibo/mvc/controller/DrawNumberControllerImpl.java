package it.unibo.mvc.controller;

import it.unibo.mvc.api.DrawNumber;
import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

import java.util.Objects;
import java.util.ArrayList;

/**
 * This class implements the game controller. It orchestrates the game, exposes methods to its observers
 * (the boundaries), and sends results to them.
 */
public final class DrawNumberControllerImpl implements DrawNumberController {

    private final DrawNumber model;
    private ArrayList<DrawNumberView> views;

    /**
     * Builds a new game controller provided a game model.
     *
     * @param model the implementation of the game model
     */
    public DrawNumberControllerImpl(final DrawNumber model) {
        this.views = new ArrayList<DrawNumberView>();
        this.model = model;
    }

    @Override
    public void addView(final DrawNumberView view) {
        this.views.add(view);
        view.setController(this);
        view.start();
    }
    
    @Override
    public boolean removeView(final DrawNumberView view) {
        Objects.requireNonNull(view, "Cannot remove a null view");
        if (!this.views.contains(view)) {
            return false;
        }
        this.views.remove(this.views.indexOf(view));
        return true;
    }

    @Override
    public void newAttempt(final int n) {
        final DrawResult result = this.model.attempt(n);
        for (final var view: this.views) {
            view.result(result);
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

}
