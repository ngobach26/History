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
import crawler.JsonIO;
import crawler.figure.FigureCrawler;
import model.Relic;

public class RelicCrawler implements ICrawler {
    private JsonIO<Relic> relicIO = new JsonIO<>(new TypeToken<ArrayList<Relic>>() {}.getType());

    @Override
    public void crawl() {
        relicIO.writeJson(crawlWiki(), "src/main/resources/json/Relics.json");
    }
    
    public List<Relic> crawlWiki() {
        Document doc;
        List<Relic> relics = new ArrayList<>();
        
        try {
        	String url = URLDecoder.decode("https://vi.wikipedia.org/wiki/Danh_s%C3%A1ch_Di_t%C3%ADch_qu%E1%BB%91c_gia_Vi%E1%BB%87t_Nam", StandardCharsets.UTF_8.name());
            doc = Jsoup.connect(url).get();
            Elements tables = doc.select("table.wikitable");
            Element tableNinhBinh = doc.selectFirst("#mw-content-text > div.mw-parser-output > table:nth-child(55)");
            Set<String> surnames = new FigureCrawler().getUniqueSurnames();

            for (Element table : tables) {
                if (table == tableNinhBinh) {
                    Elements rows = table.select("tr");
                    for (int i = 1; i < rows.size(); i++) {
                        Elements cells = rows.get(i).select("td");

                        String name = cells.get(1).text();
                        String location = cells.get(2).text();
                        String category = "Không rõ";
                        String approvedYear = "Không rõ";
                        String description = cells.get(3).text();
                        
                        Element aTag = cells.get(1).selectFirst("a[href]");
                        if (aTag != null) {
                        	String link = URLDecoder.decode(aTag.attr("abs:href"), StandardCharsets.UTF_8.name());
                        	if (!link.contains("/w/index.php?")) {
                        		Document relicDoc = Jsoup.connect(link).get();
                        		Element overviewPara = relicDoc.selectFirst("div.mw-parser-output > p");
                        		if (overviewPara != null) {
                        			overviewPara.select("sup").remove();
                        			description = overviewPara.text();
                        		}
                        	}
                        }

                        //Add relic
//                        relics.add(new Relic(name, location, category, approvedYear, description));
//                        System.out.println(name);
//                        System.out.println(location);
//                        System.out.println(category);
//                        System.out.println(approvedYear);
//                        System.out.println(description);                 
//                        System.out.println("-------------");
                    }
                    continue;
                }

                Elements rows = table.select("tr");

                for (int i = 1; i < rows.size(); i++) {
                    try {
                        Elements cells = rows.get(i).select("td");

                        String name = cells.get(0).text();
                        String location = cells.get(1).text();
                        String category = cells.get(2).text();
                        String approvedYear = cells.get(3).text();
                        String description = "Không rõ";

                        if (name.isEmpty()) name = "Không rõ";
                        if (location.isEmpty()) location = "Không rõ";
                        if (category.isEmpty()) category = "Không rõ";
                        if (approvedYear.isEmpty()) approvedYear = "Không rõ";
                        
                        Element aTag = cells.get(0).selectFirst("a[href]");
                        if (aTag != null) {
                        	String link = URLDecoder.decode(aTag.attr("abs:href"), StandardCharsets.UTF_8.name());
                        	if (!link.contains("/w/index.php?")) {
                        		Document relicDoc = Jsoup.connect(link).get();
                        		Element overviewPara = relicDoc.selectFirst("div.mw-parser-output > p");
                        		if (overviewPara != null) {
                        			overviewPara.select("sup").remove();
                        			description = overviewPara.text();
                        		}
                        		if (category.contains("Lịch sử")) {
                        			Elements paras = relicDoc.select("div.mw-parser-output > p");
                            		Pattern pattern = Pattern.compile("(\\p{Lu}\\p{Ll}*\\s+){1,}(\\p{Lu}\\p{Ll}*)");
                        			List<String> potentialFigureNames = new ArrayList<>();
                            		for (Element para: paras) {
                            			Matcher matcher = pattern.matcher(para.text());
                            	        while (matcher.find()) {
                            	            String potentialName = matcher.group().replaceAll("Động|Bia|Trận|Thành|Núi|Bộ|Đèo|Hang|Đình|Đền|Chùa|Tháp|Người|Tiếng|Bến|Phía|Theo|Nhà|Thời|Suối|Sông|Đảng|Xã|Huyện|Tỉnh|Thác|Hội|Đồn|Tại|Đàng|Sau|Châu|Biển|Chữ|Hàng|Tổng", "").trim();
                            	            int length = potentialName.split("\\s+").length;
                            	            String surname = (potentialName.split("\\s+"))[0];
                            	            if (!potentialFigureNames.contains(potentialName) && surnames.contains(surname) && length > 1) {
                            	            	potentialFigureNames.add(potentialName.replaceAll("\\s+", " "));
                            	            }
                            	        }
                            		}
                        	        System.out.println(potentialFigureNames);
                            		System.out.println("-------------");
                        		}
                        	}
                        }

                        //Add relic
//                        relics.add(new Relic(name, location, category, approvedYear, description));
//                        System.out.println(name);
//                        System.out.println(location);
//                        System.out.println(category);
//                        System.out.println(approvedYear);
//                        System.out.println(description);                 
//                        System.out.println("-------------");
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

    public static void main(String[] args) {
        RelicCrawler relicCrawler = new RelicCrawler();
        relicCrawler.crawlWiki();
    }
}