import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class team_selector_controller {
    @FXML
    public Button subBtn;

    @FXML
    public TextField ansTxt;

    public void initialize(){

    }

    public void selectTeam(ActionEvent actionEvent) throws IOException {
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
    }
}
