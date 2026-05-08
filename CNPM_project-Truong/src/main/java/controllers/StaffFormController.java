package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Staff;
import models.StaffDetail;

import java.time.LocalDate;
import java.util.List;

public class StaffFormController {

    /** Kết quả trả về khi người dùng bấm Lưu */
    public record Result(Staff staff, StaffDetail detail) {}

    private static final List<String> ROLES = List.of("ADMIN", "MANAGER", "RESIDENT", "FINANCE");

    @FXML private TextField     idField;
    @FXML private PasswordField passField;
    @FXML private ComboBox<String> roleBox;
    @FXML private TextField     nameField;
    @FXML private DatePicker    birthdayPicker;
    @FXML private TextField     addressField;
    @FXML private Label         errorLabel;
    @FXML private Button        saveButton;

    private Result result = null;

    @FXML
    public void initialize() {
        roleBox.getItems().addAll(ROLES);
        roleBox.getSelectionModel().select("RESIDENT");
    }

    /** Điền sẵn dữ liệu khi xem/sửa */
    public void setStaff(Staff staff) {
        idField.setText(staff.getStaffId());
        idField.setDisable(true);
        roleBox.setValue(staff.getRole());
        // password ẩn đi khi view mode
        passField.setDisable(true);
        passField.setPromptText("Dùng chức năng 'Đổi mật khẩu'");
    }

    @FXML
    private void handleSave(ActionEvent event) {
        errorLabel.setText("");

        String id   = idField.getText().trim();
        String pass = passField.getText();
        String role = roleBox.getValue();
        String name = nameField.getText().trim();

        if (id.isEmpty())   { errorLabel.setText("Mã nhân viên không được trống!"); return; }
        if (pass.isEmpty() && !idField.isDisabled()) {
            errorLabel.setText("Mật khẩu không được trống!"); return;
        }
        if (role == null)   { errorLabel.setText("Vui lòng chọn vai trò!"); return; }
        if (name.isEmpty()) { errorLabel.setText("Họ tên không được trống!"); return; }

        Staff staff = new Staff();
        staff.setStaffId(id);
        staff.setPassword(pass);
        staff.setRole(role);

        StaffDetail detail = new StaffDetail();
        detail.setName(name);
        detail.setBirthday(birthdayPicker.getValue() != null
                ? birthdayPicker.getValue() : LocalDate.now());
        String addr = addressField.getText().trim();
        detail.setAddress(addr.isEmpty() ? null : addr);

        result = new Result(staff, detail);
        closeStage();
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        result = null;
        closeStage();
    }

    public Result getResult() { return result; }

    private void closeStage() {
        ((Stage) saveButton.getScene().getWindow()).close();
    }
}
