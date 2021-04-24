import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Application;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends Application {

    public static ArrayList<ArrayList<String>> questions_list =new ArrayList<>(); //question, answer, link //ПРОВЕРКА НА РАЗМЕР

    public static HashMap<String, HashMap<String,String>> teams_list=new HashMap<>();  //accessKey: [name:name, state:state(0,1,2), score:score(null), link:link]

    public static String team;

    public static ArrayList<ArrayList<String>> aps=new ArrayList<>();  //[[question, answer, right answer, team, isApproved(Y, N, null), link], [...], ...]

    public static String dbLink="http://localhost:8080";//"https://cursa4-server.herokuapp.com";

    @Override
    public void start(Stage primaryStage) throws Exception{
        //need to check db connection
        //generate_questions();
        get_questions();
        get_teams();

        team=null;

        primaryStage.setTitle("Главное меню");
        primaryStage.setScene(SceneChanger.changeScene("main_menu"));
        primaryStage.show();

    }

    public void generate_questions() throws UnirestException {
        for(int i=0; i<24; i++){
            int finalI = i;
            JSONObject jo=new JSONObject(){{put("question","What is "+ finalI);put("answer",finalI);}};

            Unirest.post(dbLink+"/questions")
                    .header("Content-type", "application/hal+json")
                    .body(jo)
                    .asJson();
        }
    }


    public static void get_questions() throws Exception {
        questions_list.clear();
        AtomicInteger asyncChecker= new AtomicInteger();
        int pages=Integer.parseInt(Unirest.get(dbLink+"/questions").asJson().getBody().getObject().getJSONObject("page").get("totalPages").toString());

        new Thread(()->{
            for(int i=0; i<pages; i++){
                HttpResponse<JsonNode> get_questions_response= null;
                try {
                    get_questions_response = Unirest.get(dbLink+"/questions/?page="+i).asJson();
                } catch (UnirestException e) {
                }
                JSONArray questions=get_questions_response.getBody().getObject().getJSONObject("_embedded").getJSONArray("questions");

                for (int j=0; j<questions.length(); j++){
                    int finalJ = j;
                    questions_list.add(new ArrayList<>(){{
                        add(questions.getJSONObject(finalJ).get("question").toString());
                        add(questions.getJSONObject(finalJ).get("answer").toString());
                        add(questions.getJSONObject(finalJ).getJSONObject("_links").getJSONObject("self").getString("href"));
                    }});
                }
            }
            asyncChecker.getAndIncrement();
        }).start();

        if(pages!=1){
            while (asyncChecker.get()<pages-1){
                Thread.sleep(100);
            }
        }else {
            while (asyncChecker.get()<pages){
                Thread.sleep(100);
            }
        }



        System.out.println(questions_list);

    }

    public static void get_teams() throws Exception {
        teams_list.clear();
        AtomicInteger asyncChecker= new AtomicInteger();
        int pages=Integer.parseInt(Unirest.get(dbLink+"/teams").asJson().getBody().getObject().getJSONObject("page").get("totalPages").toString());
        new Thread(()->{
            for(int i=0; i<pages; i++) {
                HttpResponse<JsonNode> get_teams_response = null;
                try {
                    get_teams_response = Unirest.get(dbLink+"/teams/?page="+i).asJson();
                } catch (UnirestException e) {
                }
                JSONArray teams = get_teams_response.getBody().getObject().getJSONObject("_embedded").getJSONArray("teams");
                for (int j = 0; j < teams.length(); j++) {

                    switch (Integer.parseInt(teams.getJSONObject(j).get("state").toString())) {
                        case 0:
                            continue;
                        case 1:
                            int finalI = j;
                            teams_list.put(teams.getJSONObject(finalI).get("accessKey").toString(), new HashMap<>() {{
                                put("name", teams.getJSONObject(finalI).get("name").toString());
                                put("state", teams.getJSONObject(finalI).get("state").toString());
                                put("score", null);
                                put("link", teams.getJSONObject(finalI).getJSONObject("_links").getJSONObject("self").getString("href"));
                            }});
                            break;
                        case 2:
                            int finalI1 = j;
                            teams_list.put(teams.getJSONObject(finalI1).get("accessKey").toString(), new HashMap<>() {{
                                put("name", teams.getJSONObject(finalI1).get("name").toString());
                                put("state", teams.getJSONObject(finalI1).get("state").toString());
                                put("score", teams.getJSONObject(finalI1).get("score").toString());
                                put("link", teams.getJSONObject(finalI1).getJSONObject("_links").getJSONObject("self").getString("href"));
                            }});
                            break;
                        default:
                            System.out.println("Неизвестное значение состояния");
                            break;
                    }
                }
            }
            asyncChecker.getAndIncrement();
        }).start();

        if(pages!=1){
            while (asyncChecker.get()<pages-1){
                Thread.sleep(100);
            }
        }else {
            while (asyncChecker.get()<pages){
                Thread.sleep(100);
            }
        }

        System.out.println(teams_list);
    }

    public static void get_appeals() throws Exception {
        aps.clear();
        AtomicInteger asyncChecker= new AtomicInteger();
        int pages=Integer.parseInt(Unirest.get(dbLink+"/appeals").asJson().getBody().getObject().getJSONObject("page").get("totalPages").toString());

        new Thread(()->{
            for(int i=0; i<pages; i++)
            {
                HttpResponse<JsonNode> get_appeals_response = null;
                try {
                    get_appeals_response = Unirest.get(dbLink+"/appeals/?page="+i).asJson();
                } catch (UnirestException e) {
                }
                JSONArray appeals=get_appeals_response.getBody().getObject().getJSONObject("_embedded").getJSONArray("appeals");
                for (int j=0; j<appeals.length(); j++){
                    int finalI = j;
                    try{
                        if(!appeals.getJSONObject(finalI).get("team").toString().equals(null) && !teams_list.get(appeals.getJSONObject(finalI).get("team").toString()).get("score").equals(null)) {
                            aps.add(new ArrayList<>() {{
                                add(appeals.getJSONObject(finalI).get("question").toString());
                                add(appeals.getJSONObject(finalI).get("answer").toString());
                                add(appeals.getJSONObject(finalI).get("ranswer").toString());
                                add(appeals.getJSONObject(finalI).get("team").toString());
                                add("null");
                                add(appeals.getJSONObject(finalI).getJSONObject("_links").getJSONObject("self").getString("href"));
                            }});
                        }
                    }catch (NullPointerException npe){
                        String toDelete=appeals.getJSONObject(finalI).getJSONObject("_links").getJSONObject("self").getString("href");
                        try {
                            Unirest.delete(toDelete)
                                    .header("Content-type", "application/hal+json")
                                    .asJson();
                        } catch (UnirestException e) {
                        }
                    }
                    {    //если счет не null

                    }



                }
            }
            asyncChecker.getAndIncrement();
        }).start();


        if(pages!=1){
            while (asyncChecker.get()<pages-1){
                Thread.sleep(100);
            }
        }else {
            while (asyncChecker.get()<pages){
                Thread.sleep(100);
            }
        }


    }

    public static void main(String[] args) {
        launch(args);
    }
}

//        HttpResponse<JsonNode> r= Unirest.delete(dbLink+"/teams/8")
//                .header("Content-type", "application/hal+json")
//                .asJson();



//        JSONObject jo=new JSONObject(){{put("name","mm");put("accessKey","mm");put("state",0);}};
//        HttpResponse<JsonNode> r=Unirest.put(dbLink+"/teams/5")
//                .header("Content-type", "application/hal+json")
//                .body(jo)
//                .asJson();



//        JSONObject jo=new JSONObject(){{put("name","mm");put("accessKey","mm");put("state",1);}};
//
//        HttpResponse<JsonNode> r= Unirest.post(dbLink+"/teams")
//                .header("Content-type", "application/hal+json")
//        .body(jo)
//        .asJson();
//
//        System.out.println(r.getBody().toString());