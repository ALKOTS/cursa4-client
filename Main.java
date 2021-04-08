import javafx.application.Application;
import javafx.stage.Stage;


import java.util.*;

public class Main extends Application {

    public static ArrayList<ArrayList<String>> questions_list =new ArrayList<>();

    public static HashMap<String,Integer> teams_list=new HashMap<>();



    @Override
    public void start(Stage primaryStage) throws Exception{
        get_questions();
        get_teams();
        primaryStage.setTitle("Что? Где? Когда?");
        primaryStage.setScene(SceneChanger.changeScene("main_menu"));
        primaryStage.show();
    }

    public void get_questions(){
        ArrayList<String> question=new ArrayList<>();
        question.add("What is ss?");
        question.add("ss");
        questions_list.add(question);
    }

    public void get_teams(){
        teams_list.put("ss",null);
        teams_list.put("aa",1);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
