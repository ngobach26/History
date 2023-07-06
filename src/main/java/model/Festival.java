package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Festival extends HistoricalEntity{
    private static int numFes = 0;
    private String location;
    private String firstTime;
    private String startingDay;
    private Map<String, Integer> relatedRelics = new HashMap<>();
    private Map<String, Integer> relatedFigures = new HashMap<>();

    public Festival(String name, String location, String firstTime, String startingDay, String description, List<String> relatedFigures, List<String> relatedRelics) {
        super(++numFes, name, description);
        this.location = location;
        this.firstTime = firstTime;
        this.startingDay = startingDay;
        for (String figure : relatedFigures) {
        	this.relatedFigures.put(figure,0); 
        }
        for (String relic : relatedRelics) {
        	this.relatedRelics.put(relic, 0);
        }
    }
    
    public String getLocation() {
		return location;
	}
    
    public Map<String, Integer> getRelatedRelics() {
		return relatedRelics;
	}

	public Map<String, Integer> getRelatedFigures() {
		return relatedFigures;
	}	

	public void setRelatedRelics(Map<String, Integer> relatedRelics) {
		this.relatedRelics = relatedRelics;
	}

	public void setRelatedFigures(Map<String, Integer> relatedFigures) {
		this.relatedFigures = relatedFigures;
	}

	public void addRelatedFigures(String newFigure, int id) {
		boolean isFound = false;
		for (String relatedFigure : relatedFigures.keySet()) {
			if (relatedFigure.equalsIgnoreCase(newFigure)) {
				isFound = true;
				break;
			}
		}
		if (!isFound) {
			relatedFigures.put(newFigure, id);
		}		
	}
	
	public void addRelatedRelics(String newRelic, int id) {
		boolean isFound = false;
		for (String relatedRelic : relatedRelics.keySet()) {
			if (relatedRelic.equalsIgnoreCase(newRelic)) {
				isFound = true;
				break;
			}
		}
		if (!isFound) {
			relatedRelics.put(newRelic, id);
		}		
	}

	public String getRelatedFigureString() {
		StringBuilder sb = new StringBuilder();
		sb.append("");
		if (relatedFigures.isEmpty()) {
			return "không rõ";
		}
		for (String figure : relatedFigures.keySet()) {
			int id = relatedFigures.get(figure);
			sb.append(figure).append(" (").append(id).append("), ");
		}
		sb.replace(sb.length() - 2, sb.length(), "");
		return sb.toString();
	}
}
