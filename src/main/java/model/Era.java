package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Era extends HistoricalEntity{
	private static int numEras = 0;
	private String startYear;
	private String endYear;
	private Map<String, Integer> kings = new HashMap<>();
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

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public Map<String, Integer> getKings() {
		return kings;
	}


	public void setKings(Map<String, Integer> kings) {
		this.kings = kings;
	}


	public List<String> getNationNames() {
		return nationNames;
	}


	public void setNationNames(List<String> nationNames) {
		this.nationNames = nationNames;
	}


	public String getCapital() {
		return capital;
	}


	public void setCapital(String capital) {
		this.capital = capital;
	}


	public String getKingString() {
		StringBuilder sb = new StringBuilder();
		sb.append("");
		if (kings.isEmpty()) {
			return "không rõ";
		}
		for (String king : kings.keySet()) {
			int id = kings.get(king);
			sb.append(king).append(" (").append(id).append("), ");
		}
		sb.replace(sb.length() - 2, sb.length(), "");
		return sb.toString();
	}
	
}
