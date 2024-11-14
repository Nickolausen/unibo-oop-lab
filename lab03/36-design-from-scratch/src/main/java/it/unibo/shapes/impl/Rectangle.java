package it.unibo.shapes.impl;
import it.unibo.shapes.api.Polygon;

public class Rectangle implements Polygon {
    private double baseLength;
    private double heightLength;
    private int edgesCount;
    
    public Rectangle(final int edgesCount, final double baseLength, final double heightLength) {
        assert(edgesCount > 0);
        assert(baseLength > 0);
        assert(heightLength > 0);

        this.edgesCount = edgesCount;
        this.baseLength = baseLength;
        this.heightLength = heightLength;
    }

    public double getHeightLength() {
        return this.heightLength;
    }

    public double getBaseLength() {
        return this.baseLength;
    }

    public int getEdgesCount() {
        return this.edgesCount;
    }

    public double calculateArea() {
        return this.getBaseLength() * this.getHeightLength();
    }

    public double calculatePerimeter() {
        return 2 * (this.getBaseLength() + this.getHeightLength());
    }
}
