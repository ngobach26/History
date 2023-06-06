package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Event extends HistoricalEntit{
	 private String time;
	 private String location;
	 private String description;
	 private String cause;
	 private String result;
  
	 private HashMap<String, Integer> relatedFigures = new HashMap<>();
	 
	 public Event(String eventName, String time, String location, String result, ArrayList<String> relatedFigures) {
		 this.id = ++numEvents;
		 this.name = eventName;
		 this.time = time;
		 this.location = location;
		 this.result = result;
		 for (String figure : relatedFigures) {
			 this.relatedFigures.put(figure, 0);
		 }
	 }
	 
	 public Event(String eventName, String time) {
		 this.id = ++numEvents;
		 this.name = eventName;
		 this.time = time;
	 }
}
