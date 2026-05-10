package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Staff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.StaffManagerRepo;
import services.StaffManagerServices;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StaffManagementController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(StaffManagementController.class);
    private static final DateTimeFormatter DT_FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy").withZone(ZoneId.systemDefault());
    private static final List<String> ROLES =
            List.of("ADMIN", "MANAGER", "RESIDENT", "FINANCE");

    private final StaffManagerServices service =
            new StaffManagerServices(new StaffManagerRepo());
    private final ObservableList<Staff> masterData = FXCollections.observableArrayList();

    /* ── Navbar ── */
    @FXML private VBox navbar;
    @FXML private NavbarController navbarController;

    /* ── Toolbar ── */
    @FXML private TextField  searchField;
    @FXML private ComboBox<String> roleFilter;

    /* ── Table ── */
    @FXML private TableView<Staff>          staffTable;
    @FXML private TableColumn<Staff, String>  colId;
    @FXML private TableColumn<Staff, String>  colName;   // join StaffDetail -> hiển thị qua custom cell
    @FXML private TableColumn<Staff, String>  colRole;
    @FXML private TableColumn<Staff, Instant> colCreated;
    @FXML private TableColumn<Staff, Void>    colAction;

    @FXML
    public void initialize() {
        // Role filter
        roleFilter.getItems().add("Tất cả");
        roleFilter.getItems().addAll(ROLES);
        roleFilter.getSelectionModel().selectFirst();

        // Columns
        colId.setCellValueFactory(new PropertyValueFactory<>("staffId"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        // createdAt -> format
        colCreated.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        colCreated.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Instant item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : DT_FMT.format(item));
            }
        });

        // colName không có PropertyValueFactory trực tiếp vì cần join;
        // tạm thời hiển thị staffId, thay khi load nếu join được
        colName.setCellValueFactory(new PropertyValueFactory<>("staffId"));

        setupActionCol();
        reloadAll();
    }

    /* ── Action column: Đổi role | Đổi mật khẩu | Xóa ── */
    private void setupActionCol() {
        colAction.setCellFactory(param -> new TableCell<>() {
            private final Button btnRole = new Button("Đổi role");
            private final Button btnPass = new Button("Đổi MK");
            private final Button btnDel  = new Button("Xóa");

            {
                btnRole.setStyle("-fx-background-color:#FF9800;-fx-text-fill:white;-fx-font-size:12;");
                btnPass.setStyle("-fx-background-color:#2196F3;-fx-text-fill:white;-fx-font-size:12;");
                btnDel.setStyle("-fx-background-color:#e53935;-fx-text-fill:white;-fx-font-size:12;");

                btnRole.setOnAction(e -> changeRole(getTableView().getItems().get(getIndex())));
                btnPass.setOnAction(e -> changePassword(getTableView().getItems().get(getIndex())));
                btnDel.setOnAction(e  -> confirmDelete(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : new HBox(6, btnRole, btnPass, btnDel));
            }
        });
    }

    /* ── Handlers ── */
    @FXML
    private void handleSearch(ActionEvent event) {
        String q    = searchField.getText().trim();
        String role = roleFilter.getValue();
        List<Staff> result;
        if (role != null && !role.equals("Tất cả")) {
            result = service.findByRole(role);
        } else {
            result = service.search(q);
        }
        staffTable.setItems(FXCollections.observableArrayList(result));
    }

    @FXML
    private void handleReload(ActionEvent event) {
        searchField.clear();
        roleFilter.getSelectionModel().selectFirst();
        reloadAll();
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        showStaffForm(null);
    }

    /* ── Internal actions ── */
    private void changeRole(Staff staff) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(staff.getRole(), ROLES);
        dialog.setTitle("Đổi vai trò");
        dialog.setHeaderText("Nhân viên: " + staff.getStaffId());
        dialog.setContentText("Chọn vai trò mới:");
        dialog.showAndWait().ifPresent(newRole -> {
            if (!newRole.equals(staff.getRole())) {
                if (service.updateRole(staff, newRole)) {
                    staffTable.refresh();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Không thể đổi vai trò!");
                }
            }
        });
    }

    private void changePassword(Staff staff) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Đổi mật khẩu");
        dialog.setHeaderText("Nhân viên: " + staff.getStaffId());
        dialog.setContentText("Mật khẩu mới:");
        dialog.showAndWait().ifPresent(newPass -> {
            if (!newPass.isBlank()) {
                if (!service.updatePassword(staff, newPass)) {
                    showAlert(Alert.AlertType.ERROR, "Không thể đổi mật khẩu!");
                }
            }
        });
    }

    private void confirmDelete(Staff staff) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa");
        alert.setHeaderText(null);
        alert.setContentText("Xóa nhân viên " + staff.getStaffId() + "?");
        ButtonType yes = new ButtonType("Đồng ý");
        ButtonType no  = new ButtonType("Hủy");
        alert.getButtonTypes().setAll(yes, no);
        alert.showAndWait().ifPresent(btn -> {
            if (btn == yes) {
                if (service.deleteStaff(staff)) {
                    masterData.remove(staff);
                } else {
                    showAlert(Alert.AlertType.ERROR, "Không thể xóa! (Có thể đang xóa chính mình)");
                }
            }
        });
    }

    private void showStaffForm(Staff existing) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/StaffFormView.fxml"));
            Parent root = loader.load();
            StaffFormController ctrl = loader.getController();
            if (existing != null) ctrl.setStaff(existing);

            Stage dialog = new Stage();
            dialog.setTitle(existing == null ? "Thêm nhân viên" : "Chi tiết nhân viên");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setScene(new Scene(root));
            dialog.showAndWait();

            StaffFormController.Result r = ctrl.getResult();
            if (r != null) {
                if (service.addStaff(r.staff(), r.detail())) {
                    masterData.add(r.staff());
                } else {
                    showAlert(Alert.AlertType.ERROR, "Không thể thêm! Mã nhân viên đã tồn tại.");
                }
            }
        } catch (Exception e) {
            logger.error("Không mở được StaffFormView: {}", e.getMessage(), e);
        }
    }

    private void reloadAll() {
        masterData.setAll(service.findAll());
        staffTable.setItems(masterData);
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert a = new Alert(type);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
