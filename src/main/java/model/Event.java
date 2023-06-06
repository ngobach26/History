package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Event extends HistoricalEntity{
	 private static int numEvents = 0;
	 private int id;
	 private String name = "Không rõ";
	 private String time = "Không rõ";
	 private String location = "Không rõ";
	 private String description = "Không rõ";
	 private String result = "Không rõ";
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
