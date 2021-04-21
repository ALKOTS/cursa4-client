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

    public static ArrayList<ArrayList<String>> questions_list =new ArrayList<>(); //question, answer, link //ПРОВЕРКА НА РАЗМЕР

    public static HashMap<String, HashMap<String,String>> teams_list=new HashMap<>();  //accessKey: [name:name, state:state(0,1,2), score:score(null), link:link]

    public static String team;

    public static ArrayList<ArrayList<String>> aps=new ArrayList<>();  //[[question, answer, right answer, team, isApproved(Y, N, null), link], [...], ...]

    public static String dbLink="http://localhost:8080";//"https://cursa4-server.herokuapp.com";

    @Override
    public void start(Stage primaryStage) throws Exception{
        get_questions();
        get_teams();
        //get_appeals();

        team=null;

        primaryStage.setTitle("Главное меню");
        primaryStage.setScene(SceneChanger.changeScene("main_menu"));
        primaryStage.show();

    }


    public void get_questions() throws UnirestException {
        HttpResponse<JsonNode> get_questions_response = Unirest.get(dbLink+"/questions").asJson();
        ArrayList<JSONArray> questions=new ArrayList<>(Collections.singleton(get_questions_response.getBody().getObject().getJSONObject("_embedded").getJSONArray("questions")));


        for (int i=0; i<questions.get(0).length(); i++){
            int finalI = i;
            questions_list.add(new ArrayList<>(){{
                add(questions.get(0).getJSONObject(finalI).get("question").toString());
                add(questions.get(0).getJSONObject(finalI).get("answer").toString());
                add(questions.get(0).getJSONObject(finalI).getJSONObject("_links").getJSONObject("self").getString("href"));
            }});

        }
        System.out.println(questions_list);

    }

    public static void get_teams() throws Exception {
        HttpResponse<JsonNode> get_teams_response = Unirest.get(dbLink+"/teams").asJson();
        ArrayList<JSONArray> teams=new ArrayList<JSONArray>(Collections.singleton(get_teams_response.getBody().getObject().getJSONObject("_embedded").getJSONArray("teams")));
        for(int i=0; i<teams.get(0).length(); i++){
            switch (Integer.parseInt(teams.get(0).getJSONObject(i).get("state").toString())){
                case 0:
                    continue;
                case 1:
                    int finalI = i;
                    teams_list.put(teams.get(0).getJSONObject(finalI).get("accessKey").toString(), new HashMap<>(){{
                        put("name", teams.get(0).getJSONObject(finalI).get("name").toString());
                        put("state", teams.get(0).getJSONObject(finalI).get("state").toString());
                        put("score",null);
                        put("link",teams.get(0).getJSONObject(finalI).getJSONObject("_links").getJSONObject("self").getString("href"));
                    }});
                    break;
                case 2:
                    int finalI1 = i;
                    teams_list.put(teams.get(0).getJSONObject(finalI1).get("accessKey").toString(), new HashMap<>(){{
                        put("name", teams.get(0).getJSONObject(finalI1).get("name").toString());
                        put("state", teams.get(0).getJSONObject(finalI1).get("state").toString());
                        put("score",teams.get(0).getJSONObject(finalI1).get("score").toString());
                        put("link",teams.get(0).getJSONObject(finalI1).getJSONObject("_links").getJSONObject("self").getString("href"));
                    }});
                    break;
                default:
                    System.out.println("Неизвестное значение состояния");
                    break;
            }
        }
        System.out.println(teams_list);
    }

    public static void get_appeals() throws UnirestException {
        aps.clear();
        HttpResponse<JsonNode> get_appeals_response = Unirest.get(dbLink+"/appeals").asJson();
        ArrayList<JSONArray> appeals=new ArrayList<>(Collections.singleton(get_appeals_response.getBody().getObject().getJSONObject("_embedded").getJSONArray("appeals")));
        for (int i=0; i<appeals.get(0).length(); i++){
            int finalI = i;
            if(appeals.get(0).getJSONObject(finalI).get("team").toString()!="null" && teams_list.get(appeals.get(0).getJSONObject(finalI).get("team").toString()).get("score")!=null){    //если счет не null
                aps.add(new ArrayList<>(){{
                    add(appeals.get(0).getJSONObject(finalI).get("question").toString());
                    add(appeals.get(0).getJSONObject(finalI).get("answer").toString());
                    add(appeals.get(0).getJSONObject(finalI).get("ranswer").toString());
                    add(appeals.get(0).getJSONObject(finalI).get("team").toString());
                    add("null");
                    add(appeals.get(0).getJSONObject(finalI).getJSONObject("_links").getJSONObject("self").getString("href"));
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

//        HttpResponse<JsonNode> r= Unirest.delete("https://cursa4-server.herokuapp.com/teams/8")
//                .header("Content-type", "application/hal+json")
//                .asJson();



//        JSONObject jo=new JSONObject(){{put("name","mm");put("accessKey","mm");put("state",0);}};
//        HttpResponse<JsonNode> r=Unirest.put("https://cursa4-server.herokuapp.com/teams/5")
//                .header("Content-type", "application/hal+json")
//                .body(jo)
//                .asJson();



//        JSONObject jo=new JSONObject(){{put("name","mm");put("accessKey","mm");put("state",1);}};
//
//        HttpResponse<JsonNode> r= Unirest.post("https://cursa4-server.herokuapp.com/teams")
//                .header("Content-type", "application/hal+json")
//        .body(jo)
//        .asJson();
//
//        System.out.println(r.getBody().toString());