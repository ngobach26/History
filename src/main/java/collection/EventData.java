package collection;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import helper.JsonIO;
import model.Event;


public class EventData {
    public static final Type TOKEN = new TypeToken<ArrayList<Event>>() {}.getType();
    public static EntityCollection<Event> data = new EntityCollection<>();
    public static final String SOURCE_PATH = "src/main/resources/json/Events.json";

    public static void writeJson(){

    }

    public static void loadJson(){
        ArrayList<Event> data = (ArrayList<Event>) new JsonIO<Event>(TOKEN).loadJson(SOURCE_PATH);
        EventData.data.setData(data);
    }
}
