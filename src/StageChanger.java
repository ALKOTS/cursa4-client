import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class StageChanger {
    public static void simpleChangeStage(String title, String scene, Button button) throws Exception {
        Stage team_selector_stage = new Stage();
        team_selector_stage.setTitle(title);
        team_selector_stage.setScene(SceneChanger.changeScene(scene));
        team_selector_stage.show();

        Stage cur_stage = (Stage) button.getScene().getWindow();

        cur_stage.close();
    }

    public static FXMLLoader passAssistant(String scene) throws IOException {

        FXMLLoader loader = new FXMLLoader(StageChanger.class.getResource(scene));
        return loader;
    }
    public static void stageCloseFollow(FXMLLoader loader, String title, Button btn) throws IOException {
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();

        Stage cur_stage = (Stage) btn.getScene().getWindow();
        cur_stage.close();

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

}
