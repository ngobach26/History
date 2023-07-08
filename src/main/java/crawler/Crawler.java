package crawler;

import crawler.era.EraCrawler;
import crawler.event.EventCrawler;
import crawler.festival.FestivalCrawler;
import crawler.figure.FigureCrawler;
import crawler.linker.Linker;
import crawler.relic.RelicCrawler;

public class Crawler implements ICrawler{

	@Override
	public void crawl() {
		new FigureCrawler().crawl();;
		new EraCrawler().crawl();;
		new EventCrawler().crawl();;
		new FestivalCrawler().crawl();;
		new RelicCrawler().crawl();;
		new Linker().link();	
	}

}
