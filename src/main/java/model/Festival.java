package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Festival extends HistoricalEntity {
    private static int numFes = 0;
    private String location;
    private String firstTime;
    private String startingDay;
    private Map<String, Integer> relatedFigures = new HashMap<>();
    private Map<String, Integer> relatedRelics = new HashMap<>();

    public Festival(String name, String location, String firstTime, String startingDay, List<String> relatedFigures) {
        super(++numFes, name, "Không rõ");
        this.location = location;
        this.firstTime = firstTime;
        this.startingDay = startingDay;
        for (String figure : relatedFigures) {
            this.relatedFigures.put(figure, 0);
        }
    }

    public Festival(String name, String location, String firstTime, String startingDay, String description,
            List<String> relatedFigures) {
        super(++numFes, name, description);
        this.location = location;
        this.firstTime = firstTime;
        this.startingDay = startingDay;
        for (String figure : relatedFigures) {
            this.relatedFigures.put(figure, 0);
        }
    }

    public String getLocation() {
        return location;
    }

    public String getFirstTime() {
        return firstTime;
    }

    public String getStartingDay() {
        return startingDay;
    }

    public Map<String, Integer> getRelatedFigures() {
        return relatedFigures;
    }

    public Map<String, Integer> getRelatedRelics() {
        return relatedRelics;
    }
}
