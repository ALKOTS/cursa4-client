import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class team_selector_controller {
    @FXML
    public Button subBtn, returnBtn;

    @FXML
    public TextField ansTxt;

    public HashMap<String, Integer> teams_list=Main.teams_list;

    public void initialize(){

    }

    public void selectTeam(ActionEvent actionEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Error with your team!");

        if(teams_list.containsKey(ansTxt.getText())&&(teams_list.get(ansTxt.getText())==null)){
            Main.team=ansTxt.getText();
            StageChanger.simpleChangeStage("Главное меню", "main_menu", subBtn);

        }else if(ansTxt.getText().equals("adminka")){
            admin_controller ac=new admin_controller();
            ac.initialize();

            Stage cur_stage = (Stage) subBtn.getScene().getWindow();
            cur_stage.close();
//            StageChanger.simpleChangeStage("ss","admin",subBtn);

        }else if(!(teams_list.containsKey(ansTxt.getText()))){
            alert.setContentText("Looks like the team you are trying to access doesn't exist!");
            alert.showAndWait();

        }else if(teams_list.get(ansTxt.getText())!=null){
            alert.setContentText("Looks like the team you are trying to access has already played in this tournament!");
            alert.showAndWait();
        }


    }

    public void onReturn(ActionEvent actionEvent) throws Exception {
        StageChanger.simpleChangeStage("Главное меню", "main_menu", returnBtn);
    }
}
