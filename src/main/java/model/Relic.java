package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Relic extends HistoricalEntity{
    private static int numRelic = 0;
    private String location;
    private String category;
    private String approvedYear;
    private Map<String, Integer> relatedFigures = new HashMap<>();

    public Relic(String name, String location, String category, String approvedYear, String description, List<String> relatedFigures) {
        super(++numRelic, name, description);
        this.location = location;
        this.category = category;
        this.approvedYear = approvedYear;
        for (String figure : relatedFigures) {
        	this.relatedFigures.put(figure, 0);
        }
    }

	public Map<String, Integer> getRelatedFigures() {
		return relatedFigures;
	}

	public void setRelatedFigures(Map<String, Integer> relatedFigures) {
		this.relatedFigures = relatedFigures;
	}
	
}
