import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//https://stackoverflow.com/questions/52320778/javafx-change-scene
//https://stackoverflow.com/questions/44949774/javafx-how-to-add-elements-eg-buttons-dynamically-created-from-items-stored-in/45033806
//https://metanit.com/java/javafx/4.1.php

public class admin_controller {
    @FXML
    public static Button addStuff; //approveBtn, denyBtn;

    @FXML
    public VBox appsContainer;

    @FXML
    public Tab appTab;

    public ArrayList<Label> infoList=new ArrayList<>();
    public ArrayList<Button> btnList=new ArrayList<>();

    public static ArrayList aps=Main.aps;

    public static VBox v=new VBox();

    public static void generateAdmin() throws IOException {
        Integer apsNumber= aps.size();

        //Undefined
        Tab undefinedTab=new Tab("Undefined");

        //Apellations
        //Dynamic
        for (int i=0; i<apsNumber; i++){
            Label qsLbl = new Label("Question");
            //qsLbl.setPrefSize(708,21);
            Label ansLbl = new Label("Answer");
            //ansLbl.setPrefSize(708,21);
            Label rAnsLbl = new Label("Right answer");
            //rAnsLbl.setPrefSize(708,21);

            VBox lblContainer = new VBox(qsLbl, ansLbl, rAnsLbl);
            lblContainer.setMinSize(684,50);

            Button acceptBtn = new Button("A");
            acceptBtn.setPrefSize(44,50);
            acceptBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    qsLbl.setText("YES");
                }
            });

            Button denyBtn = new Button("D");
            denyBtn.setPrefSize(44,50);
            denyBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    qsLbl.setText("NO");
                }
            });

            HBox blockContainer = new HBox(lblContainer, acceptBtn, denyBtn);
            //blockContainer.setPrefSize(765,50);
            blockContainer.setMinSize(765,50);

            BorderPane allContainer = new BorderPane();
            allContainer.setLeft(blockContainer);
            //allContainer.setPrefSize(510,40);
            v.getChildren().add(allContainer);
        }

        //static
        v.setLayoutX(14);

        AnchorPane in=new AnchorPane(v);
        in.setPrefSize(600,350);

        ScrollPane sp=new ScrollPane();
        sp.setLayoutY(11);
        sp.setPrefSize(800,400);
        sp.setContent(in);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);


        AnchorPane ap=new AnchorPane(sp);
        //ap.setPrefSize(800,600);
        AnchorPane.setTopAnchor(sp,0.0);
        AnchorPane.setBottomAnchor(sp,0.0);
        AnchorPane.setRightAnchor(sp,0.0);
        AnchorPane.setLeftAnchor(sp,0.0);

        Tab appellationsTab=new Tab("Аппеляции");
        appellationsTab.setContent(ap);

        TabPane root=new TabPane(undefinedTab,appellationsTab);
        root.setPrefSize(800,600);


        Stage stage = new Stage();
        stage.setScene(new Scene(root,800,600));
        stage.setTitle("Админка");
        stage.show();

//        Stage cur_stage = (Stage) addStuff.getScene().getWindow();
//        cur_stage.close();
    }

    public static void initialize() throws IOException {
        generateAdmin();
    }

    public void addStuff(ActionEvent actionEvent) {

    }

    public void onApprove(ActionEvent actionEvent) {

    }

    public void onDeny(ActionEvent actionEvent) {
    }
}
