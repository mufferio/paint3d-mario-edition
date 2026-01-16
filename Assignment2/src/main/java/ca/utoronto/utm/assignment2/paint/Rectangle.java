package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public final class Rectangle implements Drawable {

    private Point firstCorner;
    private Point secondCorner;
    private Color colour;
    private boolean filled;
    private double thickness;
    private boolean vis = true;

    /**
     * Constructs a rectangle
     *
     * @param firstCorner the top left corner of the rectangle
     * @param secondCorner the bottom right corner of the rectangle
     * @param colour the colour of the rectangle
     * @param filled whether the rectangle is filled or outlined
     * @param thickness the thickness of the outline
     */
    public Rectangle(Point firstCorner, Point secondCorner, Color colour, boolean filled, double thickness) {
        this.setFirstCorner(firstCorner);
        this.setSecondCorner(secondCorner);
        this.colour = colour;
        this.filled = filled;
        this.thickness = thickness;
    }

    /**
     * @return the starting corner of the rectangle
     */
    public Point getStartCorner() {
        double x = Math.min(this.firstCorner.x, this.secondCorner.x);
        double y = Math.min(this.firstCorner.y, this.secondCorner.y);
        return new Point(x, y);
    }

    /**
     * Sets the starting corner of the rectangle.
     *
     * @param n_fc the new starting corner
     */
    public void setFirstCorner(Point n_fc) {
        this.firstCorner = n_fc;
    }

    /**
     * @return the ending corner of the rectangle
     */
    public Point getEndCorner() {
        double x = Math.max(this.firstCorner.x, this.secondCorner.x);
        double y = Math.max(this.firstCorner.y, this.secondCorner.y);
        return new Point(x, y);
    }

    /**
     * Sets the ending corner of the rectangle.
     *
     * @param n_sc the new ending corner
     */
    public void setSecondCorner(Point n_sc) {
        this.secondCorner = n_sc;
    }

    /**
     * @return the length of the rectangle
     */
    public double getHeight() {
        return this.getEndCorner().y - this.getStartCorner().y;
    }

    /**
     * @return the width of the rectangle
     */
    public double getWidth() {
        return this.getEndCorner().x - this.getStartCorner().x;
    }

    /**
     * @return the colour of the rectangle
     */
    public Color getColour() {
        return colour;
    }

    /**
     * Draws the rectangle on the given GraphicsContext.
     *
     * @param g2d the GraphicsContext to draw on
     */
    @Override
    public void draw(GraphicsContext g2d) {
        double x = this.getStartCorner().x;
        double y = this.getStartCorner().y;
        double width = this.getWidth();
        double height = this.getHeight();

        g2d.setLineWidth(this.thickness);
        g2d.setStroke(this.colour);

        if (filled) {
            g2d.setFill(this.colour);
            g2d.fillRect(x, y, width, height);
        } else {
            g2d.strokeRect(x, y, width, height);
        }

    }

    /**
     * @return a SelectionRectangle that bounds this rectangle
     */
    @Override
    public SelectionRectangle getSelectionRect() {
        return new SelectionRectangle(this.getStartCorner(), this.getEndCorner());
    }

    /**
     * @return the centroid point of the rectangle for selection purposes
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
     * Adjusts the rectangle's size based on a feedback point.
     * @param mx the x-coordinate of the feedback point
     * @param my the y-coordinate of the feedback point
     */
    @Override
    public void adjustForFeedback(double mx, double my) {
        this.setSecondCorner(new Point(mx, my));
    }

    /**
     * Moves the rectangle by setting its centroid to a new position.
     * @param n_cx the new x-coordinate of the centroid
     * @param n_cy the new y-coordinate of the centroid
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
    public Drawable makeCopy(){
        return new Rectangle(this.firstCorner,this.secondCorner,this.colour,this.filled,this.thickness);
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
