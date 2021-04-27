import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.json.JSONObject;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class admin_controller {
    @FXML
    public Button accChanges, disChanges, mainMenu, updateBtn;

    @FXML
    public AnchorPane  in1,in2;

    @FXML
    public VBox in3;


    public static ArrayList<ArrayList<String>> aps=new ArrayList<>();

    public static HashMap<String, ArrayList<String>> questions_list=new HashMap<>();

    public static HashMap<String, ArrayList<String>> teams_list= new HashMap<>();

    public static HashMap<Integer,HashMap<String, String>> changed1 =new HashMap<>();
    public static HashMap<Integer,HashMap<String, String>> changed3 =new HashMap<>();

    public static ArrayList<String> question_to_delete=new ArrayList<>();

    public static ArrayList<String> teams_to_delete=new ArrayList<>();

    public static HashMap<Integer,ArrayList<String>> added_questions=new HashMap<>();

    public static HashMap<Integer, BorderPane> v1=new HashMap<>();
    public static HashMap<Integer, BorderPane> added_v1=new HashMap<>();

    public static VBox v2 =new VBox();

    public static HashMap<Integer, BorderPane> v3=new HashMap<>();
    public static HashMap<Integer, BorderPane> added_v3=new HashMap<>();

    public void acceptVersion(VBox lblContainer, HashMap<String, ArrayList<String>> requiredList, HashMap<Integer,HashMap<String, String>> changed, String finalI, HBox blockContainer, Button editBtn, Button deleteBtn){
        for(int j=0; j<2; j++){
            if(!((Label) lblContainer.getChildren().get(j)).getText().equals(requiredList.get(String.valueOf(finalI)).get(j))){
                lblContainer.getChildren().get(j).setStyle("-fx-background-color: #00ff00; ");
                int finalJ = j;
                try{
                    changed.get(j).put(finalI, ((Label) lblContainer.getChildren().get(finalJ)).getText());
                }catch (NullPointerException npe){
                    changed.put(j, new HashMap<>(){{put(finalI, ((Label) lblContainer.getChildren().get(finalJ)).getText());}});
                }

            }else {
                try{
                    lblContainer.getChildren().get(j).setStyle(null);
                    changed.get(j).remove(finalI);
                }catch (NullPointerException npe){
                }

            }
        }

        blockContainer.getChildren().set(1,editBtn);
        blockContainer.getChildren().set(2,deleteBtn);
        accChanges.setDisable(false);
        disChanges.setDisable(false);

    }

    public void deleteVersion(ArrayList<String> what_to_delete, String finalI1, HashMap<Integer,BorderPane> v, VBox in, VBox toFill){
        what_to_delete.add(finalI1);

        v.remove(Integer.parseInt(finalI1));
        in.getChildren().clear();
        in.getChildren().add(toFill);//new VBox(){{getChildren().addAll(v.values()); getChildren().addAll(added_v.values()); getChildren().add(add_question);}});

        accChanges.setDisable(false);
        disChanges.setDisable(false);
    }

    public void generateQuestions(){
        Button add_question=new Button("+");

        //Dynamic

        for(Map.Entry me:questions_list.entrySet()){

            Label qsLbl=new Label(((ArrayList<String>)me.getValue()).get(0));
            Label ansLbl=new Label(((ArrayList<String>)me.getValue()).get(1));

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


            v1.put(Integer.parseInt(me.getKey().toString()),allContainer);

            //button functionality

            String finalI = me.getKey().toString();
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

                blockContainer.getChildren().set(1,accBtn);
                blockContainer.getChildren().set(2,denyBtn);


                accBtn.setOnAction(actionEvent1 -> {
                    qsLbl.setText(qsTxt.getText());
                    ansLbl.setText(ansTxt.getText());
                    lblContainer.getChildren().clear();
                    lblContainer.getChildren().addAll(qsLbl,ansLbl);


                    acceptVersion(lblContainer,questions_list,changed1,finalI,blockContainer,editBtn,deleteBtn);

                });

                denyBtn.setOnAction(actionEvent2 -> {
                    qsLbl.setText(old_qs);
                    ansLbl.setText(old_ans);
                    lblContainer.getChildren().clear();
                    lblContainer.getChildren().addAll(qsLbl,ansLbl);

                    blockContainer.getChildren().set(1,editBtn);
                    blockContainer.getChildren().set(2,deleteBtn);
                    accChanges.setDisable(false);
                    disChanges.setDisable(false);

                });
            });

            String finalI1 = me.getKey().toString();
            deleteBtn.setOnAction(actionEvent -> {
                question_to_delete.add(finalI1);

                v1.remove(Integer.parseInt(finalI1));
                in1.getChildren().clear();
                in1.getChildren().addAll(new VBox(){{getChildren().addAll(v1.values()); getChildren().addAll(added_v1.values()); getChildren().add(add_question);}});

                accChanges.setDisable(false);
                disChanges.setDisable(false);
            });
        }

        //Static

        add_question.setOnAction(event -> {
            in1.getChildren().remove(in1.getChildren().size()-1);

            TextField qsTxt=new TextField();
            TextField ansTxt=new TextField();

            VBox lblContainer=new VBox(qsTxt,ansTxt){{
                setMinSize(684,50);
            }};
            VBox.setVgrow(lblContainer, Priority.ALWAYS);

            Button accBtn = new Button("A"){{
                setPrefSize(44,50);
            }};

            Button denBtn = new Button("D"){{
                setPrefSize(44,50);
            }};

            HBox blockContainer = new HBox(lblContainer, accBtn, denBtn){{
                setMinSize(765,50);
            }};

            BorderPane allContainer = new BorderPane(){{
                setLeft(blockContainer);
            }};

            in1.getChildren().clear();
            in1.getChildren().add(new VBox(){{getChildren().addAll(v1.values());getChildren().addAll(added_v1.values()); getChildren().add(allContainer);}});

            accBtn.setOnAction(actionEvent -> {
                if(ansTxt.getText().length()>0 && qsTxt.getText().length()>0){

                    added_v1.put(added_v1.size(), allContainer);

                    added_questions.put(added_questions.size(),new ArrayList<>(){{add(qsTxt.getText()); add(ansTxt.getText());}});

                    blockContainer.getChildren().remove(1);
                    blockContainer.getChildren().remove(1);


                    lblContainer.getChildren().clear();
                    lblContainer.getChildren().addAll(new Label(qsTxt.getText()), new Label(ansTxt.getText()));

                    in1.getChildren().clear();
                    in1.getChildren().add(new VBox(){{getChildren().addAll(v1.values());getChildren().addAll(added_v1.values());getChildren().add(add_question);}});

                    accChanges.setDisable(false);
                    disChanges.setDisable(false);


                }
                else {
                    new Alert(Alert.AlertType.ERROR){{
                        setTitle("Error");
                        setHeaderText("One of the fields is empty");
                        showAndWait();
                    }};
                }
            });

            denBtn.setOnAction(actionEvent -> {
                in1.getChildren().clear();
                in1.getChildren().add(new VBox(){{getChildren().addAll(v1.values()); getChildren().addAll(added_v1.values()); getChildren().add(add_question);}});
            });
        });

        in1.getChildren().add(new VBox(){{getChildren().addAll(v1.values());getChildren().addAll(added_v1.values()); getChildren().add(add_question);}});
    }

    public void generateAppeals(){
        //Dynamic
        for (int i=0; i<aps.size(); i++){
            VBox lblContainer = new VBox(new Label("Вопрос: "+ aps.get(i).get(0)), new Label("Ответ: "+aps.get(i).get(1)), new Label("Правильный ответ: "+aps.get(i).get(2))){{
                setMinSize(684,50);
            }};

            Button acceptBtn = new Button("A"){{
                setPrefSize(55,60);
            }};

            Button denyBtn = new Button("D"){{
                setPrefSize(55,60);
            }};

            HBox blockContainer = new HBox(lblContainer, acceptBtn, denyBtn){{
                setMinSize(765,50);
            }};

            BorderPane allContainer = new BorderPane(){{
                setLeft(blockContainer);
            }};

            v2.getChildren().add(allContainer);

            //buttons functionality
            int index=i;
            acceptBtn.setOnAction(actionEvent -> {

                aps.get(index).set(4,"Y");

                acceptBtn.setStyle("-fx-background-color: #00ff00; ");
                denyBtn.setDisable(true);

//                btnList.add(acceptBtn);
//                btnList.add(denyBtn);
                accChanges.setDisable(false);
                disChanges.setDisable(false);

            });

            denyBtn.setOnAction(actionEvent -> {

                aps.get(index).set(4,"N");
                denyBtn.setStyle("-fx-background-color: #ff0000; ");
                acceptBtn.setDisable(true);

//                btnList.add(acceptBtn);
//                btnList.add(denyBtn);
                accChanges.setDisable(false);
                disChanges.setDisable(false);

            });
        }

        //static
        in2.getChildren().add(v2);
    }

    public void generateTeams(){
        Button addTeam=new Button("+");

        for (Map.Entry me:teams_list.entrySet()){
            Label nameLbl=new Label(((ArrayList<String>)me.getValue()).get(0));
            Label stateLbl=new Label(((ArrayList<String>)me.getValue()).get(1));
            Label scoreLbl=new Label("Счет: "+((ArrayList)me.getValue()).get(2));

            VBox lblContainer=new VBox(nameLbl,stateLbl,scoreLbl){{
                setMinSize(300,50);
            }};
            VBox.setVgrow(lblContainer, Priority.ALWAYS);

            Button editBtn=new Button("E"){{
                setPrefSize(50,50);
            }};
            Button deleteBtn=new Button("D"){{
                setPrefSize(50,50);
            }};

            HBox blockContainer=new HBox(lblContainer, editBtn, deleteBtn);
            in3.getChildren().add(blockContainer);

            String finalI=me.getKey().toString();

            editBtn.setOnAction(actionEvent -> {
                String old_name=nameLbl.getText();
                String old_state=stateLbl.getText();
                lblContainer.getChildren().clear();
                ObservableList<String> statesList=FXCollections.observableArrayList("0", "1", "2");
                lblContainer.getChildren().addAll(new TextField(nameLbl.getText()){{setPromptText("Название");}},new ComboBox<>(statesList){{setValue(stateLbl.getText());}},scoreLbl);

                Button accBtn=new Button("A"){{
                    setPrefSize(50,50);
                }};
                Button denBtn=new Button("D"){{
                    setPrefSize(50,50);
                }};

                blockContainer.getChildren().set(1,accBtn);
                blockContainer.getChildren().set(2,denBtn);

                accBtn.setOnAction(actionEvent1 -> {
                    nameLbl.setText(((TextField) lblContainer.getChildren().get(0)).getText());
                    stateLbl.setText(((ComboBox) lblContainer.getChildren().get(1)).getValue().toString());
                    lblContainer.getChildren().clear();
                    lblContainer.getChildren().addAll(nameLbl,stateLbl,scoreLbl);

                    acceptVersion(lblContainer, teams_list, changed3, finalI, blockContainer, editBtn, deleteBtn);

                });

                denBtn.setOnAction(actionEvent1 -> {
                    nameLbl.setText(old_name);
                    stateLbl.setText(old_state);
                    lblContainer.getChildren().clear();
                    lblContainer.getChildren().addAll(nameLbl, stateLbl, scoreLbl);

                    blockContainer.getChildren().set(1,editBtn);
                    blockContainer.getChildren().set(2,deleteBtn);
                    accChanges.setDisable(false);
                    disChanges.setDisable(false);

                });
            });

            deleteBtn.setOnAction(actionEvent -> {

            });
        }
    }

    public void acceptQuestions() throws Exception {
        Collections.sort(question_to_delete, Collections.reverseOrder());

        for (int i=0; i<question_to_delete.size(); i++){
            Unirest.delete(questions_list.get(question_to_delete.get(i)).get(2))
                    .header("Content-type", "application/hal+json")
                    .asJson();
            questions_list.remove(question_to_delete.get(i));
        }

        System.out.println(changed1);
        for(Map.Entry me: changed1.entrySet()){
            for(Map.Entry he: changed1.get(me.getKey()).entrySet()){
                try{
                    questions_list.get(he.getKey().toString()).set(Integer.parseInt(me.getKey().toString()), changed1.get(me.getKey()).get(he.getKey()));
                }catch (NullPointerException npe){
                    System.out.println("Trying to access non-existing element");
                }

            }
        }

        AtomicInteger asyncChecker= new AtomicInteger();

        for(Map.Entry me:questions_list.entrySet()){

            int finalI1 = Integer.parseInt(me.getKey().toString());
            new Thread(()->{
                int finalI = finalI1;
                try {
                    Unirest.put(questions_list.get(String.valueOf(finalI1)).get(2))
                            .header("Content-type", "application/hal+json")
                            .body(new JSONObject(){{
                                put("question",questions_list.get(String.valueOf(finalI)).get(0));
                                put("answer", questions_list.get(String.valueOf(finalI)).get(1));
                            }})
                            .asJson();
                } catch (Exception e) {
                }

                asyncChecker.getAndIncrement();
            }).start();
        }

        AtomicInteger asyncChecker2= new AtomicInteger();

        for(int i=0; i<added_questions.size(); i++){
            int finalI = i;
            new Thread(()->{
                try {
                    Unirest.post(Main.dbLink+"/questions")
                            .header("Content-type", "application/hal+json")
                            .body(new JSONObject(){{
                                put("question",added_questions.get(finalI).get(0));
                                put("answer", added_questions.get(finalI).get(1));
                            }})
                            .asJson();
                } catch (Exception e) {
                    System.out.println("Хоба");
                }
                asyncChecker2.getAndIncrement();
            }).start();
        }

        while (asyncChecker.get()<questions_list.size() || asyncChecker2.get() <added_questions.size()){
            Thread.sleep(100);
        }
        Main.get_questions();

        changed1.clear();

    }

    public void acceptAppeals() throws Exception{
        ArrayList<Integer> toRemove=new ArrayList<>();
        for (int i=0; i<aps.size(); i++){
            switch (aps.get(i).get(4)) {
                case "Y":
                    System.out.println(Integer.parseInt(teams_list.get(aps.get(i).get(3)).get(1)));
                    if(Integer.parseInt(teams_list.get(aps.get(i).get(3)).get(1))>1 && teams_list.get(aps.get(i).get(3)).get(2)!=null){
                        teams_list.get(aps.get(i).get(3)).set(2, String.valueOf(Integer.parseInt(teams_list.get(aps.get(i).get(3)).get(2))+1));
                    }
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

        AtomicInteger asyncChecker2=new AtomicInteger();

        for(int i=Main.aps.size()-1; i>-1; i--){
            try {
                System.out.println(Main.aps.get(i));
                Unirest.delete(Main.aps.get(i).get(5))
                        .header("Content-type", "application/hal+json")
                        .asJson();
            } catch (UnirestException e) {
            }

        }



        Main.aps.clear();
        Main.teams_list.clear();
        for(ArrayList<String> item:aps) Main.aps.add((ArrayList<String>) item.clone());
        for (Map.Entry me:teams_list.entrySet()){
            new Thread(()->{
                try{
                    Unirest.put(((ArrayList<String>)me.getValue()).get(3))
                            .header("Content-type", "application/hal+json")
                            .body(new JSONObject(){{
                                put("name",((ArrayList<String>) me.getValue()).get(0));
                                put("accessKey",me.getKey());
                                put("state",((ArrayList<String>) me.getValue()).get(1));
                                put("score",((ArrayList<String>) me.getValue()).get(2));
                            }})
                            .asJson();
                }catch (Exception nfe){
                }
                asyncChecker2.getAndIncrement();
            }).start();
            while (asyncChecker2.get()<Main.teams_list.size()){
                Thread.sleep(100);
            }
        }
        Main.get_teams();

    }

    public void acceptTeams(){

    }

    public void discardQuestions(){
        generateQuestions();
    }

    public void discardAppeals() throws Exception {
        for(ArrayList<String> item:Main.aps) aps.add((ArrayList<String>) item.clone());

        generateAdmin();
    }

    public void discardTeams(){

    }

    public void generateAdmin() throws Exception {
        try{
            in1.getChildren().clear();
            v1.clear();
            changed1.clear();
            questions_list.clear();
            question_to_delete.clear();
            added_questions.clear();
            added_v1.clear();


            in2.getChildren().clear();
            aps.clear();
            teams_list.clear();
//            btnList.clear();
            v2.getChildren().clear();

            in3.getChildren().clear();
            v3.clear();
            added_v3.clear();

            accChanges.setDisable(true);
            disChanges.setDisable(true);
        }catch (Exception nfe){ System.out.println("All clear"); }

        for(int i=0; i<Main.questions_list.size(); i++){
            questions_list.put(String.valueOf(i), (ArrayList<String>) Main.questions_list.get(i).clone());
        }
        for(ArrayList<String> item:Main.aps) aps.add((ArrayList<String>) item.clone());
        for (Map.Entry me:Main.teams_list.entrySet()){
//            try{
//                teams_list.put(me.getKey().toString(), (HashMap<String, String>) me.getValue());
//            }catch (NumberFormatException nfe){
//            }

            try {
                teams_list.put(me.getKey().toString(), new ArrayList<>(){{
                    add(((HashMap<String,String>) me.getValue()).get("name"));
                    add(((HashMap<String,String>) me.getValue()).get("state"));
                    add(((HashMap<String,String>) me.getValue()).get("score"));
                    add(((HashMap<String,String>) me.getValue()).get("link"));
                }});
            }catch (NumberFormatException nfe){
            }
        }

        generateQuestions();
        generateAppeals();
        generateTeams();
    }


    public void onAcceptChanges(ActionEvent actionEvent) throws Exception {
        acceptQuestions();
        acceptAppeals();


        generateAdmin();

        new Alert(Alert.AlertType.CONFIRMATION){{
            setTitle("Success");
            setHeaderText("Changes saved!");
            showAndWait();
        }};
    }

    public void onDiscardChanges(ActionEvent actionEvent) throws Exception {
        discardQuestions();
        discardAppeals();

        accChanges.setDisable(true);
        disChanges.setDisable(true);
    }

    public void onMainMenu(ActionEvent actionEvent) throws Exception {
        StageChanger.simpleChangeStage("Главное меню","main_menu", mainMenu);
    }

    public void onUpdateBtn(ActionEvent actionEvent) throws Exception {
        Main.get_questions();
        Main.get_teams();
        Main.get_appeals();
        generateAdmin();
    }

    public void initialize() throws Exception {
        System.out.println("-------------------Admin-----------------------");
        accChanges.setDisable(true);
        disChanges.setDisable(true);


        generateAdmin();
        System.out.println(questions_list);
        System.out.println(aps);
        System.out.println(teams_list);
    }

}
