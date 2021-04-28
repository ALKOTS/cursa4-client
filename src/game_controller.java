import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class game_controller {
    @FXML
    public Label qsLbl, viewerScore, playerScore, rAns;

    @FXML
    public Button subBtn, returnBtn, appealBtn;

    @FXML
    public TextField ansTxt;

    @FXML
    public ProgressBar timeBar;

    @FXML
    public VBox vb;

    ArrayList<ArrayList<String>> questions=Main.questions_list;

    ArrayList<ArrayList<String>> appeals=new ArrayList<>();

    private Random random=new Random();
    public String right_answer;

    //public ArrayList<ArrayList<String>> aps=new ArrayList<>();

    public int vScore=0;
    public int pScore=0;

    public double count;

    public my_timer mt,at;

    public String team;

    public void initialize() throws Exception {
        timeBar.setProgress(1);
        returnBtn.setVisible(false);
        appealBtn.setVisible(false);
        team=Main.team;
        mt=new my_timer(){

            @Override
            void run() {
                if(count>=0) {
                    timeBar.setProgress(count--/60.0);
                }else{
                    outOfTime();
                }
            }
        };

        at=new my_timer() {
            @Override
            void run() throws Exception {
                if(count>=0) {
                    timeBar.setProgress(count--/5.0);
                }else{
                    appealDeclined();
                }
            }
        };

        startRound();
    }

    public void btnPress(javafx.event.ActionEvent actionEvent) {
        if (vScore<6 && pScore<6){
            mt.stop();
            endRound(ansTxt.getText());
        }
    }

    public void outOfTime(){
        mt.stop();
        endRound(null);
    }

    public void appealDeclined() throws Exception {
        at.stop();
        startRound();
    }

    public void startRound() throws Exception {
        viewerScore.setText(String.valueOf(vScore));
        playerScore.setText(String.valueOf(pScore));
        appealBtn.setVisible(false);
        ansTxt.setDisable(false);
        subBtn.setDisable(false);

        if(vScore>=6){
            endGame("v");
        }else if(pScore>=6){
            endGame("p");
        }else{
            rAns.setText("");
            count=60;
            ansTxt.setText("");
            timeBar.setProgress(1);
            int qsIndex=random.nextInt(questions.size());
            right_answer=questions.get(qsIndex).get(1);
            qsLbl.setText(questions.get(qsIndex).get(0));
            questions.remove(questions.get(qsIndex));
            mt.start();
        }
    }

    public void endRound(String ans){
        rAns.setText(right_answer);
        ansTxt.setDisable(true);
        subBtn.setDisable(true);
        count=5;
        if(ans!=null&&ans.equals(right_answer)){
            pScore++;
        }
        else{
            if(ans!=null)
            {
                appealBtn.setVisible(true);
            }
            vScore++;
        }
        at.start();
    }

    public void endGame(String winner) throws Exception {
        at.stop();
        mt.stop();
        if(winner=="v"){
            qsLbl.setText("Viewers won");
        }else{
            qsLbl.setText("Players won");
        }

        //subBtn.setVisible(false);
        vb.getChildren().remove(1);
        vb.getChildren().remove(1);
        ansTxt.setDisable(true);
        returnBtn.setVisible(true);

        JSONObject toPut=new JSONObject(){{
            put("name",Main.teams_list.get(Main.team).get("name"));
            put("accessKey",Main.team);
            put("state",2);
            put("score",pScore);
            put("email", Main.teams_list.get(Main.team).get("email"));
        }};

        Unirests.put(Main.teams_list.get(Main.team).get("link"), toPut);

//        Unirest.put(Main.teams_list.get(Main.team).get("link"))
//                .header("Content-type", "application/hal+json")
//                .body(new JSONObject(){{
//                    put("name",Main.teams_list.get(Main.team).get("name"));
//                    put("accessKey",Main.team);
//                    put("state",2);
//                    put("score",pScore);
//                    put("email", Main.teams_list.get(Main.team).get("email"));
//                }})
//                .asJson();

        AtomicInteger asyncChecker= new AtomicInteger();

        for(int i=0; i<appeals.size(); i++){
            int finalI1 = i;
            new Thread(()->{
                int finalI = finalI1;
                try {
                    JSONObject toPost=new JSONObject(){{
                        put("question", appeals.get(finalI).get(0));
                        put("answer", appeals.get(finalI).get(1));
                        put("ranswer", appeals.get(finalI).get(2));
                        put("team", appeals.get(finalI).get(3));
                    }};

                    Unirests.post(Main.dbLink+"/appeals", toPost);

//                    Unirest.post(Main.dbLink+"/appeals")
//                            .header("Content-type", "application/hal+json")
//                            .body(new JSONObject(){{
//                                put("question", appeals.get(finalI).get(0));
//                                put("answer", appeals.get(finalI).get(1));
//                                put("ranswer", appeals.get(finalI).get(2));
//                                put("team", appeals.get(finalI).get(3));
//                            }})
//                            .asJson();
                } catch (UnirestException e) {
                }
                asyncChecker.getAndIncrement();
            }).start();

        }


        while (asyncChecker.get() <appeals.size()){
            Thread.sleep(100);
            System.out.println(asyncChecker.get());
        }

        Main.team=null;
        Main.get_appeals();
        Main.get_teams();

    }

    public void onReturn(ActionEvent actionEvent) throws Exception {
        StageChanger.simpleChangeStage("Главное меню", "main_menu", returnBtn);
    }

    public void onAppeal(ActionEvent actionEvent) throws Exception {

        appeals.add(new ArrayList<>(){{
            add(qsLbl.getText());
            add(ansTxt.getText());
            add(rAns.getText());
            add(Main.team);
        }});

        new Alert(Alert.AlertType.CONFIRMATION){{
            setTitle("Confirmed");
            setHeaderText("Appeal accepted!");
            setContentText("Your appeal has been accepted and will be reviewed by moderators as soon as possible");
            showAndWait();
        }};


        startRound();
    }
}
