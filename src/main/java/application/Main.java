package application;

import database.DB_manager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import user.AuthManager;
import user.SessionUser;


public class Main extends Application {
    @Override
    public void init() throws Exception {
        DB_manager.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
        SessionUser user = new SessionUser("00001", "000001", "ADMIN");
        AuthManager.login(user);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/ResidentView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void stop() throws Exception {
        DB_manager.shutdown();
    }
}
