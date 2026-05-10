package controllers;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import user.AuthManager;

import java.io.IOException;
import java.util.Optional;

public class NavbarController{
    private static final Logger logger = LoggerFactory.getLogger(NavbarController.class);
    private final double maxSize = 400;
    private final double minSize = 100;
    @FXML private VBox navbar;
    @FXML private Button homeButton;
    @FXML private Button residentButton;
    @FXML private Button financeButton;
    @FXML private Button statisticsButton;
    @FXML private Button staffManagerButton;
    @FXML private Button staffDetailButton;
    @FXML private Button logoutButton;
    @FXML private Rectangle rectangle1;
    @FXML private Rectangle rectangle2;
    @FXML private Rectangle rectangle3;
    @FXML private Rectangle rectangle4;
    
    public void initialize(){
        residentButton.setDisable(!AuthManager.hasResidentRole());
        financeButton.setDisable(!AuthManager.hasFinanceRole());
        staffManagerButton.setDisable(!AuthManager.hasAdminRole());
        animateSidebarMin();
        navbar.setOnMouseEntered(event -> {
            animateSidebarMax();
        });
        navbar.setOnMouseExited(event -> {
            animateSidebarMin();

        });
    }

    @FXML
    private void homeButtonAction(ActionEvent event) {
        switchScene(event, "Home");
    }

    @FXML
    private void residentButtonAction(ActionEvent event) {
        switchScene(event, "Resident");
    }

    @FXML
    private void financeButtonAction(ActionEvent event) {
        switchScene(event, "Finance");
    }

    @FXML
    private void statisticsButtonAction(ActionEvent event) {
        switchScene(event, "Statistics");
    }

    @FXML
    private void staffManagerButtonAction(ActionEvent event) {
        switchScene(event, "StaffManager");
    }

    @FXML
    private void staffDetailButtonAction(ActionEvent event) {
        switchScene(event, "StaffDetail");
    }

    @FXML
    private void logoutButtonAction(ActionEvent event) throws IOException {
        try{
            if(showConfirmation("Đăng xuất", "Bạn có muốn đăng xuất không ?")){
                AuthManager.logout();
                logger.info("logout successful");
                switchScene(event,"Login");
            }else{
                logger.info("User not logout");
            }
        }catch(Exception e){
            logger.error("logout failed; {}", e.getMessage(),e);
        }
    }

    private void animateSidebarMax() {
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(navbar.prefWidthProperty(), maxSize);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(200), keyValue); // 200ms cho hiệu ứng mượt
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        changeButton(homeButton, true);
        changeButton(residentButton, true);
        changeButton(financeButton, true);
        changeButton(statisticsButton, true);
        changeButton(staffManagerButton, true);
        changeButton(staffDetailButton, true);
        changeButton(logoutButton, true);
        changeRectangle(rectangle1, true);
        changeRectangle(rectangle2, true);
        changeRectangle(rectangle3, true);
        changeRectangle(rectangle4, true);
    }

    private void animateSidebarMin() {
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(navbar.prefWidthProperty(), maxSize);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(200), keyValue); // 200ms cho hiệu ứng mượt
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        changeButton(homeButton, false);
        changeButton(residentButton, false);
        changeButton(financeButton, false);
        changeButton(statisticsButton, false);
        changeButton(staffManagerButton, false);
        changeButton(staffDetailButton, false);
        changeButton(logoutButton, false);
        changeRectangle(rectangle1, false);
        changeRectangle(rectangle2, false);
        changeRectangle(rectangle3, false);
        changeRectangle(rectangle4, false);
    }

    private void switchScene(ActionEvent event, String goTo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + goTo + "View.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            logger.error("Error when load FXML file: {}", goTo, e.getMessage());
        }
    }
    private Boolean showConfirmation(String title, String message) {
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

    private void changeButton(Button button, boolean inFull){
        if(inFull){
            button.setContentDisplay(ContentDisplay.LEFT);
            button.setPrefWidth(400.0);
        }else{
            button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            button.setPrefWidth(100.0);
        }
    }
    
    private void changeRectangle(Rectangle rectangle, boolean inFull){
        if(inFull){
            rectangle.setWidth(400.0);
        }else{
            rectangle.setWidth(105.0);
        }
    }
}
