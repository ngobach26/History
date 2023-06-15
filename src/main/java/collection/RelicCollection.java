package collection;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import helper.JsonIO;
import model.Relic;


public class RelicCollection {
    public static Type token = new TypeToken<ArrayList<Relic>>() {}.getType();
    public static EntityCollection<Relic> collection = new EntityCollection<>();
    public static String path = "src/main/resources/json/Relics.json";

    public static void writeJson(){

    }

    public static void loadJson(){
        ArrayList<Relic> data = (ArrayList<Relic>) new JsonIO<Relic>(token).loadJson(path);
        collection.setData(data);
    }
}
