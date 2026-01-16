package ca.utoronto.utm.assignment2.paint;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuController implements EventHandler<ActionEvent> {

    private PaintModel p_model;
    private PaintPanel p_view;
    private Stage stage;

    /**
     * Constructs a MenuController that handles menu item actions for the
     * application
     *
     * @param p_model the model containing the shapes application state
     * @param p_view the view used for rendering and snapshotting the canvas
     * @param stage the primary application window
     */
    public MenuController(PaintModel p_model, PaintPanel p_view, Stage stage) {
        this.p_model = p_model;
        this.p_view = p_view;
        this.stage = stage;
    }

    /**
     * Handles menu item actions
     *
     * @param event the menu action event triggered by MenuItem
     */
    @Override
    public void handle(ActionEvent event) {
        System.out.println(((MenuItem) event.getSource()).getText());
        String command = ((MenuItem) event.getSource()).getText();
        System.out.println(command);
        if (command.equals("Exit")) {
            Platform.exit();
        }
        if (command.equals("Save")) {
            this.handleSave();
        }
        if (command.equals("Open")) {
            this.handleOpen();
        }
        if (command.equals("New")) {
            this.p_model.clearShapes();
            this.p_model.clear_undo();
            this.p_model.clear_redo();
        }
        if (command.equals("Undo")) {
            this.p_model.undo();
        }
        if (command.equals("Redo")) {
            this.p_model.redo();
        }
        if (command.equals("Cut")) {
            if (this.p_model.getSelectedShape() != null) {
                this.p_model.cutShape(this.p_model.getSelectedShape());
            }
        }
        if (command.equals("Copy")) {
            if (this.p_model.getSelectedShape() != null) {
                this.p_model.copyShape(this.p_model.getSelectedShape());
            }
        }
        if (command.equals("Paste")) {
            if (this.p_model.getCopy() != null) {
                this.p_model.addShape(this.p_model.getCopy().makeCopy());
            }
        }
        if (command.equals("Delete")) {
            if (this.p_model.getSelectedShape() != null) {
                this.p_model.deleteShape(this.p_model.getSelectedShape());
            }
        }
    }

    /**
     * Handles saving the current canvas as a PNG.
     */
    private void handleSave() {
        FileChooser save = new FileChooser();
        save.setTitle("Save as PNG");
        save.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        save.setInitialDirectory(new File(System.getProperty("user.home")));

        File file = save.showSaveDialog(stage);
        if (file == null) {
            return;
        }
        WritableImage snapshot = this.p_view.snapshot(new SnapshotParameters(), null);
        int width = (int) snapshot.getWidth();
        int height = (int) snapshot.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                javafx.scene.paint.Color fxColor = snapshot.getPixelReader().getColor(x, y);
                int argb = ((int) (fxColor.getOpacity() * 255) << 24)
                        | ((int) (fxColor.getRed() * 255) << 16)
                        | ((int) (fxColor.getGreen() * 255) << 8)
                        | ((int) (fxColor.getBlue() * 255));
                bufferedImage.setRGB(x, y, argb);
            }
        }
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles opening a PNG image and loading it onto the canvas.
     */
    private void handleOpen() {
        FileChooser open = new FileChooser();
        open.setTitle("Open PNG Image");
        open.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
        open.setInitialDirectory(new File(System.getProperty("user.home")));

        File file = open.showOpenDialog(stage);
        if (file == null) {
            return;
        }

        try {
            javafx.scene.image.Image image = new javafx.scene.image.Image(file.toURI().toString());
            this.p_model.clearShapes();
            p_model.addShape(new ImageShape(image, p_view.getWidth(), p_view.getHeight()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
