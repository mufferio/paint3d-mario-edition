package ca.utoronto.utm.assignment2.paint;

import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.Objects;

public final class PopupImageMaker {
    private final Stage stage;

    private PopupImageMaker(Stage stage) {
        this.stage = stage;
    }

    /**
     * Shows a splash image in an undecorated stage.
     * @param resourcePath the path to the image resource
     * @return a PopupImageMaker instance controlling the popup stage
     */
    public static PopupImageMaker show(String resourcePath) {
        Stage popupStage = new Stage(StageStyle.UNDECORATED);
        String logoUrl = Objects.requireNonNull(
                PopupImageMaker.class.getResource("/paint/repo_420.png"),
                "Splash image not found on classpath: /paint/repo_420.png"
        ).toExternalForm();
        Image image = new Image(logoUrl);
        if (image.isError()) {
            throw new IllegalStateException("failed to load splash image", image.getException());
        }
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);

        BorderPane pane = new BorderPane(imageView);
        pane.setStyle("-fx-background-color: transparent;");
        Scene splashScene = new Scene(pane);
        popupStage.setScene(splashScene);
        popupStage.setAlwaysOnTop(true);
        popupStage.centerOnScreen();
        popupStage.show();
        return new PopupImageMaker(popupStage);
    }

    /**
     * Closes the popup stage after a specified duration.
     * @param d the duration to wait beforew closing the stage
     */
    public void closeAfter(Duration d) {
        PauseTransition pause = new PauseTransition(d);
        pause.setOnFinished(e -> stage.close());
        pause.play();
    }

    /**
     * closes the popup stage immediatly.
     */
    public void close() {
        stage.close();
    }

    /**
     * @return the stage instance representing the popup
     */
    public Stage getStage() {
        return stage;
    }
}
