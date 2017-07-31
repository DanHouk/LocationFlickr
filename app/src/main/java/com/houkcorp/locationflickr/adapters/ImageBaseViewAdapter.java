package com.houkcorp.locationflickr.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.houkcorp.locationflickr.Constants;
import com.houkcorp.locationflickr.R;
import com.houkcorp.locationflickr.databinding.ItemImageListImageViewBinding;
import com.houkcorp.locationflickr.model.FlickrPhoto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class ImageBaseViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<FlickrPhoto> mFlickrImages;

    public ImageBaseViewAdapter(ArrayList<FlickrPhoto> flickrPhotos) {
        mFlickrImages = flickrPhotos;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemImageListImageViewBinding binding =
                ItemImageListImageViewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ImageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
        FlickrPhoto flickrPhoto = mFlickrImages.get(position);

        Picasso.with(imageViewHolder.getBinding().getRoot().getContext())
                .load(String.format(Locale.getDefault(), Constants.DEFAULT_IMAGE_URL,
                        flickrPhoto.getFarm(), flickrPhoto.getServer(),
                        flickrPhoto.getId(), flickrPhoto.getSecret(), "t"))
                .into(imageViewHolder.getBinding().imageListIv);
    }

    @Override
    public int getItemCount() {
        return mFlickrImages.size();
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {
        private ItemImageListImageViewBinding mBinding;

        public ImageViewHolder(ItemImageListImageViewBinding binding) {
            super(binding.getRoot());

            mBinding = binding;
        }

        public ItemImageListImageViewBinding getBinding() {
            return mBinding;
        }
    }
    /*private Context mContext;
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
    }*/
}