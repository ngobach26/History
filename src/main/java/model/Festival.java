package model;
import java.util.ArrayList;
import java.util.HashMap;

public class Festival extends HistoricalEntity{
    private static int numFes = 0;
    private String location;
    private String firstTime;
    private String startingDay;
    private HashMap<String, Integer> relatedFigures = new HashMap<>();


    public Festival(String name, String location, String firstTime, String startingDay, ArrayList<String> relatedFigures) {
        super(++numFes, name);
        this.location = location;
        this.firstTime = firstTime;
        this.startingDay = startingDay;
        for (String figure : relatedFigures) {
        	this.relatedFigures.put(figure,0); 
        }
    }

    public String getLocation() {
        return location;
    }


    public void setLocation(String location) {
        this.location = location;
    }


    public String getFirstTime() {
        return firstTime;
    }


    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }


    public String getStartingDay() {
        return startingDay;
    }


    public void setStartingDay(String startingDay) {
        this.startingDay = startingDay;
    }


    public HashMap<String, Integer> getRelatedFigures() {
        return relatedFigures;
    }


    public void setRelatedFigures(HashMap<String, Integer> relatedFigures) {
        this.relatedFigures = relatedFigures;
    }
}
