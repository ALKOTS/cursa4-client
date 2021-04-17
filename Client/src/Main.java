import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Application;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class Main extends Application {

    public static ArrayList<ArrayList<String>> questions_list =new ArrayList<>(); //ПРОВЕРКА НА РАЗМЕР

    public static HashMap<String,Integer> teams_list=new HashMap<>();

    public static String team;

    public static ArrayList<ArrayList<String>> aps=new ArrayList<>();  //[[question, answer, right answer, team, isApproved(Y, N, null)], [...], ...]

    @Override
    public void start(Stage primaryStage) throws Exception{
        get_questions();
        get_teams();

        team=null;


        //URLConnection connection = new URL("http://localhost:8080/questions").openConnection();
        //connection.setRequestProperty("header1", header1);
        //Get Response
//        InputStream is = connection.getInputStream();
//        System.out.println(connection.getContentType());

        primaryStage.setTitle("Главное меню");
        primaryStage.setScene(SceneChanger.changeScene("main_menu"));
        primaryStage.show();

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

    }

    public void get_teams() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get("http://localhost:8080/teams").asJson();
        //System.out.println(response.getBody().getObject());
        JSONArray teams=new JSONArray(response.getBody().getObject().getJSONObject("_embedded").getJSONArray("teams"));
        System.out.println(teams);
        teams_list.put("ss",null);
        teams_list.put("aa",1);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
