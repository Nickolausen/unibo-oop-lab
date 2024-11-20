package it.unibo.mvc;

/**
 * A Controller Interface in MVC application, that deals with I/O.
 */
public interface Controller {

    /**
     * Gets the next string to be printed out.
     * 
     * @return the value of the next string
     */
    String getNextString();

    /**
     * Sets a new string to the queue.
     * 
     * @param newItem string to enqueue
     */
    void setNextString(String newItem);

    /**
     * Prints the current string.
     * 
     * @throws IllegalStateException if the current string is unset
     */
    void printCurrentString();
}
