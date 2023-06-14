package crawler.festival;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
        allFestivals.addAll(crawlFesND());
        allFestivals.addAll(crawlFesAG());
        allFestivals.addAll(crawlFesLS());
        allFestivals.addAll(crawlFesQNam());
        allFestivals.addAll(crawlFesHue());
        allFestivals.addAll(crawlFesTH());
        allFestivals.addAll(crawlFesNA());
        allFestivals.addAll(crawlFesHP());
        allFestivals.addAll(crawlFesHCM());
        allFestivals.addAll(crawlFesQNinh());
        allFestivals.addAll(crawlFesBN());
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
                List<String> relatedFigures = new ArrayList<>();

                //Default
                if (name.isEmpty()) name = "Không rõ";
                if (firstTime.isEmpty()) firstTime = "Không rõ";
                if (startingDay.isEmpty()) startingDay = "Không rõ";
                if (location.isEmpty()) location = "Không rõ";
                if (tempString.isEmpty()) tempString = "Không rõ";
            
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
                festivals.add(new Festival(name, location, firstTime, startingDay, description, relatedFigures));

                System.out.println(name);
                System.out.println(location);
                System.out.println(startingDay);
                System.out.println(firstTime);
                System.out.println(relatedFigures);
                System.out.println("-------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return festivals;
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
                List<String> relatedFigures = new ArrayList<>();

                festivals_ND.add(new Festival(name, location, firstTime, startingDay, description, relatedFigures));
                System.out.println(name);
                System.out.println(startingDay);
                System.out.println(location);
                System.out.println(firstTime);
                System.out.println(description);
                System.out.println(relatedFigures);
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
                    name.add(nameElement.text().substring(nameElement.text().indexOf(" ") + 1));
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
            festivals_LS.add(new Festival(name.get(i), location.get(i), "Không rõ", startingDay.get(i), description.get(i), relatedFigures));
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
                String temp1 = temp.substring(separatorIndex + 2);
                name.add(temp1);
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
            festivals_QNam.add(new Festival(name.get(i), location.get(i), "Không rõ", startingDay.get(i), description.get(i), relatedFigures));
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

        for (int i = 0; i < name.size(); i++) {
            festivals_NA.add(new Festival(name.get(i), location.get(i), "Không rõ", startingDay.get(i), description.get(i), relatedFigures));
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
                if (temp.contains("Địa chỉ:")) {
                    currentDate = temp.substring(temp.indexOf(":") + 2, temp.indexOf("Thời gian tổ chức tham khảo:"));
                    startingDay.add(currentDate);
                    temp = temp.substring(temp.indexOf("Thời gian tổ chức tham khảo:"));
                } 

                if (temp.contains("Thời gian tổ chức tham khảo:")) {
                    currentLocation = temp.substring(temp.indexOf(":") + 2);
                    location.add(currentLocation);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 15; i++) {
            festivals_TH.add(new Festival(name.get(i), location.get(i), "Không rõ", startingDay.get(i), description.get(i), relatedFigures));
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
            festivals_HP.add(new Festival(name.get(i), location.get(i), "Không rõ", startingDay.get(i), description.get(i), relatedFigures));
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
            festivals_HCM.add(new Festival(name.get(i), location.get(i), "Không rõ", startingDay.get(i), description.get(i), relatedFigures));
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
                String temp1 = temp.substring(separatorIndex + 2);
                name.add(temp1);
                System.out.println(temp1);
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
                    startingDay.add(currentLocation);
                    temp = temp.substring(temp.indexOf("Thời gian diễn ra tham khảo:"));
                } 

                if (temp.contains("Thời gian diễn ra tham khảo:")) {
                    currentDate = temp.substring(temp.indexOf(":") + 2);
                    location.add(currentDate);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i++) {
            festivals_QNinh.add(new Festival(name.get(i), location.get(i), "Không rõ", startingDay.get(i), description.get(i), relatedFigures));
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
            festivals_Hue.add(new Festival(name.get(i), location.get(i), "Không rõ", startingDay.get(i), description.get(i), relatedFigures));
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

                startingDay = lineText.substring(lineText.indexOf(":") + 2, lineText.indexOf("Địa điểm:"));
                lineText = lineText.substring(lineText.indexOf("Địa điểm:"));
                System.out.println(startingDay);
                
                location = lineText.substring(lineText.indexOf(":") + 2, lineText.indexOf("Đối tượng suy tôn:"));
                lineText = lineText.substring(lineText.indexOf("Đối tượng suy tôn:"));
                System.out.println(location);
                

                related = lineText.substring(lineText.indexOf(":") + 2, lineText.indexOf("Đặc điểm:"));
                lineText = lineText.substring(lineText.indexOf("Đặc điểm: "));
                relatedFigures.add(related);
                System.out.println(related);

                description = lineText.substring(lineText.indexOf(":") + 2);
                System.out.println(description);
                
                System.out.println("--------------------");

                festivals_AG.add(new Festival(name, location, related, startingDay, description, relatedFigures));

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
                String nameString = name.get(idx++);
                String lineText = pElement.text();
                String loc = "";
                String startingD = "";
                String descrip = "";
                String related = "";

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

                festivals_BN.add(new Festival(nameString, loc, "Không rõ", startingD, descrip, relatedFigures));
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

    public static void main(String[] args) {
        FestivalCrawler festivalCrawler = new FestivalCrawler();
        festivalCrawler.crawl();
    }
}
