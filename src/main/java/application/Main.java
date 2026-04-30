package application;

import database.DB_manager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void init() throws Exception {
        DB_manager.init();
    }

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

    @Override
    public void stop() throws Exception {
        DB_manager.shutdown();
    }
}
