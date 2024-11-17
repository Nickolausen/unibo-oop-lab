package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

public class DrawNumberStdoutView implements DrawNumberView {
    /**
     * Actually unused — this kind of View is output-only
     */
    private DrawNumberController controller;

    public DrawNumberStdoutView() { }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setController(final DrawNumberController observer) {
        this.controller = observer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        System.out.println("--- Starting " + this.getClass().getName() + " ---");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void result(DrawResult res) {
        System.out.println(res.getDescription());
    }
}
