import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

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

    private Random random=new Random();
    public String right_answer;

    public ArrayList<ArrayList<String>> aps=new ArrayList<>();

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
            Integer qsIndex=random.nextInt(questions.size());
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
        if(winner=="v"){
            qsLbl.setText("Viewers won");
        }else{
            qsLbl.setText("Players won");
        }

        //subBtn.setVisible(false);
        vb.getChildren().remove(1);
        vb.getChildren().remove(1);
        returnBtn.setVisible(true);




        Main.teams_list.put(team, pScore);
        Main.team=null;
        Main.get_appeals();


    }


    public void onReturn(ActionEvent actionEvent) throws Exception {
        StageChanger.simpleChangeStage("Главное меню", "main_menu", returnBtn);
    }

    public void onAppeal(ActionEvent actionEvent) throws Exception {
        at.stop();

        aps.add(new ArrayList<String>(){{add(qsLbl.getText());add(ansTxt.getText());add(rAns.getText());add(Main.team);add(null);}});


        HttpResponse<JsonNode> r= Unirest.post("http://localhost:8080/appeals")
                .header("Content-type", "application/hal+json")
                .body(new JSONObject(){{
                    put("question", aps.get(aps.size()-1).get(0));
                    put("answer", aps.get(aps.size()-1).get(1));
                    put("ranswer", aps.get(aps.size()-1).get(2));
                    put("team", aps.get(aps.size()-1).get(3));
                    put("isApproved", aps.get(aps.size()-1).get(4));
                }})
                .asJson();
        System.out.println(r.getBody().toString());



        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmed");
        alert.setHeaderText("Appeal accepted!");
        alert.setContentText("Your appeal has been accepted and will be reviewed by moderators as soon as possible");

        alert.showAndWait();

        startRound();
    }
}
