package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Figure extends HistoricalEntity {
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



	public Figure(int id, String name, String bornYear, String diedYear, HashMap<String, Integer> eras,
			String location, String role, HashMap<String, Integer> spouses, HashMap<String, Integer> mother,
			HashMap<String, Integer> father, HashMap<String, Integer> children, String description) {
		super(id, name, description);
		this.bornYear = bornYear;
		this.diedYear = diedYear;
		this.eras = eras;
		this.location = location;
		this.role = role;
		this.spouses = spouses;
		this.mother = mother;
		this.father = father;
		this.children = children;
		this.description = description;
	}

	public Figure(String name, List<String> otherNames, String bornYear, String diedYear, List<String> eras, String location, String role, String description) {
		super(++numFigures, name, description);
		this.otherNames = otherNames;
		this.bornYear = bornYear;
		this.diedYear = diedYear;
		for (String era : eras) {
			this.eras.put(era, 0);
		}
		this.location = location;
		this.role = role;
	}

	public List<String> getOtherNames() {
		return otherNames;
	}

	public String getBornYear() {
		return bornYear;
	}

	public String getDiedYear() {
		return diedYear;
	}

	public Map<String, Integer> getEras() {
		return eras;
	}

	public String getLocation() {
		return location;
	}

	public String getRole() {
		return role;
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
}
