package crawler.festival;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
		festivalIO.writeJson(crawlWiki(), "src/main/resources/json/Festivals.json");
	}

    public ArrayList<Festival> crawlWiki() {
        Document doc;
        ArrayList<Festival> festivals = new ArrayList<>();

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
                ArrayList<String> relatedFigures = new ArrayList<>();

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
                            relatedFigures.add(temp);
                            temp = "";
                        }

                    }
                }

                //Add festivals
                festivals.add(new Festival(name, location, firstTime, startingDay, relatedFigures));

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

    public static void main(String[] args) {
        FestivalCrawler festivalCrawler = new FestivalCrawler();
        festivalCrawler.crawl();
    }
}
