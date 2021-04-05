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


//        new Thread(new Runnable() {
//            long startTime=System.currentTimeMillis();
//            long endTime=startTime+5000;
//            @Override
//            public void run() {
//                while (startTime<endTime){
//
////                    Platform.runLater(new Runnable() {
////                        @Override
////                        public void run() {
////                            timeLbl.setText(String.valueOf((6000-endTime+startTime)/1000));
////                        }
////                    });
//                    System.out.println(6000-endTime+startTime);
//                    startTime=System.currentTimeMillis();
//
//                }
//            }
//        }).start();

        new Thread(new Task<Void>(){

            long startTime=System.currentTimeMillis();
            long endTime=startTime+5000;

            @Override
            protected Void call(){

                while(startTime<endTime){
                    timeLbl.setText(String.valueOf((6000-endTime+startTime)/1000));
                    System.out.println((6000-endTime+startTime)/1000);
                    startTime=System.currentTimeMillis();
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(qsLbl.getText()!="Mocha"){
                            qsLbl.setText("sasat");
                        }
                    }
                });
                return null;
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
