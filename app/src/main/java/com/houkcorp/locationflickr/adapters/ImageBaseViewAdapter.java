package com.houkcorp.locationflickr.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.houkcorp.locationflickr.Constants;
import com.houkcorp.locationflickr.databinding.ItemImageListImageViewBinding;
import com.houkcorp.locationflickr.model.FlickrPhoto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class ImageBaseViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<FlickrPhoto> mFlickrImages;

    public ImageBaseViewAdapter(Context context, ArrayList<FlickrPhoto> flickrImages) {
        mContext = context;
        mFlickrImages = flickrImages;
    }

    @Override
    public int getCount() {
        return mFlickrImages.size();
    }

    @Override
    public FlickrPhoto getItem(int position) {
        return mFlickrImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ItemImageListImageViewBinding binding = ItemImageListImageViewBinding.inflate(LayoutInflater.from())
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

    public void addFlickrImages(ArrayList<FlickrPhoto> flickrImages) {
        mFlickrImages = flickrImages;
    }

    public void handleFetchThumbnails(FlickrPhoto flickrPhoto, ImageView imageView) {
        Picasso.with(mContext)
                .load(String.format(Locale.getDefault(), Constants.DEFAULT_IMAGE_URL,
                        flickrPhoto.getFarm(), flickrPhoto.getServer(),
                        flickrPhoto.getId(), flickrPhoto.getSecret(), "t"))
                .into(imageView);
    }
}