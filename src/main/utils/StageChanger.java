package main.utils;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.stage.Window;

import java.io.IOException;

public class StageChanger {

    /**
     * Обычная смена окна
     *
     * @param title stage title
     * @param scene scene to apply
     * @param button button of the previous stage
     * @throws Exception
     */

    public static void simpleChangeStage(String title, String scene, Button button) throws Exception {
        Stage team_selector_stage = new Stage();
        team_selector_stage.setTitle(title);
        team_selector_stage.setScene(SceneChanger.changeScene(scene));
        team_selector_stage.show();

        //закрытие предыдущего окна
        Stage cur_stage = (Stage) button.getScene().getWindow();
        cur_stage.close();
    }

    /**
     * Открытие сцены без закрытия предыдущей
     *
     * @param title stage title
     * @param scene scene to apply
     * @return
     * @throws Exception
     */
    public static Stage noCloseChangeStage(String title, String scene) throws Exception {
        Stage team_selector_stage = new Stage();
        team_selector_stage.setTitle(title);
        team_selector_stage.setScene(SceneChanger.changeScene(scene));
        team_selector_stage.show();

        return team_selector_stage;
    }
}




//			FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
//			Parent root = loader.load();
//
//			game_controller gc = loader.getController();
//			gc.recieveTeam(currTeamLbl.getText());
//
//			Stage stage = new Stage();
//			stage.setScene(new Scene(root));
//			stage.setTitle("Что? Где? Когда?");
//			stage.show();
//
//			Stage cur_stage = (Stage) startBtn.getScene().getWindow();
//			cur_stage.close();


