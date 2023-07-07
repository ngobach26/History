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
