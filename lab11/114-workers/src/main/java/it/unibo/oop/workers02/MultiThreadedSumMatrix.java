package it.unibo.oop.workers02;

import java.util.ArrayList;
import java.util.List;
/**
 * Multithreaded matrix sum.
 */
public class MultiThreadedSumMatrix implements SumMatrix {

    private final int nThreads;
    /**
     * 
     * @param nThreads the number of threads used
     */
    public MultiThreadedSumMatrix(final int nThreads) {
        this.nThreads = nThreads;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double sum(final double[][] matrix) {
        final int size = matrix.length % nThreads + matrix.length / nThreads;
        /*
         * Build a list of workers
         */
        final List<Worker> workers = new ArrayList<>(nThreads);
        for (int start = 0; start < matrix.length; start += size) {
            workers.add(new Worker(matrix, start, size));
        }
        /*
         * Start them
         */
        for (final Worker w: workers) {
            w.start();
        }
        /*
         * Wait for every one of them to finish. This operation is _way_ better done by
         * using barriers and latches, and the whole operation would be better done with
         * futures.
         */
        double sum = 0;
        for (final Worker w: workers) {
            try {
                w.join();
                sum += w.getResult();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }
        /*
         * Return the sum
         */
        return sum;
    }

    private static class Worker extends Thread {

        private final double[][] matrixSlice;
        private final int startPos;
        private final int nElem;
        private double res;

        /**
         * Build a new worker.
         * 
         * @param matrixSlice
         *            the slice of matrix to sum
         * @param startPos
         *            the initial position for this worker
         * @param nElem
         *            the no. of elems to sum up for this worker
         */
        Worker(final double[][] matrixSlice, final int startPos, final int nElem) {
            super();
            this.startPos = startPos;
            this.nElem = nElem;
            this.matrixSlice = matrixSlice.clone();
        }

        /**
         * Returns the result of summing up the integers within the list.
         * 
         * @return the sum of every element in the array
         */
        public double getResult() {
            return this.res;
        }

        @Override
        @SuppressWarnings("PMD.SystemPrintln")
        public void run() {
            for (int i = startPos; i < matrixSlice.length && i < startPos + nElem; i++) {
                for (final var val: matrixSlice[i]) {
                    this.res += val;
                }
            }
        }
    }
}
