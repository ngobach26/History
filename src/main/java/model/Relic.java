package model;

import java.util.HashMap;
import java.util.Map;

public class Relic extends HistoricalEntity{
    private static int numRelic = 0;
    private String location;
    private String category;
    private String approvedYear;
    private Map<String, Integer> relatedFigures = new HashMap<>();

    public Relic(String name, String location, String category, String approvedYear, String description) {
        super(++numRelic, name, description);
        this.location = location;
        this.category = category;
        this.approvedYear = approvedYear;
    }
}
