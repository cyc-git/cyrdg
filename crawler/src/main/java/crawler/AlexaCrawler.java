package crawler;

import java.util.List;
import java.util.concurrent.CompletionStage;

import crawler.data.Site;

public interface AlexaCrawler {

	CompletionStage<List<Site>> top(int number);

	CompletionStage<List<Site>> country(String country);
}
