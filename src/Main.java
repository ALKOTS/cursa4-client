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
        get_appeals();

        team=null;

        primaryStage.setTitle("Главное меню");
        primaryStage.setScene(SceneChanger.changeScene("main_menu"));
        primaryStage.show();

    }

    public void get_questions(){

        for (int i=0; i<24; i++){
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
    }


    public static void get_appeals() throws UnirestException {
        aps.clear();
        HttpResponse<JsonNode> get_appeals_response = Unirest.get("http://localhost:8080/appeals").asJson();
        ArrayList<JSONArray> appeals=new ArrayList<JSONArray>(Collections.singleton(get_appeals_response.getBody().getObject().getJSONObject("_embedded").getJSONArray("appeals")));
        for (int i=0; i<appeals.get(0).length(); i++){
            int finalI = i;
            if(teams_list.get(appeals.get(0).getJSONObject(finalI).get("team").toString())!=null){
                aps.add(new ArrayList<>(){{
                    add(appeals.get(0).getJSONObject(finalI).get("question").toString());
                    add(appeals.get(0).getJSONObject(finalI).get("answer").toString());
                    add(appeals.get(0).getJSONObject(finalI).get("ranswer").toString());
                    add(appeals.get(0).getJSONObject(finalI).get("team").toString());
                    add(appeals.get(0).getJSONObject(finalI).get("isApproved").toString());
                }});
            }
            else{
                String toDelete=appeals.get(0).getJSONObject(finalI).getJSONObject("_links").getJSONObject("self").getString("href");
                HttpResponse<JsonNode> r= Unirest.delete(toDelete)
                        .header("Content-type", "application/hal+json")
                        .asJson();
           }
        }


    }

    public static void main(String[] args) {
        launch(args);
    }
}

//        HttpResponse<JsonNode> r= Unirest.delete("http://localhost:8080/teams/8")
//                .header("Content-type", "application/hal+json")
//                .asJson();



//        JSONObject jo=new JSONObject(){{put("name","mm");put("accessKey","mm");put("state",0);}};
//        HttpResponse<JsonNode> r=Unirest.put("http://localhost:8080/teams/5")
//                .header("Content-type", "application/hal+json")
//                .body(jo)
//                .asJson();



//        JSONObject jo=new JSONObject(){{put("name","mm");put("accessKey","mm");put("state",1);}};
//
//        HttpResponse<JsonNode> r= Unirest.post("http://localhost:8080/teams")
//                .header("Content-type", "application/hal+json")
//        .body(jo)
//        .asJson();
//
//        System.out.println(r.getBody().toString());