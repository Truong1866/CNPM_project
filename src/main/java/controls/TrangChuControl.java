package controls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import models.PhienNguoiDung;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TrangChuControl implements Initializable {
    @FXML
    private Label tenDangNhap;

    @FXML
    private ImageView anhDangNhap;

    @FXML
    private BorderPane borderPane;

    public void setThongTin(){
        tenDangNhap.setText(PhienNguoiDung.nguoiDung.getTenNguoiDung());
    }

    public void thongTinCaNhan(MouseEvent mouseEvent) {

    }

    public void nhan_khau(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(DangNhapControl.class.getResource("/view/NhanKhauView.fxml"));
        Pane pane = (Pane) loader.load();
        borderPane.setCenter(pane);
    }

    public void backHome(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(DangNhapControl.class.getResource("/view/TrangChuPane.fxml"));
        Pane pane = (Pane) loader.load();
        borderPane.setCenter(pane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            FXMLLoader loader = new FXMLLoader(DangNhapControl.class.getResource("/view/TrangChuPane.fxml"));
            Pane pane = (Pane) loader.load();
            borderPane.setCenter(pane);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
