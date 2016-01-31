package com.houkcorp.locationflickr.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.houkcorp.locationflickr.Constants;
import com.houkcorp.locationflickr.model.FlickrImageSearchPhoto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class ImageBaseViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<FlickrImageSearchPhoto> mFlickrImages;

    public ImageBaseViewAdapter(Context context, ArrayList<FlickrImageSearchPhoto> flickrImages) {
        mContext = context;
        mFlickrImages = flickrImages;
    }

    @Override
    public int getCount() {
        return mFlickrImages.size();
    }

    @Override
    public FlickrImageSearchPhoto getItem(int position) {
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

        handleFetchThumbnails(mFlickrImages.get(position), imageView);

        return imageView;
    }

    public void clearArray() {
        mFlickrImages = new ArrayList<>();
    }

    public void addFlickrImages(ArrayList<FlickrImageSearchPhoto> flickrImages) {
        mFlickrImages = flickrImages;
    }

    public void handleFetchThumbnails(FlickrImageSearchPhoto flickrImageSearchPhoto, ImageView imageView) {
        Picasso.with(mContext)
                .load(String.format(Locale.getDefault(), Constants.DEFAULT_IMAGE_URL,
                        flickrImageSearchPhoto.getFarm(), flickrImageSearchPhoto.getServer(),
                        flickrImageSearchPhoto.getId(), flickrImageSearchPhoto.getSecret(), "t"))
                .into(imageView);
    }
}