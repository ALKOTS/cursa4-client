package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    public Label qsLbl;

    @FXML
    public Button subBtn;

    @FXML
    public TextField ansTxt;

    @FXML
    public Label timeLbl;


    public String right_answer="ss";


    public void initialize(){
        qsLbl.setText("What is ss?");
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

    public void timeCheck(){
        long elapsedTime=0;
        long startTime = System.currentTimeMillis();
        System.out.println(elapsedTime);
        while (elapsedTime<5000){
            elapsedTime = System.currentTimeMillis() - startTime;
            timeLbl.setText(String.valueOf(elapsedTime/1000));
        }
    }



}