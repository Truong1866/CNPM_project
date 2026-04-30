package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try{
            Parent dangNhap = FXMLLoader.load(getClass().getResource("/view/DangNhapView.fxml"));
            Scene scene = new Scene(dangNhap,800,600);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
