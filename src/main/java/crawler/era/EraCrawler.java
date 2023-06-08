package crawler.era;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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
	
	
	public ArrayList<Era> crawlVanSu(){
		ArrayList<Era> eras = new ArrayList<>();
		String url = "https://vansu.vn/viet-nam/nien-bieu-lich-su";
		HashSet<String> eraNames = new FigureCrawler().getUniqueEras();
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
		ArrayList<String> kings = new ArrayList<>();
		
		//extract startYear, endYear from eraName
		String stringArr[] = eraName.split("\\(");
		if (stringArr.length ==2) {
			stringArr = stringArr[1].replace(")", "").split("[-–]");
			startYear = stringArr[0].trim();
			endYear = stringArr[1].trim();
		}
		
		//eraNames standardization between Figures.json and Eras.json
		HashSet<String> eraNames = new FigureCrawler().getUniqueEras();
		for (String newName : eraNames) {
			if (newName.contains(startYear) && newName.contains(endYear)) {
				eraName = newName;
				break;
			}
		}
		
		System.out.println(eraName);
		System.out.println(startYear);
		System.out.println(endYear);
		
		//extract kings
		Node curr = startDiv.nextSibling();
		while (curr != endDiv) {
			if (curr instanceof TextNode) {
				String text = ((TextNode) curr).text().trim();
				if (startsWithNumber(text)) {
					String strArr[] = text.split("[.(]");
					kings.add(strArr[1].trim());
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
		return new Era(eraName, startYear, endYear, description, kings);
	}
	
	private boolean startsWithNumber(String text) {
        Pattern pattern = Pattern.compile("^\\d+\\.");
        return pattern.matcher(text).find();
    }
	
	public static void main(String[] args) {
		EraCrawler eraCrawler = new EraCrawler();
		eraCrawler.crawl();
	}
}


