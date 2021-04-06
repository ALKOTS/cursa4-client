import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.util.*;

public class Main extends Application {

    public static ArrayList<ArrayList<String>> questions=new ArrayList<ArrayList<String>>();



    @Override
    public void start(Stage primaryStage) throws Exception{
        ArrayList<String> question=new ArrayList<String>();
        question.add("What is ss?");
        question.add("ss");
        questions.add(question);
        primaryStage.setTitle("Что? Где? Когда?");
        primaryStage.setScene(SceneChanger.changeScene("main_menu"));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
