package com.example.mangareader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageAdapterViewHolder> {
    private ImageURLInterface images;
    private Activity act;
    private Bitmap placeholder;
    private int mode;

    public ImageAdapter(ImageURLInterface images, Activity act, int i){
        this.images = images;
        this.act = act;
        this.placeholder = BitmapFactory.decodeResource(this.act.getResources(), R.drawable.p);
        this.mode = i;
    }
    @NonNull
    @Override
    public ImageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        ImageAdapterViewHolder viewHolder = new ImageAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapterViewHolder imageAdapterViewHolder, int i) {
        String url = images.get(i);
        if(cancelCurrentTask(url, imageAdapterViewHolder.imageView)){
            DownloadBitmapTask task = new DownloadBitmapTask(imageAdapterViewHolder);

            AsyncDrawable asyncDrawable = new AsyncDrawable(this.act.getResources(), this.placeholder, task);
            //imageAdapterViewHolder.imageView.setBackground(asyncDrawable);
            imageAdapterViewHolder.imageView.setImageDrawable(asyncDrawable);
            if(mode == 0){
                imageAdapterViewHolder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            }else if(mode == 1){
                imageAdapterViewHolder.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }
            task.execute(url);
        }
    }

    private boolean cancelCurrentTask(String url, ImageView imageView){
        DownloadBitmapTask bitmapTask = AsyncDrawable.getDownloadBitmapTaskReference(imageView);

        if(bitmapTask != null){
            String taskURL = bitmapTask.getUrl();
            if (!url.equals(taskURL)) {
                bitmapTask.cancel(true);
                return true;
            }
            else {
                return false;
            }
        }

        return true;
    }

    @Override
    public int getItemCount() {
        return images.count();
    }

    class ImageAdapterViewHolder
            extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        Drawable v;

        public ImageAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.item_image);
            this.v = act.getDrawable(R.drawable.p);
        }

        public Drawable getV(){
            return v;
        }

        public Context getC(){
            return act;
        }
    }
}
