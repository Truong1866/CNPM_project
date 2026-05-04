package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import user.AuthManager;

import java.io.IOException;
import java.util.Optional;

public class HomeController {
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
        FXMLLoader loader = new FXMLLoader(HomeController.class.getResource("/view/StaffDetailView.fxml"));
        Parent root =  (Parent) loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) staffDetailButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void staffManagement(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(HomeController.class.getResource("/view/StaffManagerView.fxml"));
        Parent root =  (Parent) loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) staffManagerButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void residentButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(HomeController.class.getResource("/view/ResidentView.fxml"));
        Parent root =  (Parent) loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) residentButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void financeButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(HomeController.class.getResource("/view/FinanceView.fxml"));
        Parent root =  (Parent) loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) financeButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void statisticsButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(HomeController.class.getResource("/view/StatisticsView.fxml"));
        Parent root =  (Parent) loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) statisticsButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        ButtonType buttonYes = new ButtonType("Đồng ý");
        ButtonType buttonNo = new ButtonType("Hủy bỏ");
        alert.getButtonTypes().setAll(buttonYes, buttonNo);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == buttonYes;
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        if(this.showConfirmation("Đăng xuất", "Bạn có muốn đăng xuất không ?")){
            AuthManager.logout();
            FXMLLoader loader = new FXMLLoader(HomeController.class.getResource("/view/LoginView.fxml"));
            Parent root =  (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
}
