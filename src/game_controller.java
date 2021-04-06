import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Random;

public class game_controller {
    @FXML
    public Label qsLbl, timeLbl, viewerScore, playerScore;

    @FXML
    public Button subBtn;

    @FXML
    public TextField ansTxt;

    @FXML
    public ProgressBar timeBar;

    ArrayList<ArrayList<String>> questions=Main.questions_list;

    private Random random=new Random();
    public String right_answer;

    public int vScore=0;
    public int pScore=0;

    public my_timer mt;

    public int count;

    public void initialize()  {

        mt=new my_timer(){

            @Override
            void run() {
                if(count>=0) {
                    timeLbl.setText(String.valueOf(count--));
                }else{
                    outOfTime();
                }
            }
        };

        startRound();

    }

    public void btnPress(javafx.event.ActionEvent actionEvent) {
        if (vScore<6 && pScore<6){
            mt.stop();
            endRound();
        }
    }

    public void outOfTime(){
        System.out.println("ss");
        mt.stop();
    }

    public void startRound(){
        count=5;
        ansTxt.setText("");
        right_answer=questions.get(random.nextInt(questions.size())).get(1);
        qsLbl.setText(questions.get(random.nextInt(questions.size())).get(0));
        mt.start();
    }

    public void endRound(){
        if(ansTxt.getText().equals(right_answer)){
            pScore++;
        }
        else{
            vScore++;
        }
        viewerScore.setText(String.valueOf(vScore));
        playerScore.setText(String.valueOf(pScore));

        if(vScore>=6){
            endGame("v");
        }else if(pScore>=6){
            endGame("p");
        }else{
            startRound();
        }

    }

    public void endGame(String winner){
        if(winner=="v"){
            qsLbl.setText("Viewers won");
        }else{
            qsLbl.setText("Players won");
        }
    }




}
