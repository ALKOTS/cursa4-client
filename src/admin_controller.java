import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

//https://stackoverflow.com/questions/52320778/javafx-change-scene
//https://stackoverflow.com/questions/44949774/javafx-how-to-add-elements-eg-buttons-dynamically-created-from-items-stored-in/45033806
//https://metanit.com/java/javafx/4.1.php

public class admin_controller {
    @FXML
    public Button addStuff; //approveBtn, denyBtn;

    @FXML
    public VBox appsContainer;

    @FXML
    public Tab appTab;

    public ArrayList<Label> infoList=new ArrayList<>();
    public ArrayList<Button> btnList=new ArrayList<>();

    public void addStuff(ActionEvent actionEvent) {

    }

    public void onApprove(ActionEvent actionEvent) {

    }

    public void onDeny(ActionEvent actionEvent) {
    }
}
