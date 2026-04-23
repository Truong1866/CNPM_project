package controls;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
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
        try {
            FXMLLoader loader = new FXMLLoader(DangNhapControl.class.getResource("/view/QuanLyCaNhanView.fxml"));
            Pane pane = (Pane) loader.load();
            borderPane.setCenter(pane);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void nhan_khau(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(DangNhapControl.class.getResource("/view/NhanKhauView.fxml"));
        Pane pane = (Pane) loader.load();
        borderPane.setCenter(pane);
    }

    public void thu_phi(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(DangNhapControl.class.getResource("/view/ThuPhiView.fxml"));
        Pane pane = (Pane) loader.load();
        borderPane.setCenter(pane);
    }

    public void thong_ke(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(DangNhapControl.class.getResource("/view/ThongKeView.fxml"));
        Pane pane = (Pane) loader.load();
        borderPane.setCenter(pane);
    }

    public void dang_xuat(ActionEvent event) throws Exception {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc chắn muốn đăng xuất?", ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Xác nhận đăng xuất");
        confirm.setHeaderText(null);
        if(confirm.showAndWait().get() == ButtonType.YES) {
            PhienNguoiDung.nguoiDung = null;
            FXMLLoader load = new FXMLLoader(TrangChuControl.class.getResource("/view/DangNhapView.fxml"));
            Parent login = load.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(login, 800, 600));
            stage.show();
        }
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
