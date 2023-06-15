package collection;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import helper.JsonIO;
import model.Festival;


public class FestivalCollection {
    public static Type token = new TypeToken<ArrayList<Festival>>() {}.getType();
    public static EntityCollection<Festival> collection = new EntityCollection<>();
    public static String path = "src/main/resources/json/Festivals.json";

    public static void writeJson(){

    }

    public static void loadJson(){
        ArrayList<Festival> data = (ArrayList<Festival>) new JsonIO<Festival>(token).loadJson(path);
        collection.setData(data);
    }
}
