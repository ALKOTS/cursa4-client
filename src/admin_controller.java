import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.util.HashMap;
import java.util.Map;

//https://stackoverflow.com/questions/52320778/javafx-change-scene
//https://stackoverflow.com/questions/44949774/javafx-how-to-add-elements-eg-buttons-dynamically-created-from-items-stored-in/45033806
//https://metanit.com/java/javafx/4.1.php

public class admin_controller {
    public static ArrayList<Button> btnList =new ArrayList<>();

    public static ArrayList<Button> constBtnList=new ArrayList<>();

    public static ArrayList<ArrayList<String>> aps=new ArrayList<>();

    public static HashMap<String, Integer> teams_list= new HashMap<>();

    public static VBox v=new VBox();


    public static void generateAdmin() throws IOException {
        //Undefined
        Tab undefinedTab=new Tab("Undefined");
        Button test=new Button("Test");
        test.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(aps);
                System.out.println(teams_list);
            }
        });
        undefinedTab.setContent(test);

        //Apellations
        Button accChanges=new Button("Accept changes");
        Button disChanges=new Button("Discard changes");
        Button mainMenu=new Button("Main menu");
        //Dynamic
        for (int i=0; i<aps.size(); i++){
            Label qsLbl = new Label(aps.get(i).get(0));
            Label ansLbl = new Label(aps.get(i).get(1));
            Label rAnsLbl = new Label(aps.get(i).get(2));

            VBox lblContainer = new VBox(qsLbl, ansLbl, rAnsLbl);
            lblContainer.setMinSize(684,50);

            Button acceptBtn = new Button("A");
            acceptBtn.setPrefSize(44,50);
            //btnList.add(acceptBtn);

            Button denyBtn = new Button("D");
            denyBtn.setPrefSize(44,50);
            //btnList.add(denyBtn);

            Integer index=i;
            acceptBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    aps.get(index).set(4,"Y");

                    acceptBtn.setStyle("-fx-background-color: #00ff00; ");
                    denyBtn.setDisable(true);

                    btnList.add(acceptBtn);
                    btnList.add(denyBtn);
                    accChanges.setDisable(false);
                    disChanges.setDisable(false);
                }
            });

            denyBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    aps.get(index).set(4,"N");
                    denyBtn.setStyle("-fx-background-color: #ff0000; ");
                    acceptBtn.setDisable(true);

                    btnList.add(acceptBtn);
                    btnList.add(denyBtn);

                    accChanges.setDisable(false);
                    disChanges.setDisable(false);

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






        accChanges.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                constBtnList.addAll(btnList);
                btnList.clear();

                for(int i = 0; i< constBtnList.size(); i++){
                    constBtnList.get(i).setDisable(true);
                }

                ArrayList<Integer> toRemove=new ArrayList<>();
                for (int i=0; i<aps.size(); i++){

                    switch (aps.get(i).get(4)) {
                        case "N":
                            toRemove.add(i);
                            break;
                        case "Y":
                            teams_list.replace(aps.get(i).get(3), teams_list.get(aps.get(i).get(3)) + 1);
                            toRemove.add(i);
                            break;
                        default:
                            break;
                    }
                    accChanges.setDisable(true);
                    disChanges.setDisable(true);

                }
                for(int i=0; i<toRemove.size(); i++){
                    aps.remove(aps.get(toRemove.get(i)));
                }

                Main.aps.clear();
                Main.teams_list.clear();
                for(ArrayList<String> item:aps) Main.aps.add((ArrayList<String>) item.clone());
                for (Map.Entry me:teams_list.entrySet()){
                    try{
                        Main.teams_list.put(String.valueOf(me.getKey()),Integer.parseInt(String.valueOf(me.getValue())));
                    }catch (NumberFormatException nfe){
                        continue;
                    }
                }
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Changes saved!");

                alert.showAndWait();
            }
        });
        disChanges.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for(int i=0; i<btnList.size(); i++){
                    btnList.get(i).setDisable(false);
                    btnList.get(i).setStyle(null);
                }
                btnList.clear();
                aps=Main.aps;

                accChanges.setDisable(true);
                disChanges.setDisable(true);
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
        try {
            aps.clear();
            teams_list.clear();
            btnList.clear();
            v.getChildren().clear();
            constBtnList.clear();
        }catch (Exception e){
            System.out.println("All clear");
        }

        for(ArrayList<String> item:Main.aps) aps.add((ArrayList<String>) item.clone());
        for (Map.Entry me:Main.teams_list.entrySet()){
            try{
                teams_list.put(String.valueOf(me.getKey()),Integer.parseInt(String.valueOf(me.getValue())));
            }catch (NumberFormatException nfe){
                continue;
            }

        }

        generateAdmin();
    }

}
