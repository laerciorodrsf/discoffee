package com.laerciorodrsf.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BraveImageScraper {
    
    private static final String URL = "https://search.brave.com/images?q=";

    public List<String> search(String query) throws IOException {
        String url = URL + query.replace(" ", "+");

        Document document = Jsoup.connect(url)
            .userAgent(
                    "Mozilla/5.0 (X11; Linux x86_64) " +
                    "AppleWebKit/537.36 " +
                    "(KHTML, like Gecko) " +
                    "Chrome/140.0.0.0 Safari/537.36"
                )
                .header("Accept-Language", "pt-BR,pt;q=0.9")
                .timeout(10_000)
                .get();

        Elements images = document.select("img[data-rank]");

        List<String> results = new ArrayList<>();

        for (Element image : images) {
            String src = image.attr("src");

            if (!src.isBlank()) {
                results.add(src);
            }

            if (results.size() >= 10) {
                break;
            }
        }

        return results;
    }
}
