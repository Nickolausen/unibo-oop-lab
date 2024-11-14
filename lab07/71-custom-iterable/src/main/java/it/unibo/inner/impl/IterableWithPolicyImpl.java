package it.unibo.inner.impl;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import it.unibo.inner.api.*;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {

    private final List<T> items;
    private Predicate<T> filter;

    public IterableWithPolicyImpl(T[] items) {
        this(items, new Predicate<T>() {
            @Override
            public boolean test(T t) {
                return true;
            }    
        });
    }
    
    public IterableWithPolicyImpl(final T[] items, final Predicate<T> filter) 
    {
        this.items = List.of(items);
        this.filter = filter;
    }
   
    @Override
    public void setIterationPolicy(Predicate<T> filter) {
        this.filter = filter;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new FilterIterator();
    }

    @Override
    public String toString() 
    {
        final StringBuilder sb = new StringBuilder();

        sb.append("[");
        for (T el: this) {
            sb.append(el + ", ");
        }
        sb.append("]");

        return sb.toString();
    }
    
    private class FilterIterator implements Iterator<T> {

        private int currentIdx = 0;

        @Override
        public boolean hasNext() {
            while (currentIdx < items.size()) {
                T el = items.get(currentIdx);
                if (filter.test(el)) {
                    return true;
                }
                currentIdx++;
            }

            return false;
        }

        @Override
        public T next() {
            if (this.hasNext()) {
                return items.get(currentIdx++);
            }

            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}