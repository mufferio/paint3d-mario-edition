package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;
import java.util.Observable;

public class PaintModel extends Observable {

    private final ArrayList<Drawable> shapes = new ArrayList<Drawable>();
    private final ArrayList<Drawable> redo_list = new ArrayList<Drawable>();
    private final ArrayList<Drawable> undid_list = new ArrayList<Drawable>();
    private double height = 300;
    private double width = 300;

    private Drawable selectedShape;
    private Point selectPosDiffWithShape;

    private boolean drawingPolyline;

    private Drawable copiedShape;

    /**
     * Clears all shapes from the model and ends selection mode.
     */
    public void clearShapes() {
        this.endPolyline();
        this.shapes.clear();
        this.endSelectMode();
        this.notif();
    }

    /**
     * Undoes the last shape added. Moves it to the redo list for potential
     * redo.
     */
    public void undo() {
        if (!this.shapes.isEmpty()) {
            for  (Drawable shape : this.shapes.reversed()) {
                if (shape.getVis()) {
                    this.endPolyline();
                    Drawable s = this.shapes.remove(this.shapes.indexOf(shape));
                    s.setVis(false);
                    this.redo_list.add(s);
                    this.endSelectMode();
                    this.notif();
                    break;
                } else {
                    for (Drawable d : this.shapes) {
                        if(!d.getVis() && !this.undid_list.contains(d)) {
                            int counter = 0;
                            int iter = 0;
                            for(Drawable s : this.shapes) {
                                iter++;
                                if(d==s){
                                    counter++;
                                    if(counter >= 2){
                                        this.shapes.remove(iter-1);
                                        this.undid_list.add(d);
                                        this.redo_list.add(d);
                                        d.setVis(true);
                                        this.notif();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Redoes the last undone shape from the redo list.
     */
    public void redo() {
        if (!redo_list.isEmpty()) {
            if(redo_list.getLast().getVis()) {
                Drawable redoShape = redo_list.removeLast();
                Drawable finalShape = null;
                for (Drawable shape : this.shapes) {
                    if (redoShape == shape) {
                        finalShape = shape;
                        shape.setVis(false);
                        this.undid_list.remove(shape);
                    }
                }
                this.addShape(finalShape);
                this.notif();
            }else {
                Drawable redoShape = this.redo_list.removeLast();
                redoShape.setVis(true);
                this.addShape(redoShape);
                this.notif();
            }
        }
    }

    /**
     * Clears the redo list.
     */
    public void clear_redo() {
        this.redo_list.clear();
    }
    public void clear_undo() {
        this.undid_list.clear();
    }

    public void copyShape(Drawable shape) {
        this.copiedShape = shape.makeCopy();
        this.endSelectMode();
    }

    public void cutShape(Drawable shape) {
        this.copiedShape = shape.makeCopy();
        shape.setVis(false);
        this.addShape(shape);
        this.endSelectMode();
    }

    public void deleteShape(Drawable shape) {
        shape.setVis(false);
        this.addShape(shape);
        this.endSelectMode();
    }

    public Drawable getCopy() {
        return this.copiedShape;
    }

    /**
     * @return the list of Shapes currently in the model
     */
    public ArrayList<Drawable> getShapes() {
        return this.shapes;
    }

    private boolean nothingSelected() {
        return this.selectedShape == null;
    }

    /**
     * Adjusts the position of the currently selected shape based on the mouse.
     *
     * @param mx the current x-coordinate of the mouse
     * @param my the current y-coordinate of the mouse
     */
    public void adjustLatestShapeForFeedback(Point mouseClick) {
        this.shapes.getLast().adjustForFeedback(mouseClick.x, mouseClick.y);
        this.notif();
    }

    /**
     * Adjusts the position of the currently selected shape based on the mouse.
     *
     * @param mx the current x-coordinate of the mouse
     * @param my the current y-coordinate of the mouse
     */
    public void adjustPositionSelect(double mx, double my) {
        if (this.nothingSelected()) {
            return;
        }

        double n_cx = mx - this.selectPosDiffWithShape.x;
        double n_cy = my - this.selectPosDiffWithShape.y;
        this.selectedShape.adjustPositionByCentroid(n_cx, n_cy);
        this.notif();
    }

    /**
     * Adds a new shape to the model.
     *
     * @param s the shape to add
     */
    public void addShape(Drawable s) {
        this.shapes.add((Drawable) s);
        this.notif();
    }

    /**
     * Ends the selection mode and deselects any selected shape.
     */
    public void endSelectMode() {
        this.selectedShape = null;
        this.selectPosDiffWithShape = null;
        this.notif();
    }

    private void setSelectedShape(Drawable selectedShape, Point mouseClick) {
        if (!selectedShape.isSelectable()) {
            return;
        }

        if (!selectedShape.getSelectionRect().cursorInSelf(mouseClick)) {
            this.endSelectMode();
            return;
        }

        this.selectedShape = selectedShape;
        Point centroid = selectedShape.getCentroidForSelect();
        this.selectPosDiffWithShape = new Point(
                mouseClick.x - centroid.x,
                mouseClick.y - centroid.y
        );

        this.notif();
    }

    /**
     * * @return the current selected shape
     */
    public Drawable getSelectedShape() {
        return this.selectedShape;
    }

    /**
     * Finds the closest shape to the given point and selects it.
     *
     * @param mouseClick the point to compare distances against
     */
    public void findClosestToPoint(Point mouseClick) {
        Drawable closestOne = null;
        double minDist = -1;
        for (Drawable shape : this.getShapes().reversed()) {
            double dist = mouseClick.distBetweenPoints(shape.getCentroidForSelect());
            if (dist < minDist || minDist == -1) {
                minDist = dist;
                closestOne = shape;
            }
        }

        if (closestOne != null) {
            this.setSelectedShape(closestOne, mouseClick);
        } else {
            this.endSelectMode();
        }
    }

    /**
     * Sets the canvas height.
     *
     * @param h
     */
    public void setHeight(double h) {
        this.height = h;
        this.notif();
    }

    /**
     * Enables or disables polyline drawing mode.
     * @param drawingPolyline true to enable, false to disable
     */
    public void setDrawingPolyline(boolean drawingPolyline) {
        this.drawingPolyline = drawingPolyline;
    }

    /**
     * @return whether a polyline is currently being drawn
     */
    public boolean isDrawingPolyline() {
        return this.drawingPolyline;
    }

    /**
     * Adds the current mouse position as a point to the polyline being drawn, if polyline mode is active.
     * @param point: point where the mouse clicks
     */
    public void polylineAddCurrrent(Point point) {
        if (!this.isDrawingPolyline()) {
            return;
        }

        ((Polyline) this.getShapes().getLast()).addPoint(point);
    }

    /**
     * Ends polyline drawing mode by removing the temporary final point and notifying observers.
     */
    public void endPolyline() {
        if (this.isDrawingPolyline()) {
            this.setDrawingPolyline(false);
            Polyline polyline = (Polyline) this.shapes.getLast();
            polyline.getPoints().removeLast();
            this.notif();
        }
    }

    /**
     * Sets the canvas width.
     *
     * @param w the new width
     */
    public void setWidth(double w) {
        this.width = w;
        this.notif();
    }

    /**
     * @return the effective height of the canvas minus the margin
     */
    public double getHeight() {
        if (this.height - 66 < 352) {
            return 352;
        }
        return this.height - 66;
    }

    /**
     * @return the effective width of the canvas minus the margin
     */
    public double getWidth() {
        return this.width - 142;
    }

    /**
     * Marks the model as changed and notifies the observers.
     */
    public void notif() {
        this.setChanged();
        this.notifyObservers();
    }
}
