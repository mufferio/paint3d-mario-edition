package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Triangle implements Drawable {

    private Point firstCorner;
    private Point secondCorner;
    private Color colour;
    private boolean filled;
    private double thickness;
    private boolean vis = true;

    /**
     * Constructs a Triangle
     * @param firstCorner the first corner of the triangle
     * @param secondCorner the opposite corner of the triangle
     * @param colour the colour of the triangle
     * @param filled whether the triangle is filled
     * @param thickness the line thickness of the triangle
     */
    public Triangle(Point firstCorner, Point secondCorner, Color colour, boolean filled, double thickness) {
        this.firstCorner = firstCorner;
        this.secondCorner = secondCorner;
        this.colour = colour;
        this.filled = filled;
        this.thickness = thickness;
    }

    /**
     * @return the starting corner of the triangle
     */
    public Point getFirstCorner() {
        return firstCorner;
    }

    /**
     * Sets the starting corner of the triangle
     *
     * @param startCorner the new starting corner
     */
    public void setFirstCorner(Point startCorner) {
        this.firstCorner = startCorner;
    }

    /**
     * @return the ending corner of the triangle
     */
    public Point getSecondCorner() {
        return secondCorner;
    }

    /**
     * Sets the ending corner of the triangle and updates its width and height
     *
     * @param endCorner the new ending corner
     */
    public void setSecondCorner(Point endCorner) {
        this.secondCorner = endCorner;
    }

    public Point getStartCorner() {
        double x = Math.min(this.getFirstCorner().x, this.getSecondCorner().x);
        double y = Math.min(this.getFirstCorner().y, this.getSecondCorner().y);
        return new Point(x, y);
    }

    public Point getEndCorner() {
        double x = Math.max(this.getFirstCorner().x, this.getSecondCorner().x);
        double y = Math.max(this.getFirstCorner().y, this.getSecondCorner().y);
        return new Point(x, y);
    }

    /**
     * @return the height of the triangle
     */
    public double getHeight() {
        return Math.abs(this.secondCorner.y - this.firstCorner.y);
    }

    /**
     * @return the width of the triangle
     */
    public double getWidth() {
        return Math.abs(this.secondCorner.x - this.firstCorner.x);
    }

    /**
     * @return the colour of the triangle
     */
    public Color getColour() {
        return colour;
    }

    /**
     * Draws the triangle on the given GraphicsContext.
     *
     * @param g2d the GraphicsContext used for drawing
     */
    @Override
    public void draw(GraphicsContext g2d) {
        g2d.setFill(this.getColour());
        double x = Math.min(this.getFirstCorner().x, this.getSecondCorner().x);
        double tipY = this.getFirstCorner().y;
        double mouseY = this.getSecondCorner().y;
        double w = this.getWidth();
        double h = this.getHeight();
        double[] xs = {x + w / 2, x, x + w};
        double[] ys = new double[3];

        if (mouseY >= tipY) {
            ys[0] = tipY;
            ys[1] = tipY + h;
            ys[2] = tipY + h;
        } else {
            ys[0] = tipY;
            ys[1] = tipY - h;
            ys[2] = tipY - h;
        }

        g2d.setStroke(this.colour);
        g2d.setLineWidth(this.thickness);
        if (this.filled) {
            g2d.setFill(this.colour);
            g2d.fillPolygon(xs, ys, 3);
        } else {
            g2d.strokePolygon(xs, ys, 3);
        }
    }

    /**
     * @return a SelectionRectangle that bounds this triangle
     */
    @Override
    public SelectionRectangle getSelectionRect() {
        return new SelectionRectangle(this.getStartCorner(), this.getEndCorner());
    }

    /**
     * @return the centroid point of the triangle for selection purposes
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
     * Adjusts the triangle's size based on a feedback point.
     * @param mx the x-coordinate of the feedback point
     * @param my the y-coordinate of the feedback point
     */
    @Override
    public void adjustForFeedback(double mx, double my) {
        this.setSecondCorner(new Point(mx, my));
    }

    /**
     * Moves the triangle by setting its centroid to a new position.
     * @param n_cx the new x-coordinate of the centroid
     * @param n_cy the new y-coordinate of the centroid
     */
    @Override
    public void adjustPositionByCentroid(double n_cx, double n_cy) {
        double n_x1 = n_cx - this.getWidth() / 2;
        double n_y1 = n_cy - this.getHeight() / 2;
        double n_x2 = n_cx + this.getWidth() / 2;
        double n_y2 = n_cy + this.getHeight() / 2;

        Point c1 = new Point(n_x1, n_y1);
        Point c2 = new Point(n_x2, n_y2);
        Point n_fc, n_sc;

        if (this.getSecondCorner().y >= this.getFirstCorner().y) {
            n_fc = c1;
            n_sc = c2;
        } else {
            n_fc = c2;
            n_sc = c1;
        }

        this.setFirstCorner(n_fc);
        this.setSecondCorner(n_sc);
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
        return new Triangle(this.firstCorner, this.secondCorner, this.colour, this.filled, this.thickness);
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
