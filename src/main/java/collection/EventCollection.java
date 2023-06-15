package collection;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import helper.JsonIO;
import model.Event;


public class EventCollection {
    public static Type token = new TypeToken<ArrayList<Event>>() {}.getType();
    public static EntityCollection<Event> collection = new EntityCollection<>();
    public static String path = "src/main/resources/json/Events.json";

    public static void writeJson(){

    }

    public static void loadJson(){
        ArrayList<Event> data = (ArrayList<Event>) new JsonIO<Event>(token).loadJson(path);
        collection.setData(data);
    }
}
