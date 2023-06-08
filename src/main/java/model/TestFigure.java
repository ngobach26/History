package model;

import java.util.ArrayList;
import java.util.HashMap;

public class TestFigure {
    private int id;
    private String name;
	private ArrayList<String> otherNames;
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
    
    public TestFigure(int id, String name, ArrayList<String> otherNames, String bornYear, String diedYear,
            HashMap<String, Integer> eras, String location, String role, HashMap<String, Integer> spouses,
            HashMap<String, Integer> mother, HashMap<String, Integer> father, HashMap<String, Integer> children,
            String description) {
        this.id = id;
        this.name = name;
        this.otherNames = otherNames;
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
}
