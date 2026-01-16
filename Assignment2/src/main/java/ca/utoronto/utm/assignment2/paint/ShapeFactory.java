package ca.utoronto.utm.assignment2.paint;

import javafx.scene.paint.Color;

/**
 * Factory responsible for creating {@link Drawable} shape instances based on
 * the current settings in a {@link ShapeChooserModel}.
 *
 * The factory consults the {@code ShapeChooserModel} for properties such as
 * whether shapes should be filled and the current stroke thickness, and uses
 * {@link ColourSelector} to obtain the current drawing colour.
 */
public class ShapeFactory {

    private final ShapeChooserModel s_model;

    /**
     * Constructs a ShapeFactory bound to a specific {@link ShapeChooserModel}.
     *
     * @param s_model the shape chooser model used to determine shape properties
     */
    public ShapeFactory(ShapeChooserModel s_model) {
        this.s_model = s_model;
    }

    /**
     * Create and initialize a {@link Drawable} instance for the given shape
     * name at the provided point.
     *
     * The method reads the current colour via
     * {@link ColourSelector#getColour()}, and styling options (filled,
     * thickness) from the bound {@link ShapeChooserModel}. For shapes that
     * require additional initial points (for example, a {@link Polyline}), this
     * method performs any necessary setup so the returned object is ready to be
     * added to the {@link PaintModel}.
     *
     * @param name the name of the shape to create (e.g. "Circle", "Rectangle")
     * @param p the initial point where the shape should be created
     * @return a new {@link Drawable} instance configured according to current
     * settings
     * @throws RuntimeException if the provided {@code name} does not match a
     * known shape
     */
    public Drawable initShape(String name, Point p) {
        Color color = ColourSelector.getColour();
        boolean filled = s_model.isFilled();
        double thickness = s_model.getThickness();

        switch (name) {
            case "Circle" -> {
                return new Circle(p, 0, color, filled, thickness);
            }

            case "Rectangle" -> {
                return new Rectangle(p, p, color, filled, thickness);
            }

            case "Square" -> {
                return new Square(p, p, color, filled, thickness);
            }

            case "Squiggle" -> {
                return new Squiggle(color, thickness);
            }

            case "Polyline" -> {
                Polyline polyline = new Polyline(color, thickness);
                polyline.addPoint(p);
                polyline.addPoint(p.copy());
                return polyline;
            }

            case "Triangle" -> {
                return new Triangle(p, p, color, filled, thickness);
            }

            case "Oval" -> {
                return new Oval(p, p, color, filled, thickness);
            }

            default ->
                throw new RuntimeException("Shape doesn't exist");
        }
    }
}
