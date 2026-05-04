package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import user.AuthManager;

import java.io.IOException;
import java.util.Optional;

public class HomeController extends BaseController {
    @FXML private Label staffId;
    @FXML private Button residentButton;
    @FXML private Button financeButton;
    @FXML private Button statisticsButton;
    @FXML private Button logoutButton;
    @FXML private Button staffManagerButton;
    @FXML private Button staffDetailButton;

    public void initialize(){
        this.staffId.setText(AuthManager.getCurrentUser().getUserId());
        this.staffManagerButton.setDisable(!AuthManager.hasAdminRole());
        this.residentButton.setDisable(!AuthManager.hasResidentRole());
        this.financeButton.setDisable(!AuthManager.hasFinanceRole());
    }
    @FXML
    private void staffDetails(ActionEvent event) throws IOException {
        switchScene(event,"StaffDetails");
    }

    @FXML
    private void staffManagement(ActionEvent event) throws IOException {
        switchScene(event,"StaffManagement");
    }

    @FXML
    private void residentButton(ActionEvent event) throws IOException {
        switchScene(event,"Resident");
    }

    @FXML
    private void financeButton(ActionEvent event) throws IOException {
        switchScene(event,"Finance");
    }

    @FXML
    private void statisticsButton(ActionEvent event) throws IOException {
        switchScene(event,"Statistics");
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        if(showConfirmation("Đăng xuất", "Bạn có muốn đăng xuất không ?")){
            AuthManager.logout();
            switchScene(event,"Home");
        }
    }
}
