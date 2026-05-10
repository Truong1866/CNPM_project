package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import models.Resident;

public class ResidentController extends BaseController {

    /* ── Navbar ── */
    @FXML private VBox navbar;
    @FXML private NavbarController navbarController;

    /* ── Toolbar ── */
    @FXML private TextField searchField;

    /* ── Sub-controller của ResidentTable.fxml ── */
    @FXML private ResidentTableController residentTableController;

    /* ────────────────────────────────────────────
       Tìm kiếm
    ──────────────────────────────────────────── */
    @FXML
    private void handleSearch(ActionEvent event) {
        String query = searchField.getText().trim();
        residentTableController.findResident(query);
    }

    /* ────────────────────────────────────────────
       Tải lại toàn bộ danh sách
    ──────────────────────────────────────────── */
    @FXML
    private void handleReload(ActionEvent event) {
        searchField.clear();
        residentTableController.reloadAll();
    }

    /* ────────────────────────────────────────────
       Thêm cư dân mới — mở dialog nhập liệu
    ──────────────────────────────────────────── */
    @FXML
    private void handleAdd(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/ResidentFormView.fxml"));
            javafx.scene.Parent root = loader.load();

            ResidentFormController formController = loader.getController();

            javafx.stage.Stage dialog = new javafx.stage.Stage();
            dialog.setTitle("Thêm cư dân mới");
            dialog.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            dialog.setScene(new javafx.scene.Scene(root));
            dialog.showAndWait();

            Resident newResident = formController.getResult();
            if (newResident != null) {
                residentTableController.addResident(newResident);
            }
        } catch (Exception e) {
            logger.error("Không mở được form thêm cư dân: {}", e.getMessage(), e);
        }
    }
}
