package com.example.mangareader;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Querry_Link extends AsyncTask<String, Void, ArrayList<Querry_Item>> {
    private final String prefix1 = "https://m.blogtruyen.com";
    @Override
    protected ArrayList<Querry_Item> doInBackground(String... urls) {
        ArrayList<Querry_Item> list = new ArrayList<>();
        final StringBuilder builder = new StringBuilder();

        try {
            Document doc = Jsoup.connect(urls[0]).get();
            String title = doc.title();
            Elements links = doc.select("table");
            org.jsoup.nodes.Element table = links.first();
            if(table != null){
                Element table_body = table.child(0);
                for (Element link : table_body.children()) {
                    Element l = link.getElementsByTag("a").first();
                    Querry_Item q = new Querry_Item(l.attr("title"), prefix1 + l.attr("href"));
                    list.add(q);
                }
            }else{
                return null;
            }
        } catch (IOException e) {
            builder.append("Error : ").append(e.getMessage()).append("\n");
        }

        return list;
    }
}
