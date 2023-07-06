package collection;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import helper.JsonIO;
import model.Era;


public class EraData {
    private static final Type TOKEN = new TypeToken<ArrayList<Era>>() {}.getType();
    private static EntityCollection<Era> eraCollection = new EntityCollection<>();
    private static final String SOURCE_PATH = "src/main/resources/json/Eras.json";
    private static int numOfEra;
    public static void writeJson(){

    }

    public static void loadJson(){
        ArrayList<Era> data = (ArrayList<Era>) new JsonIO<Era>(TOKEN).loadJson(SOURCE_PATH);
        EraData.eraCollection.setData(data);
        numOfEra = data.size();
    }

    public static EntityCollection<Era> getEraCollection() {
        return eraCollection;
    }

    public static int getNumOfEra() {
        return numOfEra;
    }
}
