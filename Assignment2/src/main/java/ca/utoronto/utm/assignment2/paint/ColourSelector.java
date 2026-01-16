package ca.utoronto.utm.assignment2.paint;

import javafx.scene.control.ColorPicker;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class ColourSelector extends GridPane {

    private ColorPicker colourPicker;
    private static Color currentColor;
    private final ShapeChooserModel s_model;

    /**
     * Constructs a new ColourSelector, initializes its colour picker,
     * and updates the associated ShapeChooserModel with default colour.
     * @param s_model the model responsible for updating icon colours in the UI
     */
    public ColourSelector(ShapeChooserModel s_model) {
        this.s_model = s_model;
        this.currentColor = Color.BLUE;
        colourPicker = new ColorPicker(Color.BLUE);
        colourPicker.setMinWidth(110);
        colourPicker.setPrefWidth(110);

        colourPicker.getStyleClass().addAll("tool-button", "mario-color-picker");


        colourPicker.setOnAction(e -> {
            currentColor = colourPicker.getValue();
            this.s_model.setBtnIconColor(currentColor);
            System.out.println("Selected color: " + currentColor);
        });
        s_model.setBtnIconColor(currentColor);
        this.add(colourPicker, 0, 0);
    }

    /**
     * @return the colour currently selected
     */
    public static Color getColour() {
        return currentColor;
    }
}
