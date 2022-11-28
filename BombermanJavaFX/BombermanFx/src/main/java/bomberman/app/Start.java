package bomberman.app;

import javafx.application.Application;
import javafx.stage.Stage;
import bomberman.view.BombermanView;

public class Start extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        BombermanView view = new BombermanView(stage);
    }


    public static void main(String[] args) {
        launch();
    }
}
