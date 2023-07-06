package crawler.festival;

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
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.google.gson.reflect.TypeToken;

import crawler.ICrawler;
import crawler.JsonIO;
import model.Festival;

public class FestivalCrawler implements ICrawler{
    private JsonIO<Festival> festivalIO = new JsonIO<>(new TypeToken<ArrayList<Festival>>() {}.getType());

    @Override
	public void crawl() {
    	List<Festival> allFestivals = new ArrayList<>();
        allFestivals = crawlWiki();
//        allFestivals.addAll(crawlFesND());
//        allFestivals.addAll(crawlFesAG());
//        allFestivals.addAll(crawlFesLS());
//        allFestivals.addAll(crawlFesQNam());
//        allFestivals.addAll(crawlFesHue());
//        allFestivals.addAll(crawlFesTH());
//        allFestivals.addAll(crawlFesNA());
//        allFestivals.addAll(crawlFesHP());
//        allFestivals.addAll(crawlFesHCM());
//        allFestivals.addAll(crawlFesQNinh());
//        allFestivals.addAll(crawlFesBN());
//        allFestivals.addAll(crawlFesVP());
//        allFestivals.addAll(crawlFesQB());
//        allFestivals.addAll(crawlFesHT());
//        allFestivals.addAll(crawlFesTN());
//        allFestivals.addAll(crawlFesBRVT());
		
        
        merge(allFestivals, crawlFesND(), "Nam Định");
        merge(allFestivals, crawlFesAG(), "An Giang");
        merge(allFestivals, crawlFesLS(), "Lạng Sơn");
        merge(allFestivals, crawlFesQNam(), "Quảng Nam");
        merge(allFestivals, crawlFesHue(), "Huế");
        merge(allFestivals, crawlFesTH(), "Thanh Hóa");
        merge(allFestivals, crawlFesNA(), "Nghệ An");
        merge(allFestivals, crawlFesHCM(), "Hồ Chí Minh");
        merge(allFestivals, crawlFesQNinh(), "Quảng Ninh");
        merge(allFestivals, crawlFesBN(), "Bắc Ninh");
        merge(allFestivals, crawlFesVP(), "Vĩnh Phúc");
        merge(allFestivals, crawlFesQB(), "Quảng Bình");
        merge(allFestivals, crawlFesHT(), "Hà Tĩnh");
        merge(allFestivals, crawlFesTN(), "Tây Ninh");
        merge(allFestivals, crawlFesBRVT(), "Vũng Tàu");
        merge(allFestivals, crawlFesHN(), "Hà Nội");
        festivalIO.writeJson(allFestivals, "src/main/resources/json/Festivals.json");
        
	}

    public List<Festival> crawlWiki() {
        Document doc;
        List<Festival> festivals = new ArrayList<>();

        try {
            String url = URLDecoder.decode("https://vi.wikipedia.org/wiki/L%E1%BB%85_h%E1%BB%99i_Vi%E1%BB%87t_Nam?fbclid=IwAR1Qp43JccDCCImhhHwycn6jRup0POXf_qq-doZ4AuPtLRorscpvjYdaQYs", StandardCharsets.UTF_8.name());
            doc = Jsoup.connect(url).get();

            Elements table = doc.select("table.prettytable.wikitable");
            Elements rows = table.select("tr");

            for (int i = 1; i < rows.size(); i++) {

                Elements cells = rows.get(i).select("td");

                //Get informations 
                String name = cells.get(2).text();
                String firstTime = cells.get(3).text();
                String startingDay = cells.get(0).text();
                String location = cells.get(1).text();
                String tempString = cells.get(4).text();
                String description = "Không rõ";
                List<String> relatedRelics = new ArrayList<>();
                List<String> relatedFigures = new ArrayList<>();

                //Default
                if (name.isEmpty()) {
                	name = "Không rõ";
                }
                else {
                	getRelatedRelics(relatedRelics, name);
                }
                if (firstTime.isEmpty()) firstTime = "Không rõ";
                if (startingDay.isEmpty()) startingDay = "Không rõ";
                if (location.isEmpty()) location = "Không rõ";
                if (tempString.isEmpty()) tempString = "Không rõ";
                
                Element aElement = cells.get(2).selectFirst("a[href]");
                if (aElement != null) {
                	String link = URLDecoder.decode(aElement.attr("abs:href"), StandardCharsets.UTF_8.name());
                	if (!link.contains("/w/index.php")) {
                		Document linkDoc = Jsoup.connect(link).get();
                		description = linkDoc.selectFirst("div.mw-parser-output > p").text();
                		
            			getRelatedRelics(relatedRelics, description);
						
                		System.out.println("Description: " + description);
                	}
                }
            
                //Add related figures to hashmap
                if (tempString != "Không rõ") {
                    String temp = "";
                    for (int k = 0; k < tempString.length(); k ++) {

                        if (tempString.charAt(k) != ',') {
                            temp += tempString.charAt(k);
                        }

                        if (tempString.charAt(k) == ',' || k == (tempString.length() - 1)) {
                        	if (temp.contains("(") || temp.contains(")") || temp.contains(":") || temp.contains("và")) {
                        		Pattern pattern = Pattern.compile("(\\p{Lu}\\p{Ll}*\\s+){1,}(\\p{Lu}\\p{Ll}*)");
                        		Matcher matcher = pattern.matcher(temp);
                    	        while (matcher.find()) {
                    	        	relatedFigures.add(matcher.group().trim());
                    	        }
                        	}
                        	else {
                        		 relatedFigures.add(temp.trim());
                        	}                        
                            temp = "";
                        }

                    }
                }

                //Add festivals
                festivals.add(new Festival(name, location, firstTime, startingDay, description, relatedFigures, relatedRelics));

                System.out.println(name);
                System.out.println(location);
                System.out.println(startingDay);
                System.out.println(firstTime);
                System.out.println(relatedFigures);
                System.out.println(relatedRelics);
                System.out.println("-------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return festivals;
    }
    
    private void getRelatedRelics(List<String> relatedRelics, String text) {
    	Pattern pattern = Pattern.compile("((?i)(chùa|đền|Đền|đình|Đình|chợ|núi|miếu|"
    			+ "đầm|điện|Điện|gò|thành|dinh|động|lăng))"
				+ "\\s+((\\p{Lu}\\p{Ll}*\\s*)+)");
		Matcher matcher = pattern.matcher(text);						
		while (matcher.find()) {
			boolean isDuplicate = false;
			String newRelic = matcher.group().trim();
			//check duplicated			
			for (String relatedRelic : relatedRelics) {
				if (relatedRelic.equalsIgnoreCase(newRelic)) {
					isDuplicate = true;
					break;
				}
			}
			if (!isDuplicate) {
			    relatedRelics.add(newRelic);
			}										
		}
    }
    
    public List<Festival> crawlFesND() {
        Document doc;
        List<Festival> festivals_ND = new ArrayList<>();

        try {
            String url = "https://namdinh.gov.vn/portal/Pages/2020-11-3/Diem-tham-quan-du-lich-va-cac-le-hoi-tieu-bieucgc20c.aspx?fbclid=IwAR3KgZcszvaHCKQaoKENXdCO3Pv4b6i8Ccgre9k82gTEhbxi2cVYRGSgOxU";
            doc = Jsoup.connect(url).get();

            Elements table = doc.select("#art-content > table");
            Elements rows = table.select("tr");

            for (int i = 1; i < rows.size(); i++) {
                Elements cells = rows.get(i).select("td");

                String name = cells.get(1).text();
                String startingDay = cells.get(2).text();
                String location = cells.get(3).text();
                String firstTime = "Không rõ";
                String description = "Không rõ";
                List<String> relatedRelics = new ArrayList<>();
                List<String> relatedFigures = new ArrayList<>();
                
                getRelatedRelics(relatedRelics, name);

                festivals_ND.add(new Festival(name, location, firstTime, startingDay, description, relatedFigures, relatedRelics));
                System.out.println(name);
                System.out.println(startingDay);
                System.out.println(location);
                System.out.println(firstTime);
                System.out.println(description);
                System.out.println(relatedFigures);
                System.out.println(relatedRelics);
                System.out.println("---------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return festivals_ND;
    }
    
    public List<Festival> crawlFesLS() {
        Document doc;
        List<Festival> festivals_LS = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<String> startingDay = new ArrayList<>();
        List<String> location = new ArrayList<>();
        List<String> description = new ArrayList<>();
        List<String> relatedFigures = new ArrayList<>();

        try {
            String url = "https://vinpearl.com/vi/12-le-hoi-lang-son-truyen-thong-noi-tieng-bac-nhat-xu-lang?fbclid=IwAR0B5Xmqg8vZaT8GwpIptPLZGzeUdOP6-kaE4chSdNCR450j6W_glcC4lP4#1.+L%E1%BB%85+h%E1%BB%99i+ch%C3%B9a+Tam+Thanh+-+L%E1%BB%85+h%E1%BB%99i+L%E1%BA%A1ng+S%C6%A1n+%C4%91%E1%BA%B7c+s%E1%BA%AFc+%C4%91%E1%BA%A7u+n%C4%83m";
            doc = Jsoup.connect(url).get();
            Elements elements = doc.select("#block-porto-content > div > div.container.detail.news-full > div > div.main-body-wrapper > div.content-wrapper > div.content");
            for (Element element : elements) {
                
                //Get name
                Elements nameElements = elements.select("h2");
                for (Element nameElement: nameElements) {
                    String temp = nameElement.text().substring(nameElement.text().indexOf(" ") + 1);
                    String tempArr[] = temp.split("-");
                    if (tempArr.length == 2) {
                    	if (tempArr[0].contains("Lạng Sơn")) {
                    		name.add(tempArr[1].trim());
                    	}
                    	else {
                    		name.add(tempArr[0].trim());
                    	}
                    }
                    else {
                    	name.add(temp);
                    }     
                }

                String currentLocation = ""; 
                String currentDate = "";

                //Get startingDay, location, description
                Elements locTimeElements = elements.select("ul");
                
                for (Element locTimeElement: locTimeElements) {
                    Element firstP = locTimeElement.nextElementSibling(); 
                    if (firstP.tagName().equals("p")) {
                        description.add(firstP.text());
                    }
                    String temp = locTimeElement.text();
                    if (temp.contains("Địa điểm:")) {
                        currentLocation = temp.substring(temp.indexOf(":") + 2, temp.indexOf("Thời gian diễn ra:"));
                        location.add(currentLocation);
                        temp = temp.substring(temp.indexOf("Thời gian diễn ra:"));
                    } 

                    if (temp.contains("Thời gian diễn ra:")) {
                        currentDate = temp.substring(temp.indexOf(":") + 2);
                        startingDay.add(currentDate);
                    }
                }               
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < name.size(); i++) {
        	List<String> relatedRelics = new ArrayList<>();
        	getRelatedRelics(relatedRelics, name.get(i));
            festivals_LS.add(new Festival(name.get(i), location.get(i), "Không rõ", startingDay.get(i), description.get(i), relatedFigures, relatedRelics));
            System.out.println(name.get(i));
            System.out.println(location.get(i));
            System.out.println(startingDay.get(i));
            System.out.println(description.get(i));
            System.out.println(relatedFigures);
            System.out.println("---------------------------");
        }

        return festivals_LS;
    }
    
    public List<Festival> crawlFesQNam() {
        Document doc;
        List<Festival> festivals_QNam = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<String> startingDay = new ArrayList<>();
        List<String> location = new ArrayList<>();
        List<String> description = new ArrayList<>();
        List<String> relatedFigures = new ArrayList<>();

        try {
            String url = "https://vinwonders.com/vi/bai-viet-du-lich/15-le-hoi-o-quang-nam-dac-sac-nhat/?fbclid=IwAR1S5eS7WIZkqsXrVKWYA2IZjXmbhNDJFRkIg6-rTRO163meLPpNA5HjIzw";
            doc = Jsoup.connect(url).get();

            Elements elements = doc.select("#main-single-content");

            //Get name
            Elements nameElements = elements.select("h3");

            for (Element nameElement: nameElements) {
                String temp = nameElement.text();
                int separatorIndex = temp.indexOf(". "); // Find the index of the separator ". "
                temp = temp.substring(separatorIndex + 2);
                String tempArr[] = temp.split("[-–]");
                if (tempArr.length == 2) {
                	if (tempArr[0].contains("Quảng Nam")) {
                		name.add(tempArr[1].trim());
                	}
                	else {
                		name.add(tempArr[0].trim());
                	}
                }
                else {
                	name.add(temp);
                }
            }

            //Get time, location and description
            Elements locationTimeElements = elements.select("ul");
            for (Element locationTimeElement: locationTimeElements) {

                String temp = locationTimeElement.text();
                Element exclu1 = doc.selectFirst("#toc_container > ul");
                Element exclu2 = doc.selectFirst("#main-single-content > ul:nth-child(79)");
                Element exclu3 = doc.selectFirst("#main-single-content > ul:nth-child(80)");
                Element exclu4 = doc.selectFirst("#main-single-content > ul:nth-child(85)");
                Element exclu5 = doc.selectFirst("#main-single-content > ul:nth-child(86)");

                if (locationTimeElement == exclu1 || locationTimeElement == exclu2) continue;
                if (locationTimeElement == exclu3 || locationTimeElement == exclu4) continue;
                if (locationTimeElement == exclu5) continue;
                
                String time = "";
                String loc = "";              

                if (temp.contains("Thời gian:") && temp.contains("Địa điểm:")) {
                    time = temp.substring(temp.indexOf(":") + 2, temp.indexOf("Địa điểm:"));
                    temp = temp.substring(temp.indexOf("Địa điểm:"));
                    startingDay.add(time);

                    loc = temp.substring(temp.indexOf(":") + 2);
                    location.add(loc);

                    //get description
                    Element firstP = locationTimeElement.nextElementSibling(); 
                    if (firstP.tagName().equals("p")) {
                        description.add(firstP.text());
                    }
                } else if (temp.contains("Thời gian:")) {
                    time = temp.substring(temp.indexOf(":") + 2);
                    startingDay.add(time);
                } else if (temp.contains("Địa điểm:")){
                    loc = temp.substring(temp.indexOf(":") + 2);
                    location.add(loc);

                    //get description
                    Element firstP = locationTimeElement.nextElementSibling(); 
                    if (firstP.tagName().equals("p")) {
                        description.add(firstP.text());
                    }
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for (int i = 0; i < name.size(); i++) {
        	List<String> relatedRelics = new ArrayList<>();
        	getRelatedRelics(relatedRelics, name.get(i));
            festivals_QNam.add(new Festival(name.get(i), location.get(i), "Không rõ", startingDay.get(i), description.get(i), relatedFigures, relatedRelics));
            System.out.println(name.get(i));
            System.out.println(location.get(i));
            System.out.println(startingDay.get(i));
            System.out.println(description.get(i));
            System.out.println(relatedFigures);
            System.out.println("---------------------------");
        }

        return festivals_QNam;
    }
    
    public List<Festival> crawlFesNA() {
        Document doc;
        List<Festival> festivals_NA = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<String> startingDay = new ArrayList<>();
        List<String> location = new ArrayList<>();
        List<String> description = new ArrayList<>();
        List<String> relatedFigures = new ArrayList<>();

        try {
            String url = "https://vinpearl.com/vi/le-hoi-o-nghe-an-diem-danh-10-le-hoi-doc-dao-nhat-xu-nghe?fbclid=IwAR2RSiy_eHWbZ8xpbP1CDxYg78BWlf0eIORzDdAIgON_S5bIczvY76B5a0o";
            doc = Jsoup.connect(url).get();

            Elements elements = doc.select("#block-porto-content > div > div.container.detail.news-full > div > div.main-body-wrapper > div.content-wrapper > div.content");

            Elements nameElements = elements.select("h2");

            for (Element nameElement: nameElements) {
                String temp = nameElement.text();
                int separatorIndex = temp.indexOf("."); // Find the index of the separator ". "
                temp = temp.substring(separatorIndex + 2);
                String tempArr[] = temp.split("[-:\\?]");
                if (tempArr.length == 2) {
                	if (tempArr[0].contains("Nghệ An")) {
                		name.add(tempArr[1].trim());
                	}
                	else {
                		name.add(tempArr[0].trim());
                	}
                }
                else {
                	name.add(temp);
                }                
            }

            String currentLocation = ""; 
            String currentDate = "";

                //Get startingDay, location, description
            Elements locTimeElements = elements.select("ul");
                
            for (Element locTimeElement: locTimeElements) {

                Element firstP = locTimeElement.nextElementSibling(); 

                while (!firstP.tagName().equals("p")) {
                    firstP = firstP.nextElementSibling();
                }

                description.add(firstP.text());

                String temp = locTimeElement.text();
                if (temp.contains("Thời gian:")) {
                    currentDate = temp.substring(temp.indexOf(":") + 2, temp.indexOf("Địa điểm:"));
                    startingDay.add(currentDate);
                    temp = temp.substring(temp.indexOf("Địa điểm:"));
                } 

                if (temp.contains("Địa điểm:")) {
                    currentLocation = temp.substring(temp.indexOf(":") + 2);
                    location.add(currentLocation);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < name.size(); i++) {
        	List<String> relatedRelics = new ArrayList<>();
        	getRelatedRelics(relatedRelics, name.get(i));
            festivals_NA.add(new Festival(name.get(i), location.get(i), "Không rõ", startingDay.get(i), description.get(i), relatedFigures, relatedRelics));
            System.out.println(name.get(i));
            System.out.println(location.get(i));
            System.out.println(startingDay.get(i));
            System.out.println(description.get(i));
            System.out.println(relatedFigures);
            System.out.println("---------------------------");
        }

        return festivals_NA;
    }
    
    public List<Festival> crawlFesTH() {
        Document doc;
        List<Festival> festivals_TH = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<String> startingDay = new ArrayList<>();
        List<String> location = new ArrayList<>();
        List<String> description = new ArrayList<>();
        List<String> relatedFigures = new ArrayList<>();

        try {
            String url = "https://vinpearl.com/vi/tong-hop-cac-le-hoi-thanh-hoa-truyen-thong-tung-thang-trong-nam?fbclid=IwAR2LxAIyEKmSWAEEiPazRVFAzd8x_eWuDLuzjTj_CV2WmuQh--2n5LE0xGk";
            doc = Jsoup.connect(url).get();
            Elements elements = doc.select("#block-porto-content > div > div.container.detail.news-full > div > div.main-body-wrapper > div.content-wrapper.read-more > div.content");
            
            Elements nameElements = elements.select("h3");

            for (Element nameElement: nameElements) {
                String temp = nameElement.text();
                int separatorIndex = temp.indexOf(". "); // Find the index of the separator ". "
                temp = temp.substring(separatorIndex + 2);
                name.add(temp);
            }

            String currentLocation = ""; 
            String currentDate = "";

                //Get startingDay, location, description
            Elements locTimeElements = elements.select("ul");
                
            for (Element locTimeElement: locTimeElements) {

                Element firstP = locTimeElement.nextElementSibling(); 

                while (!firstP.tagName().equals("p")) {
                    firstP = firstP.nextElementSibling();
                }

                description.add(firstP.text());

                String temp = locTimeElement.text();
                if (temp.contains("Địa chỉ:")) {
                    currentLocation = temp.substring(temp.indexOf(":") + 2, temp.indexOf("Thời gian tổ chức tham khảo:"));
                    location.add(currentLocation);
                    temp = temp.substring(temp.indexOf("Thời gian tổ chức tham khảo:"));
                } 

                if (temp.contains("Thời gian tổ chức tham khảo:")) {
                    currentDate = temp.substring(temp.indexOf(":") + 2);                    
                    startingDay.add(currentDate);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 15; i++) {
        	List<String> relatedRelics = new ArrayList<>();
        	getRelatedRelics(relatedRelics, name.get(i));
            festivals_TH.add(new Festival(name.get(i), location.get(i), "Không rõ", startingDay.get(i), description.get(i), relatedFigures, relatedRelics));
            System.out.println(name.get(i));
            System.out.println(location.get(i));
            System.out.println(startingDay.get(i));
            System.out.println(description.get(i));
            System.out.println(relatedFigures);
            System.out.println("---------------------------");
        }

        return festivals_TH;
    }
    
    public List<Festival> crawlFesHP() {
        Document doc;
        List<Festival> festivals_HP = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<String> startingDay = new ArrayList<>();
        List<String> location = new ArrayList<>();
        List<String> description = new ArrayList<>();
        List<String> relatedFigures = new ArrayList<>();

        try {
            String url = "https://vinpearl.com/vi/le-hoi-hai-phong-thoi-gian-va-diem-nghi-duong-phu-hop";
            doc = Jsoup.connect(url).get();
            Elements elements = doc.select("#block-porto-content > div > div.container.detail.news-full > div > div.main-body-wrapper > div.content-wrapper > div.content");
            
            Elements nameElements = elements.select("h3");

            for (Element nameElement: nameElements) {
                String temp = nameElement.text();
                int separatorIndex = temp.indexOf(". "); // Find the index of the separator ". "
                String temp1 = temp.substring(separatorIndex + 2);
                name.add(temp1);
            }

            String currentLocation = ""; 
            String currentDate = "";

                //Get startingDay, location, description
            Elements locTimeElements = elements.select("ul");
                
            for (Element locTimeElement: locTimeElements) {

                Element firstP = locTimeElement.nextElementSibling(); 

                while (!firstP.tagName().equals("p")) {
                    firstP = firstP.nextElementSibling();
                }

                description.add(firstP.text());

                String temp = locTimeElement.text();
                if (temp.contains("Thời gian:")) {
                    currentDate = temp.substring(temp.indexOf(":") + 2, temp.indexOf("Địa điểm:"));
                    startingDay.add(currentDate);
                    temp = temp.substring(temp.indexOf("Địa điểm:"));
                } 

                if (temp.contains("Địa điểm:")) {
                    currentLocation = temp.substring(temp.indexOf(":") + 2);
                    location.add(currentLocation);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i++) {
        	List<String> relatedRelics = new ArrayList<>();
        	getRelatedRelics(relatedRelics, name.get(i));
            festivals_HP.add(new Festival(name.get(i), location.get(i), "Không rõ", startingDay.get(i), description.get(i), relatedFigures, relatedRelics));
            System.out.println(name.get(i));
            System.out.println(location.get(i));
            System.out.println(startingDay.get(i));
            System.out.println(description.get(i));
            System.out.println(relatedFigures);
            System.out.println("---------------------------");
        }

        return festivals_HP;
    }

    public List<Festival> crawlFesHCM() {
        Document doc;
        List<Festival> festivals_HCM = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<String> startingDay = new ArrayList<>();
        List<String> location = new ArrayList<>();
        List<String> description = new ArrayList<>();
        List<String> relatedFigures = new ArrayList<>();

        try {
            String url = "https://vinpearl.com/vi/10-le-hoi-hcm-dac-sac-nen-tham-du-it-nhat-mot-lan-trong-doi";
            doc = Jsoup.connect(url).get();
            Elements elements = doc.select("#block-porto-content > div > div.container.detail.news-full > div > div.main-body-wrapper > div.content-wrapper.read-more > div.content");
            
            Elements nameElements = elements.select("h2");

            for (Element nameElement: nameElements) {
                String temp = nameElement.text();
                int separatorIndex = temp.indexOf(". "); // Find the index of the separator ". "
                temp = temp.substring(separatorIndex + 2);
                String tempArr[] = temp.split("-");
                if (tempArr.length == 2) {
                	if (tempArr[0].contains("HCM")) {
                		name.add(tempArr[1].trim());
                	}
                	else {
                		name.add(tempArr[0].trim());
                	}
                }
                else {
                	name.add(temp);
                }
            }

            String currentLocation = ""; 
            String currentDate = "";

                //Get startingDay, location, description
            Elements locTimeElements = elements.select("ul");
                
            for (Element locTimeElement: locTimeElements) {

                Element firstP = locTimeElement.nextElementSibling(); 

                while (!firstP.tagName().equals("p")) {
                    firstP = firstP.nextElementSibling();
                }

                description.add(firstP.text());

                String temp = locTimeElement.text();
                if (temp.contains("Thời gian diễn ra:")) {
                    currentDate = temp.substring(temp.indexOf(":") + 2, temp.indexOf("Địa điểm tổ chức:"));
                    startingDay.add(currentDate);
                    temp = temp.substring(temp.indexOf("Địa điểm tổ chức:"));
                } 

                if (temp.contains("Địa điểm tổ chức:")) {
                    currentLocation = temp.substring(temp.indexOf(":") + 2);
                    location.add(currentLocation);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i++) {
        	List<String> relatedRelics = new ArrayList<>();
        	getRelatedRelics(relatedRelics, name.get(i));
            festivals_HCM.add(new Festival(name.get(i), location.get(i), "Không rõ", startingDay.get(i), description.get(i), relatedFigures, relatedRelics));
            System.out.println(name.get(i));
            System.out.println(location.get(i));
            System.out.println(startingDay.get(i));
            System.out.println(description.get(i));
            System.out.println(relatedFigures);
            System.out.println("---------------------------");
        }

        return festivals_HCM;
    }

    public List<Festival> crawlFesQNinh() {
        Document doc;
        List<Festival> festivals_QNinh = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<String> startingDay = new ArrayList<>();
        List<String> location = new ArrayList<>();
        List<String> description = new ArrayList<>();
        List<String> relatedFigures = new ArrayList<>();

        try {
            String url = "https://vinpearl.com/vi/le-hoi-ha-long-quang-ninh-top-10-hoat-dong-dac-sac-nhat#2.+L%E1%BB%85+h%E1%BB%99i+Y%C3%AAn+T%E1%BB%AD";
            doc = Jsoup.connect(url).get();

            Elements elements = doc.select("#block-porto-content > div > div.container.detail.news-full > div > div.main-body-wrapper > div.content-wrapper > div.content");
            
            Elements nameElements = elements.select("h2");
            for (Element nameElement: nameElements) {
                String temp = nameElement.text();
                int separatorIndex = temp.indexOf("."); // Find the index of the separator ". "
                temp = temp.substring(separatorIndex + 2);
                String tempArr[] = temp.split("-");
                if (tempArr.length == 2) {
                	if (tempArr[0].contains("Hạ Long")) {
                		name.add(tempArr[1].trim());
                	}
                	else {
                		name.add(tempArr[0].trim());
                	}
                }
                else {
                	name.add(temp);
                }
            }

            String currentLocation = ""; 
            String currentDate = "";

                //Get startingDay, location, description
            Elements locTimeElements = elements.select("ul");
                
            for (Element locTimeElement: locTimeElements) {
                Element firstP = locTimeElement.nextElementSibling(); 

                while (!firstP.tagName().equals("p")) {
                    firstP = firstP.nextElementSibling();
                }

                description.add(firstP.text());

                String temp = locTimeElement.text();
                if (temp.contains("Địa điểm tổ chức:")) {
                    currentLocation = temp.substring(temp.indexOf(":") + 2, temp.indexOf("Thời gian diễn ra tham khảo:"));
                    location.add(currentLocation);
                    temp = temp.substring(temp.indexOf("Thời gian diễn ra tham khảo:"));
                } 

                if (temp.contains("Thời gian diễn ra tham khảo:")) {
                    currentDate = temp.substring(temp.indexOf(":") + 2);
                    startingDay.add(currentDate);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i++) {
        	List<String> relatedRelics = new ArrayList<>();
        	getRelatedRelics(relatedRelics, name.get(i));
            festivals_QNinh.add(new Festival(name.get(i), location.get(i), "Không rõ", startingDay.get(i), description.get(i), relatedFigures, relatedRelics));
            System.out.println(name.get(i));
            System.out.println(location.get(i));
            System.out.println(startingDay.get(i));
            System.out.println(description.get(i));
            System.out.println(relatedFigures);
            System.out.println("---------------------------");
        }

        return festivals_QNinh;
    }
    
    public List<Festival> crawlFesHue() {
        Document doc;
        List<Festival> festivals_Hue = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<String> startingDay = new ArrayList<>();
        List<String> location = new ArrayList<>();
        List<String> description = new ArrayList<>();
        List<String> relatedFigures = new ArrayList<>();

        try {
            String url = "https://www.bestprice.vn/blog/diem-den-8/hue-5/top-8-le-hoi-hue-mang-dam-van-hoa-truyen-thong-doc-dao_2-7095.html?fbclid=IwAR0XpMy81X8jqkm1W38_Qg82ae4ULUMDNekKuoQ0pF1bfP5JpuTFlEZfLuo";
            doc = Jsoup.connect(url).get();

            Elements elements = doc.select("body > main > section.bpt-blog.container.blog-page.mart10-only-xs.margin-bottom-20 > div.blog-category-container.bpv-col-left.article-details.no-padding.article-content.responsive-description > article > div.content-article.margin-top-15");
            
            Elements nameElements = elements.select("h2");
            for (Element nameElement: nameElements) {
                name.add(nameElement.text());
            }

            String currentLocation = ""; 
            String currentDate = "";

                //Get startingDay, location, description
            Elements locTimeElements = elements.select("ul");
                
            for (Element locTimeElement: locTimeElements) {
                Element firstP = locTimeElement.nextElementSibling(); 

                while (!firstP.tagName().equals("p")) {
                    firstP = firstP.nextElementSibling();
                }

                description.add(firstP.text());
                String temp = locTimeElement.text();
                if (temp.contains("Thời gian:")) {
                    currentDate = temp.substring(temp.indexOf(":") + 2, temp.indexOf("Địa điểm tổ chức:"));
                    startingDay.add(currentDate);
                    temp = temp.substring(temp.indexOf("Địa điểm tổ chức:"));
                } 

                if (temp.contains("Địa điểm tổ chức:")) {
                    currentLocation = temp.substring(temp.indexOf(":") + 2);
                    location.add(currentLocation);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for (int i = 0; i < name.size(); i++) {
        	List<String> relatedRelics = new ArrayList<>();
        	getRelatedRelics(relatedRelics, name.get(i));
            festivals_Hue.add(new Festival(name.get(i), location.get(i), "Không rõ", startingDay.get(i), description.get(i), relatedFigures, relatedRelics));
            System.out.println(name.get(i));
            System.out.println(location.get(i));
            System.out.println(startingDay.get(i));
            System.out.println(description.get(i));
            System.out.println(relatedFigures);
            System.out.println("---------------------------");
        }

        return festivals_Hue;
    }

    public List<Festival> crawlFesAG() {
        Document doc;
        List<Festival> festivals_AG = new ArrayList<>();

        try {
            String url = "https://angiangtourist.vn/thoi-gian-va-dia-diem-to-chuc-cac-le-hoi-lon-o-an-giang/?fbclid=IwAR0Pgns8nI19fWKvq-9yAMzjg4QNIJwsqgl2mld8inhNlS49f2IwcUqzUyE";
            doc = Jsoup.connect(url).get();

            Elements pElements = doc.select("#post-2598 > div > div.entry-content.single-page > p");

            List<Element> subList = pElements.subList(6, 14);
            for (Element pElement: subList) {

                List<String> relatedFigures = new ArrayList<>();
                List<String> relatedRelics = new ArrayList<>();

                Element temp = doc.selectFirst("#post-2598 > div > div.entry-content.single-page > p:nth-child(11)");
                Element temp1 = doc.selectFirst("#post-2598 > div > div.entry-content.single-page > p:nth-child(12)");
                if (pElement == temp || pElement == temp1) continue;

                String lineText = pElement.text();
                String name = "";
                String location = "";
                String startingDay = "";
                String description = "";
                String related = "";

                name = lineText.substring(0, lineText.indexOf("Thời gian:"));
                lineText = lineText.substring(lineText.indexOf("Thời gian:"));               
                System.out.println(name);
                
                getRelatedRelics(relatedRelics, name);

                startingDay = lineText.substring(lineText.indexOf(":") + 2, lineText.indexOf("Địa điểm:"));
                lineText = lineText.substring(lineText.indexOf("Địa điểm:"));
                System.out.println(startingDay);
                
                location = lineText.substring(lineText.indexOf(":") + 2, lineText.indexOf("Đối tượng suy tôn:"));
                lineText = lineText.substring(lineText.indexOf("Đối tượng suy tôn:"));
                System.out.println(location);
                

                related = (lineText.substring(lineText.indexOf(":") + 2, lineText.indexOf("Đặc điểm:")).replace(".","").split("[(,]"))[0].trim();
                lineText = lineText.substring(lineText.indexOf("Đặc điểm: "));
                relatedFigures.add(related);
                System.out.println(related);

                description = lineText.substring(lineText.indexOf(":") + 2);
                System.out.println(description);
                
                System.out.println("--------------------");

                festivals_AG.add(new Festival(name, location, related, startingDay, description, relatedFigures, relatedRelics));

            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }

        return festivals_AG;
    }
    
    public List<Festival> crawlFesBN() {
        Document doc;
        List<Festival> festivals_BN = new ArrayList<>();
        List<String> name = new ArrayList<>();

        try {
            String url = "https://alltours.vn/bac-ninh/nhung-le-hoi-o-bac-ninh.html";
            doc = Jsoup.connect(url).get();

            Elements elements = doc.select("#single-blocks > div.single-blog-content.entry.clr");

            Elements nameElements = elements.select("h2");

            for (Element nameElement: nameElements) {
                String temp = nameElement.text();
                int separatorIndex = temp.indexOf("."); // Find the index of the separator ". "
                String temp1 = temp.substring(separatorIndex + 2);
                name.add(temp1);
            }
            
            int idx = 0;

            Elements pElements = elements.select("p");
            List<Element> subList = pElements.subList(2, 18);
            for (Element pElement: subList) {

                List<String> relatedFigures = new ArrayList<>();
                List<String> relatedRelics = new ArrayList<>();
                String nameString = name.get(idx++);
                String lineText = pElement.text();
                String loc = "";
                String startingD = "";
                String descrip = "";
                String related = "";
                
                getRelatedRelics(relatedRelics, nameString);

                if (pElement.equals(subList.get(3))) {
                    startingD = lineText.substring(lineText.indexOf(":") + 2, lineText.indexOf("Địa điểm:"));
                    lineText = lineText.substring(lineText.indexOf("Địa điểm:"));

                    loc = lineText.substring(lineText.indexOf(":") + 2, lineText.indexOf("Đặc điểm: "));
                    lineText = lineText.substring(lineText.indexOf("Đặc điểm: "));

                    descrip = lineText.substring(lineText.indexOf(":") + 2);
                } else {
                    startingD = lineText.substring(lineText.indexOf(":") + 2, lineText.indexOf("Địa điểm:"));
                    lineText = lineText.substring(lineText.indexOf("Địa điểm:"));
                   
                    loc = lineText.substring(lineText.indexOf(":") + 2, lineText.indexOf("Đối tượng suy tôn:"));
                    lineText = lineText.substring(lineText.indexOf("Đối tượng suy tôn:"));

                    related = lineText.substring(lineText.indexOf(":") + 2, lineText.indexOf("Đặc điểm:"));
                    lineText = lineText.substring(lineText.indexOf("Đặc điểm: "));
                    
                    Pattern pattern = Pattern.compile("(\\p{Lu}\\p{Ll}*\\s+){1,}(\\p{Lu}\\p{Ll}*)");
                    Matcher matcher = pattern.matcher(related.replaceAll("\\(.*?\\)", ""));
        	        while (matcher.find()) {
        	        	String figureName = matcher.group();
        	        	if (!figureName.equals("Bắc Ninh")) {
        	        		relatedFigures.add(figureName);
        	        	}
        	        }

                    descrip = lineText.substring(lineText.indexOf(":") + 2);
                }

                festivals_BN.add(new Festival(nameString, loc, "Không rõ", startingD, descrip, relatedFigures, relatedRelics));
                System.out.println(nameString);
                System.out.println(loc);
                System.out.println(startingD);
                System.out.println(descrip);
                System.out.println(relatedFigures);
                System.out.println("---------------------------");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return festivals_BN;
    }

    public List<Festival> crawlFesVP() {
        Document doc;
        List<Festival> festivals_VP = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<String> startingDay = new ArrayList<>();
        List<String> location = new ArrayList<>();
        List<String> description = new ArrayList<>();
        List<String> relatedFigures = new ArrayList<>();

        try {
            String url = "https://dulichchaovietnam.com/9-le-hoi-o-dac-sac-o-vinh-phuc.html?fbclid=IwAR0bnCuhgGAOBMTV-8TfOYri-Q_NPE07Zi2iXt8gLPTMcRIjH2suxgCXIt8";
            doc = Jsoup.connect(url).get();

            Elements elements = doc.select("body > main > div > div.col-sm-9 > article.p-home.paddtb15 > div > div > div");

            Elements pElements = elements.select("p");
            List<Element> subList = pElements.subList(3, 47);

            for (Element pElement: subList) {
                String text = pElement.text();
                if (text.isEmpty()) continue;

                String check = "";

                Pattern pattern = Pattern.compile("\\d+\\.\\s+(.+)");
                Matcher matcher = pattern.matcher(text);
                while (matcher.find()) {
                    check = matcher.group(1).trim();
                    name.add(check);
                }

                if (!check.isEmpty()) continue;
    

                if (text.contains("Thời gian:")) {
                    String temp = text.substring(text.indexOf(":") + 2);
                    startingDay.add(temp);
                } else if (text.contains("Địa điểm:")) {
                    String temp = text.substring(text.indexOf(":") + 2);
                    location.add(temp); 
                } else {
                    description.add(text);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < name.size(); i++) {
        	List<String> relatedRelics = new ArrayList<>();
        	getRelatedRelics(relatedRelics, name.get(i));
            festivals_VP.add(new Festival(name.get(i), location.get(i), "Không rõ", startingDay.get(i), description.get(i), relatedFigures, relatedRelics));
            System.out.println(name.get(i));
            System.out.println(location.get(i));
            System.out.println(startingDay.get(i));
            System.out.println(description.get(i));
            System.out.println(relatedFigures);
            System.out.println("---------------------------");
        }

        return festivals_VP;
    }
    
    public List<Festival> crawlFesQB() {
        Document doc;
        List<Festival> festivals_QB = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<String> startingDay = new ArrayList<>();
        List<String> location = new ArrayList<>();
        List<String> description = new ArrayList<>();
        List<String> relatedFigures = new ArrayList<>();

        try {
            String url = "https://www.bestprice.vn/blog/van-hoa-am-thuc-6/top-8-le-hoi-quang-binh-mang-dam-van-hoa-truyen-thong-doc-dao_14-8311.html?fbclid=IwAR23tVZkTbC7DBboNIUgDs2Wt1pUAOrk20oSixGDr9QlDQG7w0QSX5WJcWc";
            doc = Jsoup.connect(url).get();

            Elements elements = doc.select("body > main > section.bpt-blog.container.blog-page.mart10-only-xs.margin-bottom-20 > div.blog-category-container.bpv-col-left.article-details.no-padding.article-content.responsive-description > article > div.content-article.margin-top-15");

            Elements nameElements = elements.select("h2");
            for (Element nameElement: nameElements) {
            	String tempArr[] = nameElement.text().split("-");
                if (tempArr.length == 2) {
                	if (tempArr[0].contains("Quảng Bình")) {
                		name.add(tempArr[1].trim());
                	}
                	else {
                		name.add(tempArr[0].trim());
                	}
                }
                else {
                	name.add(nameElement.text());
                }       
            }

            String currentLocation = ""; 
            String currentDate = "";

                //Get startingDay, location, description
            Elements locTimeElements = elements.select("ul");
                
            for (Element locTimeElement: locTimeElements) {
                Element firstP = locTimeElement.nextElementSibling(); 

                while (!firstP.tagName().equals("p")) {
                    firstP = firstP.nextElementSibling();
                }

                description.add(firstP.text());
                String temp = locTimeElement.text();
                if (temp.contains("Thời gian:")) {
                    currentDate = temp.substring(temp.indexOf(":") + 2, temp.indexOf("Địa điểm tổ chức:"));
                    startingDay.add(currentDate);
                    temp = temp.substring(temp.indexOf("Địa điểm tổ chức:"));
                } 

                if (temp.contains("Địa điểm tổ chức:")) {
                    currentLocation = temp.substring(temp.indexOf(":") + 2);
                    location.add(currentLocation);
                }
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < name.size(); i++) {
        	List<String> relatedRelics = new ArrayList<>();
        	getRelatedRelics(relatedRelics, name.get(i));
            festivals_QB.add(new Festival(name.get(i), location.get(i), "Không rõ", startingDay.get(i), description.get(i), relatedFigures, relatedRelics));
            System.out.println(name.get(i));
            System.out.println(location.get(i));
            System.out.println(startingDay.get(i));
            System.out.println(description.get(i));
            System.out.println(relatedFigures);
            System.out.println("---------------------------");
        }


        return festivals_QB;
    }

    public List<Festival> crawlFesHT() {
        Document doc;
        List<Festival> festivals_HT = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<String> startingDay = new ArrayList<>();
        List<String> location = new ArrayList<>();
        List<String> description = new ArrayList<>();
        List<String> relatedFigures = new ArrayList<>();

        try {
            String url = "https://vinpearl.com/vi/kham-pha-y-nghia-va-nhung-trai-nghiem-doc-dao-tai-7-le-hoi-o-ha-tinh?fbclid=IwAR0a16Jnu2pyCyEMt62PpP__6J2Bq15PCJchWA1KzPf_G0p91luCgBp6tFA";
            doc = Jsoup.connect(url).get();

            Elements elements = doc.select("#block-porto-content > div > div.container.detail.news-full > div > div.main-body-wrapper > div.content-wrapper.read-more > div.content");
            Elements nameElements = elements.select("h2");

            for (Element nameElement: nameElements) {
                String temp = nameElement.text();
                int separatorIndex = temp.indexOf("."); // Find the index of the separator ". "
                temp = temp.substring(separatorIndex + 2);
                String tempArr[] = temp.split("[-:\\?]");
                if (tempArr.length == 2) {
                	if (tempArr[0].contains("Hà Tĩnh")) {
                		name.add(tempArr[1].trim());
                	}
                	else {
                		name.add(tempArr[0].trim());
                	}
                }
                else {
                	name.add(temp);
                }       
            }

            String currentLocation = ""; 
            String currentDate = "";

                //Get startingDay, location, description
            Elements locTimeElements = elements.select("ul");
                
            for (Element locTimeElement: locTimeElements) {
                Element firstP = locTimeElement.nextElementSibling(); 

                while (!firstP.tagName().equals("p")) {
                    firstP = firstP.nextElementSibling();
                }

                description.add(firstP.text());
                String temp = locTimeElement.text();
                if (temp.contains("Địa điểm:")) {
                    currentLocation = temp.substring(temp.indexOf(":") + 2, temp.indexOf("Thời gian tổ chức tham khảo:"));
                    location.add(currentLocation);
                    temp = temp.substring(temp.indexOf("Thời gian tổ chức tham khảo:"));
                } 

                if (temp.contains("Thời gian tổ chức tham khảo:")) {
                    currentDate = temp.substring(temp.indexOf(":") + 2);
                    startingDay.add(currentDate);
                }
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < name.size(); i++) {
        	List<String> relatedRelics = new ArrayList<>();
        	getRelatedRelics(relatedRelics, name.get(i));
            festivals_HT.add(new Festival(name.get(i), location.get(i), "Không rõ", startingDay.get(i), description.get(i), relatedFigures, relatedRelics));
            System.out.println(name.get(i));
            System.out.println(location.get(i));
            System.out.println(startingDay.get(i));
            System.out.println(description.get(i));
            System.out.println(relatedFigures);
            System.out.println("---------------------------");
        }

        return festivals_HT;
    }

    public List<Festival> crawlFesTN() {
        Document doc;
        List<Festival> festivals_TN = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<String> startingDay = new ArrayList<>();
        List<String> location = new ArrayList<>();
        List<String> description = new ArrayList<>();
        List<String> relatedFigures = new ArrayList<>();

        try {
            String url = "https://vinpearl.com/vi/le-hoi-tay-ninh-kham-pha-7-su-kien-dac-sac-bac-nhat-dat-thanh?fbclid=IwAR28z8FgSAt31a9S4sM_5nM6cQ2iH4gSv9HzqM0CGZWmkCIpHr3243U5_2U";
            doc = Jsoup.connect(url).get();

            Elements elements = doc.select("#block-porto-content > div > div.container.detail.news-full > div > div.main-body-wrapper > div.content-wrapper.read-more > div.content");
            Elements nameElements = elements.select("h2");

            for (Element nameElement: nameElements) {
                String temp = nameElement.text();
                int separatorIndex = temp.indexOf("."); // Find the index of the separator ". "
                temp = temp.substring(separatorIndex + 2);
                String tempArr[] = temp.split("-");
                if (tempArr.length == 2) {
                	if (tempArr[0].contains("Tây Ninh")) {
                		name.add(tempArr[1].trim());
                	}
                	else {
                		name.add(tempArr[0].trim());
                	}
                }
                else {
                	name.add(temp);
                }                
            }

            String currentLocation = ""; 
            String currentDate = "";

                //Get startingDay, location, description
            Elements locTimeElements = elements.select("ul");
                
            for (Element locTimeElement: locTimeElements) {
                Element firstP = locTimeElement.nextElementSibling(); 

                while (!firstP.tagName().equals("p")) {
                    firstP = firstP.nextElementSibling();
                }

                description.add(firstP.text());
                String temp = locTimeElement.text();
                if (temp.contains("Thời gian diễn ra tham khảo:")) {
                    currentDate = temp.substring(temp.indexOf(":") + 2, temp.indexOf("Địa điểm tổ chức:"));
                    startingDay.add(currentDate);
                    temp = temp.substring(temp.indexOf("Địa điểm tổ chức:"));
                } 

                if (temp.contains("Địa điểm tổ chức:")) {
                    currentLocation = temp.substring(temp.indexOf(":") + 2);
                    location.add(currentLocation);
                }
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < name.size(); i++) {
        	List<String> relatedRelics = new ArrayList<>();
        	getRelatedRelics(relatedRelics, name.get(i));
            festivals_TN.add(new Festival(name.get(i), location.get(i), "Không rõ", startingDay.get(i), description.get(i), relatedFigures, relatedRelics));
            System.out.println(name.get(i));
            System.out.println(location.get(i));
            System.out.println(startingDay.get(i));
            System.out.println(description.get(i));
            System.out.println(relatedFigures);
            System.out.println("---------------------------");
        }

        return festivals_TN;
    }

    public List<Festival> crawlFesBRVT() {
        Document doc;
        List<Festival> festivals_BRVT = new ArrayList<>();

        try {
            String url = "https://baria-vungtau.gov.vn/sphere/baria/vungtau/page/print.cpx?uuid=5c00ed8c1bd20b280b643764&fbclid=IwAR23tVZkTbC7DBboNIUgDs2Wt1pUAOrk20oSixGDr9QlDQG7w0QSX5WJcWc";
            doc = Jsoup.connect(url).get();

            Elements tables = doc.select("body > div > div > div > div.print-view-content.content > table");

            for (Element table: tables) {
                Elements rows  = table.select("tr");

                for (int i = 1; i < rows.size(); i++) {
                Elements cells = rows.get(i).select("td");

                String name = cells.get(1).text();
                String startingDay = cells.get(2).text();
                String location = cells.get(3).text();
                String firstTime = "Không rõ";
                String description = cells.get(4).text();
                List<String> relatedFigures = new ArrayList<>();
                List<String> relatedRelics = new ArrayList<>();
                
            	getRelatedRelics(relatedRelics, name);

                if (description.isEmpty()) description = "Không rõ";
                if (location.isEmpty()) location = "Không rõ";

                festivals_BRVT.add(new Festival(name, location, firstTime, startingDay, description, relatedFigures, relatedRelics));
                System.out.println(name);
                System.out.println(startingDay);
                System.out.println(location);
                System.out.println(firstTime);
                System.out.println(description);
                System.out.println(relatedFigures);
                System.out.println("---------------");
            }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return festivals_BRVT;
    }
    
    public List<Festival> crawlFesHN() {
    	Document doc;
    	List<Festival> festivals_HN = new ArrayList<>();
    	String url = "https://evbn.org/danh-sach-cac-le-hoi-o-ha-noi-1650760290/";
    	
    	try {
			doc = Jsoup.connect(url).get();
			Elements pElements = doc.select("div#maincontent div.content-post > div:nth-of-type(2) > p");
			for (int i=0; i<pElements.size()-1; i++) {
				Element pElement = pElements.get(i);
				String name = pElement.selectFirst("strong").text();
				String startingDay = "Không rõ";
                String location = "Không rõ";
                String firstTime = "Không rõ";
                String description = "Không rõ";
                List<String> relatedFigures = new ArrayList<>();
                List<String> relatedRelics = new ArrayList<>();
				
				List<TextNode> textNodes = pElement.textNodes();
				for (TextNode textNode : textNodes) {
					String text = textNode.text();
					if (text.contains("Thời gian:")) {
						startingDay = text.replace("Thời gian:", "").trim();
					}
					else if (text.contains("Địa điểm:") || text.contains("Địa điểm tổ chức:")) {
						location = text.replace("Địa điểm:", "").replace("Địa điểm tổ chức:","").trim();
					}
					else if (text.contains("Tôn vinh:")) {						
						text = text.replace("Tôn vinh:", "").replace("Quốc Tổ", "").replace("Quốc Mẫu", "")
								.replace("thần", "Thần").replace("đại vương", "Đại Vương");
						Pattern pattern = Pattern.compile("(\\p{Lu}\\p{Ll}*\\s+){1,}(\\p{Lu}\\p{Ll}*)");
						Matcher matcher = pattern.matcher(text);
						while (matcher.find()) {
							String newFigure = matcher.group();
							if (!relatedFigures.contains(newFigure) && 
								!newFigure.equals("Thanh Oai") && 
								!newFigure.equals("Việt Nam")) {
								if (newFigure.equals("Tản Viên")) {
									newFigure = "Đức Thánh Tản Viên";
								}
								relatedFigures.add(newFigure);
							}							
						}
						System.out.println(text);
					}
					else if (text.contains("Đặc điểm:")) {
						description = text.replace("Đặc điểm:", "").trim();
						getRelatedRelics(relatedRelics, name);
					}
				}
				System.out.println(name);
                System.out.println(startingDay);
                System.out.println(location);
                System.out.println(firstTime);
                System.out.println(description);
                System.out.println(relatedFigures);
                System.out.println(relatedRelics);
				System.out.println("------------");
				
				festivals_HN.add(new Festival(name, location, firstTime, startingDay, description, relatedFigures, relatedRelics));
			}
		} catch (IOException e) {			
			e.printStackTrace();
		}    	
    	
    	return festivals_HN;
    }
    
    //list 2 is added to base list 1, list 2 is a list of festivals in a province
    public List<Festival> merge(List<Festival> list1, List<Festival> list2, String province) {
    	List<Festival> provinceFests = new ArrayList<>();
    	for (Festival festival1 : list1) {
    		if (festival1.getLocation().contains(province)) {
    			provinceFests.add(festival1);
    		}
    	}
    	
    	for (Festival festival2: list2) {
    		String festival2Name = festival2.getName().replaceAll("Hội|Lễ hội|Lễ", "").trim();
    		boolean isFound = false;
    		for (Festival provinceFest : provinceFests) {
    			String provinceFestName = provinceFest.getName().replaceAll("Hội|Lễ hội|Lễ", "").trim();
    			if (provinceFestName.toLowerCase().contains(festival2Name.toLowerCase()) || 
    				festival2Name.toLowerCase().contains(provinceFestName.toLowerCase())) {
    				
    				if (provinceFest.getDescription().equals("Không rõ")) {
    					provinceFest.setDescription(festival2.getDescription());
    				}
    				Set<String> newRelics = festival2.getRelatedRelics().keySet();
    				Set<String> newFigures = festival2.getRelatedFigures().keySet();
    				for (String newRelic : newRelics) {
    					provinceFest.addRelatedRelics(newRelic, 0);
    				}
    				for (String newFigure : newFigures) {
    					provinceFest.addRelatedFigures(newFigure, 0);
    				}
    				System.out.println("Updated: " + provinceFest.getName());
    				System.out.println("Removed: " + festival2.getName());
    				isFound = true;
    				break;
    			}
    		}
    		if (!isFound) {
    			list1.add(festival2);
    		}
    	}
    	return list1;
    }
    
    public static void main(String[] args) {
        FestivalCrawler festivalCrawler = new FestivalCrawler();
        festivalCrawler.crawl();
    }
}
