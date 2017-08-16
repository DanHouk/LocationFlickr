package com.houkcorp.locationflickr.presenters;

import android.content.Intent;
import android.view.View;

import com.houkcorp.locationflickr.activities.ImageDetailActivity;
import com.houkcorp.locationflickr.model.FlickrPhoto;

public class ImageListPresenter {
    public void onDetailClicked(View view, FlickrPhoto flickrPhoto) {
        Intent intent = ImageDetailActivity.newIntent(view.getContext(), flickrPhoto);
        view.getContext().startActivity(intent);
    }
}