package model;

import java.util.HashMap;

public class Event extends HistoricalEntity{
	 private String time;
	 private String location;
	 private String description;
	 private String cause;
	 private String result;
	 private HashMap<String, Integer> relatedFigures = new HashMap<>();
}
