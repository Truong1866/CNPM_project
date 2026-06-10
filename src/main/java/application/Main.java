package application;

import database.DB_manager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import navigation.AppRouter;

public class Main extends Application {

    @Override
    public void init() throws Exception {
        DB_manager.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
        // ── Khởi tạo Router với primaryStage ──────────────────────────────
        AppRouter.getInstance().init(stage);

        // ── Load màn hình đầu tiên (Login) ────────────────────────────────
        // Dùng FXMLLoader trực tiếp lần đầu để tạo Scene gốc,
        // sau đó mọi điều hướng đều qua AppRouter.
        FXMLLoader fxmlLoader = new FXMLLoader(
            Main.class.getResource("/view/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        DB_manager.shutdown();
    }
}
