package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event extends HistoricalEntity {
	private static int numEvents = 0;
	private String startYear;
	private String endYear;
	private String location;
	private String result;
	private Map<String, Integer> relatedFigures = new HashMap<>();
	private Map<String, Integer> eras = new HashMap<>();

	public Map<String, Integer> getEras() {
		return eras;
	}

	public String getStartYear() {
		return startYear;
	}

	public String getLocation() {
		return location;
	}

	public String getEndYear() {
		return endYear;
	}

	public String getResult() {
		return result;
	}

	public Map<String, Integer> getRelatedFigures() {
		return relatedFigures;
	}

}
