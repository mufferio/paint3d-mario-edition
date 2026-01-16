package ca.utoronto.utm.assignment2.paint;

public class Point {

    double x, y; // Available to our package

    /**
     * Constructs a Point at the given (x, y) coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Compute the Euclidean distance between this point and another point
     *
     * @param other the other Point to measure distance too
     * @return the distance between this Point and the other Point
     */
    public double distBetweenPoints(Point other) {
        double deltaX = this.y - other.y;
        double deltaY = this.x - other.x;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    /**
     * Returns a new copy of itself.
     *
     * @return new copy of itself
     */
    public Point copy() {
        return new Point(this.x, this.y);
    }

    /**
     * @return a string representation of this point, showing its x and y values
     */
    @Override
    public String toString() {
        return ("x=" + this.x + ", y=" + this.y);
    }
}
