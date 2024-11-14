package it.unibo.shapes.impl;
import it.unibo.shapes.api.Polygon;

public class Triangle implements Polygon {
    private int edgesCount;
    private double[] sidesLenght;
    private double baseLength;
    private double heightLength;
    
    public Triangle(
            final int edgesCount, 
            final double baseLength, 
            final double heightLength, 
            final double[] sidesLength) {
        assert(edgesCount > 0);
        assert(sidesLength.length == edgesCount);
        for (double side: sidesLenght) {
            assert(side > 0.0);
        }

        this.edgesCount = edgesCount;
        this.baseLength = baseLength;
        this.heightLength = heightLength;
        this.sidesLenght = sidesLength;
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

    public double[] getSidesLength() {
        return this.sidesLenght;
    }

    public double calculateArea() {
        return this.getBaseLength() * this.getHeightLength();
    }

    public double calculatePerimeter() {
        double sum = 0;
        for (double side: sidesLenght) {
            sum += side;
        }

        return sum;
    }
}
