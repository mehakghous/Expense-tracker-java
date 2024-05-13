module demo1 {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    opens app.controllers to javafx.fxml, javafx.base;
    exports app;
    opens app to javafx.fxml;
    opens app.models to javafx.base, javafx.fxml;
}