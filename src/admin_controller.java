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


import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class admin_controller {
    @FXML
    public Button accChanges, disChanges, mainMenu, updateBtn;

    @FXML
    public AnchorPane  in1, in2, in3;



    public static ArrayList<ArrayList<String>> aps=new ArrayList<>();

    public static HashMap<String, ArrayList<String>> questions_list=new HashMap<>();

    public static HashMap<String, ArrayList<String>> teams_list= new HashMap<>();

    public static HashMap<Integer,HashMap<String, String>> changed1 =new HashMap<>();
    public static HashMap<Integer,HashMap<String, String>> changed3 =new HashMap<>();

    public static ArrayList<String> question_to_delete=new ArrayList<>();

    public static ArrayList<String> teams_to_delete=new ArrayList<>();

    public static HashMap<Integer,ArrayList<String>> added_questions=new HashMap<>();
    public static HashMap<String,ArrayList<String>> added_teams=new HashMap<>();

    public static HashMap<String, HBox> v1=new HashMap<>();
    public static HashMap<String, HBox> added_v1=new HashMap<>();

    public static VBox v2 =new VBox();

    public static HashMap<String, HBox> v3=new HashMap<>();
    public static HashMap<String, HBox> added_v3=new HashMap<>();

    public String generateKey(){
        String key="";
        String alph="0123456789QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
        for(int i=0; i<8; i++){
            key+=alph.charAt(new Random().nextInt(alph.length()-1));
        }
        return key;
    }

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

    public void deleteVersion(ArrayList<String> what_to_delete, String finalI1, HashMap<String,HBox> v, AnchorPane in, HashMap<String, HBox> added_v, Button add_el){
        what_to_delete.add(finalI1);

        v.remove(finalI1);
        in.getChildren().clear();
        in.getChildren().add(new VBox(){{getChildren().addAll(v.values()); getChildren().addAll(added_v.values()); getChildren().add(add_el);}});

        accChanges.setDisable(false);
        disChanges.setDisable(false);
    }

    public void denyBtnFunction(Label lbl1, Label lbl2, String old_lbl1, String old_lbl2, VBox lblContainer, HBox blockContainer, Button editBtn, Button deleteBtn){
        lbl1.setText(old_lbl1);
        lbl2.setText(old_lbl2);
        lblContainer.getChildren().clear();
        lblContainer.getChildren().addAll(lbl1,lbl2);

        blockContainer.getChildren().set(1,editBtn);
        blockContainer.getChildren().set(2,deleteBtn);
        accChanges.setDisable(false);
        disChanges.setDisable(false);
    }

    public void acceptUpdater(ArrayList<String> list_to_delete, HashMap<String, ArrayList<String>> list, int link_index, HashMap<Integer,HashMap<String, String>> changed) throws UnirestException {
        Collections.sort(list_to_delete, Collections.reverseOrder());
        System.out.println("-----\n"+ list_to_delete+"\n------");
        for (int i=0; i<list_to_delete.size(); i++){

            Unirests.delete(list.get(list_to_delete.get(i)).get(link_index));
            list.remove(list_to_delete.get(i));
        }

        for(Map.Entry me: changed.entrySet()){
            for(Map.Entry he: changed.get(me.getKey()).entrySet()){
                try{
                    list.get(he.getKey().toString()).set(Integer.parseInt(me.getKey().toString()), changed.get(me.getKey()).get(he.getKey()));
                }catch (NullPointerException npe){
                    System.out.println("Trying to access non-existing element");
                }
            }
        }
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

            v1.put(me.getKey().toString(),blockContainer);

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
                    denyBtnFunction(qsLbl, ansLbl, old_qs, old_ans, lblContainer, blockContainer, editBtn, deleteBtn);
                });
            });

            String finalI1 = me.getKey().toString();
            deleteBtn.setOnAction(actionEvent -> {
                deleteVersion(question_to_delete, finalI1, v1, in1, added_v1, add_question);
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


            in1.getChildren().clear();
            in1.getChildren().add(new VBox(){{getChildren().addAll(v1.values());getChildren().addAll(added_v1.values()); getChildren().add(blockContainer);}});

            accBtn.setOnAction(actionEvent -> {
                if(ansTxt.getText().length()>0 && qsTxt.getText().length()>0){

                    added_v1.put(String.valueOf(added_v1.size()), blockContainer);

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
        Button add_team=new Button("+");

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

            v3.put(me.getKey().toString(),blockContainer);

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
                    if(!stateLbl.getText().equals("2")){
                        scoreLbl.setText("Счет: "+null);
                    }else{
                        scoreLbl.setText("Счет: "+0);
                    }
                    lblContainer.getChildren().addAll(nameLbl,stateLbl,scoreLbl);

                    acceptVersion(lblContainer, teams_list, changed3, finalI, blockContainer, editBtn, deleteBtn);

                });

                denBtn.setOnAction(actionEvent1 -> {
                    denyBtnFunction(nameLbl, stateLbl, old_name, old_state, lblContainer, blockContainer, editBtn, deleteBtn);

                });
            });

            deleteBtn.setOnAction(actionEvent -> {
                deleteVersion(teams_to_delete, finalI, v3, in3, added_v3, add_team);

            });
        }

        add_team.setOnAction(actionEvent -> {

            in3.getChildren().remove(in3.getChildren().size()-1);

            TextField nameTxt=new TextField() {{
                    setPromptText("Название команды");
                }};
            ObservableList<String> statesList=FXCollections.observableArrayList("0", "1", "2");

            ComboBox stateBox=new ComboBox(statesList){{
                setValue(0);
            }};

            VBox lblContainer=new VBox(nameTxt, stateBox, new Label()){{
                setMinSize(342,50);
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


            in3.getChildren().clear();
            in3.getChildren().add(new VBox(){{getChildren().addAll(v3.values());getChildren().addAll(added_v3.values()); getChildren().add(blockContainer);}});

            accBtn.setOnAction(actionEvent1 -> {

//                String key=;
//
//                key=org.apache.commons.codec.digest.DigestUtils.sha256Hex(key);

                if(nameTxt.getText().length()>0){

                    String simple_key=generateKey();
                    String key=org.apache.commons.codec.digest.DigestUtils.sha256Hex(simple_key);

                    while (teams_list.containsKey(key) || added_teams.containsKey(key)){
                        simple_key=generateKey();
                        key=org.apache.commons.codec.digest.DigestUtils.sha256Hex(simple_key);
                    }

                    System.out.println(simple_key);

                    added_v3.put(key , blockContainer);

                    added_teams.put(key ,new ArrayList<>(){{add(nameTxt.getText()); add(stateBox.getValue().toString()); add(null);}});

                    System.out.println(added_teams);

                    blockContainer.getChildren().remove(1);
                    blockContainer.getChildren().remove(1);


                    lblContainer.getChildren().clear();
                    lblContainer.getChildren().addAll(new Label(nameTxt.getText()), new Label(stateBox.getValue().toString()), new Label(){{setText("Счет: "+null);}});

                    in3.getChildren().clear();
                    in3.getChildren().add(new VBox(){{getChildren().addAll(v3.values());getChildren().addAll(added_v3.values());getChildren().add(add_team);}});

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

            denBtn.setOnAction(actionEvent1 -> {
                in3.getChildren().clear();
                in3.getChildren().add(new VBox(){{getChildren().addAll(v3.values()); getChildren().addAll(added_v3.values()); getChildren().add(add_team);}});
            });

        });

        in3.getChildren().add(new VBox(){{getChildren().addAll(v3.values());getChildren().addAll(added_v3.values());getChildren().add(add_team);}});
    }

    public void acceptQuestions() throws Exception {
        acceptUpdater(question_to_delete, questions_list, 2, changed1);

        AtomicInteger asyncChecker= new AtomicInteger();

        for(Map.Entry me:questions_list.entrySet()){

            String finalI1 = me.getKey().toString();
            new Thread(()->{
                String finalI = finalI1;
                try {
                    JSONObject toPut=new JSONObject(){{
                        put("question",questions_list.get(finalI).get(0));
                        put("answer", questions_list.get(finalI).get(1));
                    }};
                    Unirests.put(questions_list.get(finalI1).get(2),toPut);
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

                    JSONObject toPost=new JSONObject(){{
                        put("question",added_questions.get(finalI).get(0));
                        put("answer", added_questions.get(finalI).get(1));
                    }};

                    Unirests.post(Main.dbLink+"/questions", toPost);

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
                Unirests.delete(Main.aps.get(i).get(5));
            } catch (UnirestException e) {
            }
        }

        Main.aps.clear();
        //Main.teams_list.clear();
        for(ArrayList<String> item:aps) Main.aps.add((ArrayList<String>) item.clone());
//        for (Map.Entry me:teams_list.entrySet()){
//            new Thread(()->{
//                try{
//                    JSONObject toPut=new JSONObject(){{
//                        put("name",((ArrayList<String>) me.getValue()).get(0));
//                        put("accessKey",me.getKey());
//                        put("state",((ArrayList<String>) me.getValue()).get(1));
//                        put("score",((ArrayList<String>) me.getValue()).get(2));
//                        put("email", ((ArrayList<String>) me.getValue()).get(4));
//                    }};
//
//                    Unirests.put(((ArrayList<String>)me.getValue()).get(3), toPut);
//
//                }catch (Exception e){
//                }
//                asyncChecker2.getAndIncrement();
//            }).start();
//            while (asyncChecker2.get()<Main.teams_list.size()){
//                Thread.sleep(100);
//            }
//        }
//        Main.get_teams();

    }

    public void acceptTeams() throws Exception {
        acceptUpdater(teams_to_delete, teams_list, 3, changed3);

        AtomicInteger asyncChecker = new AtomicInteger();

        for (Map.Entry me : teams_list.entrySet()) {

            if(!teams_list.get(me.getKey().toString()).get(1).equals("2")){
                teams_list.get(me.getKey().toString()).set(2,null);
            }else if(teams_list.get(me.getKey().toString()).get(2)==null && teams_list.get(me.getKey().toString()).get(1).equals("2")) {
                teams_list.get(me.getKey().toString()).set(2,"0");
            }


            String finalI1 = me.getKey().toString();
            new Thread(() -> {
                String finalI = finalI1;
                try {
                    JSONObject toPut = new JSONObject() {{
                        put("name", teams_list.get(finalI).get(0));
                        put("accessKey", finalI);
                        put("state", teams_list.get(finalI).get(1));
                        put("score",teams_list.get(finalI).get(2));
                        put("email", teams_list.get(finalI).get(4));
                    }};
                    Unirests.put(teams_list.get(finalI).get(3), toPut);
                } catch (Exception e) {
                }
                asyncChecker.getAndIncrement();
            }).start();
        }

        AtomicInteger asyncChecker2 = new AtomicInteger();

        for (Map.Entry he:added_teams.entrySet()) {

            if(!added_teams.get(he.getKey().toString()).get(1).equals("2")){
                added_teams.get(he.getKey().toString()).set(2,null);
            }else if(added_teams.get(he.getKey().toString()).get(2)==null && added_teams.get(he.getKey().toString()).get(1).equals("2")) {
                added_teams.get(he.getKey().toString()).set(2,"0");
            }

            System.out.println("----");
            System.out.println(added_teams);

            String finalI = he.getKey().toString();
            new Thread(() -> {
                try {

                    JSONObject toPost = new JSONObject() {{
                        put("name", added_teams.get(finalI).get(0));
                        put("accessKey", finalI);
                        put("state", added_teams.get(finalI).get(1));
                        put("score",added_teams.get(finalI).get(2));
                        //put email
                    }};

                    Unirests.post(Main.dbLink + "/teams", toPost);

                } catch (Exception e) {
                    System.out.println("Хоба");
                }
                asyncChecker2.getAndIncrement();
            }).start();
        }

        while (asyncChecker.get() < teams_list.size() || asyncChecker2.get() < added_teams.size()) {
            Thread.sleep(100);
        }
        Main.get_teams();

        changed3.clear();

    }

    public void discardQuestions(){
        generateQuestions();
    }

    public void discardAppeals() throws Exception {
        for(ArrayList<String> item:Main.aps) aps.add((ArrayList<String>) item.clone());

        generateAdmin();
    }

    public void discardTeams(){
        generateTeams();
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
            added_teams.clear();
            teams_to_delete.clear();

            accChanges.setDisable(true);
            disChanges.setDisable(true);
        }catch (Exception nfe){ System.out.println("All clear"); }

        for(int i=0; i<Main.questions_list.size(); i++){
            questions_list.put(String.valueOf(i), (ArrayList<String>) Main.questions_list.get(i).clone());
        }
        for(ArrayList<String> item:Main.aps) aps.add((ArrayList<String>) item.clone());
        for (Map.Entry me:Main.teams_list.entrySet()){

            try {
                teams_list.put(me.getKey().toString(), new ArrayList<>(){{
                    add(((HashMap<String,String>) me.getValue()).get("name"));
                    add(((HashMap<String,String>) me.getValue()).get("state"));
                    add(((HashMap<String,String>) me.getValue()).get("score"));
                    add(((HashMap<String,String>) me.getValue()).get("link"));
                    add(((HashMap<String,String>) me.getValue()).get("email"));
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
        acceptTeams();


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
        discardTeams();

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
