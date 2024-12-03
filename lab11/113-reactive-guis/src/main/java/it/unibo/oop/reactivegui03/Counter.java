package it.unibo.oop.reactivegui03;

import java.io.Serializable;

/**
 * Classic counter — "model" of the application.
 */
public class Counter implements Serializable {

    private static final long serialVersionUID = 2L;
    private int value;

    /**
     * Initializes the counter with a value.
     * @param initialValue starting value of the counter
     */
    public Counter(final int initialValue) {
        this.value = initialValue;
    }

    /**
     * Increment by 1 the counter.
     */
    public void increment() {
        this.value++;
    }

    /**
     * Decrement by 1 the counter.
     */
    public void decrement() {
        this.value--;
    }

    /**
     * 
     * @return the value of the counter
     */
    public int getValue() {
        return this.value;
    }
}
