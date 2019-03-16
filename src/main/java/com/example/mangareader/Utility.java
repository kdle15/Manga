package com.example.mangareader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Utility {
    private static final String TAG =
            Utility.class.getSimpleName();

    // https://developer.android.com/topic/performance/graphics/load-bitmap#java
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private static Bitmap decodeSampledBitmap(
            InputStream inputStream,
            BitmapFactory.Options options,
            int rw,
            int rh
    ) {
        options.inSampleSize = calculateInSampleSize(options, rw, rh);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(
                inputStream, null, options);
    }

    public static Bitmap downloadBitmap(String urls,
                                        int rw, int rh) {

        BitmapFactory.Options options  = downloadBitmapOptions(urls);
        if (options == null)
            return null;

        URL url = null;
        HttpURLConnection connection = null;

        try {
            url = new URL(urls);
            connection = (HttpURLConnection) url.openConnection();

            // TODO check response code for HTTP_OK

            InputStream inputStream =
                    new BufferedInputStream(connection.getInputStream());

            Bitmap bitmap = decodeSampledBitmap(
                    inputStream, options, rw, rh);
            inputStream.close();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            // TODO error log
            return null;
        } catch (IOException e) {
            // TODO error log
            e.printStackTrace();
            return null;
        }
        finally {
            if (connection != null)
                connection.disconnect();
        }

    }

    private static BitmapFactory.Options
    downloadBitmapOptions(String urls) {

        URL url = null;
        HttpURLConnection connection = null;

        try {
            url  = new URL(urls);
            connection = (HttpURLConnection)
                    url.openConnection();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, "Error while opening url" + urls);
                return null;
            }

            InputStream inputStream = null;
            inputStream = new BufferedInputStream(
                    connection.getInputStream());
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(
                    inputStream,null, options);
            inputStream.close();
            return options;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, "malformed URL " + urls);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Coult not open connection " + urls);
            return null;
        }
        finally {
            if (connection != null)
                connection.disconnect();
        }


    }

}
