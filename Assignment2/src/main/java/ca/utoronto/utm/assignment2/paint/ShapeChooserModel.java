package ca.utoronto.utm.assignment2.paint;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class ShapeChooserModel extends Observable {
    private Button lastBtnPressed;
    private Button currBtnPressed;
    private boolean filled = true;
    private boolean selectMode = false;
    private double thickness = 1.0;
    private Color btnIconColor;

    /**
     * Sets the colour used for the button icons and the notifies observers.
     * @param btnIconColor the new Colour for button icons
     */
    public void setBtnIconColor(Color btnIconColor) {
        this.btnIconColor = btnIconColor;
        this.notif();
    }

    /**
     * @return the current colour of the button icons
     */
    public Color getBtnIconColor() {
        return this.btnIconColor;
    }

    /**
     * @return the name of the current button being pressed
     */
    public String getMode() {
        if (this.currBtnPressed == null) {
            return "";
        }

        return this.currBtnPressed.getText();
    }

    /**
     * @return the last button that was pressed
     */
    public Button getLastBtnPressed() {
        return this.lastBtnPressed;
    }

    /**
     * @return the button currently pressed
     */
    public Button getCurrBtnPressed() {
        return this.currBtnPressed;
    }

    /**
     * Sets the currently pressed button and updates the last pressed button
     * @param btn the Button that was pressed
     */
    public void setCurrBtnPressed(Button btn) {
        this.lastBtnPressed = this.currBtnPressed;
        this.currBtnPressed = btn;
        this.notif();
    }

    /**
     * Sets whether shapes should be filled or outlined.
     * @param filled true if shapes should be filled, false if outlined
     */
    public void setFilled(boolean filled) {
        this.filled = filled;
        this.notif();
    }

    /**
     * @return true if shapes are set to be filled, false if outlined
     */
    public boolean isFilled() {
        return this.filled;
    }

    /**
     * Sets whether the shape is in select mode.
     * @param SelectMode true to enable select mode, false to disable
     */
    public void setSelectMode(boolean SelectMode) {
        this.selectMode = SelectMode;
    }

    /**
     * @return true if select mode is enabled, false otherwise
     */
    public boolean isSelectMode() {
        return this.selectMode;
    }

    /**
     * Sets the line thickness for shapes
     * @param thickness the new line thickness
     */
    public void setThickness(double thickness) {
        this.thickness = thickness;
        this.notif();
    }

    /**
     * @return the current line thickness for shapes
     */
    public double getThickness() {
        return this.thickness;
    }

    /**
     * Notifies all observers of a state change.
     */
    private void notif() {
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Adds an observer and immediately notifies it of the current state
     * @param o   an observer to be added.
     */
    public void addObserver(Observer o) {
        super.addObserver(o);
        this.notif();
    }
}
