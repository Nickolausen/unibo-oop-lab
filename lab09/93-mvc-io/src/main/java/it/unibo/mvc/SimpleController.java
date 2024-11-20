package it.unibo.mvc;

import java.util.ArrayList;
import java.util.List;
import static java.lang.System.out;

/**
 * A SimpleController class, which represents 
 * the Controller interface implementation.
 */
public final class SimpleController implements Controller {

    private final List<String> history;
    private String currentString;

    /**
     * Default constructor.
     */
    public SimpleController() {
        this.history = new ArrayList<>(0);
        this.currentString = "";
    }

    private void addItemToHistory(final String item) {
        this.history.add(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNextString() {
        return this.currentString;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNextString(final String newItem) {
        this.currentString = newItem;
    }

    /**
     * Returns the history of the printed strings.
     * 
     * @return the history of the strings printed (in a <code>List<String></code> form)
     */
    public List<String> getHistory() {
        return List.copyOf(this.history);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printCurrentString() {
        if (this.currentString.isBlank() || this.currentString.isEmpty()) {
            throw new IllegalStateException("Current string is unset!");
        }
        out.println(this.currentString);
        addItemToHistory(this.currentString);
    }
}
