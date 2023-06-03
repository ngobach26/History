package crawler.event;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.reflect.TypeToken;

import crawler.ICrawler;
import crawler.JsonIO;
import model.Event;

public class EventCrawler implements ICrawler{
	private JsonIO<Event> eventIO = new JsonIO<>(new TypeToken<ArrayList<Event>>() {}.getType());
	
	@Override
	public void crawl() {
		
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
					
					//crawl eventName
					Element header = eventDoc.selectFirst(".page-header h1");
					if (header != null) {
						eventName = header.text();
					}
					
					//crawl time, location
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
						}
					}
					
					System.out.println(eventName);
					System.out.println(time);
					System.out.println(location);
					System.out.println("--------------------------");
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return events;
	}
	
	public static void main(String[] args) {
		EventCrawler eventCrawler = new EventCrawler();
		eventCrawler.crawlNguoiKeSu();
	}
	
}
