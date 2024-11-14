package it.unibo.shapes.impl;
import java.lang.Math;
import it.unibo.shapes.api.Shapes;

public class Circle implements Shapes {
    private double radius;

    public Circle(final double radius) {
        assert(radius > 0.0);

        this.radius = radius;
    }

    public double calculateArea() {
        return 0;
    }

    public double calculatePerimeter() {
        return 0;
    }
}
