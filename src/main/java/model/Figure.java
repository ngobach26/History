package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Figure extends HistoricalEntity{
	private static int numFigures = 0;
	private int id;
	private ArrayList<String> names;
	private String bornYear;
	private String diedYear;
	private HashMap<String, Integer> eras = new HashMap<>();
	private String location;
	private String role;
	private HashMap<String, Integer> spouses = new HashMap<>();
	private HashMap<String, Integer> mother = new HashMap<>();
	private HashMap<String, Integer> father = new HashMap<>();
	private HashMap<String, Integer> children = new HashMap<>();
	private String description;
	
	public Figure(ArrayList<String> names, String bornYear, String diedYear, ArrayList<String> eras, String location, String role, String description) {
		numFigures ++;
		this.id = numFigures;
		this.names = names;
		this.bornYear = bornYear;
		this.diedYear = diedYear;
		for (String era : eras) {
			this.eras.put(era, 0); //id 0 means unknown
		}
		this.location = location;
		this.role = role;
//		this.spouse.put(spouse, 0);
		this.description = description;
	}

	public ArrayList<String> getNames() {
		return names;
	}
	
	public HashMap<String, Integer> getEras(){
		return eras;
	}

	public void setMother(HashMap<String, Integer> mother) {
		this.mother = mother;
	}

	public void setFather(HashMap<String, Integer> father) {
		this.father = father;
	}
	
	public void setChildren(HashMap<String, Integer> children) {
		this.children = children;
	}
	
	public void setSpouses(HashMap<String, Integer> spouses) {
		this.spouses = spouses;
	}

}

