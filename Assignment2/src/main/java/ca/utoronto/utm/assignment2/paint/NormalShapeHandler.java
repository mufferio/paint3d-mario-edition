package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

/**
 * Handles mouse events for drawing normal shapes (circle, rectangle, etc.).
 * This handler creates a new shape on mouse press, adjusts the shape dimensions
 * while the mouse is dragged, and finalizes the shape on mouse release. The
 * specific shape type is determined by the shape chooser model.
 */
public class NormalShapeHandler extends MouseEventHandler {

    /**
     * Constructs a NormalShapeHandler with the given paint and shape chooser
     * models.
     *
     * @param p_model the paint model managing the canvas state and shapes
     * @param s_model the shape chooser model determining which shape type to
     * draw
     */
    public NormalShapeHandler(PaintModel p_model, ShapeChooserModel s_model) {
        super(p_model, s_model);
    }

    /**
     * Creates a new shape of the type selected in the shape chooser model at
     * the position of the mouse press event.
     *
     * @param mouseEvent the mouse press event containing the click position
     */
    @Override
    void onMousePressed(MouseEvent mouseEvent) {
        this.addShapeByMode(mouseEvent);
    }

    /**
     * Adjusts shape that is being drawn at the moment to match the current mouse
     * position, providing real-time visual feedback to the user.
     *
     * @param mouseEvent the mouse drag event containing the current cursor
     * position
     */
    @Override
    void onMouseDragged(MouseEvent mouseEvent) {
        this.p_model.adjustLatestShapeForFeedback(MouseEventHandler.getClickedPoint(mouseEvent));
    }

    /**
     * Sets the final dimensions of the shape and clears the redo stack,
     * preventing redo operations after a new shape is drawn.
     *
     * @param mouseEvent the mouse release event containing the final cursor
     * position
     */
    @Override
    void onMouseReleased(MouseEvent mouseEvent) {
        this.p_model.adjustLatestShapeForFeedback(MouseEventHandler.getClickedPoint(mouseEvent));
        this.p_model.clear_redo();
    }

    @Override
    void onMouseMoved(MouseEvent mouseEvent) {
    }

}
