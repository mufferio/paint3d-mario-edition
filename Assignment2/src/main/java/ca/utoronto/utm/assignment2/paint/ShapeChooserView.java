package ca.utoronto.utm.assignment2.paint;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class ShapeChooserView extends GridPane implements Observer {

    private Slider thicknessSlider;

    private ArrayList<Button> buttons = new ArrayList<Button>();

    private BackgroundMusic clickSound;

    /**
     * Constructs the shape chooser view with all shape buttons, toggles and thickness slider.
     * @param controller the ShapeChooserController that handles buttton and slider events
     */
    public ShapeChooserView(ShapeChooserController controller) {

        this.getStyleClass().add("shape-chooser");

        clickSound = new BackgroundMusic("/paint/repo_420_button_sound.wav");

        String[] buttonLabels = {"Circle", "Rectangle", "Square", "Squiggle", "Polyline", "Triangle", "Oval"};

        Circle circle = new Circle(7, Color.BLUE);
        Rectangle rectangle = new Rectangle(15, 7, Color.BLUE);
        Rectangle square = new Rectangle(10, 10, Color.BLUE);
        CubicCurve squiggle = new CubicCurve(0, 0, 5, -20, 12, 20, 15, 0);
        squiggle.setStroke(Color.BLUE);
        squiggle.setStrokeWidth(1);
        squiggle.setFill(Color.TRANSPARENT);
        Polyline polyline = new Polyline(0, 0, 3, -10, 7, 6, 10, 0);
        polyline.setStroke(Color.BLUE);
        Polyline triangle = new Polyline(0, 0, 5, -10, 10, 0);
        triangle.setFill(Color.BLUE);
        triangle.setStroke(Color.BLUE);
        Ellipse oval = new Ellipse(10, 10, 6, 8);
        oval.setFill(Color.BLUE);

        ArrayList<Shape> buttonShapes = new ArrayList<>();
        buttonShapes.add(circle);
        buttonShapes.add(rectangle);
        buttonShapes.add(square);
        buttonShapes.add(squiggle);
        buttonShapes.add(polyline);
        buttonShapes.add(triangle);
        buttonShapes.add(oval);

        int row = 0;
        for (Shape shape : buttonShapes) {
            Button button = new Button(buttonLabels[row]);
            button.setGraphic(shape);
            button.setMinWidth(110);
            button.setPrefWidth(110);

            button.getStyleClass().add("tool-button");

            this.add(button, 0, row);
            button.setOnAction(e -> {
                clickSound.playOnce();
                controller.handle(e);
            });
            if (row == 0) {
                button.fire();
            }
            row++;
            this.buttons.add(button);
        }

        ToggleButton toggleButton = new ToggleButton("Filled");
        toggleButton.setSelected(true);
        toggleButton.setMinWidth(110);
        toggleButton.setPrefWidth(110);
        toggleButton.setOnAction(e -> {
            clickSound.playOnce();
            controller.handleToggle(toggleButton);
        });

        toggleButton.getStyleClass().addAll("tool-button", "tool-toggle");

        this.add(toggleButton, 0, row++);

        ToggleButton selectModeBtn = new ToggleButton("Select");
        selectModeBtn.setMinWidth(110);
        selectModeBtn.setPrefWidth(110);
        selectModeBtn.setOnAction(e -> {
            clickSound.playOnce();
            controller.handleSelectBtn(selectModeBtn);
        });

        selectModeBtn.getStyleClass().addAll("tool-button", "tool-toggle");

        this.add(selectModeBtn, 0, row++);

        thicknessSlider = new Slider(1, 21, 1);
        thicknessSlider.setShowTickMarks(true);
        thicknessSlider.setMajorTickUnit(5);
        thicknessSlider.setSnapToTicks(true);
        thicknessSlider.setBlockIncrement(1);
        thicknessSlider.setMaxWidth(110);
        thicknessSlider.valueProperty().addListener((ov, oldValue, newValue)
                -> controller.handleThicknessChange(newValue.doubleValue()));

        thicknessSlider.getStyleClass().add("thickness-slider");

        GridPane.setMargin(thicknessSlider, new Insets(10, 0, 0, 0));
        this.add(thicknessSlider, 0, row);

    }

    /**
     * Updates button colours, the currently pressed button highlight,
     * and the thickness slider value to reflect the model state.
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        ShapeChooserModel model = (ShapeChooserModel) o;

        for (Button button : buttons) {
            Shape shape = (Shape) button.getGraphic();
            if (shape instanceof CubicCurve || shape instanceof Polyline && ((Polyline) shape).getPoints().size() == 8) {
                shape.setStroke(model.getBtnIconColor());
                button.setGraphic(shape);
            } else {
                shape.setFill(model.getBtnIconColor());
                shape.setStroke(model.getBtnIconColor());
                button.setGraphic(shape);
            }
        }
        Button currBtnPressed = model.getCurrBtnPressed();
        Button lastBtnPressed = model.getLastBtnPressed();

        if (lastBtnPressed != null && lastBtnPressed != currBtnPressed) {
            lastBtnPressed.getStyleClass().remove("tool-button-selected");
        }
        if (!currBtnPressed.getStyleClass().contains("tool-button-selected")) {
            currBtnPressed.getStyleClass().add("tool-button-selected");
        }

        thicknessSlider.setValue(model.getThickness());
    }
}
