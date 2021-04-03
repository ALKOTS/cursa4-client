package sample;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
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

	public static String team="";

	public void initialize(){teamLbl.setText("Команда не выбрана");}

	public void onStart(javafx.event.ActionEvent actionEvent) throws Exception {
		SceneChanger.changeScene("questionScene");
	}

	public void onSelectTeam(javafx.event.ActionEvent actionEvent) throws Exception{

		





	}


}
