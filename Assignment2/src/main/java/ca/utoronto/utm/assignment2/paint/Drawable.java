package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;

public interface Drawable {

    /**
     * Draws this shape onto the given GraphicsContext.
     * @param g2d the GraphicsContextt used to render the shape
     */
    public void draw(GraphicsContext g2d);
    /**
     * @return a SelectionRectangle containing the shape's bounds
     */
    public SelectionRectangle getSelectionRect();
    /**
     * @return a Point representing the centroid of the shape
     */
    public Point getCentroidForSelect();
    /**
     * Adjusts the shape's radius based on the given mouse coordinates.
     * @param mx the x-coordinate of the current mouse position
     * @param my the y-coordinate of the current mouse position
     */
    public void adjustForFeedback(double mx, double my);
    /**
     * Moves the shape so that its centroid is located at the given coordinates.
     * @param n_cx the new x-coordinate for the centroid
     * @param n_cy the new y-coordinate for the centroid
     */
    public void adjustPositionByCentroid(double n_cx, double n_cy);
    /**
     * @return true or false, whether a shape is selectable
     */
    public boolean isSelectable();
    /**
     * @return an identical new instance of the shape
     */
    public Drawable makeCopy();
    public void setVis(boolean vis);
    public boolean getVis();
}
