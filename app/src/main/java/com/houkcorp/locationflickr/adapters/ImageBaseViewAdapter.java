package com.houkcorp.locationflickr.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.houkcorp.locationflickr.model.FlickrImage;

import java.util.ArrayList;

public class ImageBaseViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<FlickrImage> mFlickrImages;

    public ImageBaseViewAdapter(Context context, ArrayList<FlickrImage> flickrImages) {
        mContext = context;
        mFlickrImages = flickrImages;
    }

    @Override
    public int getCount() {
        return mFlickrImages.size();
    }

    @Override
    public FlickrImage getItem(int position) {
        return mFlickrImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if(convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView)convertView;
        }

        Bitmap bitmap = mFlickrImages.get(position).getBitmap();
        imageView.setImageBitmap(bitmap);

        return imageView;
    }

    public void clearArray() {
        mFlickrImages = new ArrayList<>();
    }

    public void addFlickrImages(ArrayList<FlickrImage> flickrImages) {
        mFlickrImages = flickrImages;
    }
}