import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class main_menu_controller {

	@FXML
	public Button startBtn, teamSelectBtn, aboutBtn;

	@FXML
	public Label currTeamLbl,teamLbl;

	public HashMap<String, HashMap<String, String>> teams_list=Main.teams_list;

	public void initialize() throws UnirestException {
		receiveTeam();
		drawScoreBoard();
	}

	public void onStart(ActionEvent actionEvent) throws Exception {
		if(Main.team!=null){
			StageChanger.simpleChangeStage("Что? Где? Когда?","game", startBtn);
		}
		else {
			new Alert(Alert.AlertType.ERROR){{
				setTitle("Error Dialog");
				setHeaderText("Error with your team!");
				setContentText("Looks like you haven't selected your team yet! You can't start a game without a team!");
				showAndWait();
			}};
		}
	}

	public void onTeamSelect(ActionEvent actionEvent) throws Exception {
		StageChanger.simpleChangeStage("Выберите команду", "team_selector", startBtn);
	}

	public void receiveTeam() throws UnirestException {
		if(Main.team!=null){
			currTeamLbl.setText(Main.teams_list.get(Main.team).get("name"));

			Main.teams_list.get(Main.team).put("score","0");
			JSONObject jo=new JSONObject(){{
				put("name",Main.teams_list.get(Main.team).get("name"));
				put("accessKey",Main.team);
				put("state",2);
				put("score",0);
			}};
			HttpResponse<JsonNode> r= Unirest.put(Main.teams_list.get(Main.team).get("link"))
					.header("Content-type", "application/hal+json")
					.body(jo)
					.asJson();
		}
	}

	public void drawScoreBoard(){
		String teams = "";
		for (Map.Entry me:teams_list.entrySet()){
			if(teams_list.get(me.getKey()).get("score")!=null){
				teams+=teams_list.get(me.getKey()).get("name") +": "+teams_list.get(me.getKey()).get("score")+"\n";
			}
		}
		teamLbl.setText(teams);
	}

	public void onAbout(ActionEvent actionEvent) throws Exception {
		StageChanger.aboutChangeStage();
	}
}