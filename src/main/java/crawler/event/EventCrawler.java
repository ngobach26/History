package crawler.event;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.google.gson.reflect.TypeToken;

import crawler.ICrawler;
import crawler.JsonIO;
import model.Event;
import model.Figure;

public class EventCrawler implements ICrawler{
	private JsonIO<Event> eventIO = new JsonIO<>(new TypeToken<ArrayList<Event>>() {}.getType());
	
	@Override
	public void crawl() {
		List<Event> events = new ArrayList<>();
		events = crawlNguoiKeSu();
		events.addAll(crawlWiki());
		eventIO.writeJson(events, "src/main/resources/json/Events.json");
	}
	
	public List<Event> crawlNguoiKeSu(){
		List<Event> events = new ArrayList<>();
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
					String description = "Không rõ";
					List<String> relatedFigures = new ArrayList<>();
					
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
		            
		            //crawl description
		            Element overviewPara = articleBody.selectFirst("> p");
		            if (overviewPara != null) {
		            	overviewPara.select("sup").remove();
		            	description = (overviewPara.text().split("(?<=\\.)\\s+(?=[A-Z])"))[0];
		            }
				
		            
					
					System.out.println(eventName);
					System.out.println(time);
					System.out.println(location);
					System.out.println(result);
					System.out.println(relatedFigures);
					System.out.println(description);
					System.out.println("--------------------------");
					events.add(new Event(eventName, time, location, result, relatedFigures, description));
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return events;
	}
	
	public List<Event> crawlWiki(){
		List<Event> events = new ArrayList<>();
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
				//many events in a year
				if (nextElement != null && nextElement.tagName().equals("dl")) {
					Elements ddElements = nextElement.select("dd");
					for (Element ddElement : ddElements) {
						String month = ddElement.select("b").text();
						String time = month + " " + year;
						ddElement.select("b").remove();
						String eventName = ddElement.text();				
						
						//crawl more information
						Elements aTags = ddElement.select("a[href]");				
						
						events.add(addInformationWiki(aTags, eventName, time));
					}
				}
				//only one event in one year
				else {
					String time = year;
					pTag.select("b").remove();
					String eventName = pTag.text();
					
					//crawl more information
					Elements aTags = pTag.select("a[href]");
					
					events.add(addInformationWiki(aTags, eventName, time));
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
	
	private Event addInformationWiki(Elements aTags, String eventName, String time) {
		String location = "Không rõ";
		String result = "Không rõ";
		String description = "Không rõ";
		List<String> relatedFigures = new ArrayList<>();
		
		String link = "";
		for (Element aTag : aTags) {
			String aText = aTag.text();
			//only get relavant links with certain keywords
			if (aText.contains("Văn hóa") || aText.contains("Hòa ước") || aText.contains("Chiến tranh") ||
					aText.contains("Khởi nghĩa") || aText.contains("khởi nghĩa") || aText.contains("Trận") ||
					aText.contains("Loạn") || aText.contains("Hội nghị") || aText.contains("Nổi dậy") || 
					aText.contains("Phong trào") || aText.contains("Cách mạng") || aText.contains("Chiến dịch") || 
					aText.contains("chiến tranh") || aText.contains("Xô Viết Nghệ Tĩnh") || aText.contains("Đổi Mới")) {
				try {
					//only get active links
					if (aTag.attr("href").contains("/wiki")) {
						link = URLDecoder.decode(aTag.attr("abs:href"), StandardCharsets.UTF_8.name());
					}	
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		
		if (!link.equals("")) {
			//connect to detail page of the event
			Document eventDoc;
			try {
				eventDoc = Jsoup.connect(link).get();
				String paragraph = eventDoc.select("div.mw-parser-output > p").text();
				if (paragraph.isBlank()) {
					paragraph = eventDoc.select("div.mw-parser-output > p:nth-child(2)").text();
				}
				//get the first sentence
				description = (paragraph.split("(?<=\\.)\\s+(?=[A-Z])"))[0];
				Element infoTable = eventDoc.selectFirst("div.mw-parser-output table.infobox tr table");
				if (infoTable != null) {
					Elements infoRows = infoTable.select("tr");
					for (Element inforRow : infoRows) {
					    Element infoHeading = inforRow.selectFirst("> th");
					    Element infoData = inforRow.selectFirst("> td");
					    if (infoHeading != null && infoHeading.text().equals("Địa điểm")) {
					        location = infoData.text();
					    }
					    if (infoHeading != null && infoHeading.text().equals("Kết quả")) {
					        result = infoData.text();
					    }
					}
				}
				
				Elements rows = eventDoc.select("div.mw-parser-output table.infobox > tbody > tr");
				for (Element row : rows) {
					if (row.text().equals("Chỉ huy và lãnh đạo")) {
						row = row.nextElementSibling();
						Elements figureLinks = row.select("td > a[href]");
						for (Element figureLink : figureLinks) {
							if (figureLink.attr("title").equals("Tử trận")) {
								continue;
							}
							relatedFigures.add(figureLink.text());
						}
						break;
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(time);
		System.out.println(eventName);
		System.out.println(location);
		System.out.println(result);
		System.out.println(description);
		System.out.println(relatedFigures);
		System.out.println("---------------");
		
		return new Event(eventName, time, location, result, relatedFigures, description);
						
	}
	public static void main(String[] args) {
		EventCrawler eventCrawler = new EventCrawler();
		eventCrawler.crawl();
	}
	
}
