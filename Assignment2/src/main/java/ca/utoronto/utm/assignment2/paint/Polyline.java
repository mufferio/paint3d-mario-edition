package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Polyline implements Drawable {

    private ArrayList<Point> points = new ArrayList<>();
    private Color colour;
    private double thickness;
    private boolean vis = true;

    /**
     * Constructs a new Polyline
     * @param colour the colour used to draw the polyline
     * @param thickness the stroke thickness of the polyline
     */
    public Polyline(Color colour, double thickness) {
        this.colour = colour;
        this.thickness = thickness;
    }

    /**
     * @return the colour of the polyline
     */
    public Color getColour() {
        return this.colour;
    }

    /**
     * @return the line thickness of the polyline
     */
    public double getThickness() {
        return this.thickness;
    }

    /**
     * Adds a new point to this polyline
     * @param point the point to add to the polyline
     */
    public void addPoint(Point point) {
        points.add(point);
    }

    /**
     * @return an ArrayList containing the points of the polyline
     */
    public ArrayList<Point> getPoints() {
        return points;
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
     * Draws the polyline on the given GraphicsContext
     * @param g2d the GraphicsContext used for drawing
     */
    @Override
    public void draw(GraphicsContext g2d) {
        ArrayList<Point> points = this.getPoints();
        for (int i = 1; i < points.size(); i++) {
            Point last = points.get(i - 1);
            Point current = points.get(i);

            g2d.setStroke(this.getColour());
            g2d.setLineWidth(this.getThickness());
            g2d.strokeLine(last.x, last.y, current.x, current.y);
        }
    }

    /**
     * @return a SelectionRectangle that bounds this Polyline
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
     * @return a Point representing the oval's centroid
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
     * Adjusts the Polyline's radius based on the given mouse coordinates.
     * @param mx the x-coordinate of the current mouse position
     * @param my the y-coordinate of the current mouse position
     */
    @Override
    public void adjustForFeedback(double mx, double my) {
        Point lastPoint = this.getPoints().getLast();

        lastPoint.x = mx;
        lastPoint.y = my;
    }

    /**
     * Moves the Polyline so that its centroid is located at the given coordinates.
     * @param n_cx the new x-coordinate for the centroid
     * @param n_cy the new y-coordinate for the centroid
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
        Polyline line = new Polyline(this.getColour(), this.getThickness());
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
