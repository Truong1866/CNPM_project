package controllers;

import javafx.application.Platform;
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
    protected void switchScene(ActionEvent event, String goTo) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + goTo + "View.fxml"));
                Parent root = loader.load();

                // Lấy stage hiện tại một cách an toàn
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
                logger.info("Switched to scene: {}", goTo);
            } catch (IOException e) {
                logger.error("Error loading view {}: {}", goTo, e.getMessage(), e);
            } catch (NullPointerException e) {
                logger.error("Incorrect FXML path for: {}", goTo);
            }
        });
    }

    // Phương thức hỗ trợ nạp controller nếu bạn cần truyền dữ liệu trực tiếp thay vì qua AuthManager
    protected <T> T loadSceneWithController(ActionEvent event, String goTo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + goTo + "View.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            return loader.getController();
        } catch (IOException e) {
            logger.error("Failed to load scene with controller: {}", e.getMessage());
            return null;
        }
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
