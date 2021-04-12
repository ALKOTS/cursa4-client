import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class game_controller {
    @FXML
    public Label qsLbl, viewerScore, playerScore;

    @FXML
    public Button subBtn, returnBtn, appealBtn;

    @FXML
    public TextField ansTxt;

    @FXML
    public ProgressBar timeBar;

    ArrayList<ArrayList<String>> questions=Main.questions_list;

    private Random random=new Random();
    public String right_answer;

    public int vScore=0;
    public int pScore=0;

    public double count;

    public my_timer mt,at;

    public String team;

    public void initialize()  {
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
            void run() {
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

    public void appealDeclined(){
        at.stop();
        startRound();
    }

    public void startRound(){
        appealBtn.setVisible(false);
        if(vScore>=6){
            endGame("v");
        }else if(pScore>=6){
            endGame("p");
        }else{
            
        

        count=60;
        ansTxt.setText("");
        timeBar.setProgress(1);
        right_answer=questions.get(random.nextInt(questions.size())).get(1);
        qsLbl.setText(questions.get(random.nextInt(questions.size())).get(0));
        mt.start();
        }
    }

    public void endRound(String ans){


        //update score

        if(ans!=null&&ans.equals(right_answer)){
            pScore++;
        }
        else{
            vScore++;
        }
        viewerScore.setText(String.valueOf(vScore));
        playerScore.setText(String.valueOf(pScore));

        //appeal chance

        count=5;
        appealBtn.setVisible(true);
        at.start();
    }

    public void endGame(String winner){
        if(winner=="v"){
            qsLbl.setText("Viewers won");
        }else{
            qsLbl.setText("Players won");
        }

        subBtn.setVisible(false);
        returnBtn.setVisible(true);
    }


    public void onReturn(ActionEvent actionEvent) throws Exception {
        Main.teams_list.put(team, pScore);
        Main.team=null;
        StageChanger.simpleChangeStage("Главное меню", "main_menu", returnBtn);
    }

    public void onAppeal(ActionEvent actionEvent) {
        at.stop();
        //appeal functionality here
        startRound();
    }
}
