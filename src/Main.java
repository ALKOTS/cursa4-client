import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Application;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.Character.UnicodeBlock;
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
//            ArrayList<String> question=new ArrayList<>();
//            question.add("");
//            question.add("");
//            question.set(0, "What is "+i);
//            question.set(1,String.valueOf(i));
            int finalI = i;
            questions_list.add(new ArrayList<>(){{add("What is "+ finalI);add(String.valueOf(finalI));}});
        }

    }

    public void get_teams() throws UnirestException, IOException {
        HttpResponse<JsonNode> get_teams_response = Unirest.get("http://localhost:8080/teams").asJson();
        ArrayList<JSONArray> teams=new ArrayList<JSONArray>(Collections.singleton(get_teams_response.getBody().getObject().getJSONObject("_embedded").getJSONArray("teams")));
        for(int i=0; i<teams.get(0).length(); i++){
            switch (Integer.parseInt(teams.get(0).getJSONObject(i).get("state").toString())){
                case 0:
                    continue;
                case 1:
                    teams_list.put(teams.get(0).getJSONObject(i).get("name").toString(),null);
                    break;
                case 2:
                    teams_list.put(teams.get(0).getJSONObject(i).get("name").toString(), Integer.parseInt(teams.get(0).getJSONObject(i).get("score").toString()));
                    break;
                default:
                    System.out.println("Неизвестное значение состояния");
                    break;
            }
        }

//        HttpResponse<JsonNode> r= Unirest.delete("http://localhost:8080/teams/8")
//                .header("Content-type", "application/hal+json")
//                .asJson();




//        JSONObject jo=new JSONObject(){{put("name","mm");put("accessKey","mm");put("state",1);}};
//
//        HttpResponse<JsonNode> r= Unirest.post("http://localhost:8080/teams")
//                .header("Content-type", "application/hal+json")
//        .body(jo)
//        .asJson();
//
//        System.out.println(r.getBody().toString());

    }

    public static void main(String[] args) {
        launch(args);
    }
}
