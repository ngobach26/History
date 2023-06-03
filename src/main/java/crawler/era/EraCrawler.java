package crawler.era;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
			
			for (Element eraTitle : eraTitles) {
				String eraName = eraTitle.text();
				String startYear = "Không rõ";
				String endYear = "Không rõ";
				
				//extract startYear, endYear from eraName
				String stringArr[] = eraName.split("\\(");
				if (stringArr.length ==2) {
					stringArr = stringArr[1].replace(")", "").split("[-–]");
					startYear = stringArr[0].trim();
					endYear = stringArr[1].trim();
				}
				
				//eraNames normalization between Figures.json and Eras.json
				for (String newName : eraNames) {
					if (newName.contains(startYear) && newName.contains(endYear)) {
						eraName = newName;
						break;
					}
				}
				
				System.out.println(eraName);
				System.out.println(startYear);
				System.out.println(endYear);
				System.out.println("----------------");
				
				eras.add(new Era(eraName, startYear, endYear));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return eras;
	}
	
	
	
	public static void main(String[] args) {
		EraCrawler eraCrawler = new EraCrawler();
		eraCrawler.crawl();
	}
}


