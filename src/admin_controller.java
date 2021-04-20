import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.json.JSONObject;


import java.lang.reflect.Array;
import java.util.*;

public class admin_controller {
    @FXML
    public Button accChanges, disChanges, mainMenu;

    @FXML
    public AnchorPane in1,in2;

    public static ArrayList<Button> btnList =new ArrayList<>();

    public static ArrayList<Button> constBtnList=new ArrayList<>();

    public static ArrayList<ArrayList<String>> aps=new ArrayList<>();

    public static ArrayList<ArrayList<String>> questions_list=new ArrayList<>();

    public static HashMap<String, HashMap<String, String>> teams_list= new HashMap<>();

    public static ArrayList<BorderPane> allContainerContainer=new ArrayList<>();

    public static VBox v1 =new VBox();
    public static VBox v2 =new VBox();

    public static VBox old_v1=new VBox();



    public void generateAdmin() throws Exception {
        //Questions

        //Dynamic

        for(int i=0; i<questions_list.size(); i++){
            Label qsLbl=new Label(questions_list.get(i).get(0));
            Label ansLbl=new Label(questions_list.get(i).get(1));

            VBox lblContainer=new VBox(qsLbl,ansLbl);
            lblContainer.setMinSize(684,50);
            VBox.setVgrow(lblContainer, Priority.ALWAYS);

            Button editBtn = new Button("E");
            editBtn.setPrefSize(44,50);

            Button deleteBtn = new Button("D");
            deleteBtn.setPrefSize(44,50);

            HBox blockContainer = new HBox(lblContainer, editBtn, deleteBtn);// btnContainer);
            blockContainer.setMinSize(765,50);

            BorderPane allContainer = new BorderPane(){{
                setLeft(blockContainer);
            }};

            v1.getChildren().add(allContainer);

            //button functionality

            int finalI = i;
            editBtn.setOnAction(actionEvent -> {

                String old_qs=qsLbl.getText();
                String old_ans=ansLbl.getText();
                TextField qsTxt=new TextField(){{setText(old_qs);}};
                TextField ansTxt=new TextField(){{setText(old_ans);}};
                lblContainer.getChildren().clear();
                lblContainer.getChildren().addAll(qsTxt,ansTxt);
                Button accBtn=new Button("A");
                accBtn.setPrefSize(44,50);
                Button denyBtn=new Button("D");
                denyBtn.setPrefSize(44,50);

                editBtn.setVisible(false);
                deleteBtn.setVisible(false);
                blockContainer.getChildren().add(1,accBtn);
                blockContainer.getChildren().add(2,denyBtn);


                accBtn.setOnAction(actionEvent1 -> {

                    qsLbl.setText(qsTxt.getText());
                    ansLbl.setText(ansTxt.getText());
                    lblContainer.getChildren().clear();
                    lblContainer.getChildren().addAll(qsLbl,ansLbl);

                    if(!qsLbl.getText().equals(questions_list.get(finalI).get(0))){
                        System.out.println(questions_list.get(finalI).get(0));
                        System.out.println(qsLbl.getText());
                        qsLbl.setStyle("-fx-background-color: #00ff00; ");
                    }else {
                        qsLbl.setStyle(null);
                    }
                    if(!ansLbl.getText().equals(questions_list.get(finalI).get(1))){
                        ansLbl.setStyle("-fx-background-color: #00ff00; ");
                    }else {
                        ansLbl.setStyle(null);
                    }

                    blockContainer.getChildren().remove(1);
                    blockContainer.getChildren().remove(1);
                    editBtn.setVisible(true);
                    deleteBtn.setVisible(true);
                    accChanges.setDisable(false);
                    disChanges.setDisable(false);

                });

                denyBtn.setOnAction(actionEvent12 -> {

                    qsLbl.setText(old_qs);
                    ansLbl.setText(old_ans);
                    lblContainer.getChildren().clear();
                    lblContainer.getChildren().addAll(qsLbl,ansLbl);

                    blockContainer.getChildren().remove(1);
                    blockContainer.getChildren().remove(1);
                    editBtn.setDisable(false);
                    deleteBtn.setDisable(false);

                });
            });

            int finalI1 = i;
            deleteBtn.setOnAction(actionEvent -> {
                v1.getChildren().remove(finalI1);
                accChanges.setDisable(false);
                disChanges.setDisable(false);
            });
        }

        //Static
        old_v1.getChildren().addAll(v1);  //backup for discard changes button
        in1.getChildren().add(v1);

        //Appeals

        //Dynamic
        for (int i=0; i<aps.size(); i++){
            Label qsLbl = new Label(aps.get(i).get(0));
            Label ansLbl = new Label(aps.get(i).get(1));
            Label rAnsLbl = new Label(aps.get(i).get(2));

            VBox lblContainer = new VBox(qsLbl, ansLbl, rAnsLbl);
            lblContainer.setMinSize(684,50);

            Button acceptBtn = new Button("A");
            acceptBtn.setPrefSize(44,50);

            Button denyBtn = new Button("D");
            denyBtn.setPrefSize(44,50);

            HBox blockContainer = new HBox(lblContainer, acceptBtn, denyBtn);

            blockContainer.setMinSize(765,50);

            BorderPane allContainer = new BorderPane(){{
                setLeft(blockContainer);
            }};

            v2.getChildren().add(allContainer);

            //buttons functionality
            Integer index=i;
            acceptBtn.setOnAction(actionEvent -> {

                aps.get(index).set(4,"Y");

                acceptBtn.setStyle("-fx-background-color: #00ff00; ");
                denyBtn.setDisable(true);

                btnList.add(acceptBtn);
                btnList.add(denyBtn);
                allContainerContainer.add(allContainer);
                accChanges.setDisable(false);
                disChanges.setDisable(false);

            });

            denyBtn.setOnAction(actionEvent -> {

                aps.get(index).set(4,"N");
                denyBtn.setStyle("-fx-background-color: #ff0000; ");
                acceptBtn.setDisable(true);

                btnList.add(acceptBtn);
                btnList.add(denyBtn);
                allContainerContainer.add(allContainer);
                accChanges.setDisable(false);
                disChanges.setDisable(false);

            });
        }

        //static
        in2.getChildren().add(v2);
    }

    public void onAcceptChanges(ActionEvent actionEvent) {
        for(int i=0; i<allContainerContainer.size(); i++){
            allContainerContainer.get(i).getChildren().clear();
        }

        ArrayList<Integer> toRemove=new ArrayList<>();
        for (int i=0; i<aps.size(); i++){

            switch (aps.get(i).get(4)) {
                case "Y":
                    teams_list.get(aps.get(i).get(3)).put("score", String.valueOf(Integer.parseInt(teams_list.get(aps.get(i).get(3)).get("score"))+1));
                case "N":
                    toRemove.add(i);
                    break;
                default:
                    break;
            }
            accChanges.setDisable(true);
            disChanges.setDisable(true);

        }
        System.out.println(toRemove);
        Collections.sort(toRemove);

        for(int i=toRemove.size()-1; i>-1; i--){
            System.out.println(toRemove.get(i));
            aps.remove(aps.get(toRemove.get(i)));
        }

        Main.aps.removeAll(aps);
        for(int i=Main.aps.size()-1; i>-1; i--){
            try {
                Unirest.delete(Main.aps.get(i).get(5))
                        .header("Content-type", "application/hal+json")
                        .asJson();
                System.out.println("YES");
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        }



        Main.aps.clear();
        Main.teams_list.clear();
        for(ArrayList<String> item:aps) Main.aps.add((ArrayList<String>) item.clone());
        for (Map.Entry me:teams_list.entrySet()){
            try{
                HttpResponse<JsonNode> r=Unirest.put(teams_list.get(me.getKey()).get("link"))
                        .header("Content-type", "application/hal+json")
                        .body(new JSONObject(){{
                            put("name",teams_list.get(me.getKey()).get("name"));
                            put("accessKey",me.getKey());
                            put("state",teams_list.get(me.getKey()).get("state"));
                            put("score",teams_list.get(me.getKey()).get("score"));
                        }})
                        .asJson();
                Main.teams_list.put(String.valueOf(me.getKey()), (HashMap<String, String>) me.getValue());
            }catch (Exception nfe){
                continue;
            }
        }

        new Alert(Alert.AlertType.CONFIRMATION){{
            setTitle("Success");
            setHeaderText("Changes saved!");
            showAndWait();
        }};
    }

    public void onDiscardChanges(ActionEvent actionEvent) {
        for(int i=0; i<btnList.size(); i++){
            btnList.get(i).setDisable(false);
            btnList.get(i).setStyle(null);
        }
        btnList.clear();
        for(ArrayList<String> item:Main.aps) aps.add((ArrayList<String>) item.clone());

        v1.getChildren().addAll(old_v1);

        accChanges.setDisable(true);
        disChanges.setDisable(true);
    }

    public void onMainMenu(ActionEvent actionEvent) throws Exception {
        StageChanger.simpleChangeStage("Главное меню","main_menu", mainMenu);
    }

    public void initialize() throws Exception {
        try {
            aps.clear();
            teams_list.clear();
            btnList.clear();
            v2.getChildren().clear();
            v1.getChildren().clear();
            old_v1.getChildren().clear();
            constBtnList.clear();
            allContainerContainer.clear();
            questions_list.clear();
        }catch (Exception e){
            System.out.println("All clear");
        }



        accChanges.setDisable(true);
        disChanges.setDisable(true);

        for(ArrayList<String> item:Main.aps) aps.add((ArrayList<String>) item.clone());
        for(ArrayList<String> item:Main.questions_list) questions_list.add((ArrayList<String>) item.clone());
        for (Map.Entry me:Main.teams_list.entrySet()){
            try{
                teams_list.put(String.valueOf(me.getKey()), (HashMap<String, String>) me.getValue());
            }catch (NumberFormatException nfe){
                continue;
            }

        }
//        System.out.println(Main.aps);
        System.out.println(questions_list);
        System.out.println(aps);
        generateAdmin();
    }

}
