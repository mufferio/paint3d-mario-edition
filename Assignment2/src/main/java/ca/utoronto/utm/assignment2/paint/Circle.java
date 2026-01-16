package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle implements Drawable {

    private Point center;
    private double radius;
    private Color colour;
    private boolean filled;
    private double thickness;
    private boolean vis = true;

    /**
     * Constructs a new circle
     * @param center    the center point of the circle
     * @param radius    the radius of the circle
     * @param colour    the coulour of the circle
     * @param filled    whether the circle is filled or just outlined
     * @param thickness the line thickness of the circle's outline
     */
    public Circle(Point center, int radius, Color colour, boolean filled, double thickness) {
        this.center = center;
        this.radius = radius;
        this.colour = colour;
        this.filled = filled;
        this.thickness = thickness;
    }

    /**
     * @return the circles center point
     */
    public Point getCenter() {
        return center;
    }

    /**
     * sets the center point of the circle
     * @param center the new center point
     */
    public void setCenter(Point center) {
        this.center = center;
    }

    /**
     * @return the circle's radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Sets the radius of the circle
     * @param radius the new radius of the circle
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * @return the colour of the circle
     */
    public Color getColour() {
        return colour;
    }

    /**
     * Sets the color of the circle
     * @param colour the new colour of the circle
     */
    public void setColour(Color colour) {
        this.colour = colour;
    }

    /**
     * Draws this circle onto the provided GraphicsContext
     *
     * If the circle is filled, the interior is rendered with the current colour.
     * Otherwise, only the outline is drawn.
     * @param g2d the GraphicsContext used to render the circle
     */
    @Override
    public void draw(GraphicsContext g2d) {

        double cx = this.getCenter().x;
        double cy = this.getCenter().y;
        double radius = this.getRadius();
        double diameter = radius * 2;

        g2d.setStroke(this.colour);
        g2d.setLineWidth(this.thickness);
        if (filled) {
            g2d.setFill(this.colour);
            g2d.fillOval(cx - radius, cy - radius, diameter, diameter);
        } else {
            g2d.strokeOval(cx - radius, cy - radius, diameter, diameter);
        }
    }

    /**
     * @return a SelectionRectangle representing the circles bounds
     */
    @Override
    public SelectionRectangle getSelectionRect() {
        double cx = this.getCenter().x;
        double cy = this.getCenter().y;

        double x1 = cx - radius;
        double y1 = cy - radius;
        double x2 = cx + radius;
        double y2 = cy + radius;

        return new SelectionRectangle(
                new Point(x1, y1),
                new Point(x2, y2)
        );
    }

    /**
     * @return a Point representing the circle's centroid
     */
    @Override
    public Point getCentroidForSelect() {
        return new Point(this.getCenter().x, this.getCenter().y);
    }

    /**
     * Adjusts the circle's radius based on the given mouse coordinates.
     * @param mx the x-coordinate of the current mouse position
     * @param my the y-coordinate of the current mouse position
     */
    @Override
    public void adjustForFeedback(double mx, double my) {
        double dx = this.getCenter().x - mx;
        double dy = this.getCenter().y - my;
        double radius = Math.hypot(dx, dy);
        this.setRadius(radius);
    }

    /**
     * Moves the circle so that its centroid is located at the given coordinates.
     * @param n_cx the new x-coordinate for the centroid
     * @param n_cy the new y-coordinate for the centroid
     */
    @Override
    public void adjustPositionByCentroid(double n_cx, double n_cy) {
        this.setCenter(new Point(n_cx, n_cy));
    }

    /**
     * Returns whether this shape is selectable.
     * @return true since shape is selectable
     */
    @Override
    public boolean isSelectable() {
        return this.getVis();
    }

    /**
     * @return an identical new instance of the shape
     */
    @Override
    public Drawable makeCopy() {
        return new Circle(this.center,(int)this.radius,this.colour,this.filled,this.thickness);
    }
    @Override
    public void setVis(boolean vis) {
        this.vis = vis;
    }

    @Override
    public boolean getVis() {
        return this.vis;
    }
}
