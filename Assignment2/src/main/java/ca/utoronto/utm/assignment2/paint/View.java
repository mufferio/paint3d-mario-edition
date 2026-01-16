package ca.utoronto.utm.assignment2.paint;

import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;

public class View {

    /**
     * Constructs the View and inittializes all UI components, controllers,
     * models, observers, styles, audio and resize behaviour for the application.
     * @param stage the primary stage provided by JavaFX on application start
     */
    public View(Stage stage) {
        PaintModel p_model = new PaintModel();
        ShapeChooserModel s_model = new ShapeChooserModel();

        ShapeChooserController s_controller = new ShapeChooserController(p_model, s_model);
        ShapeChooserView s_view = new ShapeChooserView(s_controller);
        s_model.addObserver(s_view);

        PaintController p_controller = new PaintController(p_model, s_model);
        PaintPanel p_view = new PaintPanel(p_controller);
        p_model.addObserver(p_view);

        MenuController m_controller = new MenuController(p_model, p_view, stage);
        MenuView m_view = new MenuView(m_controller);

        ColourSelector colourSelector = new ColourSelector(s_model);

        VBox leftPanel = new VBox(10); // 10px spacing
        leftPanel.getChildren().addAll(s_view, colourSelector);
        BorderPane root = new BorderPane();
        root.setTop(m_view.getMenuBar());
        root.setCenter(p_view);
        root.setLeft(leftPanel);
        Scene scene = new Scene(root);

        stage.setScene(scene);
        URL css = Objects.requireNonNull(
                getClass().getResource("/paint/mario-theme.css"),
                "/paint/mario-theme.css not found on classpath"
        );
        scene.getStylesheets().add(css.toExternalForm());

        stage.setTitle("Super Mario - Paint Edition");

        Image cursor = new Image(getClass().getResourceAsStream("/paint/repo_420_cursor.png"));
        scene.setCursor(new ImageCursor(cursor));

        PopupImageMaker popupImage = PopupImageMaker.show("/paint/repo_420.png");
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> {
            popupImage.close();
            stage.show();
        });
        delay.play();

        // music here
        BackgroundMusic backgroundMusic = new BackgroundMusic("/paint/music.wav");
        backgroundMusic.playLoop();

        ChangeListener<Number> changeListener = new ChangeListener<Number>() {
            private double oldHeight = stage.getHeight();
            private double oldWidth = stage.getWidth();

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (oldHeight != stage.getHeight() || oldWidth != stage.getWidth()) {
                    p_model.setWidth(stage.getWidth());
                    p_model.setHeight(stage.getHeight());
                    oldHeight = stage.getHeight();
                    oldWidth = stage.getWidth();
                }
            }
        };
        stage.widthProperty().addListener(changeListener);
        stage.heightProperty().addListener(changeListener);
    }
}