package com.houkcorp.locationflickr.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.houkcorp.locationflickr.R;
import com.houkcorp.locationflickr.fragments.ImageDetailViewFragment;

public class ImageDetailActivity extends AppCompatActivity {
    public static final String IMAGE_DETAIL_FRAGMENT = "image_detail_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail_view);

        if(savedInstanceState == null) {
            //addFragment();
        }
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
        Fragment fragment = ImageDetailViewFragment.newInstance();
        fragmentManager.beginTransaction().replace(R.id.image_detail_fragment_id, fragment, IMAGE_DETAIL_FRAGMENT).commit();
    }
}