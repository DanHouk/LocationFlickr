package com.houkcorp.locationflickr.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.houkcorp.locationflickr.model.FlickrImage;

import java.util.ArrayList;

public class ImageViewAdapter extends ArrayAdapter<FlickrImage> {
    private ArrayList<FlickrImage> mImages;
    private Context mContext;

    public ImageViewAdapter(Context context, int resource, ArrayList<FlickrImage> objects) {
        super(context, resource, objects);
        mImages = objects;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView)convertView;
        if(imageView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(120,
                    120));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }

        Bitmap bitmap = mImages.get(position).getBitmap();
        imageView.setImageBitmap(bitmap);

        return imageView;
    }
}