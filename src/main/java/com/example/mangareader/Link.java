package com.example.mangareader;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Link extends AsyncTask<String, Void, ArrayList<String>> {
    @Override
    protected ArrayList<String> doInBackground(String... urls) {
        ArrayList<String> images = new ArrayList<>();
        final StringBuilder builder = new StringBuilder();

        try {
            Document doc = Jsoup.connect(urls[0]).get();
            String title = doc.title();
            Elements links = doc.select("article");
            org.jsoup.nodes.Element article = links.first();


            builder.append(title).append("\n");

            for (Element link : article.children()) {
                if(link.tagName().equals("img")){
                    images.add(link.attr("src"));
                }
            }
        } catch (IOException e) {
            builder.append("Error : ").append(e.getMessage()).append("\n");
        }

        return images;
    }

}
