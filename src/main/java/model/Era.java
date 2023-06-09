package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.detail.EraDetailController;
import helper.StringHelper;
import javafx.fxml.FXMLLoader;
import main.App;
import main.EntityPages;

public class Era extends HistoricalEntity{
	private static int numEras = 0;
	private String startYear;
	private String endYear;
	private Map<String, Integer> kings = new HashMap<>();
	private Map<String, Integer> relatedEvents = new HashMap<>();
	private List<String> nationNames;
	private String capital;
	
	public Era(String eraName, String startYear, String endYear, String description,String capital, List<String> nationNames, List<String> kings) {
		super(++numEras, eraName, description);
		this.startYear = startYear;
		this.endYear = endYear;
		this.capital = capital;
		this.nationNames = nationNames;
		for (String king : kings) {
			this.kings.put(king, 0);
		}
	}
	
	public String getStartYear() {
		return startYear;
	}


	public String getEndYear() {
		return endYear;
	}


	public Map<String, Integer> getKings() {
		return kings;
	}


	public Map<String, Integer> getRelatedEvents() {
		return relatedEvents;
	}


	public List<String> getNationNames() {
		return nationNames;
	}


	public String getCapital() {
		return capital;
	}


	public void setKings(Map<String, Integer> kings) {
		this.kings = kings;
	}
	
	
	public void addRelatedEvents(String newEvent, int id) {
		if (!StringHelper.containString(relatedEvents.keySet(), newEvent)) {
			relatedEvents.put(newEvent, id);
		}		
	}

	@Override
	public void navigatePage() {
		try {
			FXMLLoader loader = App.setAndReturnRoot(EntityPages.ERA_PAGES.getDetailPage());
			EraDetailController controller = loader.getController();
			controller.setEra(this);
		}catch (NullPointerException e){
			e.printStackTrace();
		}
	}

}
