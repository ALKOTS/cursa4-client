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

	public HashMap<String, Integer> teams_list=Main.teams_list;

	public void initialize() {
		receiveTeam();
		drawScoreBoard();
	}

	public void onStart(ActionEvent actionEvent) throws Exception {
		if(Main.team!=null){
//			Main.teams_list.replace(Main.team,0);
//			JSONObject jo=new JSONObject(){{put("name",Main.team);put("accessKey","mm");put("state",0);}};
//			HttpResponse<JsonNode> r=Unirest.put("http://localhost:8080/teams/5")
//					.header("Content-type", "application/hal+json")
//					.body(jo)
//					.asJson();
			StageChanger.simpleChangeStage("Что? Где? Когда?","game", startBtn);
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
	}

	public void receiveTeam(){
		if(Main.team!=null){
			currTeamLbl.setText(Main.team);
		}
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