package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ImageShape implements Drawable {

    private Image image;
    private double width;
    private double height;
    private boolean vis = true;

    /**
     * Constructs a new ImageShape
     * @param image the Image to render
     * @param width the width to draw the image
     * @param height the height to draw the image
     */
    public ImageShape(Image image, double width, double height) {
        this.image = image;
        this.width = width;
        this.height = height;
    }

    /**
     * @return the Image used for drawing
     */
    public Image getImage() {
        return image;
    }

    /**
     * Draws the image onto the canvas at position (0, 0), scaled to the
     * specified width and height.
     * @param g2d the GraphicsContext used to render the image
     */
    @Override
    public void draw(GraphicsContext g2d) {
        g2d.drawImage(this.getImage(), 0, 0, width, height);
    }

    /**
     * @return null since image shapes cannot be selected
     */
    @Override
    public SelectionRectangle getSelectionRect() {
        return null;
    }

    /**
     * @return a Point representing the centroid of the image
     */
    @Override
    public Point getCentroidForSelect() {
        return new Point(this.width / 2, this.height / 2);
    }

    /**
     * Adjustment for live feedback is not supported for image shapes.
     * @param mx the x-coordinate of the current mouse position
     * @param my the y-coordinate of the current mouse position
     */
    @Override
    public void adjustForFeedback(double mx, double my) {
    }

    /**
     * Repositioning is not supported for image shapes.
     * @param n_cx the new x-coordinate for the centroid
     * @param n_cy the new y-coordinate for the centroid
     */
    @Override
    public void adjustPositionByCentroid(double n_cx, double n_cy) {
    }

    /**
     * @return false since shape is not selectable
     */
    @Override
    public boolean isSelectable() {
        return false;
    }

    /**
     * @return an identical new instance of the shape
     */
    @Override
    public Drawable makeCopy() {
        return null;
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
