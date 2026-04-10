package controls;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class TrangChuPaneControl {
    public void ho_khau(MouseEvent mouseEvent) {
    }

    public void khoan_thu(MouseEvent mouseEvent) {
    }

    public void nhan_khau(MouseEvent mouseEvent) throws IOException {
        Scene scene = ((Node) mouseEvent.getSource()).getScene();
        BorderPane borderPane = (BorderPane) scene.lookup("#borderPane");
        FXMLLoader loader = new FXMLLoader(DangNhapControl.class.getResource("/view/NhanKhauView.fxml"));
        Pane pane = (Pane) loader.load();
        borderPane.setCenter(pane);
    }

    public void can_ho(MouseEvent mouseEvent) {
    }

    public void thong_ke(MouseEvent mouseEvent) {
    }
}
