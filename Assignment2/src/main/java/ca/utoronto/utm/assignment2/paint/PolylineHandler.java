package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

/**
 * This handler manages multi-point polyline creation. A polyline is started
 * with the first primary mouse click and continues adding points with
 * subsequent clicks. Secondary (right) mouse button press finalizes the
 * polyline.
 */
public class PolylineHandler extends MouseEventHandler {

    /**
     * Constructs a PolylineHandler with the given paint and shape chooser
     * models.
     *
     * @param p_model the paint model managing the canvas state and shapes
     * @param s_model the shape chooser model determining the polyline
     * properties
     */
    public PolylineHandler(PaintModel p_model, ShapeChooserModel s_model) {
        super(p_model, s_model);
    }

    /**
     * Handles mouse press for polyline drawing. If the secondary (right) mouse
     * button is down and a polyline is being drawn, the polyline is finalized.
     * Otherwise: - If no polyline is being drawn, starts a new polyline at the
     * click position.</li>
     * - If a polyline is being drawn, adds the clicked point to the
     * polyline.</li>
     *
     * @param mouseEvent the mouse press event containing the click position and
     * button information
     */
    @Override
    void onMousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.isSecondaryButtonDown() && this.p_model.isDrawingPolyline()) {
            this.p_model.setDrawingPolyline(false);
        } else {
            if (!this.p_model.isDrawingPolyline()) {
                this.addShapeByMode(mouseEvent);
                this.p_model.setDrawingPolyline(true);
            } else {
                Point point = MouseEventHandler.getClickedPoint(mouseEvent);
                this.p_model.adjustLatestShapeForFeedback(point);
                this.p_model.polylineAddCurrrent(point);
            }
        }
    }

    /**
     * If a polyline is currently being drawn, adjusts the preview line segment
     * from the last polyline point to the current cursor position.
     *
     * @param mouseEvent the mouse move event containing the current cursor
     * position
     */
    @Override
    void onMouseMoved(MouseEvent mouseEvent) {
        if (this.p_model.isDrawingPolyline()) {
            this.p_model.adjustLatestShapeForFeedback(MouseEventHandler.getClickedPoint(mouseEvent));
        }
    }

    @Override
    void onMouseDragged(MouseEvent mouseEvent) {
    }

    @Override
    void onMouseReleased(MouseEvent mouseEvent) {
    }
}
