package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Era extends HistoricalEntity {
	private static int numEras = 0;
	private String startYear;
	private String endYear;
	private Map<String, Integer> kings = new HashMap<>();
	private List<String> nationNames;
	private String capital;
	private Map<String, Integer> relatedEvents = new HashMap<>();

	public Era(String eraName, String startYear, String endYear, String description, String capital,
			List<String> nationNames, List<String> kings) {
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

	public List<String> getNationNames() {
		return nationNames;
	}

	public String getCapital() {
		return capital;
	}

	public Map<String, Integer> getRelatedEvents() {
		return relatedEvents;
	}
}
