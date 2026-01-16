module ca.utoronto.utm.assignment2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.desktop;


    opens ca.utoronto.utm.assignment2 to javafx.fxml;
    exports ca.utoronto.utm.assignment2.scribble;
    exports ca.utoronto.utm.assignment2.paint;


}