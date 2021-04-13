import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
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
import java.util.Map;

//https://stackoverflow.com/questions/52320778/javafx-change-scene
//https://stackoverflow.com/questions/44949774/javafx-how-to-add-elements-eg-buttons-dynamically-created-from-items-stored-in/45033806
//https://metanit.com/java/javafx/4.1.php

public class admin_controller {
//    @FXML
//    public static Button addStuff; //approveBtn, denyBtn;
//
//    @FXML
//    public VBox appsContainer;
//
//    @FXML
//    public Tab appTab;

    public ArrayList<Label> infoList=new ArrayList<>();
    public static ArrayList<Button> btnList=new ArrayList<>();

    public static ArrayList<ArrayList<String>> aps=new ArrayList<>();

    public static VBox v=new VBox();


    public static void generateAdmin() throws IOException {
        Integer apsNumber= aps.size();

        //Undefined
        Tab undefinedTab=new Tab("Undefined");
        Button test=new Button("Test");
        test.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(aps);
            }
        });
        undefinedTab.setContent(test);

        //Apellations
        //Dynamic
        for (int i=0; i<apsNumber; i++){
            Label qsLbl = new Label(aps.get(i).get(0));
            //qsLbl.setPrefSize(708,21);
            Label ansLbl = new Label(aps.get(i).get(1));
            //ansLbl.setPrefSize(708,21);
            Label rAnsLbl = new Label(aps.get(i).get(2));
            //rAnsLbl.setPrefSize(708,21);

            VBox lblContainer = new VBox(qsLbl, ansLbl, rAnsLbl);
            lblContainer.setMinSize(684,50);

            Button acceptBtn = new Button("A");
            acceptBtn.setPrefSize(44,50);
            btnList.add(acceptBtn);

            Button denyBtn = new Button("D");
            denyBtn.setPrefSize(44,50);
            btnList.add(denyBtn);

            Integer index=i;
            acceptBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    aps.get(index).set(4,"Y");
//                    System.out.println(index);
//                    System.out.println(aps);
                    acceptBtn.setStyle("-fx-background-color: #00ff00; ");
                    denyBtn.setDisable(true);
                }
            });

            denyBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    aps.get(index).set(4,"N");
//                    System.out.println(index);
//                    System.out.println(aps);
                    denyBtn.setStyle("-fx-background-color: #ff0000; ");
                    acceptBtn.setDisable(true);
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


        Button accChanges=new Button("Accept changes");
        Button disChanges=new Button("Discard changes");
        Button mainMenu=new Button("Main menu");



        accChanges.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ArrayList<Integer> toRemove=new ArrayList<>();
                for (int i=0; i<apsNumber; i++){
                    switch (aps.get(i).get(4)){
                        case "N":
                            //aps.remove(i);
                            toRemove.add(i);
                            break;
                        case "Y":
                            Main.teams_list.replace(aps.get(i).get(3),Main.teams_list.get(aps.get(i).get(3))+1);
                            //aps.remove(i);
                            toRemove.add(i);
                            break;
                        default:
                            break;
                    }
                }
//                System.out.println(toRemove);
                for(int i=0; i<toRemove.size(); i++){
//                    //System.out.println(i+" "+toRemove.get(i)+" "+aps.get(toRemove.get(i)));
                    aps.remove(aps.get(toRemove.get(i)));
                }
                Main.aps=aps;
            }
        });
        disChanges.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for(int i=0; i<btnList.size(); i++){
                    btnList.get(i).setDisable(false);
                    btnList.get(i).setStyle(null);
                }
                aps=Main.aps;
            }
        });
        mainMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    StageChanger.simpleChangeStage("Главное меню","main_menu", mainMenu);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        HBox adBox=new HBox(accChanges,disChanges,mainMenu);

        SplitPane splitPane=new SplitPane(sp,adBox);
        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.setDividerPosition(0,1);


        AnchorPane ap=new AnchorPane(splitPane); //sp);
        //ap.setPrefSize(800,600);
        AnchorPane.setTopAnchor(splitPane,0.0);
        AnchorPane.setBottomAnchor(splitPane,0.0);
        AnchorPane.setRightAnchor(splitPane,0.0);
        AnchorPane.setLeftAnchor(splitPane,0.0);



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
        for(ArrayList<String> item:Main.aps) aps.add((ArrayList<String>) item.clone());
        generateAdmin();
//        System.out.println(aps);
    }

}
