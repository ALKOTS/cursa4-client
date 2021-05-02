package main.controllers;

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
import main.Main;
import main.utils.StageChanger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class team_selector_controller {
    @FXML
    public Button subBtn, returnBtn;

    @FXML
    public TextField ansTxt;

    public HashMap<String, HashMap<String, String>> teams_list=Main.teams_list;

    public void initialize(){
    }

    /**
     * Подтверждение выбранной команды
     *
     * Обновление списка команд и проверка на доступность
     *
     * Выдает ошибку, если команда не доступна
     *
     * @param actionEvent
     * @throws Exception
     */

    public void selectTeam(ActionEvent actionEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.ERROR){{
            setTitle("Error Dialog");
            setHeaderText("Error with your team!");
        }};

        String team_key=org.apache.commons.codec.digest.DigestUtils.sha256Hex(ansTxt.getText());
        Main.get_teams();
        teams_list= Main.teams_list;
        if(teams_list.containsKey(team_key)&&(Integer.parseInt(teams_list.get(team_key).get("state"))==1)){
            Main.team=team_key;
            StageChanger.simpleChangeStage("Главное меню", "main_menu", subBtn);

        }else if(team_key.equals("757b7d716be2b8c25fb166ff696b4d3de46b463592d7f96405a320d10cfb5660")){
            Main.get_appeals();
            Main.get_questions();
            StageChanger.simpleChangeStage("Админка","admin",subBtn);

        }else if(!(teams_list.containsKey(team_key))){
            alert.setContentText("Looks like the team you are trying to access doesn't exist!");
            alert.showAndWait();

        }else if(Integer.parseInt(teams_list.get(team_key).get("state"))==2){
            alert.setContentText("Looks like the team you are trying to access has already played in this tournament!");
            alert.showAndWait();
        }
    }

    /**
     * Возврат в главное меню
     *
     * @param actionEvent
     * @throws Exception
     */

    public void onReturn(ActionEvent actionEvent) throws Exception {
        StageChanger.simpleChangeStage("Главное меню", "main_menu", returnBtn);
    }
}
