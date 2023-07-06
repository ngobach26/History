package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Event extends HistoricalEntity{
	 private static int numEvents = 0;
	 private String startYear;
	 private String endYear;
	 private String location;
	 private String result;
	 private Map<String, Integer> eras = new HashMap<>();
	 private Map<String, Integer> relatedFigures = new HashMap<>();
	 
	 public Event(String eventName, String startYear, String endYear, String location, String result, List<String> relatedFigures, String description) {
		 super(++numEvents, eventName, description);
		 this.startYear = startYear;
		 this.endYear = endYear;
		 this.location = location;
		 this.result = result;
		 for (String figure : relatedFigures) {
			 this.relatedFigures.put(figure, 0);
		 }
	 }

	public String getStartYear() {
		return startYear;
	}

	public String getEndYear() {
		return endYear;
	}

	public String getLocation() {
		return location;
	}

	public String getResult() {
		return result;
	}


	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public void setLocation(String location) {
		this.location = location;
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
	
	public void setEras(Map<String, Integer> eras) {
		this.eras = eras;
	}

	public void addEras(String newEra, int id) {
		boolean isFound = false;
		for (String era : eras.keySet()) {
			if (era.equalsIgnoreCase(newEra)) {
				isFound = true;
				break;
			}
		}
		if (!isFound) {
			eras.put(newEra, id);
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
