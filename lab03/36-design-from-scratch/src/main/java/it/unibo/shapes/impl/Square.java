package it.unibo.shapes.impl;
import it.unibo.shapes.api.Polygon;

public class Square implements Polygon {
    private double edgeLength;
    private int edgesCount;
    
    public Square(final int edgesCount, final double edgeLength) {
        assert(edgeLength > 0);
        assert(edgesCount > 0);

        this.edgesCount = edgesCount;
        this.edgeLength = edgeLength;
    }

    public int getEdgesCount() {
        return this.edgesCount;
    }

    public double calculateArea() {
        return this.edgeLength * this.edgeLength;
    }

    public double calculatePerimeter() {
        return this.edgeLength * this.edgesCount;
    }
}
