package com.houkcorp.locationflickr.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.houkcorp.locationflickr.R;
import com.houkcorp.locationflickr.databinding.ActivityImageListViewBinding;
import com.houkcorp.locationflickr.fragments.ImageListViewFragment;

public class ImageListViewActivity extends AppCompatActivity {
    private static final String IMAGE_GRID_VIEW_FRAGMENT = "image_grid_view_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityImageListViewBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_image_list_view);
        Toolbar toolbar = binding.imageViewToolbar.toolbar;
        if(toolbar != null) {
            setSupportActionBar(toolbar);

            getSupportActionBar().setTitle(R.string.flickr_images);
        }

        if(savedInstanceState == null) {
            addFragment();
        }
    }

    /**
     * Adds the fragment to the activity
     */
    private void addFragment() {
        getSupportFragmentManager().beginTransaction().add(R.id.image_grid_fragment_id,
                ImageListViewFragment.newInstance(), IMAGE_GRID_VIEW_FRAGMENT).commit();
    }
}