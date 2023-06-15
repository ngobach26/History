package model;

import java.util.ArrayList;
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

	public static int getNumFigures() {
		return numFigures;
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

	public void setSpouses(Map<String, Integer> spouses) {
		this.spouses = spouses;
	}

	public Map<String, Integer> getMother() {
		return mother;
	}

	public void setMother(Map<String, Integer> mother) {
		this.mother = mother;
	}

	public Map<String, Integer> getFather() {
		return father;
	}

	public void setFather(Map<String, Integer> father) {
		this.father = father;
	}

	public Map<String, Integer> getChildren() {
		return children;
	}

	public void setChildren(Map<String, Integer> children) {
		this.children = children;
	}

	public String getEraString() {
		StringBuilder sb = new StringBuilder();
		sb.append("");
		if (eras.isEmpty()) {
			return "không rõ";
		}
		for (String era : eras.keySet()) {
			int id = eras.get(era);
			sb.append(era).append(" (").append(id).append("), ");
		}
		sb.replace(sb.length() - 2, sb.length(), "");
		return sb.toString();
	}

	public String getMotherString() {
		StringBuilder sb = new StringBuilder();
		sb.append("");
		if (mother.isEmpty()) {
			return "không rõ";
		}
		for (String mo : mother.keySet()) {
			int id = mother.get(mo);
			sb.append(mo).append(" (").append(id).append("), ");
		}
		sb.replace(sb.length() - 2, sb.length(), "");
		return sb.toString();
	}

	public String getfatherString() {
		StringBuilder sb = new StringBuilder();
		sb.append("");
		if (father.isEmpty()) {
			return "không rõ";
		}
		for (String fa : father.keySet()) {
			int id = father.get(fa);
			sb.append(fa).append(" (").append(id).append("), ");
		}
		sb.replace(sb.length() - 2, sb.length(), "");
		return sb.toString();
	}

	public String getChildrenString() {
		StringBuilder sb = new StringBuilder();
		sb.append("");
		if (children.isEmpty()) {
			return "không rõ";
		}
		for (String ch : children.keySet()) {
			int id = children.get(ch);
			sb.append(ch).append(" (").append(id).append("), ");
		}
		sb.replace(sb.length() - 2, sb.length(), "");
		return sb.toString();
	}

	public String getSpouseString() {
		StringBuilder sb = new StringBuilder();
		sb.append("");
		if (spouses.isEmpty()) {
			return "không rõ";
		}
		for (String sp : spouses.keySet()) {
			int id = spouses.get(sp);
			sb.append(sp).append(" (").append(id).append("), ");
		}
		sb.replace(sb.length() - 2, sb.length(), "");
		return sb.toString();
	}

}
