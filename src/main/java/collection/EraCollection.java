package collection;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import helper.JsonIO;
import model.Era;


public class EraCollection {
    public static Type token = new TypeToken<ArrayList<Era>>() {}.getType();
    public static EntityCollection<Era> collection = new EntityCollection<>();
    public static String path = "src/main/resources/json/Eras.json";

    public static void writeJson(){

    }

    public static void loadJson(){
        ArrayList<Era> data = new JsonIO<Era>(token).loadJson(path);
        collection.setData(data);
    }
}
