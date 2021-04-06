import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;


public class main_menu_controller {


	@FXML
	public Button startBtn;

	@FXML
	public Button teamSelectBtn;

	@FXML
	public Label currTeamLbl,teamLbl;

	public static String team = "";

	public HashMap<String, Integer> teams_list=Main.teams_list;

	public void initialize() {
		currTeamLbl.setText("Команда не выбрана");
		String teams = "";
		for (Map.Entry me:teams_list.entrySet()){
			teams+=me.getKey()+": "+me.getValue()+"\n";

		}
		teamLbl.setText(teams);
	}

	public void onStart(ActionEvent actionEvent) throws Exception {

		if(currTeamLbl.getText()!="Команда не выбрана"){
			StageChanger.changeStage("Что? Где? Когда?", "game", startBtn);
		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Error with your team!");
			alert.setContentText("Looks like you haven't selected your team yet! You can't start a game without a team!");

			alert.showAndWait();
		}


	}


	public void onTeamSelect(ActionEvent actionEvent) throws Exception {
		StageChanger.changeStage("Выберите команду", "team_selector", startBtn);

	}

	public void receiveTeam(String team){
		currTeamLbl.setText(team);
	}

}





