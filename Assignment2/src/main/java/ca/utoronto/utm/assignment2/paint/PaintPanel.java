package ca.utoronto.utm.assignment2.paint;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class PaintPanel extends Canvas implements Observer {

    public Circle circle; // This is VERY UGLY, should somehow fix this!!
    public Rectangle rectangle;
    public Squiggle currentSquiggle;
    public Triangle triangle;
    public Square square;
    public Oval oval;

    /**
     * Constructs the PaintPanel canvas and attaches mouse event handlers
     * for the given controller.
     * @param controller the PaintController to handle mouse events
     */
    public PaintPanel(PaintController controller) {
        super(300, 300);

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, controller);
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, controller);
        this.addEventHandler(MouseEvent.MOUSE_MOVED, controller);
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, controller);
        this.addEventHandler(MouseEvent.MOUSE_DRAGGED, controller);
    }

    /**
     * Called automatically when the observed PaintModel is updates.
     * Redraws all shapes, polylines, squiggles and the selection rectangle.
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        PaintModel model = (PaintModel) o;

        GraphicsContext g2d = this.getGraphicsContext2D();
        this.setHeight(model.getHeight());
        this.setWidth(model.getWidth());
        g2d.clearRect(0, 0, this.getWidth(), this.getHeight());

        g2d.setLineDashes(null);

        for (Drawable s : model.getShapes()) {
            if(s.getVis()) {
                s.draw(g2d);
            }
        }
        Drawable selectedShape = model.getSelectedShape();
        if (selectedShape != null) {
            selectedShape.getSelectionRect().drawSelectionBox(g2d);
        }
    }
}
