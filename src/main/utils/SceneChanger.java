package main.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.File;
import java.net.URL;
import java.util.Objects;


public class SceneChanger{

    private static Scene current_scene;

    /**
     * Получение сцены из FXML
     *
     * @param newScene name of required scene
     * @return JavaFX scene
     * @throws Exception
     */

    public static Scene changeScene(String newScene) throws Exception{
        URL url = new File("src/main/scenes/"+newScene+".fxml").toURI().toURL();
        //current_scene=new Scene(FXMLLoader.load(SceneChanger.class.getResource("scenes/"+newScene+ ".fxml")), 800, 600);
        current_scene=new Scene(FXMLLoader.load(url), 800, 600);
        return current_scene;
    }


}
