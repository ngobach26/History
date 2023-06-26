package collection;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.reflect.TypeToken;

import helper.JsonIO;
import model.Figure;

public class FigureData {
    private static final Type TOKEN = new TypeToken<ArrayList<Figure>>() {}.getType();
    public static EntityCollection<Figure> data = new EntityCollection<>();
    private static final String SOURCE_PATH = "src/main/resources/json/Figures.json";

    public static void writeJson(){

    }

    public static void loadJson(){
        ArrayList<Figure> data = (ArrayList<Figure>) new JsonIO<Figure>(TOKEN).loadJson(SOURCE_PATH);
        FigureData.data.setData(data);
    }
}
