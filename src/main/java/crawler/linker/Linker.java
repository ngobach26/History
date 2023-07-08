package crawler.linker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import helper.JsonIO;
import helper.StringHelper;
import model.Era;
import model.Event;
import model.Festival;
import model.Figure;
import model.Relic;

public class Linker {
	private static final JsonIO<Figure> FIGURE_IO = new JsonIO<>(new TypeToken<ArrayList<Figure>>() {}.getType());
	private static final JsonIO<Era> ERA_IO = new JsonIO<>(new TypeToken<ArrayList<Era>>() {}.getType());
	private static final JsonIO<Event> EVENT_IO = new JsonIO<>(new TypeToken<ArrayList<Event>>() {}.getType());
	private static final JsonIO<Festival> FESTIVAL_IO = new JsonIO<>(new TypeToken<ArrayList<Festival>>() {}.getType());
	private static final JsonIO<Relic> RELIC_IO = new JsonIO<>(new TypeToken<ArrayList<Relic>>() {}.getType());
	
	private static final String FIGURE_PATH = "src/main/resources/json/Figures.json";
	private static final String ERA_PATH = "src/main/resources/json/Eras.json";
	private static final String EVENT_PATH = "src/main/resources/json/Events.json";
	private static final String FESTIVAL_PATH = "src/main/resources/json/Festivals.json";
	private static final String RELIC_PATH = "src/main/resources/json/Relics.json";
	
	private List<Figure> figures;
	private List<Era> eras;
	private List<Event> events;
	private List<Festival> festivals;
	private List<Relic> relics;
	
	public Linker() {
		this.figures = FIGURE_IO.loadJson(FIGURE_PATH);
		this.eras = ERA_IO.loadJson(ERA_PATH);
		this.events = EVENT_IO.loadJson(EVENT_PATH);
		this.festivals = FESTIVAL_IO.loadJson(FESTIVAL_PATH);
		this.relics = RELIC_IO.loadJson(RELIC_PATH);
	}
	
	public void link() {
		linkFigureToEra();
		linkEraToFigure();
		linkRelicToFigure();
		linkEventToFigure();
		linkEventToEra();
		linkFestivalToRelic();
		linkFestivalToFigure();
		linkFigureToFigure();
		FIGURE_IO.writeJson(this.figures, FIGURE_PATH);
		ERA_IO.writeJson(this.eras, ERA_PATH);
		RELIC_IO.writeJson(this.relics, RELIC_PATH);
		EVENT_IO.writeJson(this.events, EVENT_PATH);
		FESTIVAL_IO.writeJson(this.festivals, FESTIVAL_PATH);
	}
	
	//link eras attribute of figure to era
	private void linkFigureToEra() {
		for (Figure figure : figures) {
			Map<String, Integer> linkedEras = new HashMap<>();
			
			if (figure.getEras().isEmpty()) {
				String bornYear = figure.getBornYear();
				String diedYear = figure.getDiedYear();
				int bornYearInt = Integer.MIN_VALUE;
				int diedYearInt = Integer.MIN_VALUE;
				if (!bornYear.equals("Không rõ")) {
					bornYearInt = Integer.parseInt(bornYear);										
				}
				if (!diedYear.equals("Không rõ")) {
					diedYearInt = Integer.parseInt(diedYear);					
				}
				
				for (Era era : eras) {
					String startYear = era.getStartYear();
					String endYear = era.getEndYear();
					
					if (!startYear.equals("Không rõ") && !endYear.equals("Không rõ")) {
						int startYearInt = Integer.parseInt(startYear);
						int endYearInt;
						if (endYear.equals("đến nay")) {
							endYearInt = Integer.MAX_VALUE;
						}
						else {
							endYearInt = Integer.parseInt(endYear);
						}						
						
						if ((bornYearInt >= startYearInt) && (bornYearInt < endYearInt)) {
							linkedEras.put(era.getName(), era.getId());
						}	
												
						if ((diedYearInt > startYearInt) && (diedYearInt <= endYearInt)) {
							linkedEras.put(era.getName(), era.getId());
						}
						
						if ((bornYearInt > Integer.MIN_VALUE) && (diedYearInt > Integer.MIN_VALUE)
							&& (bornYearInt < startYearInt) && (endYearInt < diedYearInt)) {
							linkedEras.put(era.getName(), era.getId());
						}
					}
				}
			}
			else {
				for (String eraName : figure.getEras().keySet()) {
					boolean isFound = false;
					for (Era era : eras) {
						if (eraName.equals(era.getName())) {
							linkedEras.put(eraName, era.getId());
							isFound = true;
							break;
						}
					}
					if (!isFound) {
						linkedEras.put(eraName, 0);
					}
				}
			}
			
			figure.setEras(linkedEras);
		}
	}
	
	//link kings attribute of era to figure
	private void linkEraToFigure() {
		for (Era era : eras) {
			Map<String, Integer> linkedKings = new HashMap<>();
			
			for (String kingName : era.getKings().keySet()) {
				boolean isFound = false;
				for (Figure figure : figures) {
					if (figure.containsName(kingName)){
						isFound = true;
						linkedKings.put(kingName, figure.getId());
						break;
					}
				}
				if (!isFound) {
					linkedKings.put(kingName, 0);
				}
			}
			
			era.setKings(linkedKings);
		}
	}
	
	//link relatedFigures attribute of event to figure
	private void linkEventToFigure() {
		for (Event event : events) {
			Map<String, Integer> linkedRelatedFigures = new HashMap<>();
			
			for (String figureName : event.getRelatedFigures().keySet()) {
				boolean isFound = false;
				for (Figure figure : figures) {
					if (figure.containsName(figureName)) {
						isFound = true;
						linkedRelatedFigures.put(figureName, figure.getId());
						//link figure to event
						figure.addRelatedEvents(event.getName(), event.getId());
						break;
					}
				}
				if (!isFound) {
					linkedRelatedFigures.put(figureName, 0);
				}
			}
			
			event.setRelatedFigures(linkedRelatedFigures);
		}
	}
	
	//link eras attribute of event to era
	private void linkEventToEra() {
		for (Event event : events) {
			Map<String, Integer> linkedEras = new HashMap<>();
			
			String eventStartYear = event.getStartYear();
			String eventEndYear = event.getEndYear();
			int eventStartYearInt = Integer.MIN_VALUE;
			int eventEndYearInt = Integer.MIN_VALUE;
			if (!eventStartYear.equals("Không rõ")) {
				eventStartYearInt = Integer.parseInt(eventStartYear);
			}
			if (!eventEndYear.equals("Không rõ")) {
				eventEndYearInt = Integer.parseInt(eventEndYear);
			}
			
			for (Era era : eras) {
				String eraStartYear = era.getStartYear();
				String eraEndYear = era.getEndYear();
				
				if (!eraStartYear.equals("Không rõ") && !eraEndYear.equals("Không rõ")) {
					int eraStartYearInt = Integer.parseInt(eraStartYear);
					int eraEndYearInt;
					if (eraEndYear.equals("đến nay")) {
						eraEndYearInt = Integer.MAX_VALUE;
					}
					else {
						eraEndYearInt = Integer.parseInt(eraEndYear);
					}					
					
					if (eventStartYearInt >= eraStartYearInt && eventStartYearInt < eraEndYearInt) {
						linkedEras.put(era.getName(), era.getId());
						//link era to event
						era.addRelatedEvents(event.getName(), event.getId());
					}					
					
					if (eventEndYearInt > eraStartYearInt && eventEndYearInt <= eraEndYearInt) {
						linkedEras.put(era.getName(), era.getId());
						//link era to event
						era.addRelatedEvents(event.getName(), event.getId());
					}
					
					if ((eventStartYearInt > Integer.MIN_VALUE) && (eventEndYearInt > Integer.MIN_VALUE)
						&& (eventStartYearInt < eraStartYearInt) && (eraEndYearInt < eventEndYearInt)) {
							linkedEras.put(era.getName(), era.getId());
							//link era to event
							era.addRelatedEvents(event.getName(), event.getId());
					}							
				}				
			}
			event.setEras(linkedEras);
		}
	}
	
	//link relatedFigures attribute of relic to figure
	private void linkRelicToFigure() {
		for (Relic relic : relics) {
			Map<String, Integer> linkedRelatedFigures = new HashMap<>();
			
			for (String figureName : relic.getRelatedFigures().keySet()) {
				for (Figure figure : figures) {
					if (figure.containsName(figureName)) {
						linkedRelatedFigures.put(figureName, figure.getId());
						//link figure to relic
						figure.addRelatedRelics(relic.getName(), relic.getId());
						break;
					}
				}
			}
			
			relic.setRelatedFigures(linkedRelatedFigures);
		}
	}
	
	//link relatedRelics attribute of festival to relic
	private void linkFestivalToRelic() {
		for (Festival festival : festivals) {
			Map<String, Integer> linkedRelatedRelics = new HashMap<>();
			
			for (String relicName : festival.getRelatedRelics().keySet()) {
				boolean isFound = false;
				for (Relic relic : relics) {
					if (StringHelper.containsSubstrings(relicName, relic.getName()) ||
							StringHelper.containsSubstrings(relic.getName(), relicName)) {
						linkedRelatedRelics.put(relicName, relic.getId());
						//link relic to festival
						relic.addRelatedFestivals(festival.getName(), festival.getId());
						isFound = true;
						break;
					}
				}
				if (!isFound) {
					linkedRelatedRelics.put(relicName, 0);
				}
			}
			
			festival.setRelatedRelics(linkedRelatedRelics);
		}
	}
	
	//link relatedFigures attribute of festival to figure
	private void linkFestivalToFigure() {
		for (Festival festival : festivals) {
			Map<String, Integer> linkedRelatedFigures = new HashMap<>();
			
			for (String figureName : festival.getRelatedFigures().keySet()) {
				boolean isFound = false;
				for (Figure figure : figures) {
					if (figure.containsName(figureName)) {
						linkedRelatedFigures.put(figureName, figure.getId());
						//link figure to festival
						figure.addRelatedFestivals(festival.getName(), festival.getId());
						isFound = true;
						break;
					}
				}
				if (!isFound) {
					linkedRelatedFigures.put(figureName, 0);
				}
			}
			
			festival.setRelatedFigures(linkedRelatedFigures);
		}
	}
	
	//link mother, father, spouses, children of figure to figure
	private void linkFigureToFigure() {
		for (Figure figure: figures) {
			Map<String, Integer> linkedMother = new HashMap<>();
			Map<String, Integer> linkedFather = new HashMap<>();
			Map<String, Integer> linkedSpouses = new HashMap<>();
			Map<String, Integer> linkedChildren = new HashMap<>();
			
			for (String motherName : figure.getMother().keySet()) {
				boolean isFound = false;
				for (Figure otherFigure : figures) {
					if (!otherFigure.equals(figure) && otherFigure.containsName(motherName)) {
						linkedMother.put(motherName, otherFigure.getId());
						isFound = true;
						break;
					}
				}
				if (!isFound) {
					linkedMother.put(motherName, 0);
				}
			}
			
			for (String fatherName : figure.getFather().keySet()) {
				boolean isFound = false;
				for (Figure otherFigure : figures) {
					if (!otherFigure.equals(figure) && otherFigure.containsName(fatherName)) {
						linkedFather.put(fatherName, otherFigure.getId());
						isFound = true;
						break;
					}
				}
				if (!isFound) {
					linkedFather.put(fatherName, 0);
				}
			}
			
			for (String spouseName : figure.getSpouses().keySet()) {
				boolean isFound = false;
				for (Figure otherFigure : figures) {
					if (!otherFigure.equals(figure) && otherFigure.containsName(spouseName)) {
						linkedSpouses.put(spouseName, otherFigure.getId());
						isFound = true;
						break;
					}
				}
				if (!isFound) {
					linkedSpouses.put(spouseName, 0);
				}
			}
			
			for (String childName : figure.getChildren().keySet()) {
				boolean isFound = false;
				for (Figure otherFigure : figures) {
					if (!otherFigure.equals(figure) && otherFigure.containsName(childName)) {
						linkedChildren.put(childName, otherFigure.getId());
						isFound = true;
						break;
					}
				}
				if (!isFound) {
					linkedChildren.put(childName, 0);
				}
			}
			
			figure.setMother(linkedMother);
			figure.setFather(linkedFather);
			figure.setSpouses(linkedSpouses);
			figure.setChildren(linkedChildren);
		}
	}
	
}
