package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event extends HistoricalEntity{
	 private static int numEvents = 0;
	 private String time;
	 private String location;
	 private String result;
	 private Map<String, Integer> relatedFigures = new HashMap<>();
	 
	 public Event(String eventName, String time, String location, String result, List<String> relatedFigures, String description) {
		 super(++numEvents, eventName, description);
		 this.time = time;
		 this.location = location;
		 this.result = result;
		 for (String figure : relatedFigures) {
			 this.relatedFigures.put(figure, 0);
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Map<String, Integer> getRelatedFigures() {
		return relatedFigures;
	}

	public void setRelatedFigures(Map<String, Integer> relatedFigures) {
		this.relatedFigures = relatedFigures;
	}
		
	 
}
