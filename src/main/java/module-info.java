module application.cnpm_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens application.cnpm_project to javafx.fxml;
    exports application.cnpm_project;
}