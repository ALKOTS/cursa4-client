//import com.mashape.unirest.http.Unirest;
//import com.mashape.unirest.http.exceptions.UnirestException;
//import javafx.collections.ObservableList;
//import javafx.collections.FXCollections;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.scene.layout.*;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class ыы {
//        public void initialize(){
//            Button accBtn=new Button();
//            VBox lblContainer=new VBox();
//            int finalI=i;
//            HashMap<Integer,HashMap<Integer,String>> changed=new HashMap<>();
//            HashMap<Integer, ArrayList<String>> requiredList=new HashMap<>();
//
//
//
//
//                for(int j=0; j<lblContainer.getChildren().size(); j++){
//
//                    if(!((Label) lblContainer.getChildren().get(j)).getText().equals(requiredList.get(finalI).get(j))){
//                        lblContainer.getChildren().get(j).setStyle("-fx-background-color: #00ff00; ");
//                        int finalJ = j;
//                        try{
//                            changed.get(j).put(finalI, ((Label) lblContainer.getChildren().get(finalJ)).getText());
//                        }catch (NullPointerException npe){
//                            changed.put(j, new HashMap<>(){{put(finalI, ((Label) lblContainer.getChildren().get(finalJ)).getText());}});
//                        }
//
//                    }else {
//                        lblContainer.getChildren().get(j).setStyle(null);
//                        changed.get(j).remove(finalI);
//                    }
//                }
//
//                blockContainer.getChildren().set(1,editBtn);
//                blockContainer.getChildren().set(2,deleteBtn);
//                accChanges.setDisable(false);
//                disChanges.setDisable(false);


//            denyBtn.setOnAction(actionEvent2 -> {
//                qsLbl.setText(old_qs);
//                ansLbl.setText(old_ans);
//                lblContainer.getChildren().clear();
//                lblContainer.getChildren().addAll(qsLbl,ansLbl);
//
//                blockContainer.getChildren().set(1,editBtn);
//                blockContainer.getChildren().set(2,deleteBtn);
//                accChanges.setDisable(false);
//                disChanges.setDisable(false);
//
//            });
//        });
//
//    int finalI1 = Integer.parseInt(me.getKey().toString());
//        deleteBtn.setOnAction(actionEvent -> {
//            question_to_delete.add(finalI1);
//
//            v1.remove(finalI1);
//            in1.getChildren().clear();
//            in1.getChildren().add(new VBox(){{getChildren().addAll(v1.values()); getChildren().addAll(added_v1.values()); getChildren().add(add_question);}});
//
//            accChanges.setDisable(false);
//            disChanges.setDisable(false);
//        });

//}
