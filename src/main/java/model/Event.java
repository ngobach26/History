package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Event extends HistoricalEntity {
	private String time;
	private String location;
	private String description;
	private String cause;
	private String result;
	private HashMap<String, Integer> relatedFigures = new HashMap<>();
	public static int numEvents = 0;

	public Event(String eventName, String time, String location, String result, ArrayList<String> relatedFigures) {
		super(++numEvents, eventName);
		this.time = time;
		this.location = location;
		this.result = result;
		for (String figure : relatedFigures) {
			this.relatedFigures.put(figure, 0);
		}
	}

	public Event(int id, String name, String time, String location, String description, String cause, String result,
			HashMap<String, Integer> relatedFigures) {
		super(id, name);
		this.time = time;
		this.location = location;
		this.description = description;
		this.cause = cause;
		this.result = result;
		this.relatedFigures = relatedFigures;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public HashMap<String, Integer> getRelatedFigures() {
		return relatedFigures;
	}

	public void setRelatedFigures(HashMap<String, Integer> relatedFigures) {
		this.relatedFigures = relatedFigures;
	}

	public static int getNumEvents() {
		return numEvents;
	}

	public static void setNumEvents(int numEvents) {
		Event.numEvents = numEvents;
	}

	public Event(String eventName, String time) {
		super(++numEvents, eventName);
		this.time = time;
	}

}
