package com.example.mangareader;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class AsyncDrawable extends BitmapDrawable {
    private final DownloadBitmapTask
            downloadBitmapTaskReference;

    public AsyncDrawable(
            Resources res,
            Bitmap bitmap,
            DownloadBitmapTask downloadBitmapTaskReference) {

        super(res, bitmap);  // placeholder bitmap
        this.downloadBitmapTaskReference =
                downloadBitmapTaskReference;

    }

    public DownloadBitmapTask getDownloadBitmapTaskReference() {
        return downloadBitmapTaskReference;
    }

    // Helper method to get the task reference given an
    // ImageView

    /**
     * Helper method to get the task reference given an ImageView
     * @param imageView - getting referent to task on this view
     * @return DownloadBitmapTask that imageView referred to
     *         or null.
     */
    public static DownloadBitmapTask getDownloadBitmapTaskReference(
            ImageView imageView
    )  {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                return ((AsyncDrawable) drawable)
                        .getDownloadBitmapTaskReference();
            }
        }
        return null;
    }
}
