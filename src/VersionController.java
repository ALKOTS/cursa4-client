//import javafx.scene.control.Label;
//
//import java.util.HashMap;
//
//public class VersionController {
//    public static void versionAccept(){
//        for(int j=0; j<lblContainer.getChildren().size(); j++){
//
//            if(!((Label) lblContainer.getChildren().get(j)).getText().equals(requiredList.get(finalI).get(j))){
//                lblContainer.getChildren().get(j).setStyle("-fx-background-color: #00ff00; ");
//                int finalJ = j;
//                try{
//                    changed.get(j).put(finalI, ((Label) lblContainer.getChildren().get(finalJ)).getText());
//                }catch (NullPointerException npe){
//                    changed.put(j, new HashMap<>(){{put(finalI, ((Label) lblContainer.getChildren().get(finalJ)).getText());}});
//                }
//
//            }else {
//                lblContainer.getChildren().get(j).setStyle(null);
//                changed.get(j).remove(finalI);
//            }
//        }
//
//        blockContainer.getChildren().set(1,editBtn);
//        blockContainer.getChildren().set(2,deleteBtn);
//        accChanges.setDisable(false);
//        disChanges.setDisable(false);
//
//    }
//}
