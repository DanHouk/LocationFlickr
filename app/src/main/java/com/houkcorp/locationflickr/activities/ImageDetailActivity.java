package com.houkcorp.locationflickr.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.houkcorp.locationflickr.R;
import com.houkcorp.locationflickr.fragments.ImageDetailViewFragment;
import com.houkcorp.locationflickr.model.FlickrPhoto;

public class ImageDetailActivity extends BaseActivity {
    public static final String IMAGE_DETAIL_FRAGMENT = "image_detail_fragment";
    public static final String EXTRA_FLICKR_PHOTO = "flickr_photo";

    private FlickrPhoto mFlickrPhoto;

    public static Intent newIntent(Context context, FlickrPhoto flickrPhoto) {
        Intent intent = new Intent(context, ImageDetailActivity.class);
        intent.putExtra(EXTRA_FLICKR_PHOTO, flickrPhoto);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFlickrPhoto = getIntent().getParcelableExtra(EXTRA_FLICKR_PHOTO);

        if(savedInstanceState == null) {
            addFragment();
        }

        /*FIXME: Possible null pointer*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Adds the fragment to the activity.
     */
    private void addFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = ImageDetailViewFragment.newInstance(mFlickrPhoto);
        fragmentManager.beginTransaction().replace(R.id.image_detail_fragment_id, fragment, IMAGE_DETAIL_FRAGMENT).commit();
    }
}