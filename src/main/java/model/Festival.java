package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Festival extends HistoricalEntity{
    private static int numFes = 0;
    private String location;
    private String firstTime;
    private String startingDay;
    private Map<String, Integer> relatedFigures = new HashMap<>();

    public Festival(String name, String location, String firstTime, String startingDay, String description, List<String> relatedFigures) {
        super(++numFes, name, description);
        this.location = location;
        this.firstTime = firstTime;
        this.startingDay = startingDay;
        for (String figure : relatedFigures) {
        	this.relatedFigures.put(figure,0); 
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
