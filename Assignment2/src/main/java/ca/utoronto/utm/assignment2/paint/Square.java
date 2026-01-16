package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public final class Square implements Drawable {

    private Point firstCorner = new Point(0, 0);
    private Point secondCorner = new Point(0, 0);
    private Color colour;
    private boolean filled;
    private double thickness;
    private boolean vis = true;

    /**
     * Constructs a square
     *
     * @param firstCorner the top left corner of the square
     * @param secondCorner the bottom right corner of the square
     * @param colour the colour of the square
     * @param filled whether the square is filled or outlined
     * @param thickness the thickness of the outline
     */
    public Square(Point firstCorner, Point secondCorner, Color colour, boolean filled, double thickness) {
        this.setFirstCorner(firstCorner);
        this.setSecondCorner(secondCorner);
        this.colour = colour;
        this.filled = filled;
        this.thickness = thickness;
    }

    /**
     * @return the starting corner of the square
     */
    public Point getStartCorner() {
        // System.out.println("" + this.firstCorner + " " + this.secondCorner);
        double x = Math.min(this.firstCorner.x, this.secondCorner.x);
        double y = Math.min(this.firstCorner.y, this.secondCorner.y);
        return new Point(x, y);
    }

    /**
     * @return the ending corner of the square
     */
    public Point getEndCorner() {
        double x = Math.max(this.firstCorner.x, this.secondCorner.x);
        double y = Math.max(this.firstCorner.y, this.secondCorner.y);
        return new Point(x, y);
    }

    /**
     * Sets the starting corner of the square.
     *
     * @param n_fc the new starting corner
     */
    public void setFirstCorner(Point n_fc) {
        this.firstCorner = n_fc;
    }

    /**
     * Sets the ending corner of the square.
     *
     * @param n_sc the new ending corner
     */
    public void setSecondCorner(Point n_sc) {
        if (this.firstCorner == null) {
            return;
        }

        double dx = n_sc.x - this.firstCorner.x;
        double dy = n_sc.y - this.firstCorner.y;
        double length = Math.max(Math.abs(dx), Math.abs(dy));

        this.secondCorner.x = this.firstCorner.x + ((dx != 0) ? (dx / Math.abs(dx)) * length : 0);
        this.secondCorner.y = this.firstCorner.y + ((dy != 0) ? (dy / Math.abs(dy)) * length : 0);
    }

    /**
     * @return the length of the square
     */
    public double getLength() {
        return Math.abs(this.getEndCorner().x - this.getStartCorner().x);
    }

    /**
     * @return the colour of the square
     */
    public Color getColour() {
        return colour;
    }

    /**
     * Draws the square on the given GraphicsContext.
     *
     * @param g2d the GraphicsContext to draw on
     */
    @Override
    public void draw(GraphicsContext g2d) {
        double x = this.getStartCorner().x;
        double y = this.getStartCorner().y;
        double length = this.getLength();

        g2d.setStroke(this.colour);
        g2d.setLineWidth(this.thickness);
        if (filled) {
            g2d.setFill(this.colour);
            g2d.fillRect(x, y, length, length);
        } else {
            g2d.strokeRect(x, y, length, length);
        }
    }

    /**
     * @return a SelectionRectangle that bounds this square
     */
    @Override
    public SelectionRectangle getSelectionRect() {
        return new SelectionRectangle(this.getStartCorner(), this.getEndCorner());
    }

    /**
     * @return the centroid point of the square for selection purposes
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
     * Adjusts the square's size based on a feedback point.
     * @param mx the x-coordinate of the feedback point
     * @param my the y-coordinate of the feedback point
     */
    @Override
    public void adjustForFeedback(double mx, double my) {
        // System.out.println(this.firstCorner);
        this.setSecondCorner(new Point(mx, my));
    }

    /**
     * Moves the square by setting its centroid to a new position.
     * @param n_cx the new x-coordinate of the centroid
     * @param n_cy the new y-coordinate of the centroid
     */
    @Override
    public void adjustPositionByCentroid(double n_cx, double n_cy) {
        double n_x1 = n_cx - this.getLength() / 2;
        double n_y1 = n_cy - this.getLength() / 2;
        double n_x2 = n_cx + this.getLength() / 2;
        double n_y2 = n_cy + this.getLength() / 2;

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
        return new Square(this.firstCorner, this.secondCorner, this.colour, this.filled, this.thickness);
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
