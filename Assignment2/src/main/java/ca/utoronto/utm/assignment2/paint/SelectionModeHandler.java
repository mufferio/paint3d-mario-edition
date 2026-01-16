package ca.utoronto.utm.assignment2.paint;

import javafx.scene.input.MouseEvent;

/**
 * This handler allows users to select shapes by clicking on them and drag them
 * to new positions. The closest shape to the cursor is selected on mouse press
 * and can be moved by dragging.
 */
public class SelectionModeHandler extends MouseEventHandler {

    /**
     * Constructs a SelectionModeHandler with the given paint and shape chooser
     * models.
     *
     * @param p_model the paint model managing the canvas state and shapes
     * @param s_model the shape chooser model (used for context, though not
     * actively utilized in selection)
     */
    public SelectionModeHandler(PaintModel p_model, ShapeChooserModel s_model) {
        super(p_model, s_model);
    }

    @Override
    void onMouseMoved(MouseEvent mouseEvent) {
    }

    /**
     * Finds and selects the shape nearest to the clicked point. This shape can
     * then be manipulated (moved) on subsequent drag events.
     *
     * @param mouseEvent the mouse press event containing the click position
     */
    @Override
    void onMousePressed(MouseEvent mouseEvent) {
        this.p_model.findClosestToPoint(
                MouseEventHandler.getClickedPoint(mouseEvent)
        );
    }

    /**
     * Updates the position of the currently selected shape to match the mouse
     * coordinates, allowing the user to drag the shape to a new location on the
     * canvas.
     *
     * @param mouseEvent the mouse drag event containing the new cursor position
     */
    @Override
    void onMouseDragged(MouseEvent mouseEvent) {
        this.p_model.adjustPositionSelect(mouseEvent.getX(), mouseEvent.getY());
    }

    @Override
    void onMouseReleased(MouseEvent mouseEvent) {
    }
}
