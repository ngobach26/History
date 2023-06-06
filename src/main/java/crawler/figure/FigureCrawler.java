package crawler.figure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


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
//		figureIO.writeJson(crawlVanSu(), "src/main/resources/json/Figures.json");
		figureIO.writeJson(addDetails(), "src/main/resources/json/Figures.json");
	}
	
	public ArrayList<Figure> crawlVanSu() {
		String url = "https://vansu.vn/viet-nam/viet-nam-nhan-vat?page=";
		Document doc;
		ArrayList<Figure> figures = new ArrayList<>();
		
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
		            
		            ArrayList<String> eras = new ArrayList<>(); 
		            ArrayList<String> names = new ArrayList<>(); 
		            String bornYear = "Không rõ";
		            String diedYear = "Không rõ";
		            String location = cells.get(2).text();  
		            String role = "Không rõ";
		            String shortDescription = "Không rõ";
		            
		            //crawl figure's main name
		            names.add(cells.get(0).text());
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
		            			names.add(name.trim());
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

		            figures.add(new Figure(names, bornYear, diedYear, eras, location, role, shortDescription));	
		            
		         }
				 System.out.println("Complete page " + i);
				 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return figures;
	}
	
	public HashSet<String> getUniqueEras(){
		HashSet<String> eras = new HashSet<>();
		ArrayList<Figure> figures = figureIO.loadJson("src/main/resources/json/Figures.json");
		for (Figure figure : figures) {
			eras.addAll(figure.getEras().keySet());
		}
		return eras;
	}
	
	public ArrayList<Figure> addDetails(){
		ArrayList<Figure> figures = figureIO.loadJson("src/main/resources/json/Figures.json");
		
		//add details for each figure
		for (int i=1200;i<1500;i++) {
			HashMap<String, Integer> mother = new HashMap<>();
			HashMap<String, Integer> father = new HashMap<>();
			HashMap<String, Integer> children = new HashMap<>();
			HashMap<String, Integer> spouses = new HashMap<>();
			
			Figure figure = figures.get(i);
			String motherName ="", fatherName = "";
			for (String name : figure.getNames()) {
				motherName = GoogleCrawler.crawl("Mẹ của " + name);
				if (!motherName.equals("Không rõ")) {  
					mother.put(motherName, 0);
					break;
				}
			}
			for (String name : figure.getNames()) {
				fatherName = GoogleCrawler.crawl("Cha của " + name);
				if (!fatherName.equals("Không rõ")) {
					father.put(fatherName, 0);
					break;
				}
			}		
			ArrayList<String> childrenNames = new ArrayList<>();
			for (String name : figure.getNames()) {
				childrenNames = GoogleCrawler.advancedCrawl("Con của " + name);
				if (!childrenNames.isEmpty()) {
					for (String childName : childrenNames) {
						children.put(childName, 0);
					}
					break;
				}
			}
			ArrayList<String> spouseNames = new ArrayList<>();
			for (String name : figure.getNames()) {
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
	
	
	public static void main(String[] args) {
		FigureCrawler figureCrawler = new FigureCrawler();
		figureCrawler.crawl();
		System.out.println(figureCrawler.getUniqueEras());
	}
	
}
