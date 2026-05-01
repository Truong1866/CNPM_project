package controls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Resident;
import service.NguoiDungService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NhanKhauControl implements Initializable {
    @FXML
    private TextField nhapTen;

    @FXML
    private Button timTen;

    @FXML
    private TableView<Resident> bangDuLieu;

    @FXML
    private TableColumn<Resident, String> maNhanKhau, hoTen, cccd, dienThoai;

    @FXML
    private TableColumn<Resident, Void> chiTiet;


    public void timTen(ActionEvent event) throws Exception {
        String ma_ten_CCCD = nhapTen.getText();
        List<Resident> residents = NguoiDungService.timNguoiDung(ma_ten_CCCD);
        ObservableList<Resident> data = FXCollections.observableArrayList(residents);
        bangDuLieu.setItems(data);
    }

    private void xemChiTiet(Resident resident) throws IOException {
        FXMLLoader loader = new FXMLLoader(DangNhapControl.class.getResource("/view/ChiTietNhanKhauView.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Thong tin chi tiet nguoi dung: " + resident.getMaNguoiDung() + "-" + resident.getTenNguoiDung());
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        maNhanKhau.setCellValueFactory(new PropertyValueFactory<>("maNguoiDung"));
        hoTen.setCellValueFactory(new PropertyValueFactory<>("tenNguoiDung"));
        cccd.setCellValueFactory(new PropertyValueFactory<>("CCCD"));
        dienThoai.setCellValueFactory(new PropertyValueFactory<>("dienThoai"));
        chiTiet.setCellFactory(param -> new TableCell<>(){
            private final Button chiTietButton = new Button("!");
            {
                chiTietButton.getStyleClass().add("button-chi-tiet");

                chiTietButton.setOnAction(event -> {
                    try {
                        Resident data = getTableView().getItems().get(getIndex());
                        xemChiTiet(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(chiTietButton);
                }
            }
        });
    }
}
