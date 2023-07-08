package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.detail.EventDetailController;
import helper.StringHelper;
import javafx.fxml.FXMLLoader;
import main.App;
import main.EntityPages;

public class Event extends HistoricalEntity{
	 private static int numEvents = 0;
	 private String startYear;
	 private String endYear;
	 private String location;
	 private String result;
	 private Map<String, Integer> eras = new HashMap<>();
	 private Map<String, Integer> relatedFigures = new HashMap<>();
	 
	 public Event(String eventName, String startYear, String endYear, String location, String result,
			 List<String> relatedFigures, String description) {
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
	
	public Map<String, Integer> getEras() {
		return eras;
	}

	public void setEras(Map<String, Integer> eras) {
		this.eras = eras;
	}

	public Map<String, Integer> getRelatedFigures() {
		return relatedFigures;
	}

	public void setRelatedFigures(Map<String, Integer> relatedFigures) {
		this.relatedFigures = relatedFigures;
	}
	
	public void addRelatedFigures(String newFigure, int id) {
		if (!StringHelper.containString(relatedFigures.keySet(), newFigure)) {
			relatedFigures.put(newFigure, id);
		}		
	}

	@Override
	public void navigatePage() {
		 try {
			 FXMLLoader loader = App.setAndReturnRoot(EntityPages.EVENT_PAGES.getDetailPage());
			 EventDetailController controller = loader.getController();
			 controller.setEvent(this);
		 }catch (NullPointerException e){
			 e.printStackTrace();
		 }
	}
}
