package crawler.impl;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletionStage;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.web.reactive.function.client.WebClient;

import crawler.AlexaCrawler;
import crawler.data.Site;
import reactor.core.publisher.Mono;

public class AlexaCrawlerImpl implements AlexaCrawler {
	private static final String baseUrl = "https://www.alexa.com";

	private WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();

	private Mono<Document> getBodyDocument(String uri) {
		return webClient.get()

				.uri(uri)

				.exchangeToMono(resp -> {
					if (resp.statusCode().is2xxSuccessful()) {
						return resp.bodyToMono(String.class);
					}
					return resp.createException().flatMap(Mono::error);
				})

				.map(body -> Parser.parse(body, baseUrl))

		;
	}

	@Override
	public CompletionStage<List<Site>> top(int number) {
		if (number <= 0) {
			return Mono.just(Collections.<Site>emptyList()).toFuture();
		}

		return this.top("/topsites", number).toFuture();
	}

	private Mono<List<Site>> top(String uri, int number) {
		return this.getBodyDocument(uri)

				.flatMapIterable(doc -> doc.getElementsByClass("site-listing"))

				.take(number)

				.map(this::convertListingElement)

				.collectList()

		;
	}

	@Override
	public CompletionStage<List<Site>> country(String country) {
		if (country == null || country.trim().isEmpty()) {
			return Mono.just(Collections.<Site>emptyList()).toFuture();
		}

		return this.getBodyDocument("/topsites/countries")

				.flatMapIterable(doc -> doc.getElementsByClass("tableContainer"))

				.flatMapIterable(ele -> ele.getElementsByTag("a"))

				.filter(ele -> country.equalsIgnoreCase(ele.text()))

				.take(1)

				.map(ele -> "/topsites/" + ele.attr("href"))

				.flatMap(uri -> this.top(uri, 20))

				.flatMapIterable(sites -> sites)

				.collectList()

				.toFuture()

		;
	}

	private Site convertListingElement(Element ele) {
		Site site = new Site();
		Elements eles = ele.getElementsByClass("td");
		if (!eles.isEmpty()) {
			try {
				site.setNumber(Integer.parseInt(eles.get(0).text()));
			} catch (NumberFormatException e) {
				site.setNumber(-1);
			}
		}

		eles = ele.getElementsByTag("a");
		if (!eles.isEmpty()) {
			Element a = eles.get(0);
			site.setUrl(baseUrl + a.attr("href"));
			site.setName(a.text());
		}
		return site;
	}
}
