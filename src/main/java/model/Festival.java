package model;
import java.util.ArrayList;
import java.util.HashMap;

public class Festival extends HistoricalEntity{
    private static int numFes = 0;
    private int id;
    private String name;
    private String location;
    private String firstTime;
    private String startingDay;
    private HashMap<String, Integer> relatedFigures = new HashMap<>();


    public Festival(String name, String location, String firstTime, String startingDay, ArrayList<String> relatedFigures) {
        this.id = ++numFes;
        this.name = name;
        this.location = location;
        this.firstTime = firstTime;
        this.startingDay = startingDay;
        for (String figure : relatedFigures) {
        	this.relatedFigures.put(figure,0); 
        }
    }
}
