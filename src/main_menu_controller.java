import javafx.event.ActionEvent;
import javafx.fxml.FXML;

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
			Stage questions_stage = new Stage();
			questions_stage.setTitle("Что? Где? Когда?");
			questions_stage.setScene(SceneChanger.changeScene("questions"));
			questions_stage.show();

			Stage cur_stage = (Stage) startBtn.getScene().getWindow();
			cur_stage.close();

			
		} catch (Exception e) {
			System.out.println("Mocha s'ela govno");
		}
	}




}





