package ca.utoronto.utm.assignment2.paint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

public class ShapeChooserController implements EventHandler<ActionEvent> {

    private PaintModel p_model;
    private ShapeChooserModel s_model;

    /**
     * Constructs a ShapeChooserController
     * @param p_model the model for the paint canvas
     * @param s_model the model for the shape selection and options
     */
    public ShapeChooserController(PaintModel p_model, ShapeChooserModel s_model) {
        this.p_model = p_model;
        this.s_model = s_model;
    }

    /**
     * Handles button press events in the shape chooser.
     * Sets the currently pressed shape button in the model.
     * @param event the ActionEvent triggered by a button press
     */
    @Override
    public void handle(ActionEvent event) {
        if (this.s_model.isSelectMode()) {
            return;
        }

        final Button btnPressed = (Button) event.getSource();
        if(!btnPressed.getText().equals("Polyline")){
            p_model.endPolyline();
        }
        String command = btnPressed.getText();
        System.out.println(command);

        this.s_model.setCurrBtnPressed(btnPressed);
    }

    /**
     * Handles toggle events for filled/unfilled shapes
     * @param toggle the ToggleButton representing the fill option
     */
    public void handleToggle(ToggleButton toggle) {
        s_model.setFilled(toggle.isSelected());
    }

    /**
     * Handles the select mode toggle button.
     * Activates or deactivates select mode in model.
     * @param select the ToggleButton representing the select mode
     */
    public void handleSelectBtn(ToggleButton select) {
        p_model.endPolyline();
        boolean isSelected = select.isSelected();
        this.s_model.setSelectMode(select.isSelected());
        if (!isSelected) {
            this.p_model.endSelectMode();
        }
    }

    /**
     * Handles changes to line thickness.
     * @param thickness the new thickness value
     */
    public void handleThicknessChange(double thickness) {
        s_model.setThickness(thickness);
    }
}
