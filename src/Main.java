import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.util.HashMap;
import java.util.Map;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Что? Где? Когда?");
        primaryStage.setScene(SceneChanger.changeScene("main_menu"));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
