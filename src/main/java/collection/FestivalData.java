package collection;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import helper.JsonIO;
import model.Festival;


public class FestivalData {
    public static final Type TOKEN = new TypeToken<ArrayList<Festival>>() {}.getType();
    private static EntityCollection<Festival> festivalCollection = new EntityCollection<>();
    public static final String SOURCE_PATH = "src/main/resources/json/Festivals.json";
    private static int numOfFestival;

    public static void writeJson(){

    }

    public static void loadJson(){
        ArrayList<Festival> data = (ArrayList<Festival>) new JsonIO<Festival>(TOKEN).loadJson(SOURCE_PATH);
        FestivalData.festivalCollection.setData(data);
        numOfFestival = data.size();
    }

    public static int getNumOfFestival() {
        return numOfFestival;
    }

    public static EntityCollection<Festival> getFestivalCollection() {
        return festivalCollection;
    }
}
