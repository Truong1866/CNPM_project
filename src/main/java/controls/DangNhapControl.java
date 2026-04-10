package controls;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.NguoiDung;
import models.PhienNguoiDung;
import service.NguoiDungService;

import java.util.List;

public class DangNhapControl {
    @FXML
    private TextField ten_ma_cccd;

    @FXML
    private PasswordField matkhau;

    @FXML
    private Button nut_dangnhap;

    public void quenmatkhau(MouseEvent mouseEvent) throws Exception{
    }

    public NguoiDung dangnhap(ActionEvent event) throws Exception {
        String svten_ma_cccd = ten_ma_cccd.getText();
        String svmatkhau = matkhau.getText();
        boolean check = false;
        if(svten_ma_cccd != null && svmatkhau != null){
            List<NguoiDung> nguoiDungs = NguoiDungService.timNguoiDung(svten_ma_cccd);
            for(NguoiDung nguoiDung: nguoiDungs){
                check = false;
                check = (nguoiDung.getMaNguoiDung().equals(svten_ma_cccd)) ||
                        (nguoiDung.getTenNguoiDung().equals(svten_ma_cccd)) ||
                        (nguoiDung.getCCCD().equals(svten_ma_cccd));
                check = check && (nguoiDung.getMatKhau().equals(svmatkhau));
                if(check){
                    PhienNguoiDung.nguoiDung = nguoiDung;
                    break;
                }

            }
            if(PhienNguoiDung.nguoiDung != null){
                FXMLLoader load = new FXMLLoader(getClass().getResource("/view/TrangChuView.fxml"));
                Parent trangChu = load.load();

                TrangChuControl trangChuControl = load.getController();
                trangChuControl.setThongTin();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(trangChu, 800, 600));
                stage.setResizable(false);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Tên tài khoản hoặc mật khẩu không đúng", ButtonType.CLOSE);
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }
        return new NguoiDung();
    }
}
