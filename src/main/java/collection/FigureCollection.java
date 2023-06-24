package collection;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import helper.JsonIO;
import model.Figure;

public class FigureCollection {
    private static Type token = new TypeToken<ArrayList<Figure>>() {}.getType();
    public static EntityCollection<Figure> collection = new EntityCollection<>();
    private static String path = "src/main/resources/json/Figures.json";

    public static void writeJson(){

    }

    public static void loadJson(){
        ArrayList<Figure> data = (ArrayList<Figure>) new JsonIO<Figure>(token).loadJson(path);
        collection.setData(data);
    }
}
