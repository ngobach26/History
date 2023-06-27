package crawler.figure;

import java.io.IOException;
import java.util.ArrayList;
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
import org.jsoup.select.Elements;

import com.google.gson.reflect.TypeToken;

import crawler.GoogleCrawler;
import crawler.ICrawler;
import crawler.JsonIO;
import model.Figure;

public class FigureCrawler implements ICrawler{
	private JsonIO<Figure> figureIO = new JsonIO<>(new TypeToken<ArrayList<Figure>>() {}.getType());
	
	@Override
	public void crawl() {
		figureIO.writeJson(merge(), "src/main/resources/json/Figures.json");
//		figureIO.writeJson(crawlThuVienLichSu(), "src/main/resources/json/FiguresTVLS.json");
//		figureIO.writeJson(addDetails(), "src/main/resources/json/Figures.json");
	}
	
	public List<Figure> crawlVanSu() {
		String url = "https://vansu.vn/viet-nam/viet-nam-nhan-vat?page=";
		Document doc;
		List<Figure> figures = new ArrayList<>();
		
		//crawl 120 pages
		for (int i=0; i<120; i++) {
			try {
				doc = Jsoup.connect(url + i).get();
				Element table = doc.selectFirst("table");
				Elements rows = table.select("tr");
				
				//each row in table corresponds to a figure
				for (int j = 1; j < rows.size() - 1; j++) {
		            Element row = rows.get(j); 
		            Elements cells = row.select("td");
		            
		            List<String> eras = new ArrayList<>(); 
		            List<String> otherNames = new ArrayList<>(); 
		            String mainName = "Không rõ";
		            String bornYear = "Không rõ";
		            String diedYear = "Không rõ";
		            String location = cells.get(2).text();  
		            String role = "Không rõ";
		            String shortDescription = "Không rõ";
		            
		            //crawl figure's main name
		            mainName = cells.get(0).text();
		            String eraArr[] = cells.get(1).html().split("<br>");
		            for (int k=0; k<eraArr.length; k++) {
		            	eras.add(eraArr[k].trim().substring(2));
		            }
		            
		            //crawl further information
		            String link = cells.get(0).select("a[href]").attr("abs:href");
		            Document linkDoc = Jsoup.connect(link).get();
		            Elements linkDocRows = linkDoc.select("tr");	//each row corresponds to a field of info	            
		            for (Element linkDocRow : linkDocRows) {
		            	//crawl bornyear and diedyear
		            	if (linkDocRow.selectFirst("td").text().equals("Năm sinh")) {
		            		String yearsString[] = linkDocRow.select("td").get(1).text().split("-", 2);
		            		yearsString[0] = yearsString[0].trim();
		            		yearsString[1] = yearsString[1].trim();
		            		if (!yearsString[0].contains(".") && !yearsString[0]. contains("…") && !yearsString[0].equals("")) {
		                        bornYear = yearsString[0];
		                    }
		                    if (!yearsString[1].contains(".") && !yearsString[1]. contains("…") && !yearsString[1].equals("")) {
		                        diedYear = yearsString[1];
		                    }
		            	}
		            	//crawl other names
		            	else if (linkDocRow.selectFirst("td").text().equals("Tên khác")) {
		            		String nameArr[] = linkDocRow.select("td").get(1).text().split("-");
		            		for (String name : nameArr) {
		            			otherNames.add(name.trim());
		            		}
		            	}
		            }
		            
		            Element lastLinkDocRow = linkDocRows.get(linkDocRows.size() - 1);	//last row corresponds to the description
		            Element iElement = lastLinkDocRow.selectFirst("i");
		            //crawl figure's role
		            if (iElement != null) {
		               role = iElement.text();
		            }
		            
		            //crawl short description
		            shortDescription = lastLinkDocRow.selectFirst("p").text();

		            figures.add(new Figure(mainName, otherNames, bornYear, diedYear, eras, location, role, shortDescription));	
		            
		         }
				 System.out.println("Complete page " + i);
				 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return figures;
	}
	
	public Set<String> getUniqueEras(){
		Set<String> eras = new HashSet<>();
		List<Figure> figures = figureIO.loadJson("src/main/resources/json/Figures.json");
		for (Figure figure : figures) {
			eras.addAll(figure.getEras().keySet());
		}
		return eras;
	}
	
	public List<Figure> addDetails(){
		List<Figure> figures = figureIO.loadJson("src/main/resources/json/Figures.json");
		
		//add details for each figure
		for (int i=1;i<20;i++) {
			Map<String, Integer> mother = new HashMap<>();
			Map<String, Integer> father = new HashMap<>();
			Map<String, Integer> children = new HashMap<>();
			Map<String, Integer> spouses = new HashMap<>();
			
			Figure figure = figures.get(i);
			List<String> nameList = new ArrayList<>();
			nameList.add(figure.getName());
			for (String otherName : figure.getOtherNames()) {
				nameList.add(otherName);
			}
			
			String motherName, fatherName;
			for (String name : nameList) {
				motherName = GoogleCrawler.crawl("Mẹ của " + name);
				if (!motherName.equals("Không rõ")) {  
					mother.put(motherName, 0);
					break;
				}
			}
			for (String name : nameList) {
				fatherName = GoogleCrawler.crawl("Cha của " + name);
				if (!fatherName.equals("Không rõ")) {
					father.put(fatherName, 0);
					break;
				}
			}		
			List<String> childrenNames;
			for (String name : nameList) {
				childrenNames = GoogleCrawler.advancedCrawl("Con của " + name);
				if (!childrenNames.isEmpty()) {
					for (String childName : childrenNames) {
						children.put(childName, 0);
					}
					break;
				}
			}
			List<String> spouseNames;
			for (String name : nameList) {
				spouseNames = GoogleCrawler.advancedCrawl("Vợ của " + name);
				if (!spouseNames.isEmpty()) {
					for (String spouseName : spouseNames) {
						spouses.put(spouseName, 0);
					}
					break;
				}
			}
			//update figure
			figure.setMother(mother);
			figure.setFather(father);
			figure.setChildren(children);
			figure.setSpouses(spouses);
			
			//wait 9s before adding details to next figure	
			try {
				Thread.sleep(9000);
				System.out.println("Complete figure " + i + " th.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return figures;
	}
	
	public Set<String> getUniqueSurnames() {
		Set<String> surnames = new HashSet<>();
		List<Figure> figures = figureIO.loadJson("src/main/resources/json/Figures.json");
		for (Figure figure : figures) {
			surnames.add((figure.getName().split("\\s+"))[0]);
//			for (String otherName : figure.getOtherNames()) {
//				surnames.add((otherName.split("\\s+"))[0]);
//			}
		}
		return surnames;
	}
	
	public List<Figure> crawlThuVienLichSu() {
		String url = "https://thuvienlichsu.vn/nhan-vat?page=";
		Document doc;
		List<Figure> figures = new ArrayList<>();
		
		//crawl 41 pages
		for (int i=1; i<=41; i++) {
			try {
				doc = Jsoup.connect(url+i).get();
				Elements aTags = doc.select("div.card a.click");
				for (Element aTag : aTags) {
					String link = aTag.attr("abs:href");
					Document linkDoc = Jsoup.connect(link).get();
					
					List<String> eras = new ArrayList<>(); 
		            List<String> otherNames = new ArrayList<>(); 
		            String mainName = "Không rõ";
		            String bornYear = "Không rõ";
		            String diedYear = "Không rõ";
		            String location = "Không rõ";  
		            String role = "Không rõ";
		            String description = "Không rõ";		            
		            
		            //extract name, bornYear, diedYear
		            String title = linkDoc.selectFirst("div.divide-tag").text();
		            mainName = title;	            
		            Pattern pattern = Pattern.compile("\\(([^)]+)\\)");
		            Matcher matcher = pattern.matcher(title);
		            if (matcher.find()) {
		            	String timeText = matcher.group(1).trim();
		            	mainName = mainName.replace(matcher.group(), "").trim();
		            	List<Integer> dashIndexes = new ArrayList<>();
		            	for (int j = 0; j < timeText.length(); j++) {
		                    if (timeText.charAt(j) == '-') {
		                        dashIndexes.add(j);
		                    }
		                }		            	
		            	if (dashIndexes.get(0) == 0) {
		            		bornYear = timeText.substring(0, dashIndexes.get(1)).trim();
	            			diedYear = timeText.substring(dashIndexes.get(1) + 1, timeText.length()).trim();
		            	}
		            	else {
		            		bornYear = timeText.substring(0, dashIndexes.get(0)).trim();
		            		diedYear = timeText.substring(dashIndexes.get(0) + 1, timeText.length()).trim();
		            	}
		            	if (bornYear.equals("?")) {
		    				bornYear = "Không rõ";
		    			}
		    			if (diedYear.equals("?")) {
		    				diedYear = "Không rõ";
		    			} 
		            	System.out.println(timeText);
		            }
		            
//					Element relatedEventsTable = linkDoc.select("div.divide-tag").get(1).selectFirst("table");
					
					
					Element descriptionPara = linkDoc.select("div.divide-tag").get(2).selectFirst("div.card-body > p.card-text");
					description = descriptionPara.text();
					
					//extract otherNames
					pattern = Pattern.compile("(còn được gọi là|tước hiệu|tên thật là|truy phong là|ban tước|ca ngợi là|còn gọi là|gọi tôn là|tên húy là|tên khác là|niên hiệu là|được phong là|được tôn là|hiệu là|người đời còn gọi là|tự xưng là|hay còn gọi là|hay|đặt tên mình là|kiểm hiệu|có tên là|tức là|được đặc phong làm|thường gọi ông là|đặt hiệu là|dâng tên thụy là|lấy vị hiệu là|đã phong bà là|đã phong ông là|lấy hiệu là|truy tôn là|phong thành|lại phong|hưởng tước|phục chức|cung kính gọi là|đổi niên hiệu|tự là|xưng là|thuỵ là|thuỵ hiệu là|phong thụy cho ông là|được biết đến với tên gọi|Tên thật của ông là|bút danh là|bút hiệu|tự|tên thuở nhỏ là|thường gọi là)(\\s+)(\\p{Lu}\\p{Ll}*\\s+){1,}(\\p{Lu}\\p{Ll}*)");
					matcher = pattern.matcher(description);
					String otherName;
					while (matcher.find()) {
						otherName = matcher.group().replaceAll("còn được gọi là|tước hiệu|tên thật là|truy phong là|ban tước|ca ngợi là|còn gọi là|gọi tôn là|tên húy là|tên khác là|niên hiệu là|được phong là|được tôn là|hiệu là|người đời còn gọi là|tự xưng là|hay còn gọi là|hay|đặt tên mình là|kiểm hiệu|có tên là|tức là|được đặc phong làm|thường gọi ông là|đặt hiệu là|dâng tên thụy là|lấy vị hiệu là|đã phong bà là|đã phong ông là|lấy hiệu là|truy tôn là|phong thành|lại phong|hưởng tước|phục chức|cung kính gọi là|đổi niên hiệu|tự là|xưng là|thuỵ là|thuỵ hiệu là|phong thụy cho ông là|được biết đến với tên gọi|Tên thật của ông là|bút danh là|bút hiệu|tự|tên thuở nhỏ là|thường gọi là", "").trim();
						if (!otherName.equals(mainName) && !otherNames.contains(otherName)) {
							otherNames.add(otherName);
						}
					}
								
					
					System.out.println(mainName);
					System.out.println(otherNames);
					System.out.println(bornYear);
					System.out.println(diedYear);
					System.out.println(description);
					System.out.println("----------");
					
					figures.add(new Figure(mainName, otherNames, bornYear, diedYear, eras, location, role, description));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//add location
		url = "https://thuvienlichsu.vn/nhan-vat/theo-dia-diem-quan-huyen-tinh-thanh";
		try {
			doc = Jsoup.connect(url).get();
			Elements locationDivs = doc.select("div.container > div.row div.divide-content");
			for (Element locationDiv : locationDivs) {
				String location = locationDiv.selectFirst("div.divide-line").text().trim();
				System.out.println(location);
				String link = locationDiv.selectFirst("div.watch-more a").attr("abs:href");
				Document linkDoc = Jsoup.connect(link).get();
				
				//more links at the end of the page
				Elements linkElements = linkDoc.select("ul.pagination li a");
				for (Element linkElement : linkElements) {				
					if (Character.isDigit(linkElement.text().trim().charAt(0))) {
						link = linkElement.attr("abs:href");
						linkDoc = Jsoup.connect(link).get();
						
						Elements figureDivs = linkDoc.select("div.container > div.row div.divide-content");
						for (Element figureDiv : figureDivs) {
							String mainName = figureDiv.selectFirst("div.card-body a.click").text();	            
				            Pattern pattern = Pattern.compile("\\(([^)]+)\\)");
				            Matcher matcher = pattern.matcher(mainName);
				            if (matcher.find()) {
				            	mainName = mainName.replace(matcher.group(), "").trim();
				            }
				            System.out.println(mainName);	
				            
				            //add location
				            for (Figure figure : figures) {
				            	if (mainName.equals(figure.getName())) {
				            		figure.setLocation(location);
				            	}
				            }
						}
					}
				}
				
				System.out.println("-------------");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return figures;
	}
	
	
	public List<Figure> merge() {
		List<Figure> figuresVS = crawlVanSu();
		List<Figure> figuresTVLS = crawlThuVienLichSu();
		
		for (Figure figureTVLS : figuresTVLS) {
			boolean isFound = false;
			
			for (Figure figureVS : figuresVS) {
				List<String> updatedOtherNames = new ArrayList<>(figureVS.getOtherNames());
				for (String figureTVLSName : figureTVLS.getAllNames()) {
					if (figureVS.containsName(figureTVLSName)) {
						isFound = true;
					}
					if (!updatedOtherNames.contains(figureTVLSName)) {
						updatedOtherNames.add(figureTVLSName);
					}
				}
				//if found, just updated other names
				if (isFound) {					
					//remove main name in other names
					updatedOtherNames.remove(figureVS.getName());
					figureVS.setOtherNames(updatedOtherNames);
					break;
				}
			}
			
			//if not found, add new figure to the list
			if (!isFound) {
				figuresVS.add(figureTVLS);
				System.out.println(figureTVLS.getName());
				System.out.println(figureTVLS.getOtherNames());
			}
		}
		
		return figuresVS;
		
	}
	
	public static void main(String[] args) {
		FigureCrawler figureCrawler = new FigureCrawler();
		figureCrawler.crawl();
//	    figureCrawler.crawlThuVienLichSu();
//		figureCrawler.merge();
	}
	
}
