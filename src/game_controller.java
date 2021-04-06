import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.concurrent.Task;

public class game_controller {
    @FXML
    public Label qsLbl, timeLbl;

    @FXML
    public Button subBtn;

    @FXML
    public TextField ansTxt;

    @FXML
    public ProgressBar timeBar;



    public String right_answer="ss";


    public void initialize()  {

        qsLbl.setText("What is ss?");
        timeBar.setProgress(0);


        my_timer mt=new my_timer(){
            int count=0;
            @Override
            void run() {
                  timeLbl.setText(String.valueOf(count++));
            }

        };
        mt.start();







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
