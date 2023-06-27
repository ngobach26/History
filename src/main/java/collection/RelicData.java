package collection;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import helper.JsonIO;
import model.Relic;


public class RelicData {
    public static final Type TOKEN = new TypeToken<ArrayList<Relic>>() {}.getType();
    public static EntityCollection<Relic> data = new EntityCollection<>();
    public static final String SOURCE_PATH = "src/main/resources/json/Relics.json";

    public static void writeJson(){

    }

    public static void loadJson(){
        ArrayList<Relic> data = (ArrayList<Relic>) new JsonIO<Relic>(TOKEN).loadJson(SOURCE_PATH);
        RelicData.data.setData(data);
    }
}
