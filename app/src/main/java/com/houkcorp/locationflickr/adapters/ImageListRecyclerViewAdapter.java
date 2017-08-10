package com.houkcorp.locationflickr.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.houkcorp.locationflickr.Constants;
import com.houkcorp.locationflickr.databinding.ItemImageListImageViewBinding;
import com.houkcorp.locationflickr.model.FlickrPhoto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class ImageListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<FlickrPhoto> mFlickrImages;

    public ImageListRecyclerViewAdapter(ArrayList<FlickrPhoto> flickrPhotos) {
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

    /**
     * The ViewHolder that holds the bindings and the views for the image in the GridView.
     */
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

    /*FIXME:Hook up diff tool*/

    /**
     *
     */
    public void clearArray() {
        mFlickrImages = new ArrayList<>();
        notifyDataSetChanged();
    }

    /**
     * Adds more flickr images to the already existing list.
     * @param flickrPhotos
     */
    public void addFlickrImages(ArrayList<FlickrPhoto> flickrPhotos) {
        mFlickrImages.addAll(flickrPhotos);
    }
}