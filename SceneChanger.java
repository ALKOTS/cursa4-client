import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;


public class SceneChanger{

    private static Scene current_scene;


    public static Scene changeScene(String newScene) throws Exception{
        current_scene=new Scene(FXMLLoader.load(SceneChanger.class.getResource(newScene+ ".fxml")), 800, 600);
//        prStage.setScene(current_scene);
//        prStage.show();
        return current_scene;
    }


}
