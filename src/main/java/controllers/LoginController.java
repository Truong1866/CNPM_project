package controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import repository.StaffRepo;
import services.StaffServices;

/**
 * Controller màn hình đăng nhập.
 *
 * Sau khi đăng nhập thành công dùng {@code clearAndNavigate("Home")}
 * để xóa stack — người dùng sẽ không thể back về màn Login.
 */
public class LoginController extends BaseController {

    private final StaffServices staffServices;

    @FXML private TextField     userField;
    @FXML private PasswordField passwordField;
    @FXML private Button        loginButton;
    @FXML private Label         noticeLabel;

    public LoginController() {
        StaffRepo staffRepo = new StaffRepo();
        this.staffServices  = new StaffServices(staffRepo);
    }

    public void initialize() {
        noticeLabel.setText("");
        loginButton.disableProperty().bind(
            userField.textProperty().isEmpty()
                .or(passwordField.textProperty().isEmpty())
        );
    }

    @FXML
    private void loginButtonAction(ActionEvent event) throws Exception {
        String username = userField.getText();
        String password = passwordField.getText();

        staffServices.loginServices(username, password).thenAccept(isSuccess -> {
            if (isSuccess) {
                // Xóa stack — không muốn back về Login từ Home
                clearAndNavigate("Home");
            } else {
                Platform.runLater(() ->
                    noticeLabel.setText("Đăng nhập không thành công"));
            }
        });
    }
}
