package sample;

import javafx.scene.Scene;


public class SceneChanger extends Main{
    public static void changeScene(String newScene) throws Exception{
        prStage.setScene(sceneMap.get(newScene));
        prStage.show();
    }


}
