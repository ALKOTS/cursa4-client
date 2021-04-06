import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class main_menu_controller {


	@FXML
	public Button startBtn;

	@FXML
	public Button teamSelectBtn;

	@FXML
	public Label teamLbl;

	public static String team = "";

	public void initialize() {
		teamLbl.setText("Команда не выбрана");
	}

	public void onStart(ActionEvent actionEvent) throws Exception {
		try {
			if(teamLbl.getText()!="Команда не выбрана"){
				StageChanger.changeStage("Что? Где? Когда?", "game", startBtn);
			}
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Error with your team!");
				alert.setContentText("Looks like you haven't selected your team yet! You can't start a game without a team!");

				alert.showAndWait();
			}


			
		} catch (Exception e) {
			System.out.println("Mocha s'ela govno");
		}
	}


	public void onTeamSelect(ActionEvent actionEvent) throws Exception {
		StageChanger.changeStage("Выберите команду", "team_selector", startBtn);

	}

	public void receiveTeam(String team){
		teamLbl.setText(team);
	}

}





