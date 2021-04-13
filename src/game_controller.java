import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
        ArrayList<String> appeal=new ArrayList<>();
        appeal.add(qsLbl.getText());
        appeal.add(ansTxt.getText());
        appeal.add(rAns.getText());
        appeal.add(Main.team);
        appeal.add("null");
        Main.aps.add(appeal);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmed");
        alert.setHeaderText("Appeal accepted!");
        alert.setContentText("Your appeal has been accepted and will be reviewed by moderators as soon as possible");

        alert.showAndWait();

        startRound();
    }
}
