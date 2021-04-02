package sample;

import javafx.fxml.FXML;

import javafx.scene.control.*;


public class main_menu_controller {
    @FXML
    public Button startBtn;

    public void onStart(javafx.event.ActionEvent actionEvent) throws Exception {
        SceneChanger.changeScene("questionScene");
    }
}
