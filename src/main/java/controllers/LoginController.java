package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import repository.StaffRepo;
import service.StaffServices;

public class LoginController extends BaseController {

    private final StaffServices staffServices;

    @FXML private TextField userField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label noticeLabel;

    public LoginController() {
        StaffRepo staffRepo = new StaffRepo();
        this.staffServices = new StaffServices(staffRepo);
    }

    public void initialize() {
        this.noticeLabel.setText("");
        this.loginButton.disableProperty().bind(
                userField.textProperty().isEmpty().or(passwordField.textProperty().isEmpty())
        );
    }

    @FXML
    private void loginButtonAction(ActionEvent event) throws Exception {
        String username = userField.getText();
        String password = passwordField.getText();
        if(staffServices.loginServices(username, password)){
            switchScene(event,"Home");
        }
        else{
            this.noticeLabel.setText("Đăng nhập không thành công");
        }
    }
}
