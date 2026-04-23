package controls;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.*;
import service.*;
import validation.ValidationUtil;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
public class ThuPhiControl {
    @FXML private ComboBox<String> cmbHoKhau;
    @FXML private TableView<HoKhau_KhoanThu> tvKhoanThu;
    @FXML private TableColumn<HoKhau_KhoanThu, String> colKhoan;
    @FXML private TableColumn<HoKhau_KhoanThu, String> colTenKhoan;
    @FXML private TableColumn<HoKhau_KhoanThu, Integer> colTienPhaiNop;
    @FXML private TableColumn<HoKhau_KhoanThu, Integer> colDaNop;
    @FXML private TableColumn<HoKhau_KhoanThu, Integer> colConNo;
    @FXML private TableColumn<HoKhau_KhoanThu, String> colTrangThai;
    @FXML private TextField tfSoTien;
    @FXML private DatePicker dpNgayThu;
    @FXML private TextArea taGhiChu;
    @FXML private Label lbThongTin;
    @FXML private Button btnLuu, btnLamMoi;
    private String selectedHo = null;
    private String selectedKhoan = null;
    @FXML
    public void initialize() {
        try {
            loadDanhSachHo();
            dpNgayThu.setValue(LocalDate.now());
        } catch (Exception e) {
            showError("Lỗi", e.getMessage());
        }
    }
    private void loadDanhSachHo() throws Exception {
        List<HoKhau> list = HoKhauServices.timHoKhau("");
        ObservableList<String> items = FXCollections.observableArrayList();
        for(HoKhau h : list) {
            items.add(h.getMaHo());
        }
        cmbHoKhau.setItems(items);
    }
    @FXML
    public void onHoKhauSelected(ActionEvent event) {
        try {
            selectedHo = cmbHoKhau.getValue();
            if(selectedHo != null && !selectedHo.isEmpty()) {
                loadKhoanThuChuaNop(selectedHo);
            }
        } catch (Exception e) {
            showError("Lỗi", e.getMessage());
        }
    }
    private void loadKhoanThuChuaNop(String maHo) throws Exception {
        List<HoKhau_KhoanThu> list = HoKhau_KhoanThuService.timKhoanThu(maHo);
        ObservableList<HoKhau_KhoanThu> items = FXCollections.observableArrayList();
        for(HoKhau_KhoanThu h : list) {
            if(!"Nộp đủ".equals(h.getTrangThaiChiTiet())) {
                items.add(h);
            }
        }
        tvKhoanThu.setItems(items);
    }
    @FXML
    public void onKhoanThuSelected() {
        HoKhau_KhoanThu selected = tvKhoanThu.getSelectionModel().getSelectedItem();
        if(selected != null) {
            selectedKhoan = selected.getMaKhoanThu();
            tfSoTien.clear();
            taGhiChu.clear();
            lbThongTin.setText(String.format("Tên khoản: %s | Còn nợ: %s | Hạn nộp: %s",
                selected.getMaKhoanThu(), ValidationUtil.formatCurrency(selected.getTienThieu()), selected.getHanNop()));
        }
    }
    @FXML
    public void onLuu(ActionEvent event) {
        try {
            if(selectedHo == null || selectedKhoan == null) {
                showWarning("Thông báo", "Vui lòng chọn hộ khẩu và khoản thu");
                return;
            }
            String soTienStr = tfSoTien.getText().trim();
            if(soTienStr.isEmpty()) {
                showWarning("Thông báo", "Vui lòng nhập số tiền");
                return;
            }
            int soTien = Integer.parseInt(soTienStr);
            if(!ValidationUtil.isValidAmount(soTien)) {
                showWarning("Thông báo", "Số tiền phải > 0 và <= 999.999.999");
                return;
            }
            LocalDate ngayThu = dpNgayThu.getValue();
            if(!ValidationUtil.isValidDate(ngayThu)) {
                showWarning("Thông báo", "Ngày thu không được trong tương lai");
                return;
            }
            // Kiểm tra không duplicate
            List<PhiBienLai> exists = PhiBienLaiService.timPhiBienLaiTheoHo(selectedHo);
            for(PhiBienLai p : exists) {
                if(p.getNgayThu().equals(ngayThu) && p.getMaKhoanThu().equals(selectedKhoan)) {
                    showWarning("Thông báo", "Đã có biên lai cho ngày này");
                    return;
                }
            }
            // Tạo mã biên lai
            String maPhiBienLai = generateMaPhiBienLai();
            PhiBienLai phi = new PhiBienLai(maPhiBienLai, selectedHo, selectedKhoan, soTien, 
                ngayThu, "Đã thu", PhienNguoiDung.nguoiDung.getMaNguoiDung(), taGhiChu.getText());
            PhiBienLaiService.themPhiBienLai(phi);
            // Update hokhau_khoanthu
            HoKhau_KhoanThu hk = new HoKhau_KhoanThu(selectedHo, selectedKhoan, true, 0, 
                null, null, "Đã cập nhật");
            hk.setSoTienThuc(soTien);
            hk.setTrangThaiChiTiet(soTien > 0 ? "Nộp 1 phần" : "Chưa nộp");
            HoKhau_KhoanThuService.suaHoKhau_KhoanThu(hk);
            showSuccess("Thành công", "Ghi nhận khoản thu thành công");
            onLamMoi(null);
            loadKhoanThuChuaNop(selectedHo);
        } catch(NumberFormatException e) {
            showError("Lỗi", "Vui lòng nhập số hợp lệ");
        } catch(Exception e) {
            showError("Lỗi", e.getMessage());
        }
    }
    @FXML
    public void onLamMoi(ActionEvent event) {
        tfSoTien.clear();
        taGhiChu.clear();
        dpNgayThu.setValue(LocalDate.now());
        lbThongTin.setText("");
        selectedKhoan = null;
    }
    private String generateMaPhiBienLai() {
        LocalDate now = LocalDate.now();
        String date = String.format("%04d%02d%02d", now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        return "RCP-" + date + "-" + System.currentTimeMillis() % 10000;
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
