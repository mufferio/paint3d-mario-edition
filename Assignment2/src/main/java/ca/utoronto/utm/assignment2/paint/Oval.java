package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Oval implements Drawable {

    private Point firstCorner;
    private Point secondCorner;
    private Color colour;
    private boolean filled;
    private double thickness;
    private boolean vis = true;

    /**
     * Constructs a new Oval.
     *
     * @param firstCorner the first corner Point
     * @param secondCorner the opposite corner Point
     * @param colour the colour of the oval
     * @param filled whether the oval is filled
     * @param thickness the line thickness when not filled
     */
    public Oval(Point firstCorner, Point secondCorner, Color colour, boolean filled, double thickness) {
        this.firstCorner = firstCorner;
        this.secondCorner = secondCorner;
        this.colour = colour;
        this.filled = filled;
        this.thickness = thickness;
    }

    /**
     * @return the Point the oval starts
     */
    public Point getStartCorner() {
        double x = Math.min(this.firstCorner.x, this.secondCorner.x);
        double y = Math.min(this.firstCorner.y, this.secondCorner.y);
        return new Point(x, y);
    }

    /**
     * Sets the starting corner of the oval.
     *
     * @param firstCorner the new starting corner
     */
    public void setFirstCorner(Point firstCorner) {
        this.firstCorner = firstCorner;
    }

    /**
     * @return the Point the oval ends
     */
    public Point getEndCorner() {
        double x = Math.max(this.firstCorner.x, this.secondCorner.x);
        double y = Math.max(this.firstCorner.y, this.secondCorner.y);
        return new Point(x, y);
    }

    /**
     * Sets the ending corner and updates oval radii accordingly.
     *
     * @param secondCorner the new ending corner
     */
    public void setSecondCorner(Point secondCorner) {
        this.secondCorner = secondCorner;
    }

    /**
     * @return the oval colour
     */
    public Color getColour() {
        return colour;
    }

    /**
     * Sets the colour of the oval.
     *
     * @param colour the new oval colour
     */
    public void setColour(Color colour) {
        this.colour = colour;
    }

    /**
     * @return the ovals width
     */
    public double getHeight() {
        return this.getEndCorner().y - this.getStartCorner().y;
    }

    /**
     * @return the ovals height
     */
    public double getWidth() {
        return this.getEndCorner().x - this.getStartCorner().x;
    }

    /**
     * Draws the oval on the given GraphicsContext.
     *
     * @param g2d The GraphicsContext to drow onto
     */
    @Override
    public void draw(GraphicsContext g2d) {
        g2d.setFill(this.getColour());
        double x = this.getStartCorner().x;
        double y = this.getStartCorner().y;
        double width = this.getWidth();
        double height = this.getHeight();

        g2d.setStroke(this.colour);
        g2d.setLineWidth(this.thickness);
        if (filled) {
            g2d.setFill(this.colour);
            g2d.fillOval(x, y, width, height);
        } else {
            g2d.strokeOval(x, y, width, height);
        }
    }

    /**
     * @return the SelectionRectangle
     */
    @Override
    public SelectionRectangle getSelectionRect() {
        return new SelectionRectangle(this.getStartCorner(), this.getEndCorner());
    }

    /**
     * @return a Point representing the oval's centroid
     */
    @Override
    public Point getCentroidForSelect() {
        double x = Math.min(this.firstCorner.x, this.secondCorner.x);
        double y = Math.min(this.firstCorner.y, this.secondCorner.y);

        double width = Math.abs(this.secondCorner.x - this.firstCorner.x);
        double length = Math.abs(this.secondCorner.y - this.firstCorner.y);

        return new Point(x + width / 2, y + length / 2);
    }

    /**
     * Adjusts the oval's radius based on the given mouse coordinates.
     * @param mx the x-coordinate of the current mouse position
     * @param my the y-coordinate of the current mouse position
     */
    @Override
    public void adjustForFeedback(double mx, double my) {
        this.setSecondCorner(new Point(mx, my));
    }

    /**
     * Moves the oval so that its centroid is located at the given coordinates.
     * @param n_cx the new x-coordinate for the centroid
     * @param n_cy the new y-coordinate for the centroid
     */
    @Override
    public void adjustPositionByCentroid(double n_cx, double n_cy) {
        double n_x1 = n_cx - this.getWidth() / 2;
        double n_y1 = n_cy - this.getHeight() / 2;
        double n_x2 = n_cx + this.getWidth() / 2;
        double n_y2 = n_cy + this.getHeight() / 2;

        this.setFirstCorner(new Point(n_x1, n_y1));
        this.setSecondCorner(new Point(n_x2, n_y2));
    }

    /**
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
        return new Oval(this.firstCorner, this.secondCorner, this.colour, this.filled, this.thickness);
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
