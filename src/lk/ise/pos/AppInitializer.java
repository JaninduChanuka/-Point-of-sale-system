package lk.ise.pos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lk.ise.pos.bo.BoFactory;
import lk.ise.pos.bo.custom.UserBo;
import lk.ise.pos.enums.BoType;

import java.io.IOException;

public class AppInitializer extends Application {
    private UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            userBo.initializeUsers();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/DashboardForm.fxml"))));
        primaryStage.centerOnScreen();
        primaryStage.show();
    }
}
