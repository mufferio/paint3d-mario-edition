package ca.utoronto.utm.assignment2.paint;

import javafx.application.Application;
import javafx.stage.Stage;

public class Paint extends Application {

    PaintModel model; // Model
    View view; // View + Controller

    /**
     * Launches the Paint application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the JavaFX stage abd initializes the application's view.
     * @param stage the primary window for this application
     * @throws Exception if initialization fails
     */
    @Override
    public void start(Stage stage) throws Exception {
        // View + Controller
        this.view = new View(stage);
    }

}
