package com.example.mangareader;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.graphics.BitmapCompat;
import android.widget.ImageView;

/**
 * class to taking a list of urls and download the corresponding images
 */
public class DownloadBitmapTask extends AsyncTask<String, Void, Bitmap> {

    private ImageAdapter.ImageAdapterViewHolder viewHolder;
    private String url;

    public DownloadBitmapTask(ImageAdapter.ImageAdapterViewHolder viewHolder){
        this.viewHolder = viewHolder;
    }

    public String getUrl(){
        return url;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        @SuppressLint("WrongThread")
        Bitmap bm = Utility.downloadBitmap(urls[0],
                viewHolder.imageView.getMaxWidth(), viewHolder.imageView.getMaxHeight() );
        this.url = urls[0];
        return bm;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if(this.isCancelled()) return;

        ImageView iv = viewHolder.imageView;
        Rect bounds = iv.getDrawable().getBounds();
        int scaledHeight = bounds.height();
        int scaledWidth = bounds.width();
        DownloadBitmapTask iv_task = AsyncDrawable.getDownloadBitmapTaskReference(iv);

        if(this == iv_task && bitmap != null && iv != null){
            Drawable image = this.viewHolder.getV();
            if(image != null ) {
                //create bitmap 4 time bigger than normal
                int bitmapByteCount= BitmapCompat.getAllocationByteCount(bitmap);
                System.out.println("total byte is here " + bitmapByteCount);
                if (bitmap != null) {
                    //attempt 1L
//                    if(bitmapByteCount > 30000000){
//                        if(bitmap.getByteCount() < 35000000){
//                            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()*2 , bitmap.getHeight()*2 , false);
//                        }else{
//                            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()*2 , bitmap.getHeight()*2, false);
//                        }
//
//                        System.out.println("total byte is here go here 1" + + bitmapByteCount);
//                        this.viewHolder.imageView.setImageBitmap(bitmap);
//                    }else if (bitmap.getHeight() > 1300 || bitmap.getWidth() > 900 ) {
//                        this.viewHolder.imageView.setImageBitmap(
//                                Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * 2, bitmap.getHeight() * 2, false));
//                    }
//                    else {
//                        this.viewHolder.imageView.setImageBitmap(
//                                Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()*2, bitmap.getHeight()*2, false));
//
//                    }
                    //attemep2:
                    if(bitmap.getWidth()*2 > 2000){
                        System.out.println("go here " + bitmap.getWidth() + ", " + scaledWidth);
                        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()*2 , bitmap.getHeight()*2, false);
                    }else{
                        if(bitmap.getByteCount() < 30000000){
                            bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()*2 , bitmap.getHeight()*2 , false);
                        }else{
                           bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()*2 , bitmap.getHeight()*2, false);
                        }
                    }
                    this.viewHolder.imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}
