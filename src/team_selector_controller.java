import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class team_selector_controller {
    @FXML
    public Button subBtn;

    @FXML
    public TextField ansTxt;

    public HashMap<String, Integer> teams_list=Main.teams_list;

    public void initialize(){

    }

//    public VBox v=new VBox();
//
//    public void generateAdmin(Integer apsNumber) throws IOException {
//        //Undefined
//        Tab undefinedTab=new Tab("Undefined");
//
//        //Apellations
//        //Dynamic
//        for (int i=0; i<apsNumber; i++){
//            Label qsLbl = new Label("Question");
//            //qsLbl.setPrefSize(708,21);
//            Label ansLbl = new Label("Answer");
//            //ansLbl.setPrefSize(708,21);
//            Label rAnsLbl = new Label("Right answer");
//            //rAnsLbl.setPrefSize(708,21);
//
//            VBox lblContainer = new VBox(qsLbl, ansLbl, rAnsLbl);
//            lblContainer.setMinSize(684,50);
//
//            Button acceptBtn = new Button("A");
//            acceptBtn.setPrefSize(44,50);
//            acceptBtn.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent actionEvent) {
//                    qsLbl.setText("YES");
//                }
//            });
//
//            Button denyBtn = new Button("D");
//            denyBtn.setPrefSize(44,50);
//            denyBtn.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent actionEvent) {
//                    qsLbl.setText("NO");
//                }
//            });
//
//            HBox blockContainer = new HBox(lblContainer, acceptBtn, denyBtn);
//            //blockContainer.setPrefSize(765,50);
//            blockContainer.setMinSize(765,50);
//
//            BorderPane allContainer = new BorderPane();
//            allContainer.setLeft(blockContainer);
//            //allContainer.setPrefSize(510,40);
//            v.getChildren().add(allContainer);
//        }
//
//        //static
//        v.setLayoutX(14);
//
//        AnchorPane in=new AnchorPane(v);
//        in.setPrefSize(600,350);
//
//        ScrollPane sp=new ScrollPane();
//        sp.setLayoutY(11);
//        sp.setPrefSize(800,400);
//        sp.setContent(in);
//        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
//        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
//
//
//        AnchorPane ap=new AnchorPane(sp);
//        //ap.setPrefSize(800,600);
//        AnchorPane.setTopAnchor(sp,0.0);
//        AnchorPane.setBottomAnchor(sp,0.0);
//        AnchorPane.setRightAnchor(sp,0.0);
//        AnchorPane.setLeftAnchor(sp,0.0);
//
//        Tab appellationsTab=new Tab("Аппеляции");
//        appellationsTab.setContent(ap);
//
//        TabPane root=new TabPane(undefinedTab,appellationsTab);
//        root.setPrefSize(800,600);
//
//
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root,800,600));
//        stage.setTitle("Админка");
//        stage.show();
//
//        Stage cur_stage = (Stage) subBtn.getScene().getWindow();
//        cur_stage.close();
//    }

    public void selectTeam(ActionEvent actionEvent) throws Exception {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Error with your team!");

        if(teams_list.containsKey(ansTxt.getText())&&(teams_list.get(ansTxt.getText())==null)){
            Main.team=ansTxt.getText();
            StageChanger.simpleChangeStage("Что? Где? Когда?", "main_menu", subBtn);

        }else if(ansTxt.getText().equals("adminka")){
            //StageChanger.simpleChangeStage("adminka", "admin", subBtn);
            //generateAdmin(Main.apsNumber);
            admin_controller.initialize();
            Stage cur_stage = (Stage) subBtn.getScene().getWindow();
            cur_stage.close();

        }else if(!(teams_list.containsKey(ansTxt.getText()))){
            alert.setContentText("Looks like the team you are trying to access doesn't exist!");
            alert.showAndWait();

        }else if(teams_list.get(ansTxt.getText())!=null){
            alert.setContentText("Looks like the team you are trying to access has already played in this tournament!");
            alert.showAndWait();
        }


    }
}
