import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class team_selector_controller {
    @FXML
    public Button subBtn;

    @FXML
    public TextField ansTxt;

    public void initialize(){

    }

    public HashMap<String, Integer> teams_list=Main.teams_list;

    public void selectTeam(ActionEvent actionEvent) throws Exception {


        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Error with your team!");


        if(teams_list.containsKey(ansTxt.getText())&&(teams_list.get(ansTxt.getText())==null)){

            //

            FXMLLoader loader = new FXMLLoader(getClass().getResource("main_menu.fxml"));
            Parent root = loader.load();

            main_menu_controller mmc = loader.getController();
            mmc.receiveTeam(ansTxt.getText());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Что? Где? Когда?");
            stage.show();

            Stage cur_stage = (Stage) subBtn.getScene().getWindow();
            cur_stage.close();

            //

        }else if(ansTxt.getText().equals("adminka")){
            StageChanger.simpleChangeStage("Админка","admin", subBtn);

//            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
//
//            Button testBtn=new Button("Test");
//            testBtn.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent actionEvent) {
//                    System.out.println("ss");
//                }
//            });
//
//            admin_controller.appsContainer.getChildren().add(testBtn);
//            Parent root=loader.load();
//
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.setTitle("Админка");
//            stage.show();

            Stage cur_stage = (Stage) subBtn.getScene().getWindow();
            cur_stage.close();

        }else if(!(teams_list.containsKey(ansTxt.getText()))){
            alert.setContentText("Looks like the team you are trying to access doesn't exist!");
            alert.showAndWait();
        }else if(teams_list.get(ansTxt.getText())!=null){
            alert.setContentText("Looks like the team you are trying to access has already played in this tournament!");
            alert.showAndWait();
        }


    }
}
