package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Era extends HistoricalEntity{
	private static int numEras = 0;
	private int id;
	private String name;
	private String startYear;
	private String endYear;
	private HashMap<String, Integer> kings = new HashMap<>();
	private String description;
//	private String nation;
//	private String capital;
	
	public Era(String eraName, String startYear, String endYear, String description, ArrayList<String> kings) {
		this.id = ++numEras;
		this.name = eraName;
		this.startYear = startYear;
		this.endYear = endYear;
		this.description = description;
		for (String king : kings) {
			this.kings.put(king, 0);
		}
	}

}
