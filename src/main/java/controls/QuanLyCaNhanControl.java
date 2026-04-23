package controls;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.NguoiDung;
import models.PhienNguoiDung;
import service.NguoiDungService;
import validation.ValidationUtil;
@SuppressWarnings("unused")
public class QuanLyCaNhanControl {
    @SuppressWarnings("unused")
    @FXML private TextField tfMa, tfTen, tfPhone, tfCCCD, tfVaiTro;
    @SuppressWarnings("unused")
    @FXML private PasswordField pfMatKhau, pfMatKhauMoi, pfXacNhan;
    @SuppressWarnings("unused")
    @FXML private Button btnCapNhat, btnDoiMK;
    @FXML
    public void initialize() {
        if(PhienNguoiDung.nguoiDung != null) {
            loadThongTin();
        }
    }
    private void loadThongTin() {
        NguoiDung nd = PhienNguoiDung.nguoiDung;
        tfMa.setText(nd.getMaNguoiDung());
        tfTen.setText(nd.getTenNguoiDung());
        tfPhone.setText(nd.getDienThoai() != null ? nd.getDienThoai() : "");
        tfCCCD.setText(nd.getCCCD() != null ? nd.getCCCD() : "");
        tfVaiTro.setText(nd.getVaiTro() != null ? nd.getVaiTro() : "");
        tfMa.setDisable(true);
        tfVaiTro.setDisable(true);
    }
    @FXML
    public void onCapNhat(ActionEvent event) {
        try {
            if(tfTen.getText().trim().isEmpty()) {
                showWarning("Thông báo", "Tên không được để trống");
                return;
            }
            if(!tfPhone.getText().trim().isEmpty() && !ValidationUtil.isValidPhone(tfPhone.getText())) {
                showWarning("Thông báo", "Số điện thoại không hợp lệ (10-11 số)");
                return;
            }
            if(!tfCCCD.getText().trim().isEmpty() && !ValidationUtil.isValidCCCD(tfCCCD.getText())) {
                showWarning("Thông báo", "CCCD phải có 12 chữ số");
                return;
            }
            NguoiDung nd = new NguoiDung(
                PhienNguoiDung.nguoiDung.getMaNguoiDung(),
                tfTen.getText(),
                PhienNguoiDung.nguoiDung.getMatKhau(),
                PhienNguoiDung.nguoiDung.getVaiTro(),
                tfPhone.getText().isEmpty() ? null : tfPhone.getText(),
                tfCCCD.getText().isEmpty() ? null : tfCCCD.getText()
            );
            NguoiDungService.suaNguoiDung(nd);
            PhienNguoiDung.nguoiDung = nd;
            showSuccess("Thành công", "Cập nhật thông tin thành công");
        } catch(Exception e) {
            showError("Lỗi", e.getMessage());
        }
    }
    @FXML
    public void onDoiMatKhau(ActionEvent event) {
        try {
            String mkCu = pfMatKhau.getText();
            String mkMoi = pfMatKhauMoi.getText();
            String xacNhan = pfXacNhan.getText();
            if(mkCu.isEmpty() || mkMoi.isEmpty() || xacNhan.isEmpty()) {
                showWarning("Thông báo", "Vui lòng điền đầy đủ thông tin");
                return;
            }
            if(!mkCu.equals(PhienNguoiDung.nguoiDung.getMatKhau())) {
                showWarning("Thông báo", "Mật khẩu cũ không chính xác");
                return;
            }
            if(!mkMoi.equals(xacNhan)) {
                showWarning("Thông báo", "Mật khẩu mới không trùng khớp");
                return;
            }
            if(mkMoi.length() < 6 || !mkMoi.matches(".*[A-Z].*") || !mkMoi.matches(".*\\d.*")) {
                showWarning("Thông báo", "Mật khẩu mới phải ít nhất 6 ký tự, chứa ít nhất 1 chữ hoa và 1 số");
                return;
            }
            NguoiDung nd = new NguoiDung(
                PhienNguoiDung.nguoiDung.getMaNguoiDung(),
                PhienNguoiDung.nguoiDung.getTenNguoiDung(),
                mkMoi,
                PhienNguoiDung.nguoiDung.getVaiTro(),
                PhienNguoiDung.nguoiDung.getDienThoai(),
                PhienNguoiDung.nguoiDung.getCCCD()
            );
            NguoiDungService.suaNguoiDung(nd);
            PhienNguoiDung.nguoiDung = nd;
            pfMatKhau.clear();
            pfMatKhauMoi.clear();
            pfXacNhan.clear();
            showSuccess("Thành công", "Đổi mật khẩu thành công");
        } catch(Exception e) {
            showError("Lỗi", e.getMessage());
        }
    }
    private void showSuccess(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    private void showWarning(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    private void showError(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
