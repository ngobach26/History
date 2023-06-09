package model;

import controller.detail.FigureDetailController;
import helper.StringHelper;
import javafx.fxml.FXMLLoader;
import main.App;
import main.EntityPages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Figure extends HistoricalEntity{
	private static int numFigures = 0;
	private List<String> otherNames;
	private String bornYear;
	private String diedYear;
	private Map<String, Integer> eras = new HashMap<>();
	private String location;
	private String role;
	private Map<String, Integer> spouses = new HashMap<>();
	private Map<String, Integer> mother = new HashMap<>();
	private Map<String, Integer> father = new HashMap<>();
	private Map<String, Integer> children = new HashMap<>();
	private Map<String, Integer> relatedEvents = new HashMap<>();
	private Map<String, Integer> relatedRelics = new HashMap<>();
	private Map<String, Integer> relatedFestivals = new HashMap<>();
	
	public Figure(String name, List<String> otherNames, String bornYear, String diedYear, List<String> eras, 
				  String location, String role, String description) {
		super(++numFigures, name, description);
		this.otherNames = otherNames;
		this.bornYear = bornYear;
		this.diedYear = diedYear;
		for (String era : eras) {
			this.eras.put(era, 0); //id 0 means unknown
		}
		this.location = location;
		this.role = role;
	}
	
	public List<String> getAllNames() {
		List<String> allNames = new ArrayList<>(otherNames);
		allNames.add(getName());
		return allNames;
	}
	
	public List<String> getOtherNames() {
		return otherNames;
	}
	
	public void setOtherNames(List<String> otherNames) {
		this.otherNames = otherNames;
	}

	public String getBornYear() {
		return bornYear;
	}

	public void setBornYear(String bornYear) {
		this.bornYear = bornYear;
	}

	public String getDiedYear() {
		return diedYear;
	}

	public void setDiedYear(String diedYear) {
		this.diedYear = diedYear;
	}
	
	public Map<String, Integer> getEras() {
		return eras;
	}

	public void setEras(Map<String, Integer> eras) {
		this.eras = eras;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Map<String, Integer> getSpouses() {
		return spouses;
	}

	public Map<String, Integer> getMother() {
		return mother;
	}

	public Map<String, Integer> getFather() {
		return father;
	}

	public Map<String, Integer> getChildren() {
		return children;
	}

	public void setSpouses(Map<String, Integer> spouses) {
		this.spouses = spouses;
	}

	public void setMother(Map<String, Integer> mother) {
		this.mother = mother;
	}

	public void setFather(Map<String, Integer> father) {
		this.father = father;
	}
	
	public void setChildren(Map<String, Integer> children) {
		this.children = children;
	}
	
	public Map<String, Integer> getRelatedEvents() {
		return relatedEvents;
	}

	public Map<String, Integer> getRelatedRelics() {
		return relatedRelics;
	}

	public Map<String, Integer> getRelatedFestivals() {
		return relatedFestivals;
	}

	public void addRelatedEvents(String newEvent, int id) {
		if (!StringHelper.containString(relatedEvents.keySet(), newEvent)) {
			relatedEvents.put(newEvent, id);
		}		
	}
	
	public void addRelatedFestivals(String newFestival, int id) {
		if (!StringHelper.containString(relatedFestivals.keySet(), newFestival)) {
			relatedFestivals.put(newFestival, id);
		}		
	}
	
	public void addRelatedRelics(String newRelic, int id) {
		if (!StringHelper.containString(relatedRelics.keySet(), newRelic)) {
			relatedRelics.put(newRelic, id);
		}		
	}

	public boolean containsName(String comparedName) {
		if (comparedName == null) {
			return false;
		}
		List<String> allNames = getAllNames();
		
		for (String name : allNames) {
			if (name.equalsIgnoreCase(comparedName)) {
				return true;
			}
			if (StringHelper.containsSubstrings(comparedName, "công chúa", "thái hậu", "hoàng hậu", "vương") ||
					StringHelper.containsSubstrings(name, "công chúa", "thái hậu", "hoàng hậu", "vương")) {
				comparedName = comparedName.replaceAll("(?i)(công chúa|thái hậu|hoàng hậu)", "").trim();
				name = name.replaceAll("(?i)(công chúa|thái hậu|hoàng hậu)", "").trim();
				if (StringHelper.containsSubstrings(name, comparedName) ||
						StringHelper.containsSubstrings(comparedName, name)) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean containsNameForSearch(String name) {
		if (name == null)
			return false;

		if (this.name == null)
			return false;
		if(this.name.toLowerCase().contains(name.toLowerCase())){
			return true;
		}
		for(String n : otherNames){
			if(n.toLowerCase().contains(name.toLowerCase())){
				return true;
			}
		}
		return false;
	}

	@Override
	public void navigatePage() {
		try{
			FXMLLoader loader = App.setAndReturnRoot(EntityPages.FIGURE_PAGES.getDetailPage());
			FigureDetailController controller = loader.getController();
			controller.setFigure(this);
		}catch (NullPointerException e){
			e.printStackTrace();
		}

	}

}

