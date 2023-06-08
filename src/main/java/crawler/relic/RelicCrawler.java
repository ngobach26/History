package crawler.relic;

import java.io.IOException;
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
import model.Relic;

public class RelicCrawler implements ICrawler {
    private JsonIO<Relic> relicIO = new JsonIO<>(new TypeToken<ArrayList<Relic>>() {}.getType());

    @Override
    public void crawl() {
        relicIO.writeJson(crawlWiki(), "src/main/resources/json/Relics.json");
    }
    
    public ArrayList<Relic> crawlWiki() {
        Document doc;
        ArrayList<Relic> relics = new ArrayList<>();
        
        try {
        	String url = URLDecoder.decode("https://vi.wikipedia.org/wiki/Danh_s%C3%A1ch_Di_t%C3%ADch_qu%E1%BB%91c_gia_Vi%E1%BB%87t_Nam", StandardCharsets.UTF_8.name());
            doc = Jsoup.connect(url).get();
            Elements tables = doc.select("table.wikitable");
            Element tableNinhBinh = doc.selectFirst("#mw-content-text > div.mw-parser-output > table:nth-child(55)");

            for (Element table : tables) {
                if (table == tableNinhBinh) {
                    Elements rows = table.select("tr");
                    for (int i = 1; i < rows.size(); i++) {
                        Elements cells = rows.get(i).select("td");

                        String name = cells.get(1).text();
                        String location = cells.get(2).text();
                        String category = "Không rõ";
                        String approvedYear = "Không rõ";
                        String overView = cells.get(3).text();

                        //Add relic
                        relics.add(new Relic(name, location, category, approvedYear, overView));
                        System.out.println(name);
                        System.out.println(location);
                        System.out.println(category);
                        System.out.println(approvedYear);
                        System.out.println(overView);                 
                        System.out.println("-------------");
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
                        String overView = "Không rõ";

                        if (name.isEmpty()) name = "Không rõ";
                        if (location.isEmpty()) location = "Không rõ";
                        if (category.isEmpty()) category = "Không rõ";
                        if (approvedYear.isEmpty()) approvedYear = "Không rõ";

                        //Add relic
                        relics.add(new Relic(name, location, category, approvedYear, overView));
                        System.out.println(name);
                        System.out.println(location);
                        System.out.println(category);
                        System.out.println(approvedYear);
                        System.out.println(overView);                 
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

    public static void main(String[] args) {
        RelicCrawler relicCrawler = new RelicCrawler();
        relicCrawler.crawl();
    }
}