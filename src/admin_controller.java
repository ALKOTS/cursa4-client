import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
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
    public AnchorPane in1,in2;

//    @FXML
//    public VBox presentQs, addedQs;

    public static ArrayList<Button> btnList =new ArrayList<>();

    //public static ArrayList<Button> constBtnList=new ArrayList<>();

    public static ArrayList<ArrayList<String>> aps=new ArrayList<>();

    public static HashMap<Integer, ArrayList<String>> questions_list=new HashMap<>();

    public static HashMap<String, HashMap<String, String>> teams_list= new HashMap<>();

    public static ArrayList<BorderPane> old_v1 =new ArrayList<>();
    public static ArrayList<BorderPane> allContainerContainer2 =new ArrayList<>();
    public static HashMap<Integer, String> changedQ =new HashMap<>();
    public static HashMap<Integer, String> changedA =new HashMap<>();

    public static ArrayList<Integer> question_to_delete=new ArrayList<>();

    public static HashMap<Integer,ArrayList<String>> added_questions=new HashMap<>();

    public static HashMap<Integer, BorderPane> v1=new HashMap<>();
    public static VBox v2 =new VBox();


    public void generateQuestions(){
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

                    //System.out.println(changedQ);
                    //System.out.println(changedA);
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

            int finalI1 = Integer.parseInt(me.getKey().toString());
            deleteBtn.setOnAction(actionEvent -> {
                question_to_delete.add(finalI1);

                v1.remove(finalI1);
                in1.getChildren().clear();
                in1.getChildren().add(new VBox(){{getChildren().addAll(v1.values());}});
                //System.out.println(v1.get(finalI1));

                accChanges.setDisable(false);
                disChanges.setDisable(false);
            });
        }
        //Static
        Button add_question=new Button("+");

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


            accBtn.setOnAction(actionEvent -> {
                if(ansTxt.getText().length()>0 && qsTxt.getText().length()>0){
                    allContainer.getChildren().removeAll(accBtn,denBtn);
                    added_questions.put(added_questions.size(),new ArrayList<>(){{add(qsTxt.getText()); add(ansTxt.getText());}});
                    
                }
                else {
//                    new Alert()
                }
            });

            denBtn.setOnAction(actionEvent -> {
                in1.getChildren().clear();
                in1.getChildren().add(new VBox(){{getChildren().addAll(v1.values());getChildren().add(add_question);}});
            });

            in1.getChildren().clear();
            in1.getChildren().add(new VBox(){{getChildren().addAll(v1.values());getChildren().add(allContainer);}});
        });

        in1.getChildren().add(new VBox(){{getChildren().addAll(v1.values());getChildren().add(add_question);}});
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

    public void acceptQuestions() throws Exception {

        Collections.sort(question_to_delete, Collections.reverseOrder());
        //System.out.println(question_to_delete);

        for (int i=0; i<question_to_delete.size(); i++){
            //System.out.println(questions_list.get(question_to_delete.get(i)).get(2));
            Unirest.delete(questions_list.get(question_to_delete.get(i)).get(2))
                    .header("Content-type", "application/hal+json")
                    .asJson();
            questions_list.remove(question_to_delete.get(i));
        }
//        System.out.println(questions_list);
//        System.out.println(v1);

        for (Map.Entry me:v1.entrySet()){
            try{
                HBox bb=(HBox) v1.get(Integer.parseInt(me.getKey().toString())).getLeft();
                VBox vv=(VBox) bb.getChildren().get(0);
                Label llQ=(Label) vv.getChildren().get(0);
                Label llA=(Label)  vv.getChildren().get(1);
                questions_list.get(Integer.parseInt(me.getKey().toString())).set(0,llQ.getText());
                questions_list.get(Integer.parseInt(me.getKey().toString())).set(1,llA.getText());

                //System.out.println(questions_list.get(Integer.parseInt(me.getKey().toString())));

                llQ.setStyle(null);
                llA.setStyle(null);
            }catch (NullPointerException npe){
                System.out.println("Tried to operate on a deleted element");
            }
        }


        AtomicInteger asyncChecker= new AtomicInteger();

        //System.out.println(questions_list);
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

        //System.out.println(Main.questions_list);

        old_v1.clear();
        for (int i=0; i<v1.size(); i++){
            old_v1.add(v1.get(i));
        }

        changedA.clear();
        changedQ.clear();

        //добавить повторную отрисовку

        generateAdmin();
    }

    public void acceptAppeals() throws Exception{
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

    public void discardQuestions(){
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
        for(int j=0; j<old_v1.size();j++){
            v1.put(j,old_v1.get(j));
        }

        in1.getChildren().add(new VBox(){{getChildren().addAll(v1.values());}});
    }

    public void discardAppeals(){
        for (Button button : btnList) {
            button.setDisable(false);
            button.setStyle(null);
        }
        btnList.clear();
        for(ArrayList<String> item:Main.aps) aps.add((ArrayList<String>) item.clone());
    }

    public void generateAdmin() throws Exception {
        try{
            in1.getChildren().clear();
            v1.clear();
            old_v1.clear();
            changedQ.clear();
            changedA.clear();
            questions_list.clear();
            question_to_delete.clear();
            added_questions.clear();

            in2.getChildren().clear();
            aps.clear();
            teams_list.clear();
            btnList.clear();
            v2.getChildren().clear();
            allContainerContainer2.clear();
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
    }


    public void onAcceptChanges(ActionEvent actionEvent) throws Exception {
        acceptQuestions();
        acceptAppeals();


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
    }

}
