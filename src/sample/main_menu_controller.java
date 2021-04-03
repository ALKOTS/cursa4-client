package sample;

import javafx.fxml.FXML;

import javafx.scene.control.*;


public class main_menu_controller {
	@FXML
	public Button startBtn;

	@FXML 
	public Button teamSelectBtn;

	public String team="";

	public void onStart(javafx.event.ActionEvent actionEvent) throws Exception {
		SceneChanger.changeScene("questionScene");
	}

	public void onSelectTeam(javafx.event.ActionEvent actionEvent) throws Exception{
		
		try{
			FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("team_selector.fxml"));
			Parent root1=(Parent) fxmlLoader.load();
			Stage team_select_stage=new Stage();
			stage.setTitle('Select team');
			stage.setScene(new Scene(root1));
			stage.show(); 
		}catch(Exception e){
			System.out.println("Mocha s'ela govno");
		}
		
	}
}
