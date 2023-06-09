package crawler.relic;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.reflect.TypeToken;

import crawler.ICrawler;
import crawler.figure.FigureCrawler;
import helper.JsonIO;
import helper.StringHelper;
import model.Relic;

public class RelicCrawler implements ICrawler {
    private static final JsonIO<Relic> RELIC_IO = new JsonIO<>(new TypeToken<ArrayList<Relic>>() {}.getType());
    private static final String PATH = "src/main/resources/json/Relics.json";

    @Override
    public void crawl() {
		RELIC_IO.writeJson(crawlWiki(), PATH);
    }
    
    private List<Relic> crawlWiki() {
        Document doc;
        List<Relic> relics = new ArrayList<>();
        
        try {
        	String url = URLDecoder.decode("https://vi.wikipedia.org/wiki/Danh_s%C3%A1ch_Di_t%C3%ADch_qu%E1%BB%91c_gia_Vi%E1%BB%87t_Nam", 
        			StandardCharsets.UTF_8.name());
            doc = Jsoup.connect(url).get();
            Elements tables = doc.select("table.wikitable");
            Element tableNinhBinh = doc.selectFirst("#mw-content-text > div.mw-parser-output > table:nth-child(55)");
            Set<String> surnames = new FigureCrawler().getUniqueSurnames();

            for (Element table : tables) {
            	//table Ninh Binh has different format
                if (table == tableNinhBinh) {
                    Elements rows = table.select("tr");
                    for (int i = 1; i < rows.size(); i++) {
                        Elements cells = rows.get(i).select("td");

                        String name = cells.get(1).text();
                        String location = cells.get(2).text();
                        String category = "Không rõ";
                        String approvedYear = "Không rõ";
                        String description = cells.get(3).text();
                        List<String> potentialFigureNames = new ArrayList<>();
                        
                        Element aTag = cells.get(1).selectFirst("a[href]");
                        if (aTag != null) {
                        	try {
                        		String link = URLDecoder.decode(aTag.attr("abs:href"), StandardCharsets.UTF_8.name());
                            	if (!link.contains("/w/index.php?")) {
                            		Document relicDoc = Jsoup.connect(link).get();
                            		Element overviewPara = relicDoc.selectFirst("div.mw-parser-output > p");
                            		if (overviewPara != null) {
                            			overviewPara.select("sup").remove();
                            			description = overviewPara.text();
                            		}
                            		
                            		Elements paras = relicDoc.select("div.mw-parser-output > p");
                            		Pattern pattern = Pattern.compile("(\\p{Lu}\\p{Ll}*\\s+){1,}(\\p{Lu}\\p{Ll}*)");                        			
                            		for (Element para: paras) {
                            			Matcher matcher = pattern.matcher(para.text());
                            	        while (matcher.find()) {
                            	        	String potentialName = matcher.group();
                            	            int length = potentialName.split("\\s+").length;
                            	            String surname = (potentialName.split("\\s+"))[0];
                            	            //only crawl potentialName with surnames found in Figures.json
                            	            if (!StringHelper.containString(potentialFigureNames, potentialName) && 
                            	            		StringHelper.containString(surnames, surname) && 
                            	            		length > 1) {
                            	            	potentialFigureNames.add(potentialName.replaceAll("\\s+", " "));
                            	            }
                            	        }
                            		}
                        	        System.out.println(potentialFigureNames);
                            	}
							} catch (IOException e) {
								e.printStackTrace();
							}     	          	
                        }

                        //Add relic
                        relics.add(new Relic(name, location, category, approvedYear, description, potentialFigureNames));
                        System.out.println(name);
                        System.out.println(location);
                        System.out.println(category);
                        System.out.println(approvedYear);
                        System.out.println(description);                 
                        System.out.println("-------------");
                    }
                    continue;
                }
                
                //crawl tables not table Ninh Binh
                Elements rows = table.select("tr");

                for (int i = 1; i < rows.size(); i++) {
                    try {
                        Elements cells = rows.get(i).select("td");

                        String name = cells.get(0).text();
                        String location = cells.get(1).text();
                        String category = cells.get(2).text();
                        String approvedYear = "Không rõ";
                        if (cells.size() > 3) {
                        	approvedYear = cells.get(3).text();
                        }         
                        String description = "Không rõ";
                        List<String> potentialFigureNames = new ArrayList<>();

                        if (name.isEmpty()) name = "Không rõ";
                        if (location.isEmpty()) location = "Không rõ";
                        if (category.isEmpty()) category = "Không rõ";
                        if (approvedYear.isEmpty()) approvedYear = "Không rõ";
                        
                        Element aTag = cells.get(0).selectFirst("a[href]");
                        if (aTag != null) {
                        	try {
                        		String link = URLDecoder.decode(aTag.attr("abs:href"), StandardCharsets.UTF_8.name());
                            	if (!link.contains("/w/index.php?")) {
                            		Document relicDoc = Jsoup.connect(link).get();
                            		Element overviewPara = relicDoc.selectFirst("div.mw-parser-output > p");
                            		if (overviewPara != null) {
                            			overviewPara.select("sup").remove();
                            			description = overviewPara.text();
                            		}
                            		//only relics with "Lich su" category have relatedFigures
                            		if (category.contains("Lịch sử")) {
                            			Elements paras = relicDoc.select("div.mw-parser-output > p");
                                		Pattern pattern = Pattern.compile("(\\p{Lu}\\p{Ll}*\\s+){1,}(\\p{Lu}\\p{Ll}*)");                        			
                                		for (Element para: paras) {
                                			Matcher matcher = pattern.matcher(para.text());
                                	        while (matcher.find()) {
                                	        	String potentialName = matcher.group();
                                	            int length = potentialName.split("\\s+").length;
                                	            String surname = (potentialName.split("\\s+"))[0];
                                	            //only crawl potential names with surnames found in Figures.json
                                	            if (!StringHelper.containString(potentialFigureNames, potentialName) && 
                                	            		StringHelper.containString(surnames, surname) && 
                                	            		length > 1) {
                                	            	potentialFigureNames.add(potentialName.replaceAll("\\s+", " "));
                                	            }
                                	        }
                                		}
                            	        System.out.println(potentialFigureNames);
                            		}
                            	}
							} catch (IOException e) {
								e.printStackTrace();
							}                        	
                        }

                        //Add relic
                        relics.add(new Relic(name, location, category, approvedYear, description, potentialFigureNames));
                        System.out.println(name);
                        System.out.println(location);
                        System.out.println(category);
                        System.out.println(approvedYear);
                        System.out.println(description);                 
                        System.out.println("-------------");
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                    
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        

        return relics;
    }

}