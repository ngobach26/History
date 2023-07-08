package crawler.event;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.google.gson.reflect.TypeToken;

import crawler.ICrawler;
import helper.JsonIO;
import helper.StringHelper;
import model.Event;
import model.Figure;

public class EventCrawler implements ICrawler{
	private static final JsonIO<Event> EVENT_IO = new JsonIO<>(new TypeToken<ArrayList<Event>>() {}.getType());
	private static final String PATH = "src/main/resources/json/Events.json";
	
	@Override
	public void crawl() {
		List<Event> tvls = crawlThuVienLichSu();
		List<Event> wiki = crawlWiki();
		List<Event> nks = crawlNguoiKeSu();
		EVENT_IO.writeJson(merge(merge(tvls, wiki), nks), PATH);
	}
	
	private List<Event> crawlNguoiKeSu(){
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
					try {
						Document eventDoc = Jsoup.connect(link.attr("abs:href")).get();
						
						String eventName = "Không rõ";
						String time = "Không rõ";
						String startYear = "Không rõ";
						String endYear = "Không rõ";
						String location = "Không rõ";
						String result = "Không rõ";
						String description = "Không rõ";
						List<String> relatedFigures = new ArrayList<>();
						
						//crawl eventName
						Element header = eventDoc.selectFirst(".page-header h1");
						if (header != null) {
							eventName = header.text();
						}
						String strArr[] = eventName.split("năm|,");
						if (strArr.length == 2) {
							time = strArr[1].trim();
						}
						
						//crawl startYear,endYear, location, result
						Element infoTable = eventDoc.selectFirst("table[cellpadding=0]");
						if (infoTable != null) {
							Elements infoRows = infoTable.select("tr");
							for (Element inforRow : infoRows) {
								Elements infoCells = inforRow.select("td");
								if (infoCells.get(0).text().equals("Thời gian") && time.equals("Không rõ")) {
									time = infoCells.get(1).text().replaceAll("năm|Năm", "").trim();
								}
								if (infoCells.get(0).text().equals("Địa điểm")) {
									location = infoCells.get(1).text();
								}
								if (infoCells.get(0).text().equals("Kết quả")) {
									result = infoCells.get(1).text();
								}
							}
						}				
						
						String timeArr[] = time.split("[-–]");
						if (timeArr.length == 1) {
							startYear = time.replaceAll("\\d*\\s*tháng\\s*\\d+", "").trim();
							endYear = startYear;
						}
						else {
							startYear = timeArr[0].replaceAll("\\d*\\s*tháng\\s*\\d+", "").trim();
							endYear = timeArr[1].replaceAll("\\d*\\s*tháng\\s*\\d+", "").trim();
							if (startYear.equals("")) {
								startYear = endYear;
							}
						}
						
						//crawl related Figures
						Element articleBody = eventDoc.selectFirst("div.com-content-article__body");					
						Elements aTags = articleBody.select("a[href]");
			            for (Element a : aTags) {
			            	if (a.attr("href").contains("/nhan-vat") && !a.attr("href").contains("/nha-")) {
			            		String figure = a.text().trim();
			            		if (!StringHelper.containString(relatedFigures, figure)) {
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
						System.out.println(startYear);
						System.out.println(endYear);
						System.out.println(location);
						System.out.println(result);
						System.out.println(relatedFigures);
						System.out.println(description);
						System.out.println("--------------------------");
						events.add(new Event(eventName, startYear, endYear, location, result, relatedFigures, description));
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return events;
	}
	
	private List<Event> crawlWiki(){
		List<Event> events = new ArrayList<>();
		Document doc;
		
		try {
			String url = URLDecoder.decode("https://vi.wikipedia.org/wiki/Ni%C3%AAn_bi%E1%BB%83u_l%E1%BB%8Bch_s%E1%BB%AD_Vi%E1%BB%87t_Nam", 
					StandardCharsets.UTF_8.name());
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
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
		
		return events;
	}
	
	private Event addInformationWiki(Elements aTags, String eventName, String time) {
		String startYear = "Không rõ";
		String endYear = "Không rõ";
		String location = "Không rõ";
		String result = "Không rõ";
		String description = "Không rõ";
		List<String> relatedFigures = new ArrayList<>();
		
		//crawl startYear, endYear
		String timeArr[] = time.split("[-–]");
		if (timeArr.length == 1) {
			if (time.equals("Thế kỉ VII TCN")) {
				time = "-700";
			}
			startYear = time.replace(".", "").replaceAll("\\d*\\s*tháng\\s*\\d+", "").trim();
			endYear = startYear;
		}
		else {
			startYear = timeArr[0].replace(".", "").replaceAll("\\d*\\s*tháng\\s*\\d+", "").trim();
			endYear = timeArr[1].replace(".", "").replaceAll("\\d*\\s*tháng\\s*\\d+", "").trim();
			if (startYear.equals("")) {
				startYear = endYear;
			}
		}
		if (startYear.contains("TCN")) {
			startYear = "-" + startYear.replaceAll("[^\\d]", "");
		}
		if (endYear.contains("TCN")) {
			endYear = "-" + endYear.replaceAll("[^\\d]", "");
		}
		
		//get link to crawl location, result, description, relatedFigures
		String link = "";
		for (Element aTag : aTags) {
			String aText = aTag.text();
			//only get relavant links with certain keywords			
			if (StringHelper.containsSubstrings(aText, "Văn hóa", "Hòa ước", "Chiến tranh", "Khởi nghĩa", "Trận", "Loạn",
		            "Hội nghị", "Nổi dậy", "Phong trào", "Cách mạng", "Chiến dịch", "Chiến tranh",  
		            "Xô Viết Nghệ Tĩnh", "Đổi Mới")) {
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
				//crawl description, location, result
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
				
				//crawl relatedFigures
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
		System.out.println(startYear);
		System.out.println(endYear);
		System.out.println(eventName);
		System.out.println(location);
		System.out.println(result);
		System.out.println(description);
		System.out.println(relatedFigures);
		System.out.println("---------------");
		
		return new Event(eventName, startYear, endYear, location, result, relatedFigures, description);
						
	}
	
	private List<Event> crawlThuVienLichSu() {
		List<Event> events = new ArrayList<>();
		String url ="https://thuvienlichsu.vn/su-kien?page=";
		Document doc;
		
		//crawl 19 pages
		for (int i=1; i<=19; i++) {
			try {
				doc = Jsoup.connect(url + i).get();
				Elements aTags = doc.select("div.card a.click");
				for (Element aTag : aTags) {
					String link = aTag.attr("abs:href");
					try {
						Document linkDoc = Jsoup.connect(link).get();
						
						String eventName = "Không rõ";
						String startYear = "Không rõ";
						String endYear = "Không rõ";
						String time = "Không rõ";
						String location = "Không rõ";
						String result = "Không rõ";
						String description = "Không rõ";
						List<String> relatedFigures = new ArrayList<>();
						
						//extract eventName, startYear, endYear
						String title = linkDoc.selectFirst("div.divide-tag").text();
						eventName = title;
						Pattern pattern = Pattern.compile("\\(([^)]+)\\)");
			            Matcher matcher = pattern.matcher(title);
			            while (matcher.find()) {
			            	if (StringHelper.containsNumber(matcher.group())) {
			            		time = matcher.group(1).trim();
			            		List<Integer> dashIndexes = new ArrayList<>();
				            	for (int j = 0; j < time.length(); j++) {
				                    if (time.charAt(j) == '-') {
				                        dashIndexes.add(j);
				                    }
				                }
				            	if (dashIndexes.isEmpty()) {
				            		startYear = time;
				            		endYear = time;
				            	}
				            	else if (dashIndexes.size() == 1) {
				            		if (dashIndexes.get(0) == 0) {
					            		if (time.charAt(1) == ' ') {
					            			startYear = time.substring(1).trim();
					            			endYear = startYear;
					            		}
					            		else {
					            			startYear = time;
					            			endYear = time;
					            		}
					            	}
					            	else {
					            		startYear = time.substring(0, dashIndexes.get(0)).trim();
					            		endYear = time.substring(dashIndexes.get(0) + 1, time.length()).trim();
					            	}
				            	}
				            	else {
				            		if (dashIndexes.get(0) == 0) {
					            		startYear = time.substring(0, dashIndexes.get(1)).trim();
				            			endYear = time.substring(dashIndexes.get(1) + 1, time.length()).trim();
					            	}
					            	else {
					            		startYear = time.substring(0, dashIndexes.get(0)).trim();
					            		endYear = time.substring(dashIndexes.get(0) + 1, time.length()).trim();
					            	}
				            	}			            	
		
				            	eventName = eventName.replace(matcher.group(), "").trim();
				            	
				            	if (eventName.contains("Giải phóng Phan Thiết")) {
				            		startYear = "1975";
				            		endYear = "1975";
				            	}
				            	break;
			            	}		            	
			            }
			            
			            //crawl location, description, relatedFigures
			            Elements cardElements = linkDoc.select("div.divide-tag");
			            for (int j=1; j<cardElements.size(); j++) {
			            	Element cardElement = cardElements.get(j);
			            	String cardTitle = cardElement.selectFirst("div.divide-line h3.header-edge").text().trim();
			            	if (cardTitle.equals("Diễn biễn lịch sử")) {          		
			            		description = cardElement.selectFirst("div.card-body").text();
			            	}
			            	else if (cardTitle.equals("Địa điểm liên quan")) {
			            		location = "";
			            		Elements locationElements = cardElement.select("div.card a.click");
			            		for (int k = 0; k < locationElements.size() - 1; k++) {
			            			location = location + locationElements.get(k).text() + ", ";
			            		}
			            		location = location + locationElements.get(locationElements.size() - 1).text();
			            	}
			            	else if (cardTitle.equals("Nhân vật liên quan")) {
			            		Elements relatedFigureCards = cardElement.select("div.card div.card");
			            		for (Element relatedFigureCard : relatedFigureCards) {
			            			String rawText = relatedFigureCard.selectFirst("a.click").text();
			            			matcher = pattern.matcher(rawText);
			            			if (matcher.find()) {
			            				relatedFigures.add(rawText.replace(matcher.group(), "").replace("Chủ tịch", "").trim());
			            			}
			            		}
			            	}
			            }

			            
			            System.out.println(eventName);
			            System.out.println(time);
			            System.out.println(startYear);
			            System.out.println(endYear);
			            System.out.println(description);
			            System.out.println(location);
			            System.out.println(relatedFigures);
			            System.out.println("--------------");
			            
			            events.add(new Event(eventName, startYear, endYear, location, result, relatedFigures, description));
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		return events;
	}
	
	private List<Event> merge(List<Event> list1, List<Event> list2) {
		//list 2 is added to base list1
		int sizeList1 = list1.size();
		for (Event event2 : list2) {
			boolean isFound = false;
			
			String startYear2 = event2.getStartYear();
			String endYear2 = event2.getEndYear();
			String eventName2 = event2.getName().replaceAll("[-–]", " ").replace("được", "").replaceAll("\\s+", " ");
			
			for (int i = 0; i<sizeList1; i++) {
				Event event1 = list1.get(i);
				String eventName1 = event1.getName().replaceAll("[-–]", " ").replaceAll("\\s+", " ").replace("được", "");
				String startYear1 = event1.getStartYear();
				String endYear1 = event1.getEndYear();
				
				if ((StringHelper.containsSubstrings(eventName2, eventName1) || StringHelper.containsSubstrings(eventName1, eventName2))
						&& (startYear1.equals(startYear2) || endYear1.equals(endYear2))) {
					isFound = true;
				}
				else if (startYear1.equals(startYear2) && endYear1.equals(endYear2)) {			
					if (StringHelper.containsSubstrings(eventName2, "lên ngôi", "xưng đế")
						&& StringHelper.containsSubstrings(eventName1, "lên ngôi", "xưng đế")) {
						isFound = true;
					}
					else if (StringHelper.containsSubstrings(eventName2, "dời đô")
							&& StringHelper.containsSubstrings(eventName1, "dời đô")) {
						isFound = true;
					}
					else if (StringHelper.containsSubstrings(eventName2, "đổi tên")
							&& StringHelper.containsSubstrings(eventName1, "đổi tên")) {
						isFound = true;
					}
					else if (StringHelper.containsSubstrings(eventName2, "qua đời")
							&& StringHelper.containsSubstrings(eventName1, "qua đời")) {
						isFound = true;
					}
					else if (StringHelper.containsSubstrings(eventName2, "tạm ước", "hòa ước", "hiệp ước")
							&& StringHelper.containsSubstrings(eventName1, "tạm ước", "hòa ước", "hiệp ước")) {
						isFound = true;
					}
					else if (StringHelper.containsSubstrings(eventName2, "tuyển cử")
							&& StringHelper.containsSubstrings(eventName1, "tuyển cử")) {
						isFound = true;
					}
					else if (StringHelper.containsSubstrings(eventName2, "hội nghị", "hiệp định")
							&& StringHelper.containsSubstrings(eventName1, "hội nghị", "hiệp định")) {
						isFound = true;
					}
					else if ((StringHelper.containsSubstrings(eventName2, "trận", "chiến thắng", "chiến tranh", "chiến dịch", "phân tranh")
							&& StringHelper.containsSubstrings(eventName1, "trận", "chiến thắng", "chiến tranh", "chiến dịch", "phân tranh"))
							|| (StringHelper.containsSubstrings(eventName2, "khởi nghĩa")
							&& StringHelper.containsSubstrings(eventName1, "khởi nghĩa")) 
							|| (StringHelper.containsSubstrings(eventName2, "thành lập", "lập ra", "lập lên")
							&& StringHelper.containsSubstrings(eventName1, "thành lập", "lập ra", "lập lên"))) {
						if (!startYear1.equals("Không rõ") && Integer.parseInt(startYear1) < 1860) {
							isFound = true;
						}
						//from 1860 to now, there are many events happening in the same year
						else {
							 Pattern regex = Pattern.compile("(\\p{Lu}\\p{Ll}*\\s*){1,}(\\p{Lu}\\p{Ll}*)");
						     Matcher matcher1 = regex.matcher(eventName1.replaceAll("(?i)(trận|chiến thắng|chiến tranh|chiến dịch|khởi nghĩa)", ""));
						     Matcher matcher2 = regex.matcher(eventName2.replaceAll("(?i)(trận|chiến thắng|chiến tranh|chiến dịch|khởi nghĩa)", ""));
						     //compare personal names of event names
						     if ((matcher1.find() && matcher2.find()) && (matcher1.group().equalsIgnoreCase(matcher2.group()))) {
						    	 isFound = true;
						     }
						}
					}					
				}	
				
				
				if (isFound) {
					System.out.println("Updated: " + eventName1);
					System.out.println("Removed: " + eventName2);
					System.out.println("------------------");
					Set<String> newFigures = event2.getRelatedFigures().keySet();
					for (String newFigure : newFigures) {
						event1.addRelatedFigures(newFigure, 0);
					}
				
					if (event1.getLocation().equals("Không rõ")) {
						event1.setLocation(event2.getLocation());
					}
					if (event1.getResult().equals("Không rõ")) {
						event1.setResult(event2.getResult());
					}
					if (event1.getDescription().equals("Không rõ")) {
						event1.setDescription(event2.getDescription());
					}
					break;
				}
			}
			
			if (!isFound) {
				list1.add(event2);
			}
		}
		
		return list1;
	}
	
	
}
