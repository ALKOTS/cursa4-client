import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.concurrent.Task;

import java.util.ArrayList;

public class game_controller {
    @FXML
    public Label qsLbl, timeLbl;

    @FXML
    public Button subBtn;

    @FXML
    public TextField ansTxt;

    @FXML
    public ProgressBar timeBar;

    ArrayList<ArrayList<String>> questions=Main.questions;

    public String right_answer="ss";

    public my_timer mt;

    public void initialize()  {

        qsLbl.setText("What is ss?");
        timeBar.setProgress(0);

        mt=new my_timer(){
            int count=5;
            @Override
            void run() {
                if(count>=0) {
                    timeLbl.setText(String.valueOf(count--));
                }else{
                    outOfTime();
                }
            }
        };
        mt.start();

    }

    public void btnPress(javafx.event.ActionEvent actionEvent) {
        System.out.println(ansTxt.getText());
        mt.stop();
        if(ansTxt.getText().equals(right_answer)){
            qsLbl.setText("Mocha");
        }
        else{
            qsLbl.setText("Govno");
        }

    }

    public void outOfTime(){
        System.out.println("ss");
        mt.stop();
    }





}
