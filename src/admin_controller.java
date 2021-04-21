import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.json.JSONObject;


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

    public static ArrayList<BorderPane> old_v1 =new ArrayList<>();
    public static ArrayList<BorderPane> allContainerContainer2 =new ArrayList<>();
    public static HashMap<Integer, String> changedQ =new HashMap<>();
    public static HashMap<Integer, String> changedA =new HashMap<>();

    public static ArrayList<Integer> question_to_delete=new ArrayList<>();

    //public static VBox v1 =new VBox();
    public static ArrayList<BorderPane> v1=new ArrayList<>();
    public static VBox v2 =new VBox();





    public void generateAdmin() throws Exception {
        //Questions

        //Dynamic

        for(int i=0; i<questions_list.size(); i++){
            Label qsLbl=new Label(questions_list.get(i).get(0));
            Label ansLbl=new Label(questions_list.get(i).get(1));

            VBox lblContainer=new VBox(qsLbl,ansLbl){{
                setMinSize(684,50);
            }};
            VBox.setVgrow(lblContainer, Priority.ALWAYS);

            Button editBtn = new Button("E"){{
                setPrefSize(44,50);
            }};

            Button deleteBtn = new Button("D"){{
                setPrefSize(44,50);
            }};

            HBox blockContainer = new HBox(lblContainer, editBtn, deleteBtn){{
                setMinSize(765,50);
            }};

            BorderPane allContainer = new BorderPane(){{
                setLeft(blockContainer);
            }};


            old_v1.add(allContainer);
            //v1.getChildren().add(allContainer);
            v1.add(allContainer);


            //Static
            in1.getChildren().add(new VBox(){{getChildren().addAll(v1);}});
            //button functionality

            int finalI = i;
            editBtn.setOnAction(actionEvent -> {

                String old_qs=qsLbl.getText();
                String old_ans=ansLbl.getText();
                TextField qsTxt=new TextField(){{setText(old_qs);}};
                TextField ansTxt=new TextField(){{setText(old_ans);}};
                lblContainer.getChildren().clear();
                lblContainer.getChildren().addAll(qsTxt,ansTxt);
                Button accBtn=new Button("A"){{
                    setPrefSize(44,50);
                }};
                Button denyBtn=new Button("D"){{
                    setPrefSize(44,50);
                }};

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
                        qsLbl.setStyle("-fx-background-color: #00ff00; ");
                        changedQ.put(finalI, qsLbl.getText());
                    }else {
                        qsLbl.setStyle(null);
                        changedQ.remove(finalI);
                    }
                    if(!ansLbl.getText().equals(questions_list.get(finalI).get(1))){
                        ansLbl.setStyle("-fx-background-color: #00ff00; ");
                        changedA.put(finalI, ansLbl.getText());
                    }else {
                        ansLbl.setStyle(null);
                        changedA.remove(finalI);
                    }

                    blockContainer.getChildren().remove(1);
                    blockContainer.getChildren().remove(1);
                    editBtn.setVisible(true);
                    deleteBtn.setVisible(true);
                    accChanges.setDisable(false);
                    disChanges.setDisable(false);

                    System.out.println(changedQ);
                    System.out.println(changedA);
                });

                denyBtn.setOnAction(actionEvent12 -> {
                    qsLbl.setText(old_qs);
                    ansLbl.setText(old_ans);
                    lblContainer.getChildren().clear();
                    lblContainer.getChildren().addAll(qsLbl,ansLbl);

                    blockContainer.getChildren().remove(1);
                    blockContainer.getChildren().remove(1);
                    editBtn.setVisible(true);
                    deleteBtn.setVisible(true);
                    accChanges.setDisable(false);
                    disChanges.setDisable(false);

                });
            });

            int finalI1 = i;
            deleteBtn.setOnAction(actionEvent -> {
                question_to_delete.add(finalI1);
                //System.out.println(v1.getChildren().toString());
                //v1.getChildren().remove(deleteBtn.getParent().getParent());
                v1.remove(v1.get(finalI1));
                in1.getChildren().clear();
                in1.getChildren().add(new VBox(){{getChildren().addAll(v1);}});
                System.out.println(v1.get(finalI1));
                //System.out.println(v1.getChildren().toString());
                accChanges.setDisable(false);
                disChanges.setDisable(false);
            });
        }



        //Appeals

        //Dynamic
        for (int i=0; i<aps.size(); i++){
            Label qsLbl = new Label(aps.get(i).get(0));
            Label ansLbl = new Label(aps.get(i).get(1));
            Label rAnsLbl = new Label(aps.get(i).get(2));

            VBox lblContainer = new VBox(qsLbl, ansLbl, rAnsLbl){{
                setMinSize(684,50);
            }};

            Button acceptBtn = new Button("A"){{
                setPrefSize(44,50);
            }};

            Button denyBtn = new Button("D"){{
                setPrefSize(44,50);
            }};

            HBox blockContainer = new HBox(lblContainer, acceptBtn, denyBtn){{
                setMinSize(765,50);
            }};

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
                allContainerContainer2.add(allContainer);
                accChanges.setDisable(false);
                disChanges.setDisable(false);

            });

            denyBtn.setOnAction(actionEvent -> {

                aps.get(index).set(4,"N");
                denyBtn.setStyle("-fx-background-color: #ff0000; ");
                acceptBtn.setDisable(true);

                btnList.add(acceptBtn);
                btnList.add(denyBtn);
                allContainerContainer2.add(allContainer);
                accChanges.setDisable(false);
                disChanges.setDisable(false);

            });
        }

        //static
        in2.getChildren().add(v2);
    }

    public void onAcceptChanges(ActionEvent actionEvent) throws UnirestException {
        for(int i = 0; i< allContainerContainer2.size(); i++){
            allContainerContainer2.get(i).getChildren().clear();
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
        Collections.sort(toRemove);

        for(int i=toRemove.size()-1; i>-1; i--){
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


        for (int i=question_to_delete.size()-1; i>-1; i--){
            Unirest.delete(questions_list.get(question_to_delete.get(i)).get(2))
                    .header("Content-type", "application/hal+json")
                    .asJson();
            questions_list.remove(questions_list.get(question_to_delete.get(i)));
        }
        //System.out.println(v1.getChildren().toString());
        System.out.println(questions_list);

        for(int i=0; i<v1.size(); i++){
            HBox bb=(HBox) v1.get(i).getLeft();
            VBox vv=(VBox) bb.getChildren().get(0);
            Label llQ=(Label) vv.getChildren().get(0);
            Label llA=(Label)  vv.getChildren().get(1);
            questions_list.get(i).set(0,llQ.getText());
            questions_list.get(i).set(1,llA.getText());

            llQ.setStyle(null);
            llA.setStyle(null);
        }

        System.out.println(questions_list);
        for(int i=0; i<questions_list.size(); i++){
            HttpResponse<JsonNode> r=Unirest.put(questions_list.get(i).get(2))
                    .header("Content-type", "application/hal+json")
                    .body(new JSONObject(){{
                        put("question",questions_list.get(0));
                        put("answer", questions_list.get(1));
                    }})
                    .asJson();
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
        Main.get_questions();

        old_v1.clear();
        for (int i=0; i<v1.size(); i++){
            old_v1.add(v1.get(i));
        }

        changedA.clear();
        changedQ.clear();

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

        for(Map.Entry me:changedQ.entrySet()){
            HBox bb=(HBox) old_v1.get((Integer) me.getKey()).getLeft();
            VBox vv=(VBox) bb.getChildren().get(0);
            Label ll=(Label)  vv.getChildren().get(0);
            ll.setText(questions_list.get((Integer) me.getKey()).get(0));
            ll.setStyle(null);
        }
        question_to_delete.clear();
        for(Map.Entry me:changedA.entrySet()){
            HBox bb=(HBox) old_v1.get((Integer) me.getKey()).getLeft();
            VBox vv=(VBox) bb.getChildren().get(0);
            Label ll=(Label)  vv.getChildren().get(1);
            ll.setText(questions_list.get((Integer) me.getKey()).get(1));
            ll.setStyle(null);
        }

        v1.clear();
        in1.getChildren().clear();
        changedQ.clear();
        changedA.clear();
        v1.addAll(old_v1);
        in1.getChildren().add(new VBox(){{getChildren().addAll(v1);}});

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
            v1.clear();
            old_v1.clear();
            constBtnList.clear();
            changedQ.clear();
            changedA.clear();
            allContainerContainer2.clear();
            questions_list.clear();
            question_to_delete.clear();
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
