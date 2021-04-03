package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class game_controller {
    @FXML
    public Label qsLbl;

    @FXML
    public Button subBtn;

    @FXML
    public TextField ansTxt;


    public String right_answer="ss";
 

    public void initialize(){
        
    }

    public void btnPress(javafx.event.ActionEvent actionEvent) {
        System.out.println(ansTxt.getText());
        if(ansTxt.getText().equals(right_answer)){
            qsLbl.setText("Mocha");
            main_menu_controller.team=ansTxt.getText();
            my_timer.timeCheck(5,null);
            Stage stage=(Stage) subBtn.getScene().getWindow();
            stage.close();
        }
        else{
            qsLbl.setText("Govno");
        }

    }

    



}