package com.houkcorp.locationflickr.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;

import com.houkcorp.locationflickr.R;
import com.houkcorp.locationflickr.fragments.ImageDetailViewFragment;
import com.houkcorp.locationflickr.model.FlickrImageSearchPhoto;

public class ImageDetailActivity extends BaseActivity {
    public static final String IMAGE_DETAIL_FRAGMENT = "image_detail_fragment";
    public static final String EXTRA_FLICKR_PHOTO = "flickr_photo";

    private FlickrImageSearchPhoto mFlickrImageSearchPhoto;

    public static Intent newIntent(Context context, FlickrImageSearchPhoto flickrImageSearchPhoto) {
        Intent intent = new Intent(context, ImageDetailActivity.class);
        intent.putExtra(EXTRA_FLICKR_PHOTO, flickrImageSearchPhoto);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFlickrImageSearchPhoto = getIntent().getParcelableExtra(EXTRA_FLICKR_PHOTO);

        if(savedInstanceState == null) {
            addFragment();
        }
    }

    @Override
    protected int getLayoutResources() {
        return R.layout.activity_image_detail_view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();

        return super.onOptionsItemSelected(item);
    }

    /**
     * Adds the fragment to the activity.
     */
    private void addFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = ImageDetailViewFragment.newInstance(mFlickrImageSearchPhoto);
        fragmentManager.beginTransaction().replace(R.id.image_detail_fragment_id, fragment, IMAGE_DETAIL_FRAGMENT).commit();
    }
}