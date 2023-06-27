package linker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import crawler.JsonIO;
import model.Era;
import model.Event;
import model.Festival;
import model.Figure;
import model.Relic;

public class Linker {
	private JsonIO<Figure> figureIO = new JsonIO<>(new TypeToken<ArrayList<Figure>>() {}.getType());
	private JsonIO<Era> eraIO = new JsonIO<>(new TypeToken<ArrayList<Era>>() {}.getType());
	private JsonIO<Event> eventIO = new JsonIO<>(new TypeToken<ArrayList<Event>>() {}.getType());
	private JsonIO<Festival> festivalIO = new JsonIO<>(new TypeToken<ArrayList<Festival>>() {}.getType());
	private JsonIO<Relic> relicIO = new JsonIO<>(new TypeToken<ArrayList<Relic>>() {}.getType());
	
	private List<Figure> figures;
	private List<Era> eras;
	private List<Event> events;
	private List<Festival> festivals;
	private List<Relic> relics;
	
	Linker() {
		this.figures = figureIO.loadJson("src/main/resources/json/Figures.json");
		this.eras = eraIO.loadJson("src/main/resources/json/Eras.json");
		this.events = eventIO.loadJson("src/main/resources/json/Events.json");
		this.festivals = festivalIO.loadJson("src/main/resources/json/Festivals.json");
		this.relics = relicIO.loadJson("src/main/resources/json/Relics.json");
	}
	
	public void link() {
//		linkFigureToEra();
//		linkEraToFigure();
		linkRelicToFigure();
//		figureIO.writeJson(this.figures, "src/main/resources/json/Figures.json");
//		eraIO.writeJson(this.eras, "src/main/resources/json/Eras.json");
		relicIO.writeJson(this.relics, "src/main/resources/json/Relics.json");
	}
	
	//link eras attribute of figure to era
	public void linkFigureToEra() {
		for (Figure figure : figures) {
			Map<String, Integer> linkedEras = new HashMap<>();
			
			for (Map.Entry<String, Integer> entry : figure.getEras().entrySet()) {
				boolean isFound = false;
				for (Era era : eras) {
					if (entry.getKey().equals(era.getName())) {
						linkedEras.put(entry.getKey(), era.getId());
						isFound = true;
						break;
					}
				}
				if (!isFound) {
					linkedEras.put(entry.getKey(), 0);
				}
			}
			
			figure.setEras(linkedEras);
		}
	}
	
	//link kings attribute of era to figure
	public void linkEraToFigure() {
		for (Era era : eras) {
			Map<String, Integer> linkedKings = new HashMap<>();
			
			for (Map.Entry<String, Integer> entry : era.getKings().entrySet()) {
				boolean isFound = false;
				String kingName = entry.getKey();
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
	
	//link relatedFigures attribute of relic to figure
	public void linkRelicToFigure() {
		for (Relic relic : relics) {
			Map<String, Integer> linkedRelatedFigures = new HashMap<>();
			
			for (Map.Entry<String, Integer> entry : relic.getRelatedFigures().entrySet()) {
				String figureName = entry.getKey();
				for (Figure figure : figures) {
					if (figure.containsName(figureName)) {
						linkedRelatedFigures.put(figureName, figure.getId());
						break;
					}
				}
			}
			
			relic.setRelatedFigures(linkedRelatedFigures);
		}
	}
	
	public static void main(String[] args) {
		Linker linker = new Linker();
		linker.link();
	}
}
