package model;

import java.util.ArrayList;

import collection.FigureCollection;
import helper.JsonIO;

public class Test {
    public static void main(String[] args) {
        FigureCollection.loadJson();
        ArrayList<TestFigure> test = new ArrayList<>();
        for (Figure f : FigureCollection.collection.getData()) {
            ArrayList<String> otherName = new ArrayList<>();
            for (int i = 1; i < f.getNames().size(); i++) {
                otherName.add(f.getNames().get(i));
            }
            test.add(new TestFigure(f.getId(), f.getNames().get(0), otherName, f.getBornYear(), f.getDiedYear(),
                    f.getEras(), f.getLocation(), f.getRole(), f.getSpouses(), f.getMother(), f.getFather(),
                    f.getChildren(), f.getDescription()));

        }
        JsonIO<TestFigure> json = new JsonIO<>(FigureCollection.token);
        json.writeJson(test, "src/main/resources/json/Figures.json");
    }
}
