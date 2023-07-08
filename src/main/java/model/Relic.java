package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import helper.StringHelper;

public class Relic extends HistoricalEntity{
    private static int numRelics = 0;
    private String location;
    private String category;
    private String approvedYear;
    private Map<String, Integer> relatedFestivals = new HashMap<>();
    private Map<String, Integer> relatedFigures = new HashMap<>();

    public Relic(String relicName, String location, String category, String approvedYear, String description, List<String> relatedFigures) {
        super(++numRelics, relicName, description);
        this.location = location;
        this.category = category;
        this.approvedYear = approvedYear;
        for (String figure : relatedFigures) {
        	this.relatedFigures.put(figure, 0);
        }
    }

	public String getLocation() {
		return location;
	}

	public String getCategory() {
		return category;
	}

	public String getApprovedYear() {
		return approvedYear;
	}

	public Map<String, Integer> getRelatedFestivals() {
		return relatedFestivals;
	}

	public Map<String, Integer> getRelatedFigures() {
		return relatedFigures;
	}

	public void setRelatedFigures(Map<String, Integer> relatedFigures) {
		this.relatedFigures = relatedFigures;
	}
	
	public void addRelatedFestivals(String newFestival, int id) {
		if (!StringHelper.containString(relatedFestivals.keySet(), newFestival)) {
			relatedFestivals.put(newFestival, id);
		}		
	}

}

