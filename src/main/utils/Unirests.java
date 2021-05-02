package main.utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONObject;

import java.net.ConnectException;

public class Unirests {

    /**
     * Запрос на удаление информации
     *
     * @param toDelete id of the field to be deleted
     * @throws UnirestException
     */

    public static void delete(String toDelete) throws UnirestException {

        Unirest.delete(toDelete)
                .header("Content-type", "application/hal+json")
                .asJson();
    }

    /**
     * Запрос на изменение информации
     *
     * @param where field id
     * @param what new info
     * @throws UnirestException
     */

    public static void put(String where, JSONObject what) throws UnirestException {

        Unirest.put(where)
                .header("Content-type", "application/hal+json")
                .body(what)
                .asJson();
    }

    /**
     * Запрос на запись
     *
     * @param where table
     * @param what info
     * @throws UnirestException
     */

    public static void post(String where, JSONObject what) throws UnirestException {
        Unirest.post(where)
                .header("Content-type", "application/hal+json")
                .body(what)
                .asJson();
    }

    /**
     * Запрос на получение информации
     *
     * @param what table
     * @return info
     * @throws Exception
     */

    public static HttpResponse<JsonNode> get(String what) throws Exception {
        HttpResponse<JsonNode> value;
        while (true){
            try{
                value=Unirest.get(what).asJson();
                return value;
            }catch (Exception e){
                new Alert(Alert.AlertType.ERROR){{
                    setTitle("Error Dialog");
                    setHeaderText("No connection");
                    setGraphic(new ImageView( new Image(getClass().getResource("/main/resources/video.gif").toExternalForm())));
                    showAndWait();
                }};
            }
        }
    }
}
