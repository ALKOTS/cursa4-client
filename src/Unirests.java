import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.net.ConnectException;

public class Unirests {
    public static void delete(String toDelete) throws UnirestException {

        Unirest.delete(toDelete)
                .header("Content-type", "application/hal+json")
                .asJson();
    }

    public static void put(String where, JSONObject what) throws UnirestException {

        Unirest.put(where)
                .header("Content-type", "application/hal+json")
                .body(what)
                .asJson();
    }

    public static void post(String where, JSONObject what) throws UnirestException {
        Unirest.post(where)
                .header("Content-type", "application/hal+json")
                .body(what)
                .asJson();
    }

    public static HttpResponse<JsonNode> get(String what) throws Exception {
        HttpResponse<JsonNode> value;
        while (true){
            try{
                value=Unirest.get(what).asJson();
                return value;
            }catch (Exception e){
                System.out.println("No");
                Thread.sleep(400);
            }
        }
    }
}
