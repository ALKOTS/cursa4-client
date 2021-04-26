import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import org.json.JSONObject;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class admin_controller {
    @FXML
    public Button accChanges, disChanges, mainMenu;

    @FXML
    public AnchorPane in1, in2;

    @FXML
    public VBox in3;


    public static ArrayList<Button> btnList =new ArrayList<>();

    public static ArrayList<ArrayList<String>> aps=new ArrayList<>();

    public static HashMap<Integer, ArrayList<String>> questions_list=new HashMap<>();

    public static HashMap<String, HashMap<String, String>> teams_list= new HashMap<>();

    public static ArrayList<BorderPane> old_v1 =new ArrayList<>();

    public static HashMap<Integer,HashMap<Integer, String>> changed=new HashMap<>();

    public static ArrayList<Integer> question_to_delete=new ArrayList<>();

    public static HashMap<Integer,ArrayList<String>> added_questions=new HashMap<>();

    public static HashMap<Integer, BorderPane> v1=new HashMap<>();
    public static HashMap<Integer, BorderPane> added_v1=new HashMap<>();
    public static VBox v2 =new VBox();


    public void generateQuestions(){
        Button add_question=new Button("+");

        //Dynamic

        for(Map.Entry me:questions_list.entrySet()){

            Label qsLbl=new Label(questions_list.get(Integer.parseInt(me.getKey().toString())).get(0));
            Label ansLbl=new Label(questions_list.get(Integer.parseInt(me.getKey().toString())).get(1));

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
            v1.put(Integer.parseInt(me.getKey().toString()),allContainer);

            //button functionality

            int finalI = Integer.parseInt(me.getKey().toString());
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

                    for(int j=0; j<2; j++){
                        if(!((Label) lblContainer.getChildren().get(j)).getText().equals(questions_list.get(finalI).get(j))){
                            lblContainer.getChildren().get(j).setStyle("-fx-background-color: #00ff00; ");
                            int finalJ = j;
                            try{
                                changed.get(j).put(finalI, ((Label) lblContainer.getChildren().get(finalJ)).getText());
                            }catch (NullPointerException npe){
                                changed.put(j, new HashMap<>(){{put(finalI, ((Label) lblContainer.getChildren().get(finalJ)).getText());}});
                            }

                        }else {
                            lblContainer.getChildren().get(j).setStyle(null);
                            changed.get(j).remove(finalI);
                        }
                    }

                    blockContainer.getChildren().set(1,editBtn);
                    blockContainer.getChildren().set(2,deleteBtn);
                    accChanges.setDisable(false);
                    disChanges.setDisable(false);

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

            int finalI1 = Integer.parseInt(me.getKey().toString());
            deleteBtn.setOnAction(actionEvent -> {
                question_to_delete.add(finalI1);

                v1.remove(finalI1);
                in1.getChildren().clear();
                in1.getChildren().add(new VBox(){{getChildren().addAll(v1.values()); getChildren().addAll(added_v1.values()); getChildren().add(add_question);}});

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

                btnList.add(acceptBtn);
                btnList.add(denyBtn);
                accChanges.setDisable(false);
                disChanges.setDisable(false);

            });

            denyBtn.setOnAction(actionEvent -> {

                aps.get(index).set(4,"N");
                denyBtn.setStyle("-fx-background-color: #ff0000; ");
                acceptBtn.setDisable(true);

                btnList.add(acceptBtn);
                btnList.add(denyBtn);
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
            Label nameLbl=new Label("Название: "+teams_list.get(me.getKey()).get("name"));
            Label stateLbl=new Label("Состояние: "+teams_list.get(me.getKey()).get("state"));
            Label scoreLbl=new Label("Счет: "+teams_list.get(me.getKey()).get("score"));

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

            editBtn.setOnAction(actionEvent -> {
                lblContainer.getChildren().clear();
                ObservableList<String> statesList=FXCollections.observableArrayList("0", "1", "2");
                lblContainer.getChildren().addAll(new TextField(teams_list.get(me.getKey()).get("name")){{setPromptText("Название");}},new ComboBox<>(statesList){{setValue(teams_list.get(me.getKey()).get("state"));}},scoreLbl);

                Button accBtn=new Button("A"){{
                    setPrefSize(50,50);
                }};
                Button denBtn=new Button("D"){{
                    setPrefSize(50,50);
                }};

                blockContainer.getChildren().set(1,accBtn);
                blockContainer.getChildren().set(2,denBtn);

                accBtn.setOnAction(actionEvent1 -> {
                    nameLbl.setText("Название: "+((TextField) lblContainer.getChildren().get(0)).getText());
                    stateLbl.setText("Состояние: "+((ComboBox) lblContainer.getChildren().get(1)).getValue().toString());
                    lblContainer.getChildren().clear();
                    lblContainer.getChildren().addAll(nameLbl,stateLbl,scoreLbl);

                });

                denBtn.setOnAction(actionEvent1 -> {

                });
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

        System.out.println(changed);
        for(Map.Entry me:changed.entrySet()){
            for(Map.Entry he:changed.get(me.getKey()).entrySet()){
                try{
                    questions_list.get(he.getKey()).set(Integer.parseInt(me.getKey().toString()),changed.get(me.getKey()).get(he.getKey()));
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
                    Unirest.put(questions_list.get(finalI1).get(2))
                            .header("Content-type", "application/hal+json")
                            .body(new JSONObject(){{
                                put("question",questions_list.get(finalI).get(0));
                                put("answer", questions_list.get(finalI).get(1));
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

        old_v1.clear();
        for (int i=0; i<v1.size(); i++){
            old_v1.add(v1.get(i));
        }

        changed.clear();

    }

    public void acceptAppeals() throws Exception{
        ArrayList<Integer> toRemove=new ArrayList<>();
        for (int i=0; i<aps.size(); i++){
            switch (aps.get(i).get(4)) {
                case "Y":
                    if(teams_list.get(aps.get(i).get(3)).get("score")!=null && Integer.parseInt(teams_list.get(aps.get(i).get(3)).get("state"))>1){
                        teams_list.get(aps.get(i).get(3)).put("score", String.valueOf(Integer.parseInt(teams_list.get(aps.get(i).get(3)).get("score"))+1));
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

        AtomicInteger asyncChecker= new AtomicInteger();

        for(int i=Main.aps.size()-1; i>-1; i--){
            int finalI = i;
            new Thread(()->{
                try {
                    Unirest.delete(Main.aps.get(finalI).get(5))
                            .header("Content-type", "application/hal+json")
                            .asJson();
                    System.out.println("YES");
                } catch (UnirestException e) {
                }
                asyncChecker.getAndIncrement();
            }).start();
        }

        while (asyncChecker.get() <Main.aps.size()){
            Thread.sleep(100);
        }

        Main.aps.clear();
        Main.teams_list.clear();
        for(ArrayList<String> item:aps) Main.aps.add((ArrayList<String>) item.clone());
        for (Map.Entry me:teams_list.entrySet()){
            try{
                Unirest.put(teams_list.get(me.getKey()).get("link"))
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
            }
        }
    }

    public void acceptTeams(){

    }

    public void discardQuestions(){
        v1.clear();
        in1.getChildren().clear();

        added_v1.clear();
        added_questions.clear();

        changed.clear();
        for(int j=0; j<old_v1.size();j++){
            v1.put(j,old_v1.get(j));
        }

        generateQuestions();
    }

    public void discardAppeals(){
        for (Button button : btnList) {
            button.setDisable(false);
            button.setStyle(null);
        }
        btnList.clear();
        for(ArrayList<String> item:Main.aps) aps.add((ArrayList<String>) item.clone());
    }

    public void discardTeams(){

    }

    public void generateAdmin() throws Exception {
        try{
            in1.getChildren().clear();
            v1.clear();
            old_v1.clear();
            changed.clear();
            questions_list.clear();
            question_to_delete.clear();
            added_questions.clear();
            added_v1.clear();


            in2.getChildren().clear();
            aps.clear();
            teams_list.clear();
            btnList.clear();
            v2.getChildren().clear();

            in3.getChildren().clear();
        }catch (Exception nfe){ System.out.println("All clear"); }

        for(int i=0; i<Main.questions_list.size(); i++){
            questions_list.put(i, (ArrayList<String>) Main.questions_list.get(i).clone());
        }
        for(ArrayList<String> item:Main.aps) aps.add((ArrayList<String>) item.clone());
        for (Map.Entry me:Main.teams_list.entrySet()){
            try{
                teams_list.put(String.valueOf(me.getKey()), (HashMap<String, String>) me.getValue());
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

    public void onDiscardChanges(ActionEvent actionEvent) {
        discardQuestions();
        discardAppeals();

        accChanges.setDisable(true);
        disChanges.setDisable(true);
    }

    public void onMainMenu(ActionEvent actionEvent) throws Exception {
        StageChanger.simpleChangeStage("Главное меню","main_menu", mainMenu);
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
