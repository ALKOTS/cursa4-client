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
    public Label qsLbl, timeLbl, viewerScore, playerScore;

    @FXML
    public Button subBtn, returnBtn;

    @FXML
    public TextField ansTxt;



    ArrayList<ArrayList<String>> questions=Main.questions_list;

    private Random random=new Random();
    public String right_answer;

    public int vScore=0;
    public int pScore=0;

    public my_timer mt;

    public int count;

    public String team;

    public void initialize()  {

        returnBtn.setVisible(false);

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
            endRound(ansTxt.getText());
        }
    }

    

    public void outOfTime(){
        mt.stop();
        endRound(null);
    }

    public void startRound(){
        count=5;
        ansTxt.setText("");
        right_answer=questions.get(random.nextInt(questions.size())).get(1);
        qsLbl.setText(questions.get(random.nextInt(questions.size())).get(0));
        mt.start();
    }

    public void endRound(String ans){
        if(ans!=null&&ans.equals(right_answer)){
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
        //Main.teams_list.replace(main_menu_controller.team,0,pScore); //.get(main_menu_controller.team)

        subBtn.setVisible(false);
        returnBtn.setVisible(true);
    }


    public void onReturn(ActionEvent actionEvent) throws Exception {
        //

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_menu.fxml"));
        Parent root = loader.load();

        main_menu_controller mmc = loader.getController();
        mmc.updateScore(team, pScore);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Что? Где? Когда?");
        stage.show();

        Stage cur_stage = (Stage) returnBtn.getScene().getWindow();
        cur_stage.close();

        //

    }

    public void recieveTeam(String Team){
        team=Team;
    }
}
