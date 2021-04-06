import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StageChanger {
    public static void changeStage(String title, String scene, Button button) throws Exception {
        Stage team_selector_stage = new Stage();
        team_selector_stage.setTitle(title);
        team_selector_stage.setScene(SceneChanger.changeScene(scene));
        team_selector_stage.show();

        Stage cur_stage = (Stage) button.getScene().getWindow();
        cur_stage.close();
    }
}
