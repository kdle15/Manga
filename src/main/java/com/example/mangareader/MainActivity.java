package com.example.mangareader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MainActivity extends Activity {
    ImageView tv;
    Spinner spinner;
    Button submit;
    EditText text;
    String Blog = null;
    String onClik_manga = null;
    LinearLayout querry;
    private final String querry1 = "https://m.blogtruyen.com/timkiem?keyword=";
    ArrayList<Querry_Item> ar_querry_item = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Spinner
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Blog, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Blog = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //TextBox
        text = findViewById(R.id.edit_text);
        submit = findViewById(R.id.button_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = text.getText().toString();
                switch (Blog) {
                    case "BlogTruyen":
                        try {
                            ar_querry_item = new Querry_Link().execute(querry1 + content).get();
                            querry.removeAllViews();
                            if(ar_querry_item != null){
                                System.out.println("How many " + ar_querry_item.size());
                                for(final Querry_Item q: ar_querry_item) {
                                    TextView tv = new TextView(getApplicationContext());
                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.WRAP_CONTENT, // Width of TextView
                                            LinearLayout.LayoutParams.WRAP_CONTENT); // Height of TextView

                                    // Apply the layout parameters to TextView widget
                                    tv.setLayoutParams(lp);
                                    tv.setText(q.getTitle());
                                    tv.setTextSize(20);
                                    tv.setClickable(true);
                                    tv.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            onClik_manga = q.getUrl();
                                            //new intent go here
                                            Intent i = new Intent(MainActivity.this, MangaSumary.class);
                                            onClik_manga = onClik_manga.substring(0, 8) + onClik_manga.substring(10);
                                            i.putExtra("EXTRA_MESSAGE", onClik_manga);
                                            startActivity(i);

                                            System.out.println("all link go here " + onClik_manga);
                                        }
                                    });
                                    // Set a text color for TextView text
                                    tv.setTextColor(Color.WHITE);
                                    querry.addView(tv);
                                }
                            }else{
                                //no such item exists
                                TextView tv = new TextView(getApplicationContext());
                                tv.setText("NO SUCH MANGA");
                                tv.setTextSize(22);
                                tv.setTextColor(Color.WHITE);
                                querry.addView(tv);

                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "NetTruyen":

                        break;
                    default:

                        break;
                }
            }
        });

        ar_querry_item = new ArrayList<>();
        querry = findViewById(R.id.querryLinear);
    }

    public Drawable scaleImage (Drawable image, float scaleFactor) {

        if ((image == null) || !(image instanceof BitmapDrawable)) {
            return image;
        }

        Bitmap b = ((BitmapDrawable)image).getBitmap();
        //create bitmap 4 time bigger than normal

        int sizeX = image.getIntrinsicWidth()/4;
        int sizeY = image.getIntrinsicHeight()/4;

        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, sizeX, sizeY, false);

        image = new BitmapDrawable(getResources(), bitmapResized);

        return image;

    }

    public static Bitmap getScaleBitmap(Bitmap bitmap) {
        //allow to hide part of the image dont need

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(36, 0, bitmap.getWidth() - 36 , bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 0;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}
