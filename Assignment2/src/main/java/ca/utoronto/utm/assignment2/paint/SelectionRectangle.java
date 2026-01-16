package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SelectionRectangle {

    private final Point startCorner;
    private final Point endCorner;
    private static Color color = new Color(0, 0, 0, 1);
    private static final int selectionPadding = 10;
    private static final int centerRadius = 3;
    private static final int objTooSmallPad = 10;
    private static final int boxTooSmallThres = 10;

    /**
     * Constructs a SelectionRectangle to indicate if a shape is selected.
     *
     * @param startCorner one corner of the rectangle
     * @param endCorner the opposite corner of the rectangle
     */
    public SelectionRectangle(Point startCorner, Point endCorner) {
        this.startCorner = startCorner;
        this.endCorner = endCorner;
    }

    /**
     * Determines whether a given point lies inside the selection rectangle,
     * including additional padding. If the rectangle is very small, further
     * padding is applied to make selection easier.
     *
     * @param p the point to test for containment
     * @return true if the point is inside the selection area, false otherwise
     */
    public boolean cursorInSelf(Point p) {
        int extraPadding = SelectionRectangle.selectionPadding;
        if (this.getWidth() <= SelectionRectangle.boxTooSmallThres
                || this.getHeight() <= SelectionRectangle.boxTooSmallThres) {
            extraPadding += SelectionRectangle.objTooSmallPad;
        }

        return ((this.startCorner.x - extraPadding) <= p.x
                && (this.startCorner.y - extraPadding) <= p.y
                && (this.endCorner.x + extraPadding) >= p.x
                && (this.endCorner.y + extraPadding) >= p.y);
    }

    /**
     * @return a Point representing the center of the rectangle
     */
    public Point getCentroid() {
        double x = Math.min(this.startCorner.x, this.endCorner.x);
        double y = Math.min(this.startCorner.y, this.endCorner.y);

        double width = this.getWidth();
        double length = this.getHeight();

        return new Point(x + width / 2, y + length / 2);
    }

    /**
     * @return the width of the selection rectangle
     */
    public double getWidth() {
        return Math.abs(this.endCorner.x - this.startCorner.x);
    }

    /**
     * @return the height of the selection rectangle
     */
    public double getHeight() {
        return Math.abs(this.endCorner.y - this.startCorner.y);
    }

    /**
     * Draws the selection rectangle on the given graphics context.
     *
     * @param g2d the GraphicsContext used to render the selection box
     */
    public void drawSelectionBox(GraphicsContext g2d) {
        double x = this.startCorner.x - SelectionRectangle.selectionPadding;
        double y = this.startCorner.y - SelectionRectangle.selectionPadding;

        double width = this.getWidth() + 2 * SelectionRectangle.selectionPadding;
        double height = this.getHeight() + 2 * SelectionRectangle.selectionPadding;

        Point centroid = this.getCentroid();

        g2d.setLineWidth(2);
        g2d.setStroke(SelectionRectangle.color);
        g2d.setLineDashes(15.0, 5.0);

        g2d.strokeRect(x, y, width, height);

        g2d.setFill(SelectionRectangle.color);
        g2d.fillOval(
                centroid.x - SelectionRectangle.centerRadius,
                centroid.y - SelectionRectangle.centerRadius,
                SelectionRectangle.centerRadius * 2,
                SelectionRectangle.centerRadius * 2
        );
    }
}
