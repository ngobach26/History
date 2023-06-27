package crawler.era;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.google.gson.reflect.TypeToken;

import crawler.ICrawler;
import crawler.JsonIO;
import crawler.figure.FigureCrawler;
import model.Era;

public class EraCrawler implements ICrawler{
	private JsonIO<Era> eraIO = new JsonIO<>(new TypeToken<ArrayList<Era>>() {}.getType());

	@Override
	public void crawl() {
		eraIO.writeJson(crawlVanSu(), "src/main/resources/json/Eras.json");
	}
	
	
	public List<Era> crawlVanSu(){
		List<Era> eras = new ArrayList<>();
		String url = "https://vansu.vn/viet-nam/nien-bieu-lich-su";
		Document doc;
		
		try {
			doc = Jsoup.connect(url).get();
			Elements eraTitles = doc.select("b a[onclick]");
			
			Element container = doc.selectFirst("div.container.ui[style]");
			Elements divs = container.select("> div");
			//between 2 divs is information about an era
			for (int i=0; i<divs.size()-1; i++) {
				Element startDiv = divs.get(i);
				Element endDiv = divs.get(i+1);
				eras.add(extractVanSu(startDiv, endDiv));
			}
					
			//crawl the last era
			Element lastDiv = divs.get(divs.size() - 1);
			eras.add(extractVanSu(lastDiv, null));
				
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return eras;
	}
	
	private Era extractVanSu(Element startDiv, Element endDiv) {
		Element eraTitle = startDiv.selectFirst("b a[onclick]");
		String eraName = eraTitle.text();
		String startYear = "Không rõ";
		String endYear = "Không rõ";
		String description = "";
		String capital = "Không rõ";
		List<String> nationNames = new ArrayList<>();
		List<String> kings = new ArrayList<>();
		
		//extract startYear, endYear from eraName
		String stringArr[] = eraName.split("\\(");
		if (stringArr.length ==2) {
			stringArr = stringArr[1].replace(")", "").split("[-–]");
			startYear = stringArr[0].trim();
			endYear = stringArr[1].trim();
		}
		
		//eraNames standardization between Figures.json and Eras.json
		Set<String> eraNames = new FigureCrawler().getUniqueEras();
		for (String newName : eraNames) {
			if (newName.contains(startYear) && newName.contains(endYear)) {
				eraName = newName;
				break;
			}
		}
		
		//reformat endYear
		if (endYear.contains("trCN")) {
			startYear += " trCN";
		}
		
		//crawl capital, nationName
		List<PeriodEntity> eraCapitals = crawlCapital();
		List<PeriodEntity> nationNameEntities = crawlNationName();
		if (!eraName.equals("Thời tiền sử")) {
			int startYearInt = convertYearStringToInt(startYear);
			int endYearInt = convertYearStringToInt(endYear);
			for (PeriodEntity eraCapital : eraCapitals) {
				if ((startYearInt >= eraCapital.startYear) && (endYearInt <= eraCapital.endYear + 1)) {
					capital = eraCapital.name;
					break;
				}
			}
			for (PeriodEntity nationNameEntity : nationNameEntities) {
				if (!(endYearInt <= nationNameEntity.startYear || nationNameEntity.endYear <= startYearInt)) {
						nationNames.add(nationNameEntity.name);
					}
			}
		}
		
	
		System.out.println(eraName);
		System.out.println(startYear);
		System.out.println(endYear);
		System.out.println(capital);
		System.out.println(nationNames);
		
		//extract kings
		Node curr = startDiv.nextSibling();
		while (curr != endDiv) {
			if (curr instanceof TextNode) {
				String text = ((TextNode) curr).text().trim();
				if (startsWithNumber(text)) {
					String strArr[] = text.split("[.(]");
					kings.add(strArr[1].replaceAll("Chúa|Hoàng đế", "").trim());
				}
				else {
					description = description + text + " ";
				}
			}
			else if (curr instanceof Element && ( ((Element) curr).tagName().equals("b") || ((Element) curr).tagName().equals("i") )) {
				description = description + ((Element) curr).text() + " ";
			}
			curr = curr.nextSibling();
		}
		System.out.println(description);
		System.out.println(kings);
		System.out.println("--------");
		return new Era(eraName, startYear, endYear, description, capital, nationNames, kings);
	}
	
	private boolean startsWithNumber(String text) {
        Pattern pattern = Pattern.compile("^\\d+\\.");
        return pattern.matcher(text).find();
    }
	
	private List<PeriodEntity> crawlCapital() {
		String url = "https://quynhluu2.edu.vn/Giao-vien/DANH-SACH-CAC-KINH-DO-THU-DO-CUA-VIET-NAM-765.html";
		Document doc;
		List<PeriodEntity> eraCapitals = new ArrayList<>();
		
		try {
			doc = Jsoup.connect(url).get();
			Elements rows = doc.select("table tr");
			for (int i=1; i<rows.size(); i++) {
				Element row = rows.get(i);
				Elements cells = row.select("td");
				
				String capital= cells.get(0).text().trim();
				String startYear = "";
				String endYear = "";
				
				Element fontElement = cells.get(2).selectFirst("font");
				List<TextNode> textNodes = fontElement.textNodes();
				startYear = (textNodes.get(0).text().split("-"))[0].trim();
				if (startYear.contains("Thế kỷ")) {
					startYear = (textNodes.get(1).text().split("-"))[0].trim();
				}
				if (capital.equals("Phong Châu (Phú Thọ)")) {
					endYear = "258 TCN";
				}
				else {
					endYear = (textNodes.get(textNodes.size()-1).text().split("-"))[1].trim();
				}
				
				eraCapitals.add(new PeriodEntity(capital, convertYearStringToInt(startYear), convertYearStringToInt(endYear)));
			}
	        //2 adjacent eras with same capital name
			for (int i=0; i<eraCapitals.size()-1; i++) {
				PeriodEntity currCapital = eraCapitals.get(i);
				PeriodEntity nextCapital = eraCapitals.get(i+1);
				if (currCapital.name.equals(nextCapital.name) && currCapital.endYear == nextCapital.startYear) {
					currCapital.endYear = nextCapital.endYear;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return eraCapitals;
	}
	
	private List<PeriodEntity> crawlNationName() {
		Document doc;
		List<PeriodEntity> nationNames = new ArrayList<>();
		
		try {
			String url = URLDecoder.decode("https://vi.wikipedia.org/wiki/T%C3%AAn_g%E1%BB%8Di_Vi%E1%BB%87t_Nam", StandardCharsets.UTF_8.name());
			doc = Jsoup.connect(url).get();
			Elements rows = doc.select("div.mw-parser-output > table[cellspacing] table.collapsible tr");
			for (Element row : rows) {
				Elements cells = row.select("> td");
				if (cells.size() == 2) {
					String startYear, endYear;
					String nationName = cells.get(1).text();
					String strArr[] = cells.get(0).text().split("–");
					startYear = strArr[0];
					if (strArr.length == 1) {
						startYear = startYear.replace("từ ", "");
						endYear = "nay";
					}
					else {
						endYear = strArr[1];
					}
					if (endYear.contains("TCN")) {
						startYear += " TCN";
					}
					
					nationNames.add(new PeriodEntity(nationName, convertYearStringToInt(startYear), convertYearStringToInt(endYear)));
				}		
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return nationNames;
	}
	
	private int convertYearStringToInt(String yearStr) {
		int yearInt;
		if (yearStr.contains("TCN") || yearStr.contains("trCN")) {
			yearInt = -(Integer.parseInt(yearStr.replaceAll("[^\\d]", "")));
		}
		else if (yearStr.contains("nay")) {
			yearInt = Integer.MAX_VALUE - 1;
		}
		else {
			yearInt = Integer.parseInt(yearStr.replace("CN", "").trim());
		}
		return yearInt;
	}
	
	public static void main(String[] args) {
		EraCrawler eraCrawler = new EraCrawler();
		eraCrawler.crawl();
	}
}

//This class stores capital/nationName in each era.
class PeriodEntity{
	String name;
	int startYear;
	int endYear;
	
	public PeriodEntity(String name, int startYear, int endYear) {
		this.name = name;
		this.startYear = startYear;
		this.endYear = endYear;
	}
}



