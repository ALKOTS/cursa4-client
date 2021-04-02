package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {


    public static Map<String,Scene> sceneMap = new HashMap<String,Scene>();
    public Scene mainMenu;
    public Scene questionScene;
    public static Stage prStage=new Stage();

    {
        try {
            mainMenu = new Scene(FXMLLoader.load(getClass().getResource("main_menu.fxml")), 800, 600);
            questionScene =new Scene(FXMLLoader.load(getClass().getResource("questions.fxml")), 800, 600);
            sceneMap.put("mainMenu",mainMenu);
            sceneMap.put("questionScene",questionScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Что? Где? Когда?");
        prStage=primaryStage;
        SceneChanger.changeScene("mainMenu");
    }


    public static void main(String[] args) {
        launch(args);
    }
}
