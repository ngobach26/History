package collection;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import helper.JsonIO;
import model.Relic;


public class RelicData {
    public static final Type TOKEN = new TypeToken<ArrayList<Relic>>() {}.getType();
    private static EntityCollection<Relic> relicCollection = new EntityCollection<>();
    private static int numOfRelic;
    public static final String SOURCE_PATH = "src/main/resources/json/Relics.json";

    public static void writeJson(){

    }

    public static void loadJson(){
        ArrayList<Relic> data = (ArrayList<Relic>) new JsonIO<Relic>(TOKEN).loadJson(SOURCE_PATH);
        RelicData.relicCollection.setData(data);
        numOfRelic = data.size();
    }

    public static int getNumOfRelic() {
        return numOfRelic;
    }

    public static EntityCollection<Relic> getRelicCollection() {
        return relicCollection;
    }
}
