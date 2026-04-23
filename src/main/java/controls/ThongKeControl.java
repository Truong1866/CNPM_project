package controls;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.BaoCaoThongKe;
import models.HoKhau_KhoanThu;
import service.BaoCaoThongKeService;
import validation.ValidationUtil;
import java.time.LocalDate;
import java.util.List;
public class ThongKeControl {
    @FXML private Label lbTongDoanh, lbTiLeNop, lbQuaHan, lbTongNo;
    @FXML private TableView<BaoCaoThongKe> tvThongKe;
    @FXML private TableColumn<BaoCaoThongKe, String> colKhoan;
    @FXML private TableColumn<BaoCaoThongKe, Integer> colTongHo;
    @FXML private TableColumn<BaoCaoThongKe, Long> colTienThu;
    @FXML private TableColumn<BaoCaoThongKe, Long> colTienThieu;
    @FXML private TableColumn<BaoCaoThongKe, Integer> colHoNopDu;
    @FXML private TableColumn<BaoCaoThongKe, Double> colTiLe;
    @FXML private TableView<HoKhau_KhoanThu> tvQuaHan;
    @FXML private TableColumn<HoKhau_KhoanThu, String> colMaHo;
    @FXML private TableColumn<HoKhau_KhoanThu, String> colMaKhoan;
    @FXML private TableColumn<HoKhau_KhoanThu, String> colHanNop;
    @FXML private TableColumn<HoKhau_KhoanThu, Integer> colNo;
    @FXML private DatePicker dpBatDau, dpKetThuc;
    @FXML
    public void initialize() {
        try {
            dpBatDau.setValue(LocalDate.now().minusMonths(1));
            dpKetThuc.setValue(LocalDate.now());
            loadBaoCao();
        } catch(Exception e) {
            showError("Lỗi", e.getMessage());
        }
    }
    private void loadBaoCao() throws Exception {
        // Tổng doanh thu
        long tongDoanh = BaoCaoThongKeService.layTongDoanh();
        lbTongDoanh.setText(ValidationUtil.formatCurrency(tongDoanh));
        // Tỉ lệ nộp
        double tiLe = BaoCaoThongKeService.layTiLeNopThu();
        lbTiLeNop.setText(String.format("%.2f%%", tiLe));
        // Danh sách quá hạn
        List<HoKhau_KhoanThu> quaHan = BaoCaoThongKeService.layDSHoQuaHan(30);
        long tongNo = 0;
        for(HoKhau_KhoanThu h : quaHan) {
            tongNo += h.getTienThieu();
        }
        lbQuaHan.setText(quaHan.size() + " hộ");
        lbTongNo.setText(ValidationUtil.formatCurrency(tongNo));
        ObservableList<HoKhau_KhoanThu> items = FXCollections.observableArrayList(quaHan);
        tvQuaHan.setItems(items);
        // Thống kê theo khoản
        List<BaoCaoThongKe> stats = BaoCaoThongKeService.layThongKeTheoKhoan();
        ObservableList<BaoCaoThongKe> items2 = FXCollections.observableArrayList(stats);
        tvThongKe.setItems(items2);
    }
    @FXML
    public void onLoc() {
        try {
            loadBaoCao();
        } catch(Exception e) {
            showError("Lỗi", e.getMessage());
        }
    }
    @FXML
    public void onXuatExcel() {
        showWarning("Thông báo", "Chức năng xuất Excel sẽ được thêm vào phiên bản tiếp theo");
    }
    private void showError(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
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
}
