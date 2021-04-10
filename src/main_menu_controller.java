import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
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
		receiveTeam();
		drawScoreBoard();

	}

	public void onStart(ActionEvent actionEvent) throws Exception {

		if(currTeamLbl.getText()!="Команда не выбрана"){
			//

			FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
			Parent root = loader.load();

			game_controller gc = loader.getController();
			gc.recieveTeam(currTeamLbl.getText());

			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.setTitle("Что? Где? Когда?");
			stage.show();

			Stage cur_stage = (Stage) startBtn.getScene().getWindow();
			cur_stage.close();

			//

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
		StageChanger.simpleChangeStage("Выберите команду", "team_selector", startBtn);
		team=currTeamLbl.getText();
	}

//	public void receiveTeam(String team){
//		currTeamLbl.setText(team);
//	}
	public void receiveTeam(){
		currTeamLbl.setText(Main.team);
	}

	public void updateScore(String key, Integer score){
		teams_list.replace(key,null,score);
		System.out.println(key+" "+ score);
		drawScoreBoard();
	}


	public void drawScoreBoard(){
		String teams = "";
		for (Map.Entry me:teams_list.entrySet()){
			if(me.getValue()!=null){
				teams+=me.getKey()+": "+me.getValue()+"\n";
			}

		}
		teamLbl.setText(teams);
	}

}





