package it.unibo.oop.workers02;

import java.util.ArrayList;
import java.util.List;

public class MultiThreadedSumMatrix implements SumMatrix {

    private final int nThreads;
    
    public MultiThreadedSumMatrix(final int nThreads) {
        this.nThreads = nThreads;
    }

    @Override
    public double sum(double[][] matrix) {
        final List<Worker> workers = new ArrayList<Worker>(this.nThreads);
        workers.forEach(worker -> {
            
        });
        return 0.0;
    }

    private static class Worker extends Thread {

        /**
         * Build a new worker.
         * 
         * @param list
         *            the list to sum
         * @param startpos
         *            the initial position for this worker
         * @param nelem
         *            the no. of elems to sum up for this worker
         */
        Worker(final List<Integer> list, final int startpos, final int nelem) {
            super();
            
        }
    }
}
