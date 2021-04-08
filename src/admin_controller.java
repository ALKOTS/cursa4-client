import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;

//https://stackoverflow.com/questions/52320778/javafx-change-scene
//https://stackoverflow.com/questions/44949774/javafx-how-to-add-elements-eg-buttons-dynamically-created-from-items-stored-in/45033806

public class admin_controller {
    @FXML
    public Button addStuff;

    public ArrayList<Label> infoList=new ArrayList<>();

    public void addStuff(ActionEvent actionEvent) {

    }
}
