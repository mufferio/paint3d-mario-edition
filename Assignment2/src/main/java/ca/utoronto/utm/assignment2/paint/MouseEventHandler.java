package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

/**
 * Abstract base class for handling mouse input on the paint canvas.
 * 
 * Subclasses implement the specific behaviour for different interaction
 * modes (for example, drawing normal shapes, drawing polylines, or
 * selecting/moving existing shapes). This class provides common utilities
 * and a single entry point, {@link #handleMouse(MouseEvent)}, which
 * dispatches incoming {@link MouseEvent}s to the appropriate abstract
 * callback methods implemented by subclasses.
 */
public abstract class MouseEventHandler {

    protected PaintModel p_model;
    protected ShapeChooserModel s_model;
    private ShapeFactory s_factory;

    /**
     * Constructs a MouseEventHandler with the provided models.
     *
     * This constructor stores references to the models and creates a
     * {@link ShapeFactory} based on the provided {@code s_model}. It does
     * not register the handler with any event source; registration should
     * be done by the caller after the handler (and its owner) is fully
     * constructed to avoid publishing a partially-initialized object.
     *
     * @param p_model the paint model managing shapes and canvas state
     * @param s_model the shape chooser model controlling drawing mode
     */
    public MouseEventHandler(PaintModel p_model, ShapeChooserModel s_model) {
        this.p_model = p_model;
        this.s_model = s_model;
        this.s_factory = new ShapeFactory(this.s_model);
    }

    /**
     * Single entry point for mouse events; dispatches the event to the
     * appropriate callback method based on the event type.
     *
     * @param mouseEvent the incoming mouse event to handle
     */
    public final void handleMouse(MouseEvent mouseEvent) {
        EventType<MouseEvent> mouseEventType = (EventType<MouseEvent>) mouseEvent.getEventType();

        if (mouseEventType.equals(MouseEvent.MOUSE_PRESSED)) {
            this.onMousePressed(mouseEvent);
        } else if (mouseEventType.equals(MouseEvent.MOUSE_DRAGGED)) {
            this.onMouseDragged(mouseEvent);
        } else if (mouseEventType.equals(MouseEvent.MOUSE_RELEASED)) {
            this.onMouseReleased(mouseEvent);
        } else if (mouseEventType.equals(MouseEvent.MOUSE_MOVED)) {
            this.onMouseMoved(mouseEvent);
        }
    }

    /**
     * Helper that creates and adds a new shape to the paint model using
     * the current mode from {@code s_model} and the provided mouse event
     * position.
     *
     * @param mouseEvent the mouse event whose position will be used to
     *                   initialize the new shape
     */
    protected final void addShapeByMode(MouseEvent mouseEvent) {
        System.out.println("[INFO] added shape " + this.s_model.getMode());
        this.p_model.addShape(
                this.s_factory.initShape(
                        this.s_model.getMode(),
                        MouseEventHandler.getClickedPoint(mouseEvent)
                )
        );
    }

    /**
     * Converts a {@link MouseEvent} into a {@link Point} with the event's
     * x/y coordinates.
     *
     * @param mouseEvent the mouse event to extract coordinates from
     * @return a new {@code Point} representing the mouse coordinates
     */
    protected static final Point getClickedPoint(MouseEvent mouseEvent) {
        return new Point(mouseEvent.getX(), mouseEvent.getY());
    }

    /**
     * Called on mouse move events (no buttons pressed). Subclasses should
     * implement mode-specific move behaviour (for example, showing preview
     * outlines).
     *
     * @param mouseEvent the mouse move event
     */
    abstract void onMouseMoved(MouseEvent mouseEvent);

    /**
     * Called when the mouse is pressed. Subclasses implement press behaviour
     * such as starting a new shape or selecting an existing one.
     *
     * @param mouseEvent the mouse press event
     */
    abstract void onMousePressed(MouseEvent mouseEvent);

    /**
     * Called while the mouse is dragged. Subclasses implement drag behaviour
     * such as resizing a shape or moving a selected shape.
     *
     * @param mouseEvent the mouse drag event
     */
    abstract void onMouseDragged(MouseEvent mouseEvent);

    /**
     * Called when the mouse is released. Subclasses implement release
     * behaviour such as finalizing a drawn shape.
     *
     * @param mouseEvent the mouse release event
     */
    abstract void onMouseReleased(MouseEvent mouseEvent);
}
