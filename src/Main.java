import javafx.application.Application;
import javafx.stage.Stage;


import java.util.*;

public class Main extends Application {

    public static ArrayList<ArrayList<String>> questions_list =new ArrayList<>(); //ПРОВЕРКА НА РАЗМЕР

    public static HashMap<String,Integer> teams_list=new HashMap<>();

    //public static  Integer apsNumber;

    public static String team;

    public static ArrayList<ArrayList<String>> aps=new ArrayList<>();  //[[question, answer, right answer, team, isApproved(Y, N, null)], [...], ...]

    //public static Integer apsNumber= aps.size();

    @Override
    public void start(Stage primaryStage) throws Exception{
        get_questions();
        get_teams();

        team=null;

        primaryStage.setTitle("Главное меню");
        primaryStage.setScene(SceneChanger.changeScene("main_menu"));
        primaryStage.show();


        ArrayList<String> tempal=new ArrayList<>();
        tempal.add("Test_qs");
        tempal.add("Test_ans");
        tempal.add("Test_rans");
        tempal.add("Test_team");
        tempal.add("null");
        aps.add(tempal);
    }

    public void get_questions(){

        for (int i=0; i<12; i++){
            ArrayList<String> question=new ArrayList<>();
            question.add("");
            question.add("");
            question.set(0, "What is "+i);
            question.set(1,String.valueOf(i));
            questions_list.add(question);
        }

        System.out.println(questions_list);
    }

    public void get_teams(){
        teams_list.put("ss",null);
        teams_list.put("aa",1);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
