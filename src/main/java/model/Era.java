package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Era extends HistoricalEntity{
	private static int numEras = 0;
	private String startYear;
	private String endYear;
	private Map<String, Integer> kings = new HashMap<>();
//	private String nation;
	private String capital;
	
	public Era(String eraName, String startYear, String endYear, String description,String capital, List<String> kings) {
		super(++numEras, eraName, description);
		this.startYear = startYear;
		this.endYear = endYear;
		this.capital = capital;
		for (String king : kings) {
			this.kings.put(king, 0);
		}
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
