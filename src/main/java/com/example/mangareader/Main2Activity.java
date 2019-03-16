package com.example.mangareader;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity extends Activity {
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private  ArrayList<String> all_url;
    private int current;
    private ImageView iv;
    private int mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        String[] url = new String[1];
        if(b!= null){
            all_url = b.getStringArrayList("ALLURLs");
            mode = b.getInt("READ_MODE");
            current = Integer.parseInt(b.getString("CURRENT_INDEX"));
            url[0] = all_url.get(all_url.size() - 1  - current);
            url[0] = url[0].substring(0,8) + url[0].substring(10);
            System.out.println("get url" + url[0]);
        }

        this.recyclerView = findViewById(R.id.rv_images);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,
                        false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);

        //on scrollListener
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int m = 0;
            int n = 0;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                if (!recyclerView.canScrollVertically(1)) {
                    if(current + 1 < all_url.size() && m == 4){
                        Intent i = new Intent(Main2Activity.this, Main2Activity.class);
                        i.putStringArrayListExtra("ALLURLs", all_url);
                        i.putExtra("CURRENT_INDEX", String.valueOf(current + 1));
                        i.putExtra("READ_MODE", mode);
                        startActivity(i);
                        finish();
                    }
                    Toast.makeText(Main2Activity.this, "Last " + m, Toast.LENGTH_LONG).show();
                    m++;
                }

                if (!recyclerView.canScrollVertically(-1)) {
                    if(current > 0 && n == 4){
                        Intent i = new Intent(Main2Activity.this, Main2Activity.class);
                        i.putStringArrayListExtra("ALLURLs", all_url);
                        i.putExtra("CURRENT_INDEX", String.valueOf(current - 1));
                        i.putExtra("READ_MODE", mode);
                        startActivity(i);
                        finish();
                    }
                    Toast.makeText(Main2Activity.this, "Top " + n, Toast.LENGTH_LONG).show();
                    n++;
                }
            }
        });

        this.imageAdapter = new ImageAdapter(ImageURLInterface.create(url[0]), this, mode);
        recyclerView.setAdapter(this.imageAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                        DividerItemDecoration.VERTICAL));
    }
}
