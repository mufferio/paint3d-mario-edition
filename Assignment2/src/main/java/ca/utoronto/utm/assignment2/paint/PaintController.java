package ca.utoronto.utm.assignment2.paint;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class PaintController implements EventHandler<MouseEvent> {

    private StrategyDecider decider;

    /**
     * Constructs a PaintController that listens to mouse events and updates the
     * model based on the selected drawing mode.
     *
     * @param p_model the PaintModel
     * @param s_model the ShapeChooserModel
     */
    public PaintController(PaintModel p_model, ShapeChooserModel s_model) {
        this.decider = new StrategyDecider(p_model, s_model);
    }

    /**
     * Handles all mouse events on the canvas.
     *
     * @param mouseEvent the mouse event being processed
     */
    @Override
    public void handle(MouseEvent mouseEvent) {
        // Later when we learn about inner classes...
        // https://docs.oracle.com/javafx/2/events/DraggablePanelsExample.java.htm

        // From leongc17: 
        // I'm very sorry that I didn't notice this until the end,
        // since I have separated the switch case to another method.
        // Anyways I have implemented this in a mixed design pattern      
        this.decider.decide(mouseEvent);
    }
}
