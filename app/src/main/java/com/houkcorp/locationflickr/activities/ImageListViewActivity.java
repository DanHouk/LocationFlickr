package com.houkcorp.locationflickr.activities;

import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.houkcorp.locationflickr.R;
import com.houkcorp.locationflickr.databinding.ActivityImageGridViewBinding;
import com.houkcorp.locationflickr.fragments.ImageListViewFragment;
import com.houkcorp.locationflickr.util.UIUtils;

public class ImageListViewActivity extends AppCompatActivity {
    private static final String IMAGE_GRID_VIEW_FRAGMENT = "image_grid_view_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityImageGridViewBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_image_grid_view);
        Toolbar toolbar = binding.imageViewToolbar.toolbar;
        if(toolbar != null) {
            setSupportActionBar(toolbar);

            //FIXME: What was this for and is it still needed?
            //getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        if(savedInstanceState == null) {
            addFragment();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image_grid_view, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.about_menu: {
                String displayMessage;
                try {
                    int versionCode =
                            getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
                    displayMessage = getString(R.string.about_location_flickr, versionCode);
                } catch(PackageManager.NameNotFoundException nameNotFoundException) {
                    displayMessage = "N/A";
                }

                UIUtils.showDialogMessage(this, R.string.about, displayMessage);

                break;
            }

            /*FIXME: Is there a better way to do this?  Possibly use a bus.*/
            case R.id.refresh_menu: {
                ImageListViewFragment imageListViewFragment =
                        (ImageListViewFragment)getSupportFragmentManager()
                                .findFragmentByTag(IMAGE_GRID_VIEW_FRAGMENT);
                if(imageListViewFragment != null) {
                    imageListViewFragment.clearListAndReQuery();
                }

                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Adds the fragment to the activity
     */
    private void addFragment() {
        getSupportFragmentManager().beginTransaction().add(R.id.image_grid_fragment_id,
                ImageListViewFragment.newInstance(), IMAGE_GRID_VIEW_FRAGMENT).commit();
    }
}