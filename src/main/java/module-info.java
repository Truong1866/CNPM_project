module application.quan_ly_chung_cu_blue_moon {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens models;
    opens controller;
    opens application;
    opens controller.nhankhau;
    opens controller.noptien;
    opens controller.hokhau;
    opens controller.khoanthu;
    exports application;
}