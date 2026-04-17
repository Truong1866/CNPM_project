module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires javafx.graphics;

    opens models to javafx.base, javafx.fxml;
    opens controls to javafx.fxml;
    opens application to javafx.fxml;
    exports application;
}