package ca.utoronto.utm.assignment2.paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Squiggle implements Drawable {

    private ArrayList<Point> points = new ArrayList<Point>();
    private Color colour;
    private double thickness;
    private boolean vis = true;

    /**
     * Constructs a Squiggle.
     *
     * @param colour the stroke colour of the squiggle
     * @param thickness the thickness of its stroke
     */
    public Squiggle(Color colour, double thickness) {
        this.colour = colour;
        this.thickness = thickness;
    }

    /**
     * @return the colour of the squiggle
     */
    public Color getColour() {
        return colour;
    }

    /**
     * Adds a new point to the squiggle's point list.
     *
     * @param point the point to be added
     */
    public void addPoint(Point point) {
        this.points.add(point);
    }

    public ArrayList<Point> getPoints() {
        return this.points;
    }

    private double getMin(boolean isX) {
        ArrayList<Point> points = this.getPoints();
        double minVal = isX ? points.get(0).x : points.get(0).y;

        for (Point point : points) {
            double val = isX ? point.x : point.y;

            if (val < minVal) {
                minVal = val;
            }
        }
        return minVal;
    }

    private double getMax(boolean isX) {
        ArrayList<Point> points = this.getPoints();
        double maxVal = isX ? points.get(0).x : points.get(0).y;

        for (Point point : points) {
            double val = isX ? point.x : point.y;

            if (val > maxVal) {
                maxVal = val;
            }
        }
        return maxVal;
    }

    /**
     * Draws the squiggle by connecting its points in sequence.
     *
     * @param g2d the GraphicsContext used for drawing
     */
    @Override
    public void draw(GraphicsContext g2d) {
        g2d.setStroke(this.getColour());
        g2d.setLineWidth(this.thickness);
        ArrayList<Point> points = this.getPoints();
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            g2d.strokeLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    /**
     * @return a SelectionRectangle that bounds this squiggle
     */
    @Override
    public SelectionRectangle getSelectionRect() {
        double x1 = this.getMin(true);
        double y1 = this.getMin(false);
        double x2 = this.getMax(true);
        double y2 = this.getMax(false);

        return new SelectionRectangle(
                new Point(x1, y1),
                new Point(x2, y2)
        );
    }

    /**
     * @return the centroid point of the squiggle for selection purposes
     */
    @Override
    public Point getCentroidForSelect() {
        double x1 = this.getMin(true);
        double y1 = this.getMin(false);
        double x2 = this.getMax(true);
        double y2 = this.getMax(false);

        return new Point((x1 + x2) / 2, (y1 + y2) / 2);
    }

    /**
     * Adjusts the squiggle's size based on a feedback point.
     *
     * @param mx the x-coordinate of the feedback point
     * @param my the y-coordinate of the feedback point
     */
    @Override
    public void adjustForFeedback(double mx, double my) {
        this.addPoint(new Point(mx, my));
    }

    /**
     * Moves the squiggle by setting its centroid to a new position.
     *
     * @param n_cx the new x-coordinate of the centroid
     * @param n_cy the new y-coordinate of the centroid
     */
    @Override
    public void adjustPositionByCentroid(double n_cx, double n_cy) {
        Point curr_centroid = this.getCentroidForSelect();
        for (Point point : this.getPoints()) {
            double dx = point.x - curr_centroid.x;
            double dy = point.y - curr_centroid.y;
            point.x = n_cx + dx;
            point.y = n_cy + dy;
        }
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
        Squiggle line = new Squiggle(this.colour, this.thickness);
        for (Point point : this.getPoints()) {
            line.addPoint(new Point(point.x, point.y));
        }
        return line;
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
