package model;

public class Era{
	private static int numEras = 0;
	private int id;
	private String name;
	private String startYear;
	private String endYear;
//	private List<String> kings;
//	private String nation;
	private String capital;
	
	public Era(String name, String startYear, String endYear) {
		this.id = ++numEras;
		this.name = name;
		this.startYear = startYear;
		this.endYear = endYear;
//		this.kings = kings;
//		this.nation = nation;
//		this.capital = capital;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCapital(String capital) {
		this.capital = capital;
	}
}
