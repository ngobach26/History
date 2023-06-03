package crawler;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GoogleCrawler {
	public static String crawl(String text) {
		String result = "Không rõ";
		try {
			String url = URLDecoder.decode("https://www.google.com/search?q=" + text, StandardCharsets.UTF_8.name());

			Document doc = Jsoup.connect(url).get();
			
			if (doc.selectFirst("a.FLP8od") != null) {
				result = doc.selectFirst("a.FLP8od").text();
			}
			else if (doc.selectFirst("span.hgKElc > b") != null) {
				result = doc.selectFirst("span.hgKElc > b").text();
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static ArrayList<String> advancedCrawl(String text) {
		ArrayList<String> results = new ArrayList<>();
		try {
			String url = URLDecoder.decode("https://www.google.com/search?q=" + text, StandardCharsets.UTF_8.name());

			Document doc = Jsoup.connect(url).get();
			
			if (doc.selectFirst("a.FLP8od") != null) {
				results.add(doc.selectFirst("a.FLP8od").text());
			}
			else if (doc.selectFirst("span.hgKElc > b") != null) {
				results.add(doc.selectFirst("span.hgKElc > b").text());
			}
			else if (doc.select("div.bVj5Zb.FozYP") != null) {
				Elements divs =  doc.select("div.bVj5Zb.FozYP");
				for (Element div:divs) {
					results.add(div.text());
				}
			}
			else if (doc.select(".dAassd .FozYP") != null) {
				Elements divs = doc.select(".dAassd .FozYP");
				for (Element div:divs) {
					results.add(div.text());
				}
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return results;
	}
}
