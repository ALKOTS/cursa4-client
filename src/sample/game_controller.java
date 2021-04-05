package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

public class game_controller {
    @FXML
    public Label qsLbl;

    @FXML
    public Button subBtn;

    @FXML
    public TextField ansTxt;


    @FXML
    public ProgressBar ansTimer;


    public String right_answer="ss";


    public void initialize(){
        qsLbl.setText("What is ss?");
        ansTimer.setProgress(0);

        new Thread(new Runnable() {
            long startTime=System.currentTimeMillis();
            long endTime=startTime+5000;
            @Override
            public void run() {
                while (startTime<endTime){
                    ansTimer.setProgress((5000-endTime+startTime)/10000);
                    System.out.println(5000-endTime+startTime);
                    startTime=System.currentTimeMillis();

                }
            }
        }).start();



    }

    public void btnPress(javafx.event.ActionEvent actionEvent) {
        System.out.println(ansTxt.getText());
        if(ansTxt.getText().equals(right_answer)){
            qsLbl.setText("Mocha");
        }
        else{
            qsLbl.setText("Govno");
        }

    }





}
