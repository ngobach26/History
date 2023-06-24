package collection;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import helper.JsonIO;
import model.Era;


public class EraCollection {
    private static Type token = new TypeToken<ArrayList<Era>>() {}.getType();
    private static EntityCollection<Era> collection = new EntityCollection<>();
    private static String path = "src/main/resources/json/Eras.json";

    public static void writeJson(){

    }

    public static void loadJson(){
        ArrayList<Era> data = (ArrayList<Era>) new JsonIO<Era>(token).loadJson(path);
        collection.setData(data);
    }

    public static EntityCollection<Era> getCollection() {
        return collection;
    }

}
