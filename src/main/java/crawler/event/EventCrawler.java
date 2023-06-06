package crawler.event;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.reflect.TypeToken;

import crawler.ICrawler;
import helper.JsonIO;
import model.Event;

public class EventCrawler implements ICrawler{
	private JsonIO<Event> eventIO = new JsonIO<>(new TypeToken<ArrayList<Event>>() {}.getType());
	
	@Override
	public void crawl() {
		ArrayList<Event> events = new ArrayList<>();
		events = crawlNguoiKeSu();
		events.addAll(crawlWiki());
		eventIO.writeJson(events, "src/main/resources/json/Events.json");
	}
	
	public ArrayList<Event> crawlNguoiKeSu(){
		ArrayList<Event> events = new ArrayList<>();
		String url ="https://nguoikesu.com/tu-lieu/quan-su?start=";
		Document doc;
		
		//each page contains 5 events
		for (int i=0; i<= 70; i+=5) {
			try {
				doc = Jsoup.connect(url + i).get();
				Elements links = doc.select("h2 a[href]");
				//Each link is about an event
				for (Element link : links) {
					Document eventDoc = Jsoup.connect(link.attr("abs:href")).get();
					
					String eventName = "Không rõ";
					String time = "Không rõ";
					String location = "Không rõ";
					String result = "Không rõ";
					ArrayList<String> relatedFigures = new ArrayList<>();
					
					//crawl eventName
					Element header = eventDoc.selectFirst(".page-header h1");
					if (header != null) {
						eventName = header.text();
					}
					
					//crawl time, location, result
					Element infoTable = eventDoc.selectFirst("table[cellpadding=0]");
					if (infoTable != null) {
						Elements infoRows = infoTable.select("tr");
						for (Element inforRow : infoRows) {
							Elements infoCells = inforRow.select("td");
							if (infoCells.get(0).text().equals("Thời gian")) {
								time = infoCells.get(1).text();
							}
							if (infoCells.get(0).text().equals("Địa điểm")) {
								location = infoCells.get(1).text();
							}
							if (infoCells.get(0).text().equals("Kết quả")) {
								result = infoCells.get(1).text();
							}
						}
					}
					
					//add missing time
					if (time.equals("Không rõ")) {
						String strArr[] = eventName.split("năm|,");
						if (strArr.length == 2) {
							time = strArr[1].trim();
						}
					}
					
					
					//crawl related Figures
					Element articleBody = eventDoc.selectFirst("div.com-content-article__body");					
					Elements aTags = articleBody.select("a[href]");
		            for (Element a : aTags) {
		            	if (a.attr("href").contains("/nhan-vat") && !a.attr("href").contains("/nha-")) {
		            		String figure = a.text().trim();
		            		if (!relatedFigures.contains(figure)) {
		            			relatedFigures.add(figure);
		            		}
		            	}
		            }
					
		            
					
					System.out.println(eventName);
					System.out.println(time);
					System.out.println(location);
					System.out.println(result);
					System.out.println(relatedFigures);
					System.out.println("--------------------------");
					events.add(new Event(eventName, time, location, result, relatedFigures));
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return events;
	}
	
	public ArrayList<Event> crawlWiki(){
		ArrayList<Event> events = new ArrayList<>();
		Document doc;
		
		try {
			String url = URLDecoder.decode("https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam", StandardCharsets.UTF_8.name());
			doc = Jsoup.connect(url).get();
			
			Element content = doc.getElementById("mw-content-text");
			content.select("table[align=right]").remove();
			Elements pTags = content.select("p");
			for (int i=1; i<pTags.size(); i++) {				
				Element pTag = pTags.get(i);
				String year = pTag.select("b").text();
				
				Element nextElement = pTag.nextElementSibling();
				if (nextElement != null && nextElement.tagName().equals("dl")) {
					Elements ddElements = nextElement.select("dd");
					for (Element ddElement : ddElements) {
						String month = ddElement.select("b").text();
						String time = month + " " + year;
						ddElement.select("b").remove();
						String eventName = ddElement.text();
						
						System.out.println(time);
						System.out.println(eventName);
						System.out.println("---------------");
						events.add(new Event(eventName, time));
					}
				}
				else {
					String time = year;
					pTag.select("b").remove();
					String eventName = pTag.text();
					
					System.out.println(time);
					System.out.println(eventName);
					System.out.println("-------------------");
					events.add(new Event(eventName, time));
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return events;
	}
	
	public static void main(String[] args) {
		EventCrawler eventCrawler = new EventCrawler();
		eventCrawler.crawl();
	}
	
}
