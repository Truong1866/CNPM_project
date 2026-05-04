package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class BaseController {
    protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);
    protected void switchScene(ActionEvent event, String goTo){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + goTo + "View.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.error("Error loading view: {}",e.getMessage(), e);
        }catch (NullPointerException e){
            logger.error("Incorrect file fxml path: {}",e.getMessage());
        }
    }
    protected FXMLLoader getLoader(String fxmlPath) {
        return new FXMLLoader(getClass().getResource(fxmlPath));
    }

    protected boolean showConfirmation(String title, String message) {
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
}
